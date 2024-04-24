-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.28-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for complaintdb
CREATE DATABASE IF NOT EXISTS `complaintdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `complaintdb`;

-- Dumping structure for table complaintdb.complaint
CREATE TABLE IF NOT EXISTS `complaint` (
  `complaint_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` text NOT NULL,
  `date` date NOT NULL,
  `complaint_handler_id` int(11) DEFAULT NULL,
  `status` enum('OPEN','ARCHIVED') DEFAULT 'OPEN',
  PRIMARY KEY (`complaint_id`),
  KEY `complaint_handler_id` (`complaint_handler_id`),
  CONSTRAINT `complaint_ibfk_1` FOREIGN KEY (`complaint_handler_id`) REFERENCES `complainthandler` (`complaint_handler_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table complaintdb.complaint: ~5 rows (approximately)
/*!40000 ALTER TABLE `complaint` DISABLE KEYS */;
INSERT INTO `complaint` (`complaint_id`, `description`, `date`, `complaint_handler_id`, `status`) VALUES
	(1, 'Slow internet connection', '2023-01-10', 1, 'OPEN'),
	(2, 'Billing discrepancy', '2023-02-15', 2, 'OPEN'),
	(3, 'Hardware malfunction', '2023-03-20', 3, 'OPEN'),
	(4, 'Software bug', '2023-04-05', 1, 'ARCHIVED');
/*!40000 ALTER TABLE `complaint` ENABLE KEYS */;

-- Dumping structure for table complaintdb.complainthandler
CREATE TABLE IF NOT EXISTS `complainthandler` (
  `complaint_handler_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`complaint_handler_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table complaintdb.complainthandler: ~3 rows (approximately)
/*!40000 ALTER TABLE `complainthandler` DISABLE KEYS */;
INSERT INTO `complainthandler` (`complaint_handler_id`, `name`) VALUES
	(1, 'John Doe'),
	(2, 'Jane Smith'),
	(3, 'Bob Johnson');
/*!40000 ALTER TABLE `complainthandler` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
