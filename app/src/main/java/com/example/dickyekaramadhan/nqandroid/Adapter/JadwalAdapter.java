package com.example.dickyekaramadhan.nqandroid.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.dickyekaramadhan.nqandroid.Model.JadwalModel;
import com.example.dickyekaramadhan.nqandroid.R;

import java.util.List;

/**
 * Created by DICKYEKA on 08/02/2018.
 */

public class JadwalAdapter extends RecyclerView.Adapter<JadwalAdapter.MyViewHolder> {
    Context mContext;
    List<JadwalModel> jadwalList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout jadwalcard;
        public TextView namapelayan,jam,jmlantrian;
        public Button btnDaftar;

        public MyViewHolder(View view) {
            super(view);
            namapelayan = (TextView) view.findViewById(R.id.namapelayan);
            jam = (TextView) view.findViewById(R.id.jam);
            jmlantrian = (TextView) view.findViewById(R.id.jmlantrian);
            btnDaftar = (Button) view.findViewById(R.id.btnDaftarJadwal);
            jadwalcard = (LinearLayout) view.findViewById(R.id.jadwalcard);
        }
    }

    public JadwalAdapter(Context mContext, List<JadwalModel> jadwalList) {
        this.mContext = mContext;
        this.jadwalList = jadwalList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.jadwal_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        JadwalModel jadwalModel = jadwalList.get(position);
        holder.namapelayan.setText(jadwalModel.getNamapelayan());
        holder.jam.setText(jadwalModel.getJam());
        holder.jmlantrian.setText(jadwalModel.getJmlantrian());
        holder.btnDaftar.setEnabled(false);
        if (jadwalModel.getStatusbuka()){
            holder.btnDaftar.setAlpha(1f);
        }else{
            holder.jadwalcard.setAlpha(0.45f);
            holder.btnDaftar.setAlpha(0.45f);
        }
    }

    @Override
    public int getItemCount() {
        return jadwalList.size();
    }
}
