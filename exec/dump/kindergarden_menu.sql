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
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu` (
  `menuId` int NOT NULL AUTO_INCREMENT,
  `amSnack` varchar(100) DEFAULT NULL,
  `amSnackImgUrl` varchar(100) DEFAULT NULL,
  `lunch` varchar(100) DEFAULT NULL,
  `lunchImgUrl` varchar(100) DEFAULT NULL,
  `pmSnack` varchar(100) DEFAULT NULL,
  `pmSnackImgUrl` varchar(100) DEFAULT NULL,
  `createDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `classCode` varchar(6) DEFAULT NULL,
  `userId` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`menuId`),
  KEY `menu_ibfk_1_idx` (`userId`),
  KEY `menu_ibfk_2_idx` (`classCode`),
  CONSTRAINT `menu_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`),
  CONSTRAINT `menu_ibfk_2` FOREIGN KEY (`classCode`) REFERENCES `kinder_class` (`classCode`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (1,'절편, 식혜','menu/1 오전 간식_1629119797224.jpeg','무콩나물밥, 유부미소된장국, 핫도그/케찹, 키위','menu/1 점심_1629119797462.jpg','고구마, 요거트','menu/1 오후 간식_1629119797526.jpeg','2021-08-06 13:16:37','100001','sfyteacher1'),(2,'약과, 수박주스','menu/2 오전간식_1629119857704.jpeg','잔치국수, 김가루주먹밥, 데리야끼닭꼬치, 파인애플','menu/2 점심_1629119857811.jpg','버섯야채죽','menu/2 오후 간식_1629119857911.jpeg','2021-08-09 13:17:38','100001','sfyteacher1'),(3,'크림파스타','menu/3 오전간식_1629119885352.jpeg','클로렐라밥, 건새우시금치된장국 ,수육, 오이양파무침, 배','menu/3 점심_1629119885719.jpg','찐 감자, 레모네이드','menu/3 오후 간식_1629119885897.jpeg','2021-08-10 13:18:06','100001','sfyteacher1'),(4,'화채','menu/4 오전간식_1629119922176.jpeg','완두콩밥, 육개장, 고등어구이, 연근엿장조림, 천혜향','menu/4 점심_1629119922354.jpg','슈크림빵, 딸기요거트','menu/4 오후 간식_1629119922480.jpeg','2021-08-11 13:18:42','100001','sfyteacher1'),(5,'빵, 요거트','menu/5 오전간식_1629119950534.jpeg','홍국쌀밥, 감자소고기된장국, 계란찜, 어묵야채볶음, 오이소박이','menu/5 점심_1629119950633.jpg','야채죽','menu/5 오후 간식_1629119950693.jpeg','2021-08-12 13:19:10','100001','sfyteacher1'),(6,'도넛, 사과주스','menu/6 오전 간식_1629119989181.jpeg','칼슘강화찹쌀밥, 한방닭곰탕, 임연수구이, 감자조림, 깍두기, 딸기','menu/6 점심_1629119989300.jpg','머쉬룸스프, 크루통','menu/6 오후 간식_1629119989384.jpeg','2021-08-13 13:19:49','100001','sfyteacher1'),(7,'오렌지, 참외','menu/7 오전 간식_1629120037167.jpeg','강황쌀밥, 어묵국, 소불고기, 포항초무침','menu/7 점심_1629120037310.jpg','크로와상, 레모네이드','menu/7 오후 간식_1629120037396.jpeg','2021-08-17 13:20:37','100001','sfyteacher1'),(8,'청포도, 귤','menu/8 오전 간식_1629120072648.jpeg','현미밥, 어묵국, 오징어김치전, 고구마순들깨볶음, 깻잎김치','menu/8 점심_1629120072796.jpg','옥수수스프, 크루통','menu/8 오후 간식_1629120072863.jpeg','2021-08-18 13:21:12','100001','sfyteacher1'),(9,'파인애플, 귤','menu/9 오전간식_1629120111238.jpeg','찰흑미밥, 쇠고기육개장, 삼치레몬장구이, 감자채볶음','menu/9 점심_1629120111349.jpg','와플, 오렌지주스','menu/9 오후 간식_1629120111504.jpeg','2021-08-19 13:21:51','100001','sfyteacher1'),(10,'참외, 방울토마토','menu/10 오전간식_1629120141105.jpeg','짜장밥, 팽이계란국, 생선까스, 석박지, 배','menu/10 점심_1629120141274.png','만두, 청포도주스','menu/10 오후 간식_1629120141422.jpeg','2021-08-20 13:22:21','100001','sfyteacher1');
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
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
