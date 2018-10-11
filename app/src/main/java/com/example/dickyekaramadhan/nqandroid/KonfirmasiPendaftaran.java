package com.example.dickyekaramadhan.nqandroid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dickyekaramadhan.nqandroid.Model.ModelInsert;
import com.example.dickyekaramadhan.nqandroid.Required.APIService;
import com.example.dickyekaramadhan.nqandroid.Required.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class KonfirmasiPendaftaran extends AppCompatActivity {

    TextView namaMember, ttlMember, alamatMember, telpMember, jkMember;
    Button btnBooking;
    ProgressDialog progressDialog;
    SharedPreferences sp;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = null;
        if (getIntent().getStringExtra("tipe_daftar").equals("baru")) {
            i = new Intent(KonfirmasiPendaftaran.this, PasienBaru.class);
        } else {
            i = new Intent(KonfirmasiPendaftaran.this, PasienLama.class);
        }
        startActivity(i);

        overridePendingTransition(
                0,
                R.anim.top_in
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
        setContentView(R.layout.activity_konfirmasi_pendaftaran);
        sp = getSharedPreferences("sp", MODE_PRIVATE);

        namaMember = (TextView) findViewById(R.id.namaMember);
        ttlMember = (TextView) findViewById(R.id.ttlMember);
        alamatMember = (TextView) findViewById(R.id.alamatMember);
        telpMember = (TextView) findViewById(R.id.telpMember);
        jkMember = (TextView) findViewById(R.id.jkMember);
        btnBooking = (Button) findViewById(R.id.btnBooking);

        Toolbar toolbar = (Toolbar) findViewById(R.id.custtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Konfirmasi Pendaftaran");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(KonfirmasiPendaftaran.this);
        progressDialog.setMessage("Memuat..");
        progressDialog.setCancelable(false);



            namaMember.setText(sp.getString("nama_pasien", ""));
            ttlMember.setText(sp.getString("tgllahir_pasien", ""));
            alamatMember.setText(sp.getString("alamat_pasien", ""));
            telpMember.setText(sp.getString("telepon_pasien", ""));
            if (sp.getString("jk_pasien", "").equals("L"))
                jkMember.setText("Laki-Laki");
            else
                jkMember.setText("Perempuan");



            btnBooking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog.show();
                    if (getIntent().getStringExtra("tipe_daftar").equals("baru")) {
                        //INSERT ANTRIAN - INSERT PASIEN BARU
                        String url = "http://nuqueue.web.id/api/";
                        final APIService service = RetrofitClient.getClient(url).create(APIService.class);
                        Call<ModelInsert> call2 = service.insertAntrian(sp.getString("tokenkey", ""), sp.getString("id_rs", ""), sp.getString("idpoli", ""), sp.getString("idjadwal", ""), sp.getString("tipe_daftar", ""), sp.getString("nik_pasien", ""));
                        call2.enqueue(new Callback<ModelInsert>() {
                            @Override
                            public void onResponse(Call<ModelInsert> call, Response<ModelInsert> response) {
                            }

                            @Override
                            public void onFailure(Call<ModelInsert> call, Throwable throwable) {
                                progressDialog.dismiss();

                                Call<ModelInsert> call3 = service.insertPasien(
                                        sp.getString("nama_pasien", ""),
                                        sp.getString("tgllahir_pasien", ""),
                                        sp.getString("alamat_pasien", ""),
                                        sp.getString("telepon_pasien", ""),
                                        sp.getString("jk_pasien", ""),
                                        sp.getString("goldar_pasien", ""),
                                        sp.getString("nik_pasien", ""));
                                call3.enqueue(new Callback<ModelInsert>() {
                                    @Override
                                    public void onResponse(Call<ModelInsert> call, Response<ModelInsert> response) {

                                        Toast.makeText(KonfirmasiPendaftaran.this, "Antrian berhasil di daftarkan", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(KonfirmasiPendaftaran.this, MainActivity.class);
                                        startActivity(i);

                                    }

                                    @Override
                                    public void onFailure(Call<ModelInsert> call, Throwable throwable) {

                                    }
                                });
                            }
                        });

                    } else if (getIntent().getStringExtra("tipe_daftar").equals("lama_rs")) {
                        //INSERT ANTRIAN - INSERT PASIEN LAMA (RS)
                        String url = "http://nuqueue.web.id/api/";
                        final APIService service = RetrofitClient.getClient(url).create(APIService.class);
                        Call<ModelInsert> call2 = service.insertAntrian(sp.getString("tokenkey", ""), sp.getString("id_rs", ""), sp.getString("idpoli", ""), sp.getString("idjadwal", ""), sp.getString("tipe_daftar", ""), sp.getString("nik_pasien", ""));
                        call2.enqueue(new Callback<ModelInsert>() {
                            @Override
                            public void onResponse(Call<ModelInsert> call, Response<ModelInsert> response) {
                                progressDialog.dismiss();

                                Call<ModelInsert> call3 = service.insertPasien(
                                        sp.getString("nama_pasien", ""),
                                        sp.getString("tgllahir_pasien", ""),
                                        sp.getString("alamat_pasien", ""),
                                        sp.getString("telepon_pasien", ""),
                                        sp.getString("jk_pasien", ""),
                                        sp.getString("goldar_pasien", ""),
                                        sp.getString("nik_pasien", ""));
                                call3.enqueue(new Callback<ModelInsert>() {
                                    @Override
                                    public void onResponse(Call<ModelInsert> call, Response<ModelInsert> response) {

                                        Toast.makeText(KonfirmasiPendaftaran.this, "Antrian berhasil di daftarkan", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(KonfirmasiPendaftaran.this, MainActivity.class);
                                        startActivity(i);

                                    }

                                    @Override
                                    public void onFailure(Call<ModelInsert> call, Throwable throwable) {

                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<ModelInsert> call, Throwable throwable) {

                            }
                        });
                    } else {
                        //INSERT ANTRIAN - INSERT PASIEN LAMA (LOKAL)
                        String url = "http://nuqueue.web.id/api/";
                        final APIService service = RetrofitClient.getClient(url).create(APIService.class);
                        Call<ModelInsert> call2 = service.insertAntrian(sp.getString("tokenkey", ""), sp.getString("id_rs", ""), sp.getString("idpoli", ""), sp.getString("idjadwal", ""), sp.getString("tipe_daftar", ""), sp.getString("nik_pasien", ""));
                        call2.enqueue(new Callback<ModelInsert>() {
                            @Override
                            public void onResponse(Call<ModelInsert> call, Response<ModelInsert> response) {
                                progressDialog.dismiss();
                                Toast.makeText(KonfirmasiPendaftaran.this, "Antrian berhasil di daftarkan", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(KonfirmasiPendaftaran.this, MainActivity.class);
                                startActivity(i);
                            }

                            @Override
                            public void onFailure(Call<ModelInsert> call, Throwable throwable) {

                            }
                        });
                    }
                }
            });


        }
    }

