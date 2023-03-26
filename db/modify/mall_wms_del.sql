/*==============================================================*/
/* Table: wms_purchase                                             */
/*==============================================================*/
alter table wms_purchase DROP is_deleted;
alter table wms_purchase DROP create_by;
alter table wms_purchase DROP create_time;
alter table wms_purchase DROP update_by;
alter table wms_purchase DROP update_time;

/*==============================================================*/
/* Table: wms_purchase_detail                                             */
/*==============================================================*/
alter table wms_purchase_detail DROP is_deleted;
alter table wms_purchase_detail DROP create_by;
alter table wms_purchase_detail DROP create_time;
alter table wms_purchase_detail DROP update_by;
alter table wms_purchase_detail DROP update_time;

/*==============================================================*/
/* Table: wms_ware_info                                             */
/*==============================================================*/
alter table wms_ware_info DROP is_deleted;
alter table wms_ware_info DROP create_by;
alter table wms_ware_info DROP create_time;
alter table wms_ware_info DROP update_by;
alter table wms_ware_info DROP update_time;

/*==============================================================*/
/* Table: wms_ware_order_task                                             */
/*==============================================================*/
alter table wms_ware_order_task DROP is_deleted;
alter table wms_ware_order_task DROP create_by;
alter table wms_ware_order_task DROP create_time;
alter table wms_ware_order_task DROP update_by;
alter table wms_ware_order_task DROP update_time;

/*==============================================================*/
/* Table: wms_ware_order_task_detail                                             */
/*==============================================================*/
alter table wms_ware_order_task_detail DROP is_deleted;
alter table wms_ware_order_task_detail DROP create_by;
alter table wms_ware_order_task_detail DROP create_time;
alter table wms_ware_order_task_detail DROP update_by;
alter table wms_ware_order_task_detail DROP update_time;

/*==============================================================*/
/* Table: wms_ware_sku                                             */
/*==============================================================*/
alter table wms_ware_sku DROP is_deleted;
alter table wms_ware_sku DROP create_by;
alter table wms_ware_sku DROP create_time;
alter table wms_ware_sku DROP update_by;
alter table wms_ware_sku DROP update_time;

