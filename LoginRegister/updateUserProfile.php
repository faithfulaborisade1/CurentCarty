<?php
require 'DataBase.php';
$db = new DataBase();

if (isset($_POST['table']) && isset($_POST['username']) && isset($_POST['fullname']) && isset($_POST['email']) && isset($_POST['contact'])) {
    $table = $_POST['table'];
    $username = $_POST['username'];
    $fullname = $_POST['fullname'];
    $email = $_POST['email'];
    $contact = $_POST['contact'];

    if ($db->updateUserProfile($table, $username, $fullname, $email, $contact)) {
            echo "Profile Updated Successfully";
    } else {
        echo "Error updating profile";
    }
} else {
    echo "Missing parameters";
}
?>
