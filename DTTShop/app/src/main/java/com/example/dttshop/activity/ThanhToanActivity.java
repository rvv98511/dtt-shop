package com.example.dttshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dttshop.R;
import com.example.dttshop.retrofit.ApiBanHang;
import com.example.dttshop.retrofit.RetrofitClient;
import com.example.dttshop.utils.Utils;
import com.example.dttshop.zalopay.Api.CreateOrder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class ThanhToanActivity extends AppCompatActivity {

    Toolbar toolbarThanhToan;
    TextInputEditText editTxtTenKH, editTxtPhone, editTxtEmail, editTxtAddress, editTxtDate;
    TextView txtTongSoLuongMua, txtTongTien;
    RadioButton radioBtnChuyenKhoanZaloPay;
    AppCompatButton appCompatBtnDatHang;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    long tongTien;
    int total;
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        anhXaThongTin();
        actionToolBar();
        xuLyMuaHang();
    }

    private void anhXaThongTin() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        toolbarThanhToan = findViewById(R.id.toolBarThanhToan);
        editTxtTenKH = findViewById(R.id.editTextTenKHDatHang);
        editTxtPhone = findViewById(R.id.editTextPhoneDatHang);
        editTxtEmail = findViewById(R.id.editTextEmailDatHang);
        editTxtAddress = findViewById(R.id.editTextAddressDatHang);
        editTxtDate = findViewById(R.id.editTextDateDatHang);
        txtTongSoLuongMua = findViewById(R.id.textViewTongSoLuongMua);
        txtTongTien = findViewById(R.id.textViewTongTienMuaHang);
        radioBtnChuyenKhoanZaloPay = findViewById(R.id.radioButtonChuyenKhoanZaloPay);
        appCompatBtnDatHang = findViewById(R.id.appCompatButtonDatHang);
    }

    private void actionToolBar() {
        setSupportActionBar(toolbarThanhToan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarThanhToan.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Utils.mangMuaHang.clear();
            }
        });
    }

    private void xuLyMuaHang() {
        editTxtTenKH.setText(Utils.currentUser.getTenKH());
        editTxtPhone.setText(Utils.currentUser.getSoDienThoai());
        editTxtEmail.setText(Utils.currentUser.getEmail());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dateFormat.format(calendar.getTime());
        editTxtDate.setText(currentDate);

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongTien = getIntent().getLongExtra("tongtien", 0);
        txtTongTien.setText(decimalFormat.format(tongTien) + " ₫");

        total = 0;
        for(int i = 0; i < Utils.mangMuaHang.size(); i++)
            total = total + Utils.mangMuaHang.get(i).getSoLuongMua();
        txtTongSoLuongMua.setText(total + "");

        appCompatBtnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInfoMuaHang();
            }
        });
    }

    private void checkInfoMuaHang() {
        String strUserName, strTenKH, strPhone, strEmail, strAddress, strHinhThuc;
        strUserName = Utils.currentUser.getUserName();
        strTenKH = editTxtTenKH.getText().toString().trim();
        strPhone = editTxtPhone.getText().toString().trim();
        strEmail = editTxtEmail.getText().toString().trim();
        strAddress = editTxtAddress.getText().toString().trim();
        SimpleDateFormat dateFormatSql = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateSql = dateFormatSql.format(calendar.getTime());

        if(radioBtnChuyenKhoanZaloPay.isChecked())
            strHinhThuc = "Chuyển khoản ZaloPay";
        else
            strHinhThuc = "Tiền mặt";

        if(TextUtils.isEmpty(strTenKH)) {
            editTxtTenKH.setError("Không được để trống");
            editTxtTenKH.requestFocus();
        }
        else if(TextUtils.isEmpty(strPhone)) {
            editTxtPhone.setError("Không được để trống");
            editTxtPhone.requestFocus();
        }
        else if(strPhone.length() > 10 || strPhone.length() < 10) {
            editTxtPhone.setError("Số điện thoại phải 10 chữ số");
            editTxtPhone.requestFocus();
        }
        else if(!phoneNumberChecker(strPhone)) {
            editTxtPhone.setError("Đầu số không hợp lệ");
            editTxtPhone.requestFocus();
        }
        else if(TextUtils.isEmpty(strEmail)) {
            editTxtEmail.setError("Không được để trống");
            editTxtEmail.requestFocus();
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            editTxtEmail.setError("Không đúng định dạng");
            editTxtEmail.requestFocus();
        }
        else if(TextUtils.isEmpty(strAddress)) {
            editTxtAddress.setError("Không được để trống");
            editTxtAddress.requestFocus();
        }
        else {
            if(radioBtnChuyenKhoanZaloPay.isChecked()) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                ZaloPaySDK.init(553, Environment.SANDBOX);

                CreateOrder orderApi = new CreateOrder();

                try {
                    JSONObject data = orderApi.createOrder(String.valueOf(tongTien));
                    String code = data.getString("returncode");
                    if (code.equals("1")) {
                        String token = data.getString("zptranstoken");
                        ZaloPaySDK.getInstance().payOrder(ThanhToanActivity.this, token, "demozpdk://app", new PayOrderListener() {
                            @Override
                            public void onPaymentSucceeded(String s, String s1, String s2) {
                                compositeDisposable.add(apiBanHang.createOrder(strUserName, strTenKH, strPhone, strEmail, strAddress, total, String.valueOf(tongTien), currentDateSql, strHinhThuc, token, new Gson().toJson(Utils.mangMuaHang))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        messageModel -> {
                                            if(messageModel.isSuccess()) {
                                                compositeDisposable.add(apiBanHang.upDateSoLuongSP(strUserName)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe());

                                                Toast.makeText(getApplicationContext(), "Đặt hàng thành công", Toast.LENGTH_LONG).show();
                                                for(int i = 0; i < Utils.mangGioHang.size(); i++) {
                                                    for(int j = 0; j < Utils.mangMuaHang.size(); j++) {
                                                        if(Utils.mangGioHang.get(i).getIdSP() == Utils.mangMuaHang.get(j).getIdSP())
                                                            Utils.mangGioHang.remove(i);
                                                    }
                                                }
                                                Utils.mangMuaHang.clear();
                                                Paper.book().write("giohang", Utils.mangGioHang);
                                                finish();
                                            }
                                            else
                                                Toast.makeText(getApplicationContext(), "Bị lỗi đặt hàng", Toast.LENGTH_LONG).show();
                                        },
                                        throwable -> {
                                            Toast.makeText(getApplicationContext(), "Không kết nối được với server " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                ));
                            }

                            @Override
                            public void onPaymentCanceled(String s, String s1) {

                            }

                            @Override
                            public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {

                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                compositeDisposable.add(apiBanHang.createOrder(strUserName, strTenKH, strPhone, strEmail, strAddress, total, String.valueOf(tongTien), currentDateSql, strHinhThuc, null, new Gson().toJson(Utils.mangMuaHang))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                            if(messageModel.isSuccess()) {
                                compositeDisposable.add(apiBanHang.upDateSoLuongSP(strUserName)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe());

                                Toast.makeText(getApplicationContext(), "Đặt hàng thành công", Toast.LENGTH_LONG).show();
                                for(int i = 0; i < Utils.mangGioHang.size(); i++) {
                                    for(int j = 0; j < Utils.mangMuaHang.size(); j++) {
                                        if(Utils.mangGioHang.get(i).getIdSP() == Utils.mangMuaHang.get(j).getIdSP())
                                            Utils.mangGioHang.remove(i);
                                    }
                                }
                                Utils.mangMuaHang.clear();
                                Paper.book().write("giohang", Utils.mangGioHang);
                                finish();
                            }
                            else
                                Toast.makeText(getApplicationContext(), "Bị lỗi đặt hàng", Toast.LENGTH_LONG).show();
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối được với server " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                ));
            }
        }
    }

    private static boolean phoneNumberChecker(String phoneNumber) {
        String[] networkCodes = {"032", "033", "034", "035", "036", "036",
                "037", "038", "039", "052", "056", "058",
                "059", "070", "076", "077", "078", "079",
                "081", "082", "083", "084", "085", "086",
                "087", "088", "089", "090", "091", "092",
                "093", "094", "096", "097", "098", "099"};
        for(String code : networkCodes) {
            if(phoneNumber.startsWith(code))
                return true;
        }
        return false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}