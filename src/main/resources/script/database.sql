CREATE DATABASE IF NOT EXISTS `tx_order` ;
USE `tx_order`;


DROP TABLE IF EXISTS `tx_order`;
CREATE TABLE `tx_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键，自增长，步长＝1',
  `serial_number` varchar(255) NOT NULL COMMENT '订单序列号'
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_serial_number` (`serial_number`),
  KEY `serial_number` (`serial_number`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=273904 DEFAULT CHARSET=utf8 COMMENT='订单主表'