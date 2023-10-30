<?php

include "connectServer.php";
$searchtext = $_POST['searchtext'];
if(isset($_POST['searchtheoloai']))
    $searchtheoloai = $_POST['searchtheoloai'];
else
    $searchtheoloai = null;
$searchtheogia = $_POST['searchtheogia'];

if(empty($searchtext)) {
    if($searchtheoloai != null && $searchtheogia == 0) {
        $query = "SELECT * FROM `sanpham` WHERE Loai = '".$searchtheoloai."'";
        $data = mysqli_query($conn, $query);
        $result = array();

        while($row = mysqli_fetch_assoc($data)) {
            $result[] = $row;
        }
    }
    else if($searchtheoloai == null && $searchtheogia != 0) {
        if($searchtheogia == 1) {
            $query = "SELECT * FROM `sanpham` WHERE GiaSP < 10000000 OR GiaKhuyenMai < 10000000";
            $data = mysqli_query($conn, $query);
            $result = array();

            while($row = mysqli_fetch_assoc($data)) {
                $result[] = $row;
            }
        }
        else if($searchtheogia == 2) {
            $query = "SELECT * FROM `sanpham` WHERE (GiaSP <= 20000000 AND GiaSP >= 10000000) OR (GiaKhuyenMai <= 20000000 AND GiaKhuyenMai >= 10000000)";
            $data = mysqli_query($conn, $query);
            $result = array();

            while($row = mysqli_fetch_assoc($data)) {
                $result[] = $row;
            }
        }
        else if($searchtheogia == 3) {
            $query = "SELECT * FROM `sanpham` WHERE GiaSP > 20000000 OR GiaKhuyenMai > 20000000";
            $data = mysqli_query($conn, $query);
            $result = array();

            while($row = mysqli_fetch_assoc($data)) {
                $result[] = $row;
            }
        }
    }
    else if($searchtheoloai != null && $searchtheogia != 0) {
        if($searchtheogia == 1) {
            $query = "SELECT * FROM `sanpham` WHERE `TenSP` LIKE '%".$searchtext."%' AND Loai = '".$searchtheoloai."' AND (GiaSP < 10000000 OR GiaKhuyenMai < 10000000)";
            $data = mysqli_query($conn, $query);
            $result = array();

            while($row = mysqli_fetch_assoc($data)) {
                $result[] = $row;
            }
        }
        else if($searchtheogia == 2) {
            $query = "SELECT * FROM `sanpham` WHERE `TenSP` LIKE '%".$searchtext."%' AND Loai = '".$searchtheoloai."' AND ((GiaSP <= 20000000 AND GiaSP >= 10000000) OR (GiaKhuyenMai <= 20000000 AND GiaKhuyenMai >= 10000000))";
            $data = mysqli_query($conn, $query);
            $result = array();

            while($row = mysqli_fetch_assoc($data)) {
                $result[] = $row;
            }
        }
        else if($searchtheogia == 3) {
            $query = "SELECT * FROM `sanpham` WHERE `TenSP` LIKE '%".$searchtext."%' AND Loai = '".$searchtheoloai."' AND (GiaSP > 20000000 OR GiaKhuyenMai > 20000000)";
            $data = mysqli_query($conn, $query);
            $result = array();

            while($row = mysqli_fetch_assoc($data)) {
                $result[] = $row;
            }
        }
    }
}
else {
    if($searchtheoloai == null && $searchtheogia == 0) {
        $query = "SELECT * FROM `sanpham` WHERE `TenSP` LIKE '%".$searchtext."%'";
        $data = mysqli_query($conn, $query);
        $result = array();

        while($row = mysqli_fetch_assoc($data)) {
            $result[] = $row;
        }
    }
    else if($searchtheoloai != null && $searchtheogia == 0) {
        $query = "SELECT * FROM `sanpham` WHERE `TenSP` LIKE '%".$searchtext."%' AND Loai = '".$searchtheoloai."'";
        $data = mysqli_query($conn, $query);
        $result = array();

        while($row = mysqli_fetch_assoc($data)) {
            $result[] = $row;
        }
    }
    else if($searchtheoloai != null && $searchtheogia != 0) {
        if($searchtheogia == 1) {
            $query = "SELECT * FROM `sanpham` WHERE `TenSP` LIKE '%".$searchtext."%' AND Loai = '".$searchtheoloai."' AND (GiaSP < 10000000 OR GiaKhuyenMai < 10000000)";
            $data = mysqli_query($conn, $query);
            $result = array();

            while($row = mysqli_fetch_assoc($data)) {
                $result[] = $row;
            }
        }
        else if($searchtheogia == 2) {
            $query = "SELECT * FROM `sanpham` WHERE `TenSP` LIKE '%".$searchtext."%' AND Loai = '".$searchtheoloai."' AND ((GiaSP <= 20000000 AND GiaSP >= 10000000) OR (GiaKhuyenMai <= 20000000 AND GiaKhuyenMai >= 10000000))";
            $data = mysqli_query($conn, $query);
            $result = array();

            while($row = mysqli_fetch_assoc($data)) {
                $result[] = $row;
            }
        }
        else if($searchtheogia == 3) {
            $query = "SELECT * FROM `sanpham` WHERE `TenSP` LIKE '%".$searchtext."%' AND Loai = '".$searchtheoloai."' AND (GiaSP > 20000000 OR GiaKhuyenMai > 20000000)";
            $data = mysqli_query($conn, $query);
            $result = array();

            while($row = mysqli_fetch_assoc($data)) {
                $result[] = $row;
            }
        }
    }
    else if($searchtheoloai == null && $searchtheogia != 0) {
        if($searchtheogia == 1) {
            $query = "SELECT * FROM `sanpham` WHERE `TenSP` LIKE '%".$searchtext."%' AND (GiaSP < 10000000 OR GiaKhuyenMai < 10000000)";
            $data = mysqli_query($conn, $query);
            $result = array();

            while($row = mysqli_fetch_assoc($data)) {
                $result[] = $row;
            }
        }
        else if($searchtheogia == 2) {
            $query = "SELECT * FROM `sanpham` WHERE `TenSP` LIKE '%".$searchtext."%' AND ((GiaSP <= 20000000 AND GiaSP >= 10000000) OR (GiaKhuyenMai <= 20000000 AND GiaKhuyenMai >= 10000000))";
            $data = mysqli_query($conn, $query);
            $result = array();

            while($row = mysqli_fetch_assoc($data)) {
                $result[] = $row;
            }
        }
        else if($searchtheogia == 3) {
            $query = "SELECT * FROM `sanpham` WHERE `TenSP` LIKE '%".$searchtext."%' AND (GiaSP > 20000000 OR GiaKhuyenMai > 20000000)";
            $data = mysqli_query($conn, $query);
            $result = array();

            while($row = mysqli_fetch_assoc($data)) {
                $result[] = $row;
            }
        }
    }
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
        "message" => "Khong thanh cong"
    ];
}

print_r(json_encode($arr));

?>