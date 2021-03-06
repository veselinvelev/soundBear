-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema soundbear
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema soundbear
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `soundbear` DEFAULT CHARACTER SET utf8 ;
USE `soundbear` ;

-- -----------------------------------------------------
-- Table `soundbear`.`artists`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundbear`.`artists` (
  `artist_id` INT(11) NOT NULL AUTO_INCREMENT,
  `artist_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`artist_id`),
  UNIQUE INDEX `artist_UNIQUE` (`artist_name` ASC),
  INDEX `artist` (`artist_name` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 0
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `soundbear`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundbear`.`users` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `is_active` TINYINT(1) NULL DEFAULT NULL,
  `registration_date` DATETIME NULL DEFAULT NULL,
  `path_photo` VARCHAR(200) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC),
  UNIQUE INDEX `usename_UNIQUE` (`username` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 0
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `soundbear`.`follows`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundbear`.`follows` (
  `user_id` INT(11) NOT NULL,
  `following` INT(11) NOT NULL,
  PRIMARY KEY (`user_id`, `following`),
  INDEX `fk_users_has_users_users1_idx` (`following` ASC),
  INDEX `fk_users_has_users_users_idx` (`user_id` ASC),
  CONSTRAINT `fk_users_has_users_users`
    FOREIGN KEY (`user_id`)
    REFERENCES `soundbear`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_has_users_users1`
    FOREIGN KEY (`following`)
    REFERENCES `soundbear`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `soundbear`.`genres`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundbear`.`genres` (
  `genre_id` INT(11) NOT NULL AUTO_INCREMENT,
  `genre_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`genre_id`),
  UNIQUE INDEX `genre_UNIQUE` (`genre_name` ASC),
  INDEX `genre_index` (`genre_name` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 0
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `soundbear`.`playlists`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundbear`.`playlists` (
  `playlist_id` INT(11) NOT NULL AUTO_INCREMENT,
  `playlist_name` VARCHAR(45) NOT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`playlist_id`),
  INDEX `fk_playlists_users1_idx` (`user_id` ASC),
  CONSTRAINT `fk_playlists_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `soundbear`.`users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 0
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `soundbear`.`songs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundbear`.`songs` (
  `song_id` INT(11) NOT NULL AUTO_INCREMENT,
  `song_name` VARCHAR(45) NOT NULL,
  `path` VARCHAR(200) NOT NULL,
  `user_id` INT(11) NOT NULL,
  `genre_id` INT(11) NOT NULL,
  `artist_id` INT(11) NOT NULL,
  PRIMARY KEY (`song_id`),
  UNIQUE INDEX `path_UNIQUE` (`path` ASC),
  INDEX `fk_songs_users_idx` (`user_id` ASC),
  INDEX `fk_songs_genres1_idx` (`genre_id` ASC),
  INDEX `song_name` (`song_name` ASC),
  INDEX `fk_songs_artists1_idx` (`artist_id` ASC),
  CONSTRAINT `fk_songs_artists1`
    FOREIGN KEY (`artist_id`)
    REFERENCES `soundbear`.`artists` (`artist_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_songs_genres1`
    FOREIGN KEY (`genre_id`)
    REFERENCES `soundbear`.`genres` (`genre_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_songs_users`
    FOREIGN KEY (`user_id`)
    REFERENCES `soundbear`.`users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 0
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `soundbear`.`playlists_has_songs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundbear`.`playlists_has_songs` (
  `playlist_id` INT(11) NOT NULL,
  `song_id` INT(11) NOT NULL,
  PRIMARY KEY (`playlist_id`, `song_id`),
  INDEX `fk_playlists_has_songs_songs1_idx` (`song_id` ASC),
  INDEX `fk_playlists_has_songs_playlists1_idx` (`playlist_id` ASC),
  CONSTRAINT `playlist_id`
    FOREIGN KEY (`playlist_id`)
    REFERENCES `soundbear`.`playlists` (`playlist_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `song_id`
    FOREIGN KEY (`song_id`)
    REFERENCES `soundbear`.`songs` (`song_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
