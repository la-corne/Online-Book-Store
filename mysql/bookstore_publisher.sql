CREATE DATABASE  IF NOT EXISTS `bookstore`;
USE `bookstore`;

DROP TABLE IF EXISTS `publisher`;

CREATE TABLE `publisher` (
  `publisher_name` varchar(200) NOT NULL,
  `ADDRESS` varchar(200) DEFAULT NULL,
  `TELEPHONENUMBER` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`publisher_name`),
  UNIQUE KEY `NAME_UNIQUE` (`publisher_name`)) ;