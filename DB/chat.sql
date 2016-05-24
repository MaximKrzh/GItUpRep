-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema chat
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `chat` ;

-- -----------------------------------------------------
-- Schema chat
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `chat` DEFAULT CHARACTER SET utf8 ;
USE `chat` ;

-- -----------------------------------------------------
-- Table `chat`.`Users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chat`.`Users` ;

CREATE TABLE IF NOT EXISTS `chat`.`Users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `chat`.`Messages`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `chat`.`Messages` ;

CREATE TABLE IF NOT EXISTS `chat`.`Messages` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `text` VARCHAR(140) NULL,
  `date` DATETIME NOT NULL DEFAULT NOW( ),
  `user_id` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `user_id_idx` (`user_id` ASC),
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `chat`.`Users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `chat`.`Users`
-- -----------------------------------------------------
START TRANSACTION;
USE `chat`;
INSERT INTO `chat`.`Users` (`id`, `name`) VALUES (1, 'nick');
INSERT INTO `chat`.`Users` (`id`, `name`) VALUES (2, 'name');
INSERT INTO `chat`.`Users` (`id`, `name`) VALUES (3, 'name1');
INSERT INTO `chat`.`Users` (`id`, `name`) VALUES (4, 'name4');
INSERT INTO `chat`.`Users` (`id`, `name`) VALUES (5, 'name5');
INSERT INTO `chat`.`Users` (`id`, `name`) VALUES (6, 'name6');
INSERT INTO `chat`.`Users` (`id`, `name`) VALUES (7, 'name7');
INSERT INTO `chat`.`Users` (`id`, `name`) VALUES (8, 'qwert');
INSERT INTO `chat`.`Users` (`id`, `name`) VALUES (9, 'nnn');
INSERT INTO `chat`.`Users` (`id`, `name`) VALUES (10, 'lastname');

COMMIT;


-- -----------------------------------------------------
-- Data for table `chat`.`Messages`
-- -----------------------------------------------------
START TRANSACTION;
USE `chat`;
INSERT INTO `chat`.`Messages` (`id`, `text`, `date`, `user_id`) VALUES (1, 'tttext', DEFAULT, 2);
INSERT INTO `chat`.`Messages` (`id`, `text`, `date`, `user_id`) VALUES (2, 'new_t', DEFAULT, 2);
INSERT INTO `chat`.`Messages` (`id`, `text`, `date`, `user_id`) VALUES (3, 'sd', DEFAULT, 1);
INSERT INTO `chat`.`Messages` (`id`, `text`, `date`, `user_id`) VALUES (4, 'fff', DEFAULT, 3);
INSERT INTO `chat`.`Messages` (`id`, `text`, `date`, `user_id`) VALUES (5, 'dsddasd', DEFAULT, 4);
INSERT INTO `chat`.`Messages` (`id`, `text`, `date`, `user_id`) VALUES (6, 'as', DEFAULT, 5);
INSERT INTO `chat`.`Messages` (`id`, `text`, `date`, `user_id`) VALUES (7, 'wewewewe', DEFAULT, 6);
INSERT INTO `chat`.`Messages` (`id`, `text`, `date`, `user_id`) VALUES (8, 'asassssss', DEFAULT, 3);
INSERT INTO `chat`.`Messages` (`id`, `text`, `date`, `user_id`) VALUES (9, 'fffffffff', DEFAULT, 1);
INSERT INTO `chat`.`Messages` (`id`, `text`, `date`, `user_id`) VALUES (10, '100000000000000', DEFAULT, 2);

COMMIT;

