<?php

include "connectServer.php";
$username = $_POST['username'];
$tenkh = $_POST['tenkh'];
$sodienthoai = $_POST['sodienthoai'];
$email = $_POST['email'];
$diachi = $_POST['diachi'];
$tongsoluong = $_POST['tongsoluong'];
$tongtien = $_POST['tongtien'];
$ngaydathang = $_POST['ngaydathang'];
$thanhtoan = $_POST['thanhtoan'];
if(isset($_POST['zalopaytoken']))
    $zalopaytoken = $_POST['zalopaytoken'];
else
    $zalopaytoken = null;
$chitiet = $_POST['chitiet'];

if($zalopaytoken == null) {
    $query = "INSERT INTO donhang (UserName, TenKH, SoDienThoai, Email, DiaChi, TongSoLuong, TongTien, NgayDatHang, ThanhToan, ZaloPayToken) VALUES ('".$username."','".$tenkh."','".$sodienthoai."','".$email."','".$diachi."','".$tongsoluong."','".$tongtien."','".$ngaydathang."','".$thanhtoan."',NULL)";
    $data = mysqli_query($conn, $query);
}
else {
    $query = "INSERT INTO donhang (UserName, TenKH, SoDienThoai, Email, DiaChi, TongSoLuong, TongTien, NgayDatHang, ThanhToan, ZaloPayToken) VALUES ('".$username."','".$tenkh."','".$sodienthoai."','".$email."','".$diachi."','".$tongsoluong."','".$tongtien."','".$ngaydathang."','".$thanhtoan."','".$zalopaytoken."')";
    $data = mysqli_query($conn, $query);
}

if($data == true) {
    $query = "SELECT IdDonHang FROM donhang WHERE UserName = '".$username."' ORDER BY IdDonHang DESC LIMIT 1";
    $data = mysqli_query($conn, $query);

    while($row = mysqli_fetch_assoc($data)) {
        $iddonhang = $row;
    }

    if(!empty($iddonhang)) {
        $chitiet = json_decode($chitiet, true);
        foreach($chitiet as $key => $value) {
            $query = "INSERT INTO chitietdonhang (IdDonHang, IdSP, GiaSP, GiaKhuyenMai, SoLuongMua) VALUES ('".$iddonhang['IdDonHang']."','".$value['idSP']."','".$value['giaSP']."','".$value['giaKhuyenMai']."','".$value['soLuongMua']."')";
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
    }
}
else {
    $arr = [
        "success" => false,
        "message" => "Khong thanh cong"
    ];
}

print_r(json_encode($arr));

?>