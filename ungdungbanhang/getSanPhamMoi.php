<?php

include "connectServer.php";
$query = "SELECT * FROM sanpham ORDER BY IdSP DESC";
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
    ];
}

print_r(json_encode($arr));

?>