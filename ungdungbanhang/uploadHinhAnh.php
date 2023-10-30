<?php

include "connectServer.php";
$target_dir = "images/sanpham/";
$fileName  =  $_FILES['file']['name'];
$tempPath  =  $_FILES['file']['tmp_name'];

$fileExt = strtolower(pathinfo($fileName, PATHINFO_EXTENSION));

$valid_extensions = array("jpeg", "jpg", "png"); 

$target_file_name = $target_dir . basename($fileName);

if (isset($_FILES["file"])) {
    if(in_array($fileExt, $valid_extensions))
    {
        if (move_uploaded_file($tempPath, $target_file_name)) {
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
    else {
        $arr = [
            "success" => false,
            "message" => "Loi khong phai hinh anh"
        ];
    }
}
else {
    $arr = [
        "success" => false,
        "message" => "Loi"
    ];
}

print_r(json_encode($arr));

?>