-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Apr 27, 2024 at 06:28 AM
-- Server version: 8.2.0
-- PHP Version: 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `quan_ly_chi_tieu`
--

-- --------------------------------------------------------

--
-- Table structure for table `t_category`
--

DROP TABLE IF EXISTS `t_category`;
CREATE TABLE IF NOT EXISTS `t_category` (
  `categoryId` int NOT NULL AUTO_INCREMENT,
  `name` text COLLATE utf8mb3_unicode_ci,
  `type` binary(1) DEFAULT NULL,
  `userId` int DEFAULT NULL,
  PRIMARY KEY (`categoryId`),
  KEY `userId` (`userId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `t_notification`
--

DROP TABLE IF EXISTS `t_notification`;
CREATE TABLE IF NOT EXISTS `t_notification` (
  `notifyId` int NOT NULL AUTO_INCREMENT,
  `message` text COLLATE utf8mb3_unicode_ci,
  `notifyDate` datetime DEFAULT NULL,
  `status` binary(1) DEFAULT NULL,
  `userId` int DEFAULT NULL,
  PRIMARY KEY (`notifyId`),
  KEY `userId` (`userId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
CREATE TABLE IF NOT EXISTS `t_user` (
  `userId` int NOT NULL AUTO_INCREMENT,
  `userName` text COLLATE utf8mb3_unicode_ci,
  `email` varchar(30) COLLATE utf8mb3_unicode_ci DEFAULT NULL,
  `password` text COLLATE utf8mb3_unicode_ci,
  `createAt` datetime DEFAULT NULL,
  `lastLogin` datetime DEFAULT NULL,
  `total_money` text COLLATE utf8mb3_unicode_ci,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
