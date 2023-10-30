package com.example.dttshop.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dttshop.R;
import com.example.dttshop.model.ChiTietDonHang;
import com.example.dttshop.utils.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietDonHangAdapter extends RecyclerView.Adapter<ChiTietDonHangAdapter.MyViewHolder> {
    Context context;
    ArrayList<ChiTietDonHang> myChiTietDonHang;

    public ChiTietDonHangAdapter(Context context, ArrayList<ChiTietDonHang> myChiTietDonHang) {
        this.context = context;
        this.myChiTietDonHang = myChiTietDonHang;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chi_tiet_don_hang, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChiTietDonHangAdapter.MyViewHolder holder, int position) {
        ChiTietDonHang chiTietDonHang = myChiTietDonHang.get(position);
        holder.txtTenSPChiTietDonHang.setText(chiTietDonHang.getTenSP());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaSPChiTietDonHang.setText(decimalFormat.format(Double.parseDouble(chiTietDonHang.getGiaSP())) + " ₫");
        if(!chiTietDonHang.getGiaKhuyenMai().equals("0")) {
            holder.txtGiaKhuyenMaiChiTietDonHang.setVisibility(View.VISIBLE);
            holder.txtGiaKhuyenMaiChiTietDonHang.setText(decimalFormat.format(Double.parseDouble(chiTietDonHang.getGiaKhuyenMai())) + " ₫");
            holder.txtGiaSPChiTietDonHang.setPaintFlags(holder.txtGiaSPChiTietDonHang.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else {
            holder.txtGiaKhuyenMaiChiTietDonHang.setVisibility(View.GONE);
            holder.txtGiaSPChiTietDonHang.setPaintFlags(holder.txtGiaSPChiTietDonHang.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        holder.txtSoLuongChiTietDonHang.setText(chiTietDonHang.getSoLuongMua() + "");
        if(chiTietDonHang.getHinhAnhSP().contains("http") || chiTietDonHang.getHinhAnhSP().contains("https"))
            Glide.with(context).load(chiTietDonHang.getHinhAnhSP()).into(holder.imgAnhChiTietDonHang);
        else {
            String urlHinhAnhSP = Utils.BASE_URL + "images/sanpham/" + chiTietDonHang.getHinhAnhSP();
            Glide.with(context).load(urlHinhAnhSP).into(holder.imgAnhChiTietDonHang);
        }
    }

    @Override
    public int getItemCount() {
        return myChiTietDonHang.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imgAnhChiTietDonHang;
        TextView txtTenSPChiTietDonHang, txtGiaSPChiTietDonHang, txtGiaKhuyenMaiChiTietDonHang, txtSoLuongChiTietDonHang;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnhChiTietDonHang = itemView.findViewById(R.id.imageViewChiTietDonHang);
            txtTenSPChiTietDonHang = itemView.findViewById(R.id.textViewTenSPChiTietDonHang);
            txtGiaSPChiTietDonHang = itemView.findViewById(R.id.textViewGiaSPChiTietDonHang);
            txtGiaKhuyenMaiChiTietDonHang = itemView.findViewById(R.id.textViewGiaKhuyenMaiChiTietDonHang);
            txtSoLuongChiTietDonHang = itemView.findViewById(R.id.textViewSoLuongSPChiTietDonHang);
        }
    }
}
