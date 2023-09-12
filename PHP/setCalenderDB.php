<?php
	$con = mysqli_connect("localhost", "svproject", "admin2556!", "svproject");
    mysqli_query($con, 'SET NAMES utf8');

	$Email = $_POST["Email"];
	$title = $_POST["title"];
    $startDay = $_POST["startDay"];
    $startTime = $_POST["startTime"];
    $endDay = $_POST["endDay"];
    $endTime = $_POST["endTime"];
    $dateLocate = $_POST["dateLocate"];
    $content = $_POST["content"];

	$statement = mysqli_prepare($con, "INSERT INTO calenderDB (Email, title, startDay, startTime, endDay, endTime, dateLocate, content) VALUES (?,?,?,?,?,?,?,?)");

	if($statement){
		mysqli_stmt_bind_param($statement, "ssssssss", $Email, $title, $startDay, $startTime, $endDay, $endTime, $dateLocate, $content);
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