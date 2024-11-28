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
CREATE DATABASE IF NOT EXISTS `gymerfinal` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci */;
USE `gymerfinal`;

-- Dumping structure for table gymerfinal.booking
CREATE TABLE IF NOT EXISTS `booking` (
  `M_id` int(11) NOT NULL,
  `C_id` int(11) NOT NULL,
  PRIMARY KEY (`M_id`,`C_id`),
  KEY `fk_member_has_workout_class_workout_class1` (`C_id`),
  CONSTRAINT `fk_member_has_workout_class_member1` FOREIGN KEY (`M_id`) REFERENCES `member` (`M_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_member_has_workout_class_workout_class1` FOREIGN KEY (`C_id`) REFERENCES `workout_class` (`C_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Dumping data for table gymerfinal.booking: ~2 rows (approximately)
INSERT INTO `booking` (`M_id`, `C_id`) VALUES
	(1, 4),
	(2, 4);

-- Dumping structure for table gymerfinal.coach
CREATE TABLE IF NOT EXISTS `coach` (
  `C_id` int(11) NOT NULL,
  `C_Fname` varchar(45) DEFAULT NULL,
  `C_Lname` varchar(45) DEFAULT NULL,
  `C_phone` varchar(10) DEFAULT NULL,
  `Salary` double DEFAULT NULL,
  `Bno` int(11) NOT NULL,
  PRIMARY KEY (`C_id`),
  KEY `fk_COACH_GYM` (`Bno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Dumping data for table gymerfinal.coach: ~18 rows (approximately)
INSERT INTO `coach` (`C_id`, `C_Fname`, `C_Lname`, `C_phone`, `Salary`, `Bno`) VALUES
	(1, 'John', 'Doe', '0551112233', 7000, 1),
	(2, 'Mark', 'Smith', '0534567890', 1000, 2),
	(3, 'Liam', 'Johnson', '0541239876', 5000, 2),
	(4, 'Carlos', 'Rodriguez', '0589988777', 1000, 2),
	(5, 'David', 'Brown', '0552233445', 3000, 3),
	(6, 'Eva', 'Williams', '0563344556', 3000, 3),
	(7, 'Ali', 'Khan', '0553344777', 3500, 4),
	(8, 'James', 'Taylor', '0538877665', 1000, 4),
	(9, 'Mohammed', 'Alsaud', '0547788999', 8000, 5),
	(10, 'Michael', 'Martin', '0586677888', 980, 5),
	(11, 'Thomas', 'White', '0561122334', 2000, 6),
	(12, 'Ryan', 'Harris', '0531122334', 4000, 6),
	(13, 'Ahmed', 'Omar', '0584433221', 900, 1),
	(14, 'Zaid', 'Saleh', '0559988777', 1000, 2),
	(15, 'Chris', 'Taylor', '0568899000', 900, 3),
	(16, 'Daniel', 'Lewis', '0556677889', 1000, 4),
	(17, 'George', 'Walker', '0582233445', 1000, 5),
	(18, 'Sam', 'Clark', '0565566777', 2050, 6);

-- Dumping structure for table gymerfinal.fitness_data
CREATE TABLE IF NOT EXISTS `fitness_data` (
  `M_id` int(11) NOT NULL,
  `Record_number` int(11) NOT NULL,
  `Record_date` date DEFAULT NULL,
  `Height` double DEFAULT NULL,
  `Weight` double DEFAULT NULL,
  `Fat_rate` double DEFAULT NULL,
  `Muscle_mass` double DEFAULT NULL,
  PRIMARY KEY (`M_id`,`Record_number`),
  CONSTRAINT `fk_FITNESS_DATA_MEMBER1` FOREIGN KEY (`M_id`) REFERENCES `member` (`M_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Dumping data for table gymerfinal.fitness_data: ~0 rows (approximately)

-- Dumping structure for table gymerfinal.gym
CREATE TABLE IF NOT EXISTS `gym` (
  `Bno` int(11) NOT NULL,
  `B_name` varchar(45) DEFAULT NULL,
  `Address` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Bno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Dumping data for table gymerfinal.gym: ~6 rows (approximately)
INSERT INTO `gym` (`Bno`, `B_name`, `Address`) VALUES
	(1, 'Rabwa', 'Nahdhah St.'),
	(2, 'Al Malaz', 'King Abdulaziz Road, Riyadh, KSA'),
	(3, 'Al Nakheel', 'Prince Turki Al Awwal Road, Riyadh, KSA'),
	(4, 'Al Sulaymaniyah', 'Tahlia Street, Riyadh, KSA'),
	(5, 'Al Yasmin', 'King Salman Road, Riyadh, KSA'),
	(6, 'Diriyah', 'Historic Diriyah, Riyadh, KSA');

-- Dumping structure for table gymerfinal.member
CREATE TABLE IF NOT EXISTS `member` (
  `M_id` int(11) NOT NULL,
  `M_Fname` varchar(45) DEFAULT NULL,
  `M_Lname` varchar(45) DEFAULT NULL,
  `M_phone` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`M_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Dumping data for table gymerfinal.member: ~6 rows (approximately)
INSERT INTO `member` (`M_id`, `M_Fname`, `M_Lname`, `M_phone`) VALUES
	(1, 'Faisal', 'Alharbi', '050101055'),
	(2, 'Abdullah', 'Alqahtani', '0539876543'),
	(3, 'Mohammed', 'Alshehri', '0548765432'),
	(4, 'Sultan', 'Aldosari', '0561122334'),
	(5, 'Turki', 'Almutairi', '0554455667'),
	(6, 'Saud', 'Aljuhani', '0583344556');

-- Dumping structure for table gymerfinal.workout_class
CREATE TABLE IF NOT EXISTS `workout_class` (
  `C_id` int(11) NOT NULL,
  `W_name` varchar(45) DEFAULT NULL,
  `Schedule` datetime NOT NULL,
  `Max_capacity` int(11) DEFAULT NULL,
  PRIMARY KEY (`C_id`),
  KEY `fk_workout_class_coach1_idx` (`C_id`),
  CONSTRAINT `fk_workout_class_coach1` FOREIGN KEY (`C_id`) REFERENCES `coach` (`C_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Dumping data for table gymerfinal.workout_class: ~1 rows (approximately)
INSERT INTO `workout_class` (`C_id`, `W_name`, `Schedule`, `Max_capacity`) VALUES
	(4, 'Abs', '2024-12-13 07:00:00', 2);

-- Dumping structure for trigger gymerfinal.Check_Classes_Coached_By_Deleted_Coach
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `Check_Classes_Coached_By_Deleted_Coach` BEFORE DELETE ON `coach` FOR EACH ROW BEGIN
    -- Check if the coach is referenced in the booking table
    IF EXISTS (SELECT 1 FROM workout_class WHERE C_id = OLD.C_id) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'There is an active class for this coach in Table(workout_class). Please update or remove the coach of this class before deleting this coach.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.Check_coaches_before_delete
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `Check_coaches_before_delete` BEFORE DELETE ON `gym` FOR EACH ROW BEGIN
    -- Check if the coach is referenced in the booking table
    IF EXISTS (SELECT 1 FROM coach WHERE coach.Bno = OLD.Bno) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'There are coaches in this branch in Table(coach), please move them to another branch before deleting this branch.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.Check_Max_Capacity
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `Check_Max_Capacity` BEFORE INSERT ON `booking` FOR EACH ROW BEGIN
DECLARE current_booking_count INT;

    -- Get the current booking count for the coach (C_id)
    SELECT COUNT(*) INTO current_booking_count
    FROM booking
    WHERE C_id = NEW.C_id;

    -- Check if the count + 1 exceeds the max capacity of the class
    IF current_booking_count + 1 > (
        SELECT Max_capacity
        FROM workout_class
        WHERE C_id = NEW.C_id
    ) THEN
        -- Raise an error if the booking count exceeds the max capacity
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'This workout class is full.';
    END IF;

END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.Check_salary_before_insert
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `Check_salary_before_insert` BEFORE INSERT ON `coach` FOR EACH ROW BEGIN
    IF NEW.Salary < 800 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Salary must be greater than or equal to 800 SAR.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.Check_salary_before_update
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `Check_salary_before_update` BEFORE UPDATE ON `coach` FOR EACH ROW BEGIN
    IF NEW.Salary < 800 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Salary must be greater than or equal to 800 SAR.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.Schedule_Cannot_Be_Before_Now
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER Schedule_Cannot_Be_Before_Now
BEFORE INSERT ON workout_class
FOR EACH ROW
BEGIN
    IF NEW.Schedule < NOW() THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Schedule cannot be earlier than the current time.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.validate_bno_before_insert
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `validate_bno_before_insert` BEFORE INSERT ON `gym` FOR EACH ROW BEGIN
	IF NEW.Bno <= 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Bno cannot be <= 0.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.validate_bno_before_update
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `validate_bno_before_update` BEFORE UPDATE ON `gym` FOR EACH ROW BEGIN
	IF NEW.Bno <= 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Bno cannot be <= 0.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.Validate_capacity_before_insert
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `Validate_capacity_before_insert` BEFORE INSERT ON `workout_class` FOR EACH ROW BEGIN
    IF NEW.Max_capacity <= 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Max capacity cannot be <= 0.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.Validate_capacity_before_update
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `Validate_capacity_before_update` BEFORE UPDATE ON `workout_class` FOR EACH ROW BEGIN
    IF NEW.Max_capacity <= 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Max capacity cannot be <= 0.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.validate_coachid_before_insert
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `validate_coachid_before_insert` BEFORE INSERT ON `coach` FOR EACH ROW BEGIN
	IF NEW.C_id <= 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'ID cannot be <= 0.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.validate_coachid_before_update
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `validate_coachid_before_update` BEFORE UPDATE ON `coach` FOR EACH ROW BEGIN
	IF NEW.C_id <= 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'ID cannot be <= 0.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.validate_coachphone_before_insert
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `validate_coachphone_before_insert` BEFORE INSERT ON `coach` FOR EACH ROW BEGIN
    -- Check if phone number starts with '05'
    IF NEW.C_phone NOT LIKE '05%' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Phone number must begin with 05';
    END IF;

    -- Check if phone number has exactly 10 digits
    IF LENGTH(NEW.C_phone) != 10 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Phone number must have 10 digits.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.validate_coachphone_before_update
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `validate_coachphone_before_update` BEFORE INSERT ON `coach` FOR EACH ROW BEGIN
    -- Check if phone number starts with '05'
    IF NEW.C_phone NOT LIKE '05%' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Phone number must begin with 05';
    END IF;

    -- Check if phone number has exactly 10 digits
    IF LENGTH(NEW.C_phone) != 10 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Phone number must have 10 digits.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.validate_mid_before_insert
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `validate_mid_before_insert` BEFORE INSERT ON `member` FOR EACH ROW BEGIN
	IF NEW.M_id <= 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'ID cannot be <= 0.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.validate_mid_before_update
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `validate_mid_before_update` BEFORE UPDATE ON `member` FOR EACH ROW BEGIN
	IF NEW.M_id <= 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'ID cannot be <= 0.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.validate_mphone_before_insert
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `validate_mphone_before_insert` BEFORE INSERT ON `member` FOR EACH ROW BEGIN
    -- Check if phone number starts with '05'
    IF NEW.M_phone NOT LIKE '05%' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Phone number must begin with 05';
    END IF;

    -- Check if phone number has exactly 10 digits
    IF LENGTH(NEW.M_phone) != 10 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Phone number must have 10 digits.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.validate_mphone_before_update
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `validate_mphone_before_update` BEFORE UPDATE ON `member` FOR EACH ROW BEGIN
    -- Check if phone number starts with '05'
    IF NEW.M_phone NOT LIKE '05%' THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Phone number must begin with 05';
    END IF;

    -- Check if phone number has exactly 10 digits
    IF LENGTH(NEW.M_phone) != 10 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Phone number must have 10 digits.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
