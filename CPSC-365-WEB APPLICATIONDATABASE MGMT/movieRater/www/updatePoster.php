<?php
//Begin Required Block
/**
 * @var $pdo
 * @var $includePath
 */
require('config.php');
require_once($includePath . 'dbconfig.php');
session_start();
var_dump($_POST);
//End Required Block

//Fix Poster From Broken Movie Page
//TODO Will destroy page and create a full update movie page.
if ($_FILES['upload']['error'] == UPLOAD_ERR_OK) {
    $finfo = new finfo (FILEINFO_MIME_TYPE);
    $ftype = $finfo->file($_FILES['upload']['tmp_name']);
    if ($ftype == "image/jpeg") {
        $movie_name = $_POST['movieName'];
        $movie_date = $_POST['date'];
        $movie_name = preg_replace('/[^A-Za-z0-9. -]/', '', $movie_name);
        $movie_name = str_replace(' ', '', $movie_name);
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

        $sql = 'UPDATE movies SET Image=:image  WHERE Name=:movieName AND Date=:date';
        $stmt = $pdo->prepare($sql);
        $stmt->bindValue(':image', $new_name);
        $stmt->bindValue(':movieName', $_POST['movieName']);
        $stmt->bindValue(':date', $_POST['date']);
        $stmt->execute();

        $sql = 'UPDATE movies SET Thumb=:Thumb  WHERE Name=:movieName AND Date=:date';
        $stmt = $pdo->prepare($sql);
        $stmt->bindValue(':Thumb', $thumbName);
        $stmt->bindValue(':movieName', $_POST['movieName']);
        $stmt->bindValue(':date', $_POST['date']);
        $stmt->execute();
    }
}

header('Location: MoviePage.php?moviename=' . $_POST['movieName']);
