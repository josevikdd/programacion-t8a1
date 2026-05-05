-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: gestionescolar
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `alumnos`
--

DROP TABLE IF EXISTS `alumnos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alumnos` (
  `codigo` int NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `apellidos` varchar(100) DEFAULT NULL,
  `poblacion` varchar(100) DEFAULT NULL,
  `f_nacimiento` date DEFAULT NULL,
  `c_curso` int DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_alumnos_curso` (`c_curso`),
  CONSTRAINT `fk_alumnos_curso` FOREIGN KEY (`c_curso`) REFERENCES `cursos` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alumnos`
--

LOCK TABLES `alumnos` WRITE;
/*!40000 ALTER TABLE `alumnos` DISABLE KEYS */;
INSERT INTO `alumnos` VALUES (1,'José Vicente','Sánchez Vargues','Segorbe','1997-10-20',1),(2,'María','López Martínez','Madrid','2004-07-25',1),(3,'Carlos','Sánchez Ruiz','Sevilla','2005-11-03',1),(4,'Lucía','Fernández Gómez','Barcelona','2006-01-15',1),(5,'David','García Torres','Bilbao','2004-09-08',1),(6,'Ana','Moreno Díaz','Valencia','2005-06-21',1),(7,'Javier','Jiménez Castro','Granada','2003-12-30',1),(8,'Elena','Romero Vega','Zaragoza','2005-04-17',1),(9,'Miguel','Navarro Ortiz','Murcia','2004-10-05',1),(10,'Carmen','Rubio Herrera','Alicante','2006-02-11',1),(11,'Pablo','Molina Castro','Córdoba','2005-08-14',1),(12,'Sara','Delgado León','Toledo','2004-03-19',1),(13,'Raúl','Ortega Cruz','Valladolid','2005-09-27',1),(14,'Laura','Reyes Santos','Salamanca','2003-11-22',1),(15,'Diego','Cano Morales','Burgos','2006-05-10',1),(16,'Paula','Guerrero Ramos','Almería','2004-06-29',1),(17,'Adrián','Prieto Núñez','Oviedo','2005-12-01',1),(18,'Nuria','Méndez Gil','Santander','2003-07-07',1),(19,'Iván','Serrano Iglesias','Logroño','2004-01-13',1),(21,'Rubén','Vargas Soto','Huelva','2006-03-05',2),(22,'Marta','Castillo Peña','Jaén','2004-08-23',2),(23,'Hugo','Cabrera Ríos','León','2005-02-09',2),(24,'Irene','Campos Silva','Cádiz','2003-09-16',2),(25,'Álvaro','Fuentes Reyes','Lugo','2006-06-01',2),(26,'Beatriz','Martín Lozano','Ourense','2004-12-20',2),(27,'Sergio','Iglesias Pardo','Segovia','2005-07-11',2),(28,'Cristina','Nieto Vega','Ávila','2003-05-03',2),(29,'Fernando','Calvo Rojas','Cuenca','2004-11-28',2),(30,'Patricia','Santana Ortega','Guadalajara','2005-01-06',2),(31,'Andrés','Soto Márquez','Tarragona','2006-04-14',2),(32,'Silvia','Ramos Cabrera','Girona','2003-10-31',2),(33,'Óscar','Peña Fuentes','Castellón','2004-02-26',2),(34,'Rocío','León Serrano','Badajoz','2005-08-02',2),(35,'Guillermo','Vega Morales','Cáceres','2006-09-19',2),(36,'Teresa','Ortega Ruiz','Melilla','2003-06-25',2),(37,'Víctor','Cruz Herrera','Ceuta','2004-07-30',2),(38,'Lorena','Navarro Gil','Soria','2005-03-08',2),(39,'Emilio','Molina Vega','Teruel','2006-01-27',2),(40,'Noelia','Castro Díaz','Huesca','2003-12-12',2);
/*!40000 ALTER TABLE `alumnos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alumnos_asignaturas`
--

DROP TABLE IF EXISTS `alumnos_asignaturas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alumnos_asignaturas` (
  `codigo_alumno` int NOT NULL,
  `codigo_asignatura` int NOT NULL,
  PRIMARY KEY (`codigo_alumno`,`codigo_asignatura`),
  KEY `codigo_asignatura` (`codigo_asignatura`),
  CONSTRAINT `alumnos_asignaturas_ibfk_1` FOREIGN KEY (`codigo_alumno`) REFERENCES `alumnos` (`codigo`),
  CONSTRAINT `alumnos_asignaturas_ibfk_2` FOREIGN KEY (`codigo_asignatura`) REFERENCES `asignaturas` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alumnos_asignaturas`
--

LOCK TABLES `alumnos_asignaturas` WRITE;
/*!40000 ALTER TABLE `alumnos_asignaturas` DISABLE KEYS */;
INSERT INTO `alumnos_asignaturas` VALUES (1,111),(2,111),(3,111),(4,111),(5,111),(6,111),(7,111),(8,111),(9,111),(10,111),(11,111),(12,111),(13,111),(14,111),(15,111),(16,111),(17,111),(18,111),(19,111),(1,112),(2,112),(3,112),(4,112),(5,112),(6,112),(7,112),(8,112),(9,112),(10,112),(11,112),(12,112),(13,112),(14,112),(15,112),(16,112),(17,112),(18,112),(19,112),(1,113),(2,113),(3,113),(4,113),(5,113),(6,113),(7,113),(8,113),(9,113),(10,113),(11,113),(12,113),(13,113),(14,113),(15,113),(16,113),(17,113),(18,113),(19,113),(1,114),(2,114),(3,114),(4,114),(5,114),(6,114),(7,114),(8,114),(9,114),(10,114),(11,114),(12,114),(13,114),(14,114),(15,114),(16,114),(17,114),(18,114),(19,114),(1,115),(2,115),(3,115),(4,115),(5,115),(6,115),(7,115),(8,115),(9,115),(10,115),(11,115),(12,115),(13,115),(14,115),(15,115),(16,115),(17,115),(18,115),(19,115),(1,116),(2,116),(3,116),(4,116),(5,116),(6,116),(7,116),(8,116),(9,116),(10,116),(11,116),(12,116),(13,116),(14,116),(15,116),(16,116),(17,116),(18,116),(19,116),(1,117),(2,117),(3,117),(4,117),(5,117),(6,117),(7,117),(8,117),(9,117),(10,117),(11,117),(12,117),(13,117),(14,117),(15,117),(16,117),(17,117),(18,117),(19,117),(1,118),(2,118),(3,118),(4,118),(5,118),(6,118),(7,118),(8,118),(9,118),(10,118),(11,118),(12,118),(13,118),(14,118),(15,118),(16,118),(17,118),(18,118),(19,118);
/*!40000 ALTER TABLE `alumnos_asignaturas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `asignaturas`
--

DROP TABLE IF EXISTS `asignaturas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asignaturas` (
  `codigo` int NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `c_profesor` int DEFAULT NULL,
  `c_curso` int DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `c_profesor` (`c_profesor`),
  KEY `c_curso` (`c_curso`),
  CONSTRAINT `asignaturas_ibfk_1` FOREIGN KEY (`c_profesor`) REFERENCES `profesores` (`codigo`),
  CONSTRAINT `asignaturas_ibfk_2` FOREIGN KEY (`c_curso`) REFERENCES `cursos` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asignaturas`
--

LOCK TABLES `asignaturas` WRITE;
/*!40000 ALTER TABLE `asignaturas` DISABLE KEYS */;
INSERT INTO `asignaturas` VALUES (111,'Programación',1,1),(112,'Bases de datos',2,1),(113,'LMSGE',3,1),(114,'Inglés profesional',4,1),(115,'Entornos de desarrollo',5,1),(116,'IPE',6,1),(117,'Proyecto Intermodular',7,1),(118,'Tutoria',8,1);
/*!40000 ALTER TABLE `asignaturas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cursos`
--

DROP TABLE IF EXISTS `cursos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cursos` (
  `codigo` int NOT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cursos`
--

LOCK TABLES `cursos` WRITE;
/*!40000 ALTER TABLE `cursos` DISABLE KEYS */;
INSERT INTO `cursos` VALUES (1,'1 º DAM'),(2,'2 º DAM');
/*!40000 ALTER TABLE `cursos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `examenes`
--

DROP TABLE IF EXISTS `examenes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `examenes` (
  `codigo` int NOT NULL,
  `fecha` date DEFAULT NULL,
  `nota` float DEFAULT NULL,
  `c_alumno` int DEFAULT NULL,
  `c_asignatura` int DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `c_alumno` (`c_alumno`),
  KEY `c_asignatura` (`c_asignatura`),
  CONSTRAINT `examenes_ibfk_1` FOREIGN KEY (`c_alumno`) REFERENCES `alumnos` (`codigo`),
  CONSTRAINT `examenes_ibfk_2` FOREIGN KEY (`c_asignatura`) REFERENCES `asignaturas` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `examenes`
--

LOCK TABLES `examenes` WRITE;
/*!40000 ALTER TABLE `examenes` DISABLE KEYS */;
INSERT INTO `examenes` VALUES (1,'2026-05-05',7,1,111),(2,'2026-01-13',8,1,112);
/*!40000 ALTER TABLE `examenes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profesores`
--

DROP TABLE IF EXISTS `profesores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profesores` (
  `codigo` int NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `apellidos` varchar(100) DEFAULT NULL,
  `poblacion` varchar(100) DEFAULT NULL,
  `f_nacimiento` date DEFAULT NULL,
  `telefono` varchar(100) DEFAULT NULL,
  `categoria` varchar(100) DEFAULT NULL,
  `c_curso` int DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `fk_profesores_curso` (`c_curso`),
  CONSTRAINT `fk_profesores_curso` FOREIGN KEY (`c_curso`) REFERENCES `cursos` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profesores`
--

LOCK TABLES `profesores` WRITE;
/*!40000 ALTER TABLE `profesores` DISABLE KEYS */;
INSERT INTO `profesores` VALUES (1,'Laura','García López','Valencia','1980-05-12','600123456','Titular',1),(2,'Carlos','Martínez Ruiz','Madrid','1975-11-03','611234567','Catedrático',1),(3,'Ana','Sánchez Pérez','Sevilla','1988-02-20','622345678','Interina',1),(4,'Javier','Fernández Gómez','Alicante','1990-07-15','633456789','Asociado',1),(5,'Marta','López Navarro','Barcelona','1983-09-28','644567890','Titular',1),(6,'David','Romero Díaz','Granada','1979-12-10','655678901','Catedrático',1),(7,'Elena','Torres Martín','Zaragoza','1992-04-05','666789012','Interina',1),(8,'Pedro','Vega Castillo','Bilbao','1985-06-18','677890123','Asociado',1);
/*!40000 ALTER TABLE `profesores` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-05 12:29:39
