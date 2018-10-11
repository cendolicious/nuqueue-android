package com.example.dickyekaramadhan.nqandroid;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dickyekaramadhan.nqandroid.Model.ModelNIK;
import com.example.dickyekaramadhan.nqandroid.Model.ModelPasien;
import com.example.dickyekaramadhan.nqandroid.Required.APIService;
import com.example.dickyekaramadhan.nqandroid.Required.RetrofitClient;
import com.example.dickyekaramadhan.nqandroid.Required.RetrofitClient2;
import com.example.dickyekaramadhan.nqandroid.Result.ResultNIK;
import com.example.dickyekaramadhan.nqandroid.Result.ResultPasien;
import com.example.dickyekaramadhan.nqandroid.Result.ResultRS;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PasienLama extends AppCompatActivity {

    RadioGroup radioMetode;
    RadioButton radioButton;
    LinearLayout namattl,laynorm;
    EditText namapasien,ttlpasien;
    Button btnBooking;
    Calendar myCalendar;
    SharedPreferences sp;
    String tokenkey,idvenue,idmember;
    String[] arraySpinnerRM;
    ProgressDialog progressDialog;
    RetrofitClient retro;
    AutoCompleteTextView nonik;
    List<String> listnik;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(PasienLama.this,PilihanDaftar.class);
        startActivity(i);
        overridePendingTransition(
                0,
                R.anim.top_out
        );
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call
                //stopActivityTask();  // finish() here.
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasien_lama);
            listnik = new ArrayList<>();

            retro = new RetrofitClient();

        Toolbar toolbar = (Toolbar) findViewById(R.id.custtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pasien Lama");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            progressDialog = new ProgressDialog(PasienLama.this);
            progressDialog.setMessage("Memuat..");
            progressDialog.setCancelable(false);

        laynorm = (LinearLayout) findViewById(R.id.layNoRM);

        radioMetode = (RadioGroup) findViewById(R.id.radioPilihanLama);
        nonik = (AutoCompleteTextView) findViewById(R.id.norm);
            namattl = (LinearLayout) findViewById(R.id.layoutNamaTTL);
        namapasien = (EditText) findViewById(R.id.namapasien);
        ttlpasien = (EditText) findViewById(R.id.ttlpasien);

        sp = getSharedPreferences("sp",MODE_PRIVATE);
        tokenkey = sp.getString("tokenkey","");
        idvenue = sp.getString("idvenue","");
        idmember = sp.getString("idmember","");

        ttlpasien.setInputType(InputType.TYPE_NULL);

        namattl.setVisibility(View.GONE);
        nonik.setVisibility(View.GONE);
        laynorm.setVisibility(View.GONE);

        radioMetode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                radioButton = (RadioButton) findViewById(checkedId);
                if (radioButton.getText().equals("No. NIK  ")){
                    laynorm.setVisibility(View.VISIBLE);
                    nonik.setVisibility(View.VISIBLE);
                    namattl.setVisibility(View.GONE);
                }else{
                    nonik.setVisibility(View.GONE);
                    laynorm.setVisibility(View.GONE);
                    namattl.setVisibility(View.VISIBLE);
                }
            }
        });

        radioMetode.check(R.id.radioRM);

        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        ttlpasien.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(PasienLama.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final Spinner sItems = (Spinner) findViewById(R.id.norm_spinner);

        arraySpinnerRM = new String[0];
        final List<String> rmList =  new ArrayList<>(Arrays.asList(arraySpinnerRM));
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(PasienLama.this, android.R.layout.simple_spinner_item, rmList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sItems.setAdapter(adapter);

            String url = "http://nuqueue.web.id/api/";
            final APIService service = RetrofitClient.getClient(url).create(APIService.class);
            Call<ModelNIK> call = service.getnik(tokenkey);
            call.enqueue(new Callback<ModelNIK>() {
                @Override
                public void onResponse(Call<ModelNIK> call, Response<ModelNIK> response) {
                    if (response.isSuccessful()){
                        List<ResultNIK> data = response.body().getResult();
                        if (response.body().getMessage().equals("data ditemukan")){
                            for (ResultNIK n: data) {
                                listnik.add(n.getNoNik());
                            }
                        }
                    }else{
                        Toast.makeText(PasienLama.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ModelNIK> call, Throwable throwable) {
                    Toast.makeText(PasienLama.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                }
            });

            ArrayAdapter<String> adapternik = new ArrayAdapter<String>(PasienLama.this,android.R.layout.simple_list_item_1,listnik);
            nonik.setAdapter(adapternik);

            btnBooking = (Button) findViewById(R.id.btnBooking);
            btnBooking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (nonik.getText().toString().isEmpty()){
                        Toast.makeText(PasienLama.this, "Silahkan isi No. NIK terlebih dahulu", Toast.LENGTH_SHORT).show();
                    }else{
                        progressDialog.show();

                        String url = "http://nuqueue.web.id/api/";
                        final APIService service = RetrofitClient.getClient(url).create(APIService.class);
                        Call<ModelPasien> call = service.cekpasienlama(nonik.getText().toString());
                        call.enqueue(new Callback<ModelPasien>() {
                            @Override
                            public void onResponse(Call<ModelPasien> call, Response<ModelPasien> response) {
                                if (response.isSuccessful()){
                                    if (response.body().getMessage().equals("data ditemukan")){
                                        progressDialog.dismiss();
                                        List<ResultPasien> data = response.body().getResult();
                                        SharedPreferences.Editor editor = getSharedPreferences("sp", MODE_PRIVATE).edit();
                                        for (ResultPasien d: data) {
                                            editor.putString("nik_pasien",d.getNoNik());
                                            editor.putString("alamat_pasien",d.getAlamatPasien());
                                            editor.putString("tgllahir_pasien",d.getTgllahirPasien());
                                            editor.putString("jk_pasien",d.getJkPasien());
                                            editor.putString("telepon_pasien",d.getTeleponPasien());
                                            editor.putString("nama_pasien",d.getNamaPasien());
                                        }
                                        editor.commit();
                                        Toast.makeText(PasienLama.this, "Data NIK ditemukan", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(PasienLama.this,KonfirmasiPendaftaran.class);
                                        i.putExtra("tipe_daftar","lama_lokal");
                                        startActivity(i);
                                    }else{
                                        String url2 = sp.getString("url_api","");
                                        final APIService service2 = RetrofitClient2.getClient(url2).create(APIService.class);
                                        Call<ModelPasien> call2 = service2.cekpasien(nonik.getText().toString());
                                        call2.enqueue(new Callback<ModelPasien>() {
                                            @Override
                                            public void onResponse(Call<ModelPasien> call, Response<ModelPasien> response) {
                                                if (response.isSuccessful()){
                                                    if (response.body().getMessage().equals("data ditemukan")){
                                                        progressDialog.dismiss();
                                                        List<ResultPasien> data = response.body().getResult();

                                                        SharedPreferences.Editor editor = getSharedPreferences("sp", MODE_PRIVATE).edit();
                                                        for (ResultPasien d: data) {
                                                            editor.putString("id_pasien",d.getIdPasien());
                                                            editor.putString("alamat_pasien",d.getAlamatPasien());
                                                            editor.putString("tgllahir_pasien",d.getTgllahirPasien());
                                                            editor.putString("jk_pasien",d.getJkPasien());
                                                            editor.putString("telepon_pasien",d.getTeleponPasien());
                                                            editor.putString("nama_pasien",d.getNamaPasien());
                                                            editor.putString("goldar_pasien",d.getGoldarPasien());
                                                            editor.putString("nik_pasien",d.getNoNik());
                                                        }
                                                        editor.commit();
                                                        Toast.makeText(PasienLama.this, "Data NIK ditemukan", Toast.LENGTH_SHORT).show();
                                                        Intent i = new Intent(PasienLama.this,KonfirmasiPendaftaran.class);
                                                        i.putExtra("tipe_daftar","lama_rs");
                                                        startActivity(i);
                                                    }else{
                                                        progressDialog.dismiss();
                                                        Toast.makeText(PasienLama.this, "Data NIK tidak ditemukan.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }else{
                                                    progressDialog.dismiss();
                                                    Toast.makeText(PasienLama.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ModelPasien> call, Throwable throwable) {
                                                progressDialog.dismiss();
                                                Toast.makeText(PasienLama.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }else{
                                    progressDialog.dismiss();
                                    Toast.makeText(PasienLama.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ModelPasien> call, Throwable throwable) {
                                progressDialog.dismiss();
                                Toast.makeText(PasienLama.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ttlpasien.setText(sdf.format(myCalendar.getTime()));
    }

}

