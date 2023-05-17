<?php
/**
 * @var $pdo
 * @var $includePath
 */
require('config.php');
require_once($includePath . 'dbconfig.php');

//Get the Username and Password and Strip SQL Injection
$userName = htmlentities($_POST['uname'], ENT_QUOTES);
$psw = htmlentities($_POST['psw'], ENT_QUOTES);

//Create the Select statement to search the database.
$sql = 'SELECT * FROM User WHERE UserName= :UserName';
$stmt = $pdo->prepare($sql);

//Bind the uname to the UserName select statement.
$stmt->bindParam(':UserName', $_POST['uname']);
$stmt->execute();

//Fetch the Row to pull data out of.
$row = $stmt->fetch(PDO::FETCH_ASSOC);

//Pull out the row password and decrypt it and compare to plain text version.
if ($row) {
    $Uname = $row['UserName'];
    $date = date('Y-m-d h:i:s', time());

    //Check for Time Ban
    if ($row['timeBan'] == 1) {
        $lastLogin = $row['lastLogin'];
        $new_timeStamp = explode(" ", $lastLogin);
        $old_timeStamp = explode(" ", $date);
        if ($new_timeStamp[0] == $old_timeStamp[0]) {
            $new_Time = explode(":", $new_timeStamp[1]);
            $old_Time = explode(":", $old_timeStamp[1]);
            if ($new_Time[0] == $old_Time[0] && $new_Time[1] == $old_Time[1]) {
                header('Location: LogIn.php?error=3');
                exit();
            } else {
                $tb = 0;
                $sql = 'UPDATE user SET  timeBan=:timeban  WHERE UserName=:UName';
                $stmt = $pdo->prepare($sql);
                $stmt->bindValue(':timeban', $tb);
                $stmt->bindValue(':UName', $Uname);
                $stmt->execute();
            }
        } else {
            $tb = 0;
            $sql = 'UPDATE user SET  timeBan=:timeban  WHERE UserName=:UName';
            $stmt = $pdo->prepare($sql);
            $stmt->bindValue(':timeban', $tb);
            $stmt->bindValue(':UName', $Uname);
            $stmt->execute();
        }
    } else {
        $tb = 0;
        $sql = 'UPDATE user SET  timeBan=:timeban  WHERE UserName=:UName';
        $stmt = $pdo->prepare($sql);
        $stmt->bindValue(':timeban', $tb);
        $stmt->bindValue(':UName', $Uname);
        $stmt->execute();
    }

    if (password_verify($_POST['psw'], $row['Password'])) {
        //Sets The session
        session_start();
        session_regenerate_id(true);

        //Updates Last Login TimeStamp
        $sql = 'UPDATE user SET lastLogin=:datetime  WHERE UserName=:UName';
        $stmt = $pdo->prepare($sql);
        $stmt->bindValue(':datetime', $date);
        $stmt->bindValue(':UName', $Uname);
        $stmt->execute();

        //Check if it's an admin logging in.
        if ($row['Administrator'] == 1) {
            $_SESSION['admin'] = $row['Administrator'];
        }
        $_SESSION['username'] = $row['UserName'];
        $_SESSION['userID'] = $row['UserID'];
        header('Location: index.php');
    } //If Passwords don't match send it back with error.
    else {
        $tb = 1;
        $sql = 'UPDATE user SET lastLogin=:datetime WHERE UserName=:UName';
        $stmt = $pdo->prepare($sql);
        $stmt->bindValue(':datetime', $date);
        $stmt->bindValue(':UName', $Uname);
        $stmt->execute();

        $sql = 'UPDATE user SET  timeBan=:timeban  WHERE UserName=:UName';
        $stmt = $pdo->prepare($sql);
        $stmt->bindValue(':timeban', $tb);
        $stmt->bindValue(':UName', $Uname);
        $stmt->execute();

        header('Location: LogIn.php?error=1');
    }
} //if There is no user then send it back with error.
else {
    header('Location: LogIn.php?error=2');
}
?>