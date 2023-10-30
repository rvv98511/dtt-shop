package com.example.dttshopmanager.model;

import java.util.ArrayList;

public class SanPhamTrangThaiDonHangModel {
    boolean success;
    String message;
    ArrayList<SanPhamTrangThaiDonHang> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<SanPhamTrangThaiDonHang> getResult() {
        return result;
    }

    public void setResult(ArrayList<SanPhamTrangThaiDonHang> result) {
        this.result = result;
    }
}
