-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: Game
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `armors`
--

DROP TABLE IF EXISTS `armors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `armors` (
  `ArmorID` int NOT NULL AUTO_INCREMENT,
  `ArmorName` varchar(100) NOT NULL,
  `ArmorDamageNegation` int NOT NULL,
  `ArmorRarity` varchar(30) NOT NULL,
  `ArmorSpecialEffect` varchar(100) DEFAULT NULL,
  `Weight` decimal(5,2) NOT NULL,
  PRIMARY KEY (`ArmorID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `armors`
--

LOCK TABLES `armors` WRITE;
/*!40000 ALTER TABLE `armors` DISABLE KEYS */;
INSERT INTO `armors` VALUES (1,'Leather Armor',10,'Common',NULL,5.50),(2,'Iron Armor',25,'Uncommon','Poison Resistance',12.00),(3,'Knight Armor',40,'Rare','Fire Resistance',20.00);
/*!40000 ALTER TABLE `armors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `consumables`
--

DROP TABLE IF EXISTS `consumables`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consumables` (
  `ConsumablesID` int NOT NULL AUTO_INCREMENT,
  `ConsumableName` varchar(100) NOT NULL,
  `EffectDuration` int NOT NULL,
  `Rarity` varchar(30) NOT NULL,
  `ConsumableEffect` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ConsumablesID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consumables`
--

LOCK TABLES `consumables` WRITE;
/*!40000 ALTER TABLE `consumables` DISABLE KEYS */;
INSERT INTO `consumables` VALUES (1,'Health Potion',0,'Common','Restore Health'),(2,'Stamina Potion',0,'Common','Restore Stamina'),(3,'Strength Elixir',60,'Rare','Increase Attack');
/*!40000 ALTER TABLE `consumables` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enemies`
--

DROP TABLE IF EXISTS `enemies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `enemies` (
  `EnemyID` int NOT NULL AUTO_INCREMENT,
  `EnemyName` varchar(100) NOT NULL,
  `Health` int NOT NULL,
  `Damage` int NOT NULL,
  `EnemyType` varchar(50) NOT NULL,
  `Location` varchar(100) NOT NULL,
  PRIMARY KEY (`EnemyID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enemies`
--

LOCK TABLES `enemies` WRITE;
/*!40000 ALTER TABLE `enemies` DISABLE KEYS */;
INSERT INTO `enemies` VALUES (1,'Goblin',50,10,'Beast','Forest'),(2,'Skeleton Warrior',80,15,'Undead','Graveyard'),(3,'Fire Demon',200,35,'Demon','Volcano');
/*!40000 ALTER TABLE `enemies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `levels`
--

DROP TABLE IF EXISTS `levels`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `levels` (
  `LevelID` int NOT NULL,
  `Health` int NOT NULL,
  `Stamina` int NOT NULL,
  `MaxLoad` int NOT NULL,
  `BaseAttack` int NOT NULL,
  PRIMARY KEY (`LevelID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `levels`
--

LOCK TABLES `levels` WRITE;
/*!40000 ALTER TABLE `levels` DISABLE KEYS */;
INSERT INTO `levels` VALUES (1,100,50,30,10),(2,120,55,35,15),(3,150,65,40,20),(4,180,75,45,25),(5,220,90,50,30);
/*!40000 ALTER TABLE `levels` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `npcs`
--

DROP TABLE IF EXISTS `npcs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `npcs` (
  `NPCID` int NOT NULL AUTO_INCREMENT,
  `NPCName` varchar(100) NOT NULL,
  `Location` varchar(100) NOT NULL,
  `DialogueLineNumber` int NOT NULL,
  PRIMARY KEY (`NPCID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `npcs`
--

LOCK TABLES `npcs` WRITE;
/*!40000 ALTER TABLE `npcs` DISABLE KEYS */;
INSERT INTO `npcs` VALUES (1,'Elder Rowan','Village Square',12),(2,'Blacksmith Torin','Forge',8),(3,'Mage Selene','Tower',15);
/*!40000 ALTER TABLE `npcs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player_consumables`
--

DROP TABLE IF EXISTS `player_consumables`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `player_consumables` (
  `PlayerID` int NOT NULL,
  `ConsumablesID` int NOT NULL,
  PRIMARY KEY (`PlayerID`,`ConsumablesID`),
  KEY `ConsumablesID` (`ConsumablesID`),
  CONSTRAINT `player_consumables_ibfk_1` FOREIGN KEY (`PlayerID`) REFERENCES `players` (`PlayerID`),
  CONSTRAINT `player_consumables_ibfk_2` FOREIGN KEY (`ConsumablesID`) REFERENCES `consumables` (`ConsumablesID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player_consumables`
--

LOCK TABLES `player_consumables` WRITE;
/*!40000 ALTER TABLE `player_consumables` DISABLE KEYS */;
INSERT INTO `player_consumables` VALUES (1,1),(3,1),(1,2);
/*!40000 ALTER TABLE `player_consumables` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `player_weapons`
--

DROP TABLE IF EXISTS `player_weapons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `player_weapons` (
  `PlayerID` int NOT NULL,
  `WeaponID` int NOT NULL,
  PRIMARY KEY (`PlayerID`,`WeaponID`),
  KEY `WeaponID` (`WeaponID`),
  CONSTRAINT `player_weapons_ibfk_1` FOREIGN KEY (`PlayerID`) REFERENCES `players` (`PlayerID`),
  CONSTRAINT `player_weapons_ibfk_2` FOREIGN KEY (`WeaponID`) REFERENCES `weapons` (`WeaponID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `player_weapons`
--

LOCK TABLES `player_weapons` WRITE;
/*!40000 ALTER TABLE `player_weapons` DISABLE KEYS */;
INSERT INTO `player_weapons` VALUES (1,1),(1,3),(3,3);
/*!40000 ALTER TABLE `player_weapons` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `players`
--

DROP TABLE IF EXISTS `players`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `players` (
  `PlayerID` int NOT NULL AUTO_INCREMENT,
  `Name` varchar(100) NOT NULL,
  `ModelType` varchar(50) NOT NULL,
  `Voicetype` varchar(50) NOT NULL,
  `CurrentLevel` int NOT NULL DEFAULT '1',
  `ArmorID` int DEFAULT NULL,
  PRIMARY KEY (`PlayerID`),
  KEY `ArmorID` (`ArmorID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `players`
--

LOCK TABLES `players` WRITE;
/*!40000 ALTER TABLE `players` DISABLE KEYS */;
INSERT INTO `players` VALUES (1,'Arthos','Warrior','Male',1,1),(3,'Bo3bo3','Knight','Male',2,3);
/*!40000 ALTER TABLE `players` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quests`
--

DROP TABLE IF EXISTS `quests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quests` (
  `QuestID` int NOT NULL AUTO_INCREMENT,
  `QuestName` varchar(100) NOT NULL,
  `NPCID` int DEFAULT NULL,
  `QuestDescription` text,
  PRIMARY KEY (`QuestID`),
  KEY `NPCID` (`NPCID`),
  CONSTRAINT `quests_ibfk_1` FOREIGN KEY (`NPCID`) REFERENCES `npcs` (`NPCID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quests`
--

LOCK TABLES `quests` WRITE;
/*!40000 ALTER TABLE `quests` DISABLE KEYS */;
INSERT INTO `quests` VALUES (1,'Defend the Village',1,'Protect the village from incoming enemies'),(2,'Forge the Legendary Sword',2,'Collect materials to forge a powerful weapon'),(3,'Secrets of the Tower',3,'Discover the hidden knowledge in the mage tower');
/*!40000 ALTER TABLE `quests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quests_enemies`
--

DROP TABLE IF EXISTS `quests_enemies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quests_enemies` (
  `QuestID` int NOT NULL,
  `EnemyID` int NOT NULL,
  PRIMARY KEY (`QuestID`,`EnemyID`),
  KEY `EnemyID` (`EnemyID`),
  CONSTRAINT `quests_enemies_ibfk_1` FOREIGN KEY (`QuestID`) REFERENCES `quests` (`QuestID`),
  CONSTRAINT `quests_enemies_ibfk_2` FOREIGN KEY (`EnemyID`) REFERENCES `enemies` (`EnemyID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quests_enemies`
--

LOCK TABLES `quests_enemies` WRITE;
/*!40000 ALTER TABLE `quests_enemies` DISABLE KEYS */;
INSERT INTO `quests_enemies` VALUES (1,1),(1,2),(2,2),(3,3);
/*!40000 ALTER TABLE `quests_enemies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `weapons`
--

DROP TABLE IF EXISTS `weapons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `weapons` (
  `WeaponID` int NOT NULL AUTO_INCREMENT,
  `WeaponName` varchar(100) NOT NULL,
  `WeaponType` varchar(50) NOT NULL,
  `Weight` decimal(5,2) NOT NULL,
  `Damage` int NOT NULL,
  `WeaponEffect` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`WeaponID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `weapons`
--

LOCK TABLES `weapons` WRITE;
/*!40000 ALTER TABLE `weapons` DISABLE KEYS */;
INSERT INTO `weapons` VALUES (1,'Iron Sword','Sword',6.00,25,NULL),(2,'Fire Staff','Staff',4.50,30,'Burn'),(3,'Battle Axe','Axe',9.00,40,'Bleed');
/*!40000 ALTER TABLE `weapons` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-25  2:29:23
