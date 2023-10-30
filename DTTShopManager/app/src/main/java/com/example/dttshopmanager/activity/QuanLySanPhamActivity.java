package com.example.dttshopmanager.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.TooltipCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dttshopmanager.R;
import com.example.dttshopmanager.adapter.QuanLySanPhamAdapter;
import com.example.dttshopmanager.model.EventBus.SuaXoaEvent;
import com.example.dttshopmanager.model.SanPhamMoi;
import com.example.dttshopmanager.model.SanPhamTrangThaiDonHang;
import com.example.dttshopmanager.retrofit.ApiQuanLyBanHang;
import com.example.dttshopmanager.retrofit.RetrofitClient;
import com.example.dttshopmanager.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class QuanLySanPhamActivity extends AppCompatActivity {

    Toolbar toolbarManageSearch;
    RecyclerView recyclerViewManageSearchSP;
    TextView txtManageSearchNone;
    EditText editTxtManageSearchSP;
    Spinner spinnerSearchTheoLoai, spinnerSearchTheoGia;
    ImageButton imgBtnClear;
    ApiQuanLyBanHang apiQuanLyBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    QuanLySanPhamAdapter quanLySanPhamAdapter;
    ArrayList<SanPhamMoi> mangManageSearchSanPham;
    LinearLayoutManager linearLayoutManager;
    ImageView imgManageAddSP;
    SanPhamMoi suaXoaSanPham;
    String strTheoLoai;
    ArrayList<SanPhamTrangThaiDonHang> mangSanPhamTrangThaiDonHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_san_pham);

        anhXaThongTin();
        actionToolBar();
        addDataSpinner();
        eventSearchSpinner();
        getAllSanPham();
    }

    private void anhXaThongTin() {
        apiQuanLyBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiQuanLyBanHang.class);

        toolbarManageSearch = findViewById(R.id.toolBarManageSP);
        recyclerViewManageSearchSP = findViewById(R.id.recyclerViewManageSearchSP);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewManageSearchSP.setLayoutManager(linearLayoutManager);
        recyclerViewManageSearchSP.setHasFixedSize(true);
        txtManageSearchNone = findViewById(R.id.textViewManageSearchNone);
        imgBtnClear = findViewById(R.id.imageButtonClear);
        editTxtManageSearchSP = findViewById(R.id.editTextManageSearchSP);
        spinnerSearchTheoLoai = findViewById(R.id.spinnerSearchTheoLoai);
        spinnerSearchTheoGia = findViewById(R.id.spinnerSearchTheoGia);
        imgManageAddSP = findViewById(R.id.imageViewManageAddSP);
        TooltipCompat.setTooltipText(imgManageAddSP, "Thêm sản phẩm");

        mangManageSearchSanPham = new ArrayList<>();

        eventSearchInput();
        openAddSP();
    }

    private void actionToolBar() {
        setSupportActionBar(toolbarManageSearch);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarManageSearch.setNavigationOnClickListener(new View.OnClickListener() {
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
                if(editTxtManageSearchSP.getText().toString().trim().length() == 0) {
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
                    searchSanPham(editTxtManageSearchSP.getText().toString().trim(), strTheoLoai, spinnerSearchTheoGia.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerSearchTheoGia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(editTxtManageSearchSP.getText().toString().trim().length() == 0)
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
                    searchSanPham(editTxtManageSearchSP.getText().toString().trim(), strTheoLoai, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void eventSearchInput() {
        editTxtManageSearchSP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0) {
                    imgBtnClear.setVisibility(View.GONE);
                    mangManageSearchSanPham.clear();
                    quanLySanPhamAdapter.notifyDataSetChanged();
                    txtManageSearchNone.setVisibility(View.GONE);
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
                            editTxtManageSearchSP.getText().clear();
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
        compositeDisposable.add(apiQuanLyBanHang.searchSP(searchText, searchTheoLoai, searchTheoGia)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                sanPhamMoiModel -> {
                    if(sanPhamMoiModel.isSuccess()) {
                        mangManageSearchSanPham.clear();
                        txtManageSearchNone.setVisibility(View.GONE);
                        mangManageSearchSanPham = sanPhamMoiModel.getResult();
                        quanLySanPhamAdapter = new QuanLySanPhamAdapter(getApplicationContext(), mangManageSearchSanPham);
                        recyclerViewManageSearchSP.setAdapter(quanLySanPhamAdapter);
                    }
                    else {
                        mangManageSearchSanPham.clear();
                        quanLySanPhamAdapter.notifyDataSetChanged();
                        txtManageSearchNone.setVisibility(View.VISIBLE);
                    }
                },
                throwable -> {
                    Toast.makeText(getApplicationContext(), "Không kết nối được với server " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
            ));
    }

    private void getAllSanPham() {
        compositeDisposable.add(apiQuanLyBanHang.getSPMoi()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                sanPhamMoiModel -> {
                    if(sanPhamMoiModel.isSuccess()) {
                        mangManageSearchSanPham = sanPhamMoiModel.getResult();
                        quanLySanPhamAdapter = new QuanLySanPhamAdapter(getApplicationContext(), mangManageSearchSanPham);
                        recyclerViewManageSearchSP.setAdapter(quanLySanPhamAdapter);
                    }
                },
                throwable -> {
                    Toast.makeText(getApplicationContext(), "Không kết nối được với server " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
        ));
    }

    private void openAddSP() {
        imgManageAddSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addSP = new Intent(getApplicationContext(), ThemSuaSanPhamActivity.class);
                startActivity(addSP);
            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals("Sửa"))
            suaSanPham();
        else if(item.getTitle().equals("Xoá")) {
            AlertDialog.Builder alertDialogConfirm = new AlertDialog.Builder(this);
            alertDialogConfirm.setTitle("Thông báo!");
            alertDialogConfirm.setMessage("Bạn có muốn xoá sản phẩm này?");
            alertDialogConfirm.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    xoaSanPham();
                }
            });
            alertDialogConfirm.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialogConfirm.show();
        }
        return super.onContextItemSelected(item);
    }

    private void suaSanPham() {
        Intent suaSP = new Intent(getApplicationContext(), ThemSuaSanPhamActivity.class);
        suaSP.putExtra("suasanpham", suaXoaSanPham);
        startActivity(suaSP);
    }

    private void xoaSanPham() {
        compositeDisposable.add(apiQuanLyBanHang.getSPTrangThaiDonHang()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                sanPhamTrangThaiDonHangModel -> {
                    if(sanPhamTrangThaiDonHangModel.isSuccess()) {
                        boolean checkBeforeDelete = false;
                        mangSanPhamTrangThaiDonHang = sanPhamTrangThaiDonHangModel.getResult();
                        for(int i = 0; i < mangSanPhamTrangThaiDonHang.size(); i++) {
                            if(mangSanPhamTrangThaiDonHang.get(i).getIdSP() == suaXoaSanPham.getIdSP()) {
                                if(mangSanPhamTrangThaiDonHang.get(i).getTrangThai() == 3 || mangSanPhamTrangThaiDonHang.get(i).getTrangThai() == 4)
                                    checkBeforeDelete = true;
                                else {
                                    checkBeforeDelete = false;
                                    break;
                                }
                            }
                            else
                                checkBeforeDelete = true;
                        }
                        if(checkBeforeDelete) {
                            if(spinnerSearchTheoLoai.getSelectedItemPosition() == 1)
                                strTheoLoai = "DT";
                            else if(spinnerSearchTheoLoai.getSelectedItemPosition() == 2)
                                strTheoLoai = "LT";
                            else if(spinnerSearchTheoLoai.getSelectedItemPosition() == 3)
                                strTheoLoai = "MTB";
                            else
                                strTheoLoai = "";
                            compositeDisposable.add(apiQuanLyBanHang.deleteSP(suaXoaSanPham.getIdSP())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    messageModel -> {
                                        if(messageModel.isSuccess()) {
                                            Toast.makeText(getApplicationContext(), "Xoá thành công", Toast.LENGTH_SHORT).show();
                                            if(editTxtManageSearchSP.getText().toString().trim().length() != 0)
                                                searchSanPham(editTxtManageSearchSP.getText().toString().trim(), strTheoLoai, spinnerSearchTheoGia.getSelectedItemPosition());
                                            else
                                                getAllSanPham();
                                        }
                                        else
                                            Toast.makeText(getApplicationContext(), "Xoá không thành công", Toast.LENGTH_SHORT).show();
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), "Không kết nối được với server " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                            ));
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Không thể xoá khi sản phẩm đang được đặt", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Tải thông tin không thành công", Toast.LENGTH_SHORT).show();
                },
                throwable -> {
                    Toast.makeText(getApplicationContext(), "Không kết nối được với server " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                }
        ));
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventSuaXoa(SuaXoaEvent event) {
        if(event != null)
            suaXoaSanPham = event.getSanPhamMoi();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(editTxtManageSearchSP.getText().toString().trim().length() != 0)
            searchSanPham(editTxtManageSearchSP.getText().toString().trim(), strTheoLoai, spinnerSearchTheoGia.getSelectedItemPosition());
        else {
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
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}