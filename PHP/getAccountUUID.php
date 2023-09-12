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
      // ���྿ �б� ���� offset�� �ش�.
      $result->data_seek($i);
  
      // ������� �迭�� �ٲ۴�.
      $row = $result->fetch_array();
      // ��������� JSON�������� �ֱ� ���� �����迭�� �ִ´�.
      $row_array = array(
        "email" => $row['email'],
        "UUID" => $row['UUID'],
        "partner_email" => $row['partner_email'],
        "partner_UUID" => $row['partner_UUID'],
        );
      // �� ���� results�� ���� �迭�� ���� �߰��Ѵ�.
      array_push($result_array,$row_array);
    }
    $arr = array(
      "status" => "OK",
      "num_result" => "$total_record",
      "results" => $result_array
    );
    echo json_encode($arr);
?>