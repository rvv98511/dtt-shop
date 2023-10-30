<?php

include "connectServer.php";
$username = $_POST['username'];
$matkhau = $_POST['matkhau'];

$query = "SELECT * FROM manager WHERE UserName = '".$username."' AND MatKhau = '".$matkhau."'";
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