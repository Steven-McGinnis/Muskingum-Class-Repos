<?php
/**
 * @var $pdo
 * @var $includePath
 */

$title = "Home - Movierater";
require('config.php');
require_once($includePath . 'header.php');
?>
<div id="wrapper">
    <body>
    <table>
        <tr>
            <?php
            //Get the Latest 10 Movies and Display them in a table with links to the movie page.
            $result = $pdo->query('SELECT * FROM Movies ORDER BY MovieID DESC LIMIT 10');
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


    echo "<p>Pending Requests</p>";
    $sql2 = ('SELECT user.UserName, user.UserID 
                     FROM friends INNER JOIN user 
                     ON friends.UserID = user.UserID 
                     WHERE friends.FriendID=:user AND STATUS=1');
    $stmt2 = $pdo->prepare($sql2);
    $stmt2->bindParam(':user', $_SESSION['userID']);
    $stmt2->execute();
    $row2 = $stmt2->fetchAll(PDO::FETCH_ASSOC);


    if ($row2 != FALSE) { ?>
        <table>
            <tr>
                <th>User</th>
                <th>Accept/Deny</th>
            </tr>
            <?php
            foreach ($row2 as $item) { ?>
                <tr>
                    <td>
                        <?php echo $item['UserName']; ?>
                    </td>
                    <td>
                        <?php echo "<input type='button' friendID='" . $item['UserID'] . "' class='addFriend' value='Add Friend'>"; ?>
                        <?php echo "<input type='button' friendID='" . $item['UserID'] . "' class='denyFriend' value='Deny Friend'>"; ?>
                    </td>
                    <td>
                        <?php echo "<label id='addF" . $item['UserID'] . "'></label>"; ?>
                    </td>
                </tr>
            <?php } ?>
        </table>
    <?php } ?>



    <?php
    //If User is logged in Show 10 Comments from friends
    //TODO NOT WORKING NOT SURE HOW TO GET ALL OF THE TABLE DATA
    if (isset($_SESSION['username'])) {
    //If not Logged in Display Current Movie Comments.
    $sql = ('SELECT *
             FROM comments INNER JOIN friends 
             ON friends.FriendID = comments.UserID
             LEFT JOIN user 
             On friends.FriendID = user.UserID
             LEFT JOIN movies
             ON comments.MovieID = movies.MovieID
             WHERE friends.UserID =:user
             ORDER BY comments.CommentID DESC LIMIT 10');
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':user', $_SESSION['userID']);
    $stmt->execute();
    $row = $stmt2->fetchAll(PDO::FETCH_ASSOC);
    echo "<h1>Latest Friends Movie Comments</h1>"; ?>
    <div class="flex-container2">
        <div>
            <table>
                <tr>
                    <th>User</th>
                    <th>MovieName</th>
                    <th>Comment</th>
                </tr>
                <?php foreach ($stmt as $row) { ?>
                    <tr>
                        <td><?php echo $row['UserName']; ?></td>
                        <td><?php echo $row['Name']; ?></td>
                        <td><?php echo $row['Comment']; ?></td>
                    </tr>
                <?php } ?>
            </table>
        </div>
    </div>
    <?php

    } else {
        //If not Logged in Display Current Movie Comments.
        $sql = ('SELECT * FROM comments, movies,user 
                 WHERE comments.MovieID = movies.MovieID AND comments.UserID = user.UserID
                 ORDER BY Timestamp DESC LIMIT 10');
        $stmt = $pdo->prepare($sql);
        $stmt->execute();
        echo "<h1>Latest Movie Comments</h1>"; ?>
        <div class="flex-container2">
            <div>
                <table>
                    <tr>
                        <th>User</th>
                        <th>MovieName</th>
                        <th>Comment</th>
                    </tr>
                    <?php foreach ($stmt as $row) { ?>
                        <tr>
                            <td><?php echo $row['UserName']; ?></td>
                            <td><?php echo $row['Name']; ?></td>
                            <td><?php echo $row['Comment']; ?></td>
                        </tr>
                    <?php } ?>
                </table>
            </div>
        </div>
    <?php } ?>
</div>
<script type="text/javascript" src="jquery-3.6.1.min.js"></script>
<script type="text/javascript" src="script.js"></script>
</body>
</div>
</html>
