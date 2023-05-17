<?php
/**
 * @var $pdo
 * @var $includePath
 */
$title = "Register - Movierater";
require('config.php');
require_once($includePath . 'header.php'); ?>

<body>
<div id="wrapper">
    <h1>Register Account</h1>
    <?php
    //Check For Error Handling
    if (isset($_GET['error'])) {
        if ($_GET['error'] == 1) {
            echo "The Passwords Did not Match Try Again.";
        } elseif ($_GET['error'] == 2) {
            echo "There is already a user with this name.";
        }
    }
    ?>
    <!-- Create a Form for Registering -->
    <div class="container">
        <form action="registrationProcessing.php" method="POST">
            <label><b>Username</b></label>
            <input type="text" placeholder="Enter Username" name="username" required>

            <label><b>Email</b></label>
            <input type="text" placeholder="Enter Email" name="email" required>

            <label><b>Password</b></label>
            <input type="password" placeholder="Enter Password" name="psw" required>

            <label><b>Password</b></label>
            <input type="password" placeholder="Enter Password" name="psw1" required>

            <button type="submit">Register</button>
            <label>Already have an account? </label>
            <a href="LogIn.php">Login</a>
        </form>
    </div>
</div>
</body>
</html>