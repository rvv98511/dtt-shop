package com.example.dttshop.model;

import java.util.ArrayList;

public class SanPhamMoiModel {
    boolean success;
    String message;
    ArrayList<SanPhamMoi> result;

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

    public ArrayList<SanPhamMoi> getResult() {
        return result;
    }

    public void setResult(ArrayList<SanPhamMoi> result) {
        this.result = result;
    }
}
