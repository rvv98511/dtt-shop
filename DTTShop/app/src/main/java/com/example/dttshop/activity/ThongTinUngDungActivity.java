package com.example.dttshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dttshop.R;

public class ThongTinUngDungActivity extends AppCompatActivity {

    Toolbar toolbarThongTinApp;
    ImageView imgBgThongTinApp;
    TextView txtCoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_ung_dung);

        anhXaThongTin();
        actionToolBar();
        allEvent();
    }

    private void anhXaThongTin() {
        toolbarThongTinApp = findViewById(R.id.toolBarThongTinApp);
        imgBgThongTinApp = findViewById(R.id.imageViewBgThongTinApp);
        txtCoder = findViewById(R.id.textViewCoder);
    }

    private void actionToolBar() {
        setSupportActionBar(toolbarThongTinApp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarThongTinApp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void allEvent() {
        Glide.with(getApplicationContext()).asGif().load(R.drawable.background_info).into(imgBgThongTinApp);
        txtCoder.setSelected(true);
    }
}