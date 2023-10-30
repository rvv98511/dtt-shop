package com.example.dttshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dttshop.R;
import com.example.dttshop.model.DanhSachMenu;
import com.example.dttshop.utils.Utils;

import java.util.ArrayList;

public class DanhSachMenuAdapter extends BaseAdapter {
    Context context;
    ArrayList<DanhSachMenu> myDanhSachMenu;

    public DanhSachMenuAdapter(Context context, ArrayList<DanhSachMenu> myDanhSachMenu) {
        this.context = context;
        this.myDanhSachMenu = myDanhSachMenu;
    }

    @Override
    public int getCount() {
        return myDanhSachMenu.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder {
        TextView txtTenDanhSach;
        ImageView imgHinhAnhDanhSach;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_danh_sach_menu, null);
            viewHolder.txtTenDanhSach = convertView.findViewById(R.id.textViewListMenu);
            viewHolder.imgHinhAnhDanhSach = convertView.findViewById(R.id.imageViewListMenu);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txtTenDanhSach.setText(myDanhSachMenu.get(position).getTenDanhSach());
        if(myDanhSachMenu.get(position).getHinhAnhDanhSach().contains("http") || myDanhSachMenu.get(position).getHinhAnhDanhSach().contains("https"))
            Glide.with(context).load(myDanhSachMenu.get(position).getHinhAnhDanhSach()).into(viewHolder.imgHinhAnhDanhSach);
        else {
            String urlHinhAnhDanhSach = Utils.BASE_URL + "images/menu/" + myDanhSachMenu.get(position).getHinhAnhDanhSach();
            Glide.with(context).load(urlHinhAnhDanhSach).into(viewHolder.imgHinhAnhDanhSach);
        }

        return convertView;
    }
}
