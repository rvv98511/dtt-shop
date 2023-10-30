<?php

include "connectServer.php";

$query = "SELECT chitietdonhang.IdSP, sanpham.TenSP, SUM(SoLuongMua) AS TongSoLuongMua FROM chitietdonhang INNER JOIN sanpham INNER JOIN donhang ON chitietdonhang.IdSP = sanpham.IdSP AND chitietdonhang.IdDonHang = donhang.IdDonHang WHERE donhang.TrangThai != 4 GROUP BY IdSP";
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