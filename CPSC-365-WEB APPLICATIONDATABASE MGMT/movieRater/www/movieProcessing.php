<?php
/**
 * @var $pdo
 * @var $includePath
 */
require('config.php');
require_once($includePath . 'dbconfig.php');
//Ensure that date is a 4-digit number
$pattern = "/^[0-9]{4}$/";
if (preg_match($pattern, $_POST['releaseDate']) == 0) {
    header('Location: Admins.php?error=1');
}

//Take the Actor list and break it into an array.
$actorList = explode(",", $_POST['actors']);

//Check to see if there is a Movie With that Date Already in the Server
$sql = 'SELECT * FROM Movies WHERE Name= :movieName AND Date= :releaseDate';
$stmt = $pdo->prepare($sql);
$stmt->bindParam(':movieName', $_POST['movieName']);
$stmt->bindParam(':releaseDate', $_POST['releaseDate']);
$stmt->execute();
$row = $stmt->fetch(PDO::FETCH_ASSOC);

//If there is send back with error handling.
if ($row) {
    header('Location: Admins.php?error=3');
    exit();
}

try {
    //Create out of loop variable.
    $new_name = "";
    $thumbName = "";
    $actorAutoIncrement = NULL;
    //Image Handling
    if ($_FILES['upload']['error'] == UPLOAD_ERR_OK) {
        $finfo = new finfo (FILEINFO_MIME_TYPE);
        $ftype = $finfo->file($_FILES['upload']['tmp_name']);
        if ($ftype == "image/jpeg") {
            $movie_name = $_POST['movieName'];
            $movie_name = preg_replace('/[^A-Za-z0-9. -]/', '', $movie_name);
            $movie_name = str_replace(' ', '', $movie_name);
            $movie_date = $_POST['releaseDate'];
            $new_name = "posters/" . $movie_name . $movie_date . ".jpg";
            move_uploaded_file($_FILES['upload']['tmp_name'], $new_name);

            $image = imagecreatefromjpeg($new_name);
            $width = imagesx($image);
            $height = imagesy($image);

            $thumbHeight = 500;
            $thumbWidth = floor($width * ($thumbHeight / $height));
            $thumbnail = imagecreatetruecolor($thumbWidth, $thumbHeight);

            imagecopyresampled($thumbnail, $image, 0, 0, 0, 0, $thumbWidth, $thumbHeight,
                $width, $height);

            imagejpeg($thumbnail, $new_name);

            $image = imagecreatefromjpeg($new_name);
            $width = imagesx($image);
            $height = imagesy($image);

            $thumbHeight = 200;
            $thumbWidth = floor($width * ($thumbHeight / $height));
            $thumbnail = imagecreatetruecolor($thumbWidth, $thumbHeight);

            imagecopyresampled($thumbnail, $image, 0, 0, 0, 0, $thumbWidth, $thumbHeight,
                $width, $height);

            $thumbName = "thumbnails/" . $movie_name . $movie_date . ".jpg";
            imagejpeg($thumbnail, $thumbName);

        } else {
            $new_name = NULL;
            $thumbName = NULL;
        }
    }

    //Prepare Sql for Movie Inserts
    $pdo->beginTransaction();
    $sql = 'INSERT INTO Movies(Name, Date, Description, Image, Thumb) VALUES (:movieName, :releaseDate, :description, :image, :Thumb)';
    $stmt = $pdo->prepare($sql);

    //Bind the Inserts for the Movie
    $stmt->bindParam(':movieName', $_POST['movieName']);
    $stmt->bindParam(':releaseDate', $_POST['releaseDate']);
    $stmt->bindParam(':description', $_POST['description']);
    $stmt->bindValue(':image', $new_name);
    $stmt->bindValue('Thumb', $thumbName);
    $stmt->execute();

    //Assign the last incrimented ID to the out of loop variable.
    $movieAutoIncrement = $pdo->lastInsertId();

    //Loop Through Each Cast Member and Apply the Movie ID and Cast Id to Many to Many Table FilmCast
    foreach ($actorList as $item) {
        $item = trim($item, " ");
        //Find if the actor is in the actor table.
        $act = 'SELECT * FROM Actors WHERE Name= :name';
        $actstmt = $pdo->prepare($act);
        $actstmt->bindParam(':name', $item);
        $actstmt->execute();
        $row = $actstmt->fetch(PDO::FETCH_ASSOC);

        //If the Actors Not already in the table add it.
        if (!$row) {
            $actadd = 'INSERT INTO Actors(Name) VALUES (:actorName)';
            $actadding = $pdo->prepare($actadd);
            $actadding->bindParam(':actorName', $item);
            $actadding->execute();
            $actorAutoIncrement = $pdo->lastInsertId();
        } else {
            $actorAutoIncrement = $row['actorID'];
        }

        //Begin Linking Actor and Movie Together in Joining Table
        $linking = 'INSERT INTO FilmCast(MovieID, actorID) VALUES (:movieID, :actorID)';
        $link = $pdo->prepare($linking);

        //Bind the Linking Table Paramaters and Execute
        $link->bindParam(':movieID', $movieAutoIncrement);
        $link->bindParam(':actorID', $actorAutoIncrement);
        $link->execute();
    }
    //Checks if any Checkboxes have been checked.
    if (isset($_POST['tags'])) {
        $tags = $_POST['tags'];

        //For Each Checkbox get the tag ID
        foreach ($tags as $tags => $value) {
            $tag = $value;
            $gettag = 'SELECT * FROM tags WHERE tagName= :tag';
            $getTagID = $pdo->prepare($gettag);
            $getTagID->bindParam(':tag', $tag);
            $getTagID->execute();
            $newrow = $getTagID->fetch(PDO::FETCH_ASSOC);
            $tagID = $newrow['tagID'];
            var_dump($newrow);


            //Take TagID and Insert it into tagged movies with the movie number
            $tags = 'INSERT INTO taggedMovies(tagID, movieID) VALUES (:tag, :movieID)';
            $tagging = $pdo->prepare($tags);
            //var_dump($tags);
            $tagging->bindParam(':tag', $tagID);
            $tagging->bindParam(':movieID', $movieAutoIncrement);
            $tagging->execute();
        }
    }
    $pdo->commit();
    header('Location: Admins.php?success=1');
} catch (Exception $e) {
    $pdo->rollBack();
    header('Location: Admins.php?error=2');
}


