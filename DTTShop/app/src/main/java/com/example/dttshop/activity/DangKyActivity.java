package com.example.dttshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dttshop.R;
import com.example.dttshop.retrofit.ApiBanHang;
import com.example.dttshop.retrofit.RetrofitClient;
import com.example.dttshop.utils.Utils;
import com.google.android.material.textfield.TextInputEditText;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangKyActivity extends AppCompatActivity {

    TextInputEditText editTxtUserName, editTxtTenKH, editTxtPhone, editTxtEmail, editTxtPassword, editTxtRetypePassword;
    AppCompatButton appCompatBtnSignUp;
    TextView txtLogin;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        anhXaThongTin();
        eventSignUp();
        openLogin();
    }

    private void anhXaThongTin() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        editTxtUserName = findViewById(R.id.editTextUserNameSignUp);
        editTxtTenKH = findViewById(R.id.editTextTenKHSignUp);
        editTxtPhone = findViewById(R.id.editTextPhoneSignUp);
        editTxtEmail = findViewById(R.id.editTextEmailSignUp);
        editTxtPassword = findViewById(R.id.editTextPasswordSignUp);
        editTxtRetypePassword = findViewById(R.id.editTextRetypePasswordSignUp);
        appCompatBtnSignUp = findViewById(R.id.appCompatButtonSignUp);
        txtLogin = findViewById(R.id.textViewLogin);
    }
    private void eventSignUp() {
        appCompatBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInputSignUp();
            }
        });
    }

    private void checkInputSignUp() {
        String strUserName, strTenKH, strPhone, strEmail, strPassword, strRetypePassword;
        strUserName = editTxtUserName.getText().toString().trim();
        strTenKH = editTxtTenKH.getText().toString().trim();
        strPhone = editTxtPhone.getText().toString().trim();
        strEmail = editTxtEmail.getText().toString().trim();
        strPassword = editTxtPassword.getText().toString().trim();
        strRetypePassword = editTxtRetypePassword.getText().toString().trim();

        if(TextUtils.isEmpty(strUserName)) {
            editTxtUserName.setError("Không được để trống");
            editTxtUserName.requestFocus();
        }
        else if(TextUtils.isEmpty(strTenKH)) {
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
        else {
            if(TextUtils.isEmpty(strPassword)) {
                editTxtPassword.setError("Không được để trống");
                editTxtPassword.requestFocus();
            }
            else if(strPassword.length() < 8) {
                editTxtPassword.setError("Mật khẩu phải 8 kí tự");
                editTxtPassword.requestFocus();
            }
            else if(TextUtils.isEmpty(strRetypePassword)) {
                editTxtRetypePassword.setError("Không được để trống");
                editTxtRetypePassword.requestFocus();
            }
            else if(strPassword.equals(strRetypePassword)) {
                compositeDisposable.add(apiBanHang.getSignUp(strUserName, strTenKH, strPhone, strEmail, strPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if(userModel.isSuccess()) {
                                Utils.currentUser.setUserName(strUserName);
                                finish();
                            }
                            else {
                                if(strUserName.equals(userModel.getResult().get(0).getUserName())) {
                                    editTxtUserName.setError(userModel.getMessage());
                                    editTxtUserName.requestFocus();
                                }
                                else if(strPhone.equals(userModel.getResult().get(0).getSoDienThoai())) {
                                    editTxtPhone.setError(userModel.getMessage());
                                    editTxtPhone.requestFocus();
                                }
                                else if(strEmail.equals(userModel.getResult().get(0).getEmail())) {
                                    editTxtEmail.setError(userModel.getMessage());
                                    editTxtEmail.requestFocus();
                                }
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối được với server " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                ));
            }
            else {
                editTxtRetypePassword.setError("Mật khẩu chưa khớp");
                editTxtRetypePassword.requestFocus();
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

    private void openLogin() {
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}