package com.example.dttshopmanager.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dttshopmanager.R;
import com.example.dttshopmanager.adapter.DonHangAdapter;
import com.example.dttshopmanager.model.DonHang;
import com.example.dttshopmanager.model.EventBus.DonHangEvent;
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

public class QuanLyDonHangActivity extends AppCompatActivity {

    Toolbar toolbarQuanLyDH;
    TextView txtQuanLyDonHangRong;
    SwipeRefreshLayout swipeRefreshLayoutQuanLyDH;
    RecyclerView recyclerViewQuanLyDonHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiQuanLyBanHang apiQuanLyBanHang;
    ArrayList<DonHang> mangDonHang;
    DonHangAdapter donHangAdapter;
    DonHang donHang;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_don_hang);

        anhXaThongTin();
        actionToolBar();
        actionRefreshLayout();
        loadDonHang();
    }

    private void anhXaThongTin() {
        apiQuanLyBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiQuanLyBanHang.class);

        toolbarQuanLyDH = findViewById(R.id.toolBarQuanLyDonHang);
        txtQuanLyDonHangRong = findViewById(R.id.textViewQuanLyDonHangRong);
        swipeRefreshLayoutQuanLyDH = findViewById(R.id.swipeRefreshLayoutQuanLyDH);
        recyclerViewQuanLyDonHang = findViewById(R.id.recyclerViewQuanLyDonHang);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewQuanLyDonHang.setLayoutManager(layoutManager);
        recyclerViewQuanLyDonHang.setHasFixedSize(true);

        mangDonHang = new ArrayList<>();
    }

    private void actionToolBar() {
        setSupportActionBar(toolbarQuanLyDH);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarQuanLyDH.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void actionRefreshLayout() {
        swipeRefreshLayoutQuanLyDH.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDonHang();
                swipeRefreshLayoutQuanLyDH.setRefreshing(false);
            }
        });
    }

    private void loadDonHang() {
        compositeDisposable.add(apiQuanLyBanHang.getDonHang()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                donHangModel -> {
                    if(donHangModel.isSuccess()) {
                        txtQuanLyDonHangRong.setVisibility(View.GONE);
                        mangDonHang = donHangModel.getResult();
                        donHangAdapter = new DonHangAdapter(getApplicationContext(), mangDonHang);
                        recyclerViewQuanLyDonHang.setAdapter(donHangAdapter);
                    }
                    else
                        txtQuanLyDonHangRong.setVisibility(View.VISIBLE);
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
    public void eventDonHang(DonHangEvent event) {
        if(event != null) {
            donHang = event.getDonHang();
            openDialog();
        }
    }

    private void openDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_trang_thai_don_hang);

        Window window = dialog.getWindow();
        if (window == null)
            return;
        else {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            WindowManager.LayoutParams windowAttributes = window.getAttributes();
            windowAttributes.gravity = Gravity.CENTER;
            window.setAttributes(windowAttributes);

            TextView txtMaDonHangDialog = dialog.findViewById(R.id.textViewMaDonHangDialog);
            txtMaDonHangDialog.setText(donHang.getIdDonHang() + "");
            Spinner spinnerDialog = dialog.findViewById(R.id.spinnerTrangThaiDonHang);
            AppCompatButton appCompatBtnUpdateDonHang = dialog.findViewById(R.id.appCompatButtonUpdateTrangThaiDonHang);
            String[] trangThaiDonHang = new String[]{"Đang chờ xử lý", "Đã chấp nhận", "Đơn vị đang vận chuyển", "Đã nhận thành công", "Đã bị huỷ"};
            ArrayAdapter<String> arrayAdapterSpinner = new ArrayAdapter<>(this, R.layout.item_spinner_centered, trangThaiDonHang);
            spinnerDialog.setAdapter(arrayAdapterSpinner);
            spinnerDialog.setSelection(donHang.getTrangThai());

            appCompatBtnUpdateDonHang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    capNhatDonHang(spinnerDialog.getSelectedItemPosition());
                }
            });


            dialog.show();
        }
    }

    private void capNhatDonHang(int trangThai) {
        compositeDisposable.add(apiQuanLyBanHang.updateDonHang(donHang.getIdDonHang(), trangThai)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                messageModel -> {
                    if(messageModel.isSuccess()) {
                        loadDonHang();
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
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