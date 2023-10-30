<?php

include "connectServer.php";

$tensp = $_POST['tensp'];
$giasp = $_POST['giasp'];
if(isset($_POST['giakhuyenmai']))
    $giakhuyenmai = $_POST['giakhuyenmai'];
else
    $giakhuyenmai = null;
$hinhanh = $_POST['hinhanh'];
$mota = $_POST['mota'];
$loai = $_POST['loai'];
$soluong = $_POST['soluong'];

if($giakhuyenmai != null) {
    $query = "INSERT INTO sanpham(TenSP, GiaSP, GiaKhuyenMai, HinhAnhSP, MoTa, Loai, SoLuongSP) VALUES ('".$tensp."','".$giasp."','".$giakhuyenmai."','".$hinhanh."','".$mota."','".$loai."','".$soluong."')";
    $data = mysqli_query($conn, $query);
}
else {
    $query = "INSERT INTO sanpham(TenSP, GiaSP, GiaKhuyenMai, HinhAnhSP, MoTa, Loai, SoLuongSP) VALUES ('".$tensp."','".$giasp."',NULL,'".$hinhanh."','".$mota."','".$loai."','".$soluong."')";
    $data = mysqli_query($conn, $query);
}

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

print_r(json_encode($arr));

?>