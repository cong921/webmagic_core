/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50527
Source Host           : localhost:3306
Source Database       : spider

Target Server Type    : MYSQL
Target Server Version : 50527
File Encoding         : 65001

Date: 2017-04-28 22:28:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for taxes
-- ----------------------------
DROP TABLE IF EXISTS `taxes`;
CREATE TABLE `taxes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company` varchar(500) DEFAULT NULL COMMENT '纳税人名称',
  `type` varchar(500) DEFAULT NULL COMMENT '纳税人类型',
  `num` varchar(100) DEFAULT NULL COMMENT '纳税人识别号',
  `name` varchar(100) DEFAULT NULL COMMENT '法定代表人或负责人姓名（业主姓名）',
  `idtype` varchar(100) DEFAULT NULL COMMENT '证件名称',
  `id_num` varchar(100) DEFAULT NULL COMMENT '居民身份证或其他有效证件号码',
  `address` varchar(100) DEFAULT NULL COMMENT '经营地址',
  `tax` varchar(100) DEFAULT NULL COMMENT '欠缴税种',
  `amount` varchar(100) DEFAULT NULL COMMENT '以前年度陈欠余额',
  `camount` varchar(50) DEFAULT NULL COMMENT '本期新欠金额',
  `debt` varchar(50) DEFAULT NULL COMMENT '总欠税额',
  `detail` longtext COMMENT '欠税详情',
  `time` varchar(50) DEFAULT NULL COMMENT '公告时间',
  `period` varchar(200) DEFAULT NULL COMMENT '公告期次',
  `source` varchar(200) DEFAULT NULL COMMENT '所属分局',
  `label` varchar(50) DEFAULT NULL COMMENT 'shanghaidisui',
  `hash_md5` char(32) DEFAULT NULL COMMENT 'hash位',
  `updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=367406 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='税务合并表（上次数据条数记录：)北京，青岛';
