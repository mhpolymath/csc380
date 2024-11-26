-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.4.32-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for gymerfinal
DROP DATABASE IF EXISTS `gymerfinal`;
CREATE DATABASE IF NOT EXISTS `gymerfinal` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci */;
USE `gymerfinal`;

-- Dumping structure for table gymerfinal.booking
DROP TABLE IF EXISTS `booking`;
CREATE TABLE IF NOT EXISTS `booking` (
  `M_id` int(11) NOT NULL,
  `C_id` int(11) NOT NULL,
  `W_number` int(11) NOT NULL,
  PRIMARY KEY (`M_id`,`C_id`,`W_number`),
  KEY `fk_MEMBER_has_WORKOUT_CLASS_WORKOUT_CLASS1` (`C_id`,`W_number`),
  CONSTRAINT `fk_MEMBER_has_WORKOUT_CLASS_MEMBER1` FOREIGN KEY (`M_id`) REFERENCES `member` (`M_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_MEMBER_has_WORKOUT_CLASS_WORKOUT_CLASS1` FOREIGN KEY (`C_id`, `W_number`) REFERENCES `workout_class` (`C_id`, `W_number`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Dumping data for table gymerfinal.booking: ~0 rows (approximately)

-- Dumping structure for table gymerfinal.coach
DROP TABLE IF EXISTS `coach`;
CREATE TABLE IF NOT EXISTS `coach` (
  `C_id` int(11) NOT NULL,
  `C_Fname` varchar(45) DEFAULT NULL,
  `C_Lname` varchar(45) DEFAULT NULL,
  `C_phone` varchar(10) DEFAULT NULL,
  `Salary` varchar(45) DEFAULT NULL,
  `Bno` int(11) NOT NULL,
  PRIMARY KEY (`C_id`),
  KEY `fk_COACH_GYM` (`Bno`),
  CONSTRAINT `fk_COACH_GYM` FOREIGN KEY (`Bno`) REFERENCES `gym` (`Bno`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Dumping data for table gymerfinal.coach: ~2 rows (approximately)
INSERT INTO `coach` (`C_id`, `C_Fname`, `C_Lname`, `C_phone`, `Salary`, `Bno`) VALUES
	(133, 'Adil', 'Khalid', '0530530531', '13000', 1);

-- Dumping structure for table gymerfinal.fitness_data
DROP TABLE IF EXISTS `fitness_data`;
CREATE TABLE IF NOT EXISTS `fitness_data` (
  `M_id` int(11) NOT NULL,
  `Record_number` int(11) NOT NULL,
  `Record_date` date DEFAULT NULL,
  `Height` double DEFAULT NULL,
  `Weight` double DEFAULT NULL,
  `Fat_rate` double DEFAULT NULL,
  `Muscle_mass` double DEFAULT NULL,
  PRIMARY KEY (`M_id`,`Record_number`),
  CONSTRAINT `fk_FITNESS_DATA_MEMBER1` FOREIGN KEY (`M_id`) REFERENCES `member` (`M_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Dumping data for table gymerfinal.fitness_data: ~0 rows (approximately)

-- Dumping structure for table gymerfinal.gym
DROP TABLE IF EXISTS `gym`;
CREATE TABLE IF NOT EXISTS `gym` (
  `Bno` int(11) NOT NULL,
  `B_name` varchar(45) DEFAULT NULL,
  `Address` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Bno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Dumping data for table gymerfinal.gym: ~2 rows (approximately)
INSERT INTO `gym` (`Bno`, `B_name`, `Address`) VALUES
	(1, 'Rabwa', 'Rabwa St.'),
	(2, 'Rawdah', 'Rawdah Dist.');

-- Dumping structure for table gymerfinal.member
DROP TABLE IF EXISTS `member`;
CREATE TABLE IF NOT EXISTS `member` (
  `M_id` int(11) NOT NULL,
  `M_Fname` varchar(45) DEFAULT NULL,
  `M_Lname` varchar(45) DEFAULT NULL,
  `M_phone` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`M_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Dumping data for table gymerfinal.member: ~1 rows (approximately)
INSERT INTO `member` (`M_id`, `M_Fname`, `M_Lname`, `M_phone`) VALUES
	(11, 'Omar', 'AlKhamis', '0531224194');

-- Dumping structure for table gymerfinal.membership_at
DROP TABLE IF EXISTS `membership_at`;
CREATE TABLE IF NOT EXISTS `membership_at` (
  `M_id` int(11) NOT NULL,
  `Bno` int(11) NOT NULL,
  `Type` enum('Basic','VIP') NOT NULL,
  `Start_date` date DEFAULT curdate(),
  `End_date` date DEFAULT NULL,
  PRIMARY KEY (`M_id`,`Bno`),
  KEY `fk_MEMBER_has_GYM_GYM1` (`Bno`),
  CONSTRAINT `fk_MEMBER_has_GYM_GYM1` FOREIGN KEY (`Bno`) REFERENCES `gym` (`Bno`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_MEMBER_has_GYM_MEMBER1` FOREIGN KEY (`M_id`) REFERENCES `member` (`M_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `DATECHECKED` CHECK (`Start_date` < `End_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Dumping data for table gymerfinal.membership_at: ~2 rows (approximately)
INSERT INTO `membership_at` (`M_id`, `Bno`, `Type`, `Start_date`, `End_date`) VALUES
	(11, 1, 'VIP', '2024-10-17', '2025-10-17'),
	(11, 2, 'VIP', '2024-10-17', '2025-10-17');

-- Dumping structure for table gymerfinal.workout_class
DROP TABLE IF EXISTS `workout_class`;
CREATE TABLE IF NOT EXISTS `workout_class` (
  `C_id` int(11) NOT NULL,
  `W_number` int(11) NOT NULL,
  `W_name` varchar(45) DEFAULT NULL,
  `Schedule` datetime NOT NULL,
  `Max_capacity` int(11) DEFAULT NULL,
  PRIMARY KEY (`C_id`,`W_number`),
  CONSTRAINT `fk_WORKOUT_CLASS_COACH1` FOREIGN KEY (`C_id`) REFERENCES `coach` (`C_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Dumping data for table gymerfinal.workout_class: ~2 rows (approximately)
INSERT INTO `workout_class` (`C_id`, `W_number`, `W_name`, `Schedule`, `Max_capacity`) VALUES
	(133, 0, NULL, '0000-00-00 00:00:00', NULL),
	(133, 1, NULL, '0000-00-00 00:00:00', 30);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
