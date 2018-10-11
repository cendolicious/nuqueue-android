package com.example.dickyekaramadhan.nqandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PilihanDaftar extends AppCompatActivity {

    Button btnLama,btnBaru;
    SharedPreferences.Editor editor;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(PilihanDaftar.this, JadwalVenue.class);
        startActivity(i);
        overridePendingTransition(
                0,
                R.anim.top_in
        );
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
        setContentView(R.layout.activity_pilihan_daftar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.custtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Daftar Sebagai..");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnBaru = (Button) findViewById(R.id.btnBaru);
        btnLama = (Button) findViewById(R.id.btnLama);

        editor = getSharedPreferences("sp", MODE_PRIVATE).edit();

        btnLama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PilihanDaftar.this,PasienLama.class);
                i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                editor.putString("tipe_daftar","1");
                editor.commit();
                startActivity(i);
            }
        });

        btnBaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PilihanDaftar.this,PasienBaru.class);
                i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                editor.putString("tipe_daftar","2");
                editor.commit();
                startActivity(i);
            }
        });
    }
}
