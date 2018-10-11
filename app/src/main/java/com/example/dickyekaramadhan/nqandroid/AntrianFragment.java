package com.example.dickyekaramadhan.nqandroid;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dickyekaramadhan.nqandroid.Adapter.AntrianAdapter;
import com.example.dickyekaramadhan.nqandroid.BerandaFragment;
import com.example.dickyekaramadhan.nqandroid.Model.AntrianModel;
import com.example.dickyekaramadhan.nqandroid.Model.ModelAntrianUser;
import com.example.dickyekaramadhan.nqandroid.Model.ModelHistory;
import com.example.dickyekaramadhan.nqandroid.Model.ModelInsert;
import com.example.dickyekaramadhan.nqandroid.R;
import com.example.dickyekaramadhan.nqandroid.Required.APIService;
import com.example.dickyekaramadhan.nqandroid.Required.ClickListener;
import com.example.dickyekaramadhan.nqandroid.Required.RecyclerTouchListener;
import com.example.dickyekaramadhan.nqandroid.Required.RetrofitClient;
import com.example.dickyekaramadhan.nqandroid.Result.ResultAntrianUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AntrianFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    TextView title,subtitle;
    Typeface typeface1;
    ProgressDialog progressDialog;
    SharedPreferences sp;
    String tokenkey;
    JSONObject jsonData,jsonAntrian;
    String[] id,namaklinik,namapoli,noantrian,booking_date,current_number,category_id;
    LinearLayout notavailble;
    Button psnskrg;
    RecyclerView recyclerView;
    List<AntrianModel> antrianList;
    AntrianAdapter adapter;
    AntrianModel antrianModel;
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    String[] id_antrian;
    SwipeRefreshLayout swipeRefreshLayout;

    public AntrianFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Memuat..");
        progressDialog.show();
        progressDialog.setCancelable(false);

        sp = getActivity().getSharedPreferences("sp",getActivity().MODE_PRIVATE);
        tokenkey = sp.getString("tokenkey","");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_antrian, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        typeface1 = Typeface.createFromAsset(getContext().getAssets(),"font/Lato-Light.ttf");

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        notavailble = (LinearLayout) view.findViewById(R.id.blmadaantrian);
        psnskrg = (Button) view.findViewById(R.id.pesanskrg);
        notavailble.setVisibility(View.GONE);

        //RECYCLER BOOKMARK
        antrianList = new ArrayList<AntrianModel>();
        adapter = new AntrianAdapter(getActivity(),antrianList);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(RecyclerViewLayoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                AlertDialog.Builder warning = new AlertDialog.Builder(getActivity());
                warning.setTitle("Apakah anda ingin membatalkan pesanan?");
                warning
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String url = "http://nuqueue.web.id/api/";
                                final APIService service = RetrofitClient.getClient(url).create(APIService.class);
                                Call<ModelInsert> call = service.batalkanantrian(id_antrian[position]);
                                call.enqueue(new Callback<ModelInsert>() {
                                    @Override
                                    public void onResponse(Call<ModelInsert> call, Response<ModelInsert> response) {
                                        Toast.makeText(getActivity(), "Antrian berhasil dibatalkan", Toast.LENGTH_SHORT).show();
                                        Fragment fragment;
                                        FragmentManager fragmentManager;
                                        fragmentManager = getActivity().getSupportFragmentManager();
                                        fragmentManager.beginTransaction().replace(R.id.main_container, new AntrianFragment()).commit();
                                        fragment = new AntrianFragment();
                                        final FragmentTransaction transaction = fragmentManager.beginTransaction();
                                        transaction.replace(R.id.main_container, fragment).commit();
                                    }

                                    @Override
                                    public void onFailure(Call<ModelInsert> call, Throwable throwable) {
                                        Toast.makeText(getActivity(), "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                warning.create();
                warning.show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        String url = "http://nuqueue.web.id/api/";
        final APIService service = RetrofitClient.getClient(url).create(APIService.class);
        Call<ModelAntrianUser> call = service.getlistantrian(tokenkey);
        call.enqueue(new Callback<ModelAntrianUser>() {
            @Override
            public void onResponse(Call<ModelAntrianUser> call, Response<ModelAntrianUser> response) {
                if (response.isSuccessful()){
                    if (response.body().getMessage().equals("data tidak ditemukan")){
                        notavailble.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }else{
                        List<ResultAntrianUser> data = response.body().getResult();
                        id_antrian = new String[data.size()];
                        int i = 0;
                        for (ResultAntrianUser d: data) {
                            id_antrian[i] = d.getIdAntrian();
                            prepareAntrian(d.getNamaRs(),d.getNamaPoli(),d.getNoAntrian(),d.getTglAntrian(),d.getJmlAntrian(),d.getIdAntrian());
                            i++;
                        }
                    }
                    progressDialog.dismiss();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelAntrianUser> call, Throwable throwable) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });

        psnskrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment;
                FragmentManager fragmentManager;
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_container, new BerandaFragment()).commit();
                fragment = new BerandaFragment();
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
            }
        });
    }

    private void prepareAntrian(String namaklinik, String namapoli, String noantri, String tglpesan, String antriandilayani, String id){
        AntrianModel a = new AntrianModel(namaklinik,namapoli,noantri,tglpesan,antriandilayani,id);
        antrianList.add(a);
        adapter.notifyDataSetChanged();
    }

    public String getBookingId(int position){
        return id[position];
    }

    public String getCategoryId(int position){
        return category_id[position];
    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
        antrianList.clear();
        String url = "http://nuqueue.web.id/api/";
        final APIService service = RetrofitClient.getClient(url).create(APIService.class);
        Call<ModelAntrianUser> call = service.getlistantrian(tokenkey);
        call.enqueue(new Callback<ModelAntrianUser>() {
            @Override
            public void onResponse(Call<ModelAntrianUser> call, Response<ModelAntrianUser> response) {
                if (response.isSuccessful()){
                    if (response.body().getMessage().equals("data tidak ditemukan")){
                        notavailble.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }else{
                        List<ResultAntrianUser> data = response.body().getResult();
                        id_antrian = new String[data.size()];
                        int i = 0;
                        for (ResultAntrianUser d: data) {
                            id_antrian[i] = d.getIdAntrian();
                            prepareAntrian(d.getNamaRs(),d.getNamaPoli(),d.getNoAntrian(),d.getTglAntrian(),d.getJmlAntrian(),d.getIdAntrian());
                            i++;
                        }
                    }
                    progressDialog.dismiss();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelAntrianUser> call, Throwable throwable) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
