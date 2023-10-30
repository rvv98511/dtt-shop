<?php

include "connectServer.php";
$username = $_POST['username'];

$query = "SELECT IdDonHang FROM donhang WHERE UserName = '".$username."' ORDER BY IdDonHang DESC LIMIT 0, 1";
$data1 = mysqli_query($conn, $query);

while($row1 = mysqli_fetch_assoc($data1)) {
    $query = "SELECT chitietdonhang.IdSP, sanpham.SoLuongSP, chitietdonhang.SoLuongMua FROM chitietdonhang INNER JOIN sanpham ON chitietdonhang.IdSP = sanpham.IdSP WHERE IdDonHang = ".$row1['IdDonHang'];
    $data2 = mysqli_query($conn, $query);

    $result = array();
    while($row2 = mysqli_fetch_assoc($data2)) {
        $result[] = $row2;
    }

    foreach($result as $key => $value) {
        $capnhatsoluong = $value['SoLuongSP'] - $value['SoLuongMua'];
        $query = "UPDATE sanpham SET SoLuongSP = '".$capnhatsoluong."' WHERE IdSP = '".$value['IdSP']."'";
        $data3 = mysqli_query($conn, $query);
    }

    if($data3 == true) {
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

print_r(json_encode($arr));

?>