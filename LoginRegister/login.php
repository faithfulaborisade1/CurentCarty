<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['username']) && isset($_POST['password'])) {
    if ($db->dbConnect()) {
        $userId = $db->logIn("users", $_POST['username'], $_POST['password']);
        if ($userId) {
            echo json_encode([
                'status' => 'success',
                'message' => 'Login Success',
                'userId' => $userId  // Send the user ID to the client
            ]);
        } else {
            echo json_encode([
                'status' => 'fail',
                'message' => 'Username or Password wrong'
            ]);
        }
    } else {
        echo json_encode([
            'status' => 'error',
            'message' => 'Error: Database connection'
        ]);
    }
} else {
    echo json_encode([
        'status' => 'error',
        'message' => 'All fields are required'
    ]);
}

?>
