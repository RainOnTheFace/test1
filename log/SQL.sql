CREATE TABLE `log_table2` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `deviceId` varchar(20) DEFAULT NULL,
  `msg` varchar(50) DEFAULT NULL,
  `startTime` varchar(100) DEFAULT NULL,
  `type` varchar(40) DEFAULT NULL,
  `latitude` decimal(15,10) DEFAULT '0.0000000000',
  `longitude` decimal(15,10) DEFAULT '0.0000000000',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;
