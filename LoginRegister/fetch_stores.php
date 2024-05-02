<?php
require "DataBase.php";
$db = new DataBase();
if ($db->dbConnect()) {
    $sql = "SELECT client_name,  client_address FROM clients";  // Fetch client_name and town
    $result = mysqli_query($db->connect, $sql);
    $clients = [];
    while ($row = mysqli_fetch_assoc($result)) {
        $clients[] = $row;  // Store the entire row in the array
    }
    echo json_encode($clients);  // This will now be an array of objects
} else {
    echo "Error: Database connection";
}
?>
