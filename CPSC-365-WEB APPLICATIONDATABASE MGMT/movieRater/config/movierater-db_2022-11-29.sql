-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Nov 29, 2022 at 09:49 PM
-- Server version: 8.0.29
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `movierater`
--

-- --------------------------------------------------------

--
-- Table structure for table `actors`
--

CREATE TABLE `actors` (
  `actorID` int NOT NULL,
  `Name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `actors`
--

INSERT INTO `actors` (`actorID`, `Name`) VALUES
(1, 'Tim Robbins'),
(2, 'Morgan Freeman'),
(3, 'Bob Gunton'),
(4, 'Marlon Brando'),
(5, 'Al Pacino'),
(6, 'James Caan'),
(7, 'Christian Bale'),
(8, 'Heath Ledger'),
(9, 'Aaron Eckhart'),
(10, 'Robert De Niro'),
(11, 'Robert Duvall'),
(12, 'Henry Fonda'),
(13, 'Lee J. Cobb'),
(14, 'Martin Balsam'),
(15, 'Liam Neeson'),
(16, 'Ralph Fiennes'),
(17, 'Ben Kingsley'),
(18, 'Elijah Wood'),
(19, 'Viggo Mortensen'),
(20, 'Ian McKellen'),
(21, 'John Travolta'),
(22, 'Uma Thurman'),
(23, 'Samuel L. Jackson'),
(24, 'Orlando Bloom'),
(25, 'Clint Eastwood'),
(26, 'Eli Wallach'),
(27, 'Lee Van Cleef'),
(28, 'Tom Hanks'),
(29, 'Robin Wright'),
(30, 'Gary Sinise'),
(31, 'Brad Pitt'),
(32, 'Edward Norton'),
(33, 'Meat Loaf');

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE `comments` (
  `Timestamp` datetime NOT NULL DEFAULT (now()),
  `CommentID` int NOT NULL,
  `UserID` int DEFAULT NULL,
  `MovieID` int DEFAULT NULL,
  `Comment` mediumtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`Timestamp`, `CommentID`, `UserID`, `MovieID`, `Comment`) VALUES
('2022-11-27 18:47:06', 1, 1, 12, 'Superb, and truly one of the greatest movies of all time.\r\n\r\nIt starts with the screenplay. Adapted from, and very faithful to, an excellent book. The book by Chuck Palahniuk was perfect for a movie: vivid, powerful, challenging, original, unpredictable. Considering how perfectly formed the book already was, the screenplay would have been a doddle.\r\n\r\nSome very interesting themes are explored - consumerism, class warfare, multiple-personality disorder, male bonding, terrorism and anarchy - without being judgemental.\r\n\r\nDirection is spot-on. Perfect cinematography, pacing and editing. The twists and nuances of the book are captured perfectly.\r\n\r\nEdward Norton and Brad Pitt are perfectly cast as the two lead characters, and deliver in spades. Helena Bonham Carter is a strange selection to take on the role of Marla, as she tends to act in Shakespearean dramas and other period pieces. However, despite this, her performance is very convincing.'),
('2022-11-27 19:34:30', 2, 1, 13, 'Peter Jackson truly outdid himself when creating the Lord of the Rings: The Fellowship of the Ring and he fails to disappoint us in the 2nd part of the Trilogy. The Two Towers shows us that he is not a one-hit wonder, like so many directors are. I actually think that The Two Towers reaches the same level as the Fellowship of the Ring, and sometimes even surpasses it.\r\n\r\nThis film is the biggest film in the trilogy. What do I mean by that? Well this film has so many things going like the amazing Battle of Helms Deep. Frodo and Sam journey to Mount Doom, to destroy the Ring. But the one who&#039;s leading them through the way is Gollum, he looks so creepy and realistic, that he doesn&#039;t feel disconnected from us. A powerful performance by Andy Serkis as Gollum, he should of been nominated for an Oscar for Best Supporting Actor.\r\n\r\nThe Best part of the film, is quite easily and everyone knows it the ending. The ending of the battle of Helm&#039;s Deep is quite breathtaking, and as Gandalf the White comes in the distance with another army to defeat the Orcs. When Treebeard and his army of Entz tear down Isengard, the destruction and the battle is so immense in size, that you truly have to see to believe.\r\n\r\nIn size and scale, Peter Jackson has truly redefined the word &quot;epic&quot; and he also pays attention to the small things that truly elevate this movie from great to amazing. I definitely recommend this film to everyone, but you really should watch the first movie to truly understand what&#039;s going on.\r\n\r\n10/10'),
('2022-11-27 21:40:29', 3, 1, 13, '2nd comment'),
('2022-11-29 14:20:37', 4, 2, 13, 'Not as good as Fellowship.');

-- --------------------------------------------------------

--
-- Table structure for table `filmcast`
--

CREATE TABLE `filmcast` (
  `castID` int NOT NULL,
  `MovieID` int NOT NULL,
  `actorID` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `filmcast`
--

INSERT INTO `filmcast` (`castID`, `MovieID`, `actorID`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 3),
(4, 2, 4),
(5, 2, 5),
(6, 2, 6),
(7, 3, 7),
(8, 3, 8),
(9, 3, 9),
(10, 4, 5),
(11, 4, 10),
(12, 4, 11),
(13, 5, 12),
(14, 5, 13),
(15, 5, 14),
(16, 6, 15),
(17, 6, 16),
(18, 6, 17),
(19, 7, 18),
(20, 7, 19),
(21, 7, 20),
(22, 8, 21),
(23, 8, 22),
(24, 8, 23),
(25, 9, 18),
(26, 9, 20),
(27, 9, 24),
(28, 10, 25),
(29, 10, 26),
(30, 10, 27),
(31, 11, 28),
(32, 11, 29),
(33, 11, 30),
(34, 12, 31),
(35, 12, 32),
(36, 12, 33),
(37, 13, 18),
(38, 13, 20),
(39, 13, 19);

-- --------------------------------------------------------

--
-- Table structure for table `friends`
--

CREATE TABLE `friends` (
  `frID` int NOT NULL,
  `UserID` int NOT NULL,
  `FriendID` int NOT NULL,
  `Status` int NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `friends`
--

INSERT INTO `friends` (`frID`, `UserID`, `FriendID`, `Status`) VALUES
(16, 2, 1, 2),
(17, 4, 1, 0),
(19, 1, 2, 2),
(20, 1, 4, 0);

-- --------------------------------------------------------

--
-- Table structure for table `movies`
--

CREATE TABLE `movies` (
  `MovieID` int NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Date` int NOT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `Image` varchar(255) DEFAULT NULL,
  `Thumb` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `movies`
--

INSERT INTO `movies` (`MovieID`, `Name`, `Date`, `Description`, `Image`, `Thumb`) VALUES
(1, 'The Shawshank Redemption', 1994, 'Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.', 'posters/TheShawshankRedemption1994.jpg', 'thumbnails/TheShawshankRedemption1994.jpg'),
(2, 'The Godfather', 1972, 'The aging patriarch of an organized crime dynasty in postwar New York City transfers control of his clandestine empire to his reluctant youngest son.', 'posters/TheGodfather1972.jpg', 'thumbnails/TheGodfather1972.jpg'),
(3, 'The Dark Knight', 2008, 'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.', 'posters/TheDarkKnight2008.jpg', 'thumbnails/TheDarkKnight2008.jpg'),
(4, 'The Godfather Part II', 1974, 'The early life and career of Vito Corleone in 1920s New York City is portrayed, while his son, Michael, expands and tightens his grip on the family crime syndicate.', 'posters/TheGodfatherPartII1974.jpg', 'thumbnails/TheGodfatherPartII1974.jpg'),
(5, '12 Angry Men', 1957, 'The jury in a New York City murder trial is frustrated by a single member whose skeptical caution forces them to more carefully consider the evidence before jumping to a hasty verdict.', 'posters/12AngryMen1957.jpg', 'thumbnails/12AngryMen1957.jpg'),
(6, 'Schindler\'s List', 1993, 'In German-occupied Poland during World War II, industrialist Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.', 'posters/SchindlersList1993.jpg', 'thumbnails/SchindlersList1993.jpg'),
(7, 'The Lord of the Rings: The Return of the King', 2003, 'Gandalf and Aragorn lead the World of Men against Sauron\'s army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.', 'posters/TheLordoftheRingsTheReturnoftheKing2003.jpg', 'thumbnails/TheLordoftheRingsTheReturnoftheKing2003.jpg'),
(8, 'Pulp Fiction', 1994, 'The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.', 'posters/PulpFiction1994.jpg', 'thumbnails/PulpFiction1994.jpg'),
(9, 'The Lord of the Rings: The Fellowship of the Ring', 2001, 'A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.', 'posters/TheLordoftheRingsTheFellowshipoftheRing2001.jpg', 'thumbnails/TheLordoftheRingsTheFellowshipoftheRing2001.jpg'),
(10, 'The Good, the Bad and the Ugly', 1966, 'A bounty hunting scam joins two men in an uneasy alliance against a third in a race to find a fortune in gold buried in a remote cemetery.', 'posters/TheGoodtheBadandtheUgly1966.jpg', 'thumbnails/TheGoodtheBadandtheUgly1966.jpg'),
(11, 'Forrest Gump', 1994, 'The presidencies of Kennedy and Johnson, the Vietnam War, the Watergate scandal and other historical events unfold from the perspective of an Alabama man with an IQ of 75, whose only desire is to be reunited with his childhood sweetheart.', 'posters/ForrestGump1994.jpg', 'thumbnails/ForrestGump1994.jpg'),
(12, 'Fight Club', 1999, 'An insomniac office worker and a devil-may-care soap maker form an underground fight club that evolves into much more.', 'posters/FightClub1999.jpg', 'thumbnails/FightClub1999.jpg'),
(13, 'The Lord of the Rings: The Two Towers', 2002, 'While Frodo and Sam edge closer to Mordor with the help of the shifty Gollum, the divided fellowship makes a stand against Sauron\'s new ally, Saruman, and his hordes of Isengard.', 'posters/TheLordoftheRingsTheTwoTowers2002.jpg', 'thumbnails/TheLordoftheRingsTheTwoTowers2002.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `rating`
--

CREATE TABLE `rating` (
  `reviewID` int NOT NULL,
  `rating` int DEFAULT NULL,
  `UserID` int NOT NULL,
  `MovieID` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `rating`
--

INSERT INTO `rating` (`reviewID`, `rating`, `UserID`, `MovieID`) VALUES
(1, 8, 1, 12),
(2, 8, 1, 13);

-- --------------------------------------------------------

--
-- Table structure for table `taggedmovies`
--

CREATE TABLE `taggedmovies` (
  `ID` int NOT NULL,
  `tagID` int NOT NULL,
  `MovieID` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `taggedmovies`
--

INSERT INTO `taggedmovies` (`ID`, `tagID`, `MovieID`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 3, 2),
(4, 1, 3),
(5, 3, 3),
(6, 5, 3),
(7, 1, 4),
(8, 3, 4),
(9, 1, 5),
(10, 3, 5),
(11, 1, 6),
(12, 10, 6),
(13, 21, 6),
(14, 1, 7),
(15, 5, 7),
(16, 6, 7),
(17, 1, 8),
(18, 3, 8),
(19, 1, 9),
(20, 5, 9),
(21, 6, 9),
(22, 6, 10),
(23, 26, 10),
(24, 1, 11),
(25, 15, 11),
(26, 1, 12),
(27, 1, 13),
(28, 5, 13),
(29, 6, 13);

-- --------------------------------------------------------

--
-- Table structure for table `tags`
--

CREATE TABLE `tags` (
  `tagID` int NOT NULL,
  `tagName` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `tags`
--

INSERT INTO `tags` (`tagID`, `tagName`) VALUES
(1, 'Drama'),
(2, 'Comedy'),
(3, 'Crime'),
(4, 'Family'),
(5, 'Action'),
(6, 'Adventure'),
(7, 'Animation'),
(8, 'Horror'),
(9, 'Music'),
(10, 'Biography'),
(11, 'Documentary'),
(12, 'Sport'),
(13, 'News'),
(14, 'Film_Noir'),
(15, 'Romance'),
(16, 'Short'),
(17, 'Thriller'),
(18, 'Mystery'),
(19, 'Fantasy'),
(20, 'Sci-Fi'),
(21, 'History'),
(22, 'Reality-TV'),
(23, 'War'),
(24, 'Talk-Show'),
(25, 'Musical'),
(26, 'Western'),
(27, 'Game-Show'),
(28, 'Adult');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `UserID` int NOT NULL,
  `UserName` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Administrator` tinyint(1) NOT NULL DEFAULT '0',
  `Bio` varchar(255) DEFAULT NULL,
  `lastLogin` datetime NOT NULL DEFAULT (now()),
  `timeBan` tinyint NOT NULL DEFAULT '0',
  `avatar` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`UserID`, `UserName`, `Email`, `Password`, `Administrator`, `Bio`, `lastLogin`, `timeBan`, `avatar`) VALUES
(1, 'admin', 'admin@admin', '$2y$10$lLAoqVlY4XbkS2EC9SIQPOzUug9H8F/8atQp6EbJoMSq61mbr4Cg2', 1, NULL, '2022-11-29 08:05:24', 0, 'avatars/admin.jpg'),
(2, 'user1', 'user1@user', '$2y$10$Tq5Nk6gG0qf8i/ncXxJmjeszFJq.EfY7CgB3/os5zwuCachgQUqqq', 0, NULL, '2022-11-29 08:02:02', 0, NULL),
(3, 'user2', 'user2@user', '$2y$10$ls37HnZk3HlvFXTdsTE3bOAzEo7RTsvGoRyn0NKIvkn7oq7KHwJzW', 0, NULL, '2022-11-29 08:03:31', 0, NULL),
(4, 'user3', 'user3@user', '$2y$10$MQCM17pVOyakNuL5ZkWi6.hdWMln7KezQPl4EmY8O5hr6mXs5tILG', 0, NULL, '2022-11-29 08:04:47', 0, NULL),
(5, 'user5', 'user5@user', '$2y$10$vfFVZaspX1dx/DC9pqqaOOV02W.zAuVTB8GwZZJBXPXGC492f7LFK', 0, NULL, '2022-11-28 02:39:15', 0, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `actors`
--
ALTER TABLE `actors`
  ADD PRIMARY KEY (`actorID`),
  ADD UNIQUE KEY `actorID` (`actorID`);

--
-- Indexes for table `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`CommentID`),
  ADD UNIQUE KEY `CommentID` (`CommentID`),
  ADD KEY `UserID` (`UserID`),
  ADD KEY `MovieID` (`MovieID`);

--
-- Indexes for table `filmcast`
--
ALTER TABLE `filmcast`
  ADD PRIMARY KEY (`castID`),
  ADD UNIQUE KEY `castID` (`castID`),
  ADD KEY `actorID` (`actorID`),
  ADD KEY `MovieID` (`MovieID`);

--
-- Indexes for table `friends`
--
ALTER TABLE `friends`
  ADD PRIMARY KEY (`frID`),
  ADD KEY `UserID` (`UserID`),
  ADD KEY `FriendID` (`FriendID`);

--
-- Indexes for table `movies`
--
ALTER TABLE `movies`
  ADD PRIMARY KEY (`MovieID`),
  ADD UNIQUE KEY `MovieID` (`MovieID`);

--
-- Indexes for table `rating`
--
ALTER TABLE `rating`
  ADD PRIMARY KEY (`reviewID`),
  ADD UNIQUE KEY `reviewID` (`reviewID`),
  ADD KEY `UserID` (`UserID`),
  ADD KEY `MovieID` (`MovieID`);

--
-- Indexes for table `taggedmovies`
--
ALTER TABLE `taggedmovies`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `tagID` (`tagID`),
  ADD KEY `MovieID` (`MovieID`);

--
-- Indexes for table `tags`
--
ALTER TABLE `tags`
  ADD PRIMARY KEY (`tagID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`UserID`),
  ADD UNIQUE KEY `UserName` (`UserName`),
  ADD UNIQUE KEY `Email` (`Email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `actors`
--
ALTER TABLE `actors`
  MODIFY `actorID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT for table `comments`
--
ALTER TABLE `comments`
  MODIFY `CommentID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `filmcast`
--
ALTER TABLE `filmcast`
  MODIFY `castID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT for table `friends`
--
ALTER TABLE `friends`
  MODIFY `frID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `movies`
--
ALTER TABLE `movies`
  MODIFY `MovieID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `rating`
--
ALTER TABLE `rating`
  MODIFY `reviewID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `taggedmovies`
--
ALTER TABLE `taggedmovies`
  MODIFY `ID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `tags`
--
ALTER TABLE `tags`
  MODIFY `tagID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `UserID` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`),
  ADD CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`MovieID`) REFERENCES `movies` (`MovieID`);

--
-- Constraints for table `filmcast`
--
ALTER TABLE `filmcast`
  ADD CONSTRAINT `filmcast_ibfk_1` FOREIGN KEY (`actorID`) REFERENCES `actors` (`actorID`),
  ADD CONSTRAINT `filmcast_ibfk_2` FOREIGN KEY (`MovieID`) REFERENCES `movies` (`MovieID`);

--
-- Constraints for table `friends`
--
ALTER TABLE `friends`
  ADD CONSTRAINT `friends_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`),
  ADD CONSTRAINT `friends_ibfk_2` FOREIGN KEY (`FriendID`) REFERENCES `user` (`UserID`);

--
-- Constraints for table `rating`
--
ALTER TABLE `rating`
  ADD CONSTRAINT `rating_ibfk_1` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`),
  ADD CONSTRAINT `rating_ibfk_2` FOREIGN KEY (`MovieID`) REFERENCES `movies` (`MovieID`);

--
-- Constraints for table `taggedmovies`
--
ALTER TABLE `taggedmovies`
  ADD CONSTRAINT `taggedmovies_ibfk_1` FOREIGN KEY (`tagID`) REFERENCES `tags` (`tagID`),
  ADD CONSTRAINT `taggedmovies_ibfk_2` FOREIGN KEY (`MovieID`) REFERENCES `movies` (`MovieID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
