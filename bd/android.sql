-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jan 05, 2020 at 06:07 PM
-- Server version: 5.7.24
-- PHP Version: 7.2.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `android`
--

-- --------------------------------------------------------

--
-- Table structure for table `enchere`
--

DROP TABLE IF EXISTS `enchere`;
CREATE TABLE IF NOT EXISTS `enchere` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user_achete` int(11) NOT NULL,
  `id_produit` int(11) NOT NULL,
  `cost` double NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_user_achete` (`id_user_achete`),
  UNIQUE KEY `id_produit` (`id_produit`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `enchere`
--

INSERT INTO `enchere` (`id`, `id_user_achete`, `id_produit`, `cost`) VALUES
(6, 8, 33, 55555);

-- --------------------------------------------------------

--
-- Table structure for table `produit`
--

DROP TABLE IF EXISTS `produit`;
CREATE TABLE IF NOT EXISTS `produit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `image` varchar(250) NOT NULL,
  `name` varchar(250) NOT NULL,
  `description` varchar(250) NOT NULL,
  `prixdebut` double NOT NULL,
  `encour` int(1) NOT NULL DEFAULT '0',
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `produit`
--

INSERT INTO `produit` (`id`, `image`, `name`, `description`, `prixdebut`, `encour`, `user_id`) VALUES
(33, 'f76e685ef42409aa22fbd2c8eea813a4.png', 'qqq', 'sss', 222, 1, 8);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `u_id` varchar(36) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `image` varchar(100) DEFAULT NULL,
  `encrypted_password` varchar(128) NOT NULL,
  `salt` varchar(16) NOT NULL,
  `telephone` varchar(8) DEFAULT NULL,
  `addresse` varchar(100) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_id` (`u_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `u_id`, `name`, `email`, `image`, `encrypted_password`, `salt`, `telephone`, `addresse`, `created_at`, `updated_at`) VALUES
(8, '8132e857-89d8-4de3-8eb5-3605596810f3', 'hajri Baha', 'hajribaha05@gmail.com', '80bb359a50abbb4f9e78941989536d56.png', '013cd11b659609bf46159c865b1320d4a8048700b200604bbb81a73f9ae036198b253124f851e89a2a8fc7f6d2b54c86740af5809ec39a6332b51458ee2569de', 'c1693fc94a5634ae', '33355889', 'ARIANA', '2020-01-05 13:47:14', '2020-01-05 16:21:42');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `enchere`
--
ALTER TABLE `enchere`
  ADD CONSTRAINT `id_achete` FOREIGN KEY (`id_user_achete`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `id_produit` FOREIGN KEY (`id_produit`) REFERENCES `produit` (`id`);

--
-- Constraints for table `produit`
--
ALTER TABLE `produit`
  ADD CONSTRAINT `user_postulee` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
