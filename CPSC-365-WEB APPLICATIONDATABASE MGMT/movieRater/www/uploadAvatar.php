<?php
//Begin Required Block
/**
 * @var $pdo
 * @var $includePath
 */
require('config.php');
require_once($includePath . 'dbconfig.php');
session_start();

//Avatar Handling
if ($_FILES['upload']['error'] == UPLOAD_ERR_OK) {
    $finfo = new finfo (FILEINFO_MIME_TYPE);
    $ftype = $finfo->file($_FILES['upload']['tmp_name']);
    if ($ftype == "image/jpeg") {
        $userName = $_SESSION['username'];
        $userName = preg_replace('/[^A-Za-z0-9. -]/', '', $userName);
        $userName = str_replace(' ', '', $userName);
        $new_name = "avatars/" . $userName . ".jpg";
        move_uploaded_file($_FILES['upload']['tmp_name'], $new_name);

        $image = imagecreatefromjpeg($new_name);
        $width = imagesx($image);
        $height = imagesy($image);

        //Avatar Size
        $thumbHeight = 250;
        $thumbWidth = floor($width * ($thumbHeight / $height));
        $thumbnail = imagecreatetruecolor($thumbWidth, $thumbHeight);

        imagecopyresampled($thumbnail, $image, 0, 0, 0, 0, $thumbWidth, $thumbHeight,
            $width, $height);

        imagejpeg($thumbnail, $new_name);

        $sql = 'UPDATE user SET avatar=:avatar  WHERE UserName=:user';
        $stmt = $pdo->prepare($sql);
        $stmt->bindValue(':avatar', $new_name);
        $stmt->bindValue(':user', $_SESSION['username']);
        $stmt->execute();
    }
}

header('Location: user.php');
