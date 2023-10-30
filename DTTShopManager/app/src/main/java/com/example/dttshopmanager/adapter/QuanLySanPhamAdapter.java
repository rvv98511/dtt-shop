package com.example.dttshopmanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dttshopmanager.Interface.ItemClickListener;
import com.example.dttshopmanager.R;
import com.example.dttshopmanager.activity.ChiTietSanPhamActivity;
import com.example.dttshopmanager.model.EventBus.SuaXoaEvent;
import com.example.dttshopmanager.model.SanPhamMoi;
import com.example.dttshopmanager.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class QuanLySanPhamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<SanPhamMoi> myLoaiSanPham;

    public QuanLySanPhamAdapter(Context context, ArrayList<SanPhamMoi> myLoaiSanPham) {
        this.context = context;
        this.myLoaiSanPham = myLoaiSanPham;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loai_san_pham, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        SanPhamMoi sanPhamMoi = myLoaiSanPham.get(position);
        myViewHolder.txtTenLoaiSP.setText(sanPhamMoi.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        myViewHolder.txtGiaLoaiSP.setText(decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiaSP())) + " ₫");
        if(sanPhamMoi.getGiaKhuyenMai() != null) {
            myViewHolder.txtGiaKhuyenMaiLoaiSP.setVisibility(View.VISIBLE);
            myViewHolder.txtGiaKhuyenMaiLoaiSP.setText(decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiaKhuyenMai())) + " ₫");
            myViewHolder.txtGiaLoaiSP.setPaintFlags(myViewHolder.txtGiaLoaiSP.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else {
            myViewHolder.txtGiaKhuyenMaiLoaiSP.setVisibility(View.GONE);
            myViewHolder.txtGiaLoaiSP.setPaintFlags(myViewHolder.txtGiaLoaiSP.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
        if(sanPhamMoi.getHinhAnhSP().contains("http") || sanPhamMoi.getHinhAnhSP().contains("https"))
            Glide.with(context).load(sanPhamMoi.getHinhAnhSP()).into(myViewHolder.imgAnhLoaiSP);
        else {
            String urlHinhAnhSP = Utils.BASE_URL + "images/sanpham/" + sanPhamMoi.getHinhAnhSP();
            Glide.with(context).load(urlHinhAnhSP).into(myViewHolder.imgAnhLoaiSP);
        }
        myViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if(!isLongClick) {
                    Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                    intent.putExtra("chitiet", sanPhamMoi);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                else {
                    EventBus.getDefault().postSticky(new SuaXoaEvent(sanPhamMoi));
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return myLoaiSanPham.size();
    }

    @Override
    public int getItemCount() {
        return myLoaiSanPham.size();
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBarLoading;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBarLoading = itemView.findViewById(R.id.progressBarLoading);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, View.OnLongClickListener {
        TextView txtTenLoaiSP, txtGiaLoaiSP, txtGiaKhuyenMaiLoaiSP;
        ImageView imgAnhLoaiSP;
        private ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenLoaiSP = itemView.findViewById(R.id.textViewTenLoaiSP);
            txtGiaLoaiSP = itemView.findViewById(R.id.textViewGiaLoaiSP);
            txtGiaKhuyenMaiLoaiSP = itemView.findViewById(R.id.textViewGiaKhuyenMaiLoaiSP);
            imgAnhLoaiSP = itemView.findViewById(R.id.imageViewLoaiSP);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0, 0, getAdapterPosition(), "Sửa");
            menu.add(0, 1, getAdapterPosition(), "Xoá");
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
            return false;
        }
    }
}
