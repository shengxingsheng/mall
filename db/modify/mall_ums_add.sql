/*==============================================================*/
/* Table: ums_growth_change_history                                             */
/*==============================================================*/
ALTER TABLE ums_growth_change_history ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE ums_growth_change_history ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE ums_growth_change_history ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE ums_growth_change_history ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE ums_growth_change_history ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: ums_integration_change_history                                             */
/*==============================================================*/
ALTER TABLE ums_integration_change_history ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE ums_integration_change_history ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE ums_integration_change_history ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE ums_integration_change_history ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE ums_integration_change_history ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: ums_member                                             */
/*==============================================================*/
ALTER TABLE ums_member ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE ums_member ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE ums_member ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE ums_member ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE ums_member ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: ums_member_collect_spu                                             */
/*==============================================================*/
ALTER TABLE ums_member_collect_spu ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE ums_member_collect_spu ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE ums_member_collect_spu ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE ums_member_collect_spu ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE ums_member_collect_spu ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: ums_member_collect_subject                                             */
/*==============================================================*/
ALTER TABLE ums_member_collect_subject ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE ums_member_collect_subject ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE ums_member_collect_subject ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE ums_member_collect_subject ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE ums_member_collect_subject ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: ums_member_level                                             */
/*==============================================================*/
ALTER TABLE ums_member_level ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE ums_member_level ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE ums_member_level ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE ums_member_level ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE ums_member_level ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: ums_member_login_log                                             */
/*==============================================================*/
ALTER TABLE ums_member_login_log ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE ums_member_login_log ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE ums_member_login_log ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE ums_member_login_log ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE ums_member_login_log ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: ums_member_receive_address                                             */
/*==============================================================*/
ALTER TABLE ums_member_receive_address ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE ums_member_receive_address ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE ums_member_receive_address ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE ums_member_receive_address ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE ums_member_receive_address ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

/*==============================================================*/
/* Table: ums_member_statistics_info                                             */
/*==============================================================*/
ALTER TABLE ums_member_statistics_info ADD COLUMN is_deleted TINYINT(1) DEFAULT 0 COMMENT '逻辑删除 0未删除 1为删除';
ALTER TABLE ums_member_statistics_info ADD COLUMN create_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE ums_member_statistics_info ADD COLUMN create_time DATETIME DEFAULT NULL COMMENT '创建时间';
ALTER TABLE ums_member_statistics_info ADD COLUMN update_by BIGINT DEFAULT NULL COMMENT '创建人id';
ALTER TABLE ums_member_statistics_info ADD COLUMN update_time DATETIME DEFAULT NULL COMMENT '更新时间';

