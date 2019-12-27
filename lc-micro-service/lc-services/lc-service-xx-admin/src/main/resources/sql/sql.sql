CREATE TABLE `cms_dict` (
  `id` BIGINT(20) UNSIGNED NOT NULL COMMENT '主键',
  `code` VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '代码',
  `value` VARCHAR(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '值',
  `name` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `desc` VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
  `sort_no` INT(11) DEFAULT NULL COMMENT '排序号',
  `is_disabled` TINYINT(3) UNSIGNED DEFAULT '0' COMMENT '是否禁用 0：否 1：是',
  `is_deleted` TINYINT(3) UNSIGNED DEFAULT '0' COMMENT '是否删除 0：否 1：是',
  `ext` json DEFAULT NULL COMMENT '拓展字段',
  `created_by` VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `created_at` DATETIME DEFAULT NULL COMMENT '创建时间',
  `last_modified_by` VARCHAR(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改人',
  `last_modified_at` DATETIME DEFAULT NULL COMMENT '修改时间',
  `version` BIGINT(20) UNSIGNED DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`),
  KEY `idx_code_sn` (`code`,`sort_no`),
  KEY `idx_name` (`name`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='数据字典';