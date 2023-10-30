package com.example.dttshopmanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dttshopmanager.Interface.ItemClickListener;
import com.example.dttshopmanager.R;
import com.example.dttshopmanager.activity.ChiTietSanPhamActivity;
import com.example.dttshopmanager.model.SanPhamMoi;
import com.example.dttshopmanager.utils.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamMoiAdapter extends RecyclerView.Adapter<SanPhamMoiAdapter.MyViewHolder> {
    Context context;
    ArrayList<SanPhamMoi> mySanPhamMoi;

    public SanPhamMoiAdapter(Context context, ArrayList<SanPhamMoi> mySanPhamMoi) {
        this.context = context;
        this.mySanPhamMoi = mySanPhamMoi;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_san_pham_moi, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SanPhamMoi sanPhamMoi = mySanPhamMoi.get(position);
        holder.txtTenSP.setText(sanPhamMoi.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaSP.setText(decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiaSP())) + " ₫");
        if(sanPhamMoi.getGiaKhuyenMai() != null ) {
            holder.txtGiaKhuyenMai.setVisibility(View.VISIBLE);
            holder.txtGiaKhuyenMai.setText(decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiaKhuyenMai())) + " ₫");
            holder.txtGiaSP.setPaintFlags(holder.txtGiaSP.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else {
            holder.txtGiaKhuyenMai.setVisibility(View.GONE);
            holder.txtGiaSP.setPaintFlags(holder.txtGiaSP.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
        if(sanPhamMoi.getHinhAnhSP().contains("http") || sanPhamMoi.getHinhAnhSP().contains("https"))
            Glide.with(context).load(sanPhamMoi.getHinhAnhSP()).into(holder.imgAnhSPMoi);
        else {
            String urlHinhAnhSP = Utils.BASE_URL + "images/sanpham/" + sanPhamMoi.getHinhAnhSP();
            Glide.with(context).load(urlHinhAnhSP).into(holder.imgAnhSPMoi);
        }
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if(!isLongClick) {
                    Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                    intent.putExtra("chitiet", sanPhamMoi);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mySanPhamMoi.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTenSP, txtGiaSP, txtGiaKhuyenMai;
        ImageView imgAnhSPMoi;
        private ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenSP = itemView.findViewById(R.id.textViewTenSP);
            txtGiaSP = itemView.findViewById(R.id.textViewGiaSP);
            txtGiaKhuyenMai = itemView.findViewById(R.id.textViewGiaKhuyenMai);
            imgAnhSPMoi = itemView.findViewById(R.id.imageViewSPMoi);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }
    }
}
