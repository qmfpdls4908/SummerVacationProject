<?php
    $con = mysqli_connect("localhost", "svproject", "admin2556!", "svproject");
    mysqli_query($con, 'SET NAMES utf8');

    $Email = $_POST["Email"];

    $statement = mysqli_prepare($con, "SELECT * FROM userPropensity WHERE Email = ?");
    mysqli_stmt_bind_param($statement, "s", $Email);
    mysqli_stmt_execute($statement);


    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $Email, $Question1, $Question2, $Question3);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)) {
        //대소문자 구분하여 비밀번호 비교
        $response["success"] = true;
        $response["success"] = $Email;
        $response["Question1"] = $Question1;
        $response["Question2"] = $Question2;
        $response["Question3"] = $Question3;
    }
    
    echo json_encode($response);
?>
