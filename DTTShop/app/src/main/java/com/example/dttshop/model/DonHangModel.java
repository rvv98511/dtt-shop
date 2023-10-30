package com.example.dttshop.model;

import java.util.ArrayList;

public class DonHangModel {
    boolean success;
    String message;
    ArrayList<DonHang> result;

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

    public ArrayList<DonHang> getResult() {
        return result;
    }

    public void setResult(ArrayList<DonHang> result) {
        this.result = result;
    }
}
