<?php
require "DataBase.php";
$db = new DataBase();

if (isset($_GET['store']) && $db->dbConnect()) {
    $store = $_GET['store'];
    // Prevent SQL injection
    $store = mysqli_real_escape_string($db->connect, $store);

    $sql = "SELECT p.prod_name, p.category, p.description, p.price FROM product p
            INNER JOIN product_client pc ON p.prod_id = pc.prod_id
            INNER JOIN clients c ON pc.client_id = c.client_id
            WHERE c.client_name = '$store'";

    $result = mysqli_query($db->connect, $sql);
    $products = [];
    while ($row = mysqli_fetch_assoc($result)) {
        $products[] = $row;
    }
    echo json_encode($products);
} else {
    echo json_encode(["error" => "Error in request or DB connection"]);
}
?>
