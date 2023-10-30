package com.example.dttshopmanager.model;

import java.util.ArrayList;

public class ThongKeDoanhThuModel {
    boolean success;
    String message;
    ArrayList<ThongKeDoanhThu> result;

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

    public ArrayList<ThongKeDoanhThu> getResult() {
        return result;
    }

    public void setResult(ArrayList<ThongKeDoanhThu> result) {
        this.result = result;
    }
}
