CREATE DATABASE  IF NOT EXISTS `bookstore`;
USE `bookstore`;

DROP TABLE IF EXISTS `books_sold`;

CREATE TABLE `books_sold` (
  `uname` varchar(100) NOT NULL,
  `isbn` int(11) NOT NULL,
  `SellingDate` date NOT NULL,
  `SellingTime` time DEFAULT NULL,
  `amount` int(15) NOT NULL,
  PRIMARY KEY (`uname`,`isbn`),
  KEY `isbn` (`isbn`),
  CONSTRAINT `books_sold_ibfk_1` FOREIGN KEY (`isbn`) REFERENCES `book` (`ISBN`),
  CONSTRAINT `books_sold_ibfk_2` FOREIGN KEY (`uname`) REFERENCES `user` (`NAME`));
