-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: delivery_app
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) NOT NULL,
  `tipo_item` enum('COMIDA','BEBIDA') CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL DEFAULT '0',
  `cuit` varchar(255) NOT NULL DEFAULT '0',
  `email` varchar(255) NOT NULL DEFAULT '0',
  `direccion` varchar(255) NOT NULL DEFAULT '0',
  `latitud` double NOT NULL DEFAULT '0',
  `longitud` double NOT NULL DEFAULT '0',
  `apellido` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `cliente_cuit_IDX` (`cuit`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_menu`
--

DROP TABLE IF EXISTS `item_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_menu` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `vendedor_id` bigint unsigned DEFAULT '0',
  `nombre` varchar(255) NOT NULL DEFAULT '0',
  `descripcion` varchar(255) NOT NULL DEFAULT '0',
  `precio` double unsigned NOT NULL DEFAULT '0',
  `categoria_id` bigint unsigned NOT NULL DEFAULT '0',
  `tipo` enum('COMIDA','BEBIDA') CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `apto_vegano` tinyint NOT NULL DEFAULT '0',
  `apto_celiaco` tinyint NOT NULL DEFAULT '0',
  `peso` double DEFAULT NULL,
  `alcoholica` tinyint DEFAULT NULL,
  `gaseosa` tinyint DEFAULT NULL,
  `volumen` double DEFAULT NULL,
  `graduacion_alcoholica` double DEFAULT NULL,
  `deleted_at` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `item_menu_categoria_FK` (`categoria_id`),
  KEY `item_menu_vendedor_FK` (`vendedor_id`),
  CONSTRAINT `item_menu_categoria_FK` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`),
  CONSTRAINT `item_menu_vendedor_FK` FOREIGN KEY (`vendedor_id`) REFERENCES `vendedor` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=710 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_pedido`
--

DROP TABLE IF EXISTS `item_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item_pedido` (
  `item_menu_id` bigint unsigned NOT NULL,
  `pedido_id` bigint unsigned NOT NULL,
  `cantidad` smallint unsigned NOT NULL DEFAULT '0',
  `precio_total` double unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`item_menu_id`,`pedido_id`),
  KEY `item_pedido_item_menu_fk` (`item_menu_id`),
  KEY `FK_item_pedido_pedido` (`pedido_id`),
  CONSTRAINT `item_pedido_item_menu_FK` FOREIGN KEY (`item_menu_id`) REFERENCES `item_menu` (`id`) ON UPDATE RESTRICT,
  CONSTRAINT `item_pedido_pedido_FK` FOREIGN KEY (`pedido_id`) REFERENCES `pedido` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pago`
--

DROP TABLE IF EXISTS `pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pago` (
  `id` bigint unsigned NOT NULL,
  `fecha` datetime NOT NULL,
  `precio_total_sin_recargo` double NOT NULL DEFAULT '0',
  `precio_total_con_recargo` double DEFAULT '0',
  `metodo_pago` enum('MERCADO_PAGO','TRANSFERENCIA') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedido` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `pago_id` bigint unsigned DEFAULT NULL,
  `vendedor_id` bigint unsigned DEFAULT NULL,
  `cliente_id` bigint unsigned DEFAULT NULL,
  `precio_acumulado` double NOT NULL,
  `estado` enum('RECIBIDO','ACEPTADO','PREPARADO','ENVIADO') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `pedido_cliente_fk` (`cliente_id`),
  KEY `pedido_vendedor_fk` (`vendedor_id`),
  KEY `pedido_pago_fk` (`pago_id`),
  CONSTRAINT `pedido_cliente_FK` FOREIGN KEY (`cliente_id`) REFERENCES `cliente` (`id`) ON DELETE SET NULL,
  CONSTRAINT `pedido_pago_FK` FOREIGN KEY (`pago_id`) REFERENCES `pago` (`id`),
  CONSTRAINT `pedido_vendedor_FK` FOREIGN KEY (`vendedor_id`) REFERENCES `vendedor` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=232 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vendedor`
--

DROP TABLE IF EXISTS `vendedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vendedor` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `latitud` double NOT NULL DEFAULT '0',
  `longitud` double NOT NULL DEFAULT '0',
  `nombre` varchar(255) NOT NULL DEFAULT '0',
  `cuit` varchar(255) NOT NULL DEFAULT '0',
  `direccion` varchar(255) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `vendedor_cuit_unique_IDX` (`cuit`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'delivery_app'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-15 19:58:46
