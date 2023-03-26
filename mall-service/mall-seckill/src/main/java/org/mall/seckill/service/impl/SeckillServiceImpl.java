package org.mall.seckill.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mall.common.constant.SeckillConstant;
import org.mall.common.dto.mq.SeckillOrderDTO;
import org.mall.common.pojo.ResponseEntity;
import org.mall.common.util.JsonUtil;
import org.mall.coupon.dto.SeckillSessionDTO;
import org.mall.coupon.entity.SeckillSkuRelation;
import org.mall.coupon.feign.ICouponClient;
import org.mall.member.entity.Member;
import org.mall.product.entity.SkuInfo;
import org.mall.product.feign.IProductClient;
import org.mall.seckill.dto.chache.SeckillSkuInfoDTO;
import org.mall.seckill.interceptor.SeckillInterceptor;
import org.mall.seckill.service.SeckillService;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author sxs
 * @since 2023/3/7
 */
@Slf4j
@Service
public class SeckillServiceImpl implements SeckillService {
    private final ICouponClient couponClient;
    private final IProductClient productClient;
    private final StringRedisTemplate redisTemplate;
    private final RedissonClient redissonClient;
    private final RabbitTemplate rabbitTemplate;

    public SeckillServiceImpl(ICouponClient couponClient, IProductClient productClient, StringRedisTemplate redisTemplate, RedissonClient redissonClient, RabbitTemplate rabbitTemplate) {
        this.couponClient = couponClient;
        this.productClient = productClient;
        this.redisTemplate = redisTemplate;
        this.redissonClient = redissonClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void uploadSeckillSkuLatest3Day() {
        ResponseEntity<List<SeckillSessionDTO>> resp = couponClient.getLatest3DaySession();
        if (resp.nonOK()) {
            log.error(resp.toString());
            return;
        }
        List<SeckillSessionDTO> sessionDTOs = resp.getData();
        //上架商品 缓存到redis
        //1.缓存活动
        saveSeckillSession(sessionDTOs);
        //2.缓存活动关联的商品
        saveSeckillSku(sessionDTOs);

    }

    @Override
    public List<SeckillSkuInfoDTO> getCurrentSeckillSkus() {
        //1.确定当前时间
        long current = System.currentTimeMillis();
        //2.
        Set<String> keys = redisTemplate.keys(SeckillConstant.CACHE_KEY_SESSION + "*");
        for (String key : keys) {
            String[] s = key.replaceAll(SeckillConstant.CACHE_KEY_SESSION, "").split("_");
            Long start = Long.valueOf(s[0]);
            Long end = Long.valueOf(s[1]);
            if (current >= start && current <= end) {
                //当前场次的商品id
                List<String> range = redisTemplate.opsForList().range(key, 0, -1);
                //
                BoundHashOperations<String, String, String> hashOps = redisTemplate.boundHashOps(SeckillConstant.CACHE_KEY_SKU);
                List<String> jsons = hashOps.multiGet(range);
                if (!CollectionUtils.isEmpty(jsons)) {
                    List<SeckillSkuInfoDTO> collect = jsons.stream().map(json -> {
                        SeckillSkuInfoDTO seckillSkuInfoDTO = JsonUtil.jsonToClass(json, SeckillSkuInfoDTO.class);
                        return seckillSkuInfoDTO;
                    }).collect(Collectors.toList());
                    return collect;
                }
                break;
            }
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public SeckillSkuInfoDTO getSeckillSku(Long skuId) {
        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(SeckillConstant.CACHE_KEY_SKU);
        Set<String> keys = ops.keys();
        if (!CollectionUtils.isEmpty(keys)) {
            //todo 待修改 如果商品有多个场次 判断时间最早的场次
            String regx = "\\d+_" + skuId;
            List<String> matchedKeys = keys.stream().filter(key -> Pattern.matches(regx, key)).collect(Collectors.toList());
            List<String> jsons = ops.multiGet(matchedKeys);
            List<SeckillSkuInfoDTO> dtos = jsons.stream().map(json -> JsonUtil.jsonToClass(json, SeckillSkuInfoDTO.class))
                    .sorted((o1, o2) -> {
                        if (o1.getStartTime() < o2.getStartTime()) {
                            return -1;
                        } else {
                            return 1;
                        }
                    }).collect(Collectors.toList());
            long currentTimeMillis = System.currentTimeMillis();
            for (SeckillSkuInfoDTO dto : dtos) {
                if (currentTimeMillis >= dto.getStartTime() && currentTimeMillis <= dto.getEndTime()) {
                    return dto;
                } else if (currentTimeMillis < dto.getStartTime()){
                    dto.setRandomCode("");
                    return dto;
                }
            }
        }
        return null;
    }

    @Override
    public String kill(String killId, String code, Integer num) {
        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(SeckillConstant.CACHE_KEY_SKU);
        //1.从redis中获取秒杀商品信息
        String json = ops.get(killId);
        if (StringUtils.isBlank(json)) {
           return "";
        }
        SeckillSkuInfoDTO seckillSku = JsonUtil.jsonToClass(json, SeckillSkuInfoDTO.class);
        //2.校验时间
        //3.校验随机码
        //4.校验数量是否超出限制
        Long startTime = seckillSku.getStartTime();
        Long endTime = seckillSku.getEndTime();
        long currentTime = System.currentTimeMillis();
        boolean flag = currentTime > startTime && currentTime < endTime && code.equals(seckillSku.getRandomCode()) && num <= seckillSku.getSeckillLimit();
        if (!flag) {
            return  "";
        }
        //5.校验当前用户是否秒杀过 保证幂等性
        Member member = SeckillInterceptor.threadLocal.get();
        String key = SeckillConstant.CACHE_KEY_USER_RECORD + member.getId() + "_" + killId;
        Boolean absent = redisTemplate.opsForValue().setIfAbsent(key, num.toString(), endTime - currentTime, TimeUnit.MILLISECONDS);
        if (!absent) {
            return null;
        }
        //6.尝试从库存信号量扣减
        RSemaphore semaphore = redissonClient.getSemaphore(SeckillConstant.CACHE_KEY_STOCK + code);
        boolean acquire = semaphore.tryAcquire(num);
        if (!acquire) {
            redisTemplate.delete(SeckillConstant.CACHE_KEY_USER_RECORD + member.getId() + "_" + killId);
            return "";
        }
        //6.1 扣减成功 发送mq消息通知订单服务创建订单
        String orderSn = IdWorker.getTimeId();
        SeckillOrderDTO seckillSkuDTO = new SeckillOrderDTO();
        seckillSkuDTO.setPromotionSessionId(seckillSku.getPromotionSessionId());
        seckillSkuDTO.setSkuId(seckillSku.getSkuId());
        seckillSkuDTO.setNum(num);
        seckillSkuDTO.setSeckillPrice(seckillSku.getSeckillPrice());
        seckillSkuDTO.setMemberId(member.getId());
        seckillSkuDTO.setOrderSn(orderSn);
        seckillSkuDTO.setRandomCode(code);
        rabbitTemplate.convertAndSend("order-event-exchange", "order.seckill.order", seckillSkuDTO, new CorrelationData(orderSn));
        return orderSn;
    }

    private void saveSeckillSku(List<SeckillSessionDTO> sessionDTOs){
        for (SeckillSessionDTO sessionDTO : sessionDTOs) {
            for (SeckillSkuRelation relation : sessionDTO.getRelations()) {
                String hashKey = relation.getPromotionSessionId() + "_" + relation.getSkuId();
                if (!redisTemplate.opsForHash().hasKey(SeckillConstant.CACHE_KEY_SKU, hashKey)) {
                    ResponseEntity<SkuInfo> resp = productClient.getSkuInfo(relation.getSkuId());
                    if (resp.nonOK()) {
                        log.error(resp.toString());
                        continue;
                    }
                    SeckillSkuInfoDTO seckillSkuInfoDTO = new SeckillSkuInfoDTO();
                    BeanUtils.copyProperties(relation, seckillSkuInfoDTO);
                    //2. skuInfo信息
                    seckillSkuInfoDTO.setSkuInfo(resp.getData());
                    //3. 时间信息
                    seckillSkuInfoDTO.setStartTime(sessionDTO.getStartTime().toInstant(ZoneOffset.of("+8")).toEpochMilli());
                    seckillSkuInfoDTO.setEndTime(sessionDTO.getEndTime().toInstant(ZoneOffset.of("+8")).toEpochMilli());
                    //4. 随机码
                    String token = UUID.randomUUID().toString().replaceAll("-", "");
                    seckillSkuInfoDTO.setRandomCode(token);
                    redisTemplate.opsForHash().put(SeckillConstant.CACHE_KEY_SKU, hashKey, JsonUtil.classToJson(seckillSkuInfoDTO));
                    //5.秒杀库存-分布式信号量 限流
                    try {
                        RSemaphore semaphore = redissonClient.getSemaphore(SeckillConstant.CACHE_KEY_STOCK + token);
                        semaphore.trySetPermits(Math.toIntExact(seckillSkuInfoDTO.getSeckillCount()));
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        redisTemplate.opsForHash().delete(SeckillConstant.CACHE_KEY_SKU, hashKey);
                    }
                }
            }

        }
    }

    private void saveSeckillSession(List<SeckillSessionDTO> sessionDTOs) {
        for (SeckillSessionDTO sessionDTO : sessionDTOs) {
            String key = SeckillConstant.CACHE_KEY_SESSION + sessionDTO.getStartTime().toInstant(ZoneOffset.of("+8")).toEpochMilli() + "_" + sessionDTO.getEndTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
            if (!redisTemplate.hasKey(key)) {
                List<String> skuIds = sessionDTO.getRelations().stream().map(seckillSkuRelation -> seckillSkuRelation.getPromotionSessionId() + "_" + seckillSkuRelation.getSkuId()).collect(Collectors.toList());
                redisTemplate.opsForList().leftPushAll(key, skuIds);
            }
        }
    }
}
