-- MySQL dump 10.19  Distrib 10.3.34-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: mescedia2
-- ------------------------------------------------------
-- Server version	10.3.34-MariaDB-0+deb10u1-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `contentAnalyserRule`
--

DROP TABLE IF EXISTS `contentAnalyserRule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contentAnalyserRule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `formatId` int(11) NOT NULL,
  `rItem` varchar(20) NOT NULL,
  `rType` varchar(20) NOT NULL,
  `rValue` varchar(255) NOT NULL,
  `active` int(1) DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `formatId` (`formatId`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `formatAnalyserRule`
--

DROP TABLE IF EXISTS `formatAnalyserRule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `formatAnalyserRule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `formatId` int(11) NOT NULL,
  `rType` varchar(20) NOT NULL,
  `rValue` varchar(255) NOT NULL,
  `active` int(1) DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `formatId` (`formatId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `messageFormat`
--

DROP TABLE IF EXISTS `messageFormat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messageFormat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `formatName` varchar(50) NOT NULL,
  `extractStartIndex` int(4) NOT NULL,
  `extractEndIndex` int(4) DEFAULT NULL,
  `extractEndIndexString` varchar(50) DEFAULT NULL,
  `active` int(1) DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `formatName` (`formatName`),
  KEY `formatName_2` (`formatName`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-14  9:33:16
