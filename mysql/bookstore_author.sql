CREATE DATABASE  IF NOT EXISTS `bookstore`;
USE `bookstore`;


DROP TABLE IF EXISTS `author`;

CREATE TABLE `author` (
  `author_name` varchar(200) NOT NULL,
  `isbn` int(11) NOT NULL,
  PRIMARY KEY (`isbn`,`author_name`),
  KEY `author_index` (`author_name`),
  CONSTRAINT `isbn` FOREIGN KEY (`isbn`) REFERENCES `book` (`ISBN`));