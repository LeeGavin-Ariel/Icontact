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
-- Table structure for table `notebook`
--

DROP TABLE IF EXISTS `notebook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notebook` (
  `noteId` int NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `noteImgUrl` varchar(100) DEFAULT '',
  `writerId` varchar(15) NOT NULL,
  `targetId` varchar(15) NOT NULL,
  PRIMARY KEY (`noteId`),
  KEY `writerId` (`writerId`),
  KEY `targetId` (`targetId`),
  CONSTRAINT `notebook_ibfk_1` FOREIGN KEY (`writerId`) REFERENCES `user` (`userId`),
  CONSTRAINT `notebook_ibfk_2` FOREIGN KEY (`targetId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notebook`
--

LOCK TABLES `notebook` WRITE;
/*!40000 ALTER TABLE `notebook` DISABLE KEYS */;
INSERT INTO `notebook` VALUES (1,'친구들과 협동놀이 하는 서준이','오늘 친구들과 다같이 협동하는 놀이를 했습니다.\n친구들과 다투지 않고 이야기하면서\n차근차근 쌓아올리는 모습이 너무 예뻤습니다.\n놀이가 끝나고 나서 바로바로 치우자고\n주도하기도 했답니다.\n항상 친구들과 잘 어울리는 착한 서준이지만\n오늘은 특히 더 특했어요\n잘했다고 집에서 칭찬 많이해주세요~~','2021-01-15 13:58:12','notebook/1_1629122292290.jpeg','sfyteacher1','ssafy000'),(2,'지안이와 동백꽃','뒷산에 올라가서 꽃을 보고 왔습니다.\n힘든 기색없이 씩씩하게 올라가더라구요.\n친구랑 함께 동백을 한아름 따다가\n이렇게 하트를 만들어서 해맑게 웃더라구요.\n지안이가 만든 하트 동백 사진 보내드려요~!!','2021-02-03 13:59:07','notebook/2_1629122406619.jpeg','sfyteacher1','ssafy555'),(3,'하준이의 체험학습 모습','체험학습을 다녀왔습니다.\n이전에는 흙을 만지기 싫어했었는데\n오늘은 아주 잘 만지고 씩씩하게 털어내는 모습이었어요.\n자연과 많이 친해진 것 같습니다.\n친구들이랑 라인을 따라 걸으면서 장난치기도 했어요.\n정말 신나게 놀아서 조금 피곤해할 수도 있을 것 같습니다.','2021-03-10 14:00:39','notebook/3_1629122439690.jpeg','sfyteacher1','ssafy111'),(4,'도윤이의 즐거운 소풍','조금 쌀쌀한 날씨였지만 아이들 조심히 잘 다녀왔습니다.\n어쩜 이렇게 귀여운지 모르겠어요~~\n친구들과 나란히 앉아서 이야기도 나누고 뛰어도 다니고\n준비해주신 간식과 도시락도 깨끗하게 먹었습니다!\n\n내일 준비물 : 비누 한조각\n작아서 쓰기 불편해진 비누를 전자렌지로 부풀게 하는\n과학시간에 사용할 예정입니다.','2021-03-10 14:01:22','notebook/4_1629122482200.jpeg','sfyteacher1','ssafy222'),(5,'토마토 심는 시우','오늘은 유치원 앞마당에서 토마토를 심어봤습니다.\n집에서 해본 적이 있다고 앞장서서 하더라구요!\n항상 적극적이고 씩씩한 시우예요.\n다른 친구들도 열심히 도와줬습니다. 칭찬 많이 해주세요.\n토마토 심기가 끝난 후에는 소꿉놀이를 했습니다.\n작은 손으로 꼬물꼬물 열심히 엄마, 아빠 흉내를 내는\n시우의 모습이 너무 귀여웠어요','2021-04-20 14:02:18','notebook/5_1629122538714.jpeg','sfyteacher1','ssafy333'),(6,'하윤이와 닭 친구들','오늘 하윤이가 처음으로 닭장에 다가갔어요!\n언제나 무섭다고 멀리서 쳐다보기만 했는데\n사료도 넣어주고 재잘재잘 이야기하는 모습이\n하윤이도 많이 자랐구나 싶었습니다.\n하윤이가 집가서 자랑한다고 했는데\n많이 많이 들어주세요!!','2021-05-16 14:03:10','notebook/6_1629122590128.jpeg','sfyteacher1','ssafy666'),(7,'서아가 좋아하는 그림 놀이','서아가 제일 좋아하는 그림그리기를 했습니다.\n색연필, 크레파스로 멋진 그림을 그리네요!\n서아가 그림을 너무 좋아하다보니\n다른 친구들도 자연스럽게 그림 활동에 관심을 보이고 있어요.\n가방에 서아가 그린 그림 같이 보내드릴게요~\n내일은 야외활동이 있고 비가 올 수도 있다고 하니\n우비, 장화 챙겨서 보내주세요~','2021-06-19 14:03:48','notebook/7_1629122628565.jpeg','sfyteacher1','ssafy777'),(8,'빗속에서 하은이','비가 많이 오지는 않았지만\n비오는 날 야외 활동이 상당히 즐거웠던 것 같습니다.\n물장구 치는 걸 너무 좋아하더라구요.\n오래 밖에 있지는 않았지만 집에서 따뜻하게 해주세요!','2021-06-25 14:04:25','notebook/8_1629122665654.jpeg','sfyteacher1','ssafy888'),(9,'은우의 젠가 놀이','오늘은 젠가 쌓기를 했습니다.\n은우가 놀랄정도로 잘했어요.\n저만큼 안정적으로 쌓을거라고 생각을 못했는데\n차분하게 은우 키의 두배 가까이 쌓더라구요.\n중간에 서준이의 장난으로 무너졌고\n많이 속상해했지만 서준이가 사과해서 잘 화해했습니다.\n너무 잘했다고 다음에 또 하면 된다고 토닥여주세요.','2021-07-10 14:05:34','notebook/9_1629122734077.jpeg','sfyteacher1','ssafy444'),(10,'지안이의 재활용 놀이','오늘은 재활용품으로 만드는 활동을 했습니다.\n휴지심으로 커다란 기린을 만들어서 타고 놀기도 했어요.\n지안이가 앞장서서 열심히 만들었는데도\n다른 친구들이 먼저 타볼 수 있도록 양보하더라구요.\n착한 우리 지안이 많이 칭찬해주세요.','2021-08-01 14:06:48','notebook/10_1629122808516.jpeg','sfyteacher1','ssafy555');
/*!40000 ALTER TABLE `notebook` ENABLE KEYS */;
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
