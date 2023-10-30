package com.example.dttshop.model;

import java.util.ArrayList;

public class DanhSachMenuModel {
    boolean success;
    String message;
    ArrayList<DanhSachMenu> result;

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

    public ArrayList<DanhSachMenu> getResult() {
        return result;
    }

    public void setResult(ArrayList<DanhSachMenu> result) {
        this.result = result;
    }
}
