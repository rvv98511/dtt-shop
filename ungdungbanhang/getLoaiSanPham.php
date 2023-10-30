<?php

include "connectServer.php";
$page = $_POST['page'];
$total = 5;
$pos = ($page - 1) * $total;
$loai = $_POST['loai'];
$query = "SELECT * FROM sanpham WHERE Loai = '".$loai."' ORDER BY IdSP DESC LIMIT ".$pos.", ".$total;
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