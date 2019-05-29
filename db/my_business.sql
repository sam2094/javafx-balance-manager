CREATE DATABASE  IF NOT EXISTS `my_business` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `my_business`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: my_business
-- ------------------------------------------------------
-- Server version	5.5.37

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
-- Table structure for table `expense`
--

DROP TABLE IF EXISTS `expense`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expense` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `note` varchar(250) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `status` varchar(45) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expense`
--

LOCK TABLES `expense` WRITE;
/*!40000 ALTER TABLE `expense` DISABLE KEYS */;
INSERT INTO `expense` VALUES (29,'Buy TV',700,'2019-03-27',9,'1'),(30,'Fix motor',200,'2019-03-25',10,'1'),(31,'Fix lamp',70,'2019-03-27',10,'1'),(32,'Fitness',50,'2019-03-27',11,'1'),(33,'Buy Protein',80,'2019-03-27',11,'1'),(34,'Buy present',500,'2019-03-27',12,'1'),(35,'Buy meat',50,'2019-04-06',9,'1'),(36,'Buy vacoom cleaner',320,'2019-04-05',9,'1'),(37,'Go to theatre',50,'2019-04-01',12,'1'),(38,'Pay communal',55,'2019-04-06',9,'1'),(39,'Go to park',45,'2019-04-06',12,'1'),(40,'Buy comp',250,'2019-04-11',9,'1'),(41,'Buy phone',100,'2019-04-11',9,'1'),(42,'Clean',5,'2019-04-11',10,'1');
/*!40000 ALTER TABLE `expense` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expense_category`
--

DROP TABLE IF EXISTS `expense_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expense_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expense_category`
--

LOCK TABLES `expense_category` WRITE;
/*!40000 ALTER TABLE `expense_category` DISABLE KEYS */;
INSERT INTO `expense_category` VALUES (9,'Home',1),(10,'Auto',1),(11,'Sport',1),(12,'Family',1);
/*!40000 ALTER TABLE `expense_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `income`
--

DROP TABLE IF EXISTS `income`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `income` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `note` varchar(250) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `income`
--

LOCK TABLES `income` WRITE;
/*!40000 ALTER TABLE `income` DISABLE KEYS */;
INSERT INTO `income` VALUES (33,'Main Job',800,'2019-03-01',14,1),(34,'Create Mobile App',450,'2019-03-27',15,1),(35,'Developiya',600,'2019-03-10',16,1),(36,'Football',120,'2019-03-27',17,1),(37,'Create web-service',1000,'2019-04-03',15,1),(38,'Roblox',1500,'2019-04-03',17,1);
/*!40000 ALTER TABLE `income` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `income_category`
--

DROP TABLE IF EXISTS `income_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `income_category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `income_category`
--

LOCK TABLES `income_category` WRITE;
/*!40000 ALTER TABLE `income_category` DISABLE KEYS */;
INSERT INTO `income_category` VALUES (14,'Salary',1),(15,'Freelance',1),(16,'Course',1),(17,'Game',1);
/*!40000 ALTER TABLE `income_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plan`
--

DROP TABLE IF EXISTS `plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `total_amount` int(11) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plan`
--

LOCK TABLES `plan` WRITE;
/*!40000 ALTER TABLE `plan` DISABLE KEYS */;
INSERT INTO `plan` VALUES (73,'Plan 1','2019-03-27','2019-04-03',1900,9,600,1),(74,'Plan 1','2019-03-27','2019-04-03',1900,10,200,1),(75,'Plan 1','2019-03-27','2019-04-03',1900,11,100,1),(76,'Plan 1','2019-03-27','2019-04-03',1900,12,1000,1),(77,'Plan 2','2019-03-27','2019-04-03',3230,9,1500,1),(78,'Plan 2','2019-03-27','2019-04-03',3230,10,800,1),(79,'Plan 2','2019-03-27','2019-04-03',3230,11,130,1),(80,'Plan 2','2019-03-27','2019-04-03',3230,12,800,1),(81,'Plan 3','2019-03-27','2019-03-30',18000,9,2000,1),(82,'Plan 3','2019-03-27','2019-03-30',18000,10,3000,1),(83,'Plan 3','2019-03-27','2019-03-30',18000,11,5000,1),(84,'Plan 3','2019-03-27','2019-03-30',18000,12,8000,1),(85,'Plan Aprel','2019-04-01','2019-04-06',480,9,80,1),(86,'Plan Aprel','2019-04-01','2019-04-06',480,10,100,1),(87,'Plan Aprel','2019-04-01','2019-04-06',480,11,100,1),(88,'Plan Aprel','2019-04-01','2019-04-06',480,12,200,1),(89,'Test Plan','2019-04-11','2019-04-13',1500,9,500,1),(90,'Test Plan','2019-04-11','2019-04-13',1500,10,300,1),(91,'Test Plan','2019-04-11','2019-04-13',1500,11,100,1),(92,'Test Plan','2019-04-11','2019-04-13',1500,12,600,1),(93,'Group C Plan','2019-04-14','2019-04-20',1200,9,150,1),(94,'Group C Plan','2019-04-14','2019-04-20',1200,10,250,1),(95,'Group C Plan','2019-04-14','2019-04-20',1200,11,350,1),(96,'Group C Plan','2019-04-14','2019-04-20',1200,12,450,1),(97,'Plan 1','2019-04-14','2019-04-20',400,9,100,1),(98,'Plan 1','2019-04-14','2019-04-20',400,10,100,1),(99,'Plan 1','2019-04-14','2019-04-20',400,11,100,1),(100,'Plan 1','2019-04-14','2019-04-20',400,12,100,1),(101,'Plan 1','2019-04-01','2019-04-13',2800,9,200,1),(102,'Plan 1','2019-04-01','2019-04-13',2800,10,1000,1),(103,'Plan 1','2019-04-01','2019-04-13',2800,11,600,1),(104,'Plan 1','2019-04-01','2019-04-13',2800,12,1000,1);
/*!40000 ALTER TABLE `plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `surname` varchar(45) DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'a','a','a','a','1');
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

-- Dump completed on 2019-04-18 12:54:30
