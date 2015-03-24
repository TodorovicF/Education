CREATE DATABASE  IF NOT EXISTS `bookstore` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `bookstore`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: bookstore
-- ------------------------------------------------------
-- Server version	5.6.22-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice` (
  `InvoiceID` int(11) NOT NULL AUTO_INCREMENT,
  `UserID` bigint(20) NOT NULL,
  `InvoiceDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `TotalAmount` float NOT NULL DEFAULT '0',
  `IsProcessed` enum('y','n') DEFAULT NULL,
  PRIMARY KEY (`InvoiceID`),
  KEY `UserID` (`UserID`),
  CONSTRAINT `invoice_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
INSERT INTO `invoice` VALUES (1,1,'2015-02-11 11:11:11',18.54,'y'),(2,1,'2015-02-12 11:11:11',32.54,'y'),(3,1,'2015-02-13 11:11:11',44.54,'y'),(4,1,'2015-02-14 11:11:11',23.54,'y'),(5,2,'2015-02-15 11:11:11',32.54,'y'),(6,2,'2015-03-01 11:11:11',14.54,'y'),(7,2,'2015-03-02 11:11:11',8.54,'y'),(8,2,'2015-03-03 11:11:11',15.54,'y'),(9,2,'2015-03-04 11:11:11',10.54,'y'),(10,2,'2015-03-05 11:11:11',9.54,'y'),(11,2,'2015-03-06 11:11:11',7.54,'y'),(12,2,'2015-03-07 11:11:11',17.54,'y'),(13,2,'2015-03-08 11:11:11',11.54,'y'),(14,2,'2015-03-09 11:11:11',22.54,'n'),(15,1,'2015-03-10 11:11:11',32.54,'n'),(16,101,'2015-03-12 18:13:42',9.91,'n'),(17,101,'2015-03-12 18:14:00',39.7,'n'),(18,101,'2015-03-12 19:27:57',121,'n'),(19,101,'2015-03-12 19:42:08',108.36,'n');
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lineitem`
--

DROP TABLE IF EXISTS `lineitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lineitem` (
  `LineItemID` int(11) NOT NULL AUTO_INCREMENT,
  `InvoiceID` int(11) NOT NULL DEFAULT '0',
  `ProductID` varchar(13) DEFAULT NULL,
  `Quantity` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`LineItemID`),
  KEY `InvoiceID` (`InvoiceID`),
  KEY `ProductID` (`ProductID`),
  CONSTRAINT `lineitem_ibfk_1` FOREIGN KEY (`InvoiceID`) REFERENCES `invoice` (`InvoiceID`),
  CONSTRAINT `lineitem_ibfk_2` FOREIGN KEY (`ProductID`) REFERENCES `product` (`ProductISBN13`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lineitem`
--

LOCK TABLES `lineitem` WRITE;
/*!40000 ALTER TABLE `lineitem` DISABLE KEYS */;
INSERT INTO `lineitem` VALUES (15,1,'9781476783703',2),(16,16,'9780451470041',1),(17,17,'9780525427209',1),(18,17,'9780805098051',1),(19,4,'9781449460365',2),(20,5,'9781612184340',2),(21,6,'9780451470041',2),(22,7,'9781477824986',3),(23,8,'9780805098051',4),(24,9,'9781476783703',5),(25,10,'9780525427209',2),(26,11,'9780525427209',2),(27,12,'9780451470041',2),(28,13,'9780525427209',2),(29,14,'9780525427209',2),(30,15,'9781476783703',2),(31,2,'9781449460365',2),(32,3,'9781612184340',2),(33,18,'9781449460365',10),(34,19,'9781612184340',12);
/*!40000 ALTER TABLE `lineitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `ProductISBN13` varchar(13) NOT NULL DEFAULT '',
  `ProductISBN10` varchar(10) DEFAULT NULL,
  `ProductDescription` varchar(254) NOT NULL DEFAULT '',
  `ProductAuthor` varchar(254) NOT NULL DEFAULT '',
  `ProductPrice` decimal(7,2) NOT NULL DEFAULT '0.00',
  `ProductCategory` varchar(100) DEFAULT NULL,
  `ProductStock` int(11) NOT NULL DEFAULT '0',
  `ProductAddedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`ProductISBN13`),
  UNIQUE KEY `ProductISBN13` (`ProductISBN13`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES ('9780451470041','451470044','Skin Game: A Novel of the Dresden Files','Jim Butcher',9.91,'Science Fiction & Fantasy',99,'2015-03-12 18:10:51'),('9780525427209','525427201','Bettyville: A Memoir','George Hodgman',16.77,'Biographies & Memoirs',14,'2015-03-12 18:10:51'),('9780805098051','805098054','The Barefoot Lawyer: A Blind Man\'s Fight for Justice and Freedom in China','Chen Guangcheng',22.93,'Biographies & Memoirs',19,'2015-03-12 18:10:51'),('9781449460365','1449460364','Exploring Calvin and Hobbes: An Exhibition Catalogue','Bill Watterson, Robb Jenny',12.10,'Arts & Photography',20,'2015-03-12 18:10:51'),('9781476772295','1476772290','Get What\'s Yours: The Secrets to Maxing Out Your Social Security','Laurence J. Kotlikoff, Philip Moeller, Paul Solman',16.69,'Business & Money',20,'2015-03-12 18:10:51'),('9781476783703','1476783705','From Jailer to Jailed: My Journey from Correction and Police Commissioner to Inmate #84888-054','Bernard B. Kerik',19.89,'Biographies & Memoirs',20,'2015-03-12 18:10:51'),('9781477824986','1477824987','Blood of Gods (The Breaking World)','David Dalglish, Robert J. Duperre',9.49,'Science Fiction & Fantasy',100,'2015-03-12 18:10:51'),('9781612184340','1612184340','The Beginning of the End (Apocalypse Z)','Manel Loureiro',9.03,'Literature & Fiction',18,'2015-03-12 18:10:51'),('9783869307886','3869307889','Henri Cartier-Bresson: The Decisive Moment','Cartier Bresson',84.99,'Arts & Photography',20,'2015-03-12 18:10:51');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase`
--

DROP TABLE IF EXISTS `purchase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchase` (
  `PurchaseID` int(11) NOT NULL AUTO_INCREMENT,
  `UserID` bigint(20) NOT NULL,
  `PurchaseDate` datetime NOT NULL,
  `ProductCode` varchar(10) NOT NULL,
  PRIMARY KEY (`PurchaseID`),
  KEY `UserID` (`UserID`),
  CONSTRAINT `purchase_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase`
--

LOCK TABLES `purchase` WRITE;
/*!40000 ALTER TABLE `purchase` DISABLE KEYS */;
/*!40000 ALTER TABLE `purchase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ratings`
--

DROP TABLE IF EXISTS `ratings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ratings` (
  `RatingID` int(11) NOT NULL AUTO_INCREMENT,
  `RatingISBN13` varchar(13) DEFAULT NULL,
  `RatingValue` int(11) NOT NULL DEFAULT '0',
  `RatingEmail` varchar(50) NOT NULL,
  `RatingDescription` varchar(254) DEFAULT NULL,
  PRIMARY KEY (`RatingID`),
  KEY `RatingISBN13` (`RatingISBN13`),
  KEY `RatingEmail` (`RatingEmail`),
  CONSTRAINT `ratings_ibfk_1` FOREIGN KEY (`RatingISBN13`) REFERENCES `product` (`ProductISBN13`),
  CONSTRAINT `ratings_ibfk_2` FOREIGN KEY (`RatingEmail`) REFERENCES `user` (`EmailAddress`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ratings`
--

LOCK TABLES `ratings` WRITE;
/*!40000 ALTER TABLE `ratings` DISABLE KEYS */;
/*!40000 ALTER TABLE `ratings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `UserID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(50) DEFAULT '',
  `LastName` varchar(50) DEFAULT '',
  `Passwd` varchar(50) DEFAULT NULL,
  `EmailAddress` varchar(50) NOT NULL,
  `Address1` varchar(50) DEFAULT NULL,
  `Address2` varchar(50) DEFAULT NULL,
  `City` varchar(50) DEFAULT NULL,
  `State` varchar(50) DEFAULT NULL,
  `Zip` varchar(50) DEFAULT NULL,
  `Country` varchar(50) DEFAULT NULL,
  `SignInDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `LastLoginDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `EmailAddress` (`EmailAddress`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'','','mauris.','mi@arcu.edu',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(2,'','','arcu.','Curabitur@ligulaelitpretium.org',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(3,'','','metus','Donec.fringilla@Quisqueporttitoreros.com',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(4,'','','varius.','convallis@Nuncquisarcu.net',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(5,'','','elit.','a.purus.Duis@euismodurnaNullam.com',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(6,'','','purus','malesuada@ultrices.ca',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(7,'','','Donec','Nullam.scelerisque.neque@veliteget.net',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(8,'','','congue.','eu.enim.Etiam@SuspendisseduiFusce.ca',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(9,'','','quis,','lacus@et.com',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(10,'','','justo.','tellus@quisturpisvitae.ca',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(11,'','','orci.','enim.Mauris.quis@euplacerat.ca',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(12,'','','massa','tempus@lacusUt.org',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(13,'','','varius.','vestibulum.massa@dictumeleifendnunc.net',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(14,'','','a','interdum.enim@nisi.org',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(15,'','','arcu.','Aliquam@dictumplacerataugue.edu',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(16,'','','aliquam,','nibh.Phasellus.nulla@rutrum.edu',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(17,'','','est','Quisque.fringilla@lorem.ca',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(18,'','','tempus','dui@Nullamfeugiat.edu',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(19,'','','eu,','at@nibhDonecest.org',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(20,'','','Proin','odio.tristique@Nulla.com',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(21,'','','dictum','urna@dui.com',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(22,'','','metus.','Etiam@Integer.co.uk',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(23,'','','Donec','tincidunt@etipsumcursus.edu',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(24,'','','amet,','vitae@dolor.ca',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(25,'','','natoque','pede.blandit@tortor.co.uk',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(26,'','','turpis','orci.luctus@dui.org',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(27,'','','erat','nibh.enim.gravida@mi.edu',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(28,'','','Nullam','condimentum@atlacusQuisque.net',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(29,'','','sed','laoreet@posuere.co.uk',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(30,'','','tempus','amet.metus@varius.org',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(31,'','','Etiam','lectus.justo.eu@dolorFusce.co.uk',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(32,'','','aliquam','lectus.Nullam@erat.net',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(33,'','','vel','Maecenas.mi.felis@egetmetuseu.edu',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(34,'','','enim.','neque.venenatis@vitaealiquetnec.ca',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(35,'','','Proin','porttitor.vulputate@ipsum.com',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(36,'','','at,','aliquet@magnaPraesent.co.uk',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(37,'','','turpis','quis.massa@arcueu.edu',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(38,'','','iaculis','Nullam@anunc.com',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(39,'','','nulla','id.mollis@purus.co.uk',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(40,'','','Suspendisse','augue@odioNaminterdum.net',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(41,'','','Duis','non.arcu@Nullaeuneque.net',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(42,'','','sit','Nunc.sollicitudin@nisi.co.uk',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(43,'','','iaculis,','Ut.semper@ametdiameu.co.uk',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(44,'','','rutrum','sagittis.semper.Nam@semperrutrum.edu',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(45,'','','a','Integer.mollis.Integer@idmagna.edu',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(46,'','','et,','sem@nec.com',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(47,'','','sociosqu','ut.aliquam.iaculis@fermentumfermentumarcu.co.uk',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(48,'','','commodo','Sed@Loremipsumdolor.net',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(49,'','','feugiat.','sed.pede.nec@dictumeleifend.edu',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(50,'','','In','et.rutrum.eu@orciluctus.net',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(51,'','','commodo','rutrum@semperduilectus.com',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(52,'','','dapibus','faucibus@ametmassaQuisque.net',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(53,'','','lacus','purus@quisaccumsan.ca',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(54,'','','eu,','arcu@sociisnatoquepenatibus.net',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(55,'','','sagittis','Aliquam.rutrum@urnaconvalliserat.org',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(56,'','','sapien.','luctus.aliquet.odio@Mauris.co.uk',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(57,'','','ac','sagittis.semper.Nam@mollisduiin.org',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(58,'','','mollis','molestie.in.tempus@felisullamcorper.co.uk',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(59,'','','augue,','pede.Cum@sapienmolestieorci.com',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(60,'','','Aliquam','magna@posuere.net',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(61,'','','mattis.','per.inceptos.hymenaeos@scelerisque.net',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(62,'','','sed','sem.magna@sociis.org',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(63,'','','ullamcorper','turpis.egestas@netusetmalesuada.org',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(64,'','','pede,','dolor@ametluctus.edu',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(65,'','','Nam','Aenean@utnisia.edu',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(66,'','','Donec','Aenean@est.org',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(67,'','','lorem,','dictum.augue@lectus.edu',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(68,'','','Aliquam','ac.mattis@seddui.com',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(69,'','','felis,','malesuada.augue.ut@Suspendissedui.edu',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(70,'','','rutrum','mauris@euismodmauris.net',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(71,'','','leo.','hendrerit.Donec@odioa.co.uk',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(72,'','','odio','Integer@nonummyut.ca',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(73,'','','rhoncus.','ut@Aliquameratvolutpat.org',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(74,'','','aliquet','gravida.Aliquam.tincidunt@rhoncusid.co.uk',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(75,'','','nec,','et@scelerisqueloremipsum.net',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(76,'','','Vivamus','dolor.sit@euismod.net',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(77,'','','mi','metus.Vivamus@faucibus.org',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(78,'','','ipsum','vestibulum.nec.euismod@Nunc.ca',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(79,'','','sollicitudin','at.egestas.a@enimCurabitur.net',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(80,'','','arcu.','volutpat.nunc.sit@Vivamus.com',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(81,'','','netus','vel.turpis@nullamagnamalesuada.edu',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(82,'','','Cras','placerat.augue.Sed@IncondimentumDonec.co.uk',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(83,'','','egestas.','non.luctus.sit@mi.ca',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(84,'','','Nullam','fermentum.fermentum.arcu@tortornibh.ca',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(85,'','','Aenean','felis@MorbivehiculaPellentesque.ca',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(86,'','','sed','molestie.Sed@Maurismolestie.edu',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(87,'','','eget','tincidunt.Donec@Vivamusnibh.org',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(88,'','','et','Curabitur@dictummi.net',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(89,'','','ornare','Nunc.lectus@interdumfeugiatSed.ca',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(90,'','','malesuada','semper.pretium@netusetmalesuada.com',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(91,'','','Donec','libero.Integer.in@semelitpharetra.ca',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(92,'','','sapien.','tincidunt@metus.com',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(93,'','','sit','auctor.quis.tristique@nequeMorbiquis.edu',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(94,'','','sagittis','feugiat.Sed@nulla.net',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(95,'','','luctus','Cum@lobortis.org',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(96,'','','Phasellus','dictum@Nullamutnisi.ca',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(97,'','','Aliquam','consectetuer.euismod.est@nuncid.edu',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(98,'','','a,','mi@loremvehiculaet.org',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(99,'','','venenatis','Integer.urna@montesnascetur.com',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(100,'','','arcu.','nunc.interdum@ullamcorperviverra.net',NULL,NULL,NULL,NULL,NULL,NULL,'0000-00-00 00:00:00','0000-00-00 00:00:00'),(101,'TestFirstAgain','TestLast','test','test@test.com','Tes','','Test','WA','12345','United States','0000-00-00 00:00:00','0000-00-00 00:00:00');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userpass`
--

DROP TABLE IF EXISTS `userpass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userpass` (
  `Username` varchar(15) NOT NULL,
  `Password` varchar(15) NOT NULL,
  PRIMARY KEY (`Username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userpass`
--

LOCK TABLES `userpass` WRITE;
/*!40000 ALTER TABLE `userpass` DISABLE KEYS */;
INSERT INTO `userpass` VALUES ('chris','tran'),('filip','todorovic');
/*!40000 ALTER TABLE `userpass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userrole`
--

DROP TABLE IF EXISTS `userrole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userrole` (
  `Username` varchar(15) NOT NULL,
  `Rolename` varchar(15) NOT NULL,
  PRIMARY KEY (`Username`,`Rolename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userrole`
--

LOCK TABLES `userrole` WRITE;
/*!40000 ALTER TABLE `userrole` DISABLE KEYS */;
INSERT INTO `userrole` VALUES ('chris','student'),('filip','student');
/*!40000 ALTER TABLE `userrole` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-03-12 19:44:16
