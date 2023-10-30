package com.example.dttshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.TooltipCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dttshop.R;
import com.example.dttshop.adapter.LoaiSanPhamAdapter;
import com.example.dttshop.model.SanPhamMoi;
import com.example.dttshop.retrofit.ApiBanHang;
import com.example.dttshop.retrofit.RetrofitClient;
import com.example.dttshop.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {

    Toolbar toolBarSearch;
    RecyclerView recyclerViewSearchSP;
    TextView txtSearchNone;
    EditText editTxtSearchSP;
    Spinner spinnerSearchTheoLoai, spinnerSearchTheoGia;
    ImageButton imgBtnClear;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    LoaiSanPhamAdapter loaiSanPhamAdapter;
    ArrayList<SanPhamMoi> mangSearchSanPham;
    LinearLayoutManager linearLayoutManager;
    FrameLayout frameLayoutGioHang;
    NotificationBadge notificationBadge;
    String strTheoLoai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        anhXaThongTin();
        actionToolBar();
        openGioHang();
        addDataSpinner();
        eventSearchSpinner();
        getAllSanPham();
    }

    private void anhXaThongTin() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        toolBarSearch = findViewById(R.id.toolBarSearch);
        recyclerViewSearchSP = findViewById(R.id.recyclerViewSearchSP);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewSearchSP.setLayoutManager(linearLayoutManager);
        recyclerViewSearchSP.setHasFixedSize(true);
        txtSearchNone = findViewById(R.id.textViewSearchNone);
        imgBtnClear = findViewById(R.id.imageButtonClear);
        editTxtSearchSP = findViewById(R.id.editTextSearch);
        spinnerSearchTheoLoai = findViewById(R.id.spinnerSearchTheoLoai);
        spinnerSearchTheoGia = findViewById(R.id.spinnerSearchTheoGia);
        frameLayoutGioHang = findViewById(R.id.frameLayoutGioHang);
        TooltipCompat.setTooltipText(frameLayoutGioHang, "Giỏ hàng");
        notificationBadge = findViewById(R.id.notificationBadge);

        mangSearchSanPham = new ArrayList<>();

        if(Utils.mangGioHang != null) {
            int total = 0;
            for(int i = 0; i < Utils.mangGioHang.size(); i++)
                total = total + Utils.mangGioHang.get(i).getSoLuongMua();
            notificationBadge.setText(String.valueOf(total));
        }

        eventSearchInput();
    }

    private void actionToolBar() {
        setSupportActionBar(toolBarSearch);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBarSearch.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addDataSpinner() {
        String[] stringSearchLoaiSP = new String[] {"Loại sản phẩm", "Điện thoại", "Laptop", "Máy tính bảng"};
        ArrayAdapter<String> arrayAdapterSpinnerSearchLoaiSP = new ArrayAdapter<>(this, R.layout.item_spinner_centered, stringSearchLoaiSP);
        spinnerSearchTheoLoai.setAdapter(arrayAdapterSpinnerSearchLoaiSP);

        String[] stringSearchGiaSP = new String[] {"Giá sản phẩm", "Dưới 10 triệu", "Từ 10 - 20 triệu", "Trên 20 triệu"};
        ArrayAdapter<String> arrayAdapterSpinnerSearchGiaSP = new ArrayAdapter<>(this, R.layout.item_spinner_centered, stringSearchGiaSP);
        spinnerSearchTheoGia.setAdapter(arrayAdapterSpinnerSearchGiaSP);
    }

    private void eventSearchSpinner() {
        spinnerSearchTheoLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(editTxtSearchSP.getText().toString().trim().length() == 0)
                    if(spinnerSearchTheoLoai.getSelectedItemPosition() == 0 && spinnerSearchTheoGia.getSelectedItemPosition() == 0)
                        getAllSanPham();
                    else {
                        if(position == 1)
                            strTheoLoai = "DT";
                        else if(position == 2)
                            strTheoLoai = "LT";
                        else if(position == 3)
                            strTheoLoai = "MTB";
                        else
                            strTheoLoai = "";
                        searchSanPham("", strTheoLoai, spinnerSearchTheoGia.getSelectedItemPosition());
                    }
                else {
                    if(position == 1)
                        strTheoLoai = "DT";
                    else if(position == 2)
                        strTheoLoai = "LT";
                    else if(position == 3)
                        strTheoLoai = "MTB";
                    else
                        strTheoLoai = "";
                    searchSanPham(editTxtSearchSP.getText().toString().trim(), strTheoLoai, spinnerSearchTheoGia.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerSearchTheoGia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(editTxtSearchSP.getText().toString().trim().length() == 0)
                    if(spinnerSearchTheoLoai.getSelectedItemPosition() == 0 && spinnerSearchTheoGia.getSelectedItemPosition() == 0)
                        getAllSanPham();
                    else {
                        if(spinnerSearchTheoLoai.getSelectedItemPosition() == 1)
                            strTheoLoai = "DT";
                        else if(spinnerSearchTheoLoai.getSelectedItemPosition() == 2)
                            strTheoLoai = "LT";
                        else if(spinnerSearchTheoLoai.getSelectedItemPosition() == 3)
                            strTheoLoai = "MTB";
                        else
                            strTheoLoai = "";
                        searchSanPham("", strTheoLoai, position);
                    }
                else
                    searchSanPham(editTxtSearchSP.getText().toString().trim(), strTheoLoai, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void eventSearchInput() {
        editTxtSearchSP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0) {
                    imgBtnClear.setVisibility(View.GONE);
                    mangSearchSanPham.clear();
                    loaiSanPhamAdapter.notifyDataSetChanged();
                    txtSearchNone.setVisibility(View.GONE);
                    if(spinnerSearchTheoLoai.getSelectedItemPosition() == 0 && spinnerSearchTheoGia.getSelectedItemPosition() == 0)
                        getAllSanPham();
                    else {
                        if(spinnerSearchTheoLoai.getSelectedItemPosition() == 1)
                            strTheoLoai = "DT";
                        else if(spinnerSearchTheoLoai.getSelectedItemPosition() == 2)
                            strTheoLoai = "LT";
                        else if(spinnerSearchTheoLoai.getSelectedItemPosition() == 3)
                            strTheoLoai = "MTB";
                        else
                            strTheoLoai = "";
                        searchSanPham("", strTheoLoai, spinnerSearchTheoGia.getSelectedItemPosition());
                    }
                }
                else {
                    imgBtnClear.setVisibility(View.VISIBLE);
                    imgBtnClear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            editTxtSearchSP.getText().clear();
                        }
                    });
                    if(spinnerSearchTheoLoai.getSelectedItemPosition() == 1)
                        strTheoLoai = "DT";
                    else if(spinnerSearchTheoLoai.getSelectedItemPosition() == 2)
                        strTheoLoai = "LT";
                    else if(spinnerSearchTheoLoai.getSelectedItemPosition() == 3)
                        strTheoLoai = "MTB";
                    else
                        strTheoLoai = "";
                    searchSanPham(s.toString().trim(), strTheoLoai, spinnerSearchTheoGia.getSelectedItemPosition());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void searchSanPham(String searchText, String searchTheoLoai, int searchTheoGia) {
        compositeDisposable.add(apiBanHang.searchSP(searchText, searchTheoLoai, searchTheoGia)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
            sanPhamMoiModel -> {
                if(sanPhamMoiModel.isSuccess()) {
                    mangSearchSanPham.clear();
                    txtSearchNone.setVisibility(View.GONE);
                    mangSearchSanPham = sanPhamMoiModel.getResult();
                    loaiSanPhamAdapter = new LoaiSanPhamAdapter(getApplicationContext(), mangSearchSanPham);
                    recyclerViewSearchSP.setAdapter(loaiSanPhamAdapter);
                }
                else {
                    mangSearchSanPham.clear();
                    loaiSanPhamAdapter.notifyDataSetChanged();
                    txtSearchNone.setVisibility(View.VISIBLE);
                }
            },
            throwable -> {
                Toast.makeText(getApplicationContext(), "Không kết nối được với server " + throwable.getMessage(), Toast.LENGTH_LONG).show();
            }
        ));
    }

    private void getAllSanPham() {
        compositeDisposable.add(apiBanHang.getSPMoi()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                sanPhamMoiModel -> {
                    if(sanPhamMoiModel.isSuccess()) {
                        mangSearchSanPham = sanPhamMoiModel.getResult();
                        loaiSanPhamAdapter = new LoaiSanPhamAdapter(getApplicationContext(), mangSearchSanPham);
                        recyclerViewSearchSP.setAdapter(loaiSanPhamAdapter);
                    }
                },
                throwable -> {
                    Toast.makeText(getApplicationContext(), "Không kết nối được với server " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
        ));
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

    @Override
    protected void onResume() {
        super.onResume();
        int total = 0;
        for(int i = 0; i < Utils.mangGioHang.size(); i++)
            total = total + Utils.mangGioHang.get(i).getSoLuongMua();
        notificationBadge.setText(String.valueOf(total));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}