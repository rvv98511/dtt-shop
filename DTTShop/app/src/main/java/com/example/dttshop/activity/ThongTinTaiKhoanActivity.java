package com.example.dttshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dttshop.R;
import com.example.dttshop.retrofit.ApiBanHang;
import com.example.dttshop.retrofit.RetrofitClient;
import com.example.dttshop.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThongTinTaiKhoanActivity extends AppCompatActivity {

    Toolbar toolbarAccountInfo;
    TextView txtUserName, txtTenKH;
    TextInputEditText editTxtTenKH, editTxtPhone, editTxtEmail, editTxtPassword, editTxtRetypePassword;
    AppCompatButton appCompatBtnTenKHUpdate, appCompatBtnPhoneUpdate, appCompatBtnEmailUpdate, appCompatBtnNewPasswordUpdate;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_tai_khoan);

        anhXaThongTin();
        actionToolBar();
        loadInformation();
    }

    private void anhXaThongTin() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        toolbarAccountInfo = findViewById(R.id.toolBarAccountInfo);
        txtUserName = findViewById(R.id.textViewUserName);
        txtTenKH = findViewById(R.id.textViewTenKH);
        editTxtTenKH = findViewById(R.id.editTextTenKHUpdate);
        editTxtPhone = findViewById(R.id.editTextPhoneUpdate);
        editTxtEmail = findViewById(R.id.editTextEmailUpdate);
        editTxtPassword = findViewById(R.id.editTextNewPasswordUpdate);
        editTxtRetypePassword = findViewById(R.id.editTextRetypeNewPasswordUpdate);
        appCompatBtnTenKHUpdate = findViewById(R.id.appCompatButtonTenKHUpdate);
        appCompatBtnPhoneUpdate = findViewById(R.id.appCompatButtonPhoneUpdate);
        appCompatBtnEmailUpdate = findViewById(R.id.appCompatButtonEmailUpdate);
        appCompatBtnNewPasswordUpdate = findViewById(R.id.appCompatButtonNewPasswordUpdate);
    }

    private void actionToolBar() {
        setSupportActionBar(toolbarAccountInfo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarAccountInfo.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void loadInformation() {
        txtUserName.setText(Utils.currentUser.getUserName());
        txtTenKH.setText("Xin chào, " + Utils.currentUser.getTenKH() + "!");
        editTxtTenKH.setText(Utils.currentUser.getTenKH());
        editTxtPhone.setText(Utils.currentUser.getSoDienThoai());
        editTxtEmail.setText(Utils.currentUser.getEmail());

        editTxtTenKH.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().equals(Utils.currentUser.getTenKH()) || s.length() == 0)
                    appCompatBtnTenKHUpdate.setEnabled(false);
                else {
                    appCompatBtnTenKHUpdate.setEnabled(true);
                    appCompatBtnTenKHUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btnUpdateTenKHClick(Utils.currentUser.getUserName(), s.toString().trim());
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTxtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().equals(Utils.currentUser.getSoDienThoai()) || s.length() == 0)
                    appCompatBtnPhoneUpdate.setEnabled(false);
                else {
                    appCompatBtnPhoneUpdate.setEnabled(true);
                    appCompatBtnPhoneUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(s.length() > 10 || s.length() < 10)
                                editTxtPhone.setError("Số điện thoại phải 10 chữ số");
                            else if(!phoneNumberChecker(s.toString()))
                                editTxtPhone.setError("Đầu số không hợp lệ");
                            else
                                btnUpdatePhoneClick(Utils.currentUser.getUserName(), s.toString().trim());
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTxtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().toLowerCase().trim().equals(Utils.currentUser.getEmail()) || s.length() == 0)
                    appCompatBtnEmailUpdate.setEnabled(false);
                else {
                    appCompatBtnEmailUpdate.setEnabled(true);
                    appCompatBtnEmailUpdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches())
                                editTxtEmail.setError("Không đúng định dạng");
                            else
                                btnUpdateEmailClick(Utils.currentUser.getUserName(), s.toString().toLowerCase().trim());
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
                                        if (s1.length() < 8) {
                                            editTxtPassword.setError("Mật khẩu phải 8 kí tự");
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

    private void btnUpdateTenKHClick(String strUserName, String strTenKH) {
        compositeDisposable.add(apiBanHang.updateTaiKhoanKH(strUserName, strTenKH, null, null, null)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    userModel -> {
                        if(userModel.isSuccess()) {
                            appCompatBtnTenKHUpdate.setEnabled(false);
                            Utils.currentUser.setTenKH(strTenKH);
                            Paper.book().write("user", Utils.currentUser);
                            txtTenKH.setText("Xin chào, " + Utils.currentUser.getTenKH() + "!");
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

    private void btnUpdatePhoneClick(String strUserName, String strPhone) {
        compositeDisposable.add(apiBanHang.updateTaiKhoanKH(strUserName, null, strPhone, null, null)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    userModel -> {
                        if(userModel.isSuccess()) {
                            appCompatBtnPhoneUpdate.setEnabled(false);
                            Utils.currentUser.setSoDienThoai(strPhone);
                            Paper.book().write("user", Utils.currentUser);
                            Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(), userModel.getMessage(), Toast.LENGTH_SHORT).show();
                    },
                    throwable -> {
                        Toast.makeText(getApplicationContext(), "Không kết nối được với server " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
            ));
    }

    private void btnUpdateEmailClick(String strUserName, String strEmail) {
        compositeDisposable.add(apiBanHang.updateTaiKhoanKH(strUserName, null, null, strEmail, null)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    userModel -> {
                        if(userModel.isSuccess()) {
                            appCompatBtnEmailUpdate.setEnabled(false);
                            Utils.currentUser.setEmail(strEmail);
                            Paper.book().write("user", Utils.currentUser);
                            Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(), userModel.getMessage(), Toast.LENGTH_SHORT).show();
                    },
                    throwable -> {
                        Toast.makeText(getApplicationContext(), "Không kết nối được với server " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
            ));
    }

    private void btnUpdatePasswordClick(String strUserName, String strPassword) {
        compositeDisposable.add(apiBanHang.updateTaiKhoanKH(strUserName, null, null, null, strPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if(userModel.isSuccess()) {
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
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}