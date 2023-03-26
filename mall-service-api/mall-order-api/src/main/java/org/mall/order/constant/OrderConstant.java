package org.mall.order.constant;

/**
 * @author sxs
 * @since 2023/2/21
 */
public class OrderConstant {
    public static final String ORDER_TOKEN_KEY_PREFIX= "order:token:";

    /**
     * 订单状态-未付款
     */
    public static final byte ORDER_STATUS_NOT_PAID = 0;
    /**
     * 订单状态-已付款
     */
    public static final byte ORDER_STATUS_PAID = 1;
    /**
     * 订单状态-已关闭
     */
    public static final byte ORDER_STATUS_ClOSE = 4;

    /**
     * 订单状态-超时待付款
     */
    public static final byte ORDER_STATUS_TIMEOUT_ = 5;
    public static final String MQ_EXCHANGE_NAME = "order-event-exchange";
    public static final String MQ_DELAY_ROUTING_KEY = "order.create.order";
    public static final String MQ_CLOSE_ORDER_ROUTING_KEY = "order.release.other";
    /**
     * 支付方式 1-支付宝
     */
    public static final byte PAY_TYPE = 1;
    /**
     * 订单来源 0->PC订单
     */
    public static final byte SOURCE_TYPE = 0;
}
