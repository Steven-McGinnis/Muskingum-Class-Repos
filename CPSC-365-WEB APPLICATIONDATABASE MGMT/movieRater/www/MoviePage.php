<?php
/**
 * @var $pdo
 * @var $includePath
 */
$movieName = $_GET['moviename'];
$title = $_GET['moviename'] . " - MovieRater";
$_POST['movieName'] = $movieName;

$isMovie = TRUE;
require('config.php');
require_once($includePath . 'header.php');
?>

<!-- Styling For body -->
<div id="wrapper">
    <body>
    <?php
    //Prepare whats necessary to get movie url and id
    $sql = 'SELECT * FROM Movies WHERE Name= :movieName';
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':movieName', $movieName);
    $stmt->execute();
    $row = $stmt->fetch(PDO::FETCH_ASSOC);

    //Get Movie Image Url From database
    $movieID = $row['MovieID'];
    $movieImg = $row['Image'];
    $date = $row['Date'];
    $movieDesc = $row['Description'];

    //If its null show the Coming soon Image.  Then Display to admins the upload button.
    if ($movieImg == NULL) {
        echo "<img class='pic' src = 'SiteImages/comingSoonPoster.jpg' />";
        if (isset($_SESSION['admin'])) {
            ?>
            <div>
                <form action="updatePoster.php" enctype="multipart/form-data" method="POST">
                    <input name="movieName" type="hidden" value="<?php echo htmlspecialchars($_POST['movieName']) ?>"/>
                    <input name="date" type="hidden" value="<?php echo htmlspecialchars($date) ?>"/>
                    <label><b>Please Upload Movie Poster Image .jpg only.</b></label><br><br>
                    <input type="file" name="upload"><br>
                    <button type="submit">Add Poster</button>
                </form>
            </div>
        <?php }
    }
    //Else Show the Movie Image
    echo "<img class='pic' src ='$movieImg' />";

    //Printing Movie Title
    echo "<div style='font-size: 80px; text-decoration: underline;' class='headline' id='mycss'>$movieName</div>";

    //Print Date
    echo "<p class='item1'>$date</p>";

    //Print Movie Description
    echo "<p>$movieDesc</p>";

    echo "<br>";

    echo "<p>Actors</p>";

    //Show Actors in Movie
    $sql = ('SELECT * 
                 FROM Actors,filmcast 
                 WHERE  filmcast.MovieID=:movieID AND Actors.actorID = filmcast.actorID');
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':movieID', $movieID);
    $stmt->execute();
    $row1 = $stmt->fetch(PDO::FETCH_ASSOC);

    echo $row1 ['Name'] . ", ";
    foreach ($stmt as $row) {
        echo $row['Name'] . ", ";
    }


    //Setup the Average for the Rating on the movies.
    $movieID = $row['MovieID'];
    $sql = ('SELECT ROUND(AVG(rating), 1) AS rating FROM Rating WHERE MovieID= :movieID');
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':movieID', $movieID);
    $stmt->execute();
    $row = $stmt->fetch(PDO::FETCH_ASSOC);

    //Print out label and rating even if empty.
    ?>
    <div class="flex-container">
        <div>
            <?php
            echo "<div class='rating';><p>Movierater Rating</p></div>";
            if ($row ['rating'] == NULL) {
                $rate = "0.0";
                echo "<div style='font-size: 60px;'>$rate</div>";
            } else {
                $rate = $row['rating'];
                echo "<div style='font-size: 60px;'>$rate</div>";

            }
            ?>
        </div>
        <?php

        //If a user is logged in.
        if (isset($_SESSION['username'])) {
        $sql = ('SELECT UserID FROM User WHERE UserName= :UserName');
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':UserName', $_SESSION['username']);
        $stmt->execute();
        $row = $stmt->fetch(PDO::FETCH_ASSOC);
        $userID = $row['UserID'];

        //Grab User Rating
        $sql = ('SELECT * FROM Rating WHERE UserID= :userID AND MovieID=:MovieID');
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':userID', $userID);
        $stmt->bindParam('MovieID', $movieID);
        $stmt->execute();
        $row = $stmt->fetch(PDO::FETCH_ASSOC);
        $userRate = NULL;
        if ($row) {
            if (is_null($row['rating'])) {
                $userRate = NULL;
            } else {
                $userRate = $row['rating'];
            }
        }
        ?>

        <div>
            <?php
            //If there is no User Rating Display Have not rated else display your rating plus numbers.
            if ($userRate == NULL) {
                echo "<div class='rating';><p>You Have not Rated this Movie Yet</p></div>";
            } else {
                echo "<div class='rating';><p>Your Rating</p></div>";
                echo "<div style='font-size: 60px;'>$userRate</div>";
            }
            ?>

            <!--Create Form for Rating the Movie-->
            <form id="rating" action="updateRating.php" method="POST">
                <input name="movieName" type="hidden" value="<?php echo htmlspecialchars($_POST['movieName']) ?>"/>
                <select name="rating">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                    <option value="6">6</option>
                    <option value="7">7</option>
                    <option value="8">8</option>
                    <option value="9">9</option>
                    <option value="10">10</option>
                </select>
                <input type="submit" name="Submit" value="Submit">
            </form>
            <?php } ?>
        </div>
    </div>
    <?php
    //Check to See if there is a User If so Create a comment box for them to add a comment.
    if (isset($_SESSION['username'])) { ?>
        <br><br><br><br><br>
        <label>Please Enter Your Comment</label><br/>
        <form action="comment.php" method="POST">
            <input name="userID" type="hidden" value="<?php echo htmlspecialchars($userID) ?>"/>
            <input name="movieID" type="hidden" value="<?php echo htmlspecialchars($movieID) ?>"/>
            <input name="movieName" type="hidden" value="<?php echo htmlspecialchars($_POST['movieName']) ?>"/>
            <textarea class="comment" placeholder="Please Enter Your Comment" name="comment" rows="4"
                      cols="50"></textarea>
            <br>
            <input type="submit" name="Submit" value="Submit">
        </form>
        <?php
    }

    ?>
    <div id="wrapper">
        <?php

        //Print out the Latest 10 Comments from the Database Using the Movie ID and User Names
        $sql = ('SELECT * FROM comments, user 
                 WHERE MovieID=:movieID AND comments.UserID = user.UserID 
                 ORDER BY Timestamp DESC LIMIT 10');
        $stmt = $pdo->prepare($sql);
        $stmt->bindParam(':movieID', $movieID);
        $stmt->execute();
        echo "<h1>Movie Comments</h1>"
        ?>

        <!-- Create Table for Comments Not the cleanest way to do it but time crunched-->
        <div class="flex-container2">
            <div>
                <table>
                    <tr>
                        <th>User</th>
                        <th>Comment</th>
                        <th>Add Friend/Delete Comment</th>
                    </tr>
                    <?php foreach ($stmt as $row) { ?>
                        <tr>
                            <td><?php echo $row['UserName']; ?></td>
                            <td><?php echo $row['Comment']; ?></td>
                            <?php
                            //Echo out a button, Should have each User ID in each button
                            if (isset($_SESSION['username'])) {
                                if ($row['UserName'] != $_SESSION['username']) {
                                    echo "<td><input type='button' friendID='" . $row['UserID'] . "' class='addFriendMov' value='Add Friend'></td>"; ?>
                                <?php } else {
                                }
                            } ?>
                        </tr>
                    <?php } ?>
                </table>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="jquery-3.6.1.min.js"></script>
    <script type="text/javascript" src="script.js"></script>
    </body>

