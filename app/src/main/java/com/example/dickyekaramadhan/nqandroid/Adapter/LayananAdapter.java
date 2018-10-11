package com.example.dickyekaramadhan.nqandroid.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dickyekaramadhan.nqandroid.Model.LayananModel;
import com.example.dickyekaramadhan.nqandroid.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by DICKYEKA on 08/02/2018.
 */

public class LayananAdapter extends RecyclerView.Adapter<LayananAdapter.MyViewHolder> {
    Context mContext;
    List<LayananModel> venueList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nama;
        public ImageView icon;

        public MyViewHolder(View view) {
            super(view);
            nama = (TextView) view.findViewById(R.id.namalayanan);
            icon = (ImageView) view.findViewById(R.id.icon);
        }
    }

    public LayananAdapter(Context mContext, List<LayananModel> venueList) {
        this.mContext = mContext;
        this.venueList = venueList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layanan_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        LayananModel layananModel = venueList.get(position);
        holder.nama.setText(layananModel.getNama());
        int icon = 0;
        if (layananModel.getNama().equals("Daftar Rumah Sakit")){
            icon = R.drawable.health;
        }
        Picasso.with(mContext).load(icon).noFade().into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return venueList.size();
    }
}
