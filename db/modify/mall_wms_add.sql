/*==============================================================*/
/* Table: wms_purchase                                             */
/*==============================================================*/
ALTER TABLE wms_purchase ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE wms_purchase ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE wms_purchase ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE wms_purchase ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE wms_purchase ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: wms_purchase_detail                                             */
/*==============================================================*/
ALTER TABLE wms_purchase_detail ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE wms_purchase_detail ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE wms_purchase_detail ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE wms_purchase_detail ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE wms_purchase_detail ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: wms_ware_info                                             */
/*==============================================================*/
ALTER TABLE wms_ware_info ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE wms_ware_info ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE wms_ware_info ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE wms_ware_info ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE wms_ware_info ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: wms_ware_order_task                                             */
/*==============================================================*/
ALTER TABLE wms_ware_order_task ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE wms_ware_order_task ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE wms_ware_order_task ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE wms_ware_order_task ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE wms_ware_order_task ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: wms_ware_order_task_detail                                             */
/*==============================================================*/
ALTER TABLE wms_ware_order_task_detail ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE wms_ware_order_task_detail ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE wms_ware_order_task_detail ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE wms_ware_order_task_detail ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE wms_ware_order_task_detail ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: wms_ware_sku                                             */
/*==============================================================*/
ALTER TABLE wms_ware_sku ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE wms_ware_sku ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE wms_ware_sku ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE wms_ware_sku ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE wms_ware_sku ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

