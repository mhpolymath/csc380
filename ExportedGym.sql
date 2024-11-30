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
  `Class_date` date NOT NULL,
  `Class_time` time NOT NULL,
  PRIMARY KEY (`M_id`,`C_id`,`Class_date`,`Class_time`),
  KEY `fk_member_has_workout_class_workout_class1_idx` (`C_id`,`Class_date`,`Class_time`),
  KEY `fk_member_has_workout_class_member1_idx` (`M_id`),
  CONSTRAINT `fk_member_has_workout_class_member1` FOREIGN KEY (`M_id`) REFERENCES `member` (`M_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_member_has_workout_class_workout_class1` FOREIGN KEY (`C_id`, `Class_date`, `Class_time`) REFERENCES `workout_class` (`C_id`, `Class_date`, `Class_time`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Dumping data for table gymerfinal.booking: ~6 rows (approximately)
DELETE FROM `booking`;
INSERT INTO `booking` (`M_id`, `C_id`, `Class_date`, `Class_time`) VALUES
	(2, 8, '2024-11-30', '20:00:00'),
	(2, 15, '2024-11-28', '13:30:00'),
	(3, 15, '2024-11-28', '13:30:00'),
	(4, 15, '2024-11-28', '13:30:00'),
	(5, 15, '2024-11-28', '13:30:00'),
	(6, 8, '2024-11-30', '20:00:00');

-- Dumping structure for table gymerfinal.coach
DROP TABLE IF EXISTS `coach`;
CREATE TABLE IF NOT EXISTS `coach` (
  `C_id` int(11) NOT NULL,
  `C_Fname` varchar(45) DEFAULT NULL,
  `C_Lname` varchar(45) DEFAULT NULL,
  `C_phone` varchar(10) DEFAULT NULL,
  `Salary` double DEFAULT NULL,
  `Bno` int(11) NOT NULL,
  PRIMARY KEY (`C_id`),
  UNIQUE KEY `C_phone` (`C_phone`),
  KEY `fk_coach_gym1_idx` (`Bno`),
  CONSTRAINT `fk_coach_gym1` FOREIGN KEY (`Bno`) REFERENCES `gym` (`Bno`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Dumping data for table gymerfinal.coach: ~10 rows (approximately)
DELETE FROM `coach`;
INSERT INTO `coach` (`C_id`, `C_Fname`, `C_Lname`, `C_phone`, `Salary`, `Bno`) VALUES
	(2, 'Mark', 'Smith', '0534567890', 1000, 4),
	(3, 'Liam', 'Johnson', '0541239876', 5000, 33),
	(8, 'James', 'Taylor', '0538877665', 1000, 5),
	(11, 'Thomas', 'White', '0561122334', 2000, 6),
	(12, 'Ryan', 'Harris', '0531122334', 4000, 33),
	(13, 'Ahmed', 'Omar', '0584433221', 900, 6),
	(14, 'Zaid', NULL, '0559988777', 1000, 5),
	(15, 'Chris', 'Taylor', NULL, 900, 4),
	(92, 'KAKA', NULL, NULL, NULL, 6),
	(112, 'Khalid', 'Obaidah', '0501247652', NULL, 6);

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
  CONSTRAINT `fk_FITNESS_DATA_MEMBER1` FOREIGN KEY (`M_id`) REFERENCES `member` (`M_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Dumping data for table gymerfinal.fitness_data: ~0 rows (approximately)
DELETE FROM `fitness_data`;

-- Dumping structure for table gymerfinal.gym
DROP TABLE IF EXISTS `gym`;
CREATE TABLE IF NOT EXISTS `gym` (
  `Bno` int(11) NOT NULL,
  `B_name` varchar(45) DEFAULT NULL,
  `Address` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Bno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Dumping data for table gymerfinal.gym: ~5 rows (approximately)
DELETE FROM `gym`;
INSERT INTO `gym` (`Bno`, `B_name`, `Address`) VALUES
	(4, 'Al Sulaymaniyah', 'Tahlia Street, Riyadh, KSA'),
	(5, 'Al Yasmin', 'King Salman Road, Riyadh, KSA'),
	(6, 'Diriyah', 'Historic Diryah, Riyadh'),
	(33, 'Jarir', 'King Abdullah Park, Riyadh, KSA'),
	(34, 'Rawabi', 'Abdu,Jeddah,KSA'),
	(55, 'Khaldiyah', 'Nassim,Jeddah');

-- Dumping structure for table gymerfinal.member
DROP TABLE IF EXISTS `member`;
CREATE TABLE IF NOT EXISTS `member` (
  `M_id` int(11) NOT NULL,
  `M_Fname` varchar(45) DEFAULT NULL,
  `M_Lname` varchar(45) DEFAULT NULL,
  `M_phone` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`M_id`),
  UNIQUE KEY `M_phone` (`M_phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Dumping data for table gymerfinal.member: ~7 rows (approximately)
DELETE FROM `member`;
INSERT INTO `member` (`M_id`, `M_Fname`, `M_Lname`, `M_phone`) VALUES
	(1, 'Faisal', 'Alharbi', '0501010550'),
	(2, 'Abdullah', 'Alqahtani', '0539876543'),
	(3, 'Mohammed', 'Alshehri', '0548765432'),
	(4, 'Sultan', 'Aldosari', '0561122334'),
	(5, 'Turki', 'Almutairi', '0554455667'),
	(6, 'Saud', 'Aljuhani', '0583344556'),
	(99, 'Abdulkhaliq', 'Khan', '0501010551');

-- Dumping structure for table gymerfinal.membership_at
DROP TABLE IF EXISTS `membership_at`;
CREATE TABLE IF NOT EXISTS `membership_at` (
  `M_id` int(11) NOT NULL,
  `Bno` int(11) NOT NULL,
  `Start_date` date NOT NULL,
  `End_date` date DEFAULT NULL,
  PRIMARY KEY (`M_id`,`Bno`,`Start_date`) USING BTREE,
  KEY `fk_member_has_gym_gym1_idx` (`Bno`),
  KEY `fk_member_has_gym_member1_idx` (`M_id`),
  CONSTRAINT `fk_member_has_gym_gym1` FOREIGN KEY (`Bno`) REFERENCES `gym` (`Bno`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_member_has_gym_member1` FOREIGN KEY (`M_id`) REFERENCES `member` (`M_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Dumping data for table gymerfinal.membership_at: ~2 rows (approximately)
DELETE FROM `membership_at`;
INSERT INTO `membership_at` (`M_id`, `Bno`, `Start_date`, `End_date`) VALUES
	(99, 33, '2024-01-01', '2024-11-16'),
	(99, 33, '2024-11-21', '2024-12-01');

-- Dumping structure for table gymerfinal.workout_class
DROP TABLE IF EXISTS `workout_class`;
CREATE TABLE IF NOT EXISTS `workout_class` (
  `C_id` int(11) NOT NULL,
  `W_name` varchar(45) DEFAULT NULL,
  `Class_date` date NOT NULL,
  `Class_time` time NOT NULL,
  `Max_capacity` int(11) NOT NULL DEFAULT 5,
  PRIMARY KEY (`C_id`,`Class_date`,`Class_time`) USING BTREE,
  UNIQUE KEY `C_id` (`C_id`),
  KEY `fk_workout_class_coach1_idx` (`C_id`),
  CONSTRAINT `fk_workout_class_coach1` FOREIGN KEY (`C_id`) REFERENCES `coach` (`C_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- Dumping data for table gymerfinal.workout_class: ~3 rows (approximately)
DELETE FROM `workout_class`;
INSERT INTO `workout_class` (`C_id`, `W_name`, `Class_date`, `Class_time`, `Max_capacity`) VALUES
	(8, 'Maxabs', '2024-11-30', '20:00:00', 8),
	(13, 'Chest', '2024-11-29', '22:00:00', 7),
	(15, 'Abs', '2024-11-28', '13:30:00', 5);

-- Dumping structure for trigger gymerfinal.check_active_memberships_insert
DROP TRIGGER IF EXISTS `check_active_memberships_insert`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `check_active_memberships_insert` BEFORE INSERT ON `membership_at` FOR EACH ROW BEGIN
    IF EXISTS (SELECT 1 FROM membership_at M WHERE M.M_id = NEW.M_id 
	 AND M.Bno = NEW.Bno 
	 AND CURDATE() >= M.Start_date AND  CURDATE() <= M.End_date)
	 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'There is an active membership for this member at this branch.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.check_bf%_before_insert
DROP TRIGGER IF EXISTS `check_bf%_before_insert`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `check_bf%_before_insert` BEFORE INSERT ON `fitness_data` FOR EACH ROW BEGIN
    IF NEW.Fat_rate NOT BETWEEN 1 AND 80 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Body fat percentage must be between 1% and 80%.';
    END IF;
      IF IFNULL(NEW.Fat_rate,0) + (IFNULL(NEW.Muscle_mass,0)/NEW.Weight)*100 > 85 THEN
	  	  SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Combined muscle mass and body fat mass cannot exceed 85% of total weight.';
		END IF;

END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.check_bf%_before_update
DROP TRIGGER IF EXISTS `check_bf%_before_update`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `check_bf%_before_update` BEFORE UPDATE ON `fitness_data` FOR EACH ROW BEGIN
    IF NEW.Fat_rate NOT BETWEEN 1 AND 80 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Body fat percentage must be between 1% and 80%.';
    END IF;
       IF IFNULL(NEW.Fat_rate,0) + (IFNULL(NEW.Muscle_mass,0)/NEW.Weight)*100 > 85 THEN
	  	  SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Combined muscle mass and body fat mass cannot exceed 85% of total weight.';
		END IF;


END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.Check_Classes_Coached_By_Deleted_Coach
DROP TRIGGER IF EXISTS `Check_Classes_Coached_By_Deleted_Coach`;
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

-- Dumping structure for trigger gymerfinal.Check_coaches_before_branch_delete
DROP TRIGGER IF EXISTS `Check_coaches_before_branch_delete`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `Check_coaches_before_branch_delete` BEFORE DELETE ON `gym` FOR EACH ROW BEGIN
    -- Check if the coach is referenced in the booking table
    IF EXISTS (SELECT 1 FROM coach WHERE coach.Bno = OLD.Bno) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'There are coaches in this branch in Table(coach), please move them to another branch before deleting this branch.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.check_healthrecord_before_insert
DROP TRIGGER IF EXISTS `check_healthrecord_before_insert`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `check_healthrecord_before_insert` BEFORE INSERT ON `fitness_data` FOR EACH ROW -- Checks if the date is <= TODAY(), because you cannot record your health data in the future.
BEGIN

    IF NEW.Record_date > CURDATE() THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Date cannot be later than today.';
    END IF;

END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.check_healthrecord_before_update
DROP TRIGGER IF EXISTS `check_healthrecord_before_update`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `check_healthrecord_before_update` BEFORE UPDATE ON `fitness_data` FOR EACH ROW -- Checks if the date is <= TODAY(), because you cannot record your health data in the future.
BEGIN

    IF NEW.Record_date > CURDATE() THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Date cannot be later than today.';
    END IF;

END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.check_height_before_insert
DROP TRIGGER IF EXISTS `check_height_before_insert`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `check_height_before_insert` BEFORE INSERT ON `fitness_data` FOR EACH ROW BEGIN


    IF NEW.Height NOT BETWEEN 0.5 AND 3 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Height must be between 0.5m and 3m.';
    END IF;

END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.check_height_before_update
DROP TRIGGER IF EXISTS `check_height_before_update`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `check_height_before_update` BEFORE UPDATE ON `fitness_data` FOR EACH ROW BEGIN
    IF NEW.Height NOT BETWEEN 0.5 AND 3 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Height must be between 0.5m and 3m.';
    END IF;

END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.check_max_capacity_before_insert
DROP TRIGGER IF EXISTS `check_max_capacity_before_insert`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `check_max_capacity_before_insert` BEFORE INSERT ON `booking` FOR EACH ROW BEGIN
    -- Declare a variable to store current booking count for this workout

   
   IF ((SELECT COUNT(*) 
    FROM booking
    WHERE C_id = NEW.C_id
    AND Class_date = NEW.Class_date
    AND Class_time = NEW.Class_time) + 1) 
	 
	 > 
	 
	 (SELECT Max_capacity
    FROM workout_class
    WHERE C_id = NEW.C_id
    AND Class_date = NEW.Class_date
    AND Class_time = NEW.Class_time)
    
 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'This workout class is full.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.check_max_capacity_before_update
DROP TRIGGER IF EXISTS `check_max_capacity_before_update`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `check_max_capacity_before_update` BEFORE UPDATE ON `booking` FOR EACH ROW BEGIN

   IF ((SELECT COUNT(*) 
    FROM booking
    WHERE C_id = NEW.C_id
    AND Class_date = NEW.Class_date
    AND Class_time = NEW.Class_time) + 1) 
	 
	 > 
	 
	 (SELECT Max_capacity
    FROM workout_class
    WHERE C_id = NEW.C_id
    AND Class_date = NEW.Class_date
    AND Class_time = NEW.Class_time)
    
 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'This workout class is full.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.check_members_before_branch_delete
DROP TRIGGER IF EXISTS `check_members_before_branch_delete`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `check_members_before_branch_delete` BEFORE DELETE ON `gym` FOR EACH ROW BEGIN
    IF EXISTS (SELECT 1 FROM membership_at M WHERE M.Bno = OLD.Bno AND CURDATE() <= IFNULL(M.End_date,CURDATE())) 
	 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'There are active members in this gym branch in Table(membership_at), 
		  please move them to another branch before deleting this branch.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.check_muscle_before_insert
DROP TRIGGER IF EXISTS `check_muscle_before_insert`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `check_muscle_before_insert` BEFORE INSERT ON `fitness_data` FOR EACH ROW BEGIN
    IF (NEW.Muscle_mass/NEW.Weight)*100 NOT BETWEEN 15 AND 70 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Muscle to total weight ratio must be between 15% and 70%.';
    END IF;
       IF IFNULL(NEW.Fat_rate,0) + (IFNULL(NEW.Muscle_mass,0)/NEW.Weight)*100 > 85 THEN
	  	  SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Combined muscle mass and body fat mass cannot exceed 85% of total weight.';
		END IF;


END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.check_muscle_before_update
DROP TRIGGER IF EXISTS `check_muscle_before_update`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `check_muscle_before_update` BEFORE INSERT ON `fitness_data` FOR EACH ROW BEGIN
    IF (NEW.Muscle_mass/NEW.Weight)*100 NOT BETWEEN 15 AND 70 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Muscle to total weight ratio must be between 15% and 70%.';
    END IF;
      IF IFNULL(NEW.Fat_rate,0) + (IFNULL(NEW.Muscle_mass,0)/NEW.Weight)*100 > 85 THEN
	  	  SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Combined muscle mass and body fat mass cannot exceed 85% of total weight.';
		END IF;


END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.Check_salary_before_insert
DROP TRIGGER IF EXISTS `Check_salary_before_insert`;
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
DROP TRIGGER IF EXISTS `Check_salary_before_update`;
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

-- Dumping structure for trigger gymerfinal.Validate_capacity_before_update
DROP TRIGGER IF EXISTS `Validate_capacity_before_update`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `Validate_capacity_before_update` BEFORE UPDATE ON `workout_class` FOR EACH ROW -- Validates negative capacity, or if updating capacity and there are more people booking the class 
-- than the current capacity.
BEGIN 
	DECLARE current_booking_count INT;
    IF NEW.Max_capacity <= 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Max capacity cannot be <= 0.';
    END IF;
    

    SELECT COUNT(*) INTO current_booking_count
    FROM booking
    WHERE booking.C_id = NEW.C_id;
	
    IF NEW.Max_capacity < current_booking_count THEN
        SET @message_text = CONCAT('Cannot set max capacity to ',NEW.Max_capacity,' currently booked by '
		  ,current_booking_count, ' members.');
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = @MESSAGE_TEXT;
        
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.validate_coachphone_before_insert
DROP TRIGGER IF EXISTS `validate_coachphone_before_insert`;
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
DROP TRIGGER IF EXISTS `validate_coachphone_before_update`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `validate_coachphone_before_update` BEFORE UPDATE ON `coach` FOR EACH ROW BEGIN
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

-- Dumping structure for trigger gymerfinal.validate_membership_dates_insert
DROP TRIGGER IF EXISTS `validate_membership_dates_insert`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `validate_membership_dates_insert` BEFORE INSERT ON `membership_at` FOR EACH ROW BEGIN


    IF NEW.Start_date > NEW.End_date THEN
    		SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Start date cannot be after end date.';
    END IF;

END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.validate_membership_date_update
DROP TRIGGER IF EXISTS `validate_membership_date_update`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `validate_membership_date_update` BEFORE UPDATE ON `membership_at` FOR EACH ROW BEGIN
    
    IF NEW.Start_date > NEW.End_date THEN
    		SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Start date cannot be after end date.';
    END IF;


END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.validate_mphone_before_insert
DROP TRIGGER IF EXISTS `validate_mphone_before_insert`;
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
DROP TRIGGER IF EXISTS `validate_mphone_before_update`;
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

-- Dumping structure for trigger gymerfinal.validate_negative_bno_before_insert
DROP TRIGGER IF EXISTS `validate_negative_bno_before_insert`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `validate_negative_bno_before_insert` BEFORE INSERT ON `gym` FOR EACH ROW BEGIN
	IF NEW.Bno <= 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Bno cannot be <= 0.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.validate_negative_bno_before_update
DROP TRIGGER IF EXISTS `validate_negative_bno_before_update`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `validate_negative_bno_before_update` BEFORE UPDATE ON `gym` FOR EACH ROW BEGIN
	IF NEW.Bno <= 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Bno cannot be <= 0.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.Validate_negative_capacity_before_insert
DROP TRIGGER IF EXISTS `Validate_negative_capacity_before_insert`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `Validate_negative_capacity_before_insert` BEFORE INSERT ON `workout_class` FOR EACH ROW BEGIN
    IF NEW.Max_capacity <= 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Max capacity cannot be <= 0.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.validate_negative_coachid_before_insert
DROP TRIGGER IF EXISTS `validate_negative_coachid_before_insert`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `validate_negative_coachid_before_insert` BEFORE INSERT ON `coach` FOR EACH ROW BEGIN
	IF NEW.C_id <= 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'ID cannot be <= 0.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.validate_negative_coachid_before_update
DROP TRIGGER IF EXISTS `validate_negative_coachid_before_update`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `validate_negative_coachid_before_update` BEFORE UPDATE ON `coach` FOR EACH ROW BEGIN
	IF NEW.C_id <= 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'ID cannot be <= 0.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.validate_negative_mid_before_insert
DROP TRIGGER IF EXISTS `validate_negative_mid_before_insert`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `validate_negative_mid_before_insert` BEFORE INSERT ON `member` FOR EACH ROW BEGIN
	IF NEW.M_id <= 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'ID cannot be <= 0.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger gymerfinal.validate_negative_mid_before_update
DROP TRIGGER IF EXISTS `validate_negative_mid_before_update`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `validate_negative_mid_before_update` BEFORE UPDATE ON `member` FOR EACH ROW BEGIN
	IF NEW.M_id <= 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'ID cannot be <= 0.';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
