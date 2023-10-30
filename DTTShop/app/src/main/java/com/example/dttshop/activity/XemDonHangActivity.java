package com.example.dttshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dttshop.R;
import com.example.dttshop.adapter.DonHangAdapter;
import com.example.dttshop.model.DonHang;
import com.example.dttshop.retrofit.ApiBanHang;
import com.example.dttshop.retrofit.RetrofitClient;
import com.example.dttshop.utils.Utils;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class XemDonHangActivity extends AppCompatActivity {

    Toolbar toolbarLichSuMuaHang;
    TextView txtLichSuMuaHangRong;
    SwipeRefreshLayout swipeRefreshLayoutLichSuMuaHang;
    RecyclerView recyclerViewLichSuMuaHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    ArrayList<DonHang> mangDonHang;
    DonHangAdapter donHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_don_hang);

        anhXaThongTin();
        actionToolBar();
        actionRefreshLayout();
        loadLichSuMuaHang();
    }

    private void anhXaThongTin() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        toolbarLichSuMuaHang = findViewById(R.id.toolBarLichSuMuaHang);
        txtLichSuMuaHangRong = findViewById(R.id.textViewLichSuMuaHangRong);
        swipeRefreshLayoutLichSuMuaHang = findViewById(R.id.swipeRefreshLayoutLichSuMuaHang);
        recyclerViewLichSuMuaHang = findViewById(R.id.recyclerViewLichSuMuaHang);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewLichSuMuaHang.setLayoutManager(layoutManager);
        recyclerViewLichSuMuaHang.setHasFixedSize(true);

        mangDonHang = new ArrayList<>();
    }

    private void actionToolBar() {
        setSupportActionBar(toolbarLichSuMuaHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLichSuMuaHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void actionRefreshLayout() {
        swipeRefreshLayoutLichSuMuaHang.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadLichSuMuaHang();
                swipeRefreshLayoutLichSuMuaHang.setRefreshing(false);
            }
        });
    }

    private void loadLichSuMuaHang() {
        compositeDisposable.add(apiBanHang.getOrder(Utils.currentUser.getUserName())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                donHangModel -> {
                    if(donHangModel.isSuccess()) {
                        txtLichSuMuaHangRong.setVisibility(View.GONE);
                        mangDonHang = donHangModel.getResult();
                        donHangAdapter = new DonHangAdapter(getApplicationContext(), mangDonHang);
                        recyclerViewLichSuMuaHang.setAdapter(donHangAdapter);
                    }
                    else
                        txtLichSuMuaHangRong.setVisibility(View.VISIBLE);
                },
                throwable -> {
                    Toast.makeText(getApplicationContext(), "Không kết nối được với server " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                }
        ));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}