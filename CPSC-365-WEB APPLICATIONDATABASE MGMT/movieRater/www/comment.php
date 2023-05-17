<?php
//Begin Required Block
/**
 * @var $pdo
 * @var $includePath
 */
require('config.php');
require_once($includePath . 'dbconfig.php');
session_start();
//End Required Block

var_dump($_POST);
$comments = htmlentities($_POST['comment'], ENT_QUOTES);
$sql = 'INSERT INTO comments(UserID, MovieID, Comment) VALUES (:userID, :movieID, :comment)';
$stmt = $pdo->prepare($sql);
$stmt->bindValue(':userID', $_POST['userID']);
$stmt->bindValue(':movieID', $_POST['movieID']);
$stmt->bindValue(':comment', $comments);
$stmt->execute();
header('Location: MoviePage.php?moviename=' . $_POST['movieName']);