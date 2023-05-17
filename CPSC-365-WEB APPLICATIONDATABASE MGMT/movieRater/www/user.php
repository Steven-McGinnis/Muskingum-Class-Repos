<?php
//This Page is a Mess but im on a time Crunch.
/**
 * @var $pdo
 * @var $includePath
 */
session_start();
$userName = $_SESSION['username'];
$title = $userName . " - Movierater";
require('config.php');
require_once($includePath . 'header.php'); ?>

<!-- Wrapper for HTML -->
<div id="wrapper">
    <body>
    <?php
    if (isset($_GET['username'])) {
        //TODO If not the user do this otherwise
        $userName = $_GET['username'];
        $sql = 'SELECT * FROM user WHERE UserName= :user';
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':user', $_GET['username']);
        $stmt->execute();
        $row = $stmt->fetch(PDO::FETCH_ASSOC);
        $userID = $row['UserID'];
        if ($row['avatar'] == NULL) {
            echo "<img class='pic' src='SiteImages/noAvatar.jpg'>";
        } else {
            $userImage = $row['avatar'];
            echo "<img class='pic' src='$userImage'>";
        }

        echo "<div style='font-size: 60px; text-decoration: underline;' class='headline' id='mycss'>$userName</div>";

        ?>

        <!-- Shows Bio -->
        <div class="flex-container">
            <div class='bio'>
                <?php
                if ($row['Bio'] == NULL) {
                    echo "This User Has not created a Bio yet.";
                } else {
                    $bio = $row['Bio'];
                    echo " <p>$bio</p> ";
                } ?>
                <br><br><br><br><br><br><br>
            </div>

            <div style="" ;>
                <p><b>Friends List</b></p>
                <!--Show your Friends-->
                <?php
                $sql = 'SELECT friends.UserID, friends.FriendID, user.UserName 
                        FROM friends INNER JOIN user 
                        ON friends.FriendID = user.UserID 
                        WHERE friends.UserID=:userID AND Status=2';
                $stmt = $pdo->prepare($sql);
                $stmt->bindValue(':userID', $userID);
                $stmt->execute();
                $stmt = $stmt->fetchAll(PDO::FETCH_ASSOC);
                echo "<table><tr><th>Freind Name</th></tr>";
                foreach ($stmt as $row) {
                    echo "<tr><td>" . $row['UserName'] . "</td></tr>";
                }
                echo "</table>";
                ?>

            </div>
        </div>
        <table>
            <tr>
                <?php
                echo "<h1>Movies They Have Rated</h1>";
                //Get the Latest 10 Movies and Display them in a table with links to the movie page.
                $sql = 'SELECT *
                FROM rating INNER JOIN movies
                ON rating.MovieID = movies.MovieID
                WHERE rating.UserID=:userID
                ORDER BY rating.reviewID DESC LIMIT 10';
                $stmt = $pdo->prepare($sql);
                $stmt->bindValue(':userID', $userID);
                $stmt->execute();
                $result = $stmt->fetchAll(PDO::FETCH_ASSOC);

                //Variable For adjusting Row Reviews.
                $rowCount = 0;
                foreach ($result as $row) {
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
        <?php
    } else {
        $sql = 'SELECT * FROM user WHERE UserName= :user';
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':user', $_SESSION['username']);
        $stmt->execute();
        $row = $stmt->fetch(PDO::FETCH_ASSOC);

        //Show User Avatar
        if ($row['avatar'] == NULL) {
            echo "<img class='pic' src='SiteImages/noAvatar.jpg'>";
        } else {
            $userImage = $row['avatar'];
            echo "<img class='pic' src='$userImage'>";
        }

        echo "<div style='font-size: 60px; text-decoration: underline;' class='headline' id='mycss'>$userName</div>";
        ?>


        <!-- Shows Bio -->
        <div class="flex-container">
            <div class='bio'>
                <?php
                if ($row['Bio'] == NULL) {
                    echo "You have not created a Bio yet.";
                } else {
                    $bio = $row['Bio'];
                    echo " <p>$bio</p> ";
                } ?>
                <br><br><br><br><br><br><br><br><br>
            </div>

            <div style="" ;>
                <p><b>Friends List</b></p>
                <!--Show your Friends-->
                <?php
                $sql = 'SELECT friends.UserID, friends.FriendID, user.UserName 
                        FROM friends INNER JOIN user 
                        ON friends.FriendID = user.UserID 
                        WHERE friends.UserID=:userID AND Status=2';
                $stmt = $pdo->prepare($sql);
                $stmt->bindValue(':userID', $_SESSION['userID']);
                $stmt->execute();
                $stmt = $stmt->fetchAll(PDO::FETCH_ASSOC);
                echo "<table><tr><th>Freind Name</th></tr>";
                foreach ($stmt as $row) {
                    echo "<tr><td>" . $row['UserName'] . "</td></tr>";
                }
                echo "</table>";
                ?>

            </div>
        </div>

        <!-- Upload an avatar Form -->
        <div class="container1">
            <form action="uploadAvatar.php" enctype="multipart/form-data" method="POST">
                <label><b>Please Upload Avatar Image .jpg only.</b></label><br><br>
                <input type="file" name="upload"><br>
                <button type="submit">Upload Avatar</button>
            </form>
        </div>

        <div class="container1">
            <form action="updateUser.php" enctype="multipart/form-data" method="POST">
                <label><b>Update BIO</b></label>
                <textarea class="textMovie" placeholder="Please Enter A Bio" name="bio" rows="4"
                          cols="50"></textarea>
                <button type="submit">Add/Update Bio</button>
            </form>
        </div>
        <table>
            <tr>
                <?php
                echo "<h1>Movies You have Rated</h1>";
                //Get the Latest 10 Movies and Display them in a table with links to the movie page.
                $sql = 'SELECT *
                FROM rating INNER JOIN movies
                ON rating.MovieID = movies.MovieID
                WHERE rating.UserID=:userID
                ORDER BY rating.reviewID DESC LIMIT 10';
                $stmt = $pdo->prepare($sql);
                $stmt->bindValue(':userID', $_SESSION['userID']);
                $stmt->execute();
                $result = $stmt->fetchAll(PDO::FETCH_ASSOC);

                //Variable For adjusting Row Reviews.
                $rowCount = 0;
                foreach ($result as $row) {
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
    <?php } ?>
    </body>
</div>
</html>