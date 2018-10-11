package com.example.dickyekaramadhan.nqandroid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dickyekaramadhan.nqandroid.Adapter.OptionAdapter;
import com.example.dickyekaramadhan.nqandroid.Model.ModelPoli;
import com.example.dickyekaramadhan.nqandroid.Model.ModelRS;
import com.example.dickyekaramadhan.nqandroid.Model.OptionModel;
import com.example.dickyekaramadhan.nqandroid.Required.APIService;
import com.example.dickyekaramadhan.nqandroid.Required.ClickListener;
import com.example.dickyekaramadhan.nqandroid.Required.RecyclerTouchListener;
import com.example.dickyekaramadhan.nqandroid.Required.RetrofitClient;
import com.example.dickyekaramadhan.nqandroid.Result.ResultPoli;
import com.example.dickyekaramadhan.nqandroid.Result.ResultRS;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SubVenue extends AppCompatActivity {

    List<OptionModel> optionList = new ArrayList<>();
    RecyclerView recyclerView;
    OptionAdapter mAdapter;
    ProgressDialog progressDialog;
    SharedPreferences sp;
    String tokenkey,namalayanan,idvenue,namavenue;
    String[] idpoli;
    String[] namapoli;
    TextView notavailable;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_venue);
        notavailable = (TextView) findViewById(R.id.tidaktersedialay);
        notavailable.setVisibility(View.GONE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.custtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Nama Venue");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(SubVenue.this);
        progressDialog.setMessage("Memuat..");
        progressDialog.setCancelable(false);

        sp = getSharedPreferences("sp",MODE_PRIVATE);
        tokenkey = sp.getString("tokenkey","");
        idvenue = sp.getString("id_rs","");
        namavenue = sp.getString("nama_rs","");
        getSupportActionBar().setTitle(namavenue);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new OptionAdapter(optionList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        String url = "http://nuqueue.web.id/api/";
        final APIService service = RetrofitClient.getClient(url).create(APIService.class);
        Call<ModelPoli> call = service.getpoli(idvenue);
        call.enqueue(new Callback<ModelPoli>() {
            @Override
            public void onResponse(Call<ModelPoli> call, Response<ModelPoli> response) {
                if (response.isSuccessful()){
                    ModelPoli databody = response.body();
                    if (databody.getMessage().equals("data ditemukan")){
                        List<ResultPoli> data = databody.getResult();
                        idpoli = new String[data.size()];
                        namapoli = new String[data.size()];
                        int i = 0;
                        for (ResultPoli d: data) {
                            idpoli[i] = d.getIdPoli();
                            namapoli[i] = d.getNamaPoli();
                            prepareSubVenue(d.getNamaPoli());
                            i++;
                        }
                    }else{
                        Toast.makeText(SubVenue.this, "Belum ada poli", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(SubVenue.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelPoli> call, Throwable throwable) {
                Toast.makeText(SubVenue.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(getApplicationContext(),JadwalVenue.class);
                SharedPreferences.Editor editor = getSharedPreferences("sp", MODE_PRIVATE).edit();
                editor.putString("namapoli",namapoli[position]);
                editor.putString("idpoli",idpoli[position]);
                editor.commit();
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void prepareSubVenue(String name) {
        OptionModel OptionModel = new OptionModel(name, null);
        optionList.add(OptionModel);

        mAdapter.notifyDataSetChanged();
    }
}
