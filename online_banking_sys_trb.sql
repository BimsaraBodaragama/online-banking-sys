-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 19, 2022 at 11:56 AM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `online_banking_sys_trb`
--

-- --------------------------------------------------------

--
-- Table structure for table `appointment`
--

CREATE TABLE `appointment` (
  `id` bigint(20) NOT NULL,
  `confirmed` bit(1) NOT NULL,
  `date` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `appointment`
--

INSERT INTO `appointment` (`id`, `confirmed`, `date`, `description`, `location`, `user_id`) VALUES
(4, b'0', '2016-10-21 15:10:00', 'Hi', 'Barisal', 1),
(14, b'0', '2016-10-21 15:10:00', 'AAA', 'Dhaka', 2);

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(89),
(89),
(89),
(89),
(89),
(89),
(89),
(89);

-- --------------------------------------------------------

--
-- Table structure for table `primary_account`
--

CREATE TABLE `primary_account` (
  `id` bigint(20) NOT NULL,
  `account_balance` decimal(19,2) DEFAULT NULL,
  `account_number` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `primary_account`
--

INSERT INTO `primary_account` (`id`, `account_balance`, `account_number`) VALUES
(1, '10090800.27', 456),
(2, '0.00', 888),
(18, '1000.00', 111),
(22, '3050.00', 2),
(26, '7500.00', 785),
(32, '7500.00', 785),
(38, '7500.00', 7851),
(42, '10000.00', 999),
(46, '500.00', 585),
(70, '15420.00', 1000123),
(85, '6500.00', 158898);

-- --------------------------------------------------------

--
-- Table structure for table `primary_transaction`
--

CREATE TABLE `primary_transaction` (
  `id` bigint(20) NOT NULL,
  `amount` double NOT NULL,
  `available_balance` decimal(19,2) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `primary_account_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `primary_transaction`
--

INSERT INTO `primary_transaction` (`id`, `amount`, `available_balance`, `date`, `description`, `status`, `type`, `primary_account_id`) VALUES
(1, 500, '100500.00', '2022-03-12 19:26:07', 'Deposit to Primary Account', 'Finished', 'Account', 1),
(2, 200, '100300.00', '2022-03-12 19:26:16', 'Withdraw from Primary Account', 'Finished', 'Account', 1),
(3, 477, '99823.00', '2022-03-12 19:27:02', 'Between account transfer from Primary to Savings', 'Finished', 'Account', 1),
(6, 1, '99822.00', '2022-03-12 19:33:22', 'Transfer to recipient Sakuni', 'Finished', 'Transfer', 1),
(8, 777, '105119.00', '2022-03-13 04:37:12', 'Deposit to Primary Account', 'Finished', 'Account', 1),
(11, 1, '105189.00', '2022-03-13 04:39:09', 'Transfer to recipient Sakuni', 'Finished', 'Transfer', 1),
(50, 1, '10105190.00', '2022-04-11 01:49:40', 'Deposit to Primary Account', 'Finished', 'Account', 1),
(53, 1, '10105189.00', '2022-04-11 02:16:29', 'Withdraw from Primary Account', 'Finished', 'Account', 1),
(54, 50000, '10055189.00', '2022-04-11 03:38:39', 'Withdraw from Primary Account', 'Finished', 'Account', 1),
(55, 73333, '10128522.00', '2022-04-11 04:13:29', 'Deposit to Primary Account', 'Finished', 'Account', 1),
(56, 50000, '10078522.00', '2022-04-11 04:14:03', 'Withdraw from Primary Account', 'Finished', 'Account', 1),
(57, 75901, '10154423.00', '2022-04-11 04:31:31', 'Deposit to Primary Account', 'Finished', 'Account', 1),
(58, 1.73, '10154421.27', '2022-04-11 05:03:15', 'Between account transfer from Primary to Savings', 'Finished', 'Account', 1),
(59, 233, '10154188.27', '2022-04-11 05:36:56', 'Transfer to recipient Sahan', 'Finished', 'Transfer', 1),
(64, 481, '10153707.27', '2022-04-11 07:45:01', 'Between account transfer from Primary to Savings', 'Finished', 'Account', 1),
(65, 500, '10154207.27', '2022-04-11 07:46:10', 'Deposit to Primary Account', 'Finished', 'Account', 1),
(68, 4500, '10149907.27', '2022-04-11 07:47:25', 'Transfer to recipient Gamunu Bandara', 'Finished', 'Transfer', 1),
(77, 50, '10149897.27', '2022-04-11 21:47:47', 'Between account transfer from Primary to Savings', 'Finished', 'Account', 1),
(78, 58555, '10091342.27', '2022-04-11 21:48:23', 'Transfer to recipient Sahan', 'Finished', 'Transfer', 1),
(82, 42, '10091300.27', '2022-04-19 15:16:58', 'Between account transfer from Primary to Savings', 'Finished', 'Account', 1),
(83, 500, '10090800.27', '2022-04-19 15:17:13', 'Transfer to recipient Gamunu Bandara', 'Finished', 'Transfer', 1);

-- --------------------------------------------------------

--
-- Table structure for table `recipient`
--

CREATE TABLE `recipient` (
  `id` bigint(20) NOT NULL,
  `account_number` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `recipient`
--

INSERT INTO `recipient` (`id`, `account_number`, `description`, `email`, `name`, `phone`, `user_id`) VALUES
(15, '1111111', 'sahan payee', 'sahan@gamail.com', 'Sahan', '0771111111', 1),
(61, '987789', 'Friend 2', 'gb3@gmail.com', 'Gamunu Bandara', '0779999999', 1);

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `role_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`role_id`, `name`) VALUES
(1, 'User'),
(2, 'Admin'),
(3, 'ROLE_USER'),
(4, 'USER'),
(5, 'USER'),
(6, 'ROLE_USER');

-- --------------------------------------------------------

--
-- Table structure for table `savings_account`
--

CREATE TABLE `savings_account` (
  `id` bigint(20) NOT NULL,
  `account_balance` decimal(19,2) DEFAULT NULL,
  `account_number` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `savings_account`
--

INSERT INTO `savings_account` (`id`, `account_balance`, `account_number`) VALUES
(1, '57.73', 123),
(2, '0.00', 777),
(19, '2000.00', 222),
(23, '5400.00', 3),
(27, '2500.00', 552),
(33, '2500.00', 552),
(39, '2500.00', 5521),
(43, '10500.00', 989),
(47, '5640.00', 586),
(71, '1468.00', 1000223),
(86, '500.00', 158897);

-- --------------------------------------------------------

--
-- Table structure for table `savings_transaction`
--

CREATE TABLE `savings_transaction` (
  `id` bigint(20) NOT NULL,
  `amount` double NOT NULL,
  `available_balance` decimal(19,2) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `savings_account_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `savings_transaction`
--

INSERT INTO `savings_transaction` (`id`, `amount`, `available_balance`, `date`, `description`, `status`, `type`, `savings_account_id`) VALUES
(7, 4520, '45957.00', '2022-03-12 19:40:00', 'Between account transfer from Savings to Primary', 'Finished', 'Transfer', 1),
(9, 8, '45949.00', '2022-03-13 04:37:31', 'Withdraw from savings Account', 'Finished', 'Account', 1),
(10, 71, '45878.00', '2022-03-13 04:38:21', 'Between account transfer from Savings to Primary', 'Finished', 'Transfer', 1),
(13, 10000000, '-9954122.00', '2022-03-13 05:22:12', 'Between account transfer from Savings to Primary', 'Finished', 'Transfer', 1),
(51, 9954122, '0.00', '2022-04-11 02:06:20', 'Deposit to savings Account', 'Finished', 'Account', 1),
(52, 5, '-5.00', '2022-04-11 02:16:20', 'Withdraw from savings Account', 'Finished', 'Account', 1),
(60, 12, '-15.27', '2022-04-11 05:37:09', 'Transfer to recipient Sahan', 'Finished', 'Transfer', 1),
(66, 65, '400.73', '2022-04-11 07:46:26', 'Withdraw from savings Account', 'Finished', 'Account', 1),
(67, 200, '200.73', '2022-04-11 07:46:52', 'Between account transfer from Savings to Primary', 'Finished', 'Transfer', 1),
(74, 500, '700.73', '2022-04-11 21:45:30', 'Deposit to savings Account', 'Finished', 'Account', 1),
(75, 40, '660.73', '2022-04-11 21:47:08', 'Between account transfer from Savings to Primary', 'Finished', 'Transfer', 1),
(76, 700, '-39.27', '2022-04-11 21:47:31', 'Withdraw from savings Account', 'Finished', 'Account', 1),
(80, 5, '5.73', '2022-04-19 15:16:34', 'Withdraw from savings Account', 'Finished', 'Account', 1),
(81, 10, '15.73', '2022-04-19 15:16:41', 'Deposit to savings Account', 'Finished', 'Account', 1);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `nic` varchar(15) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `primary_account_id` bigint(20) DEFAULT NULL,
  `savings_account_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `email`, `nic`, `enabled`, `first_name`, `last_name`, `password`, `phone`, `username`, `primary_account_id`, `savings_account_id`) VALUES
(1, 'bodaragamadb.office@gmail.com', '1111111111', b'1', 'Dinuka Bimsara', 'Bodaragama', '$2a$10$s3WZpdg3jTTDWuXvfW5dvO0loovVQ58g8fUMAf09nqlw022WVHn9a', '0772922757', 'BimsaraB', 1, 1),
(2, 'admin@gmail.com', '222222222', b'1', 'Admin', '1', '$2a$10$s3WZpdg3jTTDWuXvfW5dvO0loovVQ58g8fUMAf09nqlw022WVHn9a', '0779876543', 'Admin1', 2, 2),
(3, 'abc@gmail.com', '333333333', b'1', 'Sakuni', 'Leader', '$2a$10$s3WZpdg3jTTDWuXvfW5dvO0loovVQ58g8fUMAf09nqlw022WVHn9a', '0711111111', 'Hirikitha', NULL, NULL),
(44, 'harshanib@gmail.com', '444444444', b'1', 'Harshani', 'Bandara', '$2a$12$LtbYtEzOBdnsiD/E9Wtj2O/Oir/o9nS91sz2SV.Oab/937M.yZl7K', '0772222222', 'HarshaniBa', 42, 43),
(48, 'sakunib@gmail.com', '555555555', b'1', 'Sakuni', 'Bandara', '$2a$12$LtbYtEzOBdnsiD/E9Wtj2O/Oir/o9nS91sz2SV.Oab/937M.yZl7K', '0777777777', 'SakuniB', 46, 47),
(72, 'sc@gmail.com', NULL, b'1', 'Sahan', 'Caldera', '$2a$12$LtbYtEzOBdnsiD/E9Wtj2O/Oir/o9nS91sz2SV.Oab/937M.yZl7K', '0770000000', 'sahanC', 70, 71),
(87, 'bodaragamaddd.office@gmail.com', NULL, b'1', 'BimsaraBBB', 'Bodaragama', '$2a$12$DWCryEwHwbTYCegib92tk.FLVtOUsarICOr6Md6LfeJjxGxMFneni', '0772922757', 'Bimsara2', 85, 86);

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

CREATE TABLE `user_role` (
  `user_role_id` bigint(20) NOT NULL,
  `role_id` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role` (`user_role_id`, `role_id`, `user_id`) VALUES
(1, 1, 1),
(2, 2, 2),
(45, 3, 44),
(49, 4, 48),
(73, 5, 72),
(88, 6, 87);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `appointment`
--
ALTER TABLE `appointment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKa8m1smlfsc8kkjn2t6wpdmysk` (`user_id`);

--
-- Indexes for table `primary_account`
--
ALTER TABLE `primary_account`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `primary_transaction`
--
ALTER TABLE `primary_transaction`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK643wtfdx6y0e093wlc09csehn` (`primary_account_id`);

--
-- Indexes for table `recipient`
--
ALTER TABLE `recipient`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK3041ks22uyyuuw441k5671ah9` (`user_id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`role_id`);

--
-- Indexes for table `savings_account`
--
ALTER TABLE `savings_account`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `savings_transaction`
--
ALTER TABLE `savings_transaction`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK4bt1l2090882974glyn79q2s9` (`savings_account_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`) USING HASH,
  ADD KEY `FKbj0uoj9i40dory8w4t5ojyb9n` (`primary_account_id`),
  ADD KEY `FKihums7d3g5cv9ehminfs1539e` (`savings_account_id`);

--
-- Indexes for table `user_role`
--
ALTER TABLE `user_role`
  ADD PRIMARY KEY (`user_role_id`),
  ADD KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  ADD KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
