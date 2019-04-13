package com.example.pnam.doctruyen.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.pnam.doctruyen.R;

import com.example.pnam.doctruyen.Object.Truyen;

import java.util.List;

public class CustomListTruyen extends ArrayAdapter<Truyen> {

    @NonNull Context context;
    int resource;
    @NonNull List<Truyen> objects;

    public CustomListTruyen(@NonNull Context context, int resource, @NonNull List<Truyen> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.objects = objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_truyen,parent,false);
            viewHolder.ivAvata = convertView.findViewById(R.id.iv_avata);
            viewHolder.tvTieuDe = convertView.findViewById(R.id.tv_tieu_de);

            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Truyen truyen = objects.get(position);
        viewHolder.ivAvata.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(truyen.getUrlImgTruyen()).into(viewHolder.ivAvata);
        viewHolder.tvTieuDe.setText(truyen.getTitleTruyen());

        return convertView;
    }

    public class ViewHolder{
        ImageView ivAvata;
        TextView tvTieuDe;
    }

}
