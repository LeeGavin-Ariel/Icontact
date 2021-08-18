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
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notice` (
  `noticeId` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `content` text,
  `createDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `noticeImgUrl` varchar(100) DEFAULT NULL,
  `classCode` varchar(6) DEFAULT NULL,
  `userId` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`noticeId`),
  KEY `notice_ibfk_1_idx` (`userId`),
  KEY `notice_ibfk_2_idx` (`classCode`),
  CONSTRAINT `notice_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`),
  CONSTRAINT `notice_ibfk_2` FOREIGN KEY (`classCode`) REFERENCES `kinder_class` (`classCode`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
INSERT INTO `notice` VALUES (1,'11월 가정통신문','싸피유치원 2020년 11월 가정통신문','2020-11-02 14:09:49','notice/11월 가정통신문_1629122989508.png','100001','sfyteacher1'),(2,'코로나로 인한 유치원 휴원','코로나로 인한 유치원 휴원안내 가정통신문','2020-11-09 14:10:45','notice/코로나 어린이집 휴원_1629123045153.jpeg','100001','sfyteacher1'),(3,'12월 가정통신문','싸피유치원 2020년 12월 가정통신문','2020-12-01 14:11:44','notice/12월 가정통신문_1629123104713.png','100001','sfyteacher1'),(4,'크리스마스 안내문','행복한 크리스마스 보내세요~','2020-12-18 14:13:26','notice/크리스마스_1629123206421.png','100001','sfyteacher1'),(5,'부모님 참여 수업 안내','싸피유치원 2021년 3월 부모님 참여 수업 안내문','2021-03-29 14:14:19','notice/부모참여수업_1629123259721.png','100001','sfyteacher1'),(6,'학부모 만족도 설문','싸피유치원 2021년 상반기 학부모 만족도 설문','2021-05-17 14:15:22','notice/부모 만족도 설문지_1629123322643.png','100001','sfyteacher1'),(7,'안전교육 캠페인 안내','싸피유치원 안전교육 캠페인 안내문','2021-05-19 14:16:20','notice/안전교육_1629123380041.png','100001','sfyteacher1'),(8,'온가족 여름캠핑 안내문','부모님과 함께 떠나는 온가족 여름캠핑 안내','2021-07-08 14:17:28','notice/캠핑장_1629123448746.png','100001','sfyteacher1'),(9,'물놀이  행사 안내문','싸피유치원 연령별 원내 물놀이 안내문','2021-07-15 14:18:21','notice/물놀이_1629123501911.png','100001','sfyteacher1'),(10,'2021년 8월 17일 공지사항','1. 소풍준비\n2. 알림장 준비\n\n^^','2021-08-17 01:35:15','notice/비료_1629164122490.png','100001','sfyteacher1');
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
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
