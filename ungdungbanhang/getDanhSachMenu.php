<?php

include "connectServer.php";
$query = "SELECT TenDanhSach, HinhAnhDanhSach FROM danhsachmenu";
$data = mysqli_query($conn, $query);
$result = array();

while($row = mysqli_fetch_assoc($data)) {
    $result[] = $row;
}

if(!empty($result)) {
    $arr = [
        "success" => true,
        "message" => "Thanh cong",
        "result" => $result
    ];
}
else {
    $arr = [
        "success" => false,
        "message" => "Khong thanh cong",
        "result" => $result
    ];
}

print_r(json_encode($arr));

?>