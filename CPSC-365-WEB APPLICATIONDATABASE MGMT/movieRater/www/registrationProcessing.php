<?php
/**
 * @var $pdo
 * @var $includePath
 */
require('config.php');
require_once($includePath . 'dbconfig.php');


//Checks if the two passwords match or not.
if ($_POST['psw'] != $_POST['psw1']) {
    header('Location: registration.php?error=1');
    exit();
}

//Pulls Username from Server to see if there is a user already with that name.
$sql = 'SELECT * FROM User WHERE UserName= :UserName';
$stmt = $pdo->prepare($sql);

//Bind the posted username to the Username its selecting.
$stmt->bindParam(':UserName', $_POST['username']);
$stmt->execute();

//Fetch the row from the executed statement.
$row = $stmt->fetch(PDO::FETCH_ASSOC);

//Ask if there is a row there or not.
if ($row == true) {
    header("Location:  registration.php?error=2");
}

//Get the User PassWord and Create Encryption
$psw = htmlentities($_POST['psw'], ENT_QUOTES);
$encryptedPass = password_hash($psw, PASSWORD_BCRYPT);

//Prepare SQL Statement
$sql = 'INSERT INTO User(UserName, Email, Password) VALUES (:userName, :email, :passWord)';
$stmt = $pdo->prepare($sql);

//Bind Preperation
$stmt->bindParam(':userName', $UserName);
$stmt->bindParam(':email', $Email);
$stmt->bindParam(':passWord', $Password);

//Assign Prep Variables
$UserName = htmlentities($_POST['username'], ENT_QUOTES);
$Password = $encryptedPass;
$Email = htmlentities($_POST['email'], ENT_QUOTES);
$stmt->execute();

header('Location: LogIn.php');
exit ();
//Finished Registration
?>