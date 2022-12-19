create database int222;
create user 'int222'@'%'identified with mysql_native_password by 'int222';
grant all privileges on int222.*to'int222'@'%';
 
 
 
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
 `FileName` VARCHAR(500) NULL,
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
-- Table `EventCategoryOwner`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `EventCategoryOwner` ;
SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `EventCategoryOwner` (
 `EventCategoryOwnerID` INT NOT NULL AUTO_INCREMENT,
 `EventCategoryID` INT NOT NULL,
 `UserID` INT NOT NULL,
 PRIMARY KEY (`EventCategoryOwnerID`),
FOREIGN KEY (EventCategoryID) REFERENCES eventcategory (EventCategoryID),
FOREIGN KEY (UserID) REFERENCES User (UserID)
 )
ENGINE = InnoDB;
 
SHOW WARNINGS;
 
-- -----------------------------------------------------
-- Table `User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `User` ;
 
SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `User` (
`UserID` INT NOT NULL AUTO_INCREMENT,
 `UserName` VARCHAR(100) NOT NULL UNIQUE,
 `Email` VARCHAR(50) NOT NULL UNIQUE,
`Password` VARCHAR(100) NOT NULL,
 `Role` ENUM('Admin','Lecturer','Student') DEFAULT 'Student' NOT NULL,
 `CreatedOn` TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP,
 `UpdatedOn` TIMESTAMP NOT NULL  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (`UserID`))
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
 
INSERT INTO event values (1,'Somchai Jaidee (OR-7)','somchai.jai@mail.kmutt.com','DevOps/Infra Clinic','2022-05-23:13:30:00',30,null,null,2);
INSERT INTO event values (2,'Somsri Rakdee (SJ-3)','somsri.rak@mail.kmutt.com','Project Management Clinic','2022-04-27:09:30:00',30,'ขอปรึกษาปัญหาเพื่อนไม่ช่วยงาน',null,1);
INSERT INTO event values (3,'สมเกียรติ ขยันเรียน กลุ่ม TT-4','somkiat.kay@kmutt.ac.th','Database Clinic','2022-05-23:16:30:00',30,null,null,3);
 
INSERT INTO
 User (
   UserName,
   Email,
   Password,
   Role,
   CreatedOn,
   UpdatedOn
 )
VALUES
 (
   'OASIP ADMIN',
   'oasip.admin@kmutt.ac.th',
   '$argon2id$v=19$m=4096,t=3,p=1$sYXzbUOqBoHY1NfhJ8cjnw$H6+adWySiFPgcUogJK3hEhcF6Y4fusy7tcXYEL+f0cQ',
   'admin',
   '2022-08-01 00:00:00+07:00',
   '2022-08-01 00:00:00+07:00'
 ),
 (
   'Olarn Rojanapornpun',
   'olarn.roj@kmutt.ac.th',
   '$argon2id$v=19$m=4096,t=3,p=1$Sx7y2jxKZSjpWUV4srd8eg$AMH09iFiPQgAZ00cAdN3Gucqfhx2kRo3tQbHeLSR0RE',
   'lecturer',
   '2022-08-08 15:00:00+07:00',
   '2022-08-08 15:00:00+07:00'
 ),
 (
   'Pichet Limvachiranan',
   'pichet.limv@kmutt.ac.th',
   '$argon2id$v=19$m=4096,t=3,p=1$46EB43gQ46Z1/EmdqxtKNA$7m6cWGO2iDlFl/ETDYuYf+ArnSjRnsNwXLIP18DTYQY',
   'lecturer',
   '2022-08-08 15:00:01+07:00',
   '2022-08-08 15:00:01+07:00'
 ),
 (
   'Umaporn Supasitthimethee',
   'umaporn.sup@kmutt.ac.th',
   '$argon2id$v=19$m=4096,t=3,p=1$1Z2UK1zC76FIQeLH54GVAQ$qfXcHF31LnuWpt37QAcWyNp8PdbOQ+jjaV1xWXixS0M',
   'lecturer',
   '2022-08-08 15:00:02+07:00',
   '2022-08-08 15:00:02+07:00'
 ),
 (
   'Siam Yamsaengsung',
   'siam.yam@kmutt.ac.th',
   '$argon2id$v=19$m=4096,t=3,p=1$C4pPaNWKTnZQX2mPs14jlg$rQ5W5NYKqGOu1B4GkUWq8cFbcg2peFWGjpUMr9Nkm8g',
   'lecturer',
   '2022-08-08 15:00:03+07:00',
   '2022-08-08 15:00:03+07:00'
 ),
 (
   'Sunisa Sathapornvajana',
   'sunisa.sat@kmutt.ac.th',
   '$argon2id$v=19$m=4096,t=3,p=1$29/ffaszvjvi3CZO45bSCg$kKpfq5WEswoqa/LfyIZzQaQ6AFdjhyiYjXRCfMiTnwg',
   'lecturer',
   '2022-08-08 15:00:04+07:00',
   '2022-08-08 15:00:04+07:00'
 ),
 (
   'Somchai Jaidee',
   'somchai.jai@kmutt.ac.th',
   '$argon2id$v=19$m=4096,t=3,p=1$dmsOy7LPTjmooPu+P2oTZA$NZFTFd3f0K1Sp19aaUwyn3jgiy15yFcXhp8E4/1yXoI',
   'student',
   '2022-08-08 16:00:00+07:00',
   '2022-08-08 16:00:00+07:00'
 ),
 (
   'Komkrid Rakdee',
   'komkrid.rak@mail.kmutt.ac.th',
   '$argon2id$v=19$m=4096,t=3,p=1$8W61ZOC5RU7sJP5kKRbSqg$OLwZNPeMqxp+g0Vbn+odcA47XMClFN+IswTueVah7F0',
   'student',
   '2022-08-08 16:00:00+07:00',
   '2022-08-08 16:00:00+07:00'
 ),
 (
   'สมเกียรติ ขยันเรียน',
   'somkiat.kay@kmutt.ac.th',
   '$argon2id$v=19$m=4096,t=3,p=1$gBqgjspF45FcIKQEw8GmaQ$alrOCZ0YrDqOu8/aZiLDMGZo4vFkSEAXA0YoHhY0BDQ',
   'student',
   '2022-08-16 09:00:00+07:00',
   '2022-08-16 09:00:00+07:00'
 ),
 (
   'Charidaporn Chuangdonphai',
   'charidaporn.ch@mail.kmutt.ac.th',
   '$argon2id$v=19$m=12,t=3,p=1$amp3c3NjMXJma2cwMDAwMA$9SP1/kJ71/jW7QzWJ2e36g',
   'admin',
   '2022-10-15 17:00:00+07:00',
   '2022-10-15 17:00:00+07:00'
 ),
 (
   'Kanyapat Wittayamanitkul',
   'kanyapat.winnerkypt@mail.kmutt.ac.th',
  '$argon2id$v=19$m=12,t=3,p=1$dzF6b21wOTV4ZTkwMDAwMA$+cYfCxPb7e3q7wetPbJWVw',
  'student',
   '2022-10-15 17:00:00+07:00',
   '2022-10-15 17:00:00+07:00'
 );
 
INSERT INTO
 EventCategoryOwner (EventCategoryID, UserID)
VALUES (1,2),(2,5),(2,2),(3,6),(4,4),(5,3),(5,2);
 
 
 
 
 


