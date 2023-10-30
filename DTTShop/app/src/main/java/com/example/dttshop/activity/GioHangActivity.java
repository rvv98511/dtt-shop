package com.example.dttshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.dttshop.R;
import com.example.dttshop.adapter.GioHangAdapter;
import com.example.dttshop.model.EventBus.TinhTongEvent;
import com.example.dttshop.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {

    Toolbar toolbarGioHang;
    RecyclerView recyclerViewGioHang;
    TextView txtTongTien, txtGioHangRong;
    AppCompatButton appCompatBtnMuaHang;
    GioHangAdapter gioHangAdapter;
    long tongTien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        anhXaThongTin();
        actionToolBar();
        tinhTongTien();
        openMuaHang();
    }

    private void anhXaThongTin() {
        toolbarGioHang = findViewById(R.id.toolBarGioHang);
        recyclerViewGioHang = findViewById(R.id.recyclerViewGioHang);
        txtTongTien = findViewById(R.id.textViewTongTien);
        txtGioHangRong = findViewById(R.id.textViewGioHangRong);
        appCompatBtnMuaHang = findViewById(R.id.appCompatButtonMuaHang);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewGioHang.setLayoutManager(layoutManager);
        recyclerViewGioHang.setHasFixedSize(true);
        if(Utils.mangGioHang.size() == 0) {
            txtGioHangRong.setVisibility(View.VISIBLE);
            appCompatBtnMuaHang.setEnabled(false);
        }
        else {
            gioHangAdapter = new GioHangAdapter(getApplicationContext(), Utils.mangGioHang);
            recyclerViewGioHang.setAdapter(gioHangAdapter);
        }
    }

    private void actionToolBar() {
        setSupportActionBar(toolbarGioHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGioHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Utils.mangMuaHang.clear();
            }
        });
    }

    private void tinhTongTien() {
        tongTien = 0;
        for(int i = 0; i < Utils.mangMuaHang.size(); i++) {
            if(Utils.mangMuaHang.get(i).getGiaKhuyenMai() != 0)
                tongTien = tongTien + Utils.mangMuaHang.get(i).getGiaKhuyenMai();
            else
                tongTien = tongTien + Utils.mangMuaHang.get(i).getGiaSP();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongTien.setText(decimalFormat.format(tongTien) + " ₫");
        if(Utils.mangGioHang.size() == 0) {
            txtGioHangRong.setVisibility(View.VISIBLE);
            appCompatBtnMuaHang.setEnabled(false);
        }
        else {
            if(Utils.mangMuaHang.size() != 0)
                appCompatBtnMuaHang.setEnabled(true);
            else
                appCompatBtnMuaHang.setEnabled(false);
        }
    }

    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void tinhTongEvent(TinhTongEvent event) {
        if(event != null)
            tinhTongTien();
    }

    private void openMuaHang() {
        appCompatBtnMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent thanhToan = new Intent(getApplicationContext(), ThanhToanActivity.class);
                thanhToan.putExtra("tongtien", tongTien);
                startActivity(thanhToan);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.mangGioHang.size() == 0) {
            txtGioHangRong.setVisibility(View.VISIBLE);
            appCompatBtnMuaHang.setEnabled(false);
        }
        else {
            gioHangAdapter = new GioHangAdapter(getApplicationContext(), Utils.mangGioHang);
            recyclerViewGioHang.setAdapter(gioHangAdapter);
        }
        tinhTongTien();
    }
}