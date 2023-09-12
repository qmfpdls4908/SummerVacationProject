<?php
	$con = mysqli_connect("localhost", "svproject", "admin2556!", "svproject");
    mysqli_query($con, 'SET NAMES utf8');

	$Email = $_POST["Email"];
	$Question1 = $_POST["Question1"];
	$Question2 = $_POST["Question2"];
	$Question3 = $_POST["Question3"];

	$statement = mysqli_prepare($con, "INSERT INTO userPropensity (Email, Question1, Question2, Question3) VALUES (?, ?, ?, ?)");

	if($statement){
		mysqli_stmt_bind_param($statement, "ssss", $Email, $Question1, $Question2, $Question3);
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