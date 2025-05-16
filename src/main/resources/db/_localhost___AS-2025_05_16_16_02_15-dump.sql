-- MySQL dump 10.13  Distrib 8.4.4, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: community
-- ------------------------------------------------------
-- Server version	8.4.4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board` (
  `board_id` int NOT NULL AUTO_INCREMENT,
  `class_id` int NOT NULL,
  `user_id` int NOT NULL,
  `title` varchar(100) NOT NULL,
  `context` text,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`board_id`),
  KEY `class_id` (`class_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `board_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`),
  CONSTRAINT `board_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
INSERT INTO `board` (`board_id`, `class_id`, `user_id`, `title`, `context`, `created_at`, `updated_at`, `deleted_at`) VALUES (1,1,6,'🏃‍♀️장소 변경! 합정→망원 한강','이번 주는 망원한강공원에서 뛸게요! 합정역 2번 출구 앞에서 모입시다ㅎㅎ 날씨 미쳤음🔥','2025-05-15 13:33:25','2025-05-15 13:33:25',NULL),(2,2,3,'🍻책맥 번개 모집!','갑자기 맥주 땡기는 날... 오늘 밤 8시 성수 트레바리 앞에서 번개 할 사람?','2025-05-15 13:33:25','2025-05-15 13:33:25',NULL),(3,3,5,'🍱밀프렙 재료 리스트 공유해요','이번주는 닭가슴살, 두부, 파프리카, 고구마 갑니다~ 간장 소스는 제가 챙길게요 ㅎㅎ','2025-05-15 13:33:25','2025-05-15 13:33:25',NULL),(4,4,3,'🍷MT 공지: 와인과 함께 파주 글램핑?','불어 스터디 MT 어때요?! 파주 근교 글램핑장 예약 중입니다. 6/1~6/2로 생각중입니다!','2025-05-15 13:33:25','2025-05-15 13:33:25',NULL),(5,5,3,'✈️다음 목적지 의견 받아요~','춘천? 속초? 어디든 좋아요! 다들 댓글로 가고싶은 데 적어주세요!','2025-05-15 13:33:25','2025-05-15 13:33:25',NULL),(6,6,14,'🎲장소 바뀜! 신촌 → 연남','연남에 새로 생긴 카페에서 해요! 보드게임 모임 장소 바뀌었으니 헷갈리지 말기~','2025-05-15 13:33:25','2025-05-15 13:33:25',NULL),(7,7,10,'🎻조용한 카페 추천받아요','다음엔 클래식 들으면서 노트 쓸 수 있는 조용한 카페에서 해볼까 해요!','2025-05-15 13:33:25','2025-05-15 13:33:25',NULL),(8,8,7,'💻프로젝트 과제 안내','함께 만들 깃허브 페이지 초안 나왔어요~ 다음 시간에 코드 짜봐요!','2025-05-15 13:33:25','2025-05-15 13:33:25',NULL),(9,9,17,'🎸1곡 완주자 후기 공개!','태연의 <그대라는 시> 성공했습니다 여러분... 뿌듯 그 자체. 다음 주는 자이언티 가볼게요~','2025-05-15 13:33:25','2025-05-15 13:33:25',NULL),(10,10,10,'🎨드로잉 장소 변경!','비와서 야외 취소ㅠ 이번주는 연남동 마카롱 카페에서 만나요~','2025-05-15 13:33:25','2025-05-15 13:33:25',NULL),(11,11,1,'📸포토워크 일정 공지','이번 주 토요일! 서울숲에서 감성샷 찍어요. 필름카메라 있는 분 챙겨오셔요~','2025-05-15 13:33:25','2025-05-15 13:33:25',NULL),(12,12,13,'🎬자막 편집 팁 공유해요!','캡컷에서 자막 넣을 때 예쁘게 보이게 하는 법 정리했어요~ 프레임 강조 꿀팁 있음!','2025-05-15 13:33:25','2025-05-15 13:33:25',NULL),(13,13,15,'🚶‍♀️미라클모닝 챌린지 시작🔥','매일 아침 6시, 루틴 인증해요. 못 일어나면 벌금 500원 ㅋㅋㅋ','2025-05-15 13:33:25','2025-05-15 13:33:25',NULL),(14,14,20,'🐶메이트 추가모집! 중형견 환영💖','이번주 일요일 반포 한강 산책! 중형견도 환영합니다! 같이 걷자요~','2025-05-15 13:33:25','2025-05-15 13:33:25',NULL),(15,15,7,'🥕장보기 리스트 공유해요','두유, 토마토, 병아리콩 꼭 챙기세여~ 퀴노아는 제가 가져갈게요~','2025-05-15 13:33:25','2025-05-15 13:33:25',NULL),(16,16,18,'🐾날씨 너무 좋아요 번개 산책 가실?','오늘 저녁 7시 은평공원 산책 번개 어때요? 저희 마루도 데려가요!','2025-05-15 13:33:25','2025-05-15 13:33:25',NULL),(17,17,2,'🎨이번 전시회 장소 바뀜!','성북예술창작터 → 아르코미술관! 내부 더 좋아요. 헷갈리지 않게 공유드려요~','2025-05-15 13:33:25','2025-05-15 13:33:25',NULL),(18,18,12,'🛍️셀러 물품 체크','이번 플마에서 팔 물건 체크해요! 1인당 3개 제한입니다~','2025-05-15 13:33:25','2025-05-15 13:33:25',NULL),(19,19,19,'💃케이팝 안무 신청받아요~','이번엔 뉴진스 or 세븐틴 어때요? 여러분 의견 받습니다!','2025-05-15 13:33:25','2025-05-15 13:33:25',NULL),(20,20,4,'🎬다음 미드 고르기 투표!','<Friends> VS <Emily in Paris> 어디로 할까요? 투표 받습니다~','2025-05-15 13:33:25','2025-05-15 13:33:25',NULL);
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class`
--

DROP TABLE IF EXISTS `class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class` (
  `class_id` int NOT NULL AUTO_INCREMENT,
  `host_id` int NOT NULL,
  `title` varchar(40) NOT NULL,
  `context` text,
  `max_participants` int NOT NULL,
  `main_region` enum('강남구','강동구','강북구','강서구','관악구','광진구','구로구','금천구','노원구','도봉구','동대문구','동작구','마포구','서대문구','서초구','성동구','성북구','송파구','양천구','영등포구','용산구','은평구','종로구','중구','중랑구') NOT NULL,
  `category` enum('봉사활동','사교/인맥','문화/공연/축제','인문학/책/글','공예/만들기','댄스/무용','운동/스포츠','외국/언어','아웃도어/여행','음악/악기','차/바이크','사진/영상','스포츠관람','게임/오락','요리/제조','반려동물','자기계발','테크/프로그래밍','패션/뷰티','수집/덕질') NOT NULL,
  `occurrences` int NOT NULL DEFAULT '0',
  `recruit_deadline` datetime DEFAULT NULL,
  `status` enum('모집중','진행중','진행완료') NOT NULL DEFAULT '모집중',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`class_id`),
  KEY `host_id` (`host_id`),
  KEY `idx_class_category` (`category`),
  KEY `idx_class_status` (`status`),
  CONSTRAINT `class_ibfk_1` FOREIGN KEY (`host_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class`
--

LOCK TABLES `class` WRITE;
/*!40000 ALTER TABLE `class` DISABLE KEYS */;
INSERT INTO `class` (`class_id`, `host_id`, `title`, `context`, `max_participants`, `main_region`, `category`, `occurrences`, `recruit_deadline`, `status`, `created_at`, `updated_at`, `deleted_at`) VALUES (1,6,'마포런크루 🏃‍♀️','출근 전 새벽 러닝하는 찐친 크루 구해요!',8,'마포구','운동/스포츠',5,'2025-05-28 07:13:37','모집중','2025-05-01 10:00:00','2025-05-01 11:30:00',NULL),(2,3,'책맥토크 🍻📚','책 읽고 맥주 한잔하며 수다 떨어요!',12,'서대문구','인문학/책/글',4,'2025-05-31 07:13:37','진행완료','2025-05-02 10:00:00','2025-05-02 11:33:00',NULL),(3,5,'요리해먹클 👨‍🍳','밀프렙 하고 같이 나눠먹어요 (feat.텀블러)',10,'강서구','요리/제조',6,'2025-06-08 07:13:37','모집중','2025-05-03 10:00:00','2025-05-03 11:56:00',NULL),(4,3,'불어 한잔 할래요? 🍷','프랑스어 배우면서 와인 한잔 어떠세요?',6,'관악구','외국/언어',5,'2025-06-05 07:13:37','진행완료','2025-05-04 10:00:00','2025-05-04 10:21:00',NULL),(5,3,'혼트립 같이가요 ✈️','혼자보다 여럿이 가면 재미 + 안전',6,'송파구','아웃도어/여행',3,'2025-05-31 07:13:37','진행중','2025-05-05 10:00:00','2025-05-05 10:05:00',NULL),(6,14,'불금엔 보드게임 🎲','아무나 오면 안 돼요. TMI대환영ㅋㅋ',10,'종로구','게임/오락',4,'2025-05-21 07:13:37','진행완료','2025-05-06 10:00:00','2025-05-06 11:13:00',NULL),(7,10,'클래식 같이 들을 사람? 🎻','조용히 귀호강하고 싶은 분들 모여요~',5,'서초구','음악/악기',8,'2025-05-27 07:13:37','진행완료','2025-05-07 10:00:00','2025-05-07 11:11:00',NULL),(8,7,'코딩왕초보 탈출기 💻','개발자 아닌 사람 환영 (비전공)',15,'광진구','테크/프로그래밍',2,'2025-05-21 07:13:37','진행완료','2025-05-08 10:00:00','2025-05-08 10:54:00',NULL),(9,17,'기타 완주 챌린지 🎸','혼자 연습 안된다면 같이 해요!',5,'강서구','음악/악기',5,'2025-05-26 07:13:37','진행완료','2025-05-09 10:00:00','2025-05-09 11:26:00',NULL),(10,10,'우리동네 드로잉 🎨','카페 드로잉 감성충만한 분위기 보장',5,'광진구','공예/만들기',4,'2025-06-06 07:13:37','모집중','2025-05-10 10:00:00','2025-05-10 10:38:00',NULL),(11,1,'감성사진 찍는 날 📸','DSLR 없어도 OK! 핸드폰 유저 대환영',10,'성동구','사진/영상',6,'2025-06-01 07:13:37','모집중','2025-05-11 10:00:00','2025-05-11 11:56:00',NULL),(12,13,'영상편집 왕기초반 🎬','프리미어나 캡컷부터 같이 배워요!',8,'양천구','사진/영상',4,'2025-05-29 07:13:37','모집중','2025-05-12 10:00:00','2025-05-12 10:48:00',NULL),(13,15,'한걸음 자기계발 모임 🚶‍♀️','작은 루틴부터 같이 만들어봐요.',12,'마포구','자기계발',6,'2025-06-03 07:13:37','진행중','2025-05-13 10:00:00','2025-05-13 11:58:00',NULL),(14,20,'산책메이트 모집중 🐶','운동도 되고 힐링도 되는 모임',6,'강남구','반려동물',2,'2025-05-30 07:13:37','진행완료','2025-05-14 10:00:00','2025-05-14 11:24:00',NULL),(15,7,'비건쿠킹챌린지 🥕','비건요리 함께 도전해봐요!',10,'노원구','요리/제조',5,'2025-06-04 07:13:37','진행완료','2025-05-15 10:00:00','2025-05-15 10:31:00',NULL),(16,18,'펫러버 산책모임 🐾','강아지 산책 친구 구해요 (마루도 와요)',6,'중랑구','반려동물',5,'2025-06-02 07:13:37','모집중','2025-05-16 10:00:00','2025-05-16 10:54:00',NULL),(17,2,'소확행 전시투어 🎨','동네 전시회 같이 보러가요~',8,'성북구','문화/공연/축제',3,'2025-05-28 07:13:37','진행중','2025-05-17 10:00:00','2025-05-17 11:32:00',NULL),(18,12,'일요 플리마켓 크루 🛍️','셀러로 함께 참여해요!',15,'동작구','사교/인맥',6,'2025-06-10 07:13:37','모집중','2025-05-18 10:00:00','2025-05-18 11:47:00',NULL),(19,19,'댄스입문 같이해요 💃','Zumba부터 케이팝까지 다 가능!',10,'강북구','댄스/무용',7,'2025-06-05 07:13:37','모집중','2025-05-19 10:00:00','2025-05-19 11:06:00',NULL),(20,4,'미드로 영어마스터 🎬','미드 같이 보며 생활영어 익혀요!',12,'강동구','외국/언어',6,'2025-06-09 07:13:37','진행중','2025-05-20 10:00:00','2025-05-20 11:21:00',NULL);
/*!40000 ALTER TABLE `class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `comment_id` int NOT NULL AUTO_INCREMENT,
  `board_id` int NOT NULL,
  `user_id` int NOT NULL,
  `content` text NOT NULL,
  `parent_id` int DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `board_id` (`board_id`),
  KEY `user_id` (`user_id`),
  KEY `parent_id` (`parent_id`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`board_id`) REFERENCES `board` (`board_id`),
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `comment_ibfk_3` FOREIGN KEY (`parent_id`) REFERENCES `comment` (`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` (`comment_id`, `board_id`, `user_id`, `content`, `parent_id`, `created_at`, `updated_at`, `deleted_at`) VALUES (1,1,8,'망원 한강 넘 좋아요 ㅠㅠ 여기서 뛰면 개운함!',NULL,'2025-05-15 13:39:30','2025-05-15 13:39:30',NULL),(2,1,12,'날씨 미쳤죠 오늘? ☀️',1,'2025-05-15 13:39:30','2025-05-15 13:39:30',NULL),(3,1,15,'합정역에서 같이 가실 분~?',NULL,'2025-05-15 13:39:30','2025-05-15 13:39:30',NULL),(4,2,7,'오늘 갑니다 ㅋㅋ 책은 <보통의 존재> 가져갈게요~',NULL,'2025-05-15 13:39:30','2025-05-15 13:39:30',NULL),(5,2,11,'저도 갈게요! 성수에서 봬요 🍻',4,'2025-05-15 13:39:30','2025-05-15 13:39:30',NULL),(6,3,13,'고구마 2팩 가져갈게요! 혹시 후무스 필요하신 분?',NULL,'2025-05-15 13:39:30','2025-05-15 13:39:30',NULL),(7,3,5,'와 후무스 최고... 저랑 나눠요 ㅋㅋ',6,'2025-05-15 13:39:30','2025-05-15 13:39:30',NULL),(8,4,10,'MT라니 기대돼요! 고기 제가 맡을게요~',NULL,'2025-05-15 13:39:30','2025-05-15 13:39:30',NULL),(9,5,6,'속초 추천이요! 회 먹으러 가고 싶음ㅋㅋ',NULL,'2025-05-15 13:39:30','2025-05-15 13:39:30',NULL),(10,6,9,'연남 어디인가요? 카페 이름 공유해주세용',NULL,'2025-05-15 13:39:30','2025-05-15 13:39:30',NULL),(11,6,14,'보드몽드카페요~ 링크 걸어둘게요!',10,'2025-05-15 13:39:30','2025-05-15 13:39:30',NULL),(12,7,10,'한적한 카페 하나 알아요. 방배쪽인데 공유드릴게요~',NULL,'2025-05-15 13:39:30','2025-05-15 13:39:30',NULL),(13,8,13,'코딩 첫 과제 떨려요 ㅠㅠ 프론트 제가 해볼게요!',NULL,'2025-05-15 13:39:30','2025-05-15 13:39:30',NULL),(14,8,7,'오 좋습니다. 백은 제가 맡을게요',13,'2025-05-15 13:39:30','2025-05-15 13:39:30',NULL),(15,8,9,'저는 디자인템플릿 좀 알아볼게요!',NULL,'2025-05-15 13:39:30','2025-05-15 13:39:30',NULL),(16,9,5,'다음 자이언티 기대돼요! 영상도 찍어주세요!',NULL,'2025-05-15 13:39:30','2025-05-15 13:39:30',NULL),(17,10,4,'드로잉용 펜 어떤 거 가져가요?',NULL,'2025-05-15 13:39:30','2025-05-15 13:39:30',NULL),(18,11,6,'카메라 배터리 꼭 챙기세용📷',17,'2025-05-15 13:39:30','2025-05-15 13:39:30',NULL),(19,12,7,'자막 꾸미기 꿀팁 공유 부탁드립니다!',NULL,'2025-05-15 13:39:30','2025-05-15 13:39:30',NULL),(20,12,13,'내일 워크숍 때 같이 해봐요~',NULL,'2025-05-15 13:39:30','2025-05-15 13:39:30',NULL),(21,13,20,'헐 진짜 6시...? 못 일어나면 벌금이요? 😂',NULL,'2025-05-15 13:39:30','2025-05-15 13:39:30',NULL),(22,14,6,'제 친구도 같이 가도 될까요? 말티즈에요 🐶',NULL,'2025-05-15 13:39:30','2025-05-15 13:39:30',NULL),(23,15,12,'오 병아리콩 까는 꿀팁 있어요~ 알려드릴게요',NULL,'2025-05-15 13:39:30','2025-05-15 13:39:30',NULL),(24,16,19,'은평공원 좋아요! 조용하고 넓음',NULL,'2025-05-15 13:39:30','2025-05-15 13:39:30',NULL),(25,17,3,'아르코 좋죠! 끝나고 디저트카페 가실 분?',NULL,'2025-05-15 13:39:30','2025-05-15 13:39:30',NULL),(26,18,9,'포장재료도 챙겨야죠~ 뽁뽁이랑 쇼핑백은요?',NULL,'2025-05-15 13:39:30','2025-05-15 13:39:30',NULL),(27,19,17,'세븐틴이요! HOT 추천합니다',NULL,'2025-05-15 13:39:30','2025-05-15 13:39:30',NULL),(28,20,1,'Emily로 해요~ Paris 뽐뿌 받고 싶음ㅋㅋ',NULL,'2025-05-15 13:39:30','2025-05-15 13:39:30',NULL);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `participation`
--

DROP TABLE IF EXISTS `participation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `participation` (
  `participation_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `class_id` int NOT NULL,
  `joined_at` date NOT NULL,
  `absent` int NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`participation_id`),
  KEY `user_id` (`user_id`),
  KEY `class_id` (`class_id`),
  CONSTRAINT `participation_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `participation_ibfk_2` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `participation`
--

LOCK TABLES `participation` WRITE;
/*!40000 ALTER TABLE `participation` DISABLE KEYS */;
INSERT INTO `participation` (`participation_id`, `user_id`, `class_id`, `joined_at`, `absent`, `created_at`, `updated_at`, `deleted_at`) VALUES (1,8,1,'2025-05-01',0,'2025-05-15 13:32:54','2025-05-15 13:32:54',NULL),(2,12,1,'2025-05-01',0,'2025-05-15 13:32:54','2025-05-15 13:32:54',NULL),(3,15,1,'2025-05-01',1,'2025-05-15 13:32:54','2025-05-15 13:32:54',NULL),(4,7,2,'2025-05-02',0,'2025-05-15 13:32:54','2025-05-15 13:32:54',NULL),(5,11,2,'2025-05-02',0,'2025-05-15 13:32:54','2025-05-15 13:32:54',NULL),(6,13,3,'2025-05-03',0,'2025-05-15 13:32:54','2025-05-15 13:32:54',NULL),(7,5,3,'2025-05-03',0,'2025-05-15 13:32:54','2025-05-15 13:32:54',NULL),(8,10,4,'2025-05-04',1,'2025-05-15 13:32:54','2025-05-15 13:32:54',NULL),(9,6,5,'2025-05-05',0,'2025-05-15 13:32:54','2025-05-15 13:32:54',NULL),(10,9,6,'2025-05-06',0,'2025-05-15 13:32:54','2025-05-15 13:32:54',NULL),(11,14,6,'2025-05-06',0,'2025-05-15 13:32:54','2025-05-15 13:32:54',NULL),(12,8,7,'2025-05-07',1,'2025-05-15 13:32:54','2025-05-15 13:32:54',NULL),(13,13,8,'2025-05-08',0,'2025-05-15 13:32:54','2025-05-15 13:32:54',NULL),(14,7,8,'2025-05-08',0,'2025-05-15 13:32:54','2025-05-15 13:32:54',NULL),(15,9,8,'2025-05-08',0,'2025-05-15 13:32:54','2025-05-15 13:32:54',NULL),(16,5,9,'2025-05-09',0,'2025-05-15 13:32:54','2025-05-15 13:32:54',NULL),(17,4,10,'2025-05-10',0,'2025-05-15 13:32:54','2025-05-15 13:32:54',NULL),(18,6,11,'2025-05-11',0,'2025-05-15 13:32:54','2025-05-15 13:32:54',NULL),(19,7,12,'2025-05-12',0,'2025-05-15 13:32:54','2025-05-15 13:32:54',NULL),(20,13,12,'2025-05-12',1,'2025-05-15 13:32:54','2025-05-15 13:32:54',NULL),(21,20,13,'2025-05-13',0,'2025-05-15 13:32:54','2025-05-15 13:32:54',NULL),(22,6,14,'2025-05-14',0,'2025-05-15 13:32:54','2025-05-15 13:32:54',NULL),(23,12,15,'2025-05-15',1,'2025-05-15 13:32:54','2025-05-15 13:32:54',NULL),(24,19,16,'2025-05-16',0,'2025-05-15 13:32:54','2025-05-15 13:32:54',NULL),(25,3,17,'2025-05-17',0,'2025-05-15 13:32:54','2025-05-15 13:32:54',NULL),(26,9,18,'2025-05-18',0,'2025-05-15 13:32:54','2025-05-15 13:32:54',NULL),(27,17,19,'2025-05-19',1,'2025-05-15 13:32:54','2025-05-15 13:32:54',NULL),(28,1,20,'2025-05-20',0,'2025-05-15 13:32:54','2025-05-15 13:32:54',NULL);
/*!40000 ALTER TABLE `participation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `review_id` int NOT NULL AUTO_INCREMENT,
  `writer_id` int NOT NULL,
  `target_id` int NOT NULL,
  `class_id` int NOT NULL,
  `rating` float NOT NULL,
  `content` text,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`review_id`),
  KEY `writer_id` (`writer_id`),
  KEY `target_id` (`target_id`),
  KEY `class_id` (`class_id`),
  CONSTRAINT `review_ibfk_1` FOREIGN KEY (`writer_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `review_ibfk_2` FOREIGN KEY (`target_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `review_ibfk_3` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`),
  CONSTRAINT `review_chk_1` CHECK ((((`rating` * 10) % 5) = 0))
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` (`review_id`, `writer_id`, `target_id`, `class_id`, `rating`, `content`, `created_at`, `updated_at`, `deleted_at`) VALUES (1,8,12,1,4.5,'시간 잘 지켜주시고, 같이 뛸 때 페이스도 맞춰주셨어요!','2025-05-15 13:35:42','2025-05-15 13:35:42',NULL),(2,12,8,1,5,'쏘 스윗했어요 진짜ㅠㅠ 다음에도 또 뛰어요!','2025-05-15 13:35:42','2025-05-15 13:35:42',NULL),(3,7,11,2,4,'책 토론 진짜 잘 끌어주셔서 시간 순삭','2025-05-15 13:35:42','2025-05-15 13:35:42',NULL),(4,11,7,2,4.5,'말 너무 재밌게 하심ㅋㅋ 다음엔 맥주도 가져오세욬','2025-05-15 13:35:42','2025-05-15 13:35:42',NULL),(5,5,13,3,5,'요리 진짜 정성 가득... 밀프렙 마스터세요','2025-05-15 13:35:42','2025-05-15 13:35:42',NULL),(6,13,5,3,4.5,'뒷정리까지 도와주신 거 찐 감동','2025-05-15 13:35:42','2025-05-15 13:35:42',NULL),(7,9,14,6,3.5,'살짝 늦으셨지만 보드게임 룰 잘 설명해주셨어요!','2025-05-15 13:35:42','2025-05-15 13:35:42',NULL),(8,14,9,6,4,'조곤조곤 설명 잘하심ㅋㅋ 자주 봬요','2025-05-15 13:35:42','2025-05-15 13:35:42',NULL),(9,7,13,8,5,'디버깅 같이 해줘서 넘 고마웠어요ㅠㅠ','2025-05-15 13:35:42','2025-05-15 13:35:42',NULL),(10,13,9,8,4.5,'디자인 감각 미쳤어요... Figma 천재','2025-05-15 13:35:42','2025-05-15 13:35:42',NULL),(11,9,7,8,4,'너무 잘 끌어주셔서 편하게 따라갈 수 있었어요','2025-05-15 13:35:42','2025-05-15 13:35:42',NULL),(12,7,13,12,5,'프리미어 단축키 마스터... 은인입니다.','2025-05-15 13:35:42','2025-05-15 13:35:42',NULL),(13,13,7,12,4.5,'센스 있는 컷 편집 인정합니다ㅋㅋ','2025-05-15 13:35:42','2025-05-15 13:35:42',NULL),(14,9,12,18,4,'셀러 노하우 공유 넘 좋았어요~','2025-05-15 13:35:42','2025-05-15 13:35:42',NULL),(15,12,9,18,4,'패키징 꿀팁 대박! 다음에도 같이 해요~','2025-05-15 13:35:42','2025-05-15 13:35:42',NULL);
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedule` (
  `schedule_id` int NOT NULL AUTO_INCREMENT,
  `class_id` int NOT NULL,
  `day_of_week` enum('Mon','Tues','Wed','Thur','Fri','Sat','Sun') NOT NULL,
  `start_time` time NOT NULL,
  `duration` int NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`schedule_id`),
  KEY `class_id` (`class_id`),
  CONSTRAINT `schedule_ibfk_1` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
INSERT INTO `schedule` (`schedule_id`, `class_id`, `day_of_week`, `start_time`, `duration`, `created_at`, `updated_at`, `deleted_at`) VALUES (1,1,'Mon','06:30:00',60,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(2,1,'Thur','06:30:00',60,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(3,2,'Fri','20:00:00',120,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(4,3,'Tues','19:00:00',90,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(5,3,'Thur','19:00:00',90,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(6,3,'Sat','11:00:00',90,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(7,4,'Wed','18:30:00',90,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(8,4,'Sun','16:00:00',90,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(9,5,'Sat','09:00:00',120,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(10,6,'Fri','19:30:00',120,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(11,6,'Sat','14:00:00',90,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(12,7,'Sun','17:00:00',60,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(13,8,'Mon','20:00:00',90,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(14,8,'Wed','20:00:00',90,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(15,8,'Sat','10:00:00',90,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(16,9,'Tues','19:00:00',60,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(17,9,'Sat','14:00:00',60,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(18,10,'Sun','15:00:00',120,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(19,11,'Sat','10:00:00',90,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(20,11,'Sun','10:00:00',90,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(21,12,'Wed','19:30:00',120,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(22,13,'Mon','07:00:00',60,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(23,13,'Wed','07:00:00',60,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(24,13,'Fri','07:00:00',60,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(25,14,'Sat','08:30:00',60,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(26,15,'Tues','18:00:00',90,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(27,15,'Thur','18:00:00',90,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(28,16,'Sun','08:00:00',60,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(29,17,'Sat','14:00:00',120,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(30,17,'Sun','14:00:00',120,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(31,18,'Fri','19:00:00',90,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(32,18,'Sat','13:00:00',90,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(33,18,'Sun','13:00:00',90,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(34,19,'Wed','20:00:00',90,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(35,19,'Fri','20:00:00',90,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL),(36,20,'Sun','20:30:00',90,'2025-05-15 13:25:50','2025-05-15 13:25:50',NULL);
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scrap`
--

DROP TABLE IF EXISTS `scrap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `scrap` (
  `scrap_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `class_id` int NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`scrap_id`),
  KEY `user_id` (`user_id`),
  KEY `class_id` (`class_id`),
  CONSTRAINT `scrap_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `scrap_ibfk_2` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scrap`
--

LOCK TABLES `scrap` WRITE;
/*!40000 ALTER TABLE `scrap` DISABLE KEYS */;
INSERT INTO `scrap` (`scrap_id`, `user_id`, `class_id`, `created_at`, `updated_at`, `deleted_at`) VALUES (1,1,2,'2025-05-15 13:34:19','2025-05-15 13:34:19',NULL),(2,1,5,'2025-05-15 13:34:19','2025-05-15 13:34:19',NULL),(3,4,3,'2025-05-15 13:34:19','2025-05-15 13:34:19',NULL),(4,5,7,'2025-05-15 13:34:19','2025-05-15 13:34:19',NULL),(5,6,10,'2025-05-15 13:34:19','2025-05-15 13:34:19',NULL),(6,6,15,'2025-05-15 13:34:19','2025-05-15 13:34:19',NULL),(7,7,4,'2025-05-15 13:34:19','2025-05-15 13:34:19',NULL),(8,8,11,'2025-05-15 13:34:19','2025-05-15 13:34:19',NULL),(9,8,13,'2025-05-15 13:34:19','2025-05-15 13:34:19',NULL),(10,9,12,'2025-05-15 13:34:19','2025-05-15 13:34:19',NULL),(11,10,1,'2025-05-15 13:34:19','2025-05-15 13:34:19',NULL),(12,11,14,'2025-05-15 13:34:19','2025-05-15 13:34:19',NULL),(13,12,16,'2025-05-15 13:34:19','2025-05-15 13:34:19',NULL),(14,12,20,'2025-05-15 13:34:19','2025-05-15 13:34:19',NULL),(15,13,6,'2025-05-15 13:34:19','2025-05-15 13:34:19',NULL),(16,14,18,'2025-05-15 13:34:19','2025-05-15 13:34:19',NULL),(17,15,9,'2025-05-15 13:34:19','2025-05-15 13:34:19',NULL),(18,17,8,'2025-05-15 13:34:19','2025-05-15 13:34:19',NULL),(19,18,19,'2025-05-15 13:34:19','2025-05-15 13:34:19',NULL),(20,19,17,'2025-05-15 13:34:19','2025-05-15 13:34:19',NULL);
/*!40000 ALTER TABLE `scrap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tag` (
  `tag_id` int NOT NULL AUTO_INCREMENT,
  `name` enum('ISTJ','ISTP','ISFJ','ISFP','INTJ','INTP','INFJ','INFP','ESTJ','ESTP','ESFJ','ESFP','ENTJ','ENTP','ENFJ','ENFP','조용한/정적인','사교적인/시끌벅적한','소규모','대규모') NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` (`tag_id`, `name`, `created_at`, `updated_at`, `deleted_at`) VALUES (1,'ISTJ','2025-05-15 13:25:20','2025-05-15 13:25:20',NULL),(2,'ISTP','2025-05-15 13:25:20','2025-05-15 13:25:20',NULL),(3,'ISFJ','2025-05-15 13:25:20','2025-05-15 13:25:20',NULL),(4,'ISFP','2025-05-15 13:25:20','2025-05-15 13:25:20',NULL),(5,'INTJ','2025-05-15 13:25:20','2025-05-15 13:25:20',NULL),(6,'INTP','2025-05-15 13:25:20','2025-05-15 13:25:20',NULL),(7,'INFJ','2025-05-15 13:25:20','2025-05-15 13:25:20',NULL),(8,'INFP','2025-05-15 13:25:20','2025-05-15 13:25:20',NULL),(9,'ESTJ','2025-05-15 13:25:20','2025-05-15 13:25:20',NULL),(10,'ESTP','2025-05-15 13:25:20','2025-05-15 13:25:20',NULL),(11,'ESFJ','2025-05-15 13:25:20','2025-05-15 13:25:20',NULL),(12,'ESFP','2025-05-15 13:25:20','2025-05-15 13:25:20',NULL),(13,'ENTJ','2025-05-15 13:25:20','2025-05-15 13:25:20',NULL),(14,'ENTP','2025-05-15 13:25:20','2025-05-15 13:25:20',NULL),(15,'ENFJ','2025-05-15 13:25:20','2025-05-15 13:25:20',NULL),(16,'ENFP','2025-05-15 13:25:20','2025-05-15 13:25:20',NULL),(17,'조용한/정적인','2025-05-15 13:25:20','2025-05-15 13:25:20',NULL),(18,'사교적인/시끌벅적한','2025-05-15 13:25:20','2025-05-15 13:25:20',NULL),(19,'소규모','2025-05-15 13:25:20','2025-05-15 13:25:20',NULL),(20,'대규모','2025-05-15 13:25:20','2025-05-15 13:25:20',NULL);
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `nickname` varchar(20) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `birthyear` year DEFAULT NULL,
  `gender` enum('M','F','Other') NOT NULL,
  `region` enum('강남구','강동구','강북구','강서구','관악구','광진구','구로구','금천구','노원구','도봉구','동대문구','동작구','마포구','서대문구','서초구','성동구','성북구','송파구','양천구','영등포구','용산구','은평구','종로구','중구','중랑구') NOT NULL,
  `registered_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`),
  KEY `idx_user_region` (`region`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`user_id`, `username`, `nickname`, `password`, `email`, `birthyear`, `gender`, `region`, `registered_at`, `created_at`, `updated_at`, `deleted_at`) VALUES (1,'김고양','catlover','Meow@2024','catlover1@example.com',1997,'F','마포구','2025-05-15 13:24:42','2025-05-15 13:24:42','2025-05-16 16:01:24',NULL),(2,'이하늘','skyhug','BlueSky!22','skyhug@example.com',1995,'M','강남구','2025-05-15 13:24:42','2025-05-15 13:24:42','2025-05-16 16:01:24',NULL),(3,'박달빛','moonshine','SilentMoon_99','moonshine@example.com',2000,'F','성북구','2025-05-15 13:24:42','2025-05-15 13:24:42','2025-05-16 16:01:24',NULL),(4,'송바람','softwind','Soft1234!','softwind@example.com',1994,'Other','은평구','2025-05-15 13:24:42','2025-05-15 13:24:42','2025-05-16 16:01:24',NULL),(5,'최햇살','sunrelax','Sunshine_77','sunrelax@example.com',1993,'M','강서구','2025-05-15 13:24:42','2025-05-15 13:24:42','2025-05-16 16:01:24',NULL),(6,'남궁나무','treelight','Tree$Grow56','treelight@example.com',1998,'F','동작구','2025-05-15 13:24:42','2025-05-15 13:24:42','2025-05-16 16:01:24',NULL),(7,'이펭귄','penguluv','CutePeng!12','penguluv@example.com',2002,'F','노원구','2025-05-15 13:24:42','2025-05-15 13:24:42','2025-05-16 16:01:24',NULL),(8,'박노을','sunsetvibe','V1brantSun*','sunsetvibe@example.com',1990,'M','송파구','2025-05-15 13:24:42','2025-05-15 13:24:42','2025-05-16 16:01:24',NULL),(9,'김고요','lakepeace','Peaceful@Lake','lakepeace@example.com',1987,'F','종로구','2025-05-15 13:24:42','2025-05-15 13:24:42','2025-05-16 16:01:24',NULL),(10,'오산뜻','freshseason','Fresh4U#2023','freshseason@example.com',1999,'F','관악구','2025-05-15 13:24:42','2025-05-15 13:24:42','2025-05-16 16:01:24',NULL),(11,'오구름','cloudwarm','Warm_Cloud9!','cloudwarm@example.com',1996,'M','광진구','2025-05-15 13:24:42','2025-05-15 13:24:42','2025-05-16 16:01:24',NULL),(12,'박별빛','starcalm','Starry*Night8','starcalm@example.com',1991,'F','서초구','2025-05-15 13:24:42','2025-05-15 13:24:42','2025-05-16 16:01:24',NULL),(13,'이숲','greenwood','GreenWood_33','greenwood@example.com',1985,'F','성동구','2025-05-15 13:24:42','2025-05-15 13:24:42','2025-05-16 16:01:24',NULL),(14,'최꿈','sweetdream','Dream#Sweet7','sweetdream@example.com',2003,'F','중구','2025-05-15 13:24:42','2025-05-15 13:24:42','2025-05-16 16:01:24',NULL),(15,'박이슬','dewdrop','Dew$2025drop','dewdrop@example.com',1992,'Other','중랑구','2025-05-15 13:24:42','2025-05-15 13:24:42','2025-05-16 16:01:24',NULL),(16,'오소리','clearsound','Clear!Sound11','clearsound@example.com',1989,'M','금천구','2025-05-15 13:24:42','2025-05-15 13:24:42','2025-05-16 16:01:24',NULL),(17,'이별','twinklestar','Tw1nkle*Star','twinklestar@example.com',1997,'F','도봉구','2025-05-15 13:24:42','2025-05-15 13:24:42','2025-05-16 16:01:24',NULL),(18,'김아침','freshmorning','Morning@Fresh','freshmorning@example.com',2001,'F','양천구','2025-05-15 13:24:42','2025-05-15 13:24:42','2025-05-16 16:01:24',NULL),(19,'왕향기','softscent','Scent88_ee','softscent@example.com',1994,'F','영등포구','2025-05-15 13:24:42','2025-05-15 13:24:42','2025-05-16 16:01:24',NULL),(20,'정빛깔','prettytone','Tone4Pretty!','prettytone@example.com',1988,'F','동대문구','2025-05-15 13:24:42','2025-05-15 13:24:42','2025-05-16 16:01:24',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usertag`
--

DROP TABLE IF EXISTS `usertag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usertag` (
  `user_id` int NOT NULL,
  `tag_id` int NOT NULL,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted_at` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`,`tag_id`),
  KEY `tag_id` (`tag_id`),
  CONSTRAINT `usertag_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `usertag_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usertag`
--

LOCK TABLES `usertag` WRITE;
/*!40000 ALTER TABLE `usertag` DISABLE KEYS */;
INSERT INTO `usertag` (`user_id`, `tag_id`, `created_at`, `updated_at`, `deleted_at`) VALUES (1,2,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(1,17,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(1,19,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(2,14,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(2,18,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(2,20,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(3,3,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(3,17,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(4,4,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(4,18,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(5,6,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(5,17,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(5,19,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(6,10,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(6,18,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(6,20,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(7,12,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(7,18,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(8,13,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(8,18,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(8,20,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(9,1,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(9,17,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(9,19,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(10,16,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(10,18,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(11,9,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(11,17,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(12,7,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(12,17,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(12,19,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(13,11,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(13,18,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(14,8,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(14,18,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(14,20,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(15,5,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(15,17,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(16,1,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(16,17,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(16,19,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(17,15,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(17,18,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(17,20,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(18,6,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(18,18,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(19,13,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(19,18,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(19,20,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(20,3,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(20,17,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL),(20,19,'2025-05-15 13:25:29','2025-05-15 13:25:29',NULL);
/*!40000 ALTER TABLE `usertag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'community'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-16 16:02:15
