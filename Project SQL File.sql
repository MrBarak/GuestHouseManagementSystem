-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: mydb
-- ------------------------------------------------------
-- Server version	5.5.62

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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `addressID` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(45) NOT NULL,
  `City_cityID` int(11) NOT NULL,
  PRIMARY KEY (`addressID`),
  UNIQUE KEY `addressID_UNIQUE` (`addressID`),
  KEY `fk_Address_City1_idx` (`City_cityID`),
  CONSTRAINT `fk_Address_City1` FOREIGN KEY (`City_cityID`) REFERENCES `city` (`cityID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'M M No.1 from B-III',1),(2,'Satelite Town, House Number 234',1),(3,'Sabzazaar, house Number 3',2),(4,'Shadaab Colony, House Number 67',2),(5,'Bahria Enclave,street No.3,house No.45',3),(6,'F9, house number 34',3),(7,'G10, house No. 67',3),(8,'M M no 2, HC 23',1),(9,'M M No.1 from B-III',1),(14,'Near Uncle Shahmim House, Sugar Mills',13),(15,'235 Shirley Rd.\nFar Rockaway, NY 11691',14),(16,'M M No.1 from B-III',1),(23,'chatha Bakhtawar',3),(24,'Mughalpura',2),(25,'Korangi Kareek',5),(26,'235 Shirley Rd.Far Rockaway, NY 11691',14),(27,'235 Shirley Rd.Far Rockaway, NY 11681',14),(28,'Khairan Cantt',15),(29,'M M No.1 from B-III',4),(30,'Tarlai Kalan, Chatha Bakhtawar',3),(31,'M M No.1 from B-III',18),(32,'abc xyz',19),(33,'Bahria Town phase II',3),(34,'xyz',3),(35,'Chak Shahzad',3),(36,'Chak Shahzad, Tarlai',3),(37,'Mandi Mor',11),(38,'Satelite Town, House Number 234',5),(39,'D-9 Sector',20);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `address_has_employee`
--

DROP TABLE IF EXISTS `address_has_employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address_has_employee` (
  `addressID` int(11) NOT NULL,
  `empID` int(11) NOT NULL,
  PRIMARY KEY (`addressID`,`empID`),
  KEY `fk_Address_has_Employee_Address1_idx` (`addressID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address_has_employee`
--

LOCK TABLES `address_has_employee` WRITE;
/*!40000 ALTER TABLE `address_has_employee` DISABLE KEYS */;
INSERT INTO `address_has_employee` VALUES (1,1),(3,20),(5,3),(5,21),(6,4),(23,20),(24,5),(24,20),(25,6),(25,21),(26,7),(27,8),(28,9),(29,10),(30,11),(31,12),(32,13),(33,14),(34,15),(35,16),(36,17),(37,1),(37,18),(38,2),(38,19),(39,2);
/*!40000 ALTER TABLE `address_has_employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `empID` int(11) NOT NULL,
  `password` varchar(25) NOT NULL,
  KEY `fk_Admin_Employee_idx` (`empID`),
  CONSTRAINT `fk_Admin_Employee` FOREIGN KEY (`empID`) REFERENCES `employee` (`empID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES (2,'shaq123'),(4,'fayaz123');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bills`
--

DROP TABLE IF EXISTS `bills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bills` (
  `billsID` int(11) NOT NULL AUTO_INCREMENT,
  `customerID` int(11) NOT NULL,
  `serviceID` int(11) NOT NULL,
  `counts` int(11) NOT NULL,
  `booking` varchar(45) NOT NULL,
  PRIMARY KEY (`billsID`),
  UNIQUE KEY `billsID_UNIQUE` (`billsID`),
  KEY `fk_Customer_has_Services_Services1_idx` (`serviceID`),
  KEY `fk_Customer_has_Services_Customer1_idx` (`customerID`),
  CONSTRAINT `fk_Customer_has_Services_Customer1` FOREIGN KEY (`customerID`) REFERENCES `customer` (`customerID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Customer_has_Services_Services1` FOREIGN KEY (`serviceID`) REFERENCES `services` (`serviceID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bills`
--

LOCK TABLES `bills` WRITE;
/*!40000 ALTER TABLE `bills` DISABLE KEYS */;
INSERT INTO `bills` VALUES (1,1,1,1,'h1'),(2,1,2,2,'h22'),(3,2,1,2,'h2'),(4,2,3,2,'h2'),(5,2,4,3,'h2'),(6,3,4,1,'h20'),(7,4,4,2,'h12'),(8,4,5,1,'r4'),(9,1,1,5,'r38'),(10,4,1,1,'r23'),(11,4,1,5,'r4'),(12,4,4,1,'r4');
/*!40000 ALTER TABLE `bills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `branch`
--

DROP TABLE IF EXISTS `branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `branch` (
  `branchID` int(11) NOT NULL AUTO_INCREMENT,
  `branch_name` varchar(25) NOT NULL,
  `locationID` int(11) NOT NULL,
  PRIMARY KEY (`branchID`),
  UNIQUE KEY `branchID_UNIQUE` (`branchID`),
  KEY `fk_Branch_Location1_idx` (`locationID`),
  CONSTRAINT `fk_Branch_Location1` FOREIGN KEY (`locationID`) REFERENCES `location` (`locationID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `branch`
--

LOCK TABLES `branch` WRITE;
/*!40000 ALTER TABLE `branch` DISABLE KEYS */;
INSERT INTO `branch` VALUES (1,'ISB Branch 1',1),(2,'LHR Branch',2),(3,'KHI Branch',3),(4,'PEW Branch 1',4),(5,'PEW Branch 2',4),(6,'ISB Branch 2',5);
/*!40000 ALTER TABLE `branch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `city` (
  `cityID` int(11) NOT NULL AUTO_INCREMENT,
  `city_name` varchar(35) NOT NULL,
  `countryID` int(11) NOT NULL,
  PRIMARY KEY (`cityID`),
  UNIQUE KEY `cityID_UNIQUE` (`cityID`),
  KEY `fk_City_Country1_idx` (`countryID`),
  CONSTRAINT `fk_City_Country1` FOREIGN KEY (`countryID`) REFERENCES `country` (`countryID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (1,'Jhelum',1),(2,'Lahore',1),(3,'Islamabad',1),(4,'Multan',1),(5,'Karachi',1),(6,'Peshawar',1),(7,'Faisalabad',1),(8,'Sargodha',1),(9,'Gujrawala',1),(10,'Lalamoosa',1),(11,'Rawalpindi',1),(12,'Sakhar',1),(13,'Laiya',1),(14,'New York',5),(15,'Kharian',1),(16,'Washington',5),(17,'Rawat',1),(18,'Jhelum',5),(19,'',12),(20,'Baniyas',2);
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `complaint`
--

DROP TABLE IF EXISTS `complaint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `complaint` (
  `complaintID` int(11) NOT NULL AUTO_INCREMENT,
  `complaintCategoryID` int(11) NOT NULL,
  `complaint` varchar(100) NOT NULL,
  `customerID` int(11) NOT NULL,
  `complaint_date` date NOT NULL,
  PRIMARY KEY (`complaintID`),
  UNIQUE KEY `complaintID_UNIQUE` (`complaintID`),
  KEY `fk_Complaint_Complaint_Category1_idx` (`complaintCategoryID`),
  KEY `fk_Complaint_Customer1_idx` (`customerID`),
  CONSTRAINT `fk_Complaint_Complaint_Category1` FOREIGN KEY (`complaintCategoryID`) REFERENCES `complaint_category` (`complaintCategoryID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Complaint_Customer1` FOREIGN KEY (`customerID`) REFERENCES `customer` (`customerID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complaint`
--

LOCK TABLES `complaint` WRITE;
/*!40000 ALTER TABLE `complaint` DISABLE KEYS */;
INSERT INTO `complaint` VALUES (1,5,'House Condition is quite rough, Wall have Cracks no properly paint',1,'2019-12-13'),(2,3,'Proper cleanliness is not maintained',3,'2018-09-23'),(3,5,'House Condition was Extremely Poor',4,'2019-10-15'),(4,1,'Food Quality was unsatisfactory',4,'2020-01-08'),(5,3,'Cleaning was not good',4,'2020-01-16'),(6,2,'Laundry was unsatisfactory',4,'2020-01-17');
/*!40000 ALTER TABLE `complaint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `complaint_category`
--

DROP TABLE IF EXISTS `complaint_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `complaint_category` (
  `complaintCategoryID` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(15) NOT NULL,
  PRIMARY KEY (`complaintCategoryID`),
  UNIQUE KEY `complaintCategoryID_UNIQUE` (`complaintCategoryID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complaint_category`
--

LOCK TABLES `complaint_category` WRITE;
/*!40000 ALTER TABLE `complaint_category` DISABLE KEYS */;
INSERT INTO `complaint_category` VALUES (1,'food'),(2,'laundry'),(3,'cleaning'),(4,'mismanagement'),(5,'house condition'),(6,'other');
/*!40000 ALTER TABLE `complaint_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `country` (
  `countryID` int(11) NOT NULL AUTO_INCREMENT,
  `country_name` varchar(35) NOT NULL,
  PRIMARY KEY (`countryID`),
  UNIQUE KEY `countryID_UNIQUE` (`countryID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES (1,'Pakistan'),(2,'Dubai'),(3,'Saudi Arab'),(4,'England'),(5,'America'),(6,'China'),(7,'India'),(8,'Bangladesh'),(9,'Sirilanka'),(10,'Rusia'),(11,'Turkey'),(12,'');
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `customerID` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(25) NOT NULL,
  `last_name` varchar(25) NOT NULL,
  `gender` varchar(6) NOT NULL,
  `DOB` date NOT NULL,
  `CNIC` varchar(15) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(25) NOT NULL,
  `joining_date` date NOT NULL,
  PRIMARY KEY (`customerID`),
  UNIQUE KEY `customerID_UNIQUE` (`customerID`),
  UNIQUE KEY `CNIC_UNIQUE` (`CNIC`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'Ahmad','Hussain','Male','1998-09-19','37301-9876654-6','ahmedhussain@hotmail.com','ahmed123','2019-09-17'),(2,'Harris','Shiekh','Male','1992-07-13','37301-8765498-3','harrishiekh@gmail.com','haris123','2019-10-12'),(3,'Noor','Fatima','Female','1997-03-30','37378-9876543-2','fatimanoor@yahoo.com','noor123','2019-11-01'),(4,'Usman','Malik','Male','1997-11-15','67894-3467239-9','malikusman@gmail.com','usman123','2019-05-23');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_address`
--

DROP TABLE IF EXISTS `customer_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_address` (
  `addressID` int(11) NOT NULL,
  `customerID` int(11) NOT NULL,
  PRIMARY KEY (`addressID`,`customerID`),
  KEY `fk_Address_has_Customer_Address1_idx` (`addressID`),
  CONSTRAINT `fk_Address_has_Customer_Address1` FOREIGN KEY (`addressID`) REFERENCES `address` (`addressID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_address`
--

LOCK TABLES `customer_address` WRITE;
/*!40000 ALTER TABLE `customer_address` DISABLE KEYS */;
INSERT INTO `customer_address` VALUES (1,1),(1,4),(2,2),(3,3),(5,5),(14,6),(14,7),(15,7),(27,4),(36,4);
/*!40000 ALTER TABLE `customer_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_deleted`
--

DROP TABLE IF EXISTS `customer_deleted`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_deleted` (
  `customerID` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `gender` varchar(8) NOT NULL,
  `DOB` date NOT NULL,
  `CNIC` varchar(15) NOT NULL,
  `email` varchar(45) NOT NULL,
  `joining_date` date NOT NULL,
  `leaving_date` date NOT NULL,
  PRIMARY KEY (`customerID`),
  UNIQUE KEY `customerID_UNIQUE` (`customerID`),
  KEY `fk_Customer_Deleted_Customer1_idx` (`customerID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_deleted`
--

LOCK TABLES `customer_deleted` WRITE;
/*!40000 ALTER TABLE `customer_deleted` DISABLE KEYS */;
INSERT INTO `customer_deleted` VALUES (5,'Mushtaq','Chugtai','Male','1982-02-18','37302-2134520-8','mushtaqch@gmail.com','2019-06-24','2019-12-01'),(6,'Zunorain','Roy','Male','1990-01-04','12000-7000012-1','zunoroy@gmail.com','2020-01-11','2020-01-12');
/*!40000 ALTER TABLE `customer_deleted` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_phone`
--

DROP TABLE IF EXISTS `customer_phone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_phone` (
  `customerID` int(11) NOT NULL,
  `phone_number` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_phone`
--

LOCK TABLES `customer_phone` WRITE;
/*!40000 ALTER TABLE `customer_phone` DISABLE KEYS */;
INSERT INTO `customer_phone` VALUES (4,'01111111000'),(3,'03005428100'),(2,'03035405615'),(7,'03039007860'),(7,'03039007862'),(6,'03090078601'),(3,'03215949342'),(2,'03335428100'),(1,'03345954310'),(4,'03030303032'),(5,'09007686529'),(5,'09007869122'),(1,'12345678912'),(1,'22222222222'),(4,'09090909900');
/*!40000 ALTER TABLE `customer_phone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_record`
--

DROP TABLE IF EXISTS `customer_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer_record` (
  `customerID` int(11) NOT NULL,
  `booking` varchar(5) NOT NULL,
  `booking_date` date NOT NULL,
  `leaving_date` date NOT NULL,
  `bills` int(11) NOT NULL,
  PRIMARY KEY (`customerID`,`leaving_date`,`booking_date`,`booking`),
  KEY `fk_Customer_Record_Customer1_idx` (`customerID`),
  CONSTRAINT `fk_Customer_Record_Customer1` FOREIGN KEY (`customerID`) REFERENCES `customer` (`customerID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_record`
--

LOCK TABLES `customer_record` WRITE;
/*!40000 ALTER TABLE `customer_record` DISABLE KEYS */;
INSERT INTO `customer_record` VALUES (1,'h1','2019-11-01','2019-12-01',0),(1,'h22','2019-12-30','2020-01-06',0),(1,'r38','2020-01-12','2020-01-25',1000),(2,'h20','2019-01-05','2019-01-25',0),(2,'h12','2019-02-01','2019-02-15',0),(2,'h2','2019-10-05','2019-12-01',0),(3,'r3','2019-07-03','2019-09-01',1500),(3,'h20','2020-01-08','2020-01-16',0),(3,'r20','2020-01-08','2020-01-17',0),(4,'r4','2019-06-02','2019-07-01',2000),(4,'h12','2020-01-02','2020-01-11',0),(4,'r23','2020-01-08','2020-01-17',0),(4,'h21','2020-01-16','2020-01-21',1200);
/*!40000 ALTER TABLE `customer_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `empID` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(25) NOT NULL,
  `last_name` varchar(25) NOT NULL,
  `gender` varchar(6) NOT NULL,
  `DOB` date NOT NULL,
  `CNIC` varchar(15) NOT NULL,
  `joinDate` date NOT NULL,
  `salary` int(11) NOT NULL,
  `branchID` varchar(45) NOT NULL,
  PRIMARY KEY (`empID`),
  UNIQUE KEY `empID_UNIQUE` (`empID`),
  UNIQUE KEY `CNIC_UNIQUE` (`CNIC`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'Mushtaq','Hameed','Male','1997-01-24','12345-1234567-3','2007-03-01',35000,'1'),(2,'Shafqat','Mahmood','Male','1980-03-10','12398-4563298-7','2009-09-03',25000,'1'),(3,'Rasheed','Sheikh','Female','1977-04-24','87654-0987609-1','2007-09-05',35000,'2'),(4,'Fayaz','Choudhary','Male','1987-03-28','98099-8909891-1','2008-10-01',30000,'2'),(5,'Amjad','khan','Male','1987-03-24','11111-1111111-1','2008-10-01',30000,'3'),(6,'Bilal','butt','Male','1979-04-18','22222-2222222-2','2007-10-01',40000,'3'),(7,'Soban','Ali','Male','1997-11-21','33333-3333333-3','2008-11-01',30000,'1'),(8,'Hamza','Imtiaz','Male','1977-03-28','44444-4444444-4','2009-11-01',25000,'2'),(9,'Usman','Shafeeq','Male','1980-03-30','55555-5555555-5','2011-10-01',20000,'2'),(10,'Wajahat','Hussain','Male','1991-01-23','66666-6666666-6','2012-11-01',20000,'2'),(11,'Faseeh','Javed','Male','1997-01-18','77777-7777777-7','2010-10-01',23000,'2'),(12,'Faisal','Quraishi','Male','1997-02-22','88888-8888888-8','2005-05-01',50000,'2'),(13,'Imran','Munawar','Male','1994-01-23','99999-9999999-9','2005-06-01',50000,'2'),(14,'Jamshaid','Ansari','Male','1989-04-22','10100-1010101-0','2006-01-02',40000,'2'),(15,'Mustafa','Kamal','Male','1988-01-12','21111-1111111-1','2006-04-01',40000,'2'),(16,'Ayesha','Hameed','Female','1987-07-11','31111-1111111-1','2007-01-02',35000,'2'),(17,'Zartaj','Gul','Female','1985-12-22','42222-4222222-4','2008-01-02',25000,'2'),(18,'Ibraheem','Bakai','Male','1986-11-30','54444-5666666-7','2012-01-03',20000,'2'),(19,'Mahira','Khan','Female','1991-02-19','23323-2323232-4','2007-10-02',40000,'3'),(21,'Bilawal','Kamal','Male','1996-12-31','99999-8888888-7','2020-01-16',20000,'3');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_phone`
--

DROP TABLE IF EXISTS `employee_phone`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_phone` (
  `empID` int(11) NOT NULL,
  `phone_number` varchar(15) NOT NULL,
  KEY `fk_Employee_Phone_Employee1_idx` (`empID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_phone`
--

LOCK TABLES `employee_phone` WRITE;
/*!40000 ALTER TABLE `employee_phone` DISABLE KEYS */;
INSERT INTO `employee_phone` VALUES (1,'09090909900'),(1,'78987654390'),(1,'87654098766'),(2,'01119998885'),(2,'23232323430'),(2,'98898989898'),(3,'43548975793'),(4,'97646768868'),(5,'89898989711'),(6,'09876432112'),(6,'89898989712'),(7,'09876543212'),(7,'89898989713'),(8,'89898989123'),(9,'89898989714'),(10,'89898981245'),(11,'89898989716'),(12,'89898989715'),(13,'89898989717'),(14,'89898989119'),(15,'89898989234'),(16,'89898983450'),(17,'89898989765'),(18,'89898989098'),(19,'89898987654'),(20,'09090090909'),(20,'56789045347'),(20,'87654987654'),(21,'10102020303'),(21,'89898989788');
/*!40000 ALTER TABLE `employee_phone` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_type`
--

DROP TABLE IF EXISTS `employee_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee_type` (
  `empID` int(11) NOT NULL,
  `typeID` int(11) NOT NULL,
  PRIMARY KEY (`empID`,`typeID`),
  KEY `fk_Employee_has_Type_Type1_idx` (`typeID`),
  KEY `fk_Employee_has_Type_Employee1_idx` (`empID`),
  CONSTRAINT `fk_Employee_has_Type_Employee1` FOREIGN KEY (`empID`) REFERENCES `employee` (`empID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Employee_has_Type_Type1` FOREIGN KEY (`typeID`) REFERENCES `type` (`typeID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_type`
--

LOCK TABLES `employee_type` WRITE;
/*!40000 ALTER TABLE `employee_type` DISABLE KEYS */;
INSERT INTO `employee_type` VALUES (5,1),(8,1),(9,1),(6,2),(10,2),(11,2),(7,3),(12,3),(13,3),(18,3),(14,4),(15,4),(16,5),(17,5),(19,5);
/*!40000 ALTER TABLE `employee_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empoyeeleft`
--

DROP TABLE IF EXISTS `empoyeeleft`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empoyeeleft` (
  `empID` int(11) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `gender` varchar(8) NOT NULL,
  `DOB` date NOT NULL,
  `CNIC` varchar(45) NOT NULL,
  `joiningDate` date NOT NULL,
  `leavingDate` date NOT NULL,
  `salary` int(11) NOT NULL,
  `branch` int(11) NOT NULL,
  PRIMARY KEY (`empID`),
  KEY `fk_EmpoyeeLeft_Employee1_idx` (`empID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empoyeeleft`
--

LOCK TABLES `empoyeeleft` WRITE;
/*!40000 ALTER TABLE `empoyeeleft` DISABLE KEYS */;
INSERT INTO `empoyeeleft` VALUES (6,'Bilal','butt','Male','1979-04-18','22222-2222222-2','2007-10-01','2020-01-17',40000,3),(20,'mustafa','khan','Male','1992-01-08','09876-0987654-8','2020-01-16','2020-01-16',20000,1);
/*!40000 ALTER TABLE `empoyeeleft` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `house`
--

DROP TABLE IF EXISTS `house`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `house` (
  `houseID` int(11) NOT NULL AUTO_INCREMENT,
  `huose_Type` varchar(25) NOT NULL,
  `branchID` int(11) NOT NULL,
  PRIMARY KEY (`houseID`),
  UNIQUE KEY `houseID_UNIQUE` (`houseID`),
  KEY `fk_House_Branch1_idx` (`branchID`),
  CONSTRAINT `fk_House_Branch1` FOREIGN KEY (`branchID`) REFERENCES `branch` (`branchID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `house`
--

LOCK TABLES `house` WRITE;
/*!40000 ALTER TABLE `house` DISABLE KEYS */;
INSERT INTO `house` VALUES (1,'Luxury',1),(2,'Luxury',2),(3,'Economy',1),(4,'roomsOnlyLuxury',2),(5,'roomsOnlyEconomy',2),(6,'Luxury',3),(7,'Economy',3),(8,'roomsOnlyLuxury',3),(9,'roomsOnlyEconomy',3),(10,'Economy',4),(11,'Econnomy',5),(12,'Luxury',4),(13,'roomsOnlyEconomy',4),(14,'roomsOnlyLuxury',5),(15,'Economy',5),(16,'Luxury',5),(17,'roomsOnlyEconomy',1),(18,'roomsOnlyLuxury',1),(19,'roomsOnlyEconomy',1),(20,'Economy',1),(21,'Luxury',1),(22,'Luxury',6),(23,'Economy',6),(24,'roomsOnlyEconomy',6),(25,'roomsOnlyLuxury',6);
/*!40000 ALTER TABLE `house` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `house_booking`
--

DROP TABLE IF EXISTS `house_booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `house_booking` (
  `houseID` int(11) NOT NULL,
  `customerID` int(11) NOT NULL,
  KEY `fk_House_Booking_House1_idx` (`houseID`),
  KEY `fk_House_Booking_Customer1_idx` (`customerID`),
  CONSTRAINT `fk_House_Booking_Customer1` FOREIGN KEY (`customerID`) REFERENCES `customer` (`customerID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_House_Booking_House1` FOREIGN KEY (`houseID`) REFERENCES `house` (`houseID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `house_booking`
--

LOCK TABLES `house_booking` WRITE;
/*!40000 ALTER TABLE `house_booking` DISABLE KEYS */;
INSERT INTO `house_booking` VALUES (1,1),(2,2),(22,1),(12,4),(20,3),(21,4);
/*!40000 ALTER TABLE `house_booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `location` (
  `locationID` int(11) NOT NULL AUTO_INCREMENT,
  `location` varchar(45) NOT NULL,
  `cityID` int(11) NOT NULL,
  PRIMARY KEY (`locationID`),
  UNIQUE KEY `locationID_UNIQUE` (`locationID`),
  KEY `fk_Location_City1_idx` (`cityID`),
  CONSTRAINT `fk_Location_City1` FOREIGN KEY (`cityID`) REFERENCES `city` (`cityID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'Sector F9',3),(2,'Iqbal Colony',2),(3,'Karangi Kareek',5),(4,'Khyber Pass Road',6),(5,'Sector G10',3);
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receptionist`
--

DROP TABLE IF EXISTS `receptionist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receptionist` (
  `empID` int(11) NOT NULL,
  `password` varchar(25) NOT NULL,
  KEY `fk_Receptionist_Employee1_idx` (`empID`),
  CONSTRAINT `fk_Receptionist_Employee1` FOREIGN KEY (`empID`) REFERENCES `employee` (`empID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receptionist`
--

LOCK TABLES `receptionist` WRITE;
/*!40000 ALTER TABLE `receptionist` DISABLE KEYS */;
INSERT INTO `receptionist` VALUES (1,'mush123'),(3,'rasheed123'),(21,'bilawal123');
/*!40000 ALTER TABLE `receptionist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `response`
--

DROP TABLE IF EXISTS `response`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `response` (
  `responseID` int(11) NOT NULL AUTO_INCREMENT,
  `response` varchar(100) NOT NULL,
  `complaintID` int(11) NOT NULL,
  `responseDate` date NOT NULL,
  PRIMARY KEY (`responseID`),
  UNIQUE KEY `responseID_UNIQUE` (`responseID`),
  KEY `fk_Response_Complaint1_idx` (`complaintID`),
  CONSTRAINT `fk_Response_Complaint1` FOREIGN KEY (`complaintID`) REFERENCES `complaint` (`complaintID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `response`
--

LOCK TABLES `response` WRITE;
/*!40000 ALTER TABLE `response` DISABLE KEYS */;
INSERT INTO `response` VALUES (1,'Our Team has assigned the task, to overcome this problem',1,'2020-01-12'),(2,'Sweepers are assigned for cleaning process',2,'2020-01-12'),(3,'we asure you to keep it as you\nwant in future!',3,'2020-01-14');
/*!40000 ALTER TABLE `response` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `roomID` int(11) NOT NULL AUTO_INCREMENT,
  `room_number` varchar(5) NOT NULL,
  `houseID` int(11) NOT NULL,
  PRIMARY KEY (`roomID`),
  UNIQUE KEY `roomID_UNIQUE` (`roomID`),
  KEY `fk_Room_House1_idx` (`houseID`),
  CONSTRAINT `fk_Room_House1` FOREIGN KEY (`houseID`) REFERENCES `house` (`houseID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,'001',4),(2,'002',4),(3,'003',5),(4,'004',5),(5,'001',8),(6,'002',8),(7,'003',8),(8,'004',8),(9,'010',9),(10,'100',9),(11,'023',9),(12,'001',13),(13,'002',13),(14,'003',13),(15,'004',13),(16,'005',13),(17,'001',14),(18,'002',14),(19,'001',17),(20,'002',17),(21,'003',17),(22,'001',18),(23,'002',18),(24,'001',19),(25,'002',19),(26,'003',19),(27,'004',19),(28,'005',19),(29,'001',24),(30,'002',24),(31,'003',24),(32,'004',24),(33,'005',24),(34,'006',24),(35,'001',25),(36,'002',25),(37,'003',25),(38,'004',25);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_booking`
--

DROP TABLE IF EXISTS `room_booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_booking` (
  `roomID` int(11) NOT NULL,
  `customerID` int(11) NOT NULL,
  KEY `fk_Room_Booking_Room1_idx` (`roomID`),
  KEY `fk_Room_Booking_Customer1_idx` (`customerID`),
  CONSTRAINT `fk_Room_Booking_Customer1` FOREIGN KEY (`customerID`) REFERENCES `customer` (`customerID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Room_Booking_Room1` FOREIGN KEY (`roomID`) REFERENCES `room` (`roomID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_booking`
--

LOCK TABLES `room_booking` WRITE;
/*!40000 ALTER TABLE `room_booking` DISABLE KEYS */;
INSERT INTO `room_booking` VALUES (3,3),(4,4),(20,3),(23,4),(38,1);
/*!40000 ALTER TABLE `room_booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_record`
--

DROP TABLE IF EXISTS `service_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service_record` (
  `billsID` int(11) NOT NULL,
  `empID` int(11) NOT NULL,
  `date` date NOT NULL,
  `time` varchar(45) NOT NULL,
  PRIMARY KEY (`billsID`,`empID`),
  KEY `fk_Service_Record_Customer1_idx` (`billsID`),
  KEY `fk_Service_Record_Employee1_idx` (`empID`),
  CONSTRAINT `fk_Service_Record_Employee1` FOREIGN KEY (`empID`) REFERENCES `employee` (`empID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_record`
--

LOCK TABLES `service_record` WRITE;
/*!40000 ALTER TABLE `service_record` DISABLE KEYS */;
INSERT INTO `service_record` VALUES (1,1,'2019-09-20','11:01 AM'),(1,7,'2019-09-20','09:01 AM'),(1,8,'2019-09-20','11:31 AM'),(2,1,'2019-10-13','11:49 AM'),(2,7,'2019-10-13','02:05 PM'),(2,8,'2019-10-13','11:21 AM'),(3,1,'2019-11-05','11:51 AM'),(3,7,'2019-11-10','10:00 AM'),(3,8,'2019-11-06','11:11 AM'),(4,1,'2019-06-06','11:41 AM'),(4,7,'2019-06-01','01:01 PM'),(4,8,'2019-06-13','11:08 AM'),(11,8,'2020-01-11','06:29 PM'),(12,9,'2020-01-16','08:31 PM');
/*!40000 ALTER TABLE `service_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `services`
--

DROP TABLE IF EXISTS `services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `services` (
  `serviceID` int(11) NOT NULL AUTO_INCREMENT,
  `service_name` varchar(25) NOT NULL,
  `rate` int(11) NOT NULL,
  PRIMARY KEY (`serviceID`),
  UNIQUE KEY `serviceID_UNIQUE` (`serviceID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `services`
--

LOCK TABLES `services` WRITE;
/*!40000 ALTER TABLE `services` DISABLE KEYS */;
INSERT INTO `services` VALUES (1,'housekeeping',200),(2,'repair',150),(3,'cleaning',100),(4,'food',250),(5,'laundry',100);
/*!40000 ALTER TABLE `services` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type`
--

DROP TABLE IF EXISTS `type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type` (
  `typeID` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(15) NOT NULL,
  PRIMARY KEY (`typeID`),
  UNIQUE KEY `typeID_UNIQUE` (`typeID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type`
--

LOCK TABLES `type` WRITE;
/*!40000 ALTER TABLE `type` DISABLE KEYS */;
INSERT INTO `type` VALUES (1,'housekeeping'),(2,'repairer'),(3,'sweeper'),(4,'cook'),(5,'laundryman');
/*!40000 ALTER TABLE `type` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-17 12:10:16
