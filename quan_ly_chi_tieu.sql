-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: May 01, 2024 at 03:30 AM
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
-- Table structure for table `t_budget`
--

DROP TABLE IF EXISTS `t_budget`;
CREATE TABLE IF NOT EXISTS `t_budget` (
  `budgetId` int NOT NULL AUTO_INCREMENT,
  `name` text COLLATE utf8mb3_unicode_ci NOT NULL,
  `amount` float NOT NULL,
  `expensed` float DEFAULT NULL,
  `recurrence` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `startDate` datetime NOT NULL,
  `endDate` datetime DEFAULT NULL,
  `categoryId` int NOT NULL,
  `userId` int NOT NULL,
  PRIMARY KEY (`budgetId`),
  KEY `categoryId` (`categoryId`),
  KEY `userId` (`userId`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

--
-- Dumping data for table `t_budget`
--

INSERT INTO `t_budget` (`budgetId`, `name`, `amount`, `expensed`, `recurrence`, `startDate`, `endDate`, `categoryId`, `userId`) VALUES
(1, 'asdasd', 123123, 243423, 'DAILY', '2024-04-29 23:25:26', NULL, 3, 16),
(2, 'asdasdSSS', 123123, 123123, 'WEEKLY', '2024-04-29 23:25:26', NULL, 4, 16),
(3, 'asdasd', 123123, 0, 'DAILY', '2024-04-30 00:00:00', '2024-05-01 00:00:00', 3, 16);

-- --------------------------------------------------------

--
-- Table structure for table `t_category`
--

DROP TABLE IF EXISTS `t_category`;
CREATE TABLE IF NOT EXISTS `t_category` (
  `categoryId` int NOT NULL AUTO_INCREMENT,
  `name` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `type` tinyint(1) DEFAULT NULL,
  `userId` int NOT NULL,
  PRIMARY KEY (`categoryId`),
  KEY `userId` (`userId`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

--
-- Dumping data for table `t_category`
--

INSERT INTO `t_category` (`categoryId`, `name`, `type`, `userId`) VALUES
(3, 'FOOD', 1, 16),
(4, 'FOOD', 0, 16),
(5, 'zxczxc', 0, 16),
(6, 'asd', 0, 16),
(7, 'cxzc', 0, 16),
(8, 'wwwww', 1, 16),
(9, 'testEdit', 0, 16),
(10, 'test', 0, 16);

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
  `userName` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `email` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `password` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  `createAt` datetime DEFAULT NULL,
  `lastLogin` datetime DEFAULT NULL,
  `total_money` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci NOT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;

--
-- Dumping data for table `t_user`
--

INSERT INTO `t_user` (`userId`, `userName`, `email`, `password`, `createAt`, `lastLogin`, `total_money`) VALUES
(16, 'trung2001sgp@gmail.com', 'trung2001sgp@gmail.com', 'pmWkWSBCL51Bfkhn79xPuKBKHz//H6B+mY6G9/eieuM=', '2024-04-28 12:14:26', '2024-05-01 00:02:56', '0');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
