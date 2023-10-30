package com.example.dttshopmanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.dttshopmanager.R;
import com.example.dttshopmanager.utils.Utils;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Paper.init(this);
        threadOpenAcitivity();
    }

    private void threadOpenAcitivity() {
        Thread thread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Không thể kết nối với server", Toast.LENGTH_SHORT).show();
                }
                finally {
                    if(Paper.book().read("user") != null) {
                        finish();
                        Utils.currentUser = Paper.book().read("user");
                        Intent trangChu = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(trangChu);
                    }
                    else {
                        finish();
                        Intent login = new Intent(getApplicationContext(), DangNhapActivity.class);
                        startActivity(login);
                    }
                }
            }
        };
        thread.start();
    }
}