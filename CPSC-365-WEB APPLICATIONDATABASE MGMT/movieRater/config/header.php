<?php
/**
 * @var $pdo
 * @var $includePath
 * @var $title
 * @var $isMovie
 * @var $movieName
 * @var $userName
 */
require('config.php');
require_once($includePath . 'dbconfig.php');
if (isset($_SESSION)) {
    ;
} else {
    session_start();
}

if (isset($_SESSION['username'])) {
    $userName = $_SESSION['username'];
}
?>
<html>
<head>
    <title><?php echo $title; ?></title>
    <link rel="stylesheet" type="text/css" href="../www/stylesheet.css">

    <div class="topNavigation">
        <!--Login/Logout If Statement Code (Right Side First for some reason)-->
        <div class="topNavigation-right">
            <!-- If you are logged in show logout, otherwise show log in. -->
            <?php if (isset($_SESSION['username'])) { ?>
                <a href="logoutProcessing.php" style="text-decoration:none">Logout</a>
            <?php } else { ?>
                <a href="LogIn.php" style="text-decoration:none">Login</a>
            <?php } ?>
        </div>
        <div class="topLOGO">
            <img src="SiteImages/logo.png" height="100" width="100" class="topNavigation"/>
        </div>
        <!--Nav Bar for left side code each checks if it's the active element-->
        <?php if ($title == "Home - Movierater") { ?>
            <a class=active href="../www/index.php">Home</a>
        <?php } else { ?>
            <a href="../www/index.php">Home</a>
        <?php } ?>


        <?php if (isset($_SESSION['username'])) {
            if ($title == $userName . " - Movierater") { ?>
                <a class=active href="user.php">My Page</a>
            <?php } else { ?>
                <a href="user.php">My Page</a>
            <?php }
        }
        ?>


        <!-- If this is an Administrator -->
        <?php if (isset($_SESSION['admin'])) { ?>
            <?php if ($title == "Administration - Movierater") { ?>
                <a class=active href="Admins.php">Administrator</a>
            <?php } else { ?>
                <a href="Admins.php">Administrator</a>
            <?php } ?>
        <?php } ?>

        <?php if (isset($isMovie)) {
            echo "<a class=active href='MoviePage.php?moviename=$movieName'>$movieName</a>";
        }


        if (isset($_SESSION['username'])) {
            if ($title == "Recommending - Movierater") { ?>
                <a class=active href="recommend.php">Recommend</a>
            <?php } else { ?>
                <a href="recommend.php">Recommend</a>
            <?php }
        }



        ?>

        <div>
            <br>
            <br>
            <form action="search.php" method="POST">
                <input id="search" type="text" name='search' placeholder="Movie Name">
                <input id="submit" type="submit" value="Search">
            </form>
        </div>
        </head>
    </div>




