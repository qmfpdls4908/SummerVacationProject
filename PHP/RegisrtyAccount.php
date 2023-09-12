<?php
    $con = mysqli_connect("localhost", "svproject", "admin2556!", "svproject");
    mysqli_query($con, 'SET NAMES utf8');

    $email = $_POST["email"];
    $UUID = $_POST["UUID"];
    $partner_email = $_POST["partner_email"];
    $partner_UUID = $_POST["partner_UUID"];

    $statement = mysqli_prepare($con, "INSERT INTO userAccountUUID (email, UUID, partner_email, partner_UUID) VALUES (?, ?, ?, ?)");
    
    if ($statement) {
        mysqli_stmt_bind_param($statement, "ssss", $email, $UUID, $partner_email, $partner_UUID);
        
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
