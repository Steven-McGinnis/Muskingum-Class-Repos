<?php
//Begin Required Block
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
var_dump($_POST);
//End Required Block

if(isset($_POST['bio'])){
    echo "Did we reach this?";
    $bio = htmlentities($_POST['bio']);
    $sql = 'UPDATE user SET Bio=:bio  WHERE UserName=:user';
    $stmt = $pdo->prepare($sql);
    $stmt->bindValue(':bio', $bio);
    $stmt->bindValue(':user', $_SESSION['username']);
    $stmt->execute();
    header('Location: user.php');
}
else{

}