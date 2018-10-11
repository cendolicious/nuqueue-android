package com.example.dickyekaramadhan.nqandroid.Adapter;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dickyekaramadhan.nqandroid.MainActivity;
import com.example.dickyekaramadhan.nqandroid.Model.AntrianModel;
import com.example.dickyekaramadhan.nqandroid.Model.ModelInsert;
import com.example.dickyekaramadhan.nqandroid.Model.ModelLogin;
import com.example.dickyekaramadhan.nqandroid.R;
import com.example.dickyekaramadhan.nqandroid.AntrianFragment;
import com.example.dickyekaramadhan.nqandroid.Required.APIService;
import com.example.dickyekaramadhan.nqandroid.Required.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DICKYEKA on 08/02/2018.
 */

public class AntrianAdapter extends RecyclerView.Adapter<AntrianAdapter.MyViewHolder> {
    Context mContext;
    List<AntrianModel> antrianList;
    AntrianFragment main;
    SharedPreferences sp;
    String tokenkey;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView namaklinik,namapoli,noantri,tglpesan,antridilayani;
        public Button btnBatal;

        public MyViewHolder(View view) {
            super(view);
            namaklinik = (TextView) view.findViewById(R.id.namaklinik);
            namapoli = (TextView) view.findViewById(R.id.namapoli);
            noantri = (TextView) view.findViewById(R.id.noantrian);
            tglpesan = (TextView) view.findViewById(R.id.tglpesan);
            antridilayani = (TextView) view.findViewById(R.id.antriandilayani);
            btnBatal = (Button) view.findViewById(R.id.batalantri);
        }
    }

    public AntrianAdapter(Context mContext, List<AntrianModel> antrianList) {
        this.mContext = mContext;
        this.antrianList = antrianList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.antrian_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final AntrianModel antrianModel = antrianList.get(position);

        sp = mContext.getSharedPreferences("sp",mContext.MODE_PRIVATE);
        tokenkey = sp.getString("tokenkey","");

        holder.namaklinik.setText(antrianModel.getNamaklinik());
        holder.namapoli.setText(antrianModel.getNamapoli());
        holder.noantri.setText(antrianModel.getNoantri());
        holder.noantri.setPadding(0,0,0,0);
        holder.tglpesan.setText(antrianModel.getTglpesan());
        if (antrianModel.getAntriandilayani().equals("null")){
            holder.antridilayani.setText("-");
        }else{
            holder.antridilayani.setText(antrianModel.getAntriandilayani());
        }

    }

    @Override
    public int getItemCount() {
        return antrianList.size();
    }
}
