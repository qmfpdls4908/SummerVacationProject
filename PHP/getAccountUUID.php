<?php
    $con = mysqli_connect("localhost", "svproject", "admin2556!", "svproject");
    mysqli_query($con,'SET NAMES utf8');

    $email = $_POST["email"];

    $statement = mysqli_prepare($con, "SELECT * FROM userDB WHERE userID = ? AND userPassword = ?");
    mysqli_stmt_bind_param($statement, "ss", $userID, $userPassword);
    mysqli_stmt_execute($statement);


    $statement = mysqli_prepare($con, "SELECT UUID FROM userAccountUUID WHERE email=?");
    mysqli_stmt_bind_param($statement, "s", $email);
    mysqli_stmt_execute($statement);


    $result = $mysqli->query($query);
    $total_record = $result->num_rows;
    $result_array = array();
    for ( $i = 0; $i < $total_record; $i++ ) {
      // 한행씩 읽기 위해 offset을 준다.
      $result->data_seek($i);
  
      // 결과값을 배열로 바꾼다.
      $row = $result->fetch_array();
      // 결과값들을 JSON형식으로 넣기 위해 연관배열로 넣는다.
      $row_array = array(
        "email" => $row['email'],
        "UUID" => $row['UUID'],
        "partner_email" => $row['partner_email'],
        "partner_UUID" => $row['partner_UUID'],
        );
      // 한 행을 results에 넣을 배열의 끝에 추가한다.
      array_push($result_array,$row_array);
    }
    $arr = array(
      "status" => "OK",
      "num_result" => "$total_record",
      "results" => $result_array
    );
    echo json_encode($arr);
?>