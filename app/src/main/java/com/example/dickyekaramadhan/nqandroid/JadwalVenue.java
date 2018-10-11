package com.example.dickyekaramadhan.nqandroid;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.example.dickyekaramadhan.nqandroid.Adapter.JadwalAdapter;
import com.example.dickyekaramadhan.nqandroid.Model.JadwalModel;
import com.example.dickyekaramadhan.nqandroid.Model.ModelJadwal;
import com.example.dickyekaramadhan.nqandroid.Model.ModelPoli;
import com.example.dickyekaramadhan.nqandroid.Required.APIService;
import com.example.dickyekaramadhan.nqandroid.Required.ClickListener;
import com.example.dickyekaramadhan.nqandroid.Required.RecyclerTouchListener;
import com.example.dickyekaramadhan.nqandroid.Required.RetrofitClient;
import com.example.dickyekaramadhan.nqandroid.Result.ResultJadwal;
import com.example.dickyekaramadhan.nqandroid.Result.ResultPoli;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class JadwalVenue extends AppCompatActivity {
    RecyclerView recyclerView;
    List<JadwalModel> jadwalList;
    JadwalAdapter adapter;
    SharedPreferences sp;
    String namapoli,namavenue,idvenue,namalayanan,tokenkey,idpoli;
    TextView ket;
    ProgressDialog progressDialog;
    TextView notavailable;
    String[] idjadwal;
    int[] openedbtn;
    int jmljdwl;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_venue);



        notavailable = (TextView) findViewById(R.id.tidaktersedialay);
        notavailable.setVisibility(View.GONE);

        progressDialog = new ProgressDialog(JadwalVenue.this);
        progressDialog.setMessage("Memuat..");
        progressDialog.show();
        progressDialog.setCancelable(false);

        sp = getSharedPreferences("sp",MODE_PRIVATE);
        tokenkey = sp.getString("tokenkey","");
        namapoli = sp.getString("namapoli","");
        idpoli = sp.getString("idpoli","");
        namavenue = sp.getString("namavenue","");
        idvenue = sp.getString("idvenue","");
        namalayanan = sp.getString("namalayanan","");

        ket = (TextView) findViewById(R.id.ket);
        ket.setText("Jadwal Praktek "+namapoli);

        Toolbar toolbar = (Toolbar) findViewById(R.id.custtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(namavenue);

        jadwalList = new ArrayList<JadwalModel>();
        adapter = new JadwalAdapter(this,jadwalList);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(JadwalVenue.this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);


        String url = "http://nuqueue.web.id/api/";
        final APIService service = RetrofitClient.getClient(url).create(APIService.class);
        Call<ModelJadwal> call = service.getjadwal(idpoli);
        call.enqueue(new Callback<ModelJadwal>() {
            @Override
            public void onResponse(Call<ModelJadwal> call, Response<ModelJadwal> response) {
                if (response.isSuccessful()){
                    ModelJadwal databody = response.body();
                    if (databody.getMessage().equals("data ditemukan")){
                        List<ResultJadwal> data = databody.getResult();
                        idjadwal = new String[data.size()];
                        openedbtn = new int[data.size()];
                        jmljdwl = data.size();
                        int i = 0;
                        for (ResultJadwal d: data) {
                            idjadwal[i] = d.getIdJadwal();
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                            String date = sdf.format(Calendar.getInstance().getTime());
                            Date inTime = null;
                            try {
                                inTime = sdf.parse(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Date outTime = null;
                            try {
                                outTime = sdf.parse(d.getJammulaiPoli().substring(0,5));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Date outTime2 = null;
                            try {
                                outTime2 = sdf.parse(d.getJamselesaiPoli().substring(0,5));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            if(inTime.compareTo(outTime) < 0){
                                prepareJadwal(d.getNamaDokter(),d.getJmlAntrian(),d.getJammulaiPoli().substring(0,5)+" - "+d.getJamselesaiPoli().substring(0,5),false);
                            }else{
                                if (inTime.compareTo(outTime2) > 0){
                                    prepareJadwal(d.getNamaDokter(),d.getJmlAntrian(),d.getJammulaiPoli().substring(0,5)+" - "+d.getJamselesaiPoli().substring(0,5),false);
                                }else{
                                    openedbtn[i] = i;
                                    prepareJadwal(d.getNamaDokter(),d.getJmlAntrian(),d.getJammulaiPoli().substring(0,5)+" - "+d.getJamselesaiPoli().substring(0,5),true);
                                }

                            }

                            i++;
                        }
                        progressDialog.dismiss();
                    }else{
                        notavailable.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                    }
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(JadwalVenue.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelJadwal> call, Throwable throwable) {
                progressDialog.dismiss();
                Toast.makeText(JadwalVenue.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });

        for (int j = 0;j>jmljdwl;j++){
            Toast.makeText(JadwalVenue.this, ""+openedbtn[j], Toast.LENGTH_SHORT).show();
        }

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (view.getAlpha() == 0.45f){
                    Toast.makeText(JadwalVenue.this, "Jadwal Tutup", Toast.LENGTH_SHORT).show();
                }else{
                    if (tokenkey.equals("")){
                        Intent i = new Intent(JadwalVenue.this,Login.class);
                        Toast.makeText(JadwalVenue.this, "Silahkan masuk terlebih dahulu.", Toast.LENGTH_SHORT).show();
                        i.putExtra("from","jadwal");
                        i.putExtra("namapoli",namapoli);
                        i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        SharedPreferences.Editor editor = getSharedPreferences("sp", MODE_PRIVATE).edit();
                        editor.putString("idjadwal",idjadwal[position]);
                        editor.commit();
                        startActivity(i);
                        finish();
                    }else{

                        Intent i = new Intent(getApplicationContext(),PilihanDaftar.class);
                        SharedPreferences.Editor editor = getSharedPreferences("sp", MODE_PRIVATE).edit();
                        editor.putString("idjadwal",idjadwal[position]);
                        editor.commit();
                        i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(i);
                        finish();

                    }
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void prepareJadwal(String name, String jmlantrian, String schedule, Boolean statusbuka){
        JadwalModel j = new JadwalModel(name,"Jam : "+schedule,"Jumlah Antrian : "+jmlantrian,statusbuka);
        jadwalList.add(j);
        adapter.notifyDataSetChanged();
    }
}
