-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: May 29, 2024 at 11:17 AM
-- Server version: 5.7.39
-- PHP Version: 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `BeverageAppDB`
--

-- --------------------------------------------------------

--
-- Table structure for table `Customers`
--

CREATE TABLE `Customers` (
  `customer_id` int(11) NOT NULL,
  `customer_name` varchar(50) NOT NULL,
  `customer_mail` varchar(50) NOT NULL,
  `customer_password` varchar(100) DEFAULT NULL,
  `is_admin` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Customers`
--

INSERT INTO `Customers` (`customer_id`, `customer_name`, `customer_mail`, `customer_password`, `is_admin`) VALUES
(21, 'treg', 'fd', 'ds', 0),
(22, 'fsdsdf', 'cxz', 'bcv', 0),
(23, 'bvvcb', 'dsa', 'gfdfgd', 0),
(24, 'fggfgdf', 'dfs', 'hgf', 0),
(25, 'dsadsak', 'fgdfj', 'vcx', 0),
(26, 'vc', 'ds', 'erw', 0),
(27, 'popklj', 'lkjkj', 'mnnm', 0),
(28, 'cvf', 'sd', 'uy', 0),
(29, 'bvcvcfdg', 'dsfdfvc', 'erwrgd', 0),
(30, 'uyjgh', 'sdfd', 'bvcbcv', 0),
(31, 'aa', 'aa', 'aa', 0),
(32, 'bb', 'bb', 'bb', 0);

-- --------------------------------------------------------

--
-- Table structure for table `Drinks`
--

CREATE TABLE `Drinks` (
  `drink_id` int(11) NOT NULL,
  `drink_image` varchar(200) NOT NULL,
  `drink_price` int(11) NOT NULL,
  `drink_name` varchar(50) NOT NULL,
  `drink_desc` varchar(200) DEFAULT NULL,
  `rate_counter` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Drinks`
--

INSERT INTO `Drinks` (`drink_id`, `drink_image`, `drink_price`, `drink_name`, `drink_desc`, `rate_counter`, `type_id`) VALUES
(1, 'https://images.pexels.com/photos/2456429/pexels-photo-2456429.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2', 90, 'Turkish Coffee', 'This is description part of the beverage for Turkish Coffee', 0, 1),
(2, 'https://images.pexels.com/photos/1493080/pexels-photo-1493080.jpeg', 50, 'Black Tea', 'This is description part of the beverage for Black Tea', 2, 2),
(3, 'https://images.pexels.com/photos/18036969/pexels-photo-18036969/free-photo-of-a-glass-of-coffee-with-beans-on-top.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2', 80, 'White Chocolate Mocha', 'This is description part of the beverage for White Chocolate Mocha', 1, 1),
(4, 'https://images.pexels.com/photos/3727250/pexels-photo-3727250.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2', 100, 'Milkshake', 'This is description part of the beverage for White Milkshake', 0, 3),
(5, 'https://images.pexels.com/photos/1233319/pexels-photo-1233319.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2', 110, 'Cool Lime', 'This is description part of the beverage for White Cool Lime', 1, 3),
(6, 'https://images.pexels.com/photos/814264/pexels-photo-814264.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2', 65, 'Green Tea', 'This is description part of the beverage for White Green tea', 0, 2);

-- --------------------------------------------------------

--
-- Table structure for table `Drink_Rates`
--

CREATE TABLE `Drink_Rates` (
  `rate_id` int(11) NOT NULL,
  `rate_value` int(11) NOT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `drink_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Drink_Rates`
--

INSERT INTO `Drink_Rates` (`rate_id`, `rate_value`, `customer_id`, `drink_id`) VALUES
(11, 3, 31, 2),
(12, 2, 31, 3),
(13, 1, 31, 5),
(14, 2, 32, 2);

-- --------------------------------------------------------

--
-- Table structure for table `Drink_Types`
--

CREATE TABLE `Drink_Types` (
  `type_id` int(11) NOT NULL,
  `type_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Drink_Types`
--

INSERT INTO `Drink_Types` (`type_id`, `type_name`) VALUES
(1, 'Coffe'),
(3, 'Cold Drink'),
(2, 'Tea');

-- --------------------------------------------------------

--
-- Table structure for table `Favorite_Drinks`
--

CREATE TABLE `Favorite_Drinks` (
  `record_id` int(11) NOT NULL,
  `record_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `customer_id` int(11) DEFAULT NULL,
  `drink_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Favorite_Drinks`
--

INSERT INTO `Favorite_Drinks` (`record_id`, `record_date`, `customer_id`, `drink_id`) VALUES
(10, '2024-05-18 21:08:19', 31, 2),
(12, '2024-05-19 00:19:02', 31, 3),
(14, '2024-05-19 21:35:45', 32, 1),
(15, '2024-05-20 14:27:35', 31, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Customers`
--
ALTER TABLE `Customers`
  ADD PRIMARY KEY (`customer_id`),
  ADD UNIQUE KEY `customer_mail` (`customer_mail`);

--
-- Indexes for table `Drinks`
--
ALTER TABLE `Drinks`
  ADD PRIMARY KEY (`drink_id`),
  ADD UNIQUE KEY `drink_name` (`drink_name`),
  ADD KEY `type_id` (`type_id`);

--
-- Indexes for table `Drink_Rates`
--
ALTER TABLE `Drink_Rates`
  ADD PRIMARY KEY (`rate_id`),
  ADD KEY `customer_id` (`customer_id`),
  ADD KEY `drink_id` (`drink_id`);

--
-- Indexes for table `Drink_Types`
--
ALTER TABLE `Drink_Types`
  ADD PRIMARY KEY (`type_id`),
  ADD UNIQUE KEY `type_name` (`type_name`);

--
-- Indexes for table `Favorite_Drinks`
--
ALTER TABLE `Favorite_Drinks`
  ADD PRIMARY KEY (`record_id`),
  ADD KEY `customer_id` (`customer_id`),
  ADD KEY `drink_id` (`drink_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Customers`
--
ALTER TABLE `Customers`
  MODIFY `customer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `Drinks`
--
ALTER TABLE `Drinks`
  MODIFY `drink_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `Drink_Rates`
--
ALTER TABLE `Drink_Rates`
  MODIFY `rate_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `Drink_Types`
--
ALTER TABLE `Drink_Types`
  MODIFY `type_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `Favorite_Drinks`
--
ALTER TABLE `Favorite_Drinks`
  MODIFY `record_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Drinks`
--
ALTER TABLE `Drinks`
  ADD CONSTRAINT `drinks_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `drink_types` (`type_id`);

--
-- Constraints for table `Drink_Rates`
--
ALTER TABLE `Drink_Rates`
  ADD CONSTRAINT `drink_rates_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `Customers` (`customer_id`),
  ADD CONSTRAINT `drink_rates_ibfk_2` FOREIGN KEY (`drink_id`) REFERENCES `Drinks` (`drink_id`);

--
-- Constraints for table `Favorite_Drinks`
--
ALTER TABLE `Favorite_Drinks`
  ADD CONSTRAINT `favorite_drinks_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `Customers` (`customer_id`),
  ADD CONSTRAINT `favorite_drinks_ibfk_2` FOREIGN KEY (`drink_id`) REFERENCES `Drinks` (`drink_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
