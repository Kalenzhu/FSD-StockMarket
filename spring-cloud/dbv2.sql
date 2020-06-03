CREATE DATABASE IF NOT EXISTS fsd CHARACTER SET utf8 COLLATE utf8_general_ci;

use fsd;

DROP TABLE IF EXISTS `t_sector`;
CREATE TABLE `t_sector` (
  `id` bigint(20) NOT NULL auto_increment,
  `sector_name` varchar(256) NOT NULL,
  `brief` varchar(256),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `t_sector` VALUES (1, 'Finance', 'finace, bank and insurance');
INSERT INTO `t_sector` VALUES (2, 'Healthcare Services', 'hosipital, clinic and health center');
INSERT INTO `t_sector` VALUES (3, 'Pharmaceuticals', 'Medicine and bio-technology');
INSERT INTO `t_sector` VALUES (4, 'Hotels', 'hotel and apartment');
INSERT INTO `t_sector` VALUES (5, 'Internet Software & Services', 'Network and software company');

DROP TABLE IF EXISTS `t_company`;
CREATE TABLE `t_company` (
  `id` bigint(20) NOT NULL auto_increment,
  `company_name` varchar(256) NOT NULL,
  `turnover` float DEFAULT NULL,
  `ceo` varchar(256) DEFAULT NULL,
  `board_directors` varchar(256) DEFAULT NULL,
  `brief_write_up` varchar(256) DEFAULT NULL,
  `pic_url` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_company_sector`;
CREATE TABLE `t_company_sector` (
  `id` bigint(20) NOT NULL auto_increment,
  `company_id` bigint(20) NOT NULL,
  `sector_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_t_company_sector_company_id` FOREIGN KEY (`company_id`) REFERENCES `t_company`(`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_t_company_sector_sector_id` FOREIGN KEY (`sector_id`) REFERENCES `t_sector`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_stockexchange`;
CREATE TABLE `t_stockexchange` (
  `id` bigint(20) NOT NULL auto_increment,
  `stockexchange` varchar(256) NOT NULL,
  `brief` varchar(256),
  `contact_address` varchar(256),
  `remarks` varchar(256),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `t_stockexchange` VALUES (1, 'BSE', 'Bombay Stock Exchange', 'No 21 Bombay Center District', 'India TOP 1 stock exchage market');
INSERT INTO `t_stockexchange` VALUES (2, 'NSE', 'New York Stock Exchange', 'Mahattan District', 'Global TOP 1 stock exchage market');

DROP TABLE IF EXISTS `t_company_stockexchange`;
CREATE TABLE `t_company_stockexchange` (
  `id` bigint(20) NOT NULL auto_increment,
  `company_id` bigint(20) NOT NULL,
  `stockexchange_id` bigint(20) NOT NULL,
  `company_code` varchar(256) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_t_company_stockexchange_company_id` FOREIGN KEY (`company_id`) REFERENCES `t_company`(`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_t_company_stockexchange_stockexchange_id` FOREIGN KEY (`stockexchange_id`) REFERENCES `t_stockexchange`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_stock_price`;
CREATE TABLE `t_stock_price` (
  `id` bigint(20) NOT NULL auto_increment,
  `company_stockexchange_id` bigint(20) NOT NULL,
  `current_price` decimal(10,2) NOT NULL DEFAULT 0,
  `price_date` DATE,
  `price_time` TIME,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_t_stock_price_company_stockexchange_id` FOREIGN KEY (`company_stockexchange_id`) REFERENCES `t_company_stockexchange`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_ipo_detail`;
CREATE TABLE `t_ipo_detail` (
  `id` bigint(20) NOT NULL auto_increment,
  `company_id` bigint(20) NOT NULL,
  `stockexchange_id` bigint(20) NOT NULL,
  `price_per_share` decimal(10,2) NOT NULL DEFAULT 0,
  `total_shares` bigint(20) NOT NULL DEFAULT 0,
  `open_datetime` DATETIME,
  `remarks` varchar(256),
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_t_ipo_detail_company_id` FOREIGN KEY (`company_id`) REFERENCES `t_company`(`id`) ON DELETE CASCADE,
  CONSTRAINT `FK_t_ipo_detail_stockexchange_id` FOREIGN KEY (`stockexchange_id`) REFERENCES `t_stockexchange`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL auto_increment,
  `username` varchar(256) NOT NULL,
  `password` varchar(256) NOT NULL,
  `user_type` varchar(256) NOT NULL,
  `email` varchar(256) NOT NULL,
  `mobile_number` varchar(128) NOT NULL,
  `confirmed` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `t_user` VALUES (1, 'admin', '123456', 'admin', 'admin@163.com', '18976530021', 1);
INSERT INTO `t_user` VALUES (2, 'zuogan', '123456', 'user', 'zuogan@qq.com', '17622899511', 1);

INSERT INTO `t_company` VALUES (1, 'Apple', 1.1, 'Tim Cook', 'Ivy, Tim', 'iPhone, iPad and Mac', '');
INSERT INTO `t_company` VALUES (2, 'Google', 1.2, 'Larry Page', 'Larry, Pichar', 'Chrome, Android and Search', '');
INSERT INTO `t_company` VALUES (3, 'Microsoft', 1.3, 'Nadera', 'Bill Gates, Nadera', 'Windows and Office toll', '');
INSERT INTO `t_company` VALUES (4, 'Twitter', 1.4, 'Kubite', 'Kubite, Jimmy', 'Twitter and chat', '');
INSERT INTO `t_company` VALUES (5, 'Facebook', 1.5, 'Zuckerburg', 'Zuckerburg, Johnson', 'Facebook and instagram', '');
INSERT INTO `t_company` VALUES (6, 'Citibank', 1.6, 'Williams Simith', 'Williams, Bill', 'Citibank and insurance', '');
INSERT INTO `t_company` VALUES (7, 'Bayer', 1.7, 'Hadensburg', 'Hadensburg, Huller', 'Bayer medicine and health', '');
INSERT INTO `t_company` VALUES (8, 'Mayo clinic', 1.8, 'Gaslte', 'Gaslte, Wells', 'Mayo clinic and health center', '');
INSERT INTO `t_company` VALUES (9, 'Marriot&Starwood', 1.9, 'Wessens', 'Wessens, Kariste', 'Marriot & Starwood hotel management group', '');
INSERT INTO `t_company` VALUES (10, 'HSBC', 2.1, 'Peter Lee', 'Peter Lee, Tom Howard', 'Hongkong and Shanghai Bank Corporation', '');

INSERT INTO `t_company_sector` VALUES (1, 1, 5);
INSERT INTO `t_company_sector` VALUES (2, 2, 5);
INSERT INTO `t_company_sector` VALUES (3, 3, 5);
INSERT INTO `t_company_sector` VALUES (4, 4, 5);
INSERT INTO `t_company_sector` VALUES (5, 5, 5);
INSERT INTO `t_company_sector` VALUES (6, 6, 1);
INSERT INTO `t_company_sector` VALUES (7, 7, 3);
INSERT INTO `t_company_sector` VALUES (8, 8, 2);
INSERT INTO `t_company_sector` VALUES (9, 9, 4);
INSERT INTO `t_company_sector` VALUES (10, 10, 1);

INSERT INTO `t_company_stockexchange` VALUES (1, 1, 1, 'APPL');
INSERT INTO `t_company_stockexchange` VALUES (2, 1, 2, 'APLE');
INSERT INTO `t_company_stockexchange` VALUES (3, 2, 2, 'GOOG');
INSERT INTO `t_company_stockexchange` VALUES (4, 3, 1, 'MCS');
INSERT INTO `t_company_stockexchange` VALUES (5, 3, 2, 'MICS');
INSERT INTO `t_company_stockexchange` VALUES (6, 4, 1, 'TWT');
INSERT INTO `t_company_stockexchange` VALUES (7, 5, 1, 'FAC');
INSERT INTO `t_company_stockexchange` VALUES (8, 5, 2, 'FABK');
INSERT INTO `t_company_stockexchange` VALUES (9, 6, 1, 'CITI');
INSERT INTO `t_company_stockexchange` VALUES (10, 6, 2, 'CITB');
INSERT INTO `t_company_stockexchange` VALUES (11, 7, 2, 'BAYR');
INSERT INTO `t_company_stockexchange` VALUES (12, 8, 1, 'MAYO');
INSERT INTO `t_company_stockexchange` VALUES (13, 8, 2, 'MYOC');
INSERT INTO `t_company_stockexchange` VALUES (14, 9, 1, 'MARO');
INSERT INTO `t_company_stockexchange` VALUES (15, 9, 2, 'MART');
INSERT INTO `t_company_stockexchange` VALUES (16, 10, 2, 'HSBC');