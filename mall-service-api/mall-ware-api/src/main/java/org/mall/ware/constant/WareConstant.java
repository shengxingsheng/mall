package org.mall.ware.constant;

/**
 * @author sxs
 * @since 2023/1/26
 */
public class WareConstant {
    private WareConstant(){}
    //****************** purchase ********************//
    /**
     * 采购单状态 - 新建
     */
    public static final byte PURCHASE_STATUS_NEW = 0;
    /**
     * 采购单状态 - 已分配
     */
    public static final byte PURCHASE_STATUS_ALLOCATED = 1;
    /**
     * 采购单状态 - 已领取
     */
    public static final byte PURCHASE_STATUS_RECEIVED = 2;
    /**
     * 采购单状态 - 已完成
     */
    public static final byte PURCHASE_STATUS_COMPLETED = 3;
    /**
     * 采购单状态 - 有异常
     */
    public static final byte PURCHASE_STATUS_ABNORMAL = 4;

    //****************** purchaseDetail ********************//
    /**
     * 采购需求状态 - 新建
     */
    public static final byte PURCHASE_DETAIL_STATUS_NEW = 0;
    /**
     * 采购需求状态 - 已分配
     */
    public static final byte PURCHASE_DETAIL_STATUS_ALLOCATED = 1;
    /**
     * 采购需求状态 - 正在采购
     */
    public static final byte PURCHASE_DETAIL_STATUS_BUYING = 2;
    /**
     * 采购需求状态 - 已完成
     */
    public static final byte PURCHASE_DETAIL_STATUS_COMPLETED = 3;
    /**
     * 采购需求状态 - 采购失败
     */
    public static final byte PURCHASE_DETAIL_STATUS_FAIl = 4;


    /**
     * 库存锁定状态 已锁定
     */
    public static final byte WARE_ORDER_LOCK_STATUS_LOCKED = 1;
    /**
     * 库存锁定状态 已解锁
     */
    public static final byte WARE_ORDER_LOCK_STATUS_UNLOCK = 2;
    /**
     * 库存锁定状态 已扣减
     */
    public static final byte WARE_ORDER_LOCK_STATUS_DEDUCTED = 3;

    public static final String MQ_EXCHANGE_NAME = "stock-event-exchange";
    public static final String MQ_DELAY_ROUTING_KEY = "stock.locked";
    public static final String MQ_QUEUE_NAME = "stock.release.stock.queue";
}
