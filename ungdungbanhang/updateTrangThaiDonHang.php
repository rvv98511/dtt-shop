<?php

include "connectServer.php";
$iddonhang = $_POST['iddonhang'];
$trangthai = $_POST['trangthai'];

$query = "UPDATE donhang SET TrangThai = '".$trangthai."' WHERE IdDonHang = ".$iddonhang;
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