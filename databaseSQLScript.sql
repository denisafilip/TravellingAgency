-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema assignment1
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema assignment1
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `assignment1` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `assignment1` ;

-- -----------------------------------------------------
-- Table `assignment1`.`vacationdestination`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `assignment1`.`vacationdestination` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `UK_sdt4vgxucvbtcbb8o53e2jy8g` ON `assignment1`.`vacationdestination` (`name` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `assignment1`.`vacationpackage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `assignment1`.`vacationpackage` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `endDate` DATE NOT NULL,
  `extraDetails` VARCHAR(255) NOT NULL,
  `maxBookingUsers` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  `price` INT NOT NULL,
  `startDate` DATE NOT NULL,
  `destinationId` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK9vpyfpkxuyi1lbur9u78l2w84`
    FOREIGN KEY (`destinationId`)
    REFERENCES `assignment1`.`vacationdestination` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `UK_s572ann32yvje8ulwlmev0vgd` ON `assignment1`.`vacationpackage` (`name` ASC) VISIBLE;

CREATE INDEX `FK9vpyfpkxuyi1lbur9u78l2w84` ON `assignment1`.`vacationpackage` (`destinationId` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `assignment1`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `assignment1`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `firstName` VARCHAR(255) NOT NULL,
  `lastName` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `UK_e6gkqunxajvyxl5uctpl2vl2p` ON `assignment1`.`user` (`email` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `assignment1`.`booking`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `assignment1`.`booking` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `userId` INT NULL DEFAULT NULL,
  `packageId` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK8qwbtsqwia045s35jbroln4k`
    FOREIGN KEY (`packageId`)
    REFERENCES `assignment1`.`vacationpackage` (`id`),
  CONSTRAINT `FKoso0tvkynct7ljn554rdp193d`
    FOREIGN KEY (`userId`)
    REFERENCES `assignment1`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE INDEX `FKoso0tvkynct7ljn554rdp193d` ON `assignment1`.`booking` (`userId` ASC) VISIBLE;

CREATE INDEX `FK8qwbtsqwia045s35jbroln4k` ON `assignment1`.`booking` (`packageId` ASC) VISIBLE;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
