-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2017-06-07 08:00:10
-- 服务器版本： 10.1.16-MariaDB
-- PHP Version: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `wechat`
--

DELIMITER $$
--
-- 函数
--
CREATE DEFINER=`root`@`localhost` FUNCTION `userFound` (`userid` CHAR(6)) RETURNS INT(11) begin
	declare result int;	
 	select count(*) into result
 	from user
 	where user.userid = userid;	
 	return result;
end$$

DELIMITER ;

-- --------------------------------------------------------

--
-- 表的结构 `friends`
--

CREATE TABLE `friends` (
  `id` int(11) NOT NULL,
  `userid` char(6) NOT NULL COMMENT '用户帐号',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `freid` char(6) NOT NULL COMMENT '好友帐号',
  `frename` varchar(20) NOT NULL COMMENT '好友名'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `friends`
--

INSERT INTO `friends` (`id`, `userid`, `username`, `freid`, `frename`) VALUES
(1, '000001', 'hjh', '000002', 'zzz'),
(2, '000002', 'zzz', '000003', 'la'),
(3, '000003', 'la', '000001', 'hjh'),
(4, '000001', 'hjh', '000004', 'ad'),
(5, '000002', 'zzz', '000004', 'ad');

-- --------------------------------------------------------

--
-- 表的结构 `moment`
--

CREATE TABLE `moment` (
  `id` int(11) NOT NULL,
  `username` varchar(20) NOT NULL COMMENT '动态发送者',
  `words` text COMMENT '动态包含的文字',
  `image` varchar(100) DEFAULT NULL COMMENT '动态包含的图片的路径',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `moment`
--

INSERT INTO `moment` (`id`, `username`, `words`, `image`, `time`) VALUES
(1, 'hjh', 'Hello World.', '/wechat/Public/Moments/helloworld.jpg', '2017-06-05 05:41:41'),
(2, 'hjh', '111', NULL, '2017-06-05 05:41:56'),
(3, 'hjh', 'tttt', NULL, '2017-06-05 05:42:09'),
(4, 'zzz', '~~~~', NULL, '2017-06-05 05:46:21'),
(5, 'la', '测试', NULL, '2017-06-05 05:46:39'),
(6, 'ttt', '123', NULL, '2017-06-07 05:45:08'),
(7, 'hjh', 'begin', NULL, '2017-06-07 05:57:47');

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `userid` char(6) NOT NULL COMMENT '用户帐号',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` char(32) NOT NULL COMMENT '密码',
  `Q1` text NOT NULL COMMENT '密保问题一',
  `A1` text NOT NULL COMMENT '密保问题一答案',
  `Q2` text NOT NULL COMMENT '密保问题二',
  `A2` text NOT NULL COMMENT '密保问题二答案',
  `pic` varchar(100) DEFAULT '' COMMENT '头像路径'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`id`, `userid`, `username`, `password`, `Q1`, `A1`, `Q2`, `A2`, `pic`) VALUES
(1, '000001', 'hjh', '202cb962ac59075b964b07152d234b70', '', '', '', '', '/wechat/Public/Users/hjh.jpg'),
(2, '000002', 'zzz', '202cb962ac59075b964b07152d234b70', '', '', '', '', '/wechat/Public/Users/zzz.jpg'),
(3, '000003', 'la', '202cb962ac59075b964b07152d234b70', '', '', '', '', '/wechat/Public/Users/default.png'),
(4, '000004', 'ad', '202cb962ac59075b964b07152d234b70', '', '', '', '', '/wechat/Public/Users/default.png'),
(5, '000005', 'ttt1', '202cb962ac59075b964b07152d234b70', 'hhh', 'hhh', 'hhh', 'hhh', '/wechat/Public/Users/default.png'),
(6, '000006', 'ttt', '202cb962ac59075b964b07152d234b70', 'hhh', 'hhh', 'hhh', 'hhh', '/wechat/Public/Users/default.png');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `friends`
--
ALTER TABLE `friends`
  ADD PRIMARY KEY (`id`),
  ADD KEY `userid` (`userid`),
  ADD KEY `username` (`username`),
  ADD KEY `freid` (`freid`),
  ADD KEY `frename` (`frename`);

--
-- Indexes for table `moment`
--
ALTER TABLE `moment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `username` (`username`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `userid` (`userid`),
  ADD UNIQUE KEY `username` (`username`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `friends`
--
ALTER TABLE `friends`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- 使用表AUTO_INCREMENT `moment`
--
ALTER TABLE `moment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- 限制导出的表
--

--
-- 限制表 `friends`
--
ALTER TABLE `friends`
  ADD CONSTRAINT `friends_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `user` (`userid`),
  ADD CONSTRAINT `friends_ibfk_2` FOREIGN KEY (`username`) REFERENCES `user` (`username`),
  ADD CONSTRAINT `friends_ibfk_3` FOREIGN KEY (`freid`) REFERENCES `user` (`userid`),
  ADD CONSTRAINT `friends_ibfk_4` FOREIGN KEY (`frename`) REFERENCES `user` (`username`);

--
-- 限制表 `moment`
--
ALTER TABLE `moment`
  ADD CONSTRAINT `moment_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user` (`username`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
