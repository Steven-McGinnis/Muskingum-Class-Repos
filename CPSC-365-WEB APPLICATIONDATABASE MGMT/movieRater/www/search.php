<?php
/**
 * @var $pdo
 * @var $includePath
 */
?>
    <div id="wrapper">
    <body>

<?php
$title = "Search - Movierater";
require('config.php');
require_once($includePath . 'header.php');
$search = htmlentities($_POST['search']);
$search = "%" . $search . "%";
$sql = ('SELECT * FROM movies 
                 WHERE movies.Name 
                 LIKE ?');
$stmt = $pdo->prepare($sql);
$stmt->bindParam(1, $search);
$stmt->execute();
$row = $stmt->fetchAll(PDO::FETCH_ASSOC);
if ($row == false) {
    echo "<img src ='SiteImages/noResults.png'/></a>";
} else { ?>
    <table>
        <tr>
            <th>Image</th>
            <th>Name</th>
            <th>Date</th>
            <th>Actors</th>
            <th>Rating</th>


        </tr>
        <?php
        foreach ($row as $item) {
            //Get Movie Ratings to Display
            $movieID = $item['MovieID'];
            $sql2 = ('SELECT ROUND(AVG(rating), 1) AS rating FROM Rating WHERE MovieID= :movieID');
            $stmt2 = $pdo->prepare($sql2);
            $stmt2->bindParam(':movieID', $movieID);
            $stmt2->execute();
            $row2 = $stmt2->fetch(PDO::FETCH_ASSOC);

            //Get Movie Actors to Display
            $sql3 = ('SELECT * 
                 FROM Actors,filmcast 
                 WHERE  filmcast.MovieID=:movieID AND Actors.actorID = filmcast.actorID');
            $stmt3 = $pdo->prepare($sql3);
            $stmt3->bindParam(':movieID', $movieID);
            $stmt3->execute();
            $row3 = $stmt3->fetch(PDO::FETCH_ASSOC);


            //Start Table Row
            echo "<tr>";

            //Movie Image Cell
            echo "<td>";
            $filename = $item['Thumb'];
            echo "<a href='MoviePage.php?moviename={$item['Name']}'><img href='MoviePage.php?moviename={$item['Name']}' src ='$filename'/></a><br>";
            echo "</td>";

            //Movie Name Cell
            echo "<td>";
            echo "<a href='MoviePage.php?moviename={$item['Name']}'  value='{$item['Name']}'>" . $item['Name'] . '</a></br>';
            echo "</td>";

            // Movie Date Cell
            echo "<td>";
            echo "<label>" . $item['Date'] . "</label>";
            echo "</td>";

            //Movie Actors Cell
            echo "<td>";
            echo $row3 ['Name'] . ", ";
            foreach ($stmt3 as $row3) {
                echo $row3['Name'] . ", ";
            }
            echo "</td>";

            //Movie Rating Cell
            echo "<td>";
            if ($row2 ['rating'] == NULL) {
                $rate = "0.0";
                echo "<div style='font-size: 30px;'>$rate</div>";
            } else {
                $rate = $row2['rating'];
                echo "<div style='font-size: 30px;'>$rate</div>";

            }
            echo "</td>";

            //End Table Row
            echo "</tr>";
        } ?>
    </table>
<?php }

