package com.example.dttshopmanager.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.dttshopmanager.R;
import com.example.dttshopmanager.adapter.ThongKeHangTonAdapter;
import com.example.dttshopmanager.model.SanPhamMoi;
import com.example.dttshopmanager.retrofit.ApiQuanLyBanHang;
import com.example.dttshopmanager.retrofit.RetrofitClient;
import com.example.dttshopmanager.utils.Utils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThongKeActivity extends AppCompatActivity {

    Toolbar toolbarThongKe;
    PieChart pieChart;
    BarChart barChart;
    RecyclerView recyclerViewThongKeHangTon;
    ApiQuanLyBanHang apiQuanLyBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ArrayList<SanPhamMoi> mangThongKeHangTon;
    LinearLayoutManager linearLayoutManager;
    ThongKeHangTonAdapter thongKeHangTonAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);

        anhXaThongTin();
        actionToolbar();
        getDataPieChart();
    }

    private void anhXaThongTin() {
        apiQuanLyBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiQuanLyBanHang.class);

        toolbarThongKe = findViewById(R.id.toolBarThongKe);
        pieChart = findViewById(R.id.pieChart);
        barChart = findViewById(R.id.barChart);
        recyclerViewThongKeHangTon = findViewById(R.id.recyclerViewThongKeHangTon);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewThongKeHangTon.setLayoutManager(linearLayoutManager);
        recyclerViewThongKeHangTon.setHasFixedSize(true);
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarThongKe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarThongKe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_thong_ke, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menuThongKeSanPham:
                barChart.setVisibility(View.GONE);
                pieChart.setVisibility(View.VISIBLE);
                recyclerViewThongKeHangTon.setVisibility(View.GONE);
                getDataPieChart();
                break;
            case R.id.menuThongKeDoanhThu:
                pieChart.setVisibility(View.GONE);
                barChart.setVisibility(View.VISIBLE);
                recyclerViewThongKeHangTon.setVisibility(View.GONE);
                getDataBarChart();
                break;
            case R.id.menuThongKeHangTon:
                pieChart.setVisibility(View.GONE);
                barChart.setVisibility(View.GONE);
                recyclerViewThongKeHangTon.setVisibility(View.VISIBLE);
                getDataThongKeHangTon();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getDataPieChart() {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setRotationEnabled(true);
        pieChart.setEntryLabelTextSize(13f);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Thống kê sản phẩm");
        pieChart.setCenterTextSize(15f);
        pieChart.setCenterTextTypeface(Typeface.DEFAULT_BOLD);

        pieChart.animateXY(2000, 2000);

        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);

        compositeDisposable.add(apiQuanLyBanHang.getThongKeSP()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                thongKeSanPhamModel -> {
                    ArrayList<PieEntry> listThongKeSP = new ArrayList<>();

                    if(thongKeSanPhamModel.isSuccess()) {
                        for(int i = 0; i < thongKeSanPhamModel.getResult().size(); i++) {
                            String tenSP = thongKeSanPhamModel.getResult().get(i).getTenSP();
                            int soLanMua = thongKeSanPhamModel.getResult().get(i).getTongSoLuongMua();
                            listThongKeSP.add(new PieEntry(soLanMua, tenSP));
                        }

                        PieDataSet pieDataSet = new PieDataSet(listThongKeSP, "Thống kê sản phẩm");
                        ArrayList<Integer> listColors = new ArrayList<>();
                        for(int color : ColorTemplate.COLORFUL_COLORS)
                            listColors.add(color);
                        for(int color : ColorTemplate.JOYFUL_COLORS)
                            listColors.add(color);
                        for(int color : ColorTemplate.LIBERTY_COLORS)
                            listColors.add(color);
                        for(int color : ColorTemplate.MATERIAL_COLORS)
                            listColors.add(color);
                        for(int color : ColorTemplate.PASTEL_COLORS)
                            listColors.add(color);
                        for(int color : ColorTemplate.VORDIPLOM_COLORS)
                            listColors.add(color);
                        listColors.add(ColorTemplate.getHoloBlue());
                        pieDataSet.setColors(listColors);

                        PieData pieData = new PieData(pieDataSet);
                        pieData.setValueFormatter(new PercentFormatter(pieChart));
                        pieData.setValueTextSize(15f);
                        pieData.setValueTextColor(Color.BLACK);

                        pieChart.setData(pieData);
                        pieChart.invalidate();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Thống kê không thành công", Toast.LENGTH_SHORT).show();
                },
                throwable -> {
                    Toast.makeText(getApplicationContext(), "Không kết nối được với server " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                }
        ));
    }

    private void getDataBarChart() {
        barChart.getDescription().setEnabled(false);
        barChart.setDrawValueAboveBar(true);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setAxisMinimum(1);
        xAxis.setAxisMaximum(12);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,### ₫");
        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setAxisMinimum(0);
        yAxisLeft.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return decimalFormat.format(value);
            }
        });
        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setAxisMinimum(0);
        yAxisRight.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return decimalFormat.format(value);
            }
        });

        barChart.animateXY(2000, 2000);

        Legend legend = barChart.getLegend();
        legend.setEnabled(false);

        compositeDisposable.add(apiQuanLyBanHang.getThongKeDoanhThu()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                thongKeDoanhThuModel -> {
                    ArrayList<BarEntry> listThongKeDoanhThu = new ArrayList<>();

                    if(thongKeDoanhThuModel.isSuccess()) {
                        for(int i = 0; i < thongKeDoanhThuModel.getResult().size(); i++) {
                            long tongTienThang = thongKeDoanhThuModel.getResult().get(i).getTongTienThang();
                            int thang = thongKeDoanhThuModel.getResult().get(i).getThang();
                            listThongKeDoanhThu.add(new BarEntry(thang, tongTienThang));
                        }

                        BarDataSet barDataSet = new BarDataSet(listThongKeDoanhThu, "Thống kê doanh thu");
                        ArrayList<Integer> listColors = new ArrayList<>();
                        for(int color : ColorTemplate.COLORFUL_COLORS)
                            listColors.add(color);
                        for(int color : ColorTemplate.JOYFUL_COLORS)
                            listColors.add(color);
                        for(int color : ColorTemplate.LIBERTY_COLORS)
                            listColors.add(color);
                        for(int color : ColorTemplate.MATERIAL_COLORS)
                            listColors.add(color);
                        for(int color : ColorTemplate.PASTEL_COLORS)
                            listColors.add(color);
                        for(int color : ColorTemplate.VORDIPLOM_COLORS)
                            listColors.add(color);
                        listColors.add(ColorTemplate.getHoloBlue());
                        barDataSet.setColors(listColors);

                        BarData barData = new BarData(barDataSet);
                        barData.setValueTextSize(10f);
                        barData.setValueTextColor(Color.BLACK);

                        barChart.setData(barData);
                        barChart.invalidate();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Thống kê không thành công", Toast.LENGTH_SHORT).show();
                },
                throwable -> {
                    Toast.makeText(getApplicationContext(), "Không kết nối được với server " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                }
        ));
    }

    private void getDataThongKeHangTon() {
        compositeDisposable.add(apiQuanLyBanHang.getSPMoi()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                sanPhamMoiModel -> {
                    if(sanPhamMoiModel.isSuccess()) {
                        mangThongKeHangTon = sanPhamMoiModel.getResult();
                        thongKeHangTonAdapter = new ThongKeHangTonAdapter(getApplicationContext(), mangThongKeHangTon);
                        recyclerViewThongKeHangTon.setAdapter(thongKeHangTonAdapter);
                    }
                },
                throwable -> {
                    Toast.makeText(getApplicationContext(), "Không kết nối được với server" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }
        ));
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}