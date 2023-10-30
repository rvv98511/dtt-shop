<?php

include "connectServer.php";
$username = $_POST['username'];
$tenkh = $_POST['tenkh'];
$sodienthoai = $_POST['sodienthoai'];
$email = $_POST['email'];
$matkhau = $_POST['matkhau'];

$query = "SELECT * FROM user WHERE UserName = '".$username."' OR SoDienThoai = '".$sodienthoai."' OR Email = '".$email."'";
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
    $query = "INSERT INTO user(UserName, TenKH, SoDienThoai, Email, MatKhau) VALUES ('".$username."','".$tenkh."','".$sodienthoai."','".$email."','".$matkhau."')";
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
            "message" => "Khong thanh cong",
        ];
    }
}
print_r(json_encode($arr));

?>