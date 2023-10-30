package com.example.dttshop.model;

import java.util.ArrayList;
import java.util.Date;

public class DonHang {
    int IdDonHang, TongSoLuong, TrangThai;
    String TongTien, ThanhToan;
    Date NgayDatHang;
    ArrayList<ChiTietDonHang> ChiTiet;

    public int getIdDonHang() {
        return IdDonHang;
    }

    public void setIdDonHang(int idDonHang) {
        IdDonHang = idDonHang;
    }

    public int getTongSoLuong() {
        return TongSoLuong;
    }

    public void setTongSoLuong(int tongSoLuong) {
        TongSoLuong = tongSoLuong;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int trangThai) {
        TrangThai = trangThai;
    }

    public String getTongTien() {
        return TongTien;
    }

    public void setTongTien(String tongTien) {
        TongTien = tongTien;
    }

    public String getThanhToan() {
        return ThanhToan;
    }

    public void setThanhToan(String thanhToan) {
        ThanhToan = thanhToan;
    }

    public Date getNgayDatHang() {
        return NgayDatHang;
    }

    public void setNgayDatHang(Date ngayDatHang) {
        NgayDatHang = ngayDatHang;
    }

    public ArrayList<ChiTietDonHang> getChiTiet() {
        return ChiTiet;
    }

    public void setChiTiet(ArrayList<ChiTietDonHang> chiTiet) {
        ChiTiet = chiTiet;
    }
}
