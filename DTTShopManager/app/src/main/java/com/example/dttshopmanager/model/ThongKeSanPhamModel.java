package com.example.dttshopmanager.model;

import java.util.ArrayList;

public class ThongKeSanPhamModel {
    boolean success;
    String message;
    ArrayList<ThongKeSanPham> result;

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

    public ArrayList<ThongKeSanPham> getResult() {
        return result;
    }

    public void setResult(ArrayList<ThongKeSanPham> result) {
        this.result = result;
    }
}
