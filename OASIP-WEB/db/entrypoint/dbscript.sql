create database int222;
create user 'int222'@'%'identified with mysql_native_password by 'int222';
grant all privileges on int222.*to'int222'@'%';

use int222;

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema int222
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `int222` ;

-- -----------------------------------------------------
-- Schema int222
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `int222` DEFAULT CHARACTER SET utf8 ;
set names utf8;
SHOW WARNINGS;
USE `int222` ;

-- -----------------------------------------------------
-- Table `event`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `event` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `event` (
  `BookingID` INT NOT NULL AUTO_INCREMENT,
  `BookingName` VARCHAR(100) NOT NULL,
  `BookingEmail` VARCHAR(100) NOT NULL,
  `EventCategory` VARCHAR(100) NOT NULL,
  `EventStartTime` DATETIME NOT NULL,
  `EventDuration` INT NOT NULL,
  `EventNotes` VARCHAR(500) NULL,
  `EventCategoryID` INT NOT NULL,
  PRIMARY KEY (`BookingID`))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `eventCategory`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eventcategory` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `eventcategory` (
  `EventCategoryID` INT NOT NULL,
  `EventCategoryName` VARCHAR(100) NOT NULL,
  `EventCategoryDescription` VARCHAR(500) NULL,
  `EventDuration` INT NOT NULL,
  PRIMARY KEY (`EventCategoryID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `User` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `User` (
 `UserID` INT NOT NULL AUTO_INCREMENT,
  `UserName` VARCHAR(100) NOT NULL UNIQUE,
  `Email` VARCHAR(50) NOT NULL UNIQUE,
  `Role` ENUM('Admin','Lecturer','Student') DEFAULT 'Student' NOT NULL,
  `CreateOn` TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP,
  `UpdateOn` TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, 
  PRIMARY KEY (`UserID`))
ENGINE = InnoDB;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `Enrollment` (
  `EnrollmenID` INT NOT NULL,
  `UserID` INT NOT NULL UNIQUE,
  `EventCategoryID` INT NOT NULL,
  PRIMARY KEY (`EnrollmenID`))
ENGINE = InnoDB;


SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO eventcategory VALUES (1,'Project Management Clinic','ตารางนัดหมายนี้ใช้สำหรับนัดหมาย project management clinic ในวิชา INT221 integrated project I ให้นักศึกษาเตรียมเอกสารที่เกี่ยวข้องเพื่อแสดงระหว่างขอคำปรึกษา',30);
INSERT INTO eventcategory VALUES (2,'DevOps/Infra Clinic','Use this event category for DevOps/Infra clinic.',20);
INSERT INTO eventcategory VALUES (3,'Database Clinic','ตารางนัดหมายนี้ใช้สำหรับนัดหมาย database clinic ในวิชา INT221 integrated project I',15);
INSERT INTO eventcategory VALUES (4,'Client-side Clinic','ตารางนัดหมายนี้ใช้สำหรับนัดหมาย client-side clinic ในวิชา INT221 integrated project I',30);
INSERT INTO eventcategory VALUES (5,'Server-side Clinic',null,30);

INSERT INTO event values (1,'Somchai Jaidee (OR-7)','somchai.jai@mail.kmutt.com','DevOps/Infra Clinic','2022-05-23:13:30:00',30,null,2);
INSERT INTO event values (2,'Somsri Rakdee (SJ-3)','somsri.rak@mail.kmutt.com','Project Management Clinic','2022-04-27:09:30:00',30,'ขอปรึกษาปัญหาเพื่อนไม่ช่วยงาน',1);
INSERT INTO event values (3,'สมเกียรติ ขยันเรียน กลุ่ม TT-4','somkiat.kay@mail.kmutt.com','Database Clinic','2022-05-23:16:30:00',30,null,3);




INSERT INTO User(UserID,UserName,Email,Role)values(1,'สมส่วน สุขศรี','somsuan.s@kmutt.ac.th','Lecturer');
INSERT INTO User(UserID,UserName,Email)values(2,'กัญญาพัขร','kanyapat.w@kmutt.ac.th');



