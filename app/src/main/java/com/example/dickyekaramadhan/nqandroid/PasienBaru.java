package com.example.dickyekaramadhan.nqandroid;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.dickyekaramadhan.nqandroid.Model.ModelInsert;
import com.example.dickyekaramadhan.nqandroid.Model.ModelLogin;
import com.example.dickyekaramadhan.nqandroid.Required.APIService;
import com.example.dickyekaramadhan.nqandroid.Required.RetrofitClient;
import com.example.dickyekaramadhan.nqandroid.Result.ResultLogin;
import com.example.dickyekaramadhan.nqandroid.Result.ResultRS;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PasienBaru extends AppCompatActivity {

    RadioGroup radioJk,radioGoldar;
    RadioButton radioButton;
    Button btnTambahAsuransi,btnSimpan;
    EditText nama,ttl,alamat,telepon,nik;
    String jkReal,goldarReal,tokenkey,id_rs,idmember;
    Calendar myCalendar;
    SharedPreferences sp;
    ProgressDialog progressDialog;
    APIService service;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(PasienBaru.this,PilihanDaftar.class);
        startActivity(i);
        overridePendingTransition(
                0,
                R.anim.top_out
        );
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasien_baru);

        progressDialog = new ProgressDialog(PasienBaru.this);
        progressDialog.setMessage("Memuat..");
        progressDialog.show();
        progressDialog.setCancelable(false);

        Toolbar toolbar = (Toolbar) findViewById(R.id.custtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pasien Baru");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sp = getSharedPreferences("sp",MODE_PRIVATE);
        tokenkey = sp.getString("tokenkey","");
        id_rs = sp.getString("id_rs","");

        btnSimpan = (Button) findViewById(R.id.btnBooking);
        radioJk = (RadioGroup) findViewById(R.id.radioJK);
        radioGoldar = (RadioGroup) findViewById(R.id.radioGoldar);
        nama = (EditText) findViewById(R.id.editTextNama);
        ttl = (EditText) findViewById(R.id.editTextTTL);
        alamat = (EditText) findViewById(R.id.editTextAlamat);
        telepon = (EditText) findViewById(R.id.editTextTelepon);
        nik = (EditText) findViewById(R.id.editTextNIK);

        ttl.setInputType(InputType.TYPE_NULL);

        radioJk.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                radioButton = (RadioButton) findViewById(checkedId);
                if (radioButton.getText().equals("Pria")){
                    jkReal = "L";
                }else{
                    jkReal = "P";
                }
            }
        });

        radioJk.check(R.id.jkMale);

        radioGoldar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                radioButton = (RadioButton) findViewById(checkedId);
                if (radioButton.getText().equals("A")){
                    goldarReal = "A";
                }else if(radioButton.getText().equals("B")){
                    goldarReal = "B";
                }else if(radioButton.getText().equals("AB")){
                    goldarReal = "AB";
                }else{
                    goldarReal = "O";
                }
            }
        });

        radioGoldar.check(R.id.goldarA);

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

        ttl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(PasienBaru.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        String url = "http://nuqueue.web.id/api/";
        service = RetrofitClient.getClient(url).create(APIService.class);
        Call<ModelLogin> call = service.getprofiluser(tokenkey);
        call.enqueue(new Callback<ModelLogin>() {
            @Override
            public void onResponse(Call<ModelLogin> call, Response<ModelLogin> response) {
                if (response.isSuccessful()){
                    ModelLogin databody = response.body();
                    List<ResultLogin> data = databody.getResult();
                    for (ResultLogin d: data) {
                        nik.setText(d.getNo_nik());
                        nama.setText(d.getNamaUser());
                        ttl.setText(d.getTgllahirUser());
                        alamat.setText(d.getAlamatUser());
                        telepon.setText(d.getTeleponUser());

                        if (!TextUtils.isEmpty(d.getJkUser())){
                            if (d.getJkUser().equals("P")){
                                radioJk.check(R.id.jkFemale);
                            }else{
                                radioJk.check(R.id.jkMale);
                            }
                        }

                        if (!TextUtils.isEmpty(d.getGoldarUser())){
                            if (d.getGoldarUser().equals("A")){
                                radioGoldar.check(R.id.goldarA);
                            }else if (d.getGoldarUser().equals("B")){
                                radioGoldar.check(R.id.goldarB);
                            }else if (d.getGoldarUser().equals("AB")){
                                radioGoldar.check(R.id.goldarAB);
                            }else{
                                radioGoldar.check(R.id.goldarO);
                            }
                        }

                        progressDialog.dismiss();

                    }
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(PasienBaru.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelLogin> call, Throwable throwable) {
                progressDialog.dismiss();
                Toast.makeText(PasienBaru.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });

        Map<String, ?> allEntries = sp.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            //Toast.makeText(this, entry.getKey()+" : "+entry.getValue(), Toast.LENGTH_SHORT).show();
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
        }

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nik.getText().toString().isEmpty() ||
                        nama.getText().toString().isEmpty() ||
                        ttl.getText().toString().isEmpty() ||
                        alamat.getText().toString().isEmpty() ||
                        telepon.getText().toString().isEmpty()){
                    Toast.makeText(PasienBaru.this, "Silahkan lengkapi Form Pendaftaran", Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog.show();
                    SharedPreferences.Editor editor = getSharedPreferences("sp", MODE_PRIVATE).edit();
                    editor.putString("alamat_pasien",alamat.getText().toString());
                    editor.putString("tgllahir_pasien",ttl.getText().toString());
                    editor.putString("jk_pasien",jkReal);
                    editor.putString("telepon_pasien",telepon.getText().toString());
                    editor.putString("nama_pasien",nama.getText().toString());
                    editor.putString("goldar_pasien",goldarReal);
                    editor.putString("nik_pasien",nik.getText().toString());
                    editor.commit();
                    Intent i = new Intent(PasienBaru.this,KonfirmasiPendaftaran.class);
                    i.putExtra("tipe_daftar","baru");
                    startActivity(i);
                    /*String url = "http://nuqueue.web.id/api/";
                    service = RetrofitClient.getClient(url).create(APIService.class);
                    Call<ModelInsert> call2 = service.insertAntrian(tokenkey,id_rs,sp.getString("idpoli",""),sp.getString("idjadwal",""),sp.getString("tipe_daftar",""),nik.getText().toString());
                    call2.enqueue(new Callback<ModelInsert>() {
                        @Override
                        public void onResponse(Call<ModelInsert> call, Response<ModelInsert> response) {
                        }

                        @Override
                        public void onFailure(Call<ModelInsert> call, Throwable throwable) {
                            progressDialog.dismiss();
                            Log.d("error retro","err: "+throwable);

                            Call<ModelInsert> call3 = service.insertPasien(nama.getText().toString(),ttl.getText().toString(),alamat.getText().toString(),telepon.getText().toString(),jkReal,goldarReal,nik.getText().toString());
                            call3.enqueue(new Callback<ModelInsert>() {
                                @Override
                                public void onResponse(Call<ModelInsert> call, Response<ModelInsert> response) {

                                    Toast.makeText(PasienBaru.this, "Antrian berhasil di daftarkan", Toast.LENGTH_SHORT).show();
                                    Intent i  = new Intent(PasienBaru.this,MainActivity.class);
                                    startActivity(i);

                                }

                                @Override
                                public void onFailure(Call<ModelInsert> call, Throwable throwable) {

                                }
                            });
                        }
                    });*/
                }
            }
        });


    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ttl.setText(sdf.format(myCalendar.getTime()));
    }
}
