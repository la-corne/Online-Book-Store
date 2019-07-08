CREATE DATABASE  IF NOT EXISTS `bookstore` ;

USE `bookstore`;

DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
  `ISBN` int(11) NOT NULL,
  `TITLE` varchar(200) NOT NULL,
  `PUBLICATIONYEAR` int(11) DEFAULT NULL,
  `SELLINGPRICE` double NOT NULL,
  `CATEGORY` varchar(200) NOT NULL,
  `publisher_name` varchar(200) NOT NULL,
  `quantity` int(11) NOT NULL,
  PRIMARY KEY (`ISBN`),
  UNIQUE KEY `ISBN_UNIQUE` (`ISBN`),
  KEY `publisher_name_idx` (`publisher_name`),
  KEY `publisher_index` (`publisher_name`),
  KEY `title_index` (`TITLE`),
  KEY `category_index` (`CATEGORY`),
  CONSTRAINT `book_ibfk_1` FOREIGN KEY (`publisher_name`) REFERENCES `publisher` (`publisher_name`));