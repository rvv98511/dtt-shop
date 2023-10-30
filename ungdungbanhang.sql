-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th8 09, 2023 lúc 03:38 PM
-- Phiên bản máy phục vụ: 10.4.24-MariaDB
-- Phiên bản PHP: 7.4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `ungdungbanhang`
--
CREATE DATABASE IF NOT EXISTS `ungdungbanhang` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `ungdungbanhang`;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitietdonhang`
--

CREATE TABLE `chitietdonhang` (
  `IdDonHang` int(10) NOT NULL,
  `IdSP` int(10) NOT NULL,
  `GiaSP` varchar(100) NOT NULL,
  `GiaKhuyenMai` varchar(100) DEFAULT NULL,
  `SoLuongMua` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `chitietdonhang`
--

INSERT INTO `chitietdonhang` (`IdDonHang`, `IdSP`, `GiaSP`, `GiaKhuyenMai`, `SoLuongMua`) VALUES
(1, 30, '13980000', '11980000', 2),
(1, 3, '5690000', '0', 1),
(2, 18, '16290000', '0', 1),
(3, 16, '42980000', '35180000', 2),
(4, 23, '16990000', '15490000', 1),
(5, 18, '16290000', '0', 1),
(5, 30, '6990000', '5990000', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `danhsachmenu`
--

CREATE TABLE `danhsachmenu` (
  `Id` int(10) NOT NULL,
  `TenDanhSach` varchar(200) NOT NULL,
  `HinhAnhDanhSach` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `danhsachmenu`
--

INSERT INTO `danhsachmenu` (`Id`, `TenDanhSach`, `HinhAnhDanhSach`) VALUES
(1, 'Trang chủ', 'bieu-tuong-trang-chu.png'),
(2, 'Điện thoại', 'bieu-tuong-san-pham-mobile.png'),
(3, 'Laptop', 'bieu-tuong-san-pham-laptop.png'),
(4, 'Máy tính bảng', 'bieu-tuong-san-pham-may-tinh-bang.png'),
(5, 'Thông tin tài khoản', 'bieu-tuong-thong-tin-tai-khoan.png'),
(6, 'Lịch sử mua hàng', 'bieu-tuong-lich-su-mua-hang.png'),
(7, 'Đăng xuất', 'bieu-tuong-dang-xuat.png'),
(8, 'Liên hệ', 'bieu-tuong-lien-he.png'),
(9, 'Thông tin ứng dụng', 'bieu-tuong-thong-tin-ung-dung.png');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `danhsachmenumanager`
--

CREATE TABLE `danhsachmenumanager` (
  `Id` int(10) NOT NULL,
  `TenDanhSach` varchar(200) NOT NULL,
  `HinhAnhDanhSach` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `danhsachmenumanager`
--

INSERT INTO `danhsachmenumanager` (`Id`, `TenDanhSach`, `HinhAnhDanhSach`) VALUES
(1, 'Trang chủ', 'bieu-tuong-trang-chu.png'),
(2, 'Điện thoại', 'bieu-tuong-san-pham-mobile.png'),
(3, 'Laptop', 'bieu-tuong-san-pham-laptop.png'),
(4, 'Máy tính bảng', 'bieu-tuong-san-pham-may-tinh-bang.png'),
(5, 'Quản lý sản phẩm', 'bieu-tuong-quan-ly-san-pham.png'),
(6, 'Quản lý đơn hàng', 'bieu-tuong-quan-ly-don-hang.png'),
(7, 'Thống kê', 'bieu-tuong-thong-ke.png'),
(8, 'Đổi mật khẩu', 'bieu-tuong-doi-mat-khau.png'),
(9, 'Đăng xuất', 'bieu-tuong-dang-xuat.png'),
(10, 'Thông tin ứng dụng', 'bieu-tuong-thong-tin-ung-dung.png');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `donhang`
--

CREATE TABLE `donhang` (
  `IdDonHang` int(10) NOT NULL,
  `UserName` varchar(100) NOT NULL,
  `TenKH` varchar(200) NOT NULL,
  `SoDienThoai` varchar(10) NOT NULL,
  `Email` varchar(200) NOT NULL,
  `DiaChi` varchar(200) NOT NULL,
  `TongSoLuong` int(2) NOT NULL,
  `TongTien` varchar(100) NOT NULL,
  `NgayDatHang` date NOT NULL,
  `ThanhToan` varchar(20) NOT NULL,
  `TrangThai` int(1) NOT NULL DEFAULT 0,
  `ZaloPayToken` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `donhang`
--

INSERT INTO `donhang` (`IdDonHang`, `UserName`, `TenKH`, `SoDienThoai`, `Email`, `DiaChi`, `TongSoLuong`, `TongTien`, `NgayDatHang`, `ThanhToan`, `TrangThai`, `ZaloPayToken`) VALUES
(1, 'thu123', 'Lê Thị Thu', '0379230194', 'lethithu@gmail.com', '76 Nguyễn Tri Phương phường 04 quận 10 TP.HCM', 3, '17670000', '2023-05-24', 'Tiền mặt', 0, NULL),
(2, 'thu123', 'Lê Thị Thu', '0379230194', 'lethithu@gmail.com', '76 Nguyễn Tri Phương phường 04 quận 10 TP.HCM', 1, '16290000', '2023-05-28', 'Chuyển khoản ZaloPay', 1, 'AC5EUxJ94laZdFugR55A-TNA'),
(3, 'luan123', 'Trần Quốc Luân', '0355219012', 'tranquocluan@gmail.com', '46 Nguyễn Trãi phường 11 quận 5 TP.HCM', 2, '35180000', '2023-06-24', 'Chuyển khoản ZaloPay', 0, 'ACkcujmZMXhS_CzoBb6JUDqQ'),
(4, 'luan123', 'Trần Quốc Luân', '0355219012', 'tranquocluan@gmail.com', '46 Nguyễn Trãi phường 11 quận 5 TP.HCM', 1, '15490000', '2023-07-12', 'Tiền mặt', 0, NULL),
(5, 'luan123', 'Trần Quốc Luân', '0355219012', 'tranquocluan@gmail.com', '46 Nguyễn Trãi phường 11 quận 5 TP.HCM', 2, '22280000', '2023-08-01', 'Chuyển khoản ZaloPay', 0, 'ACEoiKda8Ci7yrWYx_QcE6uA');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `manager`
--

CREATE TABLE `manager` (
  `UserName` varchar(100) NOT NULL,
  `MatKhau` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `manager`
--

INSERT INTO `manager` (`UserName`, `MatKhau`) VALUES
('manager', '12345678');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sanpham`
--

CREATE TABLE `sanpham` (
  `IdSP` int(10) NOT NULL,
  `TenSP` varchar(200) NOT NULL,
  `GiaSP` varchar(100) NOT NULL,
  `GiaKhuyenMai` varchar(100) DEFAULT NULL,
  `HinhAnhSP` varchar(200) NOT NULL,
  `MoTa` varchar(800) NOT NULL,
  `Loai` varchar(5) NOT NULL,
  `SoLuongSP` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `sanpham`
--

INSERT INTO `sanpham` (`IdSP`, `TenSP`, `GiaSP`, `GiaKhuyenMai`, `HinhAnhSP`, `MoTa`, `Loai`, `SoLuongSP`) VALUES
(1, 'Samsung Galaxy Z Flip4 5G', '23990000', '18990000', 'DT01.jpg', 'Màn hình: Chính: Dynamic AMOLED 2X, phụ: Super AMOLED, chính 6.7\" & Phụ 1.9\", Full HD+\nHệ điều hành: Android 12\nCamera sau: 2 camera 12 MP\nCamera trước: 10 MP\nChip: Snapdragon 8+ Gen 1\nRAM: 8 GB\nDung lượng lưu trữ: 128 GB\nSIM: 1 Nano SIM & 1 eSIM, hỗ trợ 5G\nPin, Sạc: 3700 mAh, 25 W', 'DT', 180),
(2, 'Samsung Galaxy S22 Ultra 5G', '30990000', '23990000', 'DT02.jpg', 'Màn hình: Dynamic AMOLED 2X6.8\", Quad HD+ (2K+)\nHệ điều hành: Android 12\nCamera sau: Chính 108 MP & Phụ 12 MP, 10 MP, 10 MP\nCamera trước: 40 MP\nChip: Snapdragon 8 Gen 1\nRAM: 8 GB\nDung lượng lưu trữ: 128 GB\nSIM: 2 Nano SIM hoặc 1 Nano SIM + 1 eSIM, hỗ trợ 5G\nPin, Sạc: 5000 mAh, 45 W', 'DT', 0),
(3, 'Samsung Galaxy A23', '5690000', NULL, 'DT03.jpg', 'Màn hình: PLS TFT LCD, 6.6\", Full HD+\r\nHệ điều hành: Android 12\r\nCamera sau: Chính 50 MP & Phụ 5 MP, 2 MP, 2 MP\r\nCamera trước: 8 MP\r\nChip: Snapdragon 680\r\nRAM: 4 GB\r\nDung lượng lưu trữ: 128 GB\r\nSIM: 2 Nano SIM, hỗ trợ 4G\r\nPin, Sạc: 5000 mAh, 25 W', 'DT', 198),
(4, 'Samsung Galaxy A73 5G Awesome Edition', '12690000', '10990000', 'DT04.jpg', 'Màn hình: Super AMOLED Plus, 6.7\", Full HD+\r\nHệ điều hành: Android 12\r\nCamera sau: Chính 108 MP & Phụ 12 MP, 5 MP, 5 MP\r\nCamera trước: 32 MP\r\nChip: Snapdragon 778G 5G\r\nRAM: 8 GB\r\nDung lượng lưu trữ: 128 GB\r\nSIM: 2 Nano SIM (SIM 2 chung khe thẻ nhớ), hỗ trợ 5G\r\nPin, Sạc: 5000 mAh, 25 W', 'DT', 200),
(5, 'iPhone 14 Pro Max', '34490000', '29490000', 'DT05.jpg', 'Màn hình: OLED, 6.7\", Super Retina XDR\r\nHệ điều hành: iOS 16\r\nCamera sau: Chính 48 MP & Phụ 12 MP, 12 MP\r\nCamera trước: 12 MP\r\nChip: Apple A16 Bionic\r\nRAM: 6 GB\r\nDung lượng lưu trữ: 128 GB\r\nSIM: 1 Nano SIM & 1 eSIM, hỗ trợ 5G\r\nPin, Sạc: 4323 mAh, 20 W', 'DT', 40),
(6, 'iPhone 11', '14990000', '12490000', 'DT06.jpg', 'Màn hình: IPS LCD, 6.1\", Liquid Retina\r\nHệ điều hành: iOS 15\r\nCamera sau: 2 camera 12 MP\r\nCamera trước: 12 MP\r\nChip: Apple A13 Bionic\r\nRAM: 4 GB\r\nDung lượng lưu trữ: 64 GB\r\nSIM: 1 Nano SIM & 1 eSIM, hỗ trợ 4G\r\nPin, Sạc: 3110 mAh, 18 W', 'DT', 112),
(7, 'iPhone 13 Pro Max', '34990000', '30990000', 'DT07.jpg', 'Màn hình: OLED, 6.7\", Super Retina XDR\r\nHệ điều hành: iOS 15\r\nCamera sau: 3 camera 12 MP\r\nCamera trước: 12 MP\r\nChip: Apple A15 Bionic\r\nRAM: 6 GB\r\nDung lượng lưu trữ: 512 GB\r\nSIM: 1 Nano SIM & 1 eSIM, hỗ trợ 5G\r\nPin, Sạc: 4352 mAh, 20 W', 'DT', 78),
(8, 'Vivo Y35', '6990000', '6670000', 'DT08.jpg', 'Màn hình: IPS LCD, 6.58\", Full HD+\r\nHệ điều hành: Android 12\r\nCamera sau: Chính 50 MP & Phụ 2 MP, 2 MP\r\nCamera trước: 16 MP\r\nChip: Snapdragon 680\r\nRAM: 8 GB\r\nDung lượng lưu trữ: 128 GB\r\nSIM: 2 Nano SIM, hỗ trợ 4G\r\nPin, Sạc: 5000 mAh, 44 W', 'DT', 97),
(9, 'Vivo Y02s', '3490000', NULL, 'DT09.jpg', 'Màn hình: IPS LCD, 6.51\", HD+\r\nHệ điều hành: Android 12\r\nCamera sau: 8 MP\r\nCamera trước: 5 MP\r\nChip: MediaTek Helio P35\r\nRAM: 3 GB\r\nDung lượng lưu trữ: 32 GB\r\nSIM: 2 Nano SIM, hỗ trợ 4G\r\nPin, Sạc: 5000 mAh, 10 W', 'DT', 179),
(10, 'Vivo V25 Pro 5G', '13990000', '13490000', 'DT10.jpg', 'Màn hình: AMOLED, 6.56\", Full HD+\r\nHệ điều hành: Android 12\r\nCamera sau: \r\nChính 64 MP & Phụ 8 MP, 2 MP\r\nCamera trước: 32 MP\r\nChip: MediaTek Dimensity 1300\r\nRAM: 8 GB\r\nDung lượng lưu trữ: 128 GB\r\nSIM: 2 Nano SIM, hỗ trợ 5G\r\nPin, Sạc: 4830 mAh, 66 W', 'DT', 126),
(11, 'Dell Vostro 3510 i5 1135G7', '22990000', '18790000', 'LT01.jpg', 'CPU: i5, 1135G7, 2.4GHz\r\nRAM: 8 GB, DDR4 2 khe (1 khe 8 GB + 1 khe rời), 3200 MHz\r\nỔ cứng: 512 GB SSD NVMe PCIe (Có thể tháo ra, lắp thanh khác tối đa 2 TB (2280) / 1 TB (2230)), hỗ trợ khe cắm HDD SATA (nâng cấp tối đa 2 TB)\r\nMàn hình: 15.6\", Full HD (1920 x 1080)\r\nCard màn hình: Card rời, MX350 2GB\r\nCổng kết nối: HDMILAN (RJ45), USB 2.0, Jack tai nghe 3.5 mm, 2 x USB 3.2 / 1 x USB 3.2 và 1 x USB Type-C (Tuỳ thuộc vào lô hàng)\r\nHệ điều hành: Windows 11 Home SL + Office Home & Student vĩnh viễn\r\nThiết kế: Vỏ nhựa\r\nKích thước, khối lượng: Dài 358.5 mm - Rộng 235.5 mm - Dày 18.9 mm - Nặng 1.69 kg', 'LT', 143),
(12, 'Dell Vostro 5410 i5 11320H', '23090000', NULL, 'LT02.jpg', 'CPU: i5, 11320H, 3.2GHz\r\nRAM: 8 GBDDR4 2 khe (1 khe 8 GB + 1 khe rời), 3200 MHz\r\nỔ cứng: 512 GB SSD NVMe PCIe (Có thể tháo ra, lắp thanh khác tối đa 1 TB)\r\nMàn hình: 14\", Full HD (1920 x 1080)\r\nCard màn hình: Card tích hợp, Intel Iris Xe\r\nCổng kết nối: USB Type-C, HDMI, LAN (RJ45), Jack tai nghe 3.5 mm, 2 x USB 3.2\r\nĐặc biệt: Có đèn bàn phím\r\nHệ điều hành: Windows 11 Home SL + Office Home & Student vĩnh viễn\r\nThiết kế: Vỏ nhựa - nắp lưng bằng kim loại\r\nKích thước, khối lượng: Dài 321.2 mm - Rộng 212.8 mm - Dày 17.9 mm - Nặng 1.44 kg', 'LT', 0),
(13, 'Dell Inspiron 16 5620 i7 1255U', '25190000', '24490000', 'LT03.jpg', 'CPU: i7, 1255U, 1.7GHz\r\nRAM: 8 GB, DDR4 2 khe (1 khe 8 GB + 1 khe rời), 3200 MHz\r\nỔ cứng: 512 GB SSD NVMe PCIe\r\nMàn hình: 16\", Full HD+ (1920 x 1200)\r\nCard màn hình: Card tích hợp, Intel UHD\r\nCổng kết nối: HDMI, Jack tai nghe 3.5 mm, 2 x USB 3.2, USB Type-C (Power Delivery and DisplayPort)\r\nĐặc biệt: Có đèn bàn phím\r\nHệ điều hành: Windows 11 Home SL + Office Home & Student vĩnh viễn\r\nThiết kế: Vỏ kim loại\r\nKích thước, khối lượng: Dài 356.7 mm - Rộng 251.9 mm - Dày 17.95 mm - Nặng 1.87 kg', 'LT', 200),
(14, 'Dell Inspiron 15 3511 i3 1115G4', '12690000', '11490000', 'LT04.jpg', 'CPU: i3, 1115G4, 3GHz\r\nRAM: 4 GB, DDR4 2 khe (1 khe 4 GB + 1 khe rời), 2666 MHz\r\nỔ cứng: Hỗ trợ khe cắm HDD SATA (nâng cấp tối đa 2 TB), 256 GB SSD NVMe PCIe (Có thể tháo ra, lắp thanh khác tối đa 2 TB (2280) / 1 TB (2230))\r\nMàn hình: 15.6\", Full HD (1920 x 1080)\r\nCard màn hình: Card tích hợp, Intel UHD\r\nCổng kết nối: HDMI, USB 2.0, Jack tai nghe 3.5 mm, 2 x USB 3.2\r\nHệ điều hành: Windows 11 Home SL + Office Home & Student vĩnh viễn\r\nThiết kế: Vỏ nhựa\r\nKích thước, khối lượng: Dài 358.5 mm - Rộng 235.5 mm - Dày 18.9 mm - Nặng 1.7 kg', 'LT', 56),
(15, 'Asus TUF Gaming F15 FX506LHB i5 10300H', '20990000', '16190000', 'LT05.jpg', 'CPU: i5, 10300H, 2.5GHz\r\nRAM: 8 GB, DDR4 2 khe (1 khe 8 GB + 1 khe rời), 2933 MHz\r\nỔ cứng: 512 GB SSD NVMe PCIe (Có thể tháo ra, lắp thanh khác tối đa 1 TB)\r\nMàn hình: 15.6\", Full HD (1920 x 1080), 144Hz\r\nCard màn hình: Card rời, GTX 1650 4GB\r\nCổng kết nối: HDMI, LAN (RJ45), USB 2.0, Jack tai nghe 3.5 mm, 2 x USB 3.2, 1 x USB 3.2 Gen 2 Type-C (hỗ trợ DisplayPort, Power delivery, G-SYNC)\r\nĐặc biệt: Có đèn bàn phím\r\nHệ điều hành: Windows 11 Home SL\r\nThiết kế: Vỏ nhựa\r\nKích thước, khối lượng: Dài 359 mm - Rộng 256 mm - Dày 24.9 mm - Nặng 2.3 kg', 'LT', 87),
(16, 'Asus VivoBook 15X OLED A1503ZA i5 12500H', '21490000', '17590000', 'LT06.jpg', 'CPU: i5, 12500H, 2.5GHz\r\nRAM: 8 GB, DDR4 2 khe (1 khe 8 GB onboard + 1 khe trống), 3200 MHz\r\nỔ cứng: 512 GB SSD NVMe PCIe (Có thể tháo ra, lắp thanh khác không giới hạn dung lượng)\r\nMàn hình: 15.6\", Full HD (1920 x 1080) OLED\r\nCard màn hình: Card tích hợp, Intel UHD\r\nCổng kết nối: USB Type-CHDMI, Jack tai nghe 3.5 mm, 2 x USB 3.2, 1 x USB 2.0\r\nĐặc biệt: Có đèn bàn phím\r\nHệ điều hành: Windows 11 Home SL\r\nThiết kế: Vỏ nhựa\r\nKích thước, khối lượng: Dài 356.8 mm - Rộng 227.6 mm - Dày 19.9 mm - Nặng 1.7 kg', 'LT', 193),
(17, 'Asus Gaming TUF Dash F15 FX517ZC i5 12450H', '28990000', '23890000', 'LT07.jpg', 'CPU: i5, 12450H, 2GHz\r\nRAM: 8 GB, DDR5 2 khe (1 khe 8 GB + 1 khe trống), 4800 MHz\r\nỔ cứng: 512 GB SSD NVMe PCIe (Có thể tháo ra, lắp thanh khác tối đa 1 TB), hỗ trợ thêm 1 khe cắm SSD M.2 PCIe mở rộng (nâng cấp tối đa 1 TB)\r\nMàn hình: 15.6\", Full HD (1920 x 1080), 144Hz\r\nCard màn hình: Card rời, RTX 3050 4GB\r\nCổng kết nối: Thunderbolt 4, HDMI, LAN (RJ45), Jack tai nghe 3.5 mm, 2 x USB 3.2, 1 x USB 3.2 Gen 2 Type-C (hỗ trợ DisplayPort, Power delivery, G-SYNC)\r\nĐặc biệt: Có đèn bàn phím\r\nHệ điều hành: Windows 11 Home SL\r\nThiết kế: Vỏ nhựa\r\nKích thước, khối lượng: Dài 354 mm - Rộng 251 mm - Dày 20.7 mm - Nặng 2 kg', 'LT', 200),
(18, 'Lenovo Ideapad 5 15IAL7 i5 1235U', '16290000', NULL, 'LT08.jpg', 'CPU: i5, 1235U, 1.3GHz\r\nRAM: 8 GB, DDR4 (Onboard), 3200 MHz\r\nỔ cứng: 512 GB SSD NVMe PCIe (Có thể tháo ra, lắp thanh khác tối đa 1 TB (2280) / 512 GB (2242)), hỗ trợ khe cắm HDD SATA 2.5 inch mở rộng (nâng cấp tối đa 1 TB)\r\nMàn hình: 15.6\", Full HD (1920 x 1080)\r\nCard màn hình: Card tích hợp, Intel Iris Xe\r\nCổng kết nối: HDMI, 1 x USB 3.2 (Always on), Jack tai nghe 3.5 mm, 1 x USB 3.2, 2 x USB Type-C 3.2 (hỗ trợ truyền dữ liệu, Power Delivery 3.0, and DisplayPort 1.2)\r\nĐặc biệt: Có đèn bàn phím\r\nHệ điều hành: Windows 11 Home SL\r\nThiết kế: Vỏ kim loại\r\nKích thước, khối lượng: Dài 356.67 mm - Rộng 233.13 mm - Dày 16.9 mm - Nặng 1.85 kg', 'LT', 66),
(19, 'Lenovo Ideapad Gaming 3 15IAH7 i5 12500H', '26990000', '20990000', 'LT09.jpg', 'CPU: i5, 12500H, 2.5GHz\r\nRAM: 8 GB, DDR4 2 khe (1 khe 8 GB + 1 khe rời), 3200 MHz\r\nỔ cứng: 512 GB SSD NVMe PCIe (Có thể tháo ra, lắp thanh khác tối đa 1 TB)\r\nMàn hình: 15.6\", Full HD (1920 x 1080), 120Hz\r\nCard màn hình: Card rời, RTX 3050 4GB\r\nCổng kết nối: 1 x Thunderbolt 4 (hỗ trợ Power Delivery 3.0 và DisplayPort 1.4), HDMI, LAN (RJ45), Jack tai nghe 3.5 mm, 2 x USB 3.2\r\nĐặc biệt: Có đèn bàn phím\r\nHệ điều hành: Windows 11 Home SL\r\nThiết kế: Vỏ nhựa\r\nKích thước, khối lượng: Dài 359.6 mm - Rộng 266.4 mm - Dày 21.8 mm - Nặng 2.315 kg', 'LT', 200),
(20, 'Lenovo Ideapad Gaming 3 15IHU6 i5 11320H', '20790000', '17490000', 'LT10.jpg', 'CPU: i5, 11320H, 3.2GHz\nRAM: 8 GB, DDR4 2 khe (1 khe 8 GB + 1 khe rời), 3200 MHz\nỔ cứng: 512 GB SSD NVMe PCIe (Có thể tháo ra, lắp thanh khác tối đa 1 TB (2280) / 512 GB (2242)), hỗ trợ khe cắm HDD SATA 2.5 inch mở rộng (nâng cấp tối đa 1 TB)\nMàn hình: 15.6\", Full HD (1920 x 1080), 120Hz\nCard màn hình: Card rời, GTX 1650 4GB\nCổng kết nối: HDMI, LAN (RJ45), Jack tai nghe 3.5 mm, 2 x USB 3.2, 1 x USB Type-C (chỉ hỗ trợ truyền dữ liệu)\nĐặc biệt: Có đèn bàn phím\nHệ điều hành: Windows 11 Home SL\nThiết kế: Vỏ nhựa\nKích thước, khối lượng: Dài 359.6 mm - Rộng 251.9 mm - Dày 24.2 mm - Nặng 2.25 kg', 'LT', 31),
(21, 'iPad 9 WiFi', '9990000', '8490000', 'MTB01.jpg', 'Màn hình: 10.2\", Retina IPS LCD\r\nHệ điều hành: iPadOS 15\r\nChip: Apple A13 Bionic\r\nRAM: 3 GB\r\nDung lượng lưu trữ: 64 GB\r\nKết nối: Nghe gọi qua FaceTime\r\nCamera sau: 8 MP\r\nCamera trước: 12 MP\r\nPin, Sạc: 32.4 Wh (~ 8600 mAh), 20 W', 'MTB', 70),
(22, 'iPad 10 WiFi', '12990000', '12490000', 'MTB02.jpg', 'Màn hình: 10.9\", Retina IPS LCD\r\nHệ điều hành: iPadOS 16\r\nChip: Apple A14 Bionic\r\nRAM: 4 GB\r\nDung lượng lưu trữ: 64 GB\r\nKết nối: Nghe gọi qua FaceTime\r\nCamera sau: 12 MP\r\nCamera trước: 12 MP\r\nPin, Sạc: 28.6 Wh (~ 7587 mAh), 20 W', 'MTB', 0),
(23, 'iPad Air 5 M1 WiFi', '16990000', '15490000', 'MTB03.jpg', 'Màn hình: 10.9\", Retina IPS LCD\r\nHệ điều hành: iPadOS 15\r\nChip: Apple M1\r\nRAM: 8 GB\r\nDung lượng lưu trữ: 64 GB\r\nKết nối: Nghe gọi qua FaceTime\r\nCamera sau: 12 MP\r\nCamera trước: 12 MP\r\nPin, Sạc: 28.6 Wh (~ 7587 mAh), 20 W', 'MTB', 199),
(24, 'Lenovo Tab P11 Plus', '8190000', '6190000', 'MTB04.jpg', 'Màn hình: 11\", IPS LCD\r\nHệ điều hành: Android 11\r\nChip: MediaTek Helio G90T\r\nRAM: 4 GB\r\nDung lượng lưu trữ: 64 GB\r\nKết nối: Hỗ trợ 4G, có nghe gọi\r\nSIM: 1 Nano SIM\r\nCamera sau: 13 MP\r\nCamera trước: 8 MP\r\nPin, Sạc: 7700 mAh, 20 W', 'MTB', 192),
(25, 'Lenovo Tab M10', '5190000', '4590000', 'MTB05.jpg', 'Màn hình: 10.1\", IPS LCD\r\nHệ điều hành: Android 10\r\nChip: MediaTek Helio P22T\r\nRAM: 2 GB\r\nDung lượng lưu trữ: 32 GB\r\nKết nối: Hỗ trợ 4G, có nghe gọi\r\nSIM: 1 Nano SIM\r\nCamera sau: 8 MP\r\nCamera trước: 5 MP\r\nPin, Sạc: 5000 mAh, 10 W', 'MTB', 99),
(26, 'Lenovo Tab M8', '3990000', '3590000', 'MTB06.jpg', 'Màn hình: 8\", IPS LCD\r\nHệ điều hành: Android 10\r\nChip: MediaTek Helio A22\r\nRAM: 3 GB\r\nDung lượng lưu trữ: 32 GB\r\nKết nối: Hỗ trợ 4G, có nghe gọi\r\nSIM: 1 Nano SIM\r\nCamera sau: 5 MP\r\nCamera trước: 2 MP\r\nPin, Sạc: 5000 mAh, 5 W', 'MTB', 100),
(27, 'Samsung Galaxy Tab S7 FE', '12990000', NULL, 'MTB07.jpg', 'Màn hình: 12.4\", TFT LCD\r\nHệ điều hành: Android 11\r\nChip: Snapdragon 778G\r\nRAM: 4 GB\r\nDung lượng lưu trữ: 64 GB\r\nCamera sau: 8 MP\r\nCamera trước: 5 MP\r\nPin, Sạc: 10090 mAh, 45 W', 'MTB', 77),
(28, 'Samsung Galaxy Tab S6 Lite', '9990000', '8990000', 'MTB08.jpg', 'Màn hình: 10.4\", PLS LCD\r\nHệ điều hành: Android 10\r\nChip: Exynos 9611\r\nRAM: 4 GB\r\nDung lượng lưu trữ: 64 GB\r\nKết nối: Hỗ trợ 4G có nghe gọi\r\nSIM: 1 Nano SIM\r\nCamera sau: 8 MP\r\nCamera trước: 5 MP\r\nPin, Sạc: 7040 mAh, 10 W', 'MTB', 123),
(29, 'Samsung Galaxy Tab A7 Lite', '4490000', NULL, 'MTB09.jpg', 'Màn hình: 8.7\", TFT LCD\r\nHệ điều hành: Android 11\r\nChip: MediaTek MT8768T\r\nRAM: 3 GB\r\nDung lượng lưu trữ: 32 GB\r\nKết nối: Hỗ trợ 4G, có nghe gọi\r\nSIM: 1 Nano SIM\r\nCamera sau: 8 MP\r\nCamera trước: 2 MP\r\nPin, Sạc: 5100 mAh, 15 W', 'MTB', 198),
(30, 'Samsung Galaxy Tab A8', '6990000', '5990000', 'MTB10.jpg', 'Màn hình: 10.5\", TFT LCD\r\nHệ điều hành: Android 11\r\nChip: UniSOC T618\r\nRAM: 4 GB\r\nDung lượng lưu trữ: 64 GB\r\nKết nối: Hỗ trợ 4G, có nghe gọi\r\nSIM: 1 Nano SIM\r\nCamera sau: 8 MP\r\nCamera trước: 5 MP\r\nPin, Sạc: 7040 mAh, 15 W', 'MTB', 197);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `UserName` varchar(100) NOT NULL,
  `TenKH` varchar(200) NOT NULL,
  `SoDienThoai` varchar(10) NOT NULL,
  `Email` varchar(200) NOT NULL,
  `MatKhau` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`UserName`, `TenKH`, `SoDienThoai`, `Email`, `MatKhau`) VALUES
('luan123', 'Trần Quốc Luân', '0355219012', 'tranquocluan@gmail.com', '12345678'),
('thu123', 'Lê Thị Thu', '0379230194', 'lethithu@gmail.com', '12345678');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `chitietdonhang`
--
ALTER TABLE `chitietdonhang`
  ADD KEY `FOREIGN_IDDONHANG` (`IdDonHang`),
  ADD KEY `FOREIGN_IDSP` (`IdSP`);

--
-- Chỉ mục cho bảng `danhsachmenu`
--
ALTER TABLE `danhsachmenu`
  ADD PRIMARY KEY (`Id`);

--
-- Chỉ mục cho bảng `danhsachmenumanager`
--
ALTER TABLE `danhsachmenumanager`
  ADD PRIMARY KEY (`Id`);

--
-- Chỉ mục cho bảng `donhang`
--
ALTER TABLE `donhang`
  ADD PRIMARY KEY (`IdDonHang`),
  ADD KEY `FOREIGN_USERNAME` (`UserName`);

--
-- Chỉ mục cho bảng `manager`
--
ALTER TABLE `manager`
  ADD PRIMARY KEY (`UserName`);

--
-- Chỉ mục cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD PRIMARY KEY (`IdSP`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`UserName`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `danhsachmenu`
--
ALTER TABLE `danhsachmenu`
  MODIFY `Id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `danhsachmenumanager`
--
ALTER TABLE `danhsachmenumanager`
  MODIFY `Id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT cho bảng `donhang`
--
ALTER TABLE `donhang`
  MODIFY `IdDonHang` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  MODIFY `IdSP` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `chitietdonhang`
--
ALTER TABLE `chitietdonhang`
  ADD CONSTRAINT `FOREIGN_IDDONHANG` FOREIGN KEY (`IdDonHang`) REFERENCES `donhang` (`IdDonHang`) ON DELETE CASCADE,
  ADD CONSTRAINT `FOREIGN_IDSP` FOREIGN KEY (`IdSP`) REFERENCES `sanpham` (`IdSP`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `donhang`
--
ALTER TABLE `donhang`
  ADD CONSTRAINT `FOREIGN_USERNAME` FOREIGN KEY (`UserName`) REFERENCES `user` (`UserName`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
