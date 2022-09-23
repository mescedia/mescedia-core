-- MySQL dump 10.19  Distrib 10.3.36-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: mescedia2
-- ------------------------------------------------------
-- Server version	10.3.36-MariaDB-0+deb10u1-log

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
-- Current Database: `mescedia2`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `mescedia2` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `mescedia2`;

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
-- Dumping data for table `contentAnalyserRule`
--

LOCK TABLES `contentAnalyserRule` WRITE;
/*!40000 ALTER TABLE `contentAnalyserRule` DISABLE KEYS */;
INSERT INTO `contentAnalyserRule` VALUES (1,1,'senderId','regex','UNB\\+.+?\\+(.*?)[\\:|\\+]',1),(2,1,'receiverId','regex','UNB\\+.+?[\\+].+?[\\+](.*?)[\\:|\\+]',1),(3,1,'messageType','regex','UNH.+?\\+(.+?)\\:',1),(4,1,'messageVersion','regex','UNH.+?\\+[A-Z]{6}\\:([D])\\:([0-9]{2}[A|B|C]{1})\\:',1),(5,2,'senderId','regex','<E0004>(.+?)</E0004>',1),(6,2,'receiverId','regex','<E0010>(.+?)</E0010>',1),(7,2,'messageType','regex','<UNH>.+?<E0065>(.+?)</E0065>',1),(8,2,'messageVersion','regex','<UNH>.+?<E0052>(.+?)</E0052>.*?<E0054>(.+?)</E0054>',1),(9,3,'senderId','regex','<SNDPRN>(.+?)</SNDPRN>',1),(10,3,'receiverId','regex','<RCVPRN>(.+?)</RCVPRN>',1),(11,3,'messageType','regex','<MESTYP>(.+?)</MESTYP>',1),(12,3,'messageVersion','regex','<IDOCTYP>(.+?)</IDOCTYP>',1);
/*!40000 ALTER TABLE `contentAnalyserRule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dbConnections`
--

DROP TABLE IF EXISTS `dbConnections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dbConnections` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `url` text NOT NULL,
  `user` text NOT NULL,
  `passwd` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ukeyName` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dbConnections`
--

LOCK TABLES `dbConnections` WRITE;
/*!40000 ALTER TABLE `dbConnections` DISABLE KEYS */;
INSERT INTO `dbConnections` VALUES (1,'dbDemoERP','jdbc:mysql://localhost/demoERP','erp','erp');
/*!40000 ALTER TABLE `dbConnections` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `formatAnalyserRule`
--

LOCK TABLES `formatAnalyserRule` WRITE;
/*!40000 ALTER TABLE `formatAnalyserRule` DISABLE KEYS */;
INSERT INTO `formatAnalyserRule` VALUES (1,1,'indexof','UNB+',1),(2,1,'indexof','UNH+',1),(3,1,'indexof','BGM+',1),(4,2,'indexof','<UNB>',1),(5,2,'indexof','<UNH>',1),(6,2,'indexof','<BGM>',1),(7,3,'indexof','<IDOC BEGIN=',1),(8,3,'indexof','<EDI_DC40',1);
/*!40000 ALTER TABLE `formatAnalyserRule` ENABLE KEYS */;
UNLOCK TABLES;

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

--
-- Dumping data for table `messageFormat`
--

LOCK TABLES `messageFormat` WRITE;
/*!40000 ALTER TABLE `messageFormat` DISABLE KEYS */;
INSERT INTO `messageFormat` VALUES (1,'Edifact',0,400,NULL,1),(2,'DfdlXmlEdifact',0,NULL,'</BGM>',1),(3,'SapIdocXml',0,NULL,'</EDI_DC40',1);
/*!40000 ALTER TABLE `messageFormat` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-23 22:09:29
