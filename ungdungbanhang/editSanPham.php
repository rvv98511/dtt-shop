<?php

include "connectServer.php";
$idsp = $_POST['idsp'];
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
    $query = "UPDATE sanpham SET TenSP = '".$tensp."', GiaSP = '".$giasp."', GiaKhuyenMai = '".$giakhuyenmai."', HinhAnhSP = '".$hinhanh."', MoTa = '".$mota."', Loai = '".$loai."', SoLuongSP = '".$soluong."' WHERE IdSP = ".$idsp;
    $data = mysqli_query($conn, $query);
}
else {
    $query = "UPDATE sanpham SET TenSP = '".$tensp."', GiaSP = '".$giasp."', GiaKhuyenMai = NULL, HinhAnhSP = '".$hinhanh."', MoTa = '".$mota."', Loai = '".$loai."', SoLuongSP = '".$soluong."' WHERE IdSP = ".$idsp;
    $data = mysqli_query($conn, $query);
}

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