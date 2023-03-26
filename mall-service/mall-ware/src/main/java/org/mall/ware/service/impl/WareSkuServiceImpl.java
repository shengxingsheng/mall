package org.mall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.mall.common.constant.ErrorCode;
import org.mall.common.constant.SeckillConstant;
import org.mall.common.dto.mq.SeckillOrderDTO;
import org.mall.common.exception.BusinessException;
import org.mall.common.pojo.PageResult;
import org.mall.common.pojo.ResponseEntity;
import org.mall.common.util.Assert;
import org.mall.common.util.ErrorCodeUtil;
import org.mall.order.constant.OrderConstant;
import org.mall.order.entity.Order;
import org.mall.order.feign.IOrderClient;
import org.mall.product.feign.IProductClient;
import org.mall.ware.config.AlipayTemplate;
import org.mall.ware.constant.WareConstant;
import org.mall.ware.dto.LockItemDTO;
import org.mall.ware.dto.WareSkuLockDTO;
import org.mall.ware.dto.mq.StockLockDTO;
import org.mall.ware.entity.WareOrderTask;
import org.mall.ware.entity.WareOrderTaskDetail;
import org.mall.ware.entity.WareSku;
import org.mall.ware.mapper.WareOrderTaskMapper;
import org.mall.ware.mapper.WareSkuMapper;
import org.mall.ware.query.WareSkuPageQuery;
import org.mall.ware.service.WareOrderTaskDetailService;
import org.mall.ware.service.WareSkuService;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品库存 服务实现类
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
public class WareSkuServiceImpl extends ServiceImpl<WareSkuMapper, WareSku> implements WareSkuService {
    private final IProductClient productClient;
    private final IOrderClient orderClient;
    private final WareOrderTaskMapper wareOrderTaskMapper;
    private final WareOrderTaskDetailService wareOrderTaskDetailService;
    private final RabbitTemplate rabbitTemplate;
    private final StringRedisTemplate redisTemplate;
    private final RedissonClient redissonClient;

    private final AlipayTemplate alipayTemplate;
    public WareSkuServiceImpl(IProductClient productClient, IOrderClient orderClient, WareOrderTaskMapper wareOrderTaskMapper, WareOrderTaskDetailService wareOrderTaskDetailService, RabbitTemplate rabbitTemplate, StringRedisTemplate redisTemplate, RedissonClient redissonClient, AlipayTemplate alipayTemplate) {
        this.productClient = productClient;
        this.orderClient = orderClient;
        this.wareOrderTaskMapper = wareOrderTaskMapper;
        this.wareOrderTaskDetailService = wareOrderTaskDetailService;
        this.rabbitTemplate = rabbitTemplate;
        this.redisTemplate = redisTemplate;
        this.redissonClient = redissonClient;
        this.alipayTemplate = alipayTemplate;
    }

    @Override
    public PageResult page(WareSkuPageQuery query) {
        Page<WareSku> wareSkuPage = new Page<>(query.getPage(), query.getLimit());
        LambdaQueryWrapper<WareSku> wrapper = new LambdaQueryWrapper<>();
        Long skuId = query.getSkuId();
        wrapper.eq(skuId != null, WareSku::getSkuId, skuId);
        Long wareId = query.getWareId();
        wrapper.eq(wareId != null, WareSku::getWareId, wareId);
        this.page(wareSkuPage, wrapper);

        return new PageResult(wareSkuPage);
    }

    @Override
    public void addStock(List<WareSku> wareSkus) {
        Assert.notEmpty(wareSkus, "");
        List<WareSku> saveOrUpdate = wareSkus.stream().map(wareSku -> {
            LambdaQueryWrapper<WareSku> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(WareSku::getSkuId, wareSku.getSkuId());
            wrapper.eq(WareSku::getWareId, wareSku.getWareId());
            WareSku one = this.getOne(wrapper);
            if (Objects.nonNull(one)) {
                //构造更新数据
                wareSku.setId(one.getId());
                //TODO 有数据不一致问题 乐观锁？
                wareSku.setStock(wareSku.getStock() + one.getStock());
            } else {
                //构造新增数据
                try {
                    //TODO feign 失败无需回滚 高级篇？
                    ResponseEntity<String> resp = productClient.getSkuNameById(wareSku.getSkuId());
                    if (resp.getCode().equals(ErrorCode.OK.getCode())) {
                        wareSku.setSkuName(resp.getData());
                    }
                } catch (Exception e) {
                    log.error("feign远程调用失败");
                }
            }
            return wareSku;
        }).collect(Collectors.toList());
        this.saveOrUpdateBatch(saveOrUpdate);
    }

    @Override
    public void saveWareSku(WareSku wareSku) {
        Assert.notNull(wareSku);
        Long skuId = wareSku.getSkuId();
        Long wareId = wareSku.getWareId();
        LambdaQueryWrapper<WareSku> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WareSku::getSkuId, skuId);
        wrapper.eq(WareSku::getWareId, wareId);
        if (this.count(wrapper) > 0) {
            throw new BusinessException(ErrorCode.USER_REQUEST_PARAMETER_ERROR, "该仓库中已有该sku,不能重复添加");
        }
        //TODO feign 失败无需回滚 try-catch
        try {
            ResponseEntity<String> resp = productClient.getSkuNameById(wareSku.getSkuId());
            if (resp.getCode().equals(ErrorCode.OK.getCode())) {
                wareSku.setSkuName(resp.getData());
            }
        } catch (Exception e) {
            log.error("feign远程调用失败");
        }
        this.save(wareSku);
    }

    @Override
    public List<Long> filterByStock(List<Long> skuIdList) {
        if (CollectionUtils.isEmpty(skuIdList)) {
            return Collections.emptyList();
        }
        return this.baseMapper.filterByStock(skuIdList);
    }

    @Override
    public Map<Long, Boolean> hasStock(List<Long> skuIds) {
        if (CollectionUtils.isEmpty(skuIds)) {
            return new HashMap<>(0);
        }
        List<Long> newSkuIds = this.baseMapper.filterByStock(skuIds);
        return skuIds.stream().collect(Collectors.toMap(skuId -> skuId, newSkuIds::contains));
    }

    @Override
    public void orderLockStock(WareSkuLockDTO wareSkuLockDTO) {

        WareOrderTask wareOrderTask = new WareOrderTask();
        wareOrderTask.setOrderSn(wareSkuLockDTO.getOrderSn());
        //保存库存工作单
        wareOrderTaskMapper.insert(wareOrderTask);
        ArrayList<WareOrderTaskDetail> wareOrderTaskDetails = new ArrayList<>();
        for (LockItemDTO lockItem : wareSkuLockDTO.getLocks()) {
            Long skuId = lockItem.getSkuId();
            Integer num = lockItem.getNum();
            //1.查询每个sku的每个仓库的库存 符合数量
            List<Long> wareIds = this.baseMapper.listWareIdHasStock(skuId,lockItem.getNum());
            if (CollectionUtils.isEmpty(wareIds)) {
                log.error("库存不足,订单号:{},skuId:{},num:{}",wareSkuLockDTO.getOrderSn(),skuId,num);
                throw new BusinessException(ErrorCode.QUANTITY_EXCEEDS_LIMIT);
            }
            //锁定库存
            Long n = 0L;
            for (Long wareId : wareIds) {
                n = this.baseMapper.lockSkuStock(skuId, wareId, num);
                if (n == 1L) {
                    WareOrderTaskDetail wareOrderTaskDetail = new WareOrderTaskDetail();
                    wareOrderTaskDetail.setSkuNum(num);
                    wareOrderTaskDetail.setSkuId(skuId);
                    wareOrderTaskDetail.setWareId(wareId);
                    wareOrderTaskDetail.setTaskId(wareOrderTask.getId());
                    wareOrderTaskDetail.setLockStatus(WareConstant.WARE_ORDER_LOCK_STATUS_LOCKED);
                    wareOrderTaskDetails.add(wareOrderTaskDetail);
                    break;
                }
            }
            if (n != 1L) {
                log.error("库存不足,订单号:{},skuId:{},num:{}",wareSkuLockDTO.getOrderSn(),skuId,num);
                throw new BusinessException(ErrorCode.QUANTITY_EXCEEDS_LIMIT);
            }
            lockItem.setWareIds(wareIds);
        }
        // 保存库存工作详情单
        wareOrderTaskDetailService.saveBatch(wareOrderTaskDetails);
        // 发送mq消息
        List<Long> detailIds = wareOrderTaskDetails.stream().map(WareOrderTaskDetail::getId).collect(Collectors.toList());
        StockLockDTO stockLockDTO = new StockLockDTO();
        stockLockDTO.setTaskId(wareOrderTask.getId());
        stockLockDTO.setDetailIds(detailIds);
        rabbitTemplate.convertAndSend(WareConstant.MQ_EXCHANGE_NAME, WareConstant.MQ_DELAY_ROUTING_KEY, stockLockDTO, new CorrelationData(stockLockDTO.getTaskId().toString()));
        log.info("发送锁定库存成功消息,taskId:{}",wareOrderTask.getId());
    }

    /**
     * 解锁条件
     * 库存锁定成功，但订单创建失败
     * @param stockLockDTO
     */
    @Override
    public void unlockStock(StockLockDTO stockLockDTO) {
        if (stockLockDTO.getTaskId() == null && CollectionUtils.isEmpty(stockLockDTO.getDetailIds())) {
            log.warn("任务单数据异常:{}",stockLockDTO.toString());
            return;
        }
        List<WareOrderTaskDetail> wareOrderTaskDetails = wareOrderTaskDetailService.listByIds(stockLockDTO.getDetailIds());
        if (!CollectionUtils.isEmpty(wareOrderTaskDetails)) {
            WareOrderTask wareOrderTask = wareOrderTaskMapper.selectById(stockLockDTO.getTaskId());
            if (wareOrderTask == null) {
                log.warn("任务单数据异常:{}",stockLockDTO.toString());
                return;
            }

            ResponseEntity<Order> resp = orderClient.getOrderByOrderSn(wareOrderTask.getOrderSn());
            if (resp.nonOK()) {
                //调用order服务出现异常
                throw new BusinessException(ErrorCode.FEIGN_SERVICE_ERROR);
            }
            Order order = resp.getData();
            //没有查到订单，需要解锁库存
            if (order == null) {
                log.info("订单创建失败,进行解锁库存...");
                for (WareOrderTaskDetail wareOrderTaskDetail : wareOrderTaskDetails) {
                    if (wareOrderTaskDetail.getLockStatus() == WareConstant.WARE_ORDER_LOCK_STATUS_LOCKED) {
                        //任务详情单的状态为已锁定才解锁
                        Long skuId = wareOrderTaskDetail.getSkuId();
                        Integer skuNum = wareOrderTaskDetail.getSkuNum();
                        Long wareId = wareOrderTaskDetail.getWareId();
                        //解锁库存
                        this.baseMapper.unlockStock(skuId, wareId, skuNum);
                        WareOrderTaskDetail newDetail = new WareOrderTaskDetail();
                        newDetail.setId(wareOrderTaskDetail.getId());
                        newDetail.setLockStatus(WareConstant.WARE_ORDER_LOCK_STATUS_UNLOCK);
                        //更新任务单状态
                        wareOrderTaskDetailService.updateById(newDetail);
                    }
                }
            }
        }
    }

    /**
     * 解锁库存
     * todo 待完善 点击支付在超时之前，支付回调在订单超时之后
     * 1.去数据库查询订单的状态
     * 2.订单的状态为已关闭-> 工作单为锁定状态 ->解锁库存
     * 3.订单的状态为超时待付款
     *  3.1 去支付宝查询订单支付状态
     *  3.2 支付状态为TRADE_SUCCESS||TRADE_FINISHED ->不解锁库存，保存支付信息
     *  3.3 支付状态为TRADE_CLOSED -> 工作单为锁定状态  ->解锁库存
     * @param order
     */
    @Override
    public void unlockStock(Order order) {
        WareOrderTask wareOrderTask = wareOrderTaskMapper.selectOne(Wrappers.<WareOrderTask>lambdaQuery().eq(WareOrderTask::getOrderSn, order.getOrderSn()));
        List<WareOrderTaskDetail> taskDetails = wareOrderTaskDetailService.list(Wrappers.<WareOrderTaskDetail>lambdaQuery()
                .eq(WareOrderTaskDetail::getTaskId, wareOrderTask.getId())
                .eq(WareOrderTaskDetail::getLockStatus,WareConstant.WARE_ORDER_LOCK_STATUS_LOCKED));
        ArrayList<WareOrderTaskDetail> wareOrderTaskDetails = new ArrayList<>();
        for (WareOrderTaskDetail taskDetail : taskDetails) {
            //解锁库存
            this.baseMapper.unlockStock(taskDetail.getSkuId(), taskDetail.getWareId(), taskDetail.getSkuNum());
            WareOrderTaskDetail wareOrderTaskDetail = new WareOrderTaskDetail();
            wareOrderTaskDetail.setId(taskDetail.getId());
            wareOrderTaskDetail.setLockStatus(WareConstant.WARE_ORDER_LOCK_STATUS_UNLOCK);
            wareOrderTaskDetails.add(wareOrderTaskDetail);
        }
        //修改库存工作单状态
        wareOrderTaskDetailService.updateBatchById(wareOrderTaskDetails);
    }

    /**
     * 秒杀订单关单释放库存量
     * todo 用户在最后秒点开支付 ，支付成功回调消息时间 在超时关单之后
     * 1.去数据库查询订单的状态
     * 2.订单的状态为已关闭-> 有 ->返还库存量，删除用户标志位
     * 3.订单的状态为超时待付款
     *  3.1 去支付宝查询订单支付状态
     *  3.2 支付状态为TRADE_SUCCESS||TRADE_FINISHED -> 不解返还库存量，保存支付信息
     *  3.3 支付状态为TRADE_CLOSED -> 返还库存量，删除用户标志位
     * @param seckillOrderDTO
     */
    @Override
    public void unlockSeckillStock(SeckillOrderDTO seckillOrderDTO) {
        String key = SeckillConstant.CACHE_KEY_USER_RECORD + seckillOrderDTO.getMemberId() + "_" + seckillOrderDTO.getPromotionSessionId() + "_" + seckillOrderDTO.getSkuId();
        boolean flag = redisTemplate.opsForValue().get(key).equals("0");
        //保证幂等性
        if (flag) {
            //说明已经被消费过
            return;
        }
        String orderSn = seckillOrderDTO.getOrderSn();
        ResponseEntity<Order> resp = orderClient.getOrderByOrderSn(orderSn);
        if (resp.nonOK()) {
            log.error(ErrorCodeUtil.getErrorCode(resp).toString());
            throw new BusinessException(ErrorCodeUtil.getErrorCode(resp));
        }
        Order order = resp.getData();
        //1.订单支付信息不存在
        if (order.getStatus() == OrderConstant.ORDER_STATUS_ClOSE) {
            //返还库存量 修改用户用户标志位
            redisTemplate.opsForValue().set(key,"0");
            RSemaphore semaphore = redissonClient.getSemaphore(SeckillConstant.CACHE_KEY_STOCK + seckillOrderDTO.getRandomCode());
            semaphore.addPermits(seckillOrderDTO.getNum());
            return;
        }
        //todo 订单的状态为超时待付款
        if (order.getStatus()==OrderConstant.ORDER_STATUS_TIMEOUT_) {
            //去支付宝查询订单状态
            //1.支付状态为TRADE_CLOSED 则返还库存量，删除用户标志位，修改订单状态为已关闭
            //2.支付状态为支付成功 并保存支付信息,修改订单状态为已付款
        }
    }
}
