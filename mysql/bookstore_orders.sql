CREATE DATABASE  IF NOT EXISTS `bookstore`;
USE `bookstore`;

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `isbn` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `publisher_name` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `orders_ibfk_1` (`isbn`),
  KEY `orders_ibfk_2` (`publisher_name`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`isbn`) REFERENCES `book` (`ISBN`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`publisher_name`) REFERENCES `publisher` (`publisher_name`));
