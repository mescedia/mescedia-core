-- MySQL dump 10.19  Distrib 10.3.39-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: mescediaDEVOS
-- ------------------------------------------------------
-- Server version	10.3.39-MariaDB-0+deb10u1-log

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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
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
  `username` varchar(100) DEFAULT NULL,
  `passwd` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ukeyName` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dbConnections`
--

LOCK TABLES `dbConnections` WRITE;
/*!40000 ALTER TABLE `dbConnections` DISABLE KEYS */;
INSERT INTO `dbConnections` VALUES (1,'dbMariaDbDemoERP','jdbc:mysql://localhost:3306/demoerp','erp','erp'),(2,'dbPostgresDemoERP','jdbc:postgresql://localhost:5433/demoerp','mescedia','mescedia'),(3,'dbSqliteDemoERP','jdbc:sqlite:data/database/sqlite/demoerp.db','dummy','dummy');
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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `formatAnalyserRule`
--

LOCK TABLES `formatAnalyserRule` WRITE;
/*!40000 ALTER TABLE `formatAnalyserRule` DISABLE KEYS */;
INSERT INTO `formatAnalyserRule` VALUES (1,1,'indexof','UNB',1),(2,1,'indexof','UNH',1),(3,1,'indexof','BGM',1),(4,2,'indexof','<UNB>',1),(5,2,'indexof','<UNH>',1),(6,2,'indexof','<BGM>',1),(7,3,'indexof','<IDOC BEGIN=',1),(8,3,'indexof','<EDI_DC40',1);
/*!40000 ALTER TABLE `formatAnalyserRule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interfaceIn`
--

DROP TABLE IF EXISTS `interfaceIn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `interfaceIn` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `source` varchar(256) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `source` (`source`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interfaceIn`
--

LOCK TABLES `interfaceIn` WRITE;
/*!40000 ALTER TABLE `interfaceIn` DISABLE KEYS */;
/*!40000 ALTER TABLE `interfaceIn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interfaceOut`
--

DROP TABLE IF EXISTS `interfaceOut`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `interfaceOut` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(256) NOT NULL,
  `destination` varchar(256) NOT NULL,
  `endpoint` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `source` (`destination`),
  UNIQUE KEY `path` (`path`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interfaceOut`
--

LOCK TABLES `interfaceOut` WRITE;
/*!40000 ALTER TABLE `interfaceOut` DISABLE KEYS */;
/*!40000 ALTER TABLE `interfaceOut` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messageFormat`
--

LOCK TABLES `messageFormat` WRITE;
/*!40000 ALTER TABLE `messageFormat` DISABLE KEYS */;
INSERT INTO `messageFormat` VALUES (1,'Edifact',0,400,NULL,1),(2,'DfdlXmlEdifact',0,NULL,'</BGM>',1),(3,'SapIdocXml',0,NULL,'</EDI_DC40',1);
/*!40000 ALTER TABLE `messageFormat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messageTypes`
--

DROP TABLE IF EXISTS `messageTypes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messageTypes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `typeName` (`typeName`)
) ENGINE=InnoDB AUTO_INCREMENT=197 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messageTypes`
--

LOCK TABLES `messageTypes` WRITE;
/*!40000 ALTER TABLE `messageTypes` DISABLE KEYS */;
INSERT INTO `messageTypes` VALUES (1,'APERAK'),(2,'AUTHOR'),(3,'BALANC'),(4,'BANSTA'),(5,'BAPLIE'),(6,'BAPLTE'),(7,'BERMAN'),(8,'BMISRM'),(9,'BOPBNK'),(10,'BOPCUS'),(11,'BOPDIR'),(12,'BOPINF'),(13,'BUSCRD'),(14,'CALINF'),(15,'CASINT'),(16,'CASRES'),(17,'CHACCO'),(18,'CLASET'),(19,'CNTCND'),(20,'COACSU'),(21,'COARRI'),(22,'CODECO'),(23,'CODENO'),(24,'COEDOR'),(25,'COHAOR'),(26,'COLREQ'),(27,'COMDIS'),(28,'CONAPW'),(29,'CONDPV'),(30,'CONDRA'),(31,'CONDRO'),(32,'CONEST'),(33,'CONITT'),(34,'CONPVA'),(35,'CONQVA'),(36,'CONRPW'),(37,'CONTEN'),(38,'CONWQD'),(39,'COPARN'),(40,'COPAYM'),(41,'COPINO'),(42,'COPRAR'),(43,'COREOR'),(44,'COSTCO'),(45,'COSTOR'),(46,'CREADV'),(47,'CREEXT'),(48,'CREMUL'),(49,'CUSCAR'),(50,'CUSDEC'),(51,'CUSEXP'),(52,'CUSPED'),(53,'CUSREP'),(54,'CUSRES'),(55,'DAPLOS'),(56,'DEBADV'),(57,'DEBMUL'),(58,'DEBREC'),(59,'DELFOR'),(60,'DELJIT'),(61,'DESADV'),(62,'DESTIM'),(63,'DGRECA'),(64,'DIRDEB'),(65,'DIRDEF'),(66,'DMRDEF'),(67,'DMSTAT'),(68,'DOCADV'),(69,'DOCAMA'),(70,'DOCAMI'),(71,'DOCAMR'),(72,'DOCAPP'),(73,'DOCARE'),(74,'DOCINF'),(75,'ENTREC'),(76,'FINCAN'),(77,'FINPAY'),(78,'FINSTA'),(79,'GENRAL'),(80,'GESMES'),(81,'GOVCBR'),(82,'HANMOV'),(83,'ICASRP'),(84,'ICSOLI'),(85,'IFCSUM'),(86,'IFTCCA'),(87,'IFTDGN'),(88,'IFTFCC'),(89,'IFTIAG'),(90,'IFTICL'),(91,'IFTMAN'),(92,'IFTMBC'),(93,'IFTMBF'),(94,'IFTMBP'),(95,'IFTMCA'),(96,'IFTMCS'),(97,'IFTMIN'),(98,'IFTRIN'),(99,'IFTSAI'),(100,'IFTSTA'),(101,'IFTSTQ'),(102,'IMPDEF'),(103,'INFCON'),(104,'INFENT'),(105,'INSDES'),(106,'INSPRE'),(107,'INSREQ'),(108,'INSRPT'),(109,'INVOIC'),(110,'INVRPT'),(111,'IPPOAD'),(112,'IPPOMO'),(113,'ISENDS'),(114,'ITRRPT'),(115,'JAPRES'),(116,'JINFDE'),(117,'JOBAPP'),(118,'JOBCON'),(119,'JOBMOD'),(120,'JOBOFF'),(121,'JUPREQ'),(122,'LEDGER'),(123,'LREACT'),(124,'LRECLM'),(125,'MEDPID'),(126,'MEDPRE'),(127,'MEDREQ'),(128,'MEDRPT'),(129,'MEDRUC'),(130,'MEQPOS'),(131,'MOVINS'),(132,'MSCONS'),(133,'ORDCHG'),(134,'ORDERS'),(135,'ORDRSP'),(136,'OSTENQ'),(137,'OSTRPT'),(138,'PARTIN'),(139,'PAXLST'),(140,'PAYDUC'),(141,'PAYEXT'),(142,'PAYMUL'),(143,'PAYORD'),(144,'PRICAT'),(145,'PRIHIS'),(146,'PROCST'),(147,'PRODAT'),(148,'PRODEX'),(149,'PROINQ'),(150,'PROSRV'),(151,'PROTAP'),(152,'PRPAID'),(153,'QALITY'),(154,'QUOTES'),(155,'RDRMES'),(156,'REBORD'),(157,'RECADV'),(158,'RECALC'),(159,'RECECO'),(160,'RECLAM'),(161,'RECORD'),(162,'REGENT'),(163,'RELIST'),(164,'REMADV'),(165,'REPREM'),(166,'REQDOC'),(167,'REQOTE'),(168,'RESETT'),(169,'RESMSG'),(170,'RETACC'),(171,'RETANN'),(172,'RETINS'),(173,'RPCALL'),(174,'SAFHAZ'),(175,'SANCRT'),(176,'SLSFCT'),(177,'SLSRPT'),(178,'SOCADE'),(179,'SSIMOD'),(180,'SSRECH'),(181,'SSREGW'),(182,'STATAC'),(183,'STLRPT'),(184,'SUPCOT'),(185,'SUPMAN'),(186,'SUPRES'),(187,'TANSTA'),(188,'TAXCON'),(189,'TPFREP'),(190,'UTILMD'),(191,'UTILTS'),(192,'VATDEC'),(193,'VESDEP'),(194,'WASDIS'),(195,'WKGRDC'),(196,'WKGRRE');
/*!40000 ALTER TABLE `messageTypes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messageVersions`
--

DROP TABLE IF EXISTS `messageVersions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `messageVersions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `versionName` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `versionName` (`versionName`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messageVersions`
--

LOCK TABLES `messageVersions` WRITE;
/*!40000 ALTER TABLE `messageVersions` DISABLE KEYS */;
INSERT INTO `messageVersions` VALUES (1,'D00A'),(2,'D00B'),(3,'D01A'),(4,'D01B'),(5,'D01C'),(6,'D02A'),(7,'D02B'),(8,'D03A'),(9,'D03B'),(10,'D04A'),(11,'D04B'),(12,'D05A'),(13,'D05B'),(14,'D06A'),(15,'D06B'),(16,'D07A'),(17,'D07B'),(18,'D08A'),(19,'D08B'),(20,'D09A'),(21,'D09B'),(22,'D10A'),(23,'D10B'),(24,'D11A'),(25,'D11B'),(26,'D12A'),(27,'D12B'),(28,'D13A'),(29,'D13B'),(30,'D14A'),(31,'D14B'),(32,'D15A'),(33,'D15B'),(34,'D16A'),(35,'D16B'),(36,'D17A'),(37,'D17B'),(38,'D18A'),(39,'D18B'),(40,'D19A'),(41,'D19B'),(42,'D93A'),(43,'D94A'),(44,'D94B'),(45,'D95A'),(46,'D95B'),(47,'D96A'),(48,'D96B'),(49,'D97A'),(50,'D97B'),(51,'D98A'),(52,'D98B'),(53,'D99A'),(54,'D99B');
/*!40000 ALTER TABLE `messageVersions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `processRules`
--

DROP TABLE IF EXISTS `processRules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `processRules` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `interfaceInId` int(11) NOT NULL,
  `interfaceOutId` int(11) NOT NULL,
  `processSetId` int(11) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `senderUserId` int(11) NOT NULL,
  `receiverUserId` int(11) NOT NULL,
  `msgFormatId` int(11) NOT NULL,
  `msgTypeId` int(11) NOT NULL,
  `msgVersionId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `interfaceInId` (`interfaceInId`,`senderUserId`,`receiverUserId`,`msgFormatId`,`msgTypeId`,`msgVersionId`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `processRules`
--

LOCK TABLES `processRules` WRITE;
/*!40000 ALTER TABLE `processRules` DISABLE KEYS */;
/*!40000 ALTER TABLE `processRules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `processSet`
--

DROP TABLE IF EXISTS `processSet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `processSet` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `description` text DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `processSet`
--

LOCK TABLES `processSet` WRITE;
/*!40000 ALTER TABLE `processSet` DISABLE KEYS */;
/*!40000 ALTER TABLE `processSet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `processStep`
--

DROP TABLE IF EXISTS `processStep`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `processStep` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `command` text DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `processStep`
--

LOCK TABLES `processStep` WRITE;
/*!40000 ALTER TABLE `processStep` DISABLE KEYS */;
INSERT INTO `processStep` VALUES (1,'EDIFact2Xml','bean:org.mescedia.processors.Edifact2Xml'),(2,'XsltDemoJavaExtFunc','xslt-saxon:file:data/mappings/tests/extensionFunctions.xslt?saxonExtensionFunctions=#translate,#log');
/*!40000 ALTER TABLE `processStep` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `processSteps`
--

DROP TABLE IF EXISTS `processSteps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `processSteps` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `processSetId` int(11) NOT NULL,
  `processStepId` int(11) NOT NULL,
  `processOrderId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `processSetId` (`processSetId`,`processStepId`,`processOrderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `processSteps`
--

LOCK TABLES `processSteps` WRITE;
/*!40000 ALTER TABLE `processSteps` DISABLE KEYS */;
/*!40000 ALTER TABLE `processSteps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `value` (`value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-20 12:27:28
