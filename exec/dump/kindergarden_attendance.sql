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
-- Table structure for table `attendance`
--

DROP TABLE IF EXISTS `attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendance` (
  `date` date NOT NULL DEFAULT (curdate()),
  `userId` varchar(15) NOT NULL,
  `attend` int DEFAULT '1',
  PRIMARY KEY (`date`,`userId`),
  KEY `userId` (`userId`),
  CONSTRAINT `attendance_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance`
--

LOCK TABLES `attendance` WRITE;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
INSERT INTO `attendance` VALUES ('2021-08-01','ssafy000',1),('2021-08-01','ssafy111',1),('2021-08-01','ssafy222',1),('2021-08-01','ssafy333',1),('2021-08-01','ssafy444',1),('2021-08-01','ssafy555',1),('2021-08-01','ssafy666',1),('2021-08-01','ssafy777',1),('2021-08-01','ssafy888',1),('2021-08-01','ssafy999',1),('2021-08-02','ssafy000',1),('2021-08-02','ssafy111',1),('2021-08-02','ssafy222',1),('2021-08-02','ssafy333',1),('2021-08-02','ssafy444',1),('2021-08-02','ssafy555',1),('2021-08-02','ssafy666',1),('2021-08-02','ssafy777',1),('2021-08-02','ssafy888',1),('2021-08-02','ssafy999',1),('2021-08-03','ssafy000',1),('2021-08-03','ssafy111',1),('2021-08-03','ssafy222',1),('2021-08-03','ssafy333',1),('2021-08-03','ssafy444',1),('2021-08-03','ssafy555',1),('2021-08-03','ssafy666',1),('2021-08-03','ssafy777',1),('2021-08-03','ssafy888',1),('2021-08-03','ssafy999',1),('2021-08-04','ssafy000',1),('2021-08-04','ssafy111',1),('2021-08-04','ssafy222',1),('2021-08-04','ssafy333',1),('2021-08-04','ssafy444',1),('2021-08-04','ssafy555',1),('2021-08-04','ssafy666',1),('2021-08-04','ssafy777',1),('2021-08-04','ssafy888',1),('2021-08-04','ssafy999',1),('2021-08-05','ssafy000',1),('2021-08-05','ssafy111',1),('2021-08-05','ssafy222',1),('2021-08-05','ssafy333',1),('2021-08-05','ssafy444',1),('2021-08-05','ssafy555',1),('2021-08-05','ssafy666',1),('2021-08-05','ssafy777',1),('2021-08-05','ssafy888',1),('2021-08-05','ssafy999',1),('2021-08-06','ssafy000',1),('2021-08-06','ssafy111',1),('2021-08-06','ssafy222',1),('2021-08-06','ssafy333',1),('2021-08-06','ssafy444',1),('2021-08-06','ssafy555',1),('2021-08-06','ssafy666',1),('2021-08-06','ssafy777',1),('2021-08-06','ssafy888',1),('2021-08-06','ssafy999',1),('2021-08-07','ssafy000',1),('2021-08-07','ssafy111',1),('2021-08-07','ssafy222',1),('2021-08-07','ssafy333',1),('2021-08-07','ssafy444',1),('2021-08-07','ssafy555',1),('2021-08-07','ssafy666',1),('2021-08-07','ssafy777',1),('2021-08-07','ssafy888',1),('2021-08-07','ssafy999',1),('2021-08-08','ssafy000',1),('2021-08-08','ssafy111',1),('2021-08-08','ssafy222',1),('2021-08-08','ssafy333',1),('2021-08-08','ssafy444',1),('2021-08-08','ssafy555',1),('2021-08-08','ssafy666',1),('2021-08-08','ssafy777',1),('2021-08-08','ssafy888',1),('2021-08-08','ssafy999',1),('2021-08-09','ssafy000',1),('2021-08-09','ssafy111',1),('2021-08-09','ssafy222',1),('2021-08-09','ssafy333',1),('2021-08-09','ssafy444',1),('2021-08-09','ssafy555',1),('2021-08-09','ssafy666',1),('2021-08-09','ssafy777',1),('2021-08-09','ssafy888',1),('2021-08-09','ssafy999',1),('2021-08-10','ssafy000',1),('2021-08-10','ssafy111',1),('2021-08-10','ssafy222',1),('2021-08-10','ssafy333',1),('2021-08-10','ssafy444',1),('2021-08-10','ssafy555',1),('2021-08-10','ssafy666',1),('2021-08-10','ssafy777',1),('2021-08-10','ssafy888',1),('2021-08-10','ssafy999',1),('2021-08-11','ssafy000',1),('2021-08-11','ssafy111',1),('2021-08-11','ssafy222',1),('2021-08-11','ssafy333',1),('2021-08-11','ssafy444',1),('2021-08-11','ssafy555',1),('2021-08-11','ssafy666',1),('2021-08-11','ssafy777',1),('2021-08-11','ssafy888',1),('2021-08-11','ssafy999',1),('2021-08-12','ssafy000',1),('2021-08-12','ssafy111',1),('2021-08-12','ssafy222',1),('2021-08-12','ssafy333',1),('2021-08-12','ssafy444',1),('2021-08-12','ssafy555',1),('2021-08-12','ssafy666',1),('2021-08-12','ssafy777',1),('2021-08-12','ssafy888',1),('2021-08-12','ssafy999',1),('2021-08-13','ssafy000',1),('2021-08-13','ssafy111',1),('2021-08-13','ssafy222',1),('2021-08-13','ssafy333',1),('2021-08-13','ssafy444',1),('2021-08-13','ssafy555',1),('2021-08-13','ssafy666',1),('2021-08-13','ssafy777',1),('2021-08-13','ssafy888',1),('2021-08-13','ssafy999',1),('2021-08-14','ssafy000',1),('2021-08-14','ssafy111',1),('2021-08-14','ssafy222',1),('2021-08-14','ssafy333',1),('2021-08-14','ssafy444',1),('2021-08-14','ssafy555',1),('2021-08-14','ssafy666',1),('2021-08-14','ssafy777',1),('2021-08-14','ssafy888',1),('2021-08-14','ssafy999',1),('2021-08-15','ssafy000',1),('2021-08-15','ssafy111',1),('2021-08-15','ssafy222',1),('2021-08-15','ssafy333',1),('2021-08-15','ssafy444',1),('2021-08-15','ssafy555',1),('2021-08-15','ssafy666',1),('2021-08-15','ssafy777',1),('2021-08-15','ssafy888',1),('2021-08-15','ssafy999',1),('2021-08-16','ssafy000',1),('2021-08-16','ssafy111',1),('2021-08-16','ssafy222',1),('2021-08-16','ssafy333',1),('2021-08-16','ssafy444',1),('2021-08-16','ssafy555',1),('2021-08-16','ssafy666',1),('2021-08-16','ssafy777',1),('2021-08-16','ssafy888',1),('2021-08-16','ssafy999',1),('2021-08-17','ssafy000',0),('2021-08-17','ssafy111',1),('2021-08-17','ssafy222',0),('2021-08-17','ssafy333',1),('2021-08-17','ssafy444',1),('2021-08-17','ssafy555',0),('2021-08-17','ssafy666',1),('2021-08-17','ssafy777',0),('2021-08-17','ssafy888',1),('2021-08-17','ssafy999',1),('2021-08-18','ssafy000',1),('2021-08-18','ssafy111',1),('2021-08-18','ssafy222',1),('2021-08-18','ssafy333',1),('2021-08-18','ssafy444',1),('2021-08-18','ssafy555',1),('2021-08-18','ssafy666',1),('2021-08-18','ssafy777',1),('2021-08-18','ssafy888',1),('2021-08-18','ssafy999',1);
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-08-18 15:22:34
