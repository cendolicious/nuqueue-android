package com.example.dickyekaramadhan.nqandroid;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dickyekaramadhan.nqandroid.Adapter.BookmarkAdapter;
import com.example.dickyekaramadhan.nqandroid.Adapter.LayananAdapter;
import com.example.dickyekaramadhan.nqandroid.Model.LayananModel;
import com.example.dickyekaramadhan.nqandroid.Model.VenueModel;
import com.example.dickyekaramadhan.nqandroid.Required.ClickListener;
import com.example.dickyekaramadhan.nqandroid.Required.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BerandaFragment extends Fragment{

    Typeface typeface1;
    String[] namalayanan;
    int[] idvenue;
    String tokenkey;
    SharedPreferences sp,spbookmark;

    //VARIABLE REC. LAYANAN
    List<LayananModel> layananList;
    LayananAdapter adapter2;
    RecyclerView recyclerView2;

    //VARIABLE REC. BOOKMARK
    RecyclerView recyclerView;
    List<VenueModel> venueList;
    BookmarkAdapter adapter;
    ProgressDialog progressDialog;

    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    LinearLayoutManager HorizontalLayout;

    TextView blmadabook;

    SharedPreferences.Editor editor;

    JSONArray jsonArrayBookmark;

    public BerandaFragment(){super();}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      /*  ActionBar mActionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(getActivity());

        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);*/

        editor = getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE).edit();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Memuat..");
        //progressDialog.show();
        progressDialog.setCancelable(false);

        sp = getActivity().getSharedPreferences("sp",getActivity().MODE_PRIVATE);
        tokenkey = sp.getString("tokenkey","");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_beranda, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        typeface1 = Typeface.createFromAsset(getContext().getAssets(),"font/OpenSans-Light.ttf");

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.custtoolbar);
        blmadabook = (TextView) view.findViewById(R.id.belumadabookmark);
        blmadabook.setVisibility(View.GONE);


        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(null);
        toolbar.addView(mCustomView);
        
        layananList = new ArrayList<LayananModel>();
        adapter2 = new LayananAdapter(getActivity(),layananList);
        recyclerView2 = (RecyclerView) view.findViewById(R.id.recycler_view2);
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView2.setLayoutManager(RecyclerViewLayoutManager);
        // Adding items to RecyclerView.

        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(HorizontalLayout);
        recyclerView2.setAdapter(adapter2);

        //RECYCLER BOOKMARK
        venueList = new ArrayList<VenueModel>();

        adapter = new BookmarkAdapter(getActivity(),venueList);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(RecyclerViewLayoutManager);
        // Adding items to RecyclerView.
        HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(HorizontalLayout);
        recyclerView.setAdapter(adapter);
        spbookmark = getActivity().getSharedPreferences("bookmark",getActivity().MODE_PRIVATE);
        if (spbookmark.getString("listbookmarked","").equals("[]") || spbookmark.getString("listbookmarked","").equals("")){
            blmadabook.setVisibility(View.VISIBLE);
        }
        if (!spbookmark.getString("listbookmarked","").equals("") || !spbookmark.getString("listbookmarked","").equals("[]")){
            try {
                jsonArrayBookmark = new JSONArray(spbookmark.getString("listbookmarked",""));
                idvenue = new int[jsonArrayBookmark.length()];
                for (int i = 0;i<jsonArrayBookmark.length();i++){
                    JSONObject jsonob = jsonArrayBookmark.getJSONObject(i);
                    idvenue[i] = jsonob.getInt("id");
                    prepareBookmark(jsonob.getString("namavenue"),jsonob.getString("address"),"0 KM");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        prepareLayanan("Daftar Rumah Sakit");

        //RECYCLER LAYANAN
        recyclerView2.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView2, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(getActivity(),ListVenue.class);
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        //RECYCLER BOOKMARK
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(getActivity(),DetailVenue.class);

                editor.putBoolean("bookmarked",true);
                editor.putString("id_rs",String.valueOf(idvenue[position]));
                editor.commit();
                i.putExtra("frombook","book");
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void prepareBookmark(String nama,String address, String range){
        VenueModel v = new VenueModel(nama,address,range,null);
        venueList.add(v);
        adapter.notifyDataSetChanged();
    }

    private void prepareLayanan(String nama){
        LayananModel l = new LayananModel(R.drawable.health,nama);
        layananList.add(l);
        adapter2.notifyDataSetChanged();
    }
}
