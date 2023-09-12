<?php
    $con = mysqli_connect("localhost", "svproject", "admin2556!", "svproject");
    mysqli_query($con, 'SET NAMES utf8');

    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];
    $userEmail = $_POST["userEmail"];
    $userName = $_POST["userName"];
    $userPhoneNumber = $_POST["userPhoneNumber"];

    $statement = mysqli_prepare($con, "INSERT INTO userDB (userID, userPassword, userEmail, userName, userPhoneNumber) VALUES (?, ?, ?, ?, ?)");
    
    if ($statement) {
        mysqli_stmt_bind_param($statement, "sssss", $userID, $userPassword, $userEmail, $userName, $userPhoneNumber);
        
        if (mysqli_stmt_execute($statement)) {
            $response = array("success" => true);
        } else {
            $response = array("success" => false);
        }
    
        mysqli_stmt_close($statement);
    } else {
        $response = array("success" => false);
    }

    echo json_encode($response);
?>
