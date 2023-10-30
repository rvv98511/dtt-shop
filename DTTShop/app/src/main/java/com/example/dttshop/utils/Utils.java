package com.example.dttshop.utils;

import com.example.dttshop.model.GioHang;
import com.example.dttshop.model.User;

import java.util.ArrayList;

public class Utils {
    public static final String BASE_URL = "http://192.168.1.4/ungdungbanhang/";
    public static ArrayList<GioHang> mangGioHang;
    public static ArrayList<GioHang> mangMuaHang = new ArrayList<>();
    public static User currentUser = new User();
}
