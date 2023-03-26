package org.mall.order.service.impl;

import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.mall.cart.dto.CartItemDTO;
import org.mall.cart.feign.ICartClient;
import org.mall.common.constant.CommonConstant;
import org.mall.common.constant.ErrorCode;
import org.mall.common.dto.mq.SeckillOrderDTO;
import org.mall.common.exception.BusinessException;
import org.mall.common.pojo.PageQuery;
import org.mall.common.pojo.PageResult;
import org.mall.common.pojo.ResponseEntity;
import org.mall.common.util.Assert;
import org.mall.common.util.ErrorCodeUtil;
import org.mall.common.util.JsonUtil;
import org.mall.member.entity.Member;
import org.mall.member.entity.MemberReceiveAddress;
import org.mall.member.feign.IMemberClient;
import org.mall.order.config.AlipayTemplate;
import org.mall.order.constant.OrderConstant;
import org.mall.order.constant.OrderStatusEnum;
import org.mall.order.dto.*;
import org.mall.order.entity.Order;
import org.mall.order.entity.OrderItem;
import org.mall.order.entity.PaymentInfo;
import org.mall.order.interceptor.OrderInterceptor;
import org.mall.order.mapper.OrderMapper;
import org.mall.order.mapper.PaymentInfoMapper;
import org.mall.order.service.OrderItemService;
import org.mall.order.service.OrderService;
import org.mall.order.service.PaymentInfoService;
import org.mall.order.vo.OrderVO;
import org.mall.order.vo.PayAsyncVo;
import org.mall.order.vo.PayVo;
import org.mall.product.entity.SpuInfo;
import org.mall.product.feign.IProductClient;
import org.mall.ware.dto.FareDTO;
import org.mall.ware.dto.LockItemDTO;
import org.mall.ware.dto.WareSkuLockDTO;
import org.mall.ware.feign.IWareClient;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author sxs
 * @since 2023-01-24
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    private final RabbitTemplate rabbitTemplate;
    private final IMemberClient memberClient;
    private final ICartClient cartClient;
    private final IWareClient wareClient;
    private final IProductClient productClient;
    private final ThreadPoolExecutor threadPoolExecutor;
    private final StringRedisTemplate redisTemplate;
    private final OrderItemService orderItemService;
    private final PaymentInfoMapper paymentInfoMapper;
    private final PaymentInfoService paymentInfoService;
    private final AlipayTemplate alipayTemplate;


    public OrderServiceImpl(RabbitTemplate rabbitTemplate, IMemberClient memberClient, ICartClient cartClient, IWareClient wareClient, IProductClient productClient, ThreadPoolExecutor threadPoolExecutor, StringRedisTemplate redisTemplate, OrderItemService orderItemService, PaymentInfoMapper paymentInfoMapper, PaymentInfoService paymentInfoService, AlipayTemplate alipayTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.memberClient = memberClient;
        this.cartClient = cartClient;
        this.wareClient = wareClient;
        this.productClient = productClient;
        this.threadPoolExecutor = threadPoolExecutor;
        this.redisTemplate = redisTemplate;
        this.orderItemService = orderItemService;
        this.paymentInfoMapper = paymentInfoMapper;
        this.paymentInfoService = paymentInfoService;
        this.alipayTemplate = alipayTemplate;
    }

    @Override
    public OrderConfirmDTO getConfirmOrder() throws ExecutionException, InterruptedException {
        OrderConfirmDTO orderConfirmDTO = new OrderConfirmDTO();
        Member member = OrderInterceptor.loginUser.get();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        CompletableFuture<Void> addressFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            //1.地址
            //TODO feign
            ResponseEntity<List<MemberReceiveAddress>> addressResp = memberClient.getAddress(member.getId());
            if (addressResp.nonOK()) {
                log.error("{}:{}", addressResp.getCode(), addressResp.getMsg());
                throw new BusinessException(ErrorCodeUtil.getErrorCode(addressResp));
            }
            orderConfirmDTO.setAddress(addressResp.getData());
        }, threadPoolExecutor);

        //2.购物项
        CompletableFuture<Void> cartItemFuture = CompletableFuture.runAsync(() -> {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            //TODO feign
            ResponseEntity<List<CartItemDTO>> cartItemResp = cartClient.getCheckedCartItems();
            if (cartItemResp.nonOK()) {
                log.error("{}:{}", cartItemResp.getCode(), cartItemResp.getMsg());
                throw new BusinessException(ErrorCodeUtil.getErrorCode(cartItemResp));
            }
            orderConfirmDTO.setItems(cartItemResp.getData());
        }, threadPoolExecutor).thenRunAsync(() -> {
            //库存
            List<Long> skuIds = orderConfirmDTO.getItems().stream().map(CartItemDTO::getSkuId).collect(Collectors.toList());
            ResponseEntity<List<Long>> response = wareClient.filterByStock(skuIds);
            if (response.nonOK()) {
                log.error("wareClient.filterByStock={}:{}", response.getCode(), response.getMsg());
                throw new BusinessException(ErrorCodeUtil.getErrorCode(response));
            }
            orderConfirmDTO.setStocks(skuIds.stream()
                    .collect(Collectors.toMap(skuId -> skuId, response.getData()::contains)));
        }, threadPoolExecutor);
        //3.用户积分
        orderConfirmDTO.setIntegration(member.getIntegration());
        //等待异步完成
        CompletableFuture.allOf(addressFuture, cartItemFuture).get();

        //5.防重令牌
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        orderConfirmDTO.setOrderToken(token);
        redisTemplate.opsForValue().set(OrderConstant.ORDER_TOKEN_KEY_PREFIX + member.getId(), token, 30, TimeUnit.MINUTES);
        return orderConfirmDTO;
    }


//    @GlobalTransactional
    @Override
    public OrderSubmitResponseDTO submitOrder(OrderSubmitDTO orderSubmitDTO) {
        Long userId = OrderInterceptor.loginUser.get().getId();
        //0.验证令牌 lua脚本实现原子操作：对比值、删除
        String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1]\n" +
                "then\n" +
                "    return redis.call(\"del\",KEYS[1])\n" +
                "else\n" +
                "    return 0\n" +
                "end";
        String orderToken = orderSubmitDTO.getOrderToken();
        Long resultCode = redisTemplate.execute(new DefaultRedisScript<>(script, Long.class), Arrays.asList(OrderConstant.ORDER_TOKEN_KEY_PREFIX + userId), orderToken);
        OrderSubmitResponseDTO response = new OrderSubmitResponseDTO();
        if (resultCode == 0) {
            //令牌不通过
            response.setCode(1);
            return response;
        }
        //构造订单项
        OrderDTO orderDTO = creatOrderDTO(orderSubmitDTO);
        //验价
        BigDecimal payAmount = orderDTO.getOrder().getPayAmount();
        BigDecimal payPrice = orderSubmitDTO.getPayPrice();
        if (payAmount.subtract(payPrice).abs().doubleValue() < 0.01) {
            //金额对比成功 保存订单数据
            saveOrderDTO(orderDTO);
            //锁定库存
            WareSkuLockDTO wareSkuLockDTO = new WareSkuLockDTO();
            wareSkuLockDTO.setOrderSn(orderDTO.getOrder().getOrderSn());
            List<LockItemDTO> lockItemDTOs = orderDTO.getOrderItems().stream().map(orderItem -> {
                LockItemDTO lockItemDTO = new LockItemDTO();
                lockItemDTO.setSkuId(orderItem.getSkuId());
                lockItemDTO.setNum(orderItem.getSkuQuantity());
                return lockItemDTO;
            }).collect(Collectors.toList());
            wareSkuLockDTO.setLocks(lockItemDTOs);
            //TODO 锁定库存 使用mq
            ResponseEntity<Void> resp = wareClient.orderLockStock(wareSkuLockDTO);
            if (resp.nonOK()) {
                throw new BusinessException(ErrorCode.QUANTITY_EXCEEDS_LIMIT);
            }
            //模拟订单创建失败 ，库存锁定成功
//            int i=1/0;
            response.setCode(0);
            response.setOrder(orderDTO.getOrder());
            //订单创建成功发送mq
            rabbitTemplate.convertAndSend(OrderConstant.MQ_EXCHANGE_NAME, OrderConstant.MQ_DELAY_ROUTING_KEY, orderDTO.getOrder(), new CorrelationData(orderDTO.getOrder().getOrderSn()));
            log.info("发送订单创建成功消息,orderSn:{}",orderDTO.getOrder().getOrderSn());
            return response;
        } else {
            response.setCode(2);
            return response;
        }
    }

    @Override
    public Order getByOrderSn(String orderSn) {
        return this.getOne(Wrappers.<Order>lambdaQuery().eq(Order::getOrderSn, orderSn));
    }

    @Override
    public void closeOrder(Order order) {
        Order byOrderSn = this.getByOrderSn(order.getOrderSn());
        //超时 订单状态为未付款 则关单
        if (byOrderSn.getStatus() == OrderConstant.ORDER_STATUS_NOT_PAID) {
            //todo 调用支付宝查询接口 查询该订单的支付状态,如果支付订单存在,怎根据状态进行操作
            //todo 1.WAIT_BUYER_PAY（交易创建，等待买家付款）-> 订单状态修改为 超时待付款 ，发送库存解锁消息
            //todo 2.TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）-> 订单状态修改为已关闭，发送库存解锁消息
            //todo 3.TRADE_SUCCESS（交易支付成功） ->  订单状态修改为已付款，保存订单支付信息
            //todo 4.TRADE_FINISHED（交易结束，不可退款）-> 订单状态修改为已付款，保存订单支付信息
            Order newOrder = new Order();
            newOrder.setId(order.getId());
            newOrder.setStatus(OrderConstant.ORDER_STATUS_ClOSE);
            this.updateById(newOrder);
            //mq发送已关单消息 去解锁库存
            try {
                rabbitTemplate.convertAndSend(OrderConstant.MQ_EXCHANGE_NAME, OrderConstant.MQ_CLOSE_ORDER_ROUTING_KEY, order, new CorrelationData(order.getOrderSn()));
                log.info("订单超时未支付,发送关单消息,orderSn:{}",order.getOrderSn());
            } catch (AmqpException e) {
                log.error("关单消息发送失败",e.getMessage());
                
            }
        }
    }

    @Override
    public String aliPayOrder(String orderSn) throws AlipayApiException {
        PayVo payVo = new PayVo();
        Order order = this.getByOrderSn(orderSn);
        //订单会自动关单
        if (order == null || order.getStatus() != OrderConstant.ORDER_STATUS_NOT_PAID) {
            throw new BusinessException(ErrorCode.ORDER_CLOSED);
        }
        payVo.setOut_trade_no(order.getOrderSn());
        payVo.setTotal_amount(order.getTotalAmount().setScale(2, RoundingMode.UP).toString());
        payVo.setSubject(order.getOrderSn());
        payVo.setBody(order.getNote());
        return alipayTemplate.pay(payVo);
    }

    @Override
    public PageResult<OrderVO> getOrderPageByMemberId(Long id, PageQuery pageQuery) {
        IPage<Order> page = new Page(pageQuery.getPage(),pageQuery.getLimit());
        this.page(page, Wrappers.<Order>lambdaQuery().eq(Order::getMemberId, id).orderByDesc(Order::getCreateTime));
        ArrayList<OrderVO> orderVOs = new ArrayList<>();
        for (Order order : page.getRecords()) {
            List<OrderItem> orderItems = orderItemService.list(Wrappers.<OrderItem>lambdaQuery().eq(OrderItem::getOrderSn, order.getOrderSn()));
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(order,orderVO);
            orderVO.setOrderItemList(orderItems);
            orderVOs.add(orderVO);
        }
        return new PageResult<OrderVO>(page,orderVOs);
    }

    @Override
    public void aliPayNotify(PayAsyncVo payAsyncVo)  {
        Order order = this.getByOrderSn(payAsyncVo.getOut_trade_no());
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOrderSn(payAsyncVo.getOut_trade_no());
        paymentInfo.setAlipayTradeNo(payAsyncVo.getTrade_no());
        paymentInfo.setOrderId(order.getId());
        paymentInfo.setTotalAmount(new BigDecimal(payAsyncVo.getTotal_amount()));
        paymentInfo.setSubject(payAsyncVo.getSubject());
        paymentInfo.setPaymentStatus(payAsyncVo.getTrade_status());
        paymentInfo.setConfirmTime(LocalDateTime.parse(payAsyncVo.getGmt_create(), DateTimeFormatter.ofPattern(CommonConstant.DATE_TIME_FORMAT)));
        paymentInfo.setCallbackContent(JsonUtil.classToJson(payAsyncVo));
        paymentInfo.setCallbackTime(LocalDateTime.parse(payAsyncVo.getNotify_time(), DateTimeFormatter.ofPattern(CommonConstant.DATE_TIME_FORMAT)));
        paymentInfoService.saveOrUpdate(paymentInfo, Wrappers.<PaymentInfo>lambdaUpdate().eq(PaymentInfo::getOrderSn, paymentInfo.getOrderId()).eq(PaymentInfo::getAlipayTradeNo,paymentInfo.getAlipayTradeNo()));
        if (payAsyncVo.getTrade_status().equals("TRADE_SUCCESS")||payAsyncVo.getTrade_status().equals("TRADE_FINISHED")) {
            Order newOrder = new Order();
            newOrder.setId(order.getId());
            newOrder.setPayType(OrderConstant.PAY_TYPE);
            newOrder.setStatus(OrderConstant.ORDER_STATUS_PAID);
            newOrder.setPaymentTime(LocalDateTime.parse(payAsyncVo.getGmt_payment(), DateTimeFormatter.ofPattern(CommonConstant.DATE_TIME_FORMAT)));
            this.updateById(newOrder);
        }else {

        }

    }

    @Override
    public void createSeckillOrder(SeckillOrderDTO seckillOrderDTO) {
        //构造并保存订单
        Order order = new Order();
        order.setMemberId(seckillOrderDTO.getMemberId());
        order.setOrderSn(seckillOrderDTO.getOrderSn());
        order.setTotalAmount(seckillOrderDTO.getSeckillPrice().multiply(new BigDecimal(seckillOrderDTO.getNum())));
        order.setPayAmount(order.getTotalAmount());
        order.setFreightAmount(new BigDecimal("0"));
        order.setStatus(OrderConstant.ORDER_STATUS_NOT_PAID);
        order.setNote("");
        this.save(order);
        //构造并保存订单项
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order.getId());
        orderItem.setOrderSn(seckillOrderDTO.getOrderSn());
        orderItem.setSkuId(seckillOrderDTO.getSkuId());
        orderItem.setSkuPrice(seckillOrderDTO.getSeckillPrice());
        orderItem.setSkuQuantity(seckillOrderDTO.getNum());
        orderItem.setRealAmount(seckillOrderDTO.getSeckillPrice().multiply(new BigDecimal(seckillOrderDTO.getNum())));
        orderItemService.save(orderItem);
        //发送订单创建成功消息 需要单独设计队列
        rabbitTemplate.convertAndSend(OrderConstant.MQ_EXCHANGE_NAME, OrderConstant.MQ_DELAY_ROUTING_KEY, seckillOrderDTO, new CorrelationData(order.getOrderSn()));
        log.info("发送秒杀订单创建成功消息,orderSn:{}",seckillOrderDTO.getOrderSn());
        //收到订单创建成功消息,判断订单状态,状态为待支付则需要修改为已关闭
        //收到支付成功消息,修改订单状态为待发货
        //问题:支付宝回调成功消息在自动关单之后，订单的状态 待付款->已关闭->待发货
        //支付宝订单创建时间<
    }

    @Override
    public void closeOrder(SeckillOrderDTO seckillOrderDTO) {
        Order order = this.getByOrderSn(seckillOrderDTO.getOrderSn());
        //超时时间到 订单状态为待付款
        if (order.getStatus() == OrderConstant.ORDER_STATUS_NOT_PAID) {
            //todo 调用支付宝查询接口 查询该订单的支付状态,如果支付订单存在,怎根据状态进行操作
            //todo 1.WAIT_BUYER_PAY（交易创建，等待买家付款）-> 订单状态修改为 超时待付款
            //todo 2.TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）-> 订单状态修改为已关闭
            //todo 3.TRADE_SUCCESS（交易支付成功） ->  订单状态修改为 已付款，保存订单支付信息
            //todo 4.TRADE_FINISHED（交易结束，不可退款）-> 订单状态修改为 已付款，保存订单支付信息
            Order orderNew = new Order();
            orderNew.setId(order.getId());
            orderNew.setStatus(OrderConstant.ORDER_STATUS_ClOSE);
            //先关单
            this.updateById(orderNew);
            //发送mq消息 判断是否需要解锁库存信号量
            rabbitTemplate.convertAndSend(OrderConstant.MQ_EXCHANGE_NAME, OrderConstant.MQ_CLOSE_ORDER_ROUTING_KEY, seckillOrderDTO, new CorrelationData(order.getOrderSn()));
            log.info("秒杀订单超时未支付,发送返还库存量消息,,orderSn:{}",order.getOrderSn());
        }

    }

    /**
     * 保存订单数据
     *
     * @param orderDTO
     */
    private void saveOrderDTO(OrderDTO orderDTO) {
        //保存order
        Order order = orderDTO.getOrder();
        this.save(order);
        //保存orderItems
        List<OrderItem> orderItems = orderDTO.getOrderItems();
        orderItems.stream().forEach(orderItem -> orderItem.setOrderId(order.getId()));
        orderItemService.saveBatch(orderItems);
    }

    private OrderDTO creatOrderDTO(OrderSubmitDTO orderSubmitDTO) {
        OrderDTO orderDTO = new OrderDTO();
        //1 创建Order
        Order order = buildOrder(orderSubmitDTO);
        orderDTO.setOrder(order);
        //2 创建OrderItems
        List<OrderItem> orderItems = buildOrderItems(order.getOrderSn());
        //3 检价
        computePrice(order, orderItems);

        orderDTO.setOrderItems(orderItems);
        return orderDTO;

    }

    private void computePrice(Order order, List<OrderItem> orderItems) {
        //订单总额
        BigDecimal total = new BigDecimal(0);
        BigDecimal promotionAmount = new BigDecimal(0);
        BigDecimal integrationAmount = new BigDecimal(0);
        BigDecimal couponAmount = new BigDecimal(0);
        Integer integration = 0;
        Integer growth = 0;
        for (OrderItem orderItem : orderItems) {
            total = total.add(orderItem.getRealAmount());
            promotionAmount = promotionAmount.add(orderItem.getPromotionAmount());
            integrationAmount = integrationAmount.add(orderItem.getIntegrationAmount());
            couponAmount = couponAmount.add(orderItem.getCouponAmount());
            growth += orderItem.getGiftGrowth();
            integration += orderItem.getGiftIntegration();
        }
        //设置 各种价格
        order.setTotalAmount(total);
        order.setPayAmount(total.add(order.getFreightAmount()));
        order.setPromotionAmount(promotionAmount);
        order.setIntegrationAmount(integrationAmount);
        order.setCouponAmount(couponAmount);
        //设置 积分 成长值
        order.setIntegration(integration);
        order.setGrowth(growth);


    }

    /**
     * 构建订单项数据
     *
     * @return
     */
    private List<OrderItem> buildOrderItems(String orderSn) {
        ResponseEntity<List<CartItemDTO>> response = cartClient.getCheckedCartItems();
        Assert.isOK(response);
        List<CartItemDTO> cartItemDTOs = response.getData();
        List<OrderItem> orderItems = cartItemDTOs.stream().map(cartItemDTO -> {
            OrderItem orderItem = buildOrderItem(cartItemDTO);
            orderItem.setOrderSn(orderSn);
            return orderItem;
        }).collect(Collectors.toList());
        return orderItems;
    }

    private OrderItem buildOrderItem(CartItemDTO cartItemDTO) {
        OrderItem orderItem = new OrderItem();
        ResponseEntity<SpuInfo> response = productClient.getSpuInfo(cartItemDTO.getSkuId());
        Assert.isOK(response);
        SpuInfo spuInfo = response.getData();
        //spu信息
        orderItem.setSpuId(spuInfo.getId());
        orderItem.setSpuName(spuInfo.getSpuName());
        orderItem.setCategoryId(spuInfo.getCatalogId());
        //sku信息
        orderItem.setSkuId(cartItemDTO.getSkuId());
        orderItem.setSkuName(cartItemDTO.getTitle());
        orderItem.setSkuPic(cartItemDTO.getImage());
        orderItem.setSkuPrice(cartItemDTO.getPrice());
        orderItem.setSkuQuantity(cartItemDTO.getCount());
        orderItem.setSkuAttrsVals(StringUtils.collectionToDelimitedString(cartItemDTO.getSkuAttrs(), ";"));

        //积分信息
        orderItem.setGiftIntegration(cartItemDTO.getPrice().multiply(BigDecimal.valueOf(cartItemDTO.getCount())).intValue() / 10);
        orderItem.setGiftGrowth(cartItemDTO.getPrice().multiply(BigDecimal.valueOf(cartItemDTO.getCount())).intValue() / 10);
        //优惠信息 (不做)
        orderItem.setPromotionAmount(new BigDecimal(0));
        orderItem.setCouponAmount(new BigDecimal(0));
        orderItem.setIntegrationAmount(new BigDecimal(0));
        //实际金额
        BigDecimal origin = cartItemDTO.getPrice().multiply(new BigDecimal(cartItemDTO.getCount()));
        BigDecimal real = origin.subtract(orderItem.getPromotionAmount())
                .subtract(orderItem.getCouponAmount())
                .subtract(orderItem.getIntegrationAmount());
        orderItem.setRealAmount(real);
        return orderItem;
    }

    /**
     * 构建Order
     *
     * @param orderSubmitDTO
     * @return
     */
    private Order buildOrder(OrderSubmitDTO orderSubmitDTO) {
        ResponseEntity<FareDTO> response = wareClient.fare(orderSubmitDTO.getAddrId());
        if (response.nonOK()) {
            throw new BusinessException(ErrorCodeUtil.getErrorCode(response));
        }
        FareDTO fareDTO = response.getData();
        //创建order
        Order order = new Order();
        order.setMemberId(OrderInterceptor.loginUser.get().getId());
        order.setOrderSn(IdWorker.getTimeId());
        order.setMemberUsername(OrderInterceptor.loginUser.get().getUsername());
        order.setFreightAmount(new BigDecimal(fareDTO.getFare()));
        //收获地址
        order.setReceiverName(fareDTO.getAddress().getName());
        order.setReceiverPhone(fareDTO.getAddress().getPhone());
        order.setReceiverPostCode(fareDTO.getAddress().getPostCode());
        order.setReceiverProvince(fareDTO.getAddress().getProvince());
        order.setReceiverCity(fareDTO.getAddress().getCity());
        order.setReceiverRegion(fareDTO.getAddress().getRegion());
        order.setReceiverDetailAddress(fareDTO.getAddress().getDetailAddress());
        order.setNote(orderSubmitDTO.getNote());
        //订单状态
        order.setStatus(OrderStatusEnum.CREATE_NEW.getCode());
        order.setAutoConfirmDay(7);
        order.setDeleteStatus((byte) 0);

        order.setSourceType(OrderConstant.SOURCE_TYPE);
        return order;
    }
}
