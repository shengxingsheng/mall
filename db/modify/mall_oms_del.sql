/*==============================================================*/
/* Table: oms_order                                             */
/*==============================================================*/
alter table oms_order DROP is_deleted;
alter table oms_order DROP create_by;
alter table oms_order DROP create_time;
alter table oms_order DROP update_by;
alter table oms_order DROP update_time;

/*==============================================================*/
/* Table: oms_order_item                                             */
/*==============================================================*/
alter table oms_order_item DROP is_deleted;
alter table oms_order_item DROP create_by;
alter table oms_order_item DROP create_time;
alter table oms_order_item DROP update_by;
alter table oms_order_item DROP update_time;

/*==============================================================*/
/* Table: oms_order_operate_history                                             */
/*==============================================================*/
alter table oms_order_operate_history DROP is_deleted;
alter table oms_order_operate_history DROP create_by;
alter table oms_order_operate_history DROP create_time;
alter table oms_order_operate_history DROP update_by;
alter table oms_order_operate_history DROP update_time;

/*==============================================================*/
/* Table: oms_order_return_apply                                             */
/*==============================================================*/
alter table oms_order_return_apply DROP is_deleted;
alter table oms_order_return_apply DROP create_by;
alter table oms_order_return_apply DROP create_time;
alter table oms_order_return_apply DROP update_by;
alter table oms_order_return_apply DROP update_time;

/*==============================================================*/
/* Table: oms_order_return_reason                                             */
/*==============================================================*/
alter table oms_order_return_reason DROP is_deleted;
alter table oms_order_return_reason DROP create_by;
alter table oms_order_return_reason DROP create_time;
alter table oms_order_return_reason DROP update_by;
alter table oms_order_return_reason DROP update_time;

/*==============================================================*/
/* Table: oms_order_setting                                             */
/*==============================================================*/
alter table oms_order_setting DROP is_deleted;
alter table oms_order_setting DROP create_by;
alter table oms_order_setting DROP create_time;
alter table oms_order_setting DROP update_by;
alter table oms_order_setting DROP update_time;

/*==============================================================*/
/* Table: oms_payment_info                                             */
/*==============================================================*/
alter table oms_payment_info DROP is_deleted;
alter table oms_payment_info DROP create_by;
alter table oms_payment_info DROP create_time;
alter table oms_payment_info DROP update_by;
alter table oms_payment_info DROP update_time;

/*==============================================================*/
/* Table: oms_refund_info                                             */
/*==============================================================*/
alter table oms_refund_info DROP is_deleted;
alter table oms_refund_info DROP create_by;
alter table oms_refund_info DROP create_time;
alter table oms_refund_info DROP update_by;
alter table oms_refund_info DROP update_time;

