<?php
    $con = mysqli_connect("localhost", "svproject", "admin2556!", "svproject");
    mysqli_query($con,'SET NAMES utf8');

    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];

    $statement = mysqli_prepare($con, "SELECT * FROM userDB WHERE userID = ? AND userPassword = ?");
    mysqli_stmt_bind_param($statement, "ss", $userID, $userPassword);
    mysqli_stmt_execute($statement);


    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $userPassword, $userEmail, $userName, $userPhoneNumber);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)) {
        //대소문자 구분하여 비밀번호 비교
        if(strcmp($userPassword, $_POST["userPassword"]) === 0){
            $response["success"] = true;
            $response["userID"] = $userID;
            $response["userPassword"] = $userPassword;
            $response["userEmail"] = $userEmail;
            $response["userName"] = $userName;
            $response["userPhoneNumber"] = $userPhoneNumber;
        }
    }
    if ($response["success"]) {
        echo json_encode($response);
    } else {
        // 비밀번호가 일치하지 않는 경우 에러 반환
        $errorResponse = array("success" => false, "error" => "비밀번호가 잘못되었습니다.");
        echo json_encode($errorResponse);
    }
?>