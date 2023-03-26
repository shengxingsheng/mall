/*==============================================================*/
/* Table: sms_coupon                                             */
/*==============================================================*/
ALTER TABLE sms_coupon ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE sms_coupon ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_coupon ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE sms_coupon ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_coupon ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: sms_coupon_history                                             */
/*==============================================================*/
ALTER TABLE sms_coupon_history ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE sms_coupon_history ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_coupon_history ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE sms_coupon_history ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_coupon_history ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: sms_coupon_spu_category_relation                                             */
/*==============================================================*/
ALTER TABLE sms_coupon_spu_category_relation ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE sms_coupon_spu_category_relation ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_coupon_spu_category_relation ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE sms_coupon_spu_category_relation ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_coupon_spu_category_relation ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: sms_coupon_spu_relation                                             */
/*==============================================================*/
ALTER TABLE sms_coupon_spu_relation ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE sms_coupon_spu_relation ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_coupon_spu_relation ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE sms_coupon_spu_relation ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_coupon_spu_relation ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: sms_home_adv                                             */
/*==============================================================*/
ALTER TABLE sms_home_adv ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE sms_home_adv ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_home_adv ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE sms_home_adv ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_home_adv ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: sms_home_subject                                             */
/*==============================================================*/
ALTER TABLE sms_home_subject ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE sms_home_subject ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_home_subject ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE sms_home_subject ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_home_subject ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: sms_home_subject_spu                                             */
/*==============================================================*/
ALTER TABLE sms_home_subject_spu ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE sms_home_subject_spu ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_home_subject_spu ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE sms_home_subject_spu ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_home_subject_spu ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: sms_member_price                                             */
/*==============================================================*/
ALTER TABLE sms_member_price ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE sms_member_price ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_member_price ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE sms_member_price ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_member_price ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: sms_seckill_promotion                                             */
/*==============================================================*/
ALTER TABLE sms_seckill_promotion ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE sms_seckill_promotion ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_seckill_promotion ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE sms_seckill_promotion ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_seckill_promotion ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: sms_seckill_session                                             */
/*==============================================================*/
ALTER TABLE sms_seckill_session ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE sms_seckill_session ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_seckill_session ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE sms_seckill_session ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_seckill_session ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: sms_seckill_sku_notice                                             */
/*==============================================================*/
ALTER TABLE sms_seckill_sku_notice ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE sms_seckill_sku_notice ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_seckill_sku_notice ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE sms_seckill_sku_notice ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_seckill_sku_notice ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: sms_seckill_sku_relation                                             */
/*==============================================================*/
ALTER TABLE sms_seckill_sku_relation ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE sms_seckill_sku_relation ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_seckill_sku_relation ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE sms_seckill_sku_relation ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_seckill_sku_relation ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: sms_sku_full_reduction                                             */
/*==============================================================*/
ALTER TABLE sms_sku_full_reduction ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE sms_sku_full_reduction ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_sku_full_reduction ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE sms_sku_full_reduction ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_sku_full_reduction ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: sms_sku_ladder                                             */
/*==============================================================*/
ALTER TABLE sms_sku_ladder ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE sms_sku_ladder ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_sku_ladder ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE sms_sku_ladder ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_sku_ladder ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: sms_spu_bounds                                             */
/*==============================================================*/
ALTER TABLE sms_spu_bounds ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE sms_spu_bounds ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_spu_bounds ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE sms_spu_bounds ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE sms_spu_bounds ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

