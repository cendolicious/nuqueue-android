package com.example.dickyekaramadhan.nqandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dickyekaramadhan.nqandroid.Adapter.OptionAdapter;
import com.example.dickyekaramadhan.nqandroid.Model.OptionModel;
import com.example.dickyekaramadhan.nqandroid.Required.ClickListener;
import com.example.dickyekaramadhan.nqandroid.Required.RecyclerTouchListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class AkunFragment extends Fragment {
    List<OptionModel> optionList = new ArrayList<>();
    RecyclerView optionRecycler;
    OptionAdapter optionAdapter;
    Intent i;
    JSONObject jsonSaldo,jsonData;
    SharedPreferences sp;
    String tokenkey;

    public AkunFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = getActivity().getSharedPreferences("sp",getActivity().MODE_PRIVATE);
        tokenkey = sp.getString("tokenkey","");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_akun, container, false);

        optionRecycler = (RecyclerView) v.findViewById(R.id.recycler_view);
        optionAdapter = new OptionAdapter(optionList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        optionRecycler.setLayoutManager(mLayoutManager);
        optionRecycler.setItemAnimator(new DefaultItemAnimator());
        optionRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        optionRecycler.setAdapter(optionAdapter);
        prepareOptionData(null);

        optionRecycler.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), optionRecycler, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                switch (position){
                    case 0:
                        i = new Intent(getActivity(),HistoryAntrian.class);
                        startActivity(i);
                        break;
                    case 1:
                        i = new Intent(getActivity(),ProfilSaya.class);
                        startActivity(i);
                        break;
                    case 2:
                        AlertDialog.Builder warning = new AlertDialog.Builder(getContext());
                        warning.setTitle("Apakah anda yakin akan keluar?");
                        warning
                                .setCancelable(false)
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getContext(),SplashScreen.class);
                                        getActivity().getSharedPreferences("sp",0).edit().clear().apply();
                                        startActivity(intent);
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
                        break;
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return v;

    }

    private void prepareOptionData(String formattedSaldo) {
        OptionModel OptionModel;

        OptionModel = new OptionModel("Riwayat Antrian", null);
        optionList.add(OptionModel);

        OptionModel = new OptionModel("Edit Profil", null);
        optionList.add(OptionModel);

        OptionModel = new OptionModel("Keluar", null);
        optionList.add(OptionModel);

        optionAdapter.notifyDataSetChanged();
    }

    private BroadcastReceiver mGetSaldo = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onReceive(Context context, final Intent intent) {
            try {
                optionList.clear();
                JSONObject result = new JSONObject(intent.getStringExtra("jsonsaldo"));
                String saldo = result.getString("data");
                NumberFormat formatter = new DecimalFormat("#,###");
                String formattedSaldo = formatter.format(Double.parseDouble(saldo.toString()));
                prepareOptionData(formattedSaldo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

}


