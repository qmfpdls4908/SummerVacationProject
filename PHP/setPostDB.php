<?php
	$con = mysqli_connect("localhost", "svproject", "admin2556!", "svproject");
    mysqli_query($con, 'SET NAMES utf8');

	$Email = $_POST["Email"];
    $content = $_POST["content"];

	$statement = mysqli_prepare($con, "INSERT INTO userPostDB (Email, content) VALUES (?,?)");

	if($statement){
		mysqli_stmt_bind_param($statement, "ss", $Email, $content);
		if (mysqli_stmt_execute($statement)) {
            $response = array("success" => true);
        } else {
            $response = array("success" => false);
        }
		mysqli_stmt_close($statement);
	}else{
		$response = array("success" => false);
	}
	echo json_encode($response);
?>