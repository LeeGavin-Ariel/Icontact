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
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedule` (
  `scheduleId` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `content` text,
  `createDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `scheduleImgUrl` varchar(100) DEFAULT NULL,
  `classCode` varchar(6) DEFAULT NULL,
  `userId` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`scheduleId`),
  KEY `schedule_ibfk_1_idx` (`userId`),
  KEY `schedule_ibfk_2_idx` (`classCode`),
  CONSTRAINT `schedule_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`),
  CONSTRAINT `schedule_ibfk_2` FOREIGN KEY (`classCode`) REFERENCES `kinder_class` (`classCode`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
INSERT INTO `schedule` VALUES (1,'2021년 3월 일정입니다','싸피유치원 2021년 3월 일정 안내드립니다','2021-03-01 13:09:43','schedule/3월 일정_1629119382675.png','100001','sfyteacher1'),(2,'2021년 4월 일정입니다','싸피유치원 2021년 4월 일정 안내드립니다','2021-04-01 13:10:10','schedule/4월 일정_1629119409850.png','100001','sfyteacher1'),(3,'2021년 5월 일정입니다','싸피유치원 2021년 5월 일정 안내드립니다','2021-05-03 13:10:34','schedule/5월 일정_1629119434001.png','100001','sfyteacher1'),(4,'2021년 6월 일정입니다','싸피유치원 2021년 6월 일정 안내드립니다','2021-06-01 13:10:58','schedule/6월일정_1629119457392.png','100001','sfyteacher1'),(5,'2021년 7월 일정입니다','싸피유치원 2021년 7월 일정 안내드립니다','2021-07-01 13:11:14','schedule/7월일정_1629119473400.png','100001','sfyteacher1'),(6,'2021년 8월 일정입니다','싸피유치원 2021년 8월 일정 안내드립니다','2021-08-02 13:11:33','schedule/8월 일정_1629119492576.png','100001','sfyteacher1');
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
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
