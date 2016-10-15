CREATE TABLE IF NOT EXISTS `funcionario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `cpf` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `cargo` INT NOT NULL,
  `usuario` VARCHAR(45) NOT NULL,
  `senha` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`, `usuario`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `genero` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`, `nome`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `horario` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `hora` TIME(6) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `cinetudo`.`filme` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(45) NOT NULL,
  `diretor` VARCHAR(45) NOT NULL,
  `ator` VARCHAR(255) NOT NULL,
  `duracao` TIME(6) NOT NULL,
  `classificacao` INT NOT NULL,
  `genero` VARCHAR(45) NOT NULL,
  `genero_id` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;