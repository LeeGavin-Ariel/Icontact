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
-- Table structure for table `album`
--

DROP TABLE IF EXISTS `album`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `album` (
  `albumId` int NOT NULL AUTO_INCREMENT,
  `title` varchar(50) DEFAULT NULL,
  `createDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `thumbnailUrl` varchar(100) DEFAULT NULL,
  `classCode` varchar(6) DEFAULT NULL,
  `writerId` varchar(15) DEFAULT NULL,
  `downloadUrl` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`albumId`),
  KEY `album_ibfk_1_idx` (`classCode`),
  KEY `album_ibfk_2_idx` (`writerId`),
  CONSTRAINT `album_ibfk_1` FOREIGN KEY (`classCode`) REFERENCES `kinder_class` (`classCode`),
  CONSTRAINT `album_ibfk_2` FOREIGN KEY (`writerId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `album`
--

LOCK TABLES `album` WRITE;
/*!40000 ALTER TABLE `album` DISABLE KEYS */;
INSERT INTO `album` VALUES (1,'김장체험','2019-12-10 14:24:13','album/김장1_1629123852795.jpeg','100001','sfyteacher1','album/1629123853213.zip'),(2,'단오날 전통체험','2020-05-05 14:24:46','album/단오3_1629123885516.jpeg','100001','sfyteacher1','album/1629123886069.zip'),(3,'관악산 등산','2020-05-20 14:25:10','album/등산1_1629123910304.jpeg','100001','sfyteacher1','album/1629123910483.zip'),(4,'야외체험','2020-06-03 14:25:28','album/바깥놀이1_1629123928043.jpeg','100001','sfyteacher1','album/1629123928380.zip'),(5,'산 속 자연체험','2020-06-25 14:26:02','album/산속체험2_1629123962491.jpeg','100001','sfyteacher1','album/1629123962787.zip'),(6,'야외 산책','2020-07-12 14:27:07','album/산책1_1629124027272.jpeg','100001','sfyteacher1','album/1629124027536.zip'),(7,'실내 활동','2020-07-25 14:27:33','album/실내놀이3_1629124053372.jpeg','100001','sfyteacher1','album/1629124053517.zip'),(8,'아나바다 체험','2020-08-15 14:27:49','album/아나바다_1629124069158.jpeg','100001','sfyteacher1','album/1629124069375.zip'),(9,'어린이날 행사','2020-08-25 14:29:09','album/어린이날1_1629124149228.jpeg','100001','sfyteacher1','album/1629124149582.zip'),(10,'추석 전통놀이','2020-09-20 14:29:37','album/추석5_1629124176940.jpeg','100001','sfyteacher1','album/1629124177245.zip'),(11,'캠핑 체험','2020-10-16 14:29:58','album/캠핑2_1629124198023.jpeg','100001','sfyteacher1','album/1629124198284.zip'),(12,'크리스마스 행사','2020-12-24 14:30:13','album/크리스마스2_1629124213056.jpeg','100001','sfyteacher1','album/1629124213423.zip'),(13,'학예회 활동','2020-12-30 14:30:29','album/학예회준비1_1629124229189.jpeg','100001','sfyteacher1','album/1629124229804.zip'),(15,'화전 만들기','2021-03-15 14:31:11','album/화전만들기1_1629124271733.jpeg','100001','sfyteacher1','album/1629124271961.zip'),(16,'환경 보호 체험','2021-05-25 14:31:24','album/환경보호1_1629124283830.jpeg','100001','sfyteacher1','album/1629124284140.zip');
/*!40000 ALTER TABLE `album` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-08-18 15:22:33
