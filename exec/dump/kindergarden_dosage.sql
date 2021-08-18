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
-- Table structure for table `dosage`
--

DROP TABLE IF EXISTS `dosage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dosage` (
  `dosageId` int NOT NULL AUTO_INCREMENT,
  `symptom` varchar(50) DEFAULT NULL,
  `medicineType` varchar(30) DEFAULT NULL,
  `dosageVol` int DEFAULT NULL,
  `dosageCnt` int DEFAULT NULL,
  `dosageTime` varchar(30) DEFAULT NULL,
  `storage` varchar(30) DEFAULT NULL,
  `specialNote` varchar(100) DEFAULT NULL,
  `createDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `userId` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`dosageId`),
  KEY `userId_idx` (`userId`),
  CONSTRAINT `dosage_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dosage`
--

LOCK TABLES `dosage` WRITE;
/*!40000 ALTER TABLE `dosage` DISABLE KEYS */;
INSERT INTO `dosage` VALUES (1,'기침','물약',10,2,'오전 10시 / 오후 1시','냉장','한 숟가락씩 먹여주세요~','2021-08-16 14:43:18','ssafy000'),(2,'기침','가루약',5,1,'오후 1시','냉장','물에 타서 먹여주세요','2021-08-16 14:43:31','ssafy111'),(4,'감기','물약',1,1,'오전 10시 식후 30분','실온','알레르기 증상이 같이 보임','2021-08-17 06:04:46','ssafy111');
/*!40000 ALTER TABLE `dosage` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-08-18 10:24:26
