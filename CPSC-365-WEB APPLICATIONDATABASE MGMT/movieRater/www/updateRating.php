<?php
//Begin Required Block
/**
 * @var $pdo
 * @var $includePath
 */
require('config.php');
require_once($includePath . 'dbconfig.php');
session_start();
var_dump($_POST);
//End Required Block


$sql = ('SELECT UserID FROM User WHERE UserName= :UserName');
$stmt = $pdo->prepare($sql);
$stmt->bindParam(':UserName', $_SESSION['username']);
$stmt->execute();
$row = $stmt->fetch(PDO::FETCH_ASSOC);
$userID = $row['UserID'];

$sql = 'SELECT * FROM movies WHERE Name= :Name';
$stmt = $pdo->prepare($sql);
$stmt->bindParam(':Name', $_POST['movieName']);
$stmt->execute();
$row = $stmt->fetch(PDO::FETCH_ASSOC);
$movieID = $row['MovieID'];

$sql = 'SELECT * FROM rating WHERE UserID= :UserID AND MovieID=:MovieID';
$stmt = $pdo->prepare($sql);
$stmt->bindParam(':UserID', $userID);
$stmt->bindParam(':MovieID', $movieID);
$stmt->execute();
$row = $stmt->fetch(PDO::FETCH_ASSOC);


//If user has already rated the movie Update The Rating
if ($row) {
    $sql = ('UPDATE rating SET rating=:rating WHERE UserID=:UserID AND MovieID=:MovieID');
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':rating', $_POST['rating']);
    $stmt->bindParam(':UserID', $userID);
    $stmt->bindParam(':MovieID', $movieID);
    $stmt->execute();
    header('Location: MoviePage.php?moviename=' . $_POST['movieName']);
} //Insert Into the Database a Row With Rating
else {
    $sql = ('INSERT INTO rating(rating, UserID, MovieID) VALUES (:rating, :UserID, :MovieID)');
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':rating', $_POST['rating']);
    $stmt->bindParam(':UserID', $userID);
    $stmt->bindParam(':MovieID', $movieID);
    $stmt->execute();
    header('Location: MoviePage.php?moviename=' . $_POST['movieName']);
}





