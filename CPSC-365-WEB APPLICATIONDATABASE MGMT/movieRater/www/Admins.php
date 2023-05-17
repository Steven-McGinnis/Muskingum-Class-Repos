<?php
/**
 * @var $pdo
 * @var $includePath
 */
$title = "Administration - Movierater";
require('config.php');
require_once($includePath . 'header.php');

//Recheck for Administrator Privileges.
if (isset($_SESSION['username'])) {
    $sql = 'SELECT * FROM User WHERE UserName= :UserName';
    $stmt = $pdo->prepare($sql);
    $stmt->bindParam(':UserName', $_SESSION['username']);
    $stmt->execute();
    $row = $stmt->fetch(PDO::FETCH_ASSOC);
    if ($row) {
        if ($row['UserName'] == $_SESSION['username'] && $row['Administrator'] == $_SESSION['admin']) {
            if ($row['Administrator'] == 1) {
            } else {
                header('Location: logoutProcessing.php');
            }
        } else {
            header('Location: logoutProcessing.php');
        }
    }
}

?>

<body>
<div id="wrapper">
    <h2><?php echo("Welcome {$_SESSION['username']}!" . "<br />"); ?></h2><br>
    <?php
    if (isset($_GET['error'])) {
        if ($_GET['error'] == 1) {
            echo "The ReleaseDate must be a 4 digit number.";
        } elseif ($_GET['error'] == 2) {
            echo "The Movie Upload Encountered an Error and was RolledBack";
        } elseif ($_GET['error'] == 3) {
            echo "There is Already a Movie With this Name and Date!";
        }
    }
    if (isset($_GET['success'])) {
        if ($_GET['success'] == 1) {
            echo "Movie Added Successfully";
        }
    }
    ?>

    <div class="tab">
        <button class="tablinks" onclick="adminTab(event, 'addMovie')">Add Movie</button>
        <!--<button class="tablinks" onclick="adminTab(event, 'addActor')">Add Actor</button>
        <button class="tablinks" onclick="adminTab(event, 'mngUser')">Manage Users</button>-->
    </div>

    <div id="addMovie" class="tabcontent">
        <h3>Add Movie</h3>
        <div class="container">
            <form action="movieProcessing.php" enctype="multipart/form-data" method="POST">

                <label><b>Movie Name</b></label>
                <input type="text" placeholder="Enter Movie Name" name="movieName" required>

                <label><b>Release Date</b></label>
                <input type="text" placeholder="Release Date" name="releaseDate" required>

                <label><b>Movie Description</b></label>
                <textarea class="textMovie" placeholder="Please Enter Movie Description" name="description" rows="4"
                          cols="50"></textarea>

                <label><b>Please Enter Actors (with Comma Between names)</b></label>
                <input type="text" placeholder="Please Enter Actors" name="actors"><br>

                <label><b>Please Upload Movie Poster Image .jpg only.</b></label><br><br>
                <input type="file" name="upload"><br>
                <br>

                <label><b>Please Check All Relevant Tags</b></label>
                <br>
                <?php $result = $pdo->query('SELECT * FROM tags');
                foreach ($result as $row) {
                    echo "<input type='checkbox' name='tags[]'  value='{$row['tagName']}'>" . $row['tagName'] . '</br>';

                } ?>


                <button type="submit">Add Movie</button>
            </form>
        </div>
    </div>


    <!--<div id="addActor" class="tabcontent">
       <h3>Add Actor</h3>
        <div class="container">
            <form action="movieProcessing.php" method="POST">
                <label><b>Actor Name</b></label>
                <input type="text" placeholder="Enter Actor" name="actorName" required>

                <input type = "checkbox" name="director"> Director<br>

                <input type = "checkbox" name="star"> Star<br>

                <input type = "checkbox" name="supportingRole"> Supporting Role

                <button type="submit">Add Movie</button>
            </form>
        </div>
    </div>-->

    <div id="mngUser" class="tabcontent">
        <h3>Manage User</h3>

    </div>

    <script>
        function adminTab(evt, command) {
            let i, tabcontent, tablinks;
            tabcontent = document.getElementsByClassName("tabcontent");
            for (i = 0; i < tabcontent.length; i++) {
                tabcontent[i].style.display = "none";
            }
            tablinks = document.getElementsByClassName("tablinks");
            for (i = 0; i < tablinks.length; i++) {
                tablinks[i].className = tablinks[i].className.replace(" active", "");
            }
            document.getElementById(command).style.display = "block";
            evt.currentTarget.className += " active";
        }
    </script>
</div>
</body>

</html>