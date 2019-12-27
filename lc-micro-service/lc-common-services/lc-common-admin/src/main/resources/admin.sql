/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.17-log : Database - lc_admin
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`lc_admin` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `lc_admin`;

/*Table structure for table `sys_function` */

DROP TABLE IF EXISTS `sys_function`;

CREATE TABLE `sys_function` (
  `id` bigint(20) unsigned NOT NULL COMMENT '编号',
  `menu_id` bigint(20) unsigned DEFAULT NULL COMMENT '菜单编号',
  `menu_name` varchar(64) DEFAULT NULL COMMENT '菜单名称',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `sort_no` int(10) unsigned DEFAULT '0' COMMENT '序号',
  `method` varchar(32) DEFAULT NULL COMMENT '方法',
  `url` varchar(255) DEFAULT NULL COMMENT '地址',
  `desc` varchar(255) DEFAULT NULL COMMENT '描述',
  `is_offline` tinyint(3) unsigned DEFAULT '0' COMMENT '是否下线 0：否 1：是',
  `is_regex` tinyint(3) DEFAULT NULL COMMENT '是否授权 0：否 1：是',
  `is_authenticated` tinyint(3) DEFAULT NULL COMMENT '是否授权 0：否 1：是',
  `is_deleted` tinyint(3) unsigned DEFAULT '0' COMMENT '是否删除 0：否 1：是',
  `ext` json DEFAULT NULL COMMENT '拓展字段',
  `created_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `last_modified_by` varchar(32) DEFAULT NULL COMMENT '修改人',
  `last_modified_at` datetime DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) unsigned DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`),
  KEY `idx_mi` (`menu_id`),
  KEY `idx_sn` (`sort_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统功能';

/*Data for the table `sys_function` */

insert  into `sys_function`(`id`,`menu_id`,`menu_name`,`name`,`sort_no`,`method`,`url`,`desc`,`is_offline`,`is_regex`,`is_authenticated`,`is_deleted`,`ext`,`created_by`,`created_at`,`last_modified_by`,`last_modified_at`,`version`) values (1,1120934674471796738,'角色管理','搜索',0,'GET','/roles','',0,0,0,0,NULL,'admin','2019-04-27 16:48:23','wuxuan','2019-06-13 15:02:25',2),(2,1120934674471796738,'角色管理','查询权限',0,'GET','/roles/menus/tree/\\d+',NULL,0,1,0,0,NULL,'admin','2019-04-27 16:48:23','admin','2019-04-27 16:48:23',1),(4,1120934674471796738,'角色管理','保存权限',0,'POST','/roles/functions/\\d+',NULL,0,1,0,0,NULL,'admin','2019-04-27 16:48:23','admin','2019-04-27 16:48:23',1),(5,1120936271964749826,'资源管理','搜索',0,'GET','/functions',NULL,0,0,0,0,NULL,'admin','2019-04-27 16:48:23','admin','2019-04-27 16:48:23',1),(6,1120936271964749826,'资源管理','单条查询',0,'GET','/functions/\\d+',NULL,0,1,0,0,NULL,'admin','2019-04-27 16:48:23','admin','2019-04-27 16:48:23',1),(7,1120936271964749826,'资源管理','新增',0,'POST','/functions/',NULL,0,0,0,0,NULL,'admin','2019-04-27 16:48:23','admin','2019-04-27 16:48:23',1),(8,1120936271964749826,'资源管理','删除',0,'DELETE','/functions/\\d+',NULL,0,1,0,0,NULL,'admin','2019-04-27 16:48:23','admin','2019-04-27 16:48:23',1),(9,1120936271964749826,'资源管理','修改',0,'PUT','/functions/\\d+',NULL,0,1,0,0,NULL,'admin','2019-04-27 16:48:23','admin','2019-04-27 16:48:23',1),(10,1120934674471796722,'用户管理','搜索',0,'GET','/users',NULL,0,0,0,0,NULL,'admin','2019-04-27 16:48:23','admin','2019-04-27 16:48:23',1),(11,1120934674471796722,'用户管理','单条查询',0,'GET','/users/\\d+',NULL,0,1,0,0,NULL,'admin','2019-04-27 16:48:23','admin','2019-04-27 16:48:23',1),(12,1120934674471796722,'用户管理','新增',0,'POST','/users/register',NULL,0,0,0,0,NULL,'admin','2019-04-27 16:48:23','admin','2019-04-27 16:48:23',1),(13,1120934674471796722,'用户管理','删除',0,'DELETE','/users/\\d+',NULL,0,1,0,0,NULL,'admin','2019-04-27 16:48:23','admin','2019-04-27 16:48:23',1),(14,1120934674471796722,'用户管理','修改',0,'PUT','/users/password/update',NULL,0,0,0,0,NULL,'admin','2019-04-27 16:48:23','admin','2019-04-27 16:48:23',1),(16,1120935867457683457,'菜单管理','单条查询',0,'GET','/menus/\\d+',NULL,0,1,0,0,NULL,'admin','2019-04-27 16:48:23','admin','2019-04-27 16:48:23',1),(17,1120935867457683457,'菜单管理','新增',0,'POST','/menus',NULL,0,0,0,0,NULL,'admin','2019-04-27 16:48:23','admin','2019-04-27 16:48:23',1),(18,1120935867457683457,'菜单管理','删除',0,'DELETE','/menus/\\d+',NULL,0,1,0,0,NULL,'admin','2019-04-27 16:48:23','admin','2019-04-27 16:48:23',1),(19,1120935867457683457,'菜单管理','修改',0,'PUT','/menus/\\d+',NULL,0,1,0,0,NULL,'admin','2019-04-27 16:48:23','admin','2019-04-27 16:48:23',1),(20,1120934674471796738,'角色管理','新增',0,'POST','/roles',NULL,0,0,0,0,NULL,'admin','2019-04-27 16:48:23','admin','2019-04-27 16:48:23',1),(21,1120934674471796738,'角色管理','修改',0,'PUT','/roles/\\d+',NULL,0,1,0,0,NULL,'admin','2019-04-27 16:48:23','admin','2019-04-27 16:48:23',1),(22,1120934674471796738,'角色管理','删除',0,'DELETE','/roles/\\d+',NULL,0,1,0,0,NULL,'admin','2019-04-27 16:48:23','admin','2019-04-27 16:48:23',1),(1122042229315051521,1120934674471796738,'角色管理','单条查询',1,'GET','/roles/\\d+','',0,1,0,0,NULL,'admin','2019-04-27 16:48:23','admin','2019-04-27 16:48:23',1),(1122052874693484545,1120935867457683457,'菜单管理','搜索',1,'GET','/menus','',0,0,0,0,NULL,'admin','2019-04-27 16:48:23','admin','2019-04-27 16:48:23',1),(1122386680197644293,1120934674471796722,'用户管理','配置角色',1,'POST','/users/roles/\\d+','',0,1,0,0,NULL,'admin','2019-04-27 16:48:23','admin','2019-04-27 16:48:23',1),(1122386680197644294,1120934674471796722,'用户管理','查询用户角色',1,'GET','/users/roles/\\d+','',0,1,0,0,NULL,'admin','2019-04-27 16:48:23','admin','2019-04-27 16:48:23',1);

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `id` bigint(20) unsigned NOT NULL COMMENT '编号',
  `parent_id` bigint(20) unsigned DEFAULT '0' COMMENT '父编号',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `level` int(10) unsigned DEFAULT '0' COMMENT '级别',
  `sort_no` int(10) unsigned DEFAULT '0' COMMENT '序号',
  `component` varchar(128) DEFAULT NULL COMMENT '组件',
  `menu_page` varchar(255) DEFAULT NULL COMMENT '页面地址',
  `path` varchar(255) DEFAULT NULL COMMENT '访问路径',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `redirect` varchar(255) DEFAULT NULL COMMENT '重定向路径',
  `desc` varchar(255) DEFAULT NULL COMMENT '描述',
  `is_hidden` tinyint(3) unsigned DEFAULT '0' COMMENT '隐藏类型 0：菜单树和角色配置都不过滤 1：菜单树不过滤，角色配置过滤 2:菜单树过滤，角色配置不过滤',
  `is_offline` tinyint(3) unsigned DEFAULT '0' COMMENT '是否下线 0：否 1：是',
  `is_deleted` tinyint(3) unsigned DEFAULT '0' COMMENT '是否删除 0：否 1：是',
  `ext` json DEFAULT NULL COMMENT '拓展字段',
  `created_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `last_modified_by` varchar(32) DEFAULT NULL COMMENT '修改人',
  `last_modified_at` datetime DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) unsigned DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`),
  KEY `idx_pi` (`parent_id`),
  KEY `idx_sn` (`sort_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统菜单';

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`id`,`parent_id`,`name`,`level`,`sort_no`,`component`,`menu_page`,`path`,`icon`,`redirect`,`desc`,`is_hidden`,`is_offline`,`is_deleted`,`ext`,`created_by`,`created_at`,`last_modified_by`,`last_modified_at`,`version`) values (1120883299692003330,0,'系统管理',1,1,'layout','layout','/system','el-icon-s-tools@element','','',0,0,0,NULL,'admin','2019-04-24 10:52:58','admin','2019-08-31 18:34:23',0),(1120934228369817602,1120883299692003330,'新增用户',2,2,'views/system/users/edit','views/system/users/edit','users/edit','clipboard@svg','','',1,0,0,NULL,'admin','2019-04-24 14:15:21','yanghuiyong','2019-10-15 11:37:05',1),(1120934674471796722,1120883299692003330,'用户管理',2,1,'views/system/users/list','views/system/users/list','users','el-icon-user-solid@element','','',0,0,0,NULL,'admin','2019-04-24 14:17:07','admin','2019-06-13 20:39:59',0),(1120934674471796738,1120883299692003330,'角色管理',2,3,'views/system/roles/list','views/system/roles/list','roles','peoples@svg','','',0,0,0,NULL,'admin','2019-04-24 14:17:07','admin','2019-06-13 20:52:31',0),(1120935022024409090,1120883299692003330,'新增角色',2,3,'views/system/roles/edit','views/system/roles/edit','roles/edit','el-icon-circle-plus@element','','',1,0,0,NULL,'admin','2019-04-24 14:18:30','admin','2019-06-12 18:35:35',0),(1120935381480456194,1120883299692003330,'配置权限',2,4,'views/system/roles/perm','views/system/roles/perm','roles/perm','el-icon-circle-plus@element','','',1,0,0,NULL,'admin','2019-04-24 14:19:56','admin','2019-04-24 14:19:56',0),(1120935867457683457,1120883299692003330,'菜单管理',2,6,'views/system/menus/list','views/system/menus/list','menus','el-icon-menu@element','','',0,0,0,NULL,'admin','2019-04-24 14:21:51','admin','2019-06-13 20:53:11',0),(1120936008558264321,1120883299692003330,'新增菜单',2,7,'views/system/menus/edit','views/system/menus/edit','menus/edit','el-icon-circle-plus@element','','',1,0,0,NULL,'admin','2019-04-24 14:22:25','admin','2019-04-24 14:22:25',0),(1120936271964749826,1120883299692003330,'资源管理',2,8,'views/system/functions/list','views/system/functions/list','functions','el-icon-s-data@element','','',0,0,0,NULL,'admin','2019-04-24 14:23:28','admin','2019-06-12 18:35:49',0),(1120936428231933954,1120883299692003330,'新增资源',2,9,'views/system/functions/edit','views/system/functions/edit','functions/edit','el-icon-circle-plus@element','','',1,0,0,NULL,'admin','2019-04-24 14:24:05','admin','2019-04-24 14:24:05',0),(1123158514685956141,1120883299692003330,'字典管理',2,9,'views/system/dict/list','views/system/dict/list','dict','dashboard@svg','','',0,0,0,NULL,'test','2019-04-24 14:21:51','yanghuiyong','2019-10-15 11:40:14',2),(1123158514685956142,1120883299692003330,'新增字典',2,10,'views/system/dict/edit','views/system/dict/edit','dict/edit','table','','',1,0,0,NULL,'test','2019-04-24 14:22:25','test','2019-04-24 14:22:25',0);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `desc` varchar(255) DEFAULT NULL COMMENT '描述',
  `is_disabled` tinyint(3) unsigned DEFAULT '0' COMMENT '是否禁用 0：否 1：是',
  `is_deleted` tinyint(3) unsigned DEFAULT '0' COMMENT '是否删除 0：否 1：是',
  `ext` json DEFAULT NULL COMMENT '拓展字段',
  `created_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `created_at` datetime DEFAULT NULL COMMENT '创建日期',
  `last_modified_by` varchar(32) DEFAULT NULL COMMENT '修改人',
  `last_modified_at` datetime DEFAULT NULL COMMENT '修改日期',
  `version` bigint(20) unsigned DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`),
  KEY `idx_name` (`name`),
  KEY `idx_ca` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色';

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`name`,`desc`,`is_disabled`,`is_deleted`,`ext`,`created_by`,`created_at`,`last_modified_by`,`last_modified_at`,`version`) values (1120973680538378242,'admin','系统管理员',0,0,NULL,'admin','2019-04-24 16:52:07','admin','2019-06-05 11:42:36',1),(1167789619585990658,'channel','推广渠道角色',0,0,NULL,'yanghuiyong','2019-08-31 21:21:56','yanghuiyong','2019-08-31 21:21:56',0),(1169438971479916546,'商务用户','商务用户角色',0,0,NULL,'yanghuiyong','2019-09-05 10:35:53','yanghuiyong','2019-09-05 10:35:53',0),(1171731300027068417,'运营','运营',0,0,NULL,'yanghuiyong','2019-09-11 18:24:46','yanghuiyong','2019-09-11 18:24:46',0);

/*Table structure for table `sys_role_function` */

DROP TABLE IF EXISTS `sys_role_function`;

CREATE TABLE `sys_role_function` (
  `id` bigint(20) NOT NULL,
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色编号',
  `function_id` bigint(20) unsigned NOT NULL COMMENT '功能编号',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色功能';

/*Data for the table `sys_role_function` */

insert  into `sys_role_function`(`id`,`role_id`,`function_id`,`created_at`) values (1142000017453625346,1141999888256479233,6,'2019-06-21 17:23:16'),(1142000017541705729,1141999888256479233,7,'2019-06-21 17:23:16'),(1142000017600425986,1141999888256479233,8,'2019-06-21 17:23:16'),(1142000017621397506,1141999888256479233,5,'2019-06-21 17:23:16'),(1142000017663340545,1141999888256479233,9,'2019-06-21 17:23:16'),(1207968294461583361,1120973680538378242,6,'2019-12-20 18:17:39'),(1207968295275278338,1120973680538378242,1122042229315051521,'2019-12-20 18:17:39'),(1207968295954755586,1120973680538378242,16,'2019-12-20 18:17:39'),(1207968296709730306,1120973680538378242,7,'2019-12-20 18:17:40'),(1207968296747479041,1120973680538378242,1122386680197644293,'2019-12-20 18:17:40'),(1207968297586339842,1120973680538378242,1122052874693484545,'2019-12-20 18:17:40'),(1207968297833803778,1120973680538378242,17,'2019-12-20 18:17:40'),(1207968297867358209,1120973680538378242,1122386680197644294,'2019-12-20 18:17:40'),(1207968298119016449,1120973680538378242,8,'2019-12-20 18:17:40'),(1207968299012403202,1120973680538378242,1,'2019-12-20 18:17:40'),(1207968299217924098,1120973680538378242,18,'2019-12-20 18:17:40'),(1207968299914178562,1120973680538378242,9,'2019-12-20 18:17:40'),(1207968300241334273,1120973680538378242,11,'2019-12-20 18:17:40'),(1207968300572684289,1120973680538378242,2,'2019-12-20 18:17:40'),(1207968301445099522,1120973680538378242,19,'2019-12-20 18:17:41'),(1207968301776449538,1120973680538378242,10,'2019-12-20 18:17:41'),(1207968302250405890,1120973680538378242,12,'2019-12-20 18:17:41'),(1207968303814881282,1120973680538378242,20,'2019-12-20 18:17:41'),(1207968304196562945,1120973680538378242,13,'2019-12-20 18:17:41'),(1207968305022840834,1120973680538378242,4,'2019-12-20 18:17:42'),(1207968305278693377,1120973680538378242,21,'2019-12-20 18:17:42'),(1207968306318880769,1120973680538378242,14,'2019-12-20 18:17:42'),(1207968306646036482,1120973680538378242,5,'2019-12-20 18:17:42'),(1207968307421982722,1120973680538378242,22,'2019-12-20 18:17:42');

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL,
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色编号',
  `menu_id` bigint(20) unsigned NOT NULL COMMENT '菜单编号',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色菜单';

/*Data for the table `sys_role_menu` */

insert  into `sys_role_menu`(`id`,`role_id`,`menu_id`,`created_at`) values (1142000017726255105,1141999888256479233,1120883299692003330,'2019-06-21 17:23:16'),(1142000017768198145,1141999888256479233,1120936271964749826,'2019-06-21 17:23:16'),(1207968308160180226,1120973680538378242,1120934674471796738,'2019-12-20 18:17:42'),(1207968308852240386,1120973680538378242,1120935867457683457,'2019-12-20 18:17:42'),(1207968309439442945,1120973680538378242,1120883299692003330,'2019-12-20 18:17:43'),(1207968309896622082,1120973680538378242,1120936271964749826,'2019-12-20 18:17:43'),(1207968310576099329,1120973680538378242,1123158514685956141,'2019-12-20 18:17:43'),(1207968310722899970,1120973680538378242,1120934674471796722,'2019-12-20 18:17:43');

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键',
  `type` varchar(255) DEFAULT NULL COMMENT '用户类型',
  `username` varchar(32) DEFAULT NULL COMMENT '用户名',
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(512) DEFAULT NULL COMMENT '电子邮箱',
  `password` varchar(1024) DEFAULT NULL COMMENT '密码',
  `salt` varchar(32) DEFAULT NULL COMMENT '盐',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `busi_user_name` varchar(128) DEFAULT NULL COMMENT '商务用户名',
  `busi_name` varchar(128) DEFAULT NULL COMMENT '商务姓名',
  `push_link` varchar(1024) DEFAULT NULL COMMENT '推广链接',
  `gender` varchar(1) DEFAULT 'N' COMMENT '性别 N：未知 M：男性 F：女性',
  `birthday` date DEFAULT '1970-01-01' COMMENT '生日',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `country` varchar(32) DEFAULT NULL COMMENT '国家',
  `province` varchar(32) DEFAULT NULL COMMENT '省份',
  `city` varchar(32) DEFAULT NULL COMMENT '城市',
  `district` varchar(32) DEFAULT NULL COMMENT '区县',
  `address` varchar(1024) DEFAULT NULL COMMENT '详细地址',
  `is_disabled` tinyint(3) unsigned DEFAULT '0' COMMENT '是否禁用 0：否 1：是',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `ext` json DEFAULT NULL COMMENT '拓展字段',
  `created_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `last_modified_by` varchar(32) DEFAULT NULL COMMENT '修改人',
  `last_modified_at` datetime DEFAULT NULL COMMENT '修改时间',
  `version` bigint(20) unsigned DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_mobile` (`mobile`),
  UNIQUE KEY `uk_email` (`email`),
  KEY `idx_ca` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`type`,`username`,`mobile`,`email`,`password`,`salt`,`name`,`busi_user_name`,`busi_name`,`push_link`,`gender`,`birthday`,`avatar`,`country`,`province`,`city`,`district`,`address`,`is_disabled`,`remark`,`ext`,`created_by`,`created_at`,`last_modified_by`,`last_modified_at`,`version`) values (1120958492359540737,'platform','yanghy',NULL,NULL,'$2a$12$S/O.m5t71G3wxOthK0UIhO0.W3qpf0VJ.6tlyo3e3KhDmxbtE4X0i','8jsmoqcn','杨辉勇',NULL,NULL,NULL,'N',NULL,'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'admin','2019-04-24 15:51:46','yanghuiyong','2019-09-24 16:45:03',82),(1129001479744401410,'platform','yanghuiyong',NULL,NULL,'$2a$12$LNFNI8YNDiQQmXQ2hrImjuffIvjISXQ5nDlkyHr6xvb5L0PtgzDIS','0mxjkvpy','杨辉勇',NULL,NULL,NULL,NULL,NULL,'',NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'admin','2019-05-16 20:31:43','yanghuiyong','2019-09-06 09:57:17',10),(1129203701350612994,'platform','zhaoqinfeng',NULL,NULL,'$2a$12$LNFNI8YNDiQQmXQ2hrImjuffIvjISXQ5nDlkyHr6xvb5L0PtgzDIS','10n8w6ee',NULL,NULL,NULL,NULL,NULL,NULL,'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'admin','2019-05-17 09:55:17','xieze','2019-09-06 11:32:26',4),(1130284156065898498,'platform','wangxd',NULL,NULL,'$2a$12$ZlwpRKcgq06ymXd70kMbQO5DLH6j1voNcC/07Zzlk36iQ0m55sd3e','th4jmkld','王晓东',NULL,NULL,NULL,NULL,NULL,'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'admin','2019-05-20 09:28:37','wangxd','2019-09-29 15:32:01',1),(1130729484619587586,'platform','likaiou',NULL,NULL,'$2a$12$LNFNI8YNDiQQmXQ2hrImjuffIvjISXQ5nDlkyHr6xvb5L0PtgzDIS','vlctucjn',NULL,NULL,NULL,NULL,NULL,NULL,'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'admin','2019-05-21 14:58:12','admin','2019-05-21 14:58:12',0),(1131073105348915201,'platform','chensz',NULL,NULL,'$2a$12$aa.9VO5y5WwzvHHEO7R5HOlaKIaD68bh19IVJQafOAEHeX3CFUOwS','8zqr5o72','陈少忠',NULL,NULL,NULL,NULL,NULL,'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'admin','2019-05-22 13:43:37','wangxd','2019-09-17 10:40:17',3),(1131465477291216898,'platform','tangdc',NULL,NULL,'$2a$12$LNFNI8YNDiQQmXQ2hrImjuffIvjISXQ5nDlkyHr6xvb5L0PtgzDIS','4k0j0hpf',NULL,NULL,NULL,NULL,NULL,NULL,'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'admin','2019-05-23 15:42:46','admin','2019-05-23 15:42:46',0),(1132989986544558081,'platform','wuxuan',NULL,NULL,'$2a$12$LNFNI8YNDiQQmXQ2hrImjuffIvjISXQ5nDlkyHr6xvb5L0PtgzDIS','3ht1h4gv',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'admin','2019-05-27 20:40:37','admin','2019-05-29 15:49:56',1),(1133314118796713985,'platform','admin',NULL,NULL,'$2a$12$LNFNI8YNDiQQmXQ2hrImjuffIvjISXQ5nDlkyHr6xvb5L0PtgzDIS','5t17gl8c',NULL,NULL,NULL,NULL,NULL,NULL,'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif',NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'admin','2019-05-28 18:08:37','admin','2019-06-05 09:25:39',2),(1133658881597136898,'platform','shengwl',NULL,NULL,'$2a$12$wZ/aJUNExwzQ0SqVrLSHcuwFV7y9i5lAohYPAv/nsWLbWVuiqWAby','0uc8xc9f',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'admin','2019-05-29 16:58:34','admin','2019-05-29 17:19:19',1),(1133672861271048194,'platform','yuanxl',NULL,NULL,'$2a$12$eT/4fqszram275l/WIt3.O.27itCGSn2NLYQBTq7MZpTnh1GlGp3W',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'admin','2019-05-29 17:54:07','admin','2019-05-29 17:54:07',0),(1136089386045112322,'platform','daijianshi',NULL,NULL,'$2a$12$LNFNI8YNDiQQmXQ2hrImjuffIvjISXQ5nDlkyHr6xvb5L0PtgzDIS',NULL,NULL,NULL,NULL,NULL,'N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'admin','2019-06-05 09:56:32','admin','2019-06-05 09:56:49',1),(1149245549435785217,'platform','zhangkuan',NULL,NULL,'$2a$12$yA3dPbHFsjg3ta/Y/UBeQeZhMIxwPZbT2gtcqE.cGDDDwozgmB8me',NULL,NULL,NULL,NULL,NULL,'N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'yanghuiyong','2019-07-11 17:14:26','yanghuiyong','2019-07-11 17:14:33',1),(1154278629661859841,'platform','liucheng',NULL,NULL,'$2a$12$vCNhnnzGxKAZ6R5sv1YWqOx.7W2TFxPYUGYGckS6Vur6piz8RQs4S',NULL,NULL,NULL,NULL,NULL,'N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'wuxuan','2019-07-25 14:34:05','wuxuan','2019-07-25 14:36:54',1),(1168502691489271810,'busi','busi123',NULL,NULL,'$2a$12$EP1JaSSew5o52Daez7WlnuzWXFLZgPIcHBjueUJSLHu0Xlde5yULK',NULL,'测试商务',NULL,NULL,NULL,'N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'yanghuiyong','2019-09-02 20:35:26','yanghuiyong','2019-09-02 20:35:26',0),(1169431340672692225,'channel','WD',NULL,NULL,'$2a$12$A6HiyQb5o/80jxUs2Cf6Z.1hBKVvDpooBeh6BrnkC.0NxFXdr8ije',NULL,'WD渠道测试','shangwuA','商务A','https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=WD&SecChannel=s001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'qiulongjie','2019-09-05 10:05:34','shangwuA','2019-09-06 16:33:19',3),(1169543404319838210,'platform','xieze',NULL,NULL,'$2a$12$lpVBK2yV.3f8diDO7/RuZOOMa3nhF.uusv86ddE1CjC/7OSKP3a3W',NULL,'谢正恩',NULL,NULL,NULL,'N','1970-01-01','https://s2.ax1x.com/2019/12/18/QHQo1H.gif',NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'admin','2019-09-05 17:30:51','admin','2019-09-05 17:30:51',0),(1169546453820989441,'platform','daijs',NULL,NULL,'$2a$12$LNFNI8YNDiQQmXQ2hrImjuffIvjISXQ5nDlkyHr6xvb5L0PtgzDIS',NULL,NULL,NULL,NULL,NULL,'N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'admin','2019-09-05 17:42:58','admin','2019-09-05 17:42:58',0),(1169788113498624001,'busi','busi2',NULL,NULL,'$2a$12$OPjf96EJyNp7T0WqiEV6JeSkCbp/CgdDLvCB4C6JPL4fccjNgZY6u',NULL,NULL,NULL,NULL,NULL,'N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'yanghuiyong','2019-09-06 09:43:15','yanghuiyong','2019-09-06 09:43:15',0),(1169790426539823105,'busi','busi4',NULL,NULL,'$2a$12$tInzMvn3MUBkKuAYcbwIweN/nAJU3SydRQd/GcZfovpfUeM7/vF4K',NULL,'商务2',NULL,NULL,NULL,'N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'yanghuiyong','2019-09-06 09:52:26','yanghuiyong','2019-09-06 09:52:26',0),(1169790650964447233,'busi','busi3',NULL,NULL,'$2a$12$CrlU9pfxAlNS4ff2PI80Kuv5T0P73W0SZiSxmKxGVQGppqct2wr7u',NULL,'商务3',NULL,NULL,NULL,'N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'yanghuiyong','2019-09-06 09:53:20','yanghuiyong','2019-09-06 09:53:20',0),(1169804325116952578,'channel','test',NULL,NULL,'$2a$12$a7qSnHFeINM99FjR0XWMeO3E1ECVnqYWvR67IHwOOcRcclmShety6',NULL,'测试渠道','busi123','测试商务','https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=test&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'yanghuiyong','2019-09-06 10:47:40','yanghuiyong','2019-09-29 20:36:24',6),(1169804403193921538,'channel','test2',NULL,NULL,'$2a$12$c2DupMsLRfUVomXlfmHsHOAYrYTrx1NSTc1d4ASkWwIeQecoFNJri',NULL,'测试渠道二','busi123','测试商务','https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=test2&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'yanghuiyong','2019-09-06 10:47:58','yanghuiyong','2019-09-06 10:47:58',0),(1169819622246858753,'channel','test3',NULL,NULL,'$2a$12$s2IGoplb4IIONQmYlnTDm.8eq/gYjb5jH4JKFEZwtEOKy.SxmY2w6',NULL,'测试三','busi3','商务3','https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=test3&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'yanghuiyong','2019-09-06 11:48:27','yanghuiyong','2019-09-06 11:48:35',1),(1170889656443641857,'channel','shanji',NULL,NULL,'$2a$12$y.Q2qpadxKn5.CPEJgtvj.Sjotpd2ytgnr9UFH7RxKb2Nvg6wRRHG',NULL,'闪集','wangsik','黄思凯','https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=shanji&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'zhouzhou','2019-09-09 10:40:23','zhouzhou','2019-09-09 10:40:23',0),(1170890133739601921,'channel','11',NULL,NULL,'$2a$12$Fo3EItU0obhty/.Iq8/JkONkM6HnSdnHtn.YVDqJCyeUIgSURrq0m',NULL,'111','busi123','测试商务','https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=11&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'wangxd','2019-09-09 10:42:17','yanghuiyong','2019-09-29 20:41:36',1),(1170892681560236033,'channel','yunshi',NULL,NULL,'$2a$12$E/1NFO2sO5y6O57OTXdzN.PSHWaU.47jTUqO6hdtmopiwH2HjeWIG',NULL,'云视科技','chenchaos','陈朝顺','https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=yunshi&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'zhouzhou','2019-09-09 10:52:24','zhouzhou','2019-09-09 10:52:24',0),(1170893944100265986,'channel','yijialing',NULL,NULL,'$2a$12$T1yD/aiUqgTb63GZI7pRjO7L7pdlIP/cadWsDqrq8tJiW7wKmx9C6',NULL,'壹加零','liangjq','梁骏祺','https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=yijialing&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'zhouzhou','2019-09-09 10:57:25','zhouzhou','2019-09-09 11:44:34',1),(1170894413514186754,'channel','huitui',NULL,NULL,'$2a$12$aEl.uOB23MzqUepW6rPdsuPZmmO/uI.kUZmHUBK0eonjbt.cPkMay',NULL,'汇推网络','liangjq','梁骏祺','https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=huitui&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'zhouzhou','2019-09-09 10:59:17','zhouzhou','2019-09-09 11:44:30',1),(1170894758562799618,'channel','1991',NULL,NULL,'$2a$12$WpFYOKGfDiThJFOtZCOjvOfsjGngbJbTLODZohsrB88Qc1qTgf6vm',NULL,'一九九一','liangjq','梁骏祺','https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=1991&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'zhouzhou','2019-09-09 11:00:39','zhouzhou','2019-09-09 11:44:25',1),(1170897640691376129,'channel','meida',NULL,NULL,'$2a$12$Iux1vHY0rgvlgqumLo4Un.DpjDc4vWL5OlGLx1yvzVBUjx65yWkZO',NULL,'浙江美达国际旅行社有限公司','chengb','陈耿彬','https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=meida&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'zhouzhou','2019-09-09 11:12:06','zhouzhou','2019-09-09 11:12:06',0),(1170910901847498753,'channel','365',NULL,NULL,'$2a$12$y7Ru448mhvCXyxDaHB2iQea4KRVjdgjrDWZ0pFPhzILPwpJMYNkHq',NULL,'易365','hehongkangjie','何弦康杰','https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=365&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'zhouzhou','2019-09-09 12:04:48','zhouzhou','2019-09-09 12:04:53',1),(1171259662666866689,'channel','youdianbige',NULL,NULL,'$2a$12$3VTUfzmiFGe6CUB5evZIReYBtDDAmFOuztyuU1mmng48Vu4pfKEF.',NULL,'优点比格','liangjq','梁骏祺','https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=youdianbige&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'zhouzhou','2019-09-10 11:10:39','zhouzhou','2019-09-10 11:10:39',0),(1171261670007255042,'platform','lujinjian',NULL,NULL,'$2a$12$nNgNj8RkxcM3aqMZVHwbOOut13jOoH9s848O22OvLB.Yio9TCNh/S',NULL,'鲁津健',NULL,NULL,NULL,'N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'yanghuiyong','2019-09-10 11:18:38','daijs','2019-09-11 19:55:05',1),(1171316482030739457,'channel','youke',NULL,NULL,'$2a$12$yNBowsvqXbBhKoBpwRC/SOlmQizIKJht0/IrpJG8auNSPFtdroU1.',NULL,'安徽佑客','zhongrr','钟瑞荣','https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=youke&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'zhouzhou','2019-09-10 14:56:26','zhouzhou','2019-09-10 14:56:26',0),(1171317066016268289,'channel','majia',NULL,NULL,'$2a$12$d7HvkczyqKWVf5gAVzMVCepB2jWtlCF3oqp4uv3DxQgZZKdoT8Ok2',NULL,'马甲文化','zhongrr','钟瑞荣','https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=majia&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'zhouzhou','2019-09-10 14:58:45','zhouzhou','2019-09-10 14:58:45',0),(1171319625900662786,'channel','huituiJX',NULL,NULL,'$2a$12$BoSM3uiYi/DD.CljWOa2dOJjs7QwLgPdQmE1jhFhWh433sVxuHnzW',NULL,'江西汇推','zhongrr','钟瑞荣','https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=huituiJX&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'zhouzhou','2019-09-10 15:08:56','zhouzhou','2019-09-11 18:36:23',1),(1171320490879393793,'channel','wangsou',NULL,NULL,'$2a$12$bET0oQk2.JBdPtJQCd5Ceugyht69oPc//gXC659j9X76/PgHxst7O',NULL,'网搜科技','chenchaos','陈朝顺','https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=wangsou&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'zhouzhou','2019-09-10 15:12:22','zhouzhou','2019-09-10 15:12:22',0),(1171731854107209729,'platform','huangyx',NULL,NULL,'$2a$12$ntPylKY4bQfDcuwxCo2MdOWCU1oaHv185S5WC0jywRgjDfHraL/De',NULL,'黄远雄',NULL,NULL,NULL,'N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'xieze','2019-09-11 18:26:58','xieze','2019-09-11 18:26:58',0),(1171732078502473729,'platform','guanjj',NULL,NULL,'$2a$12$0XK4i8vWLF5Se/uk9LQZfuCAW6.uP5tn44I0l8txi0eqm5rVwy7SK',NULL,'关锦津',NULL,NULL,NULL,'N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'xieze','2019-09-11 18:27:52','xieze','2019-09-11 18:27:52',0),(1171732174744973314,'platform','panglh',NULL,NULL,'$2a$12$eOvIa7yCnbQABeigBq1OsePbki8Dr4o3NvJrByZeqhw30.9udlwYi',NULL,'庞丽华',NULL,NULL,NULL,'N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'xieze','2019-09-11 18:28:15','xieze','2019-09-11 18:28:15',0),(1171732356404473857,'platform','zhouying',NULL,NULL,'$2a$12$gqxTwAA.0EKYFFB6YYxG6Oh5ZWuK9wAuhSzlDtH6vWhsF8EQkgV5S',NULL,'周莹',NULL,NULL,NULL,'N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'xieze','2019-09-11 18:28:58','xieze','2019-09-11 18:28:58',0),(1171734077250732033,'channel','hanru',NULL,NULL,'$2a$12$/CPr7D7T7AQXisSJiU8jQev72m1B.7UIhvXhyHershJNDXYga0mce',NULL,'涵儒',NULL,NULL,'https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=hanru&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'zhouzhou','2019-09-11 18:35:48','zhouzhou','2019-09-11 18:35:48',0),(1171753931950329858,'platform','lujj',NULL,NULL,'$2a$12$8iRnLmYSzKWh/L3FpEQzneloP53AOwh46/6NTa5/uiYhrTjyhksEC',NULL,'ljj001',NULL,NULL,NULL,'N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'daijs','2019-09-11 19:54:42','daijs','2019-09-11 19:54:42',0),(1172049609401307137,'channel','cyt',NULL,NULL,'$2a$12$2kxsWYFpgkEDTjYyX857i.b3cwY/XGZIYz/cuUTuZ6/DQ1oW45Td6',NULL,'畅游通','guanjinj','关锦津','https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=cyt&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'linyaos','2019-09-12 15:29:37','linyaos','2019-09-12 15:38:04',2),(1173800880776945666,'channel','aidu',NULL,NULL,'$2a$12$bySzGiW9tyH5Rj86zaZMguV9uWKK.g3K6dg/D4eZZe2zUz6Vw7Fs2',NULL,'爱度旅游','wangsik','黄思凯','https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=aidu&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'linyaos','2019-09-17 11:28:33','linyaos','2019-09-17 11:28:33',0),(1173802003856367617,'channel','neihan',NULL,NULL,'$2a$12$DubYfB0uT2oJ16sZlHnAsOtdo6ZG8jGqfU6ifdYuqJ8WeAWhMmm5O',NULL,'内涵','zhongrr','钟瑞荣','https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=neihan&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'linyaos','2019-09-17 11:33:01','linyaos','2019-09-17 11:33:01',0),(1174168037834629122,'channel','jintian',NULL,NULL,'$2a$12$ooO0P2LW4J.rReijbHi4fex9jgGTiRsed9L953CxmLAFg/bjr06/i',NULL,'津甜集团','wangsik','黄思凯','https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=jintian&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'linyaos','2019-09-18 11:47:30','linyaos','2019-09-18 11:47:30',0),(1174207777883561985,'channel','ziqi',NULL,NULL,'$2a$12$lmk1uVjnUr.EtM8dSGczZ.bJ5vtWZio5I09J4xjN0btc.6/M8Zva6',NULL,'资旗',NULL,NULL,'https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=ziqi&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'linyaos','2019-09-18 14:25:25','linyaos','2019-09-18 14:25:25',0),(1174887436774297602,'channel','zhiming',NULL,NULL,'$2a$12$UxQbEbeqxULKkA8xFwNk3.iWDm4WmB3qBco7KqpOssA.jjlB1ZdX2',NULL,'智明通信','chengb','陈耿彬','https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=zhiming&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'linyaos','2019-09-20 11:26:08','linyaos','2019-09-20 11:26:08',0),(1174988857393827841,'channel','cs',NULL,NULL,'$2a$12$VlI3f4nnJuBYKHdBLBgk1.Z2c8Vjsy48K1JEv8ZLi371i1u0c3S/W',NULL,'测试',NULL,NULL,'https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=cs&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'linyaos','2019-09-20 18:09:08','linyaos','2019-09-20 18:09:08',0),(1175945919011508225,'channel','caijin',NULL,NULL,'$2a$12$G9oziNJ3MuTctSnv0zmfRu0q2NR0GBBiIOyABhoGC1b9X1tSNKU7y',NULL,'财今科技','chenchaos','陈朝顺','https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=caijin&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'linyaos','2019-09-23 09:32:10','linyaos','2019-09-23 09:32:10',0),(1176083024639447041,'channel','jiashihua',NULL,NULL,'$2a$12$DR/HLR3khsUeXWvCS1SFbu8Evj3B30RVyE/l8hkMai0GOv8v3XT8C',NULL,'加世华',NULL,NULL,'https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=jiashihua&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'linyaos','2019-09-23 18:36:58','linyaos','2019-09-23 18:36:58',0),(1177421251472293890,'channel','fcyl',NULL,NULL,'$2a$12$xS2LYXqUQitARGhjUjZu2O7v4CnxUtCNm9TsPgcXvOzSj3CakBP2G',NULL,'非常有量',NULL,NULL,'https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=fcyl&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'linyaos','2019-09-27 11:14:36','linyaos','2019-09-27 11:14:36',0),(1178210393769639937,'platform','zhuangyt',NULL,NULL,'$2a$12$U9n4EY7x7H7cRRtNs59dRuxV3S1Aso7ciFCRDGfv.v7EAks1RmZHu',NULL,'庄苑婷',NULL,NULL,NULL,'N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'wangxd','2019-09-29 15:30:23','wangxd','2019-09-29 15:30:23',0),(1178233467897413633,'channel','zhuoyian',NULL,NULL,'$2a$12$yS9D7I9PTxWREIKB5WqfAuzGMtB3ihN0u33wdtzpo25T0nCgMQx7m',NULL,'卓易安',NULL,NULL,'https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=zhuoyian&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'linyaos','2019-09-29 17:02:04','linyaos','2019-09-29 17:02:04',0),(1178287581059559425,'channel','aa',NULL,NULL,'$2a$12$lDXIGeSr.vs86BTXA0LFp..UuVK/UHgbRLpKJxopXmfVk09awpcu.',NULL,'测试渠道','busi123','测试商务','https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=aa&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'yanghuiyong','2019-09-29 20:37:06','yanghuiyong','2019-09-29 20:37:06',0),(1178288467005616129,'channel','test1',NULL,NULL,'$2a$12$LH7.dpntW3y3Dn8qO6vbEOnJYaWt51LRtOah8ylCkZQNa8XdeR2AS',NULL,'测试1','busi123','测试商务','https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=test1&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'yanghuiyong','2019-09-29 20:40:37','yanghuiyong','2019-09-29 20:40:37',0),(1178586499034132481,'channel','leyou',NULL,NULL,'$2a$12$UxGHIe32WEf/G6BH/398POrRgKD8QVWED6rE5EsJz5Q.cIh2TPFUy',NULL,'乐游','wangsik','黄思凯','https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=leyou&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'linyaos','2019-09-30 16:24:53','linyaos','2019-09-30 16:24:53',0),(1189141294313738241,'platform','tuowenkun',NULL,NULL,'$2a$12$7j/YZzUcnJ0pJM63Yb24we1iufKtUZSj3CrqXX5xa7qfeR53Aao.m',NULL,'庹文坤',NULL,NULL,NULL,'N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'lujinjian','2019-10-29 19:25:52','lujinjian','2019-10-29 19:25:52',0),(1198808465507692546,'platform','liangyong',NULL,NULL,'$2a$12$Z0lu/FLaCHre0s9NvFX9Ee4WbqG/SvFb87BMC1mufKrt/NjV3g1.e',NULL,'梁勇',NULL,NULL,NULL,'N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'xieze','2019-11-25 11:39:46','xieze','2019-11-25 11:39:46',0),(1199586514117353473,'platform','laicj',NULL,NULL,'$2a$12$H/.9YJzpWdrKQRVBMq1kn.8Cj4TWp9tXV3NcFHBH2wFzfoneYMdi2',NULL,'赖纯静',NULL,NULL,NULL,'N','1970-01-01','https://s2.ax1x.com/2019/12/18/QHQo1H.gif',NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'xieze','2019-11-27 15:11:27','wangxd','2019-12-20 10:52:39',4),(1201326073507307522,'platform','yugs',NULL,NULL,'$2a$12$j9GH2nj3M4qS8nt/P/EUjuuLaMTfLnLFZSyqcnpp3N/uLi1V4VwJi',NULL,'余国森',NULL,NULL,NULL,'N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'daijs','2019-12-02 10:23:50','daijs','2019-12-02 10:23:50',0),(1204945137806974977,'channel','fangkk',NULL,NULL,'$2a$12$aH26ccTHNVnX/3xxIRhtU.q6v//FZ7dVBHU0aCJLAoBlJKjWs1eme',NULL,'方可可','busi123','测试商务','https://app.eadaa.com/wt/toH5/huihe/promotion/#/?PanChannel=fangkk&SecChannel=sec001&t=1','N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'laicj','2019-12-12 10:04:42','laicj','2019-12-12 10:04:42',0),(1207868767180050433,'platform','liguol',NULL,NULL,'$2a$12$bppvvUWeWxm8iKiTwXgxielYxiekwywguPYNVBo1UsbdCR2smlmL.',NULL,'李国良',NULL,NULL,NULL,'N','1970-01-01',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,'wangxd','2019-12-20 11:42:10','wangxd','2019-12-20 11:42:10',0);

/*Table structure for table `sys_user_recycle_bin` */

DROP TABLE IF EXISTS `sys_user_recycle_bin`;

CREATE TABLE `sys_user_recycle_bin` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户编号',
  `username` varchar(32) DEFAULT NULL COMMENT '用户名',
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(512) DEFAULT NULL COMMENT '电子邮箱',
  `password` varchar(1024) DEFAULT NULL COMMENT '密码',
  `salt` varchar(32) DEFAULT NULL COMMENT '盐',
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `gender` varchar(1) DEFAULT 'N' COMMENT '性别 N：未知 M：男性 F：女性',
  `birthday` date DEFAULT '1970-01-01' COMMENT '生日',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `country` varchar(32) DEFAULT NULL COMMENT '国家',
  `province` varchar(32) DEFAULT NULL COMMENT '省份',
  `city` varchar(32) DEFAULT NULL COMMENT '城市',
  `area` varchar(32) DEFAULT NULL COMMENT '区县',
  `address` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `is_disabled` tinyint(3) unsigned DEFAULT '0' COMMENT '是否禁用 0：否 1：是',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `ext` json DEFAULT NULL COMMENT '拓展字段',
  `created_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `last_modified_by` varchar(32) DEFAULT NULL COMMENT '修改人',
  `last_modified_at` datetime DEFAULT NULL COMMENT '修改时间',
  `deleted_by` varchar(32) DEFAULT NULL COMMENT '删除人',
  `deleted_at` datetime DEFAULT NULL COMMENT '删除时间',
  `version` bigint(20) unsigned DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_id` (`user_id`),
  KEY `idx_username` (`username`),
  KEY `idx_mobile` (`mobile`),
  KEY `idx_email` (`email`),
  KEY `idx_ca` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户回收站';

/*Data for the table `sys_user_recycle_bin` */

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户编号',
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色编号',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户角色';

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`id`,`user_id`,`role_id`,`created_at`) values (4,1129001479744401410,1120973680538378242,'2019-05-16 20:31:53'),(6,1129203701350612994,1120973680538378242,'2019-05-17 09:55:38'),(10,1130285150082392065,1120973680538378242,'2019-05-20 09:33:05'),(12,1130285900485324801,1120973680538378242,'2019-05-20 09:35:50'),(14,1130295550530252802,1120973680538378242,'2019-05-20 10:14:04'),(16,1130729484619587586,1120973680538378242,'2019-05-21 14:58:22'),(18,1131013265859670018,1120973680538378242,'2019-05-22 09:46:14'),(20,1131073105348915201,1120973680538378242,'2019-05-22 13:43:50'),(24,1131736485378105345,1120973680538378242,'2019-05-24 09:39:47'),(26,1132989986544558081,1120973680538378242,'2019-05-27 20:41:36'),(28,1133314118796713985,1120973680538378242,'2019-05-28 18:08:58'),(29,1133562182790762497,1120973680538378242,'2019-05-29 10:35:22'),(30,1133641935375777793,1120973680538378242,'2019-05-29 15:51:34'),(31,1133658881597136898,1120973680538378242,'2019-05-29 16:58:51'),(32,1133672861271048194,1120973680538378242,'2019-05-29 17:54:14'),(1136089454240301057,1136089386045112322,1120973680538378242,'2019-06-05 09:56:48'),(1138013672481681410,1131465477291216898,1120973680538378242,'2019-06-10 17:22:57'),(1149245575008456706,1149245549435785217,1120973680538378242,'2019-07-11 17:14:32'),(1154278664441028610,1154278629661859841,1120973680538378242,'2019-07-25 14:34:14'),(1168509713500733441,1168509711063842817,1167789619585990658,'2019-09-02 21:03:20'),(1169231146325606401,1169231144220065793,1167789619585990658,'2019-09-04 20:50:03'),(1169235861503426562,1169235859070730242,1167789619585990658,'2019-09-04 21:08:47'),(1169439069001678850,1168502691489271810,1169438971479916546,'2019-09-05 10:36:16'),(1169497138612121602,1169497136166842370,1167789619585990658,'2019-09-05 14:27:01'),(1169507381437923329,1169507379248496642,1167789619585990658,'2019-09-05 15:07:43'),(1169543439363248129,1169543404319838210,1120973680538378242,'2019-09-05 17:31:00'),(1169546477233594370,1169546453820989441,1120973680538378242,'2019-09-05 17:43:04'),(1169801529487507458,1169801528925470721,1167789619585990658,'2019-09-06 10:36:33'),(1169804325846761474,1169804325116952578,1167789619585990658,'2019-09-06 10:47:40'),(1169804403651100674,1169804403193921538,1167789619585990658,'2019-09-06 10:47:58'),(1169819622901170178,1169819622246858753,1167789619585990658,'2019-09-06 11:48:27'),(1170888325130551297,1120958492359540737,1167789619585990658,'2019-09-09 10:35:05'),(1170890134301638658,1170890133739601921,1167789619585990658,'2019-09-09 10:42:17'),(1171732395235340290,1171731854107209729,1171731300027068417,'2019-09-11 18:29:07'),(1171732406568349698,1171732078502473729,1171731300027068417,'2019-09-11 18:29:10'),(1171732418824105985,1171732174744973314,1171731300027068417,'2019-09-11 18:29:13'),(1171732428609417218,1171732356404473857,1171731300027068417,'2019-09-11 18:29:15'),(1177404094026936321,1171261670007255042,1120973680538378242,'2019-09-27 10:06:26'),(1178210699052056577,1130284156065898498,1120973680538378242,'2019-09-29 15:31:35'),(1178210699052056578,1130284156065898498,1167789619585990658,'2019-09-29 15:31:35'),(1178287581667733506,1178287581059559425,1167789619585990658,'2019-09-29 20:37:06'),(1178288467391492097,1178288467005616129,1167789619585990658,'2019-09-29 20:40:37'),(1189141386047361026,1189141294313738241,1120973680538378242,'2019-10-29 19:26:14'),(1198808524945174530,1198808465507692546,1120973680538378242,'2019-11-25 11:40:00'),(1201326137453666306,1201326073507307522,1169438971479916546,'2019-12-02 10:24:05'),(1201326137491415042,1201326073507307522,1171731300027068417,'2019-12-02 10:24:05'),(1201326137512386561,1201326073507307522,1120973680538378242,'2019-12-02 10:24:05'),(1201326137550135297,1201326073507307522,1167789619585990658,'2019-12-02 10:24:05'),(1201326165073158146,1178210393769639937,1171731300027068417,'2019-12-02 10:24:12'),(1201326165102518273,1178210393769639937,1120973680538378242,'2019-12-02 10:24:12'),(1201326165123489794,1178210393769639937,1169438971479916546,'2019-12-02 10:24:12'),(1201326165157044226,1178210393769639937,1167789619585990658,'2019-12-02 10:24:12'),(1201756660147810306,1199586514117353473,1120973680538378242,'2019-12-03 14:54:50'),(1204945138352234497,1204945137806974977,1167789619585990658,'2019-12-12 10:04:42'),(1207870539659567105,1207868767180050433,1120973680538378242,'2019-12-20 11:49:12');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
