<?php
require "DataBase.php";
$db = new DataBase();

if (isset($_GET['user_id']) && $db->dbConnect()) {
    $user_id = mysqli_real_escape_string($db->connect, $_GET['user_id']);

    $sql = "SELECT c.client_name, c.client_address FROM clients c
            JOIN user_client uc ON c.client_id = uc.client_id
            WHERE uc.user_id = '$user_id'";

    $result = mysqli_query($db->connect, $sql);
    $stores = [];
    while ($row = mysqli_fetch_assoc($result)) {
        $stores[] = $row;
    }
    echo json_encode($stores);
} else {
    echo "Failed to connect to the database";
}
?>
