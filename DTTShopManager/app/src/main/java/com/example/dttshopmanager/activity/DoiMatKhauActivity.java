package com.example.dttshopmanager.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class DoiMatKhauActivity extends AppCompatActivity {

    Toolbar toolbarDoiMatKhau;
    TextInputEditText editTxtPassword, editTxtRetypePassword;
    AppCompatButton appCompatBtnNewPasswordUpdate;
    ApiQuanLyBanHang apiQuanLyBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);

        anhXaThongTin();
        actionToolBar();
        checkInputPassword();
    }

    private void anhXaThongTin() {
        apiQuanLyBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiQuanLyBanHang.class);

        toolbarDoiMatKhau = findViewById(R.id.toolBarDoiMatKhau);
        editTxtPassword = findViewById(R.id.editTextNewPasswordUpdate);
        editTxtRetypePassword = findViewById(R.id.editTextRetypeNewPasswordUpdate);
        appCompatBtnNewPasswordUpdate = findViewById(R.id.appCompatButtonNewPasswordUpdate);
    }

    private void actionToolBar() {
        setSupportActionBar(toolbarDoiMatKhau);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDoiMatKhau.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void checkInputPassword() {
        editTxtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s1, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s1, int start, int before, int count) {
                if(s1.length() == 0)
                    appCompatBtnNewPasswordUpdate.setEnabled(false);
                else {
                    editTxtRetypePassword.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s2, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s2, int start, int before, int count) {
                            if(s2.length() == 0)
                                appCompatBtnNewPasswordUpdate.setEnabled(false);
                            else {
                                appCompatBtnNewPasswordUpdate.setEnabled(true);
                                appCompatBtnNewPasswordUpdate.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(s1.length() < 8) {
                                            editTxtPassword.setError("Mật khẩu phải 8 ký tự");
                                            editTxtPassword.requestFocus();
                                        }
                                        else if(s1.toString().trim().equals(s2.toString().trim()))
                                            btnUpdatePasswordClick(Utils.currentUser.getUserName(), s1.toString().trim());
                                        else {
                                            editTxtRetypePassword.setError("Mật khẩu mới chưa khớp");
                                            editTxtRetypePassword.requestFocus();
                                        }
                                    }
                                });
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s2) {

                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s1) {

            }
        });
    }

    private void btnUpdatePasswordClick(String strUserName, String strPassword) {
        compositeDisposable.add(apiQuanLyBanHang.updatePassword(strUserName, strPassword)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                messageModel -> {
                    if(messageModel.isSuccess()) {
                        appCompatBtnNewPasswordUpdate.setEnabled(false);
                        editTxtPassword.getText().clear();
                        editTxtRetypePassword.getText().clear();
                        Utils.currentUser.setMatKhau(strPassword);
                        Paper.book().write("user", Utils.currentUser);
                        Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
                },
                throwable -> {
                    Toast.makeText(getApplicationContext(), "Không kết nối được với server " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
        ));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}