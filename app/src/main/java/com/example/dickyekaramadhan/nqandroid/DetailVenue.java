package com.example.dickyekaramadhan.nqandroid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dickyekaramadhan.nqandroid.Model.ModelRS;
import com.example.dickyekaramadhan.nqandroid.Required.APIService;
import com.example.dickyekaramadhan.nqandroid.Required.RetrofitClient;
import com.example.dickyekaramadhan.nqandroid.Result.ResultRS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DetailVenue extends AppCompatActivity {

    Button daftar;
    LinearLayout btnLokasi,btnFav,telpvenue;
    SharedPreferences sp;
    ProgressDialog progressDialog;
    TextView alamatvenue,titleKlinik,telptext;
    ImageView starFav,starUnFav;
    Typeface typeface1;

    String id_rs,nama_rs,alamat_rs,telepon_rs,deskripsi_rs,email_rs,lat_rs,lon_rs,url_api;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getIntent().getStringExtra("frombook").equals("book")){
            Intent i = new Intent(DetailVenue.this, MainActivity.class);
            startActivity(i);
            overridePendingTransition(
                    0,
                    R.anim.top_out
            );
        }else{
            Intent i = new Intent(DetailVenue.this,ListVenue.class);
            startActivity(i);
            overridePendingTransition(
                    0,
                    R.anim.top_out
            );
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_venue);

        Toolbar toolbar = (Toolbar) findViewById(R.id.custtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        daftar = (Button) findViewById(R.id.btnDaftar);
        btnLokasi = (LinearLayout) findViewById(R.id.btnLokasi);
        btnFav = (LinearLayout) findViewById(R.id.btnFav);
        starFav = (ImageView) findViewById(R.id.starFavorited);
        starUnFav = (ImageView) findViewById(R.id.starunFavorited);
        alamatvenue = (TextView) findViewById(R.id.alamatvenue);
        titleKlinik = (TextView) findViewById(R.id.titleKlinik);
        telptext = (TextView) findViewById(R.id.telptext);

        telpvenue = (LinearLayout) findViewById(R.id.telpvenue);

        starFav.setVisibility(View.GONE);
        starUnFav.setVisibility(View.GONE);
        btnLokasi.setVisibility(View.GONE);

        typeface1 = Typeface.createFromAsset(getAssets(),"font/OpenSans-Light.ttf");
        titleKlinik.setTypeface(typeface1);

        progressDialog = new ProgressDialog(DetailVenue.this);
        progressDialog.setMessage("Memuat..");
        progressDialog.show();
        progressDialog.setCancelable(false);

        sp = getSharedPreferences("sp",MODE_PRIVATE);
        id_rs = sp.getString("id_rs","");

        final String url = "http://nuqueue.web.id/api/";
        final APIService service = RetrofitClient.getClient(url).create(APIService.class);
        Call<ModelRS> call = service.getrumahsakit(id_rs);
        call.enqueue(new Callback<ModelRS>() {
            @Override
            public void onResponse(Call<ModelRS> call, Response<ModelRS> response) {
                if (response.isSuccessful()){
                    ModelRS databody = response.body();
                    List<ResultRS> data = databody.getResult();
                    nama_rs = new String();
                    alamat_rs = new String();
                    telepon_rs = new String();
                    deskripsi_rs = new String();
                    email_rs = new String();
                    lat_rs = new String();
                    lon_rs = new String();
                    url_api = new String();

                    for (ResultRS d: data) {
                        nama_rs = d.getNamaRs();
                        alamat_rs = d.getAlamatRs();
                        telepon_rs = d.getTeleponRs();
                        deskripsi_rs = d.getDeskripsiRs();
                        email_rs = d.getEmailRs();
                        lat_rs = d.getLatRs();
                        lon_rs = d.getLonRs();
                        url_api = d.getUrlApi();
                    }

                    titleKlinik.setText(nama_rs);
                    alamatvenue.setText(alamat_rs);
                    getSupportActionBar().setTitle(nama_rs);

                    telptext.setText(telepon_rs);
                    telpvenue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = null;
                            intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telepon_rs));
                            startActivity(intent);
                        }
                    });

                    SharedPreferences bookmark = getSharedPreferences("bookmark",MODE_PRIVATE);
                    String stringBookmark = bookmark.getString("listbookmarked","");
                    JSONArray jsonArrayBookmark;

                    if (stringBookmark.equals("")){
                        starFav.setVisibility(View.GONE);
                        starUnFav.setVisibility(View.VISIBLE);
                    }else{
                        try {
                            jsonArrayBookmark = new JSONArray(bookmark.getString("listbookmarked",""));
                            Boolean bookmarkedStatus = false;
                            for (int j=0;j<jsonArrayBookmark.length();j++){
                                JSONObject jsonCheck = jsonArrayBookmark.getJSONObject(j);
                                if (jsonCheck.getString("namavenue").equals(nama_rs)){
                                    bookmarkedStatus = true;
                                    break;
                                }
                            }
                            if (bookmarkedStatus){
                                starFav.setVisibility(View.VISIBLE);
                            }else{
                                starUnFav.setVisibility(View.VISIBLE);
                            }

                            btnLokasi.setVisibility(View.VISIBLE);
                            if (lat_rs.equals("0") || lon_rs.equals("0")){
                                btnLokasi.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(DetailVenue.this, "Mohon maaf, RS belum melengkapi data lokasi.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else{
                                btnLokasi.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent i = new Intent(DetailVenue.this,MapsVenue.class);
                                        i.putExtra("lat",lat_rs);
                                        i.putExtra("lon",lon_rs);
                                        i.putExtra("namavenue",nama_rs);
                                        startActivity(i);
                                    }
                                });
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    progressDialog.dismiss();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(DetailVenue.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelRS> call, Throwable throwable) {
                progressDialog.dismiss();
                Toast.makeText(DetailVenue.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),SubVenue.class);
                SharedPreferences.Editor editor = getSharedPreferences("sp", MODE_PRIVATE).edit();
                editor.putString("nama_rs",nama_rs);
                editor.putString("url_api",url_api);
                editor.commit();
                startActivity(i);
            }
        });

        btnFav.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                SharedPreferences bookmark = getSharedPreferences("bookmark",MODE_PRIVATE);
                String stringBookmark = bookmark.getString("listbookmarked","");
                SharedPreferences.Editor editor = getSharedPreferences("bookmark", MODE_PRIVATE).edit();
                JSONArray jsonArrayBookmark;
                JSONObject jsonBookmark;
                jsonBookmark = new JSONObject();

                if (stringBookmark.equals("")){
                    jsonArrayBookmark = new JSONArray();
                    try {
                        jsonBookmark.put("id",id_rs);
                        jsonBookmark.put("namavenue",nama_rs);
                        jsonBookmark.put("lat",lat_rs);
                        jsonBookmark.put("lon",lon_rs);
                        jsonBookmark.put("address",alamat_rs);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    starFav.setVisibility(View.VISIBLE);
                    starUnFav.setVisibility(View.GONE);
                    Toast.makeText(DetailVenue.this, "Venue Berhasil di Bookmark", Toast.LENGTH_SHORT).show();
                    jsonArrayBookmark.put(jsonBookmark);
                    editor.putString("listbookmarked",jsonArrayBookmark.toString());
                }else{
                    try {
                        jsonArrayBookmark = new JSONArray(bookmark.getString("listbookmarked",""));
                        Boolean bookmarkedStatus = false;
                        for (int i=0;i<jsonArrayBookmark.length();i++){
                            JSONObject jsonCheck = jsonArrayBookmark.getJSONObject(i);
                            if (jsonCheck.getString("namavenue").equals(nama_rs)){
                                bookmarkedStatus = true;
                                break;
                            }
                        }
                        if (bookmarkedStatus){
                            starFav.setVisibility(View.GONE);
                            starUnFav.setVisibility(View.VISIBLE);
                            Toast.makeText(DetailVenue.this, "Venue Sudah di hapus dari Bookmark", Toast.LENGTH_SHORT).show();
                            for (int i=0;i<jsonArrayBookmark.length();i++){
                                JSONObject jsonCheck = jsonArrayBookmark.getJSONObject(i);
                                if (jsonCheck.getString("namavenue").equals(nama_rs)){
                                    jsonArrayBookmark.remove(i);
                                    break;
                                }
                            }
                            editor.putString("listbookmarked",jsonArrayBookmark.toString());
                        }else{
                            starFav.setVisibility(View.VISIBLE);
                            starUnFav.setVisibility(View.GONE);
                            try {
                                jsonBookmark.put("id",id_rs);
                                jsonBookmark.put("namavenue",nama_rs);
                                jsonBookmark.put("lat",lat_rs);
                                jsonBookmark.put("lon",lon_rs);
                                jsonBookmark.put("address",alamat_rs);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(DetailVenue.this, "Venue Berhasil di Bookmark", Toast.LENGTH_SHORT).show();
                            jsonArrayBookmark.put(jsonBookmark);
                            editor.putString("listbookmarked",jsonArrayBookmark.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                editor.commit();
            }
        });
    }
}
