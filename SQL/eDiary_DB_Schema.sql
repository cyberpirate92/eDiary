CREATE DATABASE IF NOT EXISTS `eDiary`

CREATE TABLE IF NOT EXISTS `eDiary`.`entries` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL COMMENT 'The user who has created this journal entry',
  `entry` TEXT NULL DEFAULT NULL COMMENT 'The hill cipher encrypted journal entry of user ‘username’ for the date ‘entry_date’',
  `entry_date` BIGINT(50) NOT NULL COMMENT 'The date for which this journal entry was created',
  `last_edited` BIGINT(50) NOT NULL COMMENT 'The date at which this entry was last edited',
  `entry_day` DATE NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `entry_day_UNIQUE` (`entry_day` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = latin1


CREATE TABLE IF NOT EXISTS `eDiary`.`users` (
  `ID` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `enckey` VARCHAR(5) NOT NULL COMMENT 'The encryption key to be used for storing the user’s journal data. The encryption key is off 4 letter and the cipher used is Hill cipher.',
  `question` VARCHAR(255) NOT NULL,
  `answer` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = latin1
