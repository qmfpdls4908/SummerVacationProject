<?php
	$con = mysqli_connect("localhost", "svproject", "admin2556!", "svproject");
    mysqli_query($con, 'SET NAMES utf8');

	$Email = $_POST["Email"];
	$Name = $_POST["Name"];
	$Sex = $_POST["Sex"];
	$Birthday = $_POST["Birthday"];
	$SpareTime = $_POST["SpareTime"];
	$Favorite = $_POST["Favorite"];
	$Dislike = $_POST["Dislike"];
	$Image = $_POST["Image"];

	$statement = mysqli_prepare($con, "INSERT INTO usersPageDB (Email, Name, Sex, Birthday, SpareTime, Favorite, Dislike, profileImage) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
	if($statement){
		mysqli_stmt_bind_param($statement, "ssssssss", $Email, $Name, $Sex, $Birthday, $SpareTime, $Favorite, $Dislike, $Image);
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