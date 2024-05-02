<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['product']) && isset($_POST['title']) && isset($_POST['store']) && isset($_POST['description'])) {
    if ($db->dbConnect()) {
        if ($db->feedback("feedback", $_POST['product'], $_POST['title'], $_POST['store'], $_POST['description'])) {
            echo "Feedback Submitted Successfully";
        } else echo "Feedback Submission Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
