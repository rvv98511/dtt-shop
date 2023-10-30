package com.example.dttshopmanager.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dttshopmanager.R;
import com.example.dttshopmanager.model.SanPhamMoi;
import com.example.dttshopmanager.utils.Utils;

import java.text.DecimalFormat;

public class ChiTietSanPhamActivity extends AppCompatActivity {

    Toolbar toolbarChiTietSP;
    TextView txtTenChiTietSP, txtGiaChiTietSP, txtGiaKhuyenMaiChiTietSP, txtMoTaChiTietSP, txtSoLuongChiTietSP;
    ImageView imgAnhChiTietSP;
    SanPhamMoi sanPhamMoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        anhXaThongTin();
        actionToolBar();
        getChiTietSP();
    }

    private void anhXaThongTin() {
        toolbarChiTietSP = findViewById(R.id.toolBarChiTietSP);
        txtTenChiTietSP = findViewById(R.id.textViewTenChiTietSP);
        txtGiaChiTietSP = findViewById(R.id.textViewGiaChiTietSP);
        txtGiaKhuyenMaiChiTietSP = findViewById(R.id.textViewGiaKhuyenMaiChiTietSP);
        txtMoTaChiTietSP = findViewById(R.id.textViewMoTaChiTietSP);
        txtSoLuongChiTietSP = findViewById(R.id.textViewSoLuongChiTietSP);
        imgAnhChiTietSP = findViewById(R.id.imageViewChiTietSP);
    }

    private void actionToolBar() {
        setSupportActionBar(toolbarChiTietSP);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTietSP.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getChiTietSP() {
        sanPhamMoi = (SanPhamMoi) getIntent().getSerializableExtra("chitiet");
        txtTenChiTietSP.setText(sanPhamMoi.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGiaChiTietSP.setText(decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiaSP())) + " ₫");
        if (sanPhamMoi.getGiaKhuyenMai() != null) {
            txtGiaKhuyenMaiChiTietSP.setVisibility(View.VISIBLE);
            txtGiaKhuyenMaiChiTietSP.setText(decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiaKhuyenMai())) + " ₫");
            txtGiaChiTietSP.setPaintFlags(txtGiaChiTietSP.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            txtGiaKhuyenMaiChiTietSP.setVisibility(View.GONE);
            txtGiaChiTietSP.setPaintFlags(txtGiaChiTietSP.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
        if (sanPhamMoi.getHinhAnhSP().contains("http") || sanPhamMoi.getHinhAnhSP().contains("https"))
            Glide.with(getApplicationContext()).load(sanPhamMoi.getHinhAnhSP()).into(imgAnhChiTietSP);
        else {
            String urlHinhAnhSP = Utils.BASE_URL + "images/sanpham/" + sanPhamMoi.getHinhAnhSP();
            Glide.with(getApplicationContext()).load(urlHinhAnhSP).into(imgAnhChiTietSP);
        }
        txtMoTaChiTietSP.setText(sanPhamMoi.getMoTa());
        txtSoLuongChiTietSP.setText(sanPhamMoi.getSoLuongSP() + "");;
    }
}