package com.example.dttshopmanager.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.dttshopmanager.R;
import com.example.dttshopmanager.adapter.LoaiSanPhamAdapter;
import com.example.dttshopmanager.model.SanPhamMoi;
import com.example.dttshopmanager.retrofit.ApiQuanLyBanHang;
import com.example.dttshopmanager.retrofit.RetrofitClient;
import com.example.dttshopmanager.utils.Utils;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoaiSanPhamActivity extends AppCompatActivity {

    Toolbar toolBarLoaiSP;
    RecyclerView recyclerViewLoaiSP;
    ApiQuanLyBanHang apiQuanLyBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int page = 1;
    String loai;
    LoaiSanPhamAdapter loaiSanPhamAdapter;
    ArrayList<SanPhamMoi> mangLoaiSanPham;
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai_san_pham);

        anhXaThongTin();
        actionToolBar();
        getLoaiSanPham(page);
        addEventLoading();
    }

    private void anhXaThongTin() {
        apiQuanLyBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiQuanLyBanHang.class);
        loai = getIntent().getStringExtra("loai");

        toolBarLoaiSP = findViewById(R.id.toolBarLoaiSP);
        recyclerViewLoaiSP = findViewById(R.id.recyclerViewLoaiSP);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewLoaiSP.setLayoutManager(linearLayoutManager);
        recyclerViewLoaiSP.setHasFixedSize(true);

        mangLoaiSanPham = new ArrayList<>();
    }

    private void actionToolBar() {
        if(loai.contains("DT"))
            toolBarLoaiSP.setTitle("Điện thoại");
        else if(loai.contains("LT"))
            toolBarLoaiSP.setTitle("Laptop");
        else
            toolBarLoaiSP.setTitle("Máy tính bảng");
        setSupportActionBar(toolBarLoaiSP);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBarLoaiSP.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getLoaiSanPham(int page) {
        compositeDisposable.add(apiQuanLyBanHang.getLoaiSP(page, loai)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            sanPhamMoiModel -> {
                if(sanPhamMoiModel.isSuccess()) {
                    if(loaiSanPhamAdapter == null) {
                        mangLoaiSanPham = sanPhamMoiModel.getResult();
                        loaiSanPhamAdapter = new LoaiSanPhamAdapter(getApplicationContext(), mangLoaiSanPham);
                        recyclerViewLoaiSP.setAdapter(loaiSanPhamAdapter);
                    }
                    else {
                        int viTri = mangLoaiSanPham.size();
                        int soLuongThem = sanPhamMoiModel.getResult().size();
                        for(int i = 0; i < soLuongThem; i++) {
                            mangLoaiSanPham.add(sanPhamMoiModel.getResult().get(i));
                        }
                        loaiSanPhamAdapter.notifyItemRangeInserted(viTri, soLuongThem);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Đã tải hết sản phẩm", Toast.LENGTH_LONG).show();
                    isLoading = true;
                }
            },
            throwable -> {
                Toast.makeText(getApplicationContext(), "Không kết nối được với server " + throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        ));
    }

    private void addEventLoading() {
        recyclerViewLoaiSP.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(!isLoading) {
                    if(linearLayoutManager.findLastCompletelyVisibleItemPosition() == mangLoaiSanPham.size() - 1) {
                        loadingMore();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void loadingMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                mangLoaiSanPham.add(null);
                loaiSanPhamAdapter.notifyItemInserted(mangLoaiSanPham.size() - 1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mangLoaiSanPham.remove(mangLoaiSanPham.size() - 1);
                loaiSanPhamAdapter.notifyItemRemoved(mangLoaiSanPham.size());
                page++;
                getLoaiSanPham(page);
                loaiSanPhamAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 1500);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}