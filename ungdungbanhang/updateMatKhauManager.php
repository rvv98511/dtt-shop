<?php

include "connectServer.php";
$username = $_POST["username"];
$matkhau = $_POST["matkhau"];

$query = "UPDATE manager SET MatKhau = '".$matkhau."' WHERE UserName = '".$username."'";
$data = mysqli_query($conn, $query);

if($data == true) {
    $arr = [
        "success" => true,
        "message" => "Thanh cong",
    ];
}
else {
    $arr = [
        "success" => false,
        "message" => "Khong thanh cong"
    ];
}

print_r(json_encode($arr));

?>