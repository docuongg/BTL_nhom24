-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.22-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for qaweb
CREATE DATABASE IF NOT EXISTS `qaweb` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `qaweb`;

-- Dumping structure for table qaweb.category
CREATE TABLE IF NOT EXISTS `category` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table qaweb.category: ~6 rows (approximately)
DELETE FROM `category`;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` (`id`, `name`) VALUES
	(1, 'programming'),
	(2, 'jobs'),
	(3, 'english'),
	(4, 'share'),
	(5, 'randomq'),
	(6, 'study'),
	(7, 'general');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;

-- Dumping structure for table qaweb.comment
CREATE TABLE IF NOT EXISTS `comment` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `post_id` int(10) NOT NULL,
  `user_id` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKComment678975` (`post_id`),
  KEY `FKComment447531` (`user_id`),
  CONSTRAINT `FKComment447531` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKComment678975` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table qaweb.comment: ~5 rows (approximately)
DELETE FROM `comment`;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` (`id`, `content`, `created_at`, `updated_at`, `post_id`, `user_id`) VALUES
	(1, 'B có thể nói rõ hơn được không ạ???', '2022-05-22 11:44:39', '2022-05-28 10:54:25', 7, 4),
	(2, 'Bây giờ làm gì cũng cũng cần có dữ liệu, dự liệu không khác gì vàng cả. Nếu khai thác tốt thì nó đem lại rất nhiều giá trị. Từ DA b có thể phát triển lên thành DE hoặc DS nữa.', '2022-05-22 11:46:24', '2022-05-22 11:46:24', 7, 7),
	(3, 'Mình cám ơn ạ', '2022-05-22 11:48:55', '2022-05-22 11:48:55', 7, 4),
	(4, 'Em cám ơn ạ', '2022-05-22 11:55:28', '2022-05-22 11:55:28', 8, 4),
	(5, 'FPT nữa bạn, mình đang học ở đây, cơ sở vật chất cũng như chất lượng cũng rất tốt.', '2022-05-22 12:04:13', '2022-05-22 12:04:13', 10, 4);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;

-- Dumping structure for table qaweb.post
CREATE TABLE IF NOT EXISTS `post` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `is_question` bit(1) NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `content` text NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `user_id` int(10) NOT NULL,
  `parent_id` int(10) DEFAULT NULL,
  `category_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpost83447` (`user_id`),
  KEY `FKpost743724` (`category_id`),
  KEY `FKpost226824` (`parent_id`),
  CONSTRAINT `FKpost226824` FOREIGN KEY (`parent_id`) REFERENCES `post` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKpost743724` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKpost83447` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table qaweb.post: ~13 rows (approximately)
DELETE FROM `post`;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
INSERT INTO `post` (`id`, `is_question`, `title`, `content`, `created_at`, `updated_at`, `user_id`, `parent_id`, `category_id`) VALUES
	(1, b'1', 'Tư vấn trường đại học ngành CNTT ở Hà Nội', 'Em hiện là hs lớp 12, năm nay e có muốn thi đại học ngành CNTT. Vậy ac có thể tư vấn cho e một vài trường ở Hà Nội được không ạ ?', '2022-05-12 11:00:21', '2022-05-19 12:02:13', 6, NULL, 6),
	(2, b'1', 'Nên học Tiếng Anh như thế nào để giao tiếp như người bản xứ', 'Em sinh viên năm nhất PTIT, trình độ tiếng anh của e khá là thấp. Vậy e nên làm gì bây giờ để nâng cao trình độ của mình ạ ? Có nên đi học ở trung tâm không ạ ?', '2022-05-15 11:04:14', '2022-05-17 11:35:57', 2, NULL, 3),
	(3, b'1', 'Chuyển sang CNTT ở tuổi 25, liệu có còn phù hợp ?', 'Mình là nam, 25 tuổi, tốt nghiệp ngành Kế toán.  \r\nNhưng giờ mình lại muốn chuyển sang ngành CNTT, liệu có quá muộn.\r\nGiờ mình nên bắt đầu từ đâu và ntn ạ ? Mong muốn nhận được tư vấn từ mn.', '2022-05-16 11:06:32', '2022-05-28 10:47:59', 3, NULL, 2),
	(4, b'1', 'Ngành Data Analyst và cơ hội phát triển nghề nghiệp', 'E là sinh viên năm 3 nghành CNTT, hiện đang quan tâm đến mảng DA.  \r\nVậy ac đi trước có thể chia sẻ cho e những ưu nhược điểm của ngành này được không ạ ? Và cơ hội nghề nghiệp nữa.', '2022-05-16 11:09:02', '2022-05-28 09:48:17', 4, NULL, 4),
	(5, b'1', 'Học gì để trở thành Tester ?', 'Em là nữ, e được biết mảng Tester không đòi hỏi nhiều về code, khá là phù hợp với nữ. Vậy xin hỏi ac đang làm ở mảng này, cần học những gì để có thể trở thành một Tester', '2022-05-16 11:27:40', '2022-05-20 12:11:27', 6, NULL, 4),
	(6, b'0', NULL, 'Em nên tự học lấy gốc Tiếng Anh trước, sau đó có thể ra trung tâm học bởi vì ở đó có người bản ngữ dạy.', '2022-05-17 11:35:57', '2022-05-17 11:35:57', 4, 2, NULL),
	(7, b'0', NULL, 'Ngành này được coi là ngành "hấp dẫn" nhất thế kỷ 21 mà e.  \r\nCơ hội nghề nghiệp rất nhiều.', '2022-05-17 15:35:57', '2022-05-28 14:46:12', 7, 4, NULL),
	(8, b'0', NULL, 'Nghành nào cũng có những khó dễ khác nhau, quan trọng là ý thức và sự cố gắng của bản thân. Ngành này không chỉ đòi hỏi kĩ năng về code mà còn cả kĩ năng thống kê, phân tích nữa.', '2022-05-17 19:52:53', '2022-05-17 19:52:53', 8, 4, NULL),
	(9, b'0', NULL, 'Ngành này luôn cần nguồn nhân lực trẻ mà, bạn như thế là cũng hơi muộn, tuy nhiên nếu bạn quyết tâm thì không bao giờ là muộn. \r\n**Fightting**', '2022-05-18 11:58:06', '2022-05-28 10:47:59', 2, 3, NULL),
	(10, b'0', NULL, 'Ở Hà Nội thì có HUST, PTIT,.. này', '2022-05-19 12:02:13', '2022-05-19 12:02:13', 8, 1, NULL),
	(11, b'0', NULL, 'Các kiến thức cơ bản về máy tính, tin học văn phòng, cài đặt phần mềm, internet. Ba điều căn bản cần nắm về lập trình là SQL, HTML, CSS. Trước khi làm test, bạn phải nắm được những điều này. Bạn không cần phải tìm hiểu quá sâu để có thể viết code nhưng ít nhất phải có thể hiểu được code và chỉnh sửa code đơn giản, ....', '2022-05-20 12:11:27', '2022-05-20 12:11:27', 2, 5, NULL),
	(12, b'1', 'Lỗi “scanner cannot be resolved to a type”', 'Mình mới học Java, tại sao mình dùng eclipse nó báo lỗi như này, vậy phải khắc phục như thế nào mọi người?  \r\n```\r\nException in thread “main” java.lang.Error: Unresolved compilation problems:\r\nscanner cannot be resolved to a type\r\nscanner cannot be resolved to a type\r\n```', '2022-05-22 12:18:13', '2022-05-28 15:03:50', 9, NULL, 1),
	(13, b'0', NULL, 'Bạn có thể sử dụng IntelliJ để code Java, nếu bạn không import class nó sẽ nhắc nhở bạn, và bạn ấn Alt Enter nó sẽ tự import cho bạn. Đỡ phải suy nghĩ nhiều :v:  \r\n![](https://blog.jetbrains.com/wp-content/uploads/2020/08/context-actions-add-import-statement.png)', '2022-05-22 12:21:41', '2022-05-28 15:03:50', 8, 12, NULL);
/*!40000 ALTER TABLE `post` ENABLE KEYS */;

-- Dumping structure for table qaweb.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `registered_at` datetime NOT NULL,
  `last_login` datetime NOT NULL,
  `is_admin` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table qaweb.user: ~10 rows (approximately)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `email`, `username`, `password`, `registered_at`, `last_login`, `is_admin`) VALUES
	(1, 'ad@gmail.com', 'ad', 'ad', '2022-05-13 10:38:25', '2022-05-28 15:04:00', b'1'),
	(2, 'admin@gmail.com', 'admin', 'admin', '2022-05-13 11:23:07', '2022-05-28 10:33:01', b'1'),
	(3, 'user1@gmail.com', 'nthung', '123456', '2022-05-13 12:00:00', '2022-05-28 14:54:51', b'0'),
	(4, 'user2@gmail.com', 'dncuong', '123456', '2022-05-13 11:23:07', '2022-05-28 14:45:56', b'0'),
	(5, 'user3@gmail.com', 'nnmanh', '123456', '2022-05-13 11:23:07', '2022-05-27 14:41:10', b'0'),
	(6, 'user4@gmail.com', 'nthien', '123456', '2022-05-13 11:23:07', '2022-05-22 11:24:57', b'0'),
	(7, 'user5@gmail.com', 'btthao', '123456', '2022-05-13 11:23:07', '2022-05-27 14:41:12', b'0'),
	(8, 'user6@gmail.com', 'dnlinh', '123456', '2022-05-13 11:23:07', '2022-05-28 15:03:27', b'0'),
	(9, 'user7@gmail.com', 'btyen', '123456', '2022-05-13 11:23:07', '2022-05-28 10:09:47', b'0'),
	(10, 'user8@gmail.com', 'tvanh', '123456', '2022-05-13 11:23:07', '2022-05-22 12:12:57', b'0');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dumping structure for table qaweb.vote
CREATE TABLE IF NOT EXISTS `vote` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `is_upvote` bit(1) NOT NULL,
  `user_id` int(10) NOT NULL,
  `post_id` int(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_post_id` (`user_id`,`post_id`) USING BTREE,
  KEY `FKvote136129` (`post_id`),
  CONSTRAINT `FKvote136129` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FKvote904684` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

-- Dumping data for table qaweb.vote: ~9 rows (approximately)
DELETE FROM `vote`;
/*!40000 ALTER TABLE `vote` DISABLE KEYS */;
INSERT INTO `vote` (`id`, `is_upvote`, `user_id`, `post_id`) VALUES
	(1, b'1', 4, 9),
	(2, b'1', 1, 4),
	(3, b'1', 8, 9),
	(4, b'1', 3, 4),
	(5, b'1', 5, 1),
	(6, b'1', 9, 2),
	(7, b'1', 6, 4),
	(8, b'1', 3, 12),
	(9, b'1', 4, 5);
/*!40000 ALTER TABLE `vote` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
