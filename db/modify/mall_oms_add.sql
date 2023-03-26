/*==============================================================*/
/* Table: oms_order                                             */
/*==============================================================*/
ALTER TABLE oms_order ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE oms_order ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE oms_order ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE oms_order ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE oms_order ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: oms_order_item                                             */
/*==============================================================*/
ALTER TABLE oms_order_item ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE oms_order_item ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE oms_order_item ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE oms_order_item ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE oms_order_item ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: oms_order_operate_history                                             */
/*==============================================================*/
ALTER TABLE oms_order_operate_history ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE oms_order_operate_history ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE oms_order_operate_history ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE oms_order_operate_history ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE oms_order_operate_history ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: oms_order_return_apply                                             */
/*==============================================================*/
ALTER TABLE oms_order_return_apply ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE oms_order_return_apply ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE oms_order_return_apply ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE oms_order_return_apply ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE oms_order_return_apply ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: oms_order_return_reason                                             */
/*==============================================================*/
ALTER TABLE oms_order_return_reason ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE oms_order_return_reason ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE oms_order_return_reason ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE oms_order_return_reason ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE oms_order_return_reason ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: oms_order_setting                                             */
/*==============================================================*/
ALTER TABLE oms_order_setting ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE oms_order_setting ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE oms_order_setting ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE oms_order_setting ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE oms_order_setting ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: oms_payment_info                                             */
/*==============================================================*/
ALTER TABLE oms_payment_info ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE oms_payment_info ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE oms_payment_info ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE oms_payment_info ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE oms_payment_info ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: oms_refund_info                                             */
/*==============================================================*/
ALTER TABLE oms_refund_info ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE oms_refund_info ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE oms_refund_info ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE oms_refund_info ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE oms_refund_info ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

