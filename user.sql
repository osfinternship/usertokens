-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: college
-- ------------------------------------------------------
-- Server version	5.6.28-log

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
-- Table structure for table `grades`
--

DROP TABLE IF EXISTS `grades`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grades` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  `grade1` double NOT NULL,
  `grade2` double NOT NULL,
  `average` double NOT NULL,
  `studentId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_studentId_id_idx` (`studentId`),
  CONSTRAINT `FK_studentId_id` FOREIGN KEY (`studentId`) REFERENCES `students` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=149 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grades`
--

LOCK TABLES `grades` WRITE;
/*!40000 ALTER TABLE `grades` DISABLE KEYS */;
INSERT INTO `grades` VALUES (2,'Math',5,7.3,6.15,2),(3,'Math',5,7.3,6.15,3),(4,'Math',5,7.3,6.15,4),(5,'Math',5,7.3,6.15,5),(6,'Math',5,7.3,6.15,6),(7,'Math',5,7.3,6.15,7),(8,'Math',5,7.3,6.15,8),(9,'Math',5,7.3,6.15,9),(10,'Math',5,7.3,6.15,10),(11,'Math',5,7.3,6.15,11),(12,'Math',5,7.3,6.15,12),(13,'Math',5,7.3,6.15,13),(14,'Math',5,7.3,6.15,14),(15,'Math',5,7.3,6.15,15),(16,'Math',5,7.3,6.15,16),(17,'Math',5,7.3,6.15,17),(18,'Math',5,7.3,6.15,18),(19,'Math',5,7.3,6.15,19),(20,'Math',5,7.3,6.15,20),(21,'Math',5,7.3,6.15,21),(22,'Math',5,7.3,6.15,22),(23,'Math',5,7.3,6.15,23),(24,'Math',5,7.3,6.15,24),(25,'Math',5,7.3,6.15,25),(26,'Math',5,7.3,6.15,26),(27,'Math',5,7.3,6.15,27),(28,'Math',5,7.3,6.15,28),(29,'Math',5,7.3,6.15,29),(31,'Math',5,7.3,6.15,31),(33,'Math',5,7.3,6.15,33),(35,'Math',5,7.3,6.15,35),(37,'Math',5,7.3,6.15,37),(39,'Math',5,7.3,6.15,39),(41,'Math',5,7.3,6.15,41),(43,'Math',5,7.3,6.15,43),(45,'Math',5,7.3,6.15,45),(47,'Math',5,7.3,6.15,47),(49,'Math',5,7.3,6.15,49),(51,'Math',5,7.3,6.15,51),(53,'Math',5,7.3,6.15,53),(55,'Math',5,7.3,6.15,55),(57,'Math',5,7.3,6.15,57),(59,'Math',5,7.3,6.15,59),(61,'Math',5,7.3,6.15,61),(63,'Math',5,7.3,6.15,63),(65,'Math',5,7.3,6.15,65),(67,'Math',5,7.3,6.15,67),(69,'Math',5,7.3,6.15,69),(71,'Math',5,7.3,6.15,71),(73,'Math',5,7.3,6.15,73),(75,'Math',5,7.3,6.15,75),(77,'Math',5,7.3,6.15,77),(79,'Math',5,7.3,6.15,79),(81,'Math',5,7.3,6.15,81),(83,'Math',5,7.3,6.15,83),(85,'Math',5,7.3,6.15,85),(87,'Math',5,7.3,6.15,87),(89,'Math',5,7.3,6.15,89),(91,'Math',5,7.3,6.15,91),(93,'Math',5,7.3,6.15,93),(95,'Math',5,7.3,6.15,95),(97,'Math',5,7.3,6.15,97),(99,'Math',5,7.3,6.15,99),(101,'Math',5,7.3,6.15,101),(103,'Math',5,7.3,6.15,103),(105,'Math',5,7.3,6.15,105),(107,'Math',5,7.3,6.15,107),(109,'Math',5,7.3,6.15,109),(111,'Math',5,7.3,6.15,111),(113,'Math',5,7.3,6.15,113),(115,'Math',5,7.3,6.15,115),(117,'Math',5,7.3,6.15,117),(119,'Math',5,7.3,6.15,119),(121,'Math',5,7.3,6.15,121),(123,'Math',5,7.3,6.15,123),(125,'Math',5,7.3,6.15,125),(127,'Math',5,7.3,6.15,128),(129,'Math',5,7.3,6.15,131),(131,'Math',5,7.3,6.15,133),(133,'Math',5,7.3,6.15,135),(135,'Math',5,7.3,6.15,137),(137,'Math',5,7.3,6.15,140),(139,'Math',5,7.3,6.15,142),(141,'Math',5,7.3,6.15,144),(142,'Math',5,7.3,6.15,148),(144,'Math',5,7.3,6.15,152),(146,'Math',5,7.3,6.15,154),(147,'Math',5,7.3,6.15,155);
/*!40000 ALTER TABLE `grades` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `students` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(15) NOT NULL,
  `surname` varchar(15) NOT NULL,
  `birthDate` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=157 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES (2,'Bill','Hakins','2016-02-04'),(3,'John','Lock','2016-02-04'),(4,'John','Lock','2016-02-04'),(5,'John','Lock','2016-02-04'),(6,'John','Lock','2016-02-04'),(7,'John','Lock','2016-02-04'),(8,'John','Lock','2016-02-04'),(9,'John','Lock','2016-02-04'),(10,'John','Lock','2016-02-04'),(11,'John','Lock','2016-02-04'),(12,'John','Lock','2016-02-04'),(13,'John','Lock','2016-02-04'),(14,'John','Lock','2016-02-04'),(15,'John','Lock','2016-02-04'),(16,'John','Lock','2016-02-04'),(17,'John','Lock','2016-02-04'),(18,'John','Lock','2016-02-04'),(19,'John','Lock','2016-02-04'),(20,'John','Lock','2016-02-04'),(21,'John','Lock','2016-02-04'),(22,'John','Lock','2016-02-04'),(23,'John','Lock','2016-02-04'),(24,'John','Lock','2016-02-04'),(25,'John','Lock','2016-02-04'),(26,'John','Lock','2016-02-04'),(27,'John','Lock','2016-02-04'),(28,'John','Lock','2016-02-04'),(29,'John','Lock','2016-02-04'),(31,'John','Lock','2016-02-04'),(33,'John','Lock','2016-02-04'),(35,'John','Lock','2016-02-04'),(37,'John','Lock','2016-02-04'),(39,'John','Lock','2016-02-04'),(41,'John','Lock','2016-02-04'),(43,'John','Lock','2016-02-04'),(45,'John','Lock','2016-02-04'),(47,'John','Lock','2016-02-04'),(49,'John','Lock','2016-02-04'),(51,'John','Lock','2016-02-04'),(53,'John','Lock','2016-02-04'),(55,'John','Lock','2016-02-04'),(57,'John','Lock','2016-02-04'),(59,'John','Lock','2016-02-05'),(61,'John','Lock','2016-02-05'),(63,'John','Lock','2016-02-05'),(65,'John','Lock','2016-02-05'),(67,'John','Lock','2016-02-05'),(69,'John','Lock','2016-02-05'),(71,'John','Lock','2016-02-05'),(73,'John','Lock','2016-02-05'),(75,'John','Lock','2016-02-05'),(77,'John','Lock','2016-02-05'),(79,'John','Lock','2016-02-05'),(81,'John','Lock','2016-02-05'),(83,'John','Lock','2016-02-05'),(85,'John','Lock','2016-02-05'),(87,'John','Lock','2016-02-05'),(89,'John','Lock','2016-02-05'),(91,'John','Lock','2016-02-05'),(93,'John','Lock','2016-02-05'),(95,'John','Lock','2016-02-05'),(97,'John','Lock','2016-02-05'),(99,'John','Lock','2016-02-05'),(101,'John','Lock','2016-02-05'),(103,'John','Lock','2016-02-05'),(105,'John','Lock','2016-02-05'),(107,'John','Lock','2016-02-05'),(109,'John','Lock','2016-02-05'),(111,'John','Lock','2016-02-05'),(113,'John','Lock','2016-02-05'),(115,'John','Lock','2016-02-05'),(117,'John','Lock','2016-02-05'),(119,'John','Lock','2016-02-05'),(121,'John','Lock','2016-02-05'),(123,'John','Lock','2016-02-05'),(125,'John','Lock','2016-02-05'),(127,'Lock','Lock','2016-02-05'),(128,'John','Lock','2016-02-05'),(130,'dsadsad','dsadsad','2016-02-05'),(131,'John','Lock','2016-02-05'),(133,'John','Lock','2016-02-05'),(135,'John','Lock','2016-02-05'),(137,'John','Lock','2016-02-05'),(139,'neweee','neweee','2016-02-05'),(140,'John','Lock','2016-02-05'),(142,'John','Lock','2016-02-05'),(144,'John','Lock','2016-02-05'),(146,'Lock12112','Lock12112','2016-02-05'),(147,'Lockx','Lockx','2016-02-05'),(148,'John','Lock','2016-02-05'),(150,'Lockzaqzaqzaq','Lockzaqzaqzaq','2016-02-05'),(151,'new_boris','new_boris','2016-02-05'),(152,'John','Lock','2016-02-05'),(154,'John','Lock','2016-02-05'),(155,'John','Lock','2016-02-07');
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `password` varchar(60) NOT NULL,
  `role` varchar(15) DEFAULT 'user',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'ol','1111','user','2011-12-31 22:00:00','2013-12-31 22:00:00',1),(2,'igor','0000','user','2013-12-31 22:00:00','2013-12-31 22:00:00',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-08  0:12:36
