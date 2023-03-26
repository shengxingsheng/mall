package org.mall.order.constant;

public enum  OrderStatusEnum {
    CREATE_NEW((byte)0,"待付款"),
    PAYED((byte)1,"已付款"),
    SENDED((byte)2,"已发货"),
    RECIEVED((byte)3,"已完成"),
    CANCLED((byte)4,"已取消"),
    SERVICING((byte)5,"售后中"),
    SERVICED((byte)6,"售后完成");
    private Byte code;
    private String msg;

    OrderStatusEnum(Byte code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Byte getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
