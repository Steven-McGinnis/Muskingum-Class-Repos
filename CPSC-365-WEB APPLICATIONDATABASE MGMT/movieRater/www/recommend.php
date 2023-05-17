<?php
//This Page is a Mess but im on a time Crunch.
/**
 * @var $pdo
 * @var $includePath
 */
session_start();
$userName = $_SESSION['username'];
$title = "Recommend - Movierater";
require('config.php');
require_once($includePath . 'header.php'); ?>

<div id="wrapper">
    <body>
    <table>
        <tr>
            <?php
            echo "<h1>Movies You Should Try</h1>";
            //Get the Latest 10 Movies and Display them in a table with links to the movie page.
            $sql = "SELECT m.Name, m.Thumb, COUNT(r.MovieID) AS RatingCount
                        FROM Movies m
                        INNER JOIN Rating r ON m.MovieID = r.MovieID
                        INNER JOIN User u ON r.UserID = u.UserID
                        INNER JOIN User u2 ON u2.UserName = :username
                        WHERE u.UserID IN (SELECT UserID FROM User WHERE u2.UserID = u.UserID)
                        GROUP BY m.MovieID
                        ORDER BY RatingCount DESC
                        LIMIT 10";

            $stmt = $pdo->prepare($sql);
            $stmt->bindParam(':username', $_SESSION['username']);
            $stmt->execute();
            $results = $stmt->fetchAll();

            //Variable For adjusting Row Reviews.
            $rowCount = 0;
            foreach ($results as $row) {
                if ($rowCount < 5) {
                    echo "<td>";
                    $filename = $row['Thumb'];
                    if ($filename == NULL) {
                        echo "<a href='MoviePage.php?moviename={$row['Name']}'><img src ='SiteImages/comingsoon.jpg'/></a>";
                    }
                    echo "<a href='MoviePage.php?moviename={$row['Name']}'><img href='MoviePage.php?moviename={$row['Name']}' src ='$filename'/></a><br>";
                    echo "<a href='MoviePage.php?moviename={$row['Name']}'  value='{$row['Name']}'>" . $row['Name'] . '</a></br>';
                    echo "</td>";
                    $rowCount++;
                } else {
                    //This is a table row break for the if to make a new line.
                    echo "</tr>";
                    echo "<tr>";
                    echo "<td>";
                    $filename = $row['Thumb'];
                    if ($filename == NULL) {
                        echo "<a href='MoviePage.php?moviename={$row['Name']}'><img src ='SiteImages/comingsoon.jpg'/></a>";
                    }
                    echo "<a href='MoviePage.php?moviename={$row['Name']}'><img href='MoviePage.php?moviename={$row['Name']}' src ='$filename'/></a><br>";
                    echo "<a href='MoviePage.php?moviename={$row['Name']}'  value='{$row['Name']}'>" . $row['Name'] . '</a></br>';
                    echo "</td>";
                    $rowCount = 0;
                }
            }
            echo "</tr>"; ?>
        </tr>
    </table>
