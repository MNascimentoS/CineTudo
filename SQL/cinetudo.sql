-- MySQL dump 10.13  Distrib 5.7.12, for linux-glibc2.5 (x86_64)
--
-- Host: localhost    Database: cinetudo
-- ------------------------------------------------------
-- Server version	5.7.16-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cinema`
--

CREATE SCHEMA IF NOT EXISTS `cinetudo` DEFAULT CHARACTER SET utf8 ;
USE `cinetudo` ;


DROP TABLE IF EXISTS `cinema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cinema` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `id_sessao` int(11) DEFAULT NULL,
  `id_sala` int(11) DEFAULT NULL,
  `endereco` varchar(45) NOT NULL,
  `cnpj` varchar(45) NOT NULL,
  `valor_semana` float NOT NULL,
  `valor_fimsemana` float NOT NULL,
  `id_venda` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nome_UNIQUE` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cinema`
--

LOCK TABLES `cinema` WRITE;
/*!40000 ALTER TABLE `cinema` DISABLE KEYS */;
INSERT INTO `cinema` VALUES (1,'Cinetudo Iguatemi',NULL,NULL,'Av. Tancredo Neves, 148 Salvador - BA','62.212.097/0001-53',15,20,NULL);
/*!40000 ALTER TABLE `cinema` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `filme`
--

DROP TABLE IF EXISTS `filme`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `filme` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `titulo` varchar(45) NOT NULL,
  `diretor` varchar(45) NOT NULL,
  `ator` varchar(255) NOT NULL,
  `duracao` varchar(15) NOT NULL,
  `classificacao` int(11) NOT NULL,
  `genero_id` int(11) NOT NULL,
  `image` blob NOT NULL,
  `cinema_id` int(11) NOT NULL,
  `tipo` varchar(4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_filme_genero1_idx` (`genero_id`),
  KEY `fk_filme_cinema1_idx` (`cinema_id`),
  CONSTRAINT `fk_filme_cinema1` FOREIGN KEY (`cinema_id`) REFERENCES `cinema` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_filme_genero1` FOREIGN KEY (`genero_id`) REFERENCES `genero` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `filme`
--

LOCK TABLES `filme` WRITE;
/*!40000 ALTER TABLE `filme` DISABLE KEYS */;
/*!40000 ALTER TABLE `filme` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `funcionario`
--

DROP TABLE IF EXISTS `funcionario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `funcionario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `cpf` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `cargo` int(11) NOT NULL,
  `usuario` varchar(45) NOT NULL,
  `senha` varchar(100) NOT NULL,
  `cinema_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`usuario`),
  UNIQUE KEY `usuario` (`usuario`),
  KEY `fk_funcionario_cinema1_idx` (`cinema_id`),
  CONSTRAINT `fk_funcionario_cinema1` FOREIGN KEY (`cinema_id`) REFERENCES `cinema` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `funcionario`
--

LOCK TABLES `funcionario` WRITE;
/*!40000 ALTER TABLE `funcionario` DISABLE KEYS */;
INSERT INTO `funcionario` VALUES (1,'Mateus Nascimento','111111111','mateus.nnascimento.s@gmail.com',1,'mateus','minhasenha',1),(2,'Teste','111111111','teste@gmail.com',1,'user','pass',1),(3,'Fernandinho da Bahia','696969696','nandinho69@gmail.com',0,'nandinho','gay69',1),(4,'Joao Da Silva Pinto','02354867800','jdapinto@email.com',0,'jdapinto','kkkkkk',1);
/*!40000 ALTER TABLE `funcionario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genero`
--

DROP TABLE IF EXISTS `genero`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `genero` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  PRIMARY KEY (`id`,`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genero`
--

LOCK TABLES `genero` WRITE;
/*!40000 ALTER TABLE `genero` DISABLE KEYS */;
INSERT INTO `genero` VALUES (1,'Ação'),(2,'Aventura'),(3,'Comédia'),(4,'Ficção Científica'),(5,'Romance'),(6,'Suspence'),(7,'Terror'),(8,'Animação'),(9,'Infantil');
/*!40000 ALTER TABLE `genero` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `horario`
--

DROP TABLE IF EXISTS `horario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `horario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hora` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `horario`
--

LOCK TABLES `horario` WRITE;
/*!40000 ALTER TABLE `horario` DISABLE KEYS */;
/*!40000 ALTER TABLE `horario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingresso`
--

DROP TABLE IF EXISTS `ingresso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ingresso` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `preco` float NOT NULL,
  `tipo` int(11) NOT NULL,
  `venda_id` int(11) NOT NULL,
  `sessao_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_ingresso_venda1_idx` (`venda_id`),
  KEY `fk_ingresso_sessao1_idx` (`sessao_id`),
  CONSTRAINT `fk_ingresso_sessao1` FOREIGN KEY (`sessao_id`) REFERENCES `sessao` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_ingresso_venda1` FOREIGN KEY (`venda_id`) REFERENCES `venda` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingresso`
--

LOCK TABLES `ingresso` WRITE;
/*!40000 ALTER TABLE `ingresso` DISABLE KEYS */;
/*!40000 ALTER TABLE `ingresso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promocao`
--

DROP TABLE IF EXISTS `promocao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `promocao` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `data` varchar(15) NOT NULL,
  `descricao` varchar(255) NOT NULL,
  `desconto` float NOT NULL,
  `cinema_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_promocao_cinema1_idx` (`cinema_id`),
  CONSTRAINT `fk_promocao_cinema1` FOREIGN KEY (`cinema_id`) REFERENCES `cinema` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promocao`
--

LOCK TABLES `promocao` WRITE;
/*!40000 ALTER TABLE `promocao` DISABLE KEYS */;
/*!40000 ALTER TABLE `promocao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sala`
--

DROP TABLE IF EXISTS `sala`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sala` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `numero` int(11) NOT NULL,
  `capacidade` int(11) NOT NULL,
  `preco_ingreco` float NOT NULL,
  `tipo` varchar(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sala`
--

LOCK TABLES `sala` WRITE;
/*!40000 ALTER TABLE `sala` DISABLE KEYS */;
/*!40000 ALTER TABLE `sala` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sessao`
--

DROP TABLE IF EXISTS `sessao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sessao` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sala_id` int(11) NOT NULL,
  `data` varchar(15) NOT NULL,
  `assentos` int(11) NOT NULL,
  `valor_sessao` float NOT NULL,
  `ingresso_disponivel` int(11) NOT NULL,
  `filme_id` int(11) NOT NULL,
  `horario_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_sessao_filme_idx` (`filme_id`),
  KEY `fk_sessao_horario1_idx` (`horario_id`),
  CONSTRAINT `fk_sessao_filme` FOREIGN KEY (`filme_id`) REFERENCES `filme` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_sessao_horario1` FOREIGN KEY (`horario_id`) REFERENCES `horario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_sessao_sala` FOREIGN KEY (`id`) REFERENCES `sala` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sessao`
--

LOCK TABLES `sessao` WRITE;
/*!40000 ALTER TABLE `sessao` DISABLE KEYS */;
/*!40000 ALTER TABLE `sessao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `venda`
--

DROP TABLE IF EXISTS `venda`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `venda` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ingresso_id` int(11) NOT NULL,
  `valor_total` float NOT NULL,
  `data` varchar(15) NOT NULL,
  `horario_id` int(11) NOT NULL,
  `cinema_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_venda_horario1_idx` (`horario_id`),
  KEY `fk_venda_cinema1_idx` (`cinema_id`),
  CONSTRAINT `fk_venda_cinema1` FOREIGN KEY (`cinema_id`) REFERENCES `cinema` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_venda_horario1` FOREIGN KEY (`horario_id`) REFERENCES `horario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `venda`
--

LOCK TABLES `venda` WRITE;
/*!40000 ALTER TABLE `venda` DISABLE KEYS */;
/*!40000 ALTER TABLE `venda` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-10-25 12:37:54
