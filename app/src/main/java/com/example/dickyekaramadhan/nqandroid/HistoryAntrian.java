package com.example.dickyekaramadhan.nqandroid;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.dickyekaramadhan.nqandroid.Adapter.RiwayatAdapter;
import com.example.dickyekaramadhan.nqandroid.Model.ModelHistory;
import com.example.dickyekaramadhan.nqandroid.Model.ModelRS;
import com.example.dickyekaramadhan.nqandroid.Model.RiwayatModel;
import com.example.dickyekaramadhan.nqandroid.Required.APIService;
import com.example.dickyekaramadhan.nqandroid.Required.RetrofitClient;
import com.example.dickyekaramadhan.nqandroid.Result.ResultHistory;
import com.example.dickyekaramadhan.nqandroid.Result.ResultRS;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryAntrian extends AppCompatActivity {
    List<RiwayatModel> riwayatList = new ArrayList<>();
    RecyclerView recyclerView;
    RiwayatAdapter mAdapter;
    SharedPreferences sp;
    String tokenkey;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_antrian);

        progressDialog = new ProgressDialog(HistoryAntrian.this);
        progressDialog.setMessage("Memuat..");
        progressDialog.show();
        progressDialog.setCancelable(false);

        Toolbar toolbar = (Toolbar) findViewById(R.id.custtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Riwayat Antrian");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sp = getSharedPreferences("sp",MODE_PRIVATE);
        tokenkey = sp.getString("tokenkey","");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new RiwayatAdapter(getApplicationContext(),riwayatList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        String url = "http://nuqueue.web.id/api/";
        final APIService service = RetrofitClient.getClient(url).create(APIService.class);
        Call<ModelHistory> call = service.getriwayatantrian(tokenkey);
        call.enqueue(new Callback<ModelHistory>() {
            @Override
            public void onResponse(Call<ModelHistory> call, Response<ModelHistory> response) {
                if (response.isSuccessful()){
                    List<ResultHistory> data = response.body().getResult();
                    for (ResultHistory d: data) {
                        prepareRiwayatData(d.getNamaRs(),d.getNamaPoli()+" - "+d.getNamaDokter(),"Nama Pasien : "+d.getNamaPasien(),"Tanggal Pemesanan : "+d.getTglAntrian());
                    }
                    progressDialog.dismiss();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(HistoryAntrian.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelHistory> call, Throwable throwable) {
                progressDialog.dismiss();
                Toast.makeText(HistoryAntrian.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void prepareRiwayatData(String namaklinik, String namapoli, String status, String tanggal) {
        RiwayatModel riwayatModel = new RiwayatModel(namaklinik, namapoli, status, tanggal);
        riwayatList.add(riwayatModel);

        mAdapter.notifyDataSetChanged();
    }
}
