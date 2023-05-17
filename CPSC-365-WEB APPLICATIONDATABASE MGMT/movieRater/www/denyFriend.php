<?php
/**
 * @var $pdo
 * @var $includePath
 */
require('config.php');
require_once($includePath . 'dbconfig.php');
session_start();
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);
$user = $_SESSION['userID'];
$friendID = $_POST['userID'];

$status = 0;
$sql = 'INSERT INTO friends(UserID, FriendID, Status) VALUES (:userID, :friendID, :status)';
$stmt = $pdo->prepare($sql);
$stmt->bindValue(':userID', $user);
$stmt->bindValue(':friendID', $friendID);
$stmt->bindValue(':status', $status);
$stmt->execute();

$sql = 'UPDATE friends SET Status=:status  WHERE UserID=:UName AND FriendID=:friendID';
$stmt = $pdo->prepare($sql);
$stmt->bindValue(':status', $status);
$stmt->bindValue(':UName', $friendID);
$stmt->bindValue(':friendID', $user);
$stmt->execute();
echo '{"status":"Friend Denied"}';


