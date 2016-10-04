-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
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
  `artist_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `artist_name` VARCHAR(45) NOT NULL COMMENT '',
  PRIMARY KEY (`artist_id`)  COMMENT '',
  UNIQUE INDEX `artist_UNIQUE` (`artist_name` ASC)  COMMENT '',
  INDEX `artist` (`artist_name` ASC)  COMMENT '')
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `soundbear`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundbear`.`users` (
  `user_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `username` VARCHAR(45) NOT NULL COMMENT '',
  `email` VARCHAR(45) NOT NULL COMMENT '',
  `password` VARCHAR(45) NOT NULL COMMENT '',
  `is_active` TINYINT(1) NULL DEFAULT NULL COMMENT '',
  `rigistration_date` DATETIME NULL DEFAULT NULL COMMENT '',
  PRIMARY KEY (`user_id`)  COMMENT '',
  UNIQUE INDEX `email_UNIQUE` (`email` ASC)  COMMENT '',
  UNIQUE INDEX `usename_UNIQUE` (`username` ASC)  COMMENT '')
ENGINE = InnoDB
AUTO_INCREMENT = 21
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `soundbear`.`follows`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundbear`.`follows` (
  `follower` INT(11) NOT NULL COMMENT '',
  `following` INT(11) NOT NULL COMMENT '',
  PRIMARY KEY (`follower`, `following`)  COMMENT '',
  INDEX `fk_users_has_users_users1_idx` (`following` ASC)  COMMENT '',
  INDEX `fk_users_has_users_users_idx` (`follower` ASC)  COMMENT '',
  CONSTRAINT `fk_users_has_users_users`
    FOREIGN KEY (`follower`)
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
  `genre_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `genre_name` VARCHAR(45) NOT NULL COMMENT '',
  PRIMARY KEY (`genre_id`)  COMMENT '',
  UNIQUE INDEX `genre_UNIQUE` (`genre_name` ASC)  COMMENT '',
  INDEX `genre_index` (`genre_name` ASC)  COMMENT '')
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `soundbear`.`playlists`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundbear`.`playlists` (
  `playlist_id` INT(11) NOT NULL COMMENT '',
  `playlist_name` VARCHAR(45) NOT NULL COMMENT '',
  `user_id` INT(11) NOT NULL COMMENT '',
  PRIMARY KEY (`playlist_id`)  COMMENT '',
  INDEX `fk_playlists_users1_idx` (`user_id` ASC)  COMMENT '',
  CONSTRAINT `fk_playlists_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `soundbear`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `soundbear`.`songs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundbear`.`songs` (
  `song_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '',
  `song_name` VARCHAR(45) NOT NULL COMMENT '',
  `path` VARCHAR(200) NOT NULL COMMENT '',
  `user_id` INT(11) NOT NULL COMMENT '',
  `genre_id` INT(11) NOT NULL COMMENT '',
  `artist_id` INT(11) NOT NULL COMMENT '',
  PRIMARY KEY (`song_id`)  COMMENT '',
  UNIQUE INDEX `path_UNIQUE` (`path` ASC)  COMMENT '',
  INDEX `fk_songs_users_idx` (`user_id` ASC)  COMMENT '',
  INDEX `fk_songs_genres1_idx` (`genre_id` ASC)  COMMENT '',
  INDEX `song_name` (`song_name` ASC)  COMMENT '',
  INDEX `fk_songs_artists1_idx` (`artist_id` ASC)  COMMENT '',
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
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `soundbear`.`playlists_has_songs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `soundbear`.`playlists_has_songs` (
  `playlist_id` INT(11) NOT NULL COMMENT '',
  `song_id` INT(11) NOT NULL COMMENT '',
  PRIMARY KEY (`playlist_id`, `song_id`)  COMMENT '',
  INDEX `fk_playlists_has_songs_songs1_idx` (`song_id` ASC)  COMMENT '',
  INDEX `fk_playlists_has_songs_playlists1_idx` (`playlist_id` ASC)  COMMENT '',
  CONSTRAINT `fk_playlists_has_songs_playlists1`
    FOREIGN KEY (`playlist_id`)
    REFERENCES `soundbear`.`playlists` (`playlist_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_playlists_has_songs_songs1`
    FOREIGN KEY (`song_id`)
    REFERENCES `soundbear`.`songs` (`song_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
