-- MySQL Script generated by MySQL Workbench
-- Wed Apr 27 14:21:57 2022
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `mydb` ;

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `scheduledb` DEFAULT CHARACTER SET utf8 ;
set names utf8;
SHOW WARNINGS;
USE `scheduledb` ;

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

SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- INSERT INTO eventcategory VALUES (1,'Project Management Clinic','ตารางนัดหมายนี้ใช้สำหรับนัดหมาย project management clinic ในวิชา INT221 integrated project I ให้นักศึกษาเตรียมเอกสารที่เกี่ยวข้องเพื่อแสดงระหว่างขอคำปรึกษา',30);
-- INSERT INTO eventcategory VALUES (2,'DevOps/Infra Clinic','Use this event category for DevOps/Infra clinic.',20);
-- INSERT INTO eventcategory VALUES (3,'Database Clinic','ตารางนัดหมายนี้ใช้สำหรับนัดหมาย database clinic ในวิชา INT221 integrated project I',15);
-- INSERT INTO eventcategory VALUES (4,'Client-side Clinic','ตารางนัดหมายนี้ใช้สำหรับนัดหมาย client-side clinic ในวิชา INT221 integrated project I',30);
-- INSERT INTO eventcategory VALUES (5,'Server-side Clinic',null,30);

INSERT INTO event values (1,'Somchai Jaidee (OR-7)','somchai.jai@mail.kmutt.com','DevOps/Infra Clinic','2022-05-23:13:30:00',30,null,2);
INSERT INTO event values (2,'Somsri Rakdee (SJ-3)','somsri.rak@mail.kmutt.com','Project Management Clinic','2022-04-27:09:30:00',30,'ขอปรึกษาปัญหาเพื่อนไม่ช่วยงาน',1);
INSERT INTO event values (3,'สมเกียรติ ขยันเรียน กลุ่ม TT-4','somkiat.kay@mail.kmutt.com','Database Clinic','2022-05-23:16:30:00',30,null,3);

INSERT INTO eventcategory VALUES (1,'Project Management Clinic','ตารางนัดหมายนี้ใช้สำหรับนัดหมาย project management clinic ในวิชา INT221 integrated project I ให้นักศึกษาเตรียมเอกสารที่เกี่ยวข้องเพื่อแสดงระหว่างขอคำปรึกษา',30);
INSERT INTO eventcategory VALUES (2,'DevOps/Infra Clinic','Use this event category for DevOps/Infra clinic.',20);
INSERT INTO eventcategory VALUES (3,'Database Clinic','ตารางนัดหมายนี้ใช้สำหรับนัดหมาย database clinic ในวิชา INT221 integrated project I',15);
INSERT INTO eventcategory VALUES (4,'Business Requirements',null,30);
INSERT INTO eventcategory VALUES (5,'Server-side Clinic',null,30);
INSERT INTO eventcategory VALUES (6,'Client-side Clinic','ตารางนัดหมายนี้ใช้สำหรับนัดหมาย client-side clinic ในวิชา INT221 integrated project I',30);

