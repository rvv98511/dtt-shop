<?php

include "connectServer.php";

$query = "SELECT IdDonHang, TenKH, DiaChi, TongSoLuong, TongTien, NgayDatHang, ThanhToan, TrangThai FROM donhang ORDER BY IdDonHang DESC";
$data1 = mysqli_query($conn, $query);
$result1 = array();

while($row1 = mysqli_fetch_assoc($data1)) {
    $query = "SELECT sanpham.TenSP, chitietdonhang.GiaSP, chitietdonhang.GiaKhuyenMai, sanpham.HinhAnhSP, chitietdonhang.SoLuongMua FROM chitietdonhang INNER JOIN sanpham ON chitietdonhang.IdSP = sanpham.IdSP WHERE IdDonHang = ".$row1['IdDonHang'];
    $data2 = mysqli_query($conn, $query);

    $result2 = array();
    while($row2 = mysqli_fetch_assoc($data2)) {
        $result2[] = $row2;
    }

    $row1['ChiTiet'] = $result2;
    $result1[] = $row1;
}

if(!empty($result1)) {
    $arr = [
        "success" => true,
        "message" => "Thanh cong",
        "result" => $result1
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