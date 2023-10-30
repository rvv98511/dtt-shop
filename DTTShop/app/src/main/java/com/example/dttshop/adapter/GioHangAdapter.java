package com.example.dttshop.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dttshop.Interface.TangGiamSLClickListener;
import com.example.dttshop.R;
import com.example.dttshop.model.EventBus.TinhTongEvent;
import com.example.dttshop.model.GioHang;
import com.example.dttshop.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.ArrayList;

import io.paperdb.Paper;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {
    Context context;
    ArrayList<GioHang> myGioHang;

    public GioHangAdapter(Context context, ArrayList<GioHang> myGioHang) {
        this.context = context;
        this.myGioHang = myGioHang;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gio_hang, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangAdapter.MyViewHolder holder, int position) {
        GioHang gioHang = myGioHang.get(position);
        holder.txtGioHangTenSP.setText(gioHang.getTenSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        long tempGia = gioHang.getGiaSP() / gioHang.getSoLuongMua();
        long tempGiaKhuyenMai = gioHang.getGiaKhuyenMai() / gioHang.getSoLuongMua();

        holder.txtGioHangGiaSP.setText(decimalFormat.format(gioHang.getGiaSP()) + " ₫");
        if(gioHang.getGiaKhuyenMai() != 0) {
            holder.txtGioHangGiaKhuyenMai.setVisibility(View.VISIBLE);
            holder.txtGioHangGiaKhuyenMai.setText(decimalFormat.format(gioHang.getGiaKhuyenMai()) + " ₫");
            holder.txtGioHangGiaSP.setPaintFlags(holder.txtGioHangGiaSP.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else {
            holder.txtGioHangGiaKhuyenMai.setVisibility(View.GONE);
            holder.txtGioHangGiaSP.setPaintFlags(holder.txtGioHangGiaSP.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
        holder.txtGioHangSLMua.setText(gioHang.getSoLuongMua() + "");
        if(gioHang.getHinhSP().contains("http") || gioHang.getHinhSP().contains("https"))
            Glide.with(context).load(gioHang.getHinhSP()).into(holder.imgAnhGioHangSP);
        else {
            String urlHinhSP = Utils.BASE_URL + "images/sanpham/" + gioHang.getHinhSP();
            Glide.with(context).load(urlHinhSP).into(holder.imgAnhGioHangSP);
        }

        holder.imgBtnDeleteGioHangSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogConfirm = new AlertDialog.Builder(v.getContext());
                alertDialogConfirm.setTitle("Thông báo!");
                alertDialogConfirm.setMessage("Bạn có muốn xoá sản phẩm này khỏi giỏ hàng?");
                alertDialogConfirm.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Utils.mangGioHang.remove(position);
                        Paper.book().write("giohang", Utils.mangGioHang);
                        notifyDataSetChanged();

                        if(holder.checkBoxMuaSP.isChecked()) {
                            for(int i = 0; i < Utils.mangMuaHang.size(); i++) {
                                if(Utils.mangMuaHang.get(i).getIdSP() == gioHang.getIdSP())
                                    Utils.mangMuaHang.remove(i);
                            }
                        }

                        EventBus.getDefault().postSticky(new TinhTongEvent());
                    }
                });
                alertDialogConfirm.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialogConfirm.show();
            }
        });

        holder.setTangGiamSLClickListener(new TangGiamSLClickListener() {
            @Override
            public void onImageButtonClick(View view, int pos, int giaTri) {
                if(giaTri == 1) {
                    if(myGioHang.get(pos).getSoLuongMua() > 1) {
                        int soLuongMoi = myGioHang.get(pos).getSoLuongMua() - 1;
                        myGioHang.get(pos).setSoLuongMua(soLuongMoi);
                    }
                }
                else if(giaTri == 2) {
                    if(myGioHang.get(pos).getSoLuongMua() < 10 && myGioHang.get(pos).getSoLuongMua() < myGioHang.get(pos).getSoLuongSP()) {
                        int soLuongMoi = myGioHang.get(pos).getSoLuongMua() + 1;
                        myGioHang.get(pos).setSoLuongMua(soLuongMoi);
                    }
                }

                myGioHang.get(pos).setGiaSP(tempGia * myGioHang.get(pos).getSoLuongMua());
                myGioHang.get(pos).setGiaKhuyenMai(tempGiaKhuyenMai * myGioHang.get(pos).getSoLuongMua());
                Paper.book().write("giohang", Utils.mangGioHang);
                holder.txtGioHangSLMua.setText(myGioHang.get(pos).getSoLuongMua() + "");
                holder.txtGioHangGiaSP.setText(decimalFormat.format(myGioHang.get(pos).getGiaSP()) + " ₫");
                holder.txtGioHangGiaKhuyenMai.setText(decimalFormat.format(myGioHang.get(pos).getGiaKhuyenMai()) + " ₫");

                if(holder.checkBoxMuaSP.isChecked()) {
                    for(int i = 0; i < Utils.mangMuaHang.size(); i++) {
                        if(Utils.mangMuaHang.get(i).getIdSP() == myGioHang.get(pos).getIdSP()) {
                            Utils.mangMuaHang.get(i).setGiaSP(myGioHang.get(pos).getGiaSP());
                            Utils.mangMuaHang.get(i).setGiaKhuyenMai(myGioHang.get(pos).getGiaKhuyenMai());
                            Utils.mangMuaHang.get(i).setSoLuongMua(myGioHang.get(pos).getSoLuongMua());
                        }
                    }
                }

                EventBus.getDefault().postSticky(new TinhTongEvent());
            }
        });

        holder.checkBoxMuaSP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    Utils.mangMuaHang.add(gioHang);
                else {
                    for(int i = 0; i < Utils.mangMuaHang.size(); i++) {
                        if(Utils.mangMuaHang.get(i).getIdSP() == gioHang.getIdSP())
                            Utils.mangMuaHang.remove(i);
                    }
                }
                EventBus.getDefault().postSticky(new TinhTongEvent());
            }
        });
    }

    @Override
    public int getItemCount() {
        return myGioHang.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtGioHangTenSP, txtGioHangGiaSP, txtGioHangGiaKhuyenMai, txtGioHangSLMua;
        ImageView imgAnhGioHangSP;
        ImageButton imgBtnRemoveSL, imgBtnAddSL, imgBtnDeleteGioHangSP;
        CheckBox checkBoxMuaSP;
        private TangGiamSLClickListener tangGiamSLClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtGioHangTenSP = itemView.findViewById(R.id.textViewGioHangTenSP);
            txtGioHangGiaSP = itemView.findViewById(R.id.textViewGioHangGiaSP);
            txtGioHangGiaKhuyenMai = itemView.findViewById(R.id.textViewGioHangGiaKhuyenMai);
            txtGioHangSLMua = itemView.findViewById(R.id.textViewGioHangSoLuongMua);
            imgAnhGioHangSP = itemView.findViewById(R.id.imageViewGioHangSP);
            imgBtnRemoveSL = itemView.findViewById(R.id.imageButtonRemoveSL);
            imgBtnAddSL = itemView.findViewById(R.id.imageButtonAddSL);
            imgBtnDeleteGioHangSP = itemView.findViewById(R.id.imageButtonDeleteGioHangSP);
            checkBoxMuaSP = itemView.findViewById(R.id.checkBoxMuaSP);

            imgBtnRemoveSL.setOnClickListener(this);
            imgBtnAddSL.setOnClickListener(this);
        }

        public void setTangGiamSLClickListener(TangGiamSLClickListener tangGiamSLClickListener) {
            this.tangGiamSLClickListener = tangGiamSLClickListener;
        }

        @Override
        public void onClick(View v) {
            if(v == imgBtnRemoveSL)
                tangGiamSLClickListener.onImageButtonClick(v, getAdapterPosition(), 1);
            else if(v == imgBtnAddSL)
                tangGiamSLClickListener.onImageButtonClick(v, getAdapterPosition(), 2);
        }
    }
}
