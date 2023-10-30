package com.example.dttshop.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.dttshop.R;

public class LienHeActivity extends AppCompatActivity {

    Toolbar toolbarLienHe;
    ImageView imageViewGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lien_he);

        anhXaThongTin();
        actionToolBar();
    }

    private void anhXaThongTin() {
        toolbarLienHe = findViewById(R.id.toolBarLienHe);
        imageViewGoogleMap = findViewById(R.id.imageViewGoogleMap);
        imageViewGoogleMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = "111 Lý Nam Đế, Phường 7, Quận 11, Thành phố Hồ Chí Minh, Việt Nam";
                openGoogleMap(address);
            }
        });
    }

    private void actionToolBar() {
        setSupportActionBar(toolbarLienHe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLienHe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void openGoogleMap(String address) {
        Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if(mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }
}