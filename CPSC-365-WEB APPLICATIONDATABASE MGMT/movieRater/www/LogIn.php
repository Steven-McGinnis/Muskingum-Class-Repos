<?php
/**
 * @var $pdo
 * @var $includePath
 */
$title = "LogIn - Movierater";
require('config.php');
require_once($includePath . 'header.php');
?>

<body>
<div id="wrapper">
    <h1>User Log In</h1>
    <?php
    if (isset($_GET['error'])) {
        if ($_GET['error'] == 1) {
            echo "There was an error with username or password.";
        } elseif ($_GET['error'] == 2) {
            echo "There is no user with this name.";
        } elseif ($_GET['error'] == 3) {
            echo "You Must Wait 1 Minute Before Attempting To Log In Again.";
        }
    }
    ?>

    <div class="container">
        <form action="loginProcessing.php" method="POST">
            <label><b>Username</b></label>
            <input type="text" placeholder="Enter Username" name="uname" required>

            <label><b>Password</b></label>
            <input type="password" placeholder="Enter Password" name="psw" required>

            <button type="submit">Login</button>

            <label>Dont have an account?</label>
            <a href="registration.php">Create Account</a>
        </form>
    </div>
</div>
</body>
</html>