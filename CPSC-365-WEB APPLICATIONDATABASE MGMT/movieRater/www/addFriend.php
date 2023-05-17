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

$sql = 'SELECT * FROM friends WHERE UserID=:userID AND FriendID=:friendID';
$stmt = $pdo->prepare($sql);
$stmt->bindValue(':userID', $user);
$stmt->bindValue(':friendID', $friendID);
$stmt->execute();
$row = $stmt->fetch(PDO::FETCH_ASSOC);

$sql2 = 'SELECT * FROM friends WHERE UserID=:userID AND FriendID=:friendID';
$stmt2 = $pdo->prepare($sql2);
$stmt2->bindValue(':userID', $friendID);
$stmt2->bindValue(':friendID', $user);
$stmt2->execute();
$row2 = $stmt2->fetch(PDO::FETCH_ASSOC);
var_dump($row);
//If Users Already Friends or Request is Still Pending Or Friendship Has been Denied
if($row["Status"] == false){
    if($row2['Status'] == 1){
        $status = 2;
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
        echo '{"status":"Friend Added"}';
    }else {
        $status = 1;
        $sql = 'INSERT INTO friends(UserID, FriendID, Status) VALUES (:userID, :friendID, :status)';
        $stmt = $pdo->prepare($sql);
        $stmt->bindValue(':userID', $user);
        $stmt->bindValue(':friendID', $friendID);
        $stmt->bindValue(':status', $status);
        $stmt->execute();
        echo '{"status":"Request Pending"}';
    }
}
if($row['Status'] == 1 or $row['Status'] == 2 or $row['Status'] == 0){
    echo '{"status":"Cannot Do that with this user"}';
}
elseif($row2['Status'] == 1){
    $status = 2;
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
    echo '{"status":"Friend Added"}';
}else{
    $status = 1;
    $sql = 'INSERT INTO friends(UserID, FriendID, Status) VALUES (:userID, :friendID, :status)';
    $stmt = $pdo->prepare($sql);
    $stmt->bindValue(':userID', $user);
    $stmt->bindValue(':friendID', $friendID);
    $stmt->bindValue(':status', $status);
    $stmt->execute();
    echo '{"status":"Request Pending"}';
}






