package com.example.dickyekaramadhan.nqandroid;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.example.dickyekaramadhan.nqandroid.Adapter.VenueAdapter;
import com.example.dickyekaramadhan.nqandroid.Model.ModelRS;
import com.example.dickyekaramadhan.nqandroid.Model.VenueModel;
import com.example.dickyekaramadhan.nqandroid.Required.APIService;
import com.example.dickyekaramadhan.nqandroid.Required.ClickListener;
import com.example.dickyekaramadhan.nqandroid.Required.RecyclerTouchListener;
import com.example.dickyekaramadhan.nqandroid.Required.RetrofitClient;
import com.example.dickyekaramadhan.nqandroid.Result.ResultRS;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ListVenue extends AppCompatActivity {
    static final String TAG = "ListVenue";

    RecyclerView recyclerView;
    List<VenueModel> venueList;
    VenueAdapter adapter;
    VenueModel v;
    Context context;
    SharedPreferences sp;
    String tokenkey,namalayanan;
    int catlayanan;
    Bundle bundle;
    JSONObject jsoncatlayanan,jsonData;
    ProgressDialog progressDialog;
    TextView notavailable;
    String[] id_rs;
    LinearLayout btnTerpopuler,btnTerdekat;
    double latitude,longitude;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(ListVenue.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_venue);
        context = ListVenue.this;
        notavailable = (TextView) findViewById(R.id.tidaktersedia);
        notavailable.setVisibility(View.GONE);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Memuat..");
        progressDialog.setCancelable(false);

        sp = getSharedPreferences("sp",MODE_PRIVATE);
        tokenkey = sp.getString("tokenkey","");

        String url = "http://nuqueue.web.id/api/";
        final APIService service = RetrofitClient.getClient(url).create(APIService.class);
        Call<ModelRS> call = service.getrumahsakit("");
        call.enqueue(new Callback<ModelRS>() {
            @Override
            public void onResponse(Call<ModelRS> call, Response<ModelRS> response) {
                if (response.isSuccessful()){
                    ModelRS databody = response.body();
                    List<ResultRS> data = databody.getResult();
                    id_rs = new String[data.size()];
                    int i = 0;
                    for (ResultRS d: data) {
                        id_rs[i] = d.getIdRs();
                        prepareVenue(d.getNamaRs(),d.getAlamatRs(),d.getTeleponRs());
                        i++;
                    }
                }else{
                    Toast.makeText(ListVenue.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelRS> call, Throwable throwable) {
                Toast.makeText(ListVenue.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.custtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Kesehatan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        venueList = new ArrayList<VenueModel>();
        adapter = new VenueAdapter(this,venueList);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(ListVenue.this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(ListVenue.this,DetailVenue.class);

                SharedPreferences.Editor editor = getSharedPreferences("sp", MODE_PRIVATE).edit();
                editor.putString("id_rs",id_rs[position]);
                editor.commit();
                i.putExtra("frombook","list");
                startActivity(i);
                finish();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("key");
            Boolean status = intent.getBooleanExtra("status",true);
            if (status == false){
                progressDialog.dismiss();
                TSnackbar.make(findViewById(R.id.custtoolbar), Html.fromHtml("<font color=\"#ffffff\">Tidak ada koneksi internet.</font>"),TSnackbar.LENGTH_LONG).show();
            }else{
                progressDialog.dismiss();
            }
        }
    };

    private void prepareVenue(String name, String address,String range){
        VenueModel v = new VenueModel(name,address,range,null);
        venueList.add(v);
        adapter.notifyDataSetChanged();
    }
}
