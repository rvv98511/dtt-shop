package com.example.dttshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dttshop.R;
import com.example.dttshop.model.DonHang;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.MyViewHolder> {
    private RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
    Context context;
    ArrayList<DonHang> myDonHang;

    public DonHangAdapter(Context context, ArrayList<DonHang> myDonHang) {
        this.context = context;
        this.myDonHang = myDonHang;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_don_hang, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangAdapter.MyViewHolder holder, int position) {
        DonHang donHang = myDonHang.get(position);
        holder.txtMaDonHang.setText(donHang.getIdDonHang() + "");
        holder.txtTrangThai.setText(trangThaiDonHang(donHang.getTrangThai()));
        holder.txtThanhToan.setText(donHang.getThanhToan());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dateFormat.format(donHang.getNgayDatHang());
        holder.txtNgayDatHang.setText(currentDate);
        holder.txtTongSoLuong.setText(donHang.getTongSoLuong() + "");

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtTongTienDonHang.setText(decimalFormat.format(Double.parseDouble(donHang.getTongTien())) + " ₫");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.recyclerViewChiTietDonHang.getContext(), LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setInitialPrefetchItemCount(donHang.getChiTiet().size());
        holder.recyclerViewChiTietDonHang.setLayoutManager(linearLayoutManager);

        ChiTietDonHangAdapter chiTietDonHangAdapter = new ChiTietDonHangAdapter(context, donHang.getChiTiet());
        holder.recyclerViewChiTietDonHang.setAdapter(chiTietDonHangAdapter);
        holder.recyclerViewChiTietDonHang.setRecycledViewPool(recycledViewPool);
    }

    private String trangThaiDonHang(int status) {
        String result = "";
        switch(status) {
            case 0:
                result = "Đang chờ xử lý";
                break;
            case 1:
                result = "Đã chấp nhận";
                break;
            case 2:
                result = "Đơn vị đang vận chuyển";
                break;
            case 3:
                result = "Đã nhận thành công";
                break;
            case 4:
                result = "Đã bị huỷ";
                break;
        }

        return result;
    }

    @Override
    public int getItemCount() {
        return myDonHang.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaDonHang, txtThanhToan, txtNgayDatHang, txtTongSoLuong, txtTongTienDonHang, txtTrangThai;
        RecyclerView recyclerViewChiTietDonHang;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaDonHang = itemView.findViewById(R.id.textViewMaDonHang);
            txtThanhToan = itemView.findViewById(R.id.textViewThanhToan);
            txtNgayDatHang = itemView.findViewById(R.id.textViewNgayDatHang);
            txtTongSoLuong = itemView.findViewById(R.id.textViewTongSoLuong);
            txtTongTienDonHang = itemView.findViewById(R.id.textViewTongTienDonHang);
            txtTrangThai = itemView.findViewById(R.id.textViewTrangThaiDonHang);
            recyclerViewChiTietDonHang = itemView.findViewById(R.id.recyclerViewChiTietDonHang);
        }
    }
}
