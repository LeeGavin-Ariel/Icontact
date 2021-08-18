-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: i5a304.p.ssafy.io    Database: kindergarden
-- ------------------------------------------------------
-- Server version	8.0.26-0ubuntu0.20.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `returnhome`
--

DROP TABLE IF EXISTS `returnhome`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `returnhome` (
  `rhId` int NOT NULL AUTO_INCREMENT,
  `rhDate` varchar(8) DEFAULT NULL,
  `rhTime` varchar(10) DEFAULT NULL,
  `guardian` varchar(10) DEFAULT NULL,
  `guardianTel` varchar(11) DEFAULT NULL,
  `emergency` varchar(10) DEFAULT NULL,
  `emergencyTel` varchar(11) DEFAULT NULL,
  `createDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `userId` varchar(15) NOT NULL,
  PRIMARY KEY (`rhId`),
  KEY `returnhome_ibfk_1_idx` (`userId`),
  CONSTRAINT `returnhome_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `returnhome`
--

LOCK TABLES `returnhome` WRITE;
/*!40000 ALTER TABLE `returnhome` DISABLE KEYS */;
INSERT INTO `returnhome` VALUES (1,'8월 13일','오후 3시','할머니','01084658132','엄마','01055461324','2021-08-16 14:44:57','ssafy222'),(2,'08월 19일','오후 04시 30분','할머니','01012345678','엄마','01098765432','2021-08-16 15:07:23','ssafy000'),(3,'08월 17일','오후 7시 30분','할머니','01012345678','엄마','01098765432','2021-08-16 15:12:24','ssafy111');
/*!40000 ALTER TABLE `returnhome` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-08-18 10:24:25
