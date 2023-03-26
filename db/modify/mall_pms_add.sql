/*==============================================================*/
/* Table: pms_attr                                             */
/*==============================================================*/
ALTER TABLE pms_attr ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE pms_attr ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_attr ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE pms_attr ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_attr ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: pms_attr_attrgroup_relation                                             */
/*==============================================================*/
ALTER TABLE pms_attr_attrgroup_relation ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE pms_attr_attrgroup_relation ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_attr_attrgroup_relation ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE pms_attr_attrgroup_relation ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_attr_attrgroup_relation ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: pms_attr_group                                             */
/*==============================================================*/
ALTER TABLE pms_attr_group ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE pms_attr_group ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_attr_group ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE pms_attr_group ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_attr_group ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: pms_brand                                             */
/*==============================================================*/
ALTER TABLE pms_brand ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE pms_brand ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_brand ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE pms_brand ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_brand ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: pms_category                                             */
/*==============================================================*/
ALTER TABLE pms_category ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE pms_category ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_category ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE pms_category ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_category ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: pms_category_brand_relation                                             */
/*==============================================================*/
ALTER TABLE pms_category_brand_relation ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE pms_category_brand_relation ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_category_brand_relation ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE pms_category_brand_relation ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_category_brand_relation ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: pms_comment_replay                                             */
/*==============================================================*/
ALTER TABLE pms_comment_replay ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE pms_comment_replay ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_comment_replay ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE pms_comment_replay ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_comment_replay ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: pms_product_attr_value                                             */
/*==============================================================*/
ALTER TABLE pms_product_attr_value ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE pms_product_attr_value ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_product_attr_value ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE pms_product_attr_value ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_product_attr_value ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: pms_sku_images                                             */
/*==============================================================*/
ALTER TABLE pms_sku_images ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE pms_sku_images ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_sku_images ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE pms_sku_images ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_sku_images ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: pms_sku_info                                             */
/*==============================================================*/
ALTER TABLE pms_sku_info ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE pms_sku_info ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_sku_info ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE pms_sku_info ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_sku_info ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: pms_sku_sale_attr_value                                             */
/*==============================================================*/
ALTER TABLE pms_sku_sale_attr_value ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE pms_sku_sale_attr_value ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_sku_sale_attr_value ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE pms_sku_sale_attr_value ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_sku_sale_attr_value ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: pms_spu_comment                                             */
/*==============================================================*/
ALTER TABLE pms_spu_comment ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE pms_spu_comment ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_spu_comment ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE pms_spu_comment ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_spu_comment ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: pms_spu_images                                             */
/*==============================================================*/
ALTER TABLE pms_spu_images ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE pms_spu_images ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_spu_images ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE pms_spu_images ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_spu_images ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: pms_spu_info                                             */
/*==============================================================*/
ALTER TABLE pms_spu_info ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE pms_spu_info ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_spu_info ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE pms_spu_info ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_spu_info ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: pms_spu_info_desc                                             */
/*==============================================================*/
ALTER TABLE pms_spu_info_desc ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE pms_spu_info_desc ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_spu_info_desc ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE pms_spu_info_desc ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE pms_spu_info_desc ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

