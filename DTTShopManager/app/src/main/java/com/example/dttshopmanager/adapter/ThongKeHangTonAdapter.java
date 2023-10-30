package com.example.dttshopmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dttshopmanager.R;
import com.example.dttshopmanager.model.SanPhamMoi;
import com.example.dttshopmanager.utils.Utils;

import java.util.ArrayList;

public class ThongKeHangTonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<SanPhamMoi> myThongKeHangTon;

    public ThongKeHangTonAdapter(Context context, ArrayList<SanPhamMoi> myThongKeHangTon) {
        this.context = context;
        this.myThongKeHangTon = myThongKeHangTon;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thong_ke_hang_ton, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        SanPhamMoi sanPhamMoi = myThongKeHangTon.get(position);
        myViewHolder.txtTenSPHangTon.setText(sanPhamMoi.getTenSP());
        myViewHolder.txtSoLuongHangTon.setText(sanPhamMoi.getSoLuongSP() + "");
        if(sanPhamMoi.getHinhAnhSP().contains("http") || sanPhamMoi.getHinhAnhSP().contains("https"))
            Glide.with(context).load(sanPhamMoi.getHinhAnhSP()).into(myViewHolder.imgAnhSPHangTon);
        else {
            String urlHinhAnhSP = Utils.BASE_URL + "images/sanpham/" + sanPhamMoi.getHinhAnhSP();
            Glide.with(context).load(urlHinhAnhSP).into(myViewHolder.imgAnhSPHangTon);
        }
    }

    @Override
    public int getItemCount() {
        return myThongKeHangTon.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenSPHangTon, txtSoLuongHangTon;
        ImageView imgAnhSPHangTon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenSPHangTon = itemView.findViewById(R.id.textViewTenSPHangTon);
            txtSoLuongHangTon = itemView.findViewById(R.id.textViewSoLuongHangTon);
            imgAnhSPHangTon = itemView.findViewById(R.id.imageViewSPHangTon);
        }
    }
}
