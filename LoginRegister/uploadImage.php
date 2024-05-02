<?php
require "DataBaseConfig.php";

$targetDir = "uploads/"; // Ensure this directory exists on your server
$response = array('status' => 'error', 'message' => 'Error uploading file', 'url' => '');

if (!file_exists($targetDir)) {
    mkdir($targetDir, 0777, true);
}

if (isset($_FILES['image']['name'])) {
    $filename = basename($_FILES['image']['name']);
    $targetFilePath = $targetDir . $filename;
    $fileType = pathinfo($targetFilePath, PATHINFO_EXTENSION);

    // Specify file extensions allowed (for security reasons)
    $allowTypes = array('jpg', 'png', 'jpeg', 'gif');
    if (in_array($fileType, $allowTypes)) {
        // Upload file to the server
        if (move_uploaded_file($_FILES['image']['tmp_name'], $targetFilePath)) {
            $response['status'] = 'success';
            $response['message'] = 'File uploaded successfully';
            $response['url'] = $targetFilePath; // Or any other way to get the full URL to the file
        }
    } else {
        $response['message'] = 'Sorry, only JPG, JPEG, PNG, & GIF files are allowed to upload.';
    }
}

echo json_encode($response);
?>
