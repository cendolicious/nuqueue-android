package com.example.dickyekaramadhan.nqandroid.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.dickyekaramadhan.nqandroid.Model.OptionModel;
import com.example.dickyekaramadhan.nqandroid.R;

import java.util.List;

/**
 * Created by DICKYEKA on 07/02/2018.
 */

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.MyViewHolder> {
    List<OptionModel> OptionsList;

    public OptionAdapter(List<OptionModel> optionList) {
        this.OptionsList = optionList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year;
        public ImageView rightarr;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            year = (TextView) view.findViewById(R.id.value);
            rightarr = (ImageView) view.findViewById(R.id.rightarrow);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.option_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        OptionModel OptionModel = OptionsList.get(position);
        holder.title.setText(OptionModel.getNama());
        if (OptionModel.getValue() != null){
            holder.rightarr.setVisibility(View.GONE);
            holder.year.setText(OptionModel.getValue());
        }
        else{
            if (OptionModel.getNama() == "Keluar") {
                holder.year.setVisibility(View.GONE);
                holder.rightarr.setVisibility(View.GONE);
            }
            else{
                holder.rightarr.setVisibility(View.VISIBLE);
                holder.year.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public int getItemCount() {
        return OptionsList.size();
    }
}
