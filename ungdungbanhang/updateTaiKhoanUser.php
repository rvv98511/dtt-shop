<?php

include "connectServer.php";
if(isset($_POST['username']))
    $username = $_POST['username'];

if(isset($_POST['tenkh']))
    $tenkh = $_POST['tenkh'];
else
    $tenkh = null;

if(isset($_POST['sodienthoai']))
    $sodienthoai = $_POST['sodienthoai'];
else
    $sodienthoai = null;

if(isset($_POST['email']))
    $email = $_POST['email'];
else
    $email = null;

if(isset($_POST['matkhau']))
    $matkhau = $_POST['matkhau'];
else
    $matkhau = null;

if($tenkh != null) {
    $query = "UPDATE user SET TenKH = '".$tenkh."' WHERE UserName = '".$username."'";
    $data = mysqli_query($conn, $query);

    if($data == true) {
        $arr = [
            "success" => true,
            "message" => "Thanh cong"
        ];
    }
    else {
        $arr = [
            "success" => false,
            "message" => "Khong thanh cong"
        ];
    }
}

if($sodienthoai != null) {
    $query = "SELECT * FROM user WHERE SoDienThoai = '".$sodienthoai."'";
    $data = mysqli_query($conn, $query);
    $numrow = mysqli_num_rows($data);

    $result = array();

    while($row = mysqli_fetch_assoc($data)) {
        $result[] = $row;
    }

    if($numrow > 0) {
        $arr = [
            "success" => false,
            "message" => "Thông tin đã tồn tại",
            "result" => $result
        ];
    }
    else {
        $query = "UPDATE user SET SoDienThoai = '".$sodienthoai."' WHERE UserName = '".$username."'";
        $data = mysqli_query($conn, $query);

        if($data == true) {
            $arr = [
                "success" => true,
                "message" => "Thanh cong"
            ];
        }
        else {
            $arr = [
                "success" => false,
                "message" => "Khong thanh cong"
            ];
        }
    }
}

if($email != null) {
    $query = "SELECT * FROM user WHERE Email = '".$email."'";
    $data = mysqli_query($conn, $query);
    $numrow = mysqli_num_rows($data);

    $result = array();

    while($row = mysqli_fetch_assoc($data)) {
        $result[] = $row;
    }

    if($numrow > 0) {
        $arr = [
            "success" => false,
            "message" => "Thông tin đã tồn tại",
            "result" => $result
        ];
    }
    else {
        $query = "UPDATE user SET Email = '".$email."' WHERE UserName = '".$username."'";
        $data = mysqli_query($conn, $query);

        if($data == true) {
            $arr = [
                "success" => true,
                "message" => "Thanh cong"
            ];
        }
        else {
            $arr = [
                "success" => false,
                "message" => "Khong thanh cong"
            ];
        }
    }
}

if($matkhau != null) {
    $query = "UPDATE user SET MatKhau = '".$matkhau."' WHERE UserName = '".$username."'";
    $data = mysqli_query($conn, $query);

    if($data == true) {
        $arr = [
            "success" => true,
            "message" => "Thanh cong"
        ];
    }
    else {
        $arr = [
            "success" => false,
            "message" => "Khong thanh cong"
        ];
    }
}

print_r(json_encode($arr));

?>