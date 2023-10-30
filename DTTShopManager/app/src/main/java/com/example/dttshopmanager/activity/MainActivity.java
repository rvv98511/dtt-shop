package com.example.dttshopmanager.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.dttshopmanager.R;
import com.example.dttshopmanager.adapter.DanhSachMenuAdapter;
import com.example.dttshopmanager.adapter.SanPhamMoiAdapter;
import com.example.dttshopmanager.model.DanhSachMenu;
import com.example.dttshopmanager.model.SanPhamMoi;
import com.example.dttshopmanager.retrofit.ApiQuanLyBanHang;
import com.example.dttshopmanager.retrofit.RetrofitClient;
import com.example.dttshopmanager.utils.Utils;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolBarTrangChu;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewSPMoi;
    NavigationView navigationViewDSMenu;
    ListView listViewMenu;
    DanhSachMenuAdapter danhSachMenuAdapter;
    ArrayList<DanhSachMenu> mangDanhSachMenu;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiQuanLyBanHang apiQuanLyBanHang;
    ArrayList<SanPhamMoi> mangSanPhamMoi;
    SanPhamMoiAdapter sanPhamMoiAdapter;
    SwipeRefreshLayout swipeRefreshLayoutSanPhamMoi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhXaThongTin();
        actionToolBar();

        if(isConnected(this)) {
            actionViewFlipper();
            actionRefreshLayout();
            getDanhSachMenu();
            getSanPhamMoi();
            getEventClickMenu();
        }
        else
            Toast.makeText(getApplicationContext(), "Không có kết nối mạng, vui lòng kết nối!", Toast.LENGTH_LONG).show();
    }

    private void anhXaThongTin() {
        apiQuanLyBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiQuanLyBanHang.class);

        drawerLayout = findViewById(R.id.drawerLayout);
        toolBarTrangChu = findViewById(R.id.toolBarTrangChu);
        viewFlipper = findViewById(R.id.viewFilpper);
        recyclerViewSPMoi = findViewById(R.id.recyclerViewSPMoi);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerViewSPMoi.setLayoutManager(layoutManager);
        recyclerViewSPMoi.setHasFixedSize(true);
        navigationViewDSMenu = findViewById(R.id.navigationViewDSMenu);
        listViewMenu = findViewById(R.id.listViewMenu);
        swipeRefreshLayoutSanPhamMoi = findViewById(R.id.swipeRefreshLayoutSPMoi);

        mangDanhSachMenu = new ArrayList<>();
        mangSanPhamMoi = new ArrayList<>();
    }

    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected()))
            return true;
        else
            return false;
    }

    private void actionToolBar() {
        setSupportActionBar(toolBarTrangChu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBarTrangChu.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolBarTrangChu.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void actionRefreshLayout() {
        swipeRefreshLayoutSanPhamMoi.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSanPhamMoi();
                swipeRefreshLayoutSanPhamMoi.setRefreshing(false);
            }
        });
    }

    private void actionViewFlipper() {
        ArrayList<String> mangQuangCao = new ArrayList<>();
        mangQuangCao.add(Utils.BASE_URL + "images/quangcao/banner-1.png");
        mangQuangCao.add(Utils.BASE_URL + "images/quangcao/banner-2.png");
        mangQuangCao.add(Utils.BASE_URL + "images/quangcao/banner-3.png");
        for(int i = 0; i < mangQuangCao.size(); i++) {
            ImageView anhQuangCao = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangQuangCao.get(i)).into(anhQuangCao);
            anhQuangCao.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(anhQuangCao);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);

        Animation slideIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation slideOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_left);
        viewFlipper.setInAnimation(slideIn);
        viewFlipper.setOutAnimation(slideOut);
    }

    private void getDanhSachMenu() {
        compositeDisposable.add(apiQuanLyBanHang.getDSMenu()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                danhSachMenuModel -> {
                    if(danhSachMenuModel.isSuccess()) {
                        mangDanhSachMenu = danhSachMenuModel.getResult();
                        danhSachMenuAdapter = new DanhSachMenuAdapter(getApplicationContext(), mangDanhSachMenu);
                        listViewMenu.setAdapter(danhSachMenuAdapter);
                    }
                },
                throwable -> {
                    Toast.makeText(getApplicationContext(), "Không kết nối được với server" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
        ));
    }

    private void getSanPhamMoi() {
        compositeDisposable.add(apiQuanLyBanHang.getSPMoi()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                sanPhamMoiModel -> {
                    if(sanPhamMoiModel.isSuccess()) {
                        mangSanPhamMoi = sanPhamMoiModel.getResult();
                        sanPhamMoiAdapter = new SanPhamMoiAdapter(getApplicationContext(), mangSanPhamMoi);
                        recyclerViewSPMoi.setAdapter(sanPhamMoiAdapter);
                    }
                },
                throwable -> {
                    Toast.makeText(getApplicationContext(), "Không kết nối được với server" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
        ));
    }

    private void getEventClickMenu() {
        listViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        drawerLayout.closeDrawer(navigationViewDSMenu);
                        getSanPhamMoi();
                        break;
                    case 1:
                        drawerLayout.closeDrawer(navigationViewDSMenu);
                        Intent dienThoai = new Intent(getApplicationContext(), LoaiSanPhamActivity.class);
                        dienThoai.putExtra("loai", "DT");
                        startActivity(dienThoai);
                        break;
                    case 2:
                        drawerLayout.closeDrawer(navigationViewDSMenu);
                        Intent laptop = new Intent(getApplicationContext(), LoaiSanPhamActivity.class);
                        laptop.putExtra("loai", "LT");
                        startActivity(laptop);
                        break;
                    case 3:
                        drawerLayout.closeDrawer(navigationViewDSMenu);
                        Intent mayTinhBang = new Intent(getApplicationContext(), LoaiSanPhamActivity.class);
                        mayTinhBang.putExtra("loai", "MTB");
                        startActivity(mayTinhBang);
                        break;
                    case 4:
                        drawerLayout.closeDrawer(navigationViewDSMenu);
                        Intent quanLySP = new Intent(getApplicationContext(), QuanLySanPhamActivity.class);
                        startActivity(quanLySP);
                        break;
                    case 5:
                        drawerLayout.closeDrawer(navigationViewDSMenu);
                        Intent quanLyDH = new Intent(getApplicationContext(), QuanLyDonHangActivity.class);
                        startActivity(quanLyDH);
                        break;
                    case 6:
                        drawerLayout.closeDrawer(navigationViewDSMenu);
                        Intent thongKe = new Intent(getApplicationContext(), ThongKeActivity.class);
                        startActivity(thongKe);
                        break;
                    case 7:
                        drawerLayout.closeDrawer(navigationViewDSMenu);
                        Intent changPassword = new Intent(getApplicationContext(), DoiMatKhauActivity.class);
                        startActivity(changPassword);
                        break;
                    case 8:
                        drawerLayout.closeDrawer(navigationViewDSMenu);
                        Paper.book().destroy();
                        Intent logIn = new Intent(getApplicationContext(), DangNhapActivity.class);
                        startActivity(logIn);
                        finish();
                        break;
                    case 9:
                        drawerLayout.closeDrawer(navigationViewDSMenu);
                        Intent thongTinUngDung = new Intent(getApplicationContext(), ThongTinUngDungActivity.class);
                        startActivity(thongTinUngDung);
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSanPhamMoi();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
