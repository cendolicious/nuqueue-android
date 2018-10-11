package com.example.dickyekaramadhan.nqandroid.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dickyekaramadhan.nqandroid.Model.VenueModel;
import com.example.dickyekaramadhan.nqandroid.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by DICKYEKA on 08/02/2018.
 */

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.MyViewHolder> {
    private Context mContext;
    private List<VenueModel> venueList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView nama,alamat,jarak,id;

        public MyViewHolder(View view) {
            super(view);
            nama = (TextView) view.findViewById(R.id.nama);
            alamat = (TextView) view.findViewById(R.id.alamat);
            jarak = (TextView) view.findViewById(R.id.jarak);
        }
    }

    public BookmarkAdapter(Context mContext, List<VenueModel> venueList) {
        this.mContext = mContext;
        this.venueList = venueList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bookmark_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        VenueModel venueModel = venueList.get(position);
        holder.nama.setText(venueModel.getNama());
        holder.alamat.setText(venueModel.getAlamat());
        holder.jarak.setText(venueModel.getJarak());
    }

    @Override
    public int getItemCount() {
        return venueList.size();
    }
}
