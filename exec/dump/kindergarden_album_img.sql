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
-- Table structure for table `album_img`
--

DROP TABLE IF EXISTS `album_img`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `album_img` (
  `albumId` int DEFAULT NULL,
  `photoId` varchar(100) NOT NULL,
  PRIMARY KEY (`photoId`),
  KEY `album_img_ibfk_1_idx` (`albumId`),
  CONSTRAINT `album_img_ibfk_1` FOREIGN KEY (`albumId`) REFERENCES `album` (`albumId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `album_img`
--

LOCK TABLES `album_img` WRITE;
/*!40000 ALTER TABLE `album_img` DISABLE KEYS */;
INSERT INTO `album_img` VALUES (1,'album/김장1_1629123852795.jpeg'),(1,'album/김장2_1629123852839.jpeg'),(1,'album/김장3_1629123852840.jpeg'),(2,'album/단오1_1629123885510.jpeg'),(2,'album/단오2_1629123885514.jpeg'),(2,'album/단오3_1629123885516.jpeg'),(2,'album/단오4_1629123885519.jpeg'),(2,'album/단오5_1629123885522.jpeg'),(2,'album/단오7_1629123885529.jpeg'),(2,'album/단오8_1629123885534.jpeg'),(3,'album/등산1_1629123910304.jpeg'),(3,'album/등산2_1629123910306.jpeg'),(4,'album/바깥놀이1_1629123928043.jpeg'),(4,'album/바깥놀이2_1629123928046.png'),(5,'album/산속체험1_1629123962490.png'),(5,'album/산속체험2_1629123962491.jpeg'),(6,'album/산책1_1629124027272.jpeg'),(6,'album/산책2_1629124027275.jpeg'),(6,'album/산책3_1629124027277.jpeg'),(7,'album/실내놀이1_1629124053369.jpeg'),(7,'album/실내놀이2_1629124053371.jpeg'),(7,'album/실내놀이3_1629124053372.jpeg'),(8,'album/아나바다_1629124069158.jpeg'),(8,'album/아나바다2_1629124069160.jpeg'),(8,'album/아나바다3_1629124069163.jpeg'),(8,'album/아나바다4_1629124069164.jpeg'),(9,'album/_1629124149227.jpeg'),(9,'album/어린이날1_1629124149228.jpeg'),(9,'album/어린이날2_1629124149229.jpeg'),(9,'album/어린이날3_1629124149230.jpeg'),(9,'album/어린이날5_1629124149232.jpeg'),(9,'album/어린이날7_1629124149233.jpeg'),(10,'album/추석1_1629124176925.jpeg'),(10,'album/추석2_1629124176930.jpeg'),(10,'album/추석3_1629124176932.jpeg'),(10,'album/추석4_1629124176936.jpeg'),(10,'album/추석5_1629124176940.jpeg'),(10,'album/추석6_1629124176943.jpeg'),(11,'album/캠핑1_1629124198021.jpeg'),(11,'album/캠핑2_1629124198023.jpeg'),(11,'album/캠핑3_1629124198024.jpeg'),(11,'album/캠핑4_1629124198026.jpeg'),(11,'album/캠핑5_1629124198027.jpeg'),(12,'album/크리스마스1_1629124213055.jpeg'),(12,'album/크리스마스2_1629124213056.jpeg'),(13,'album/학예회1_1629124229185.jpeg'),(13,'album/학예회2_1629124229188.jpeg'),(13,'album/학예회준비1_1629124229189.jpeg'),(15,'album/화전만들기1_1629124271733.jpeg'),(15,'album/화전만들기2_1629124271735.jpeg'),(15,'album/화전만들기3_1629124271737.jpeg'),(15,'album/화전만들기4_1629124271739.jpeg'),(15,'album/화전만들기5_1629124271741.jpeg'),(16,'album/환경보호1_1629124283830.jpeg'),(16,'album/환경보호2_1629124283831.jpeg'),(16,'album/환경보호3_1629124283833.jpeg'),(16,'album/환경보호4_1629124283834.jpeg');
/*!40000 ALTER TABLE `album_img` ENABLE KEYS */;
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
