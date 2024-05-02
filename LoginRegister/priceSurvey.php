<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['location']) && isset($_POST['product']) && isset($_POST['barcode']) && isset($_POST['price'])) {
    if ($db->dbConnect()) {
        if ($db->addPriceSurveyEntry("priceSurvey", $_POST['location'], $_POST['product'], $_POST['barcode'], $_POST['price'])) {
            echo "Price Survey Entry Submitted Successfully";
        } else echo "Price Survey Entry Submission Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
