<?php
require "DataBase.php";
$db = new DataBase();

// Assuming 'users' is the default table for user profiles.
// This can be changed based on your application's requirements.
$defaultTable = "users";

if (isset($_POST['username'])) {
    $username = $_POST['username'];

    // Optionally, you can pass a different table name via POST request if needed.
    $table = isset($_POST['table']) ? $_POST['table'] : $defaultTable;

    if ($db->dbConnect()) {
        if ($profile = $db->getUserProfile($table, $username)) {
            echo json_encode($profile);
        } else {
            echo "Error fetching user profile";
        }
    } else {
        echo "Error: Database connection";
    }
} else {
    echo "Username not provided";
}
?>
