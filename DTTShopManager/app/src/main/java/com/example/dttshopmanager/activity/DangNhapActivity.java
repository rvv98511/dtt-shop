package com.example.dttshopmanager.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.dttshopmanager.R;
import com.example.dttshopmanager.retrofit.ApiQuanLyBanHang;
import com.example.dttshopmanager.retrofit.RetrofitClient;
import com.example.dttshopmanager.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangNhapActivity extends AppCompatActivity {

    TextInputEditText editTxtUserName, editTxtPassword;
    AppCompatButton appCompatBtnLogIn;
    ApiQuanLyBanHang apiQuanLyBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        anhXaThongTin();
        eventLogIn();
    }

    private void anhXaThongTin() {
        apiQuanLyBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiQuanLyBanHang.class);

        editTxtUserName = findViewById(R.id.editTextUserNameLogin);
        editTxtPassword = findViewById(R.id.editTextPasswordLogin);
        appCompatBtnLogIn = findViewById(R.id.appCompatButtonLogin);
    }

    private void eventLogIn() {
        appCompatBtnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInputLogIn();
            }
        });
    }

    private void checkInputLogIn() {
        String strUserName, strPassword;
        strUserName = editTxtUserName.getText().toString().trim();
        strPassword = editTxtPassword.getText().toString().trim();

        if(TextUtils.isEmpty(strUserName)) {
            editTxtUserName.setError("Không được để trống");
            editTxtUserName.requestFocus();
        }
        else if(TextUtils.isEmpty(strPassword)) {
            editTxtPassword.setError("Không được để trống");
            editTxtPassword.requestFocus();
        }
        else {
            compositeDisposable.add(apiQuanLyBanHang.getLogIn(strUserName, strPassword)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            userModel -> {
                                if(userModel.isSuccess()) {
                                    Utils.currentUser = userModel.getResult().get(0);
                                    Paper.book().write("user", Utils.currentUser);
                                    Intent trangChu = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(trangChu);
                                    finish();
                                }
                                else
                                    Toast.makeText(getApplicationContext(), "Tên tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_LONG).show();
                            },
                            throwable -> {
                                Toast.makeText(getApplicationContext(), "Không kết nối được với server " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                            }
                    ));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.currentUser.getUserName() != null)
            editTxtUserName.setText(Utils.currentUser.getUserName());
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}