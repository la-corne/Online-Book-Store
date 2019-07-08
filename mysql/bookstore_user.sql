CREATE DATABASE  IF NOT EXISTS `bookstore`;

USE `bookstore`;

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `NAME` varchar(200) NOT NULL,
  `FNAME` varchar(200) NOT NULL,
  `LNAME` varchar(200) NOT NULL,
  `PASSWORD` varchar(200) NOT NULL,
  `EMAIL` varchar(200) NOT NULL,
  `PHONENUMBER` varchar(15) NOT NULL,
  `SHIPPINGADDRESS` varchar(200) NOT NULL,
  `ISMANAGER` tinyint(1) NOT NULL,
  PRIMARY KEY (`NAME`));
