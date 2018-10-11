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
import android.text.TextUtils;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProfilSaya extends AppCompatActivity {
    ProgressDialog progressDialog;
    RadioGroup radioJk,radioGoldar;
    RadioButton radioButton;
    Button btnTambahAsuransi,btnSimpan;
    EditText nama,ttl,alamat,telepon,nik;
    String jkReal,goldarReal,tokenkey,id_rs,idmember;
    Calendar myCalendar;
    SharedPreferences sp;
    APIService service;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_saya);

        progressDialog = new ProgressDialog(ProfilSaya.this);
        progressDialog.setMessage("Memuat..");
        progressDialog.show();
        progressDialog.setCancelable(false);

        sp = getSharedPreferences("sp",MODE_PRIVATE);
        tokenkey = sp.getString("tokenkey","");

        btnSimpan = (Button) findViewById(R.id.btnSimpan);
        radioJk = (RadioGroup) findViewById(R.id.radioJK);
        radioGoldar = (RadioGroup) findViewById(R.id.radioGoldar);
        nama = (EditText) findViewById(R.id.editTextNama);
        ttl = (EditText) findViewById(R.id.editTextTTL);
        alamat = (EditText) findViewById(R.id.editTextAlamat);
        telepon = (EditText) findViewById(R.id.editTextTelepon);
        nik = (EditText) findViewById(R.id.editTextNIK);

        Toolbar toolbar = (Toolbar) findViewById(R.id.custtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profil Saya");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                new DatePickerDialog(ProfilSaya.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        String url = "http://nuqueue.web.id/api/";
        final APIService service = RetrofitClient.getClient(url).create(APIService.class);
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
                    Toast.makeText(ProfilSaya.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelLogin> call, Throwable throwable) {
                progressDialog.dismiss();
                Toast.makeText(ProfilSaya.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nik.getText().toString().isEmpty() ||
                        nama.getText().toString().isEmpty() ||
                        ttl.getText().toString().isEmpty() ||
                        alamat.getText().toString().isEmpty() ||
                        telepon.getText().toString().isEmpty()){
                    Toast.makeText(ProfilSaya.this, "Silahkan Lengkapi Form Profil Anda", Toast.LENGTH_SHORT).show();
                }else {

                    String url = "http://nuqueue.web.id/api/";
                    final APIService service = RetrofitClient.getClient(url).create(APIService.class);
                    Call<ModelInsert> call = service.editprofil(tokenkey, nama.getText().toString(), ttl.getText().toString(), alamat.getText().toString(), telepon.getText().toString(), jkReal, goldarReal, nik.getText().toString());
                    call.enqueue(new Callback<ModelInsert>() {
                        @Override
                        public void onResponse(Call<ModelInsert> call, Response<ModelInsert> response) {
                            Toast.makeText(ProfilSaya.this, "Profil berhasil diubah", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(ProfilSaya.this, MainActivity.class);
                            startActivity(i);
                        }

                        @Override
                        public void onFailure(Call<ModelInsert> call, Throwable throwable) {
                            Toast.makeText(ProfilSaya.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                        }
                    });
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
