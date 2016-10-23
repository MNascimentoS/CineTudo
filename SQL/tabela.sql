-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema cinetudo
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema cinetudo
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `cinetudo` DEFAULT CHARACTER SET utf8 ;
USE `cinetudo` ;

-- -----------------------------------------------------
-- Table `cinetudo`.`genero`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinetudo`.`genero` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`, `nome`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cinetudo`.`cinema`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinetudo`.`cinema` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `id_sessao` INT NULL,
  `id_sala` INT NULL,
  `endereco` VARCHAR(45) NOT NULL,
  `cnpj` VARCHAR(45) NOT NULL,
  `valor_semana` FLOAT NOT NULL,
  `valor_fimsemana` FLOAT NOT NULL,
  `venda_id` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `nome_UNIQUE` (`nome` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cinetudo`.`filme`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinetudo`.`filme` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(45) NOT NULL,
  `diretor` VARCHAR(45) NOT NULL,
  `ator` VARCHAR(255) NOT NULL,
  `duracao` TIME(6) NOT NULL,
  `classificacao` INT NOT NULL,
  `genero_id` INT NOT NULL,
  `image` BLOB NOT NULL,
  `cinema_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_filme_genero1_idx` (`genero_id` ASC),
  INDEX `fk_filme_cinema1_idx` (`cinema_id` ASC),
  CONSTRAINT `fk_filme_genero1`
    FOREIGN KEY (`genero_id`)
    REFERENCES `cinetudo`.`genero` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_filme_cinema1`
    FOREIGN KEY (`cinema_id`)
    REFERENCES `cinetudo`.`cinema` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cinetudo`.`horario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinetudo`.`horario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `hora` VARCHAR(10) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cinetudo`.`sessao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinetudo`.`sessao` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `sala_id` INT NOT NULL,
  `data` VARCHAR(15) NOT NULL,
  `assentos` INT NOT NULL,
  `valor_sessao` FLOAT NOT NULL,
  `ingresso_disponivel` INT NOT NULL,
  `filme_id` INT NOT NULL,
  `horario_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_sessao_filme_idx` (`filme_id` ASC),
  INDEX `fk_sessao_horario1_idx` (`horario_id` ASC),
  CONSTRAINT `fk_sessao_filme`
    FOREIGN KEY (`filme_id`)
    REFERENCES `cinetudo`.`filme` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_sessao_horario1`
    FOREIGN KEY (`horario_id`)
    REFERENCES `cinetudo`.`horario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cinetudo`.`sala`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinetudo`.`sala` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `numero` INT NOT NULL,
  `capacidade` INT NOT NULL,
  `preco_ingreco` FLOAT NOT NULL,
  `sessao_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_sala_sessao1_idx` (`sessao_id` ASC),
  CONSTRAINT `fk_sala_sessao1`
    FOREIGN KEY (`sessao_id`)
    REFERENCES `cinetudo`.`sessao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cinetudo`.`promocao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinetudo`.`promocao` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `data` VARCHAR(15) NOT NULL,
  `descricao` VARCHAR(255) NOT NULL,
  `desconto` FLOAT NOT NULL,
  `cinema_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_promocao_cinema1_idx` (`cinema_id` ASC),
  CONSTRAINT `fk_promocao_cinema1`
    FOREIGN KEY (`cinema_id`)
    REFERENCES `cinetudo`.`cinema` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cinetudo`.`venda`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinetudo`.`venda` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `ingresso_id` INT NOT NULL,
  `valor_total` FLOAT NOT NULL,
  `data` VARCHAR(15) NOT NULL,
  `horario_id` INT NOT NULL,
  `cinema_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_venda_horario1_idx` (`horario_id` ASC),
  INDEX `fk_venda_cinema1_idx` (`cinema_id` ASC),
  CONSTRAINT `fk_venda_horario1`
    FOREIGN KEY (`horario_id`)
    REFERENCES `cinetudo`.`horario` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_venda_cinema1`
    FOREIGN KEY (`cinema_id`)
    REFERENCES `cinetudo`.`cinema` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cinetudo`.`ingresso`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinetudo`.`ingresso` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `preco` FLOAT NOT NULL,
  `tipo` INT NOT NULL,
  `venda_id` INT NOT NULL,
  `sessao_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_ingresso_venda1_idx` (`venda_id` ASC),
  INDEX `fk_ingresso_sessao1_idx` (`sessao_id` ASC),
  CONSTRAINT `fk_ingresso_venda1`
    FOREIGN KEY (`venda_id`)
    REFERENCES `cinetudo`.`venda` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ingresso_sessao1`
    FOREIGN KEY (`sessao_id`)
    REFERENCES `cinetudo`.`sessao` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cinetudo`.`funcionario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `cinetudo`.`funcionario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `cpf` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `cargo` INT NOT NULL,
  `usuario` VARCHAR(45) NOT NULL,
  `senha` VARCHAR(100) NOT NULL,
  `cinema_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_funcionario_cinema1_idx` (`cinema_id` ASC),
  UNIQUE INDEX `usuario_UNIQUE` (`usuario` ASC),
  CONSTRAINT `fk_funcionario_cinema1`
    FOREIGN KEY (`cinema_id`)
    REFERENCES `cinetudo`.`cinema` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
