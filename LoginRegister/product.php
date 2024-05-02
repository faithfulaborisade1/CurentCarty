<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['prod_name']) && isset($_POST['prod_pic']) && isset($_POST['category']) && isset($_POST['description']) && isset($_POST['prod_stock'])&& isset($_POST['prize'])) {
    if ($db->dbConnect()) {
        if ($db->addProduct("product", $_POST['prod_name'], $_POST['prod_pic'], $_POST['category'], $_POST['description'], $_POST['prod_stock'], $_POST['prize'])) {
            echo "Product added";
        } else echo "Product Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
