<?php

include "connectServer.php";
$idsp = $_POST['idsp'];

$query = "DELETE FROM sanpham WHERE IdSP = ".$idsp;
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