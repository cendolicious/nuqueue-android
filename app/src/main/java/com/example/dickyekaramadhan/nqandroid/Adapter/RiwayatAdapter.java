package com.example.dickyekaramadhan.nqandroid.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dickyekaramadhan.nqandroid.Model.RiwayatModel;
import com.example.dickyekaramadhan.nqandroid.R;

import java.util.List;

/**
 * Created by DICKYEKA on 08/02/2018.
 */

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.MyViewHolder> {
    Context mContext;
    List<RiwayatModel> riwayatList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView namaklinik,namapoli,status,tanggal;

        public MyViewHolder(View view) {
            super(view);
            namaklinik = (TextView) view.findViewById(R.id.namaklinik);
            namapoli = (TextView) view.findViewById(R.id.namapoli);
            status = (TextView) view.findViewById(R.id.status);
            tanggal = (TextView) view.findViewById(R.id.tanggal);
        }
    }

    public RiwayatAdapter(Context mContext, List<RiwayatModel> riwayatList) {
        this.mContext = mContext;
        this.riwayatList = riwayatList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.riwayat_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        RiwayatModel riwayatModel = riwayatList.get(position);
        holder.namaklinik.setText(riwayatModel.getNamaklinik());
        holder.namapoli.setText(riwayatModel.getNamapoli());
        holder.status.setText(riwayatModel.getStatus());
        holder.tanggal.setText(riwayatModel.getTanggal());
    }

    @Override
    public int getItemCount() {
        return riwayatList.size();
    }
}
