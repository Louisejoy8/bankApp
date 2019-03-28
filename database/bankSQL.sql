-- --------------------------------------------------------
-- Vært:                         127.0.0.1
-- Server-version:               5.6.37 - MySQL Community Server (GPL)
-- ServerOS:                     Win32
-- HeidiSQL Version:             10.1.0.5464
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for bank
CREATE DATABASE IF NOT EXISTS `bank` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `bank`;

-- Dumping structure for tabel bank.accounts
CREATE TABLE IF NOT EXISTS `accounts` (
  `accountnumber` varchar(50) NOT NULL,
  `user_id` int(11) NOT NULL,
  `balance` double DEFAULT '0',
  `type` varchar(50) NOT NULL DEFAULT '0',
  `account_name` varchar(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (`accountnumber`),
  KEY `FK_accounts_users` (`user_id`),
  CONSTRAINT `FK_accounts_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table bank.accounts: ~6 rows (approximately)
/*!40000 ALTER TABLE `accounts` DISABLE KEYS */;
INSERT INTO `accounts` (`accountnumber`, `user_id`, `balance`, `type`, `account_name`) VALUES
	('1553761686474', 8, 74000, 'saving', 'Opsparing '),
	('1553761697470', 8, 25400, 'checking', 'Köpa '),
	('1553761705078', 8, 0, 'salary', 'Lönekonto'),
	('1553761950962', 14, 49400, 'checking', 'KöpaAccount'),
	('1553761957341', 14, 50000, 'saving', 'Savings'),
	('1553761968034', 14, 0, 'salary', 'För lön '),
	('1553762088473', 16, 50000, 'saving', 'SavingAccount'),
	('1553762098541', 16, 29800, 'checking', 'BuyingAccount'),
	('1553762110073', 16, 20000, 'salary', 'ForPayCheck'),
	('1553762185999', 17, 0, 'checking', 'Købe'),
	('1553762191926', 17, 99800, 'saving', 'Opsparing'),
	('1553762197743', 17, 0, 'salary', 'Til løn'),
	('1553762383996', 15, 94200, 'checking', 'Forbrug '),
	('1553762394367', 15, 500, 'salary', 'Lønkonto'),
	('1553762408581', 15, 4500, 'saving', 'Opsparing ');
/*!40000 ALTER TABLE `accounts` ENABLE KEYS */;

-- Dumping structure for tabel bank.transactions
CREATE TABLE IF NOT EXISTS `transactions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `senderaccount` varchar(50) NOT NULL DEFAULT '0',
  `message` varchar(50) NOT NULL DEFAULT '0',
  `amount` double NOT NULL DEFAULT '0',
  `time` varchar(50) NOT NULL,
  `receiveraccount` varchar(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=latin1;

-- Dumping data for table bank.transactions: ~28 rows (approximately)
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` (`id`, `senderaccount`, `message`, `amount`, `time`, `receiveraccount`) VALUES
	(70, 'Your boss', 'Your salary', 100000, '2019-03-28', '1553761705078'),
	(71, '1553761705078', 'Til opsparing ', 75000, '2019-03-28', '1553761686474'),
	(72, '1553761705078', 'Til forbrug ', 25000, '2019-03-28', '1553761697470'),
	(73, '1553761697470', 'Skor ', 200, '2019-03-28', 'Card purchase'),
	(74, '1553761697470', 'Hund', 200, '2019-03-28', 'Card purchase'),
	(75, '1553761697470', 'Mat ', 200, '2019-03-28', 'Card purchase'),
	(76, 'Your boss', 'Your salary', 100000, '2019-03-28', '1553761968034'),
	(77, '1553761968034', 'Buy nice things ', 50000, '2019-03-28', '1553761950962'),
	(78, '1553761968034', 'Kanske till ett hus', 50000, '2019-03-28', '1553761957341'),
	(79, '1553761950962', 'Katt ', 200, '2019-03-28', 'Card purchase'),
	(80, '1553761950962', 'Bord ', 200, '2019-03-28', 'Card purchase'),
	(81, '1553761950962', 'Stol ', 200, '2019-03-28', 'Card purchase'),
	(82, 'Your boss', 'Your salary', 100000, '2019-03-28', '1553762110073'),
	(83, '1553762110073', 'To buy things ', 30000, '2019-03-28', '1553762098541'),
	(84, '1553762110073', 'Til opsparing ', 50000, '2019-03-28', '1553762088473'),
	(85, 'Your boss', 'Your salary', 100000, '2019-03-28', '1553762197743'),
	(86, '1553762197743', 'Til opsparing ', 99800, '2019-03-28', '1553762191926'),
	(87, '1553762197743', 'Til forbrug ', 200, '2019-03-28', '1553762185999'),
	(88, '1553762185999', 'Sodavand ', 200, '2019-03-28', 'Card purchase'),
	(89, 'Your boss', 'Your salary', 100000, '2019-03-28', '1553762394367'),
	(90, '1553762394367', 'Til ting ', 95000, '2019-03-28', '1553762383996'),
	(91, '1553762394367', 'Til ting ', 4500, '2019-03-28', '1553762408581'),
	(92, '1553762383996', 'GreenGate ', 200, '2019-03-28', 'Card purchase'),
	(93, '1553762383996', 'Mad ', 200, '2019-03-28', 'Card purchase'),
	(94, '1553762383996', 'Hund ', 200, '2019-03-28', 'Card purchase'),
	(95, '1553762383996', 'Tshirt ', 200, '2019-03-28', 'Card purchase'),
	(96, '1553762098541', 'Hund ', 200, '2019-03-28', 'Card purchase'),
	(97, '1553761686474', 'Forbrug', 1000, '2019-03-28', '1553761697470');
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;

-- Dumping structure for tabel bank.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `birthdate` varchar(50) NOT NULL DEFAULT '0',
  `name` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

-- Dumping data for table bank.users: ~2 rows (approximately)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `birthdate`, `name`, `username`, `password`) VALUES
	(8, '2807984545', 'Louise Frahm', 'LouiseJoy8', 'hejmormor'),
	(14, '1993-07-08', 'Christian Frahm', 'ChristianF', '123'),
	(15, '1958-07-13', 'Sanne Frahm', 'SanneF', '123'),
	(16, '1996-01-12', 'Oliver Frahm', 'OliverF', '123'),
	(17, '1947-04-05', 'Knud Frahm', 'KnudF', '123');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
