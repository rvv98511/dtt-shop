package com.example.dttshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
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

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangNhapActivity extends AppCompatActivity {

    TextInputEditText editTxtUserName, editTxtPassword;
    TextView txtSignUp;
    AppCompatButton appCompatBtnLogIn;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        anhXaThongTin();
        eventLogIn();
        openSignUp();
    }

    private void anhXaThongTin() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        editTxtUserName = findViewById(R.id.editTextUserNameLogin);
        editTxtPassword = findViewById(R.id.editTextPasswordLogin);
        txtSignUp = findViewById(R.id.textViewSignUp);
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
            compositeDisposable.add(apiBanHang.getLogIn(strUserName, strPassword)
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

    private void openSignUp() {
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(getApplicationContext(), DangKyActivity.class);
                startActivity(signUp);
            }
        });
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