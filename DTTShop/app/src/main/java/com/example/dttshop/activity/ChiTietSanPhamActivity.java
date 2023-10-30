package com.example.dttshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.TooltipCompat;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dttshop.R;
import com.example.dttshop.model.GioHang;
import com.example.dttshop.model.SanPhamMoi;
import com.example.dttshop.retrofit.ApiBanHang;
import com.example.dttshop.retrofit.RetrofitClient;
import com.example.dttshop.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChiTietSanPhamActivity extends AppCompatActivity {

    Toolbar toolbarChiTietSP;
    TextView txtTenChiTietSP, txtGiaChiTietSP, txtGiaKhuyenMaiChiTietSP, txtMoTaChiTietSP, txtSoLuongChiTietSP;
    ImageView imgAnhChiTietSP;
    AppCompatButton appCompatBtnThemGioHang;
    Spinner spinnerSoLuong;
    FrameLayout frameLayoutGioHang;
    NotificationBadge notificationBadge;
    SanPhamMoi sanPhamMoi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        anhXaThongTin();
        actionToolBar();
        getChiTietSP();
        addGioHang();
        openGioHang();
    }

    private void anhXaThongTin() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        toolbarChiTietSP = findViewById(R.id.toolBarChiTietSP);
        txtTenChiTietSP = findViewById(R.id.textViewTenChiTietSP);
        txtGiaChiTietSP = findViewById(R.id.textViewGiaChiTietSP);
        txtGiaKhuyenMaiChiTietSP = findViewById(R.id.textViewGiaKhuyenMaiChiTietSP);
        txtMoTaChiTietSP = findViewById(R.id.textViewMoTaChiTietSP);
        txtSoLuongChiTietSP = findViewById(R.id.textViewSoLuongChiTietSP);
        imgAnhChiTietSP = findViewById(R.id.imageViewChiTietSP);
        appCompatBtnThemGioHang = findViewById(R.id.appCompatButtonThemGioHang);
        spinnerSoLuong = findViewById(R.id.spinnerSoLuong);
        frameLayoutGioHang = findViewById(R.id.frameLayoutGioHang);
        TooltipCompat.setTooltipText(frameLayoutGioHang, "Giỏ hàng");
        notificationBadge = findViewById(R.id.notificationBadge);

        if(Utils.mangGioHang != null) {
            int total = 0;
            for(int i = 0; i < Utils.mangGioHang.size(); i++)
                total = total + Utils.mangGioHang.get(i).getSoLuongMua();
            notificationBadge.setText(String.valueOf(total));
        }
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
        if(sanPhamMoi.getGiaKhuyenMai() != null) {
            txtGiaKhuyenMaiChiTietSP.setVisibility(View.VISIBLE);
            txtGiaKhuyenMaiChiTietSP.setText(decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiaKhuyenMai())) + " ₫");
            txtGiaChiTietSP.setPaintFlags(txtGiaChiTietSP.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else {
            txtGiaKhuyenMaiChiTietSP.setVisibility(View.GONE);
            txtGiaChiTietSP.setPaintFlags(txtGiaChiTietSP.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
        if(sanPhamMoi.getHinhAnhSP().contains("http") || sanPhamMoi.getHinhAnhSP().contains("https"))
            Glide.with(getApplicationContext()).load(sanPhamMoi.getHinhAnhSP()).into(imgAnhChiTietSP);
        else {
            String urlHinhAnhSP = Utils.BASE_URL + "images/sanpham/" + sanPhamMoi.getHinhAnhSP();
            Glide.with(getApplicationContext()).load(urlHinhAnhSP).into(imgAnhChiTietSP);
        }
        txtMoTaChiTietSP.setText(sanPhamMoi.getMoTa());
        txtSoLuongChiTietSP.setText(sanPhamMoi.getSoLuongSP() + "");
        if(sanPhamMoi.getSoLuongSP() > 0) {
            appCompatBtnThemGioHang.setEnabled(true);
            spinnerSoLuong.setEnabled(true);
        }
        else {
            appCompatBtnThemGioHang.setEnabled(false);
            spinnerSoLuong.setEnabled(false);
        }
        Integer[] soLuong = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> arrayAdapterSpinner = new ArrayAdapter<>(this, R.layout.item_spinner_centered, soLuong);
        spinnerSoLuong.setAdapter(arrayAdapterSpinner);
    }

    private void addGioHang() {
        appCompatBtnThemGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int soLuongMua = Integer.parseInt(spinnerSoLuong.getSelectedItem().toString());
                if(Utils.mangGioHang.size() > 0) {
                    boolean flag = false;
                    for(int i = 0; i < Utils.mangGioHang.size(); i++) {
                        if (Utils.mangGioHang.get(i).getIdSP() == sanPhamMoi.getIdSP()) {
                            int soLuongMuaTemp = Utils.mangGioHang.get(i).getSoLuongMua() + soLuongMua;
                            if (soLuongMuaTemp > sanPhamMoi.getSoLuongSP())
                                Toast.makeText(getApplicationContext(), "Số lượng mua vượt quá số lượng tồn", Toast.LENGTH_SHORT).show();
                            else {
                                addDataGioHang();
                                Paper.book().write("giohang", Utils.mangGioHang);
                            }
                            flag = true;
                        }
                    }
                    if(!flag) {
                        if (soLuongMua > sanPhamMoi.getSoLuongSP())
                            Toast.makeText(getApplicationContext(), "Số lượng mua vượt quá số lượng sản phẩm", Toast.LENGTH_SHORT).show();
                        else {
                            addDataGioHang();
                            Paper.book().write("giohang", Utils.mangGioHang);
                        }
                    }
                }
                else {
                    if(soLuongMua > sanPhamMoi.getSoLuongSP())
                        Toast.makeText(getApplicationContext(), "Số lượng mua vượt quá số lượng sản phẩm", Toast.LENGTH_SHORT).show();
                    else {
                        addDataGioHang();
                        Paper.book().write("giohang", Utils.mangGioHang);
                    }
                }
            }
        });
    }

    private void addDataGioHang() {
        long giaSP, giaKhuyenMai;
        int soLuongMua = Integer.parseInt(spinnerSoLuong.getSelectedItem().toString());
        if(Utils.mangGioHang.size() > 0) {
            boolean flag = false;
            for(int i = 0; i < Utils.mangGioHang.size(); i++) {
                if(Utils.mangGioHang.get(i).getIdSP() == sanPhamMoi.getIdSP()) {
                    Utils.mangGioHang.get(i).setSoLuongMua(soLuongMua + Utils.mangGioHang.get(i).getSoLuongMua());
                    if(sanPhamMoi.getGiaKhuyenMai() != null) {
                        giaSP = Long.parseLong(sanPhamMoi.getGiaSP()) * Utils.mangGioHang.get(i).getSoLuongMua();
                        giaKhuyenMai = Long.parseLong(sanPhamMoi.getGiaKhuyenMai()) * Utils.mangGioHang.get(i).getSoLuongMua();
                        Utils.mangGioHang.get(i).setGiaSP(giaSP);
                        Utils.mangGioHang.get(i).setGiaKhuyenMai(giaKhuyenMai);
                    }
                    else {
                        giaSP = Long.parseLong(sanPhamMoi.getGiaSP()) * Utils.mangGioHang.get(i).getSoLuongMua();
                        Utils.mangGioHang.get(i).setGiaSP(giaSP);
                    }
                    flag = true;
                }
            }
            if(!flag) {
                GioHang gioHang = new GioHang();
                if(sanPhamMoi.getGiaKhuyenMai() != null) {
                    giaSP = Long.parseLong(sanPhamMoi.getGiaSP()) * soLuongMua;
                    giaKhuyenMai = Long.parseLong(sanPhamMoi.getGiaKhuyenMai()) * soLuongMua;
                    gioHang.setGiaSP(giaSP);
                    gioHang.setGiaKhuyenMai(giaKhuyenMai);
                }
                else {
                    giaSP = Long.parseLong(sanPhamMoi.getGiaSP()) * soLuongMua;
                    gioHang.setGiaSP(giaSP);
                }

                gioHang.setSoLuongMua(soLuongMua);
                gioHang.setIdSP(sanPhamMoi.getIdSP());
                gioHang.setTenSP(sanPhamMoi.getTenSP());
                gioHang.setHinhSP(sanPhamMoi.getHinhAnhSP());
                gioHang.setSoLuongSP(sanPhamMoi.getSoLuongSP());
                Utils.mangGioHang.add(gioHang);
            }
        }
        else {
            GioHang gioHang = new GioHang();
            if(sanPhamMoi.getGiaKhuyenMai() != null) {
                giaSP = Long.parseLong(sanPhamMoi.getGiaSP()) * soLuongMua;
                giaKhuyenMai = Long.parseLong(sanPhamMoi.getGiaKhuyenMai()) * soLuongMua;
                gioHang.setGiaSP(giaSP);
                gioHang.setGiaKhuyenMai(giaKhuyenMai);
            }
            else {
                giaSP = Long.parseLong(sanPhamMoi.getGiaSP()) * soLuongMua;
                gioHang.setGiaSP(giaSP);
            }

            gioHang.setSoLuongMua(soLuongMua);
            gioHang.setIdSP(sanPhamMoi.getIdSP());
            gioHang.setTenSP(sanPhamMoi.getTenSP());
            gioHang.setHinhSP(sanPhamMoi.getHinhAnhSP());
            gioHang.setSoLuongSP(sanPhamMoi.getSoLuongSP());
            Utils.mangGioHang.add(gioHang);
        }
        int total = 0;
        for(int i = 0; i < Utils.mangGioHang.size(); i++)
            total = total + Utils.mangGioHang.get(i).getSoLuongMua();
        notificationBadge.setText(String.valueOf(total));
    }

    private void openGioHang() {
        frameLayoutGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gioHang = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(gioHang);
            }
        });
    }

    private void updateSoLuongChiTietSP() {
        compositeDisposable.add(apiBanHang.getSPMoi()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                sanPhamMoiModel -> {
                    if(sanPhamMoiModel.isSuccess()) {
                        for(int i = 0; i < sanPhamMoiModel.getResult().size(); i++) {
                            if(sanPhamMoiModel.getResult().get(i).getIdSP() == sanPhamMoi.getIdSP()) {
                                sanPhamMoi.setSoLuongSP(sanPhamMoiModel.getResult().get(i).getSoLuongSP());
                                txtSoLuongChiTietSP.setText(sanPhamMoi.getSoLuongSP() + "");
                            }
                        }
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Lỗi", Toast.LENGTH_LONG).show();
                },
                throwable -> {
                    Toast.makeText(getApplicationContext(), "Không kết nối được với server " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                }
        ));
    }

    @Override
    protected void onResume() {
        super.onResume();
        int total = 0;
        for(int i = 0; i < Utils.mangGioHang.size(); i++)
            total = total + Utils.mangGioHang.get(i).getSoLuongMua();
        notificationBadge.setText(String.valueOf(total));
        updateSoLuongChiTietSP();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}