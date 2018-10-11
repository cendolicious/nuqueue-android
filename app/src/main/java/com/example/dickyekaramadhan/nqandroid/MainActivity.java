package com.example.dickyekaramadhan.nqandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.dickyekaramadhan.nqandroid.Model.ModelAntrianUser;
import com.example.dickyekaramadhan.nqandroid.Required.APIService;
import com.example.dickyekaramadhan.nqandroid.Required.RetrofitClient;
import com.example.dickyekaramadhan.nqandroid.Result.ResultAntrianUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    AHBottomNavigation bottomNavigation;
    Fragment fragment;
    FragmentManager fragmentManager;
    SharedPreferences sp;
    String tokenkey;
    int countdata;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        sp = getSharedPreferences("sp",MODE_PRIVATE);
        tokenkey = sp.getString("tokenkey","");

        fragmentManager = getSupportFragmentManager();

        if (!TextUtils.isEmpty(getIntent().getStringExtra("fromfragment"))){
            if (getIntent().getStringExtra("fromfragment").equals("akun")){
                fragmentManager.beginTransaction().replace(R.id.main_container, new AkunFragment()).commit();
            }
        }else{
            fragmentManager.beginTransaction().replace(R.id.main_container, new BerandaFragment()).commit();
        }
        
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.beranda, R.drawable.beranda, R.color.white);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.antrian, R.drawable.antrian, R.color.white);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.akun, R.drawable.akun, R.color.white);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item4);

        bottomNavigation.setDefaultBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
        bottomNavigation.setAccentColor(ContextCompat.getColor(this,R.color.white));
        bottomNavigation.setInactiveColor(ContextCompat.getColor(this,R.color.palewhite));
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                int id = position;
                switch (id){
                    case 0:
                        fragment = new BerandaFragment();
                        final FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.addToBackStack("Beranda");
                        transaction.replace(R.id.main_container, fragment).commit();
                        break;
                    case 1:
                        if (tokenkey.equals("")){
                            Intent i = new Intent(MainActivity.this,Login.class);
                            i.putExtra("from","akun");
                            Toast.makeText(MainActivity.this, "Silahkan masuk terlebih dahulu.", Toast.LENGTH_SHORT).show();
                            startActivity(i);
                        }else{
                            fragment = new AntrianFragment();
                            final FragmentTransaction transaction2 = fragmentManager.beginTransaction();
                            transaction2.addToBackStack("Antrian");
                            transaction2.replace(R.id.main_container, fragment).commit();
                        }
                        break;
                    case 2:
                        if (tokenkey.equals("")){
                            Intent i = new Intent(MainActivity.this,Login.class);
                            i.putExtra("from","akun");
                            Toast.makeText(MainActivity.this, "Silahkan masuk terlebih dahulu.", Toast.LENGTH_SHORT).show();
                            startActivity(i);
                        }else{
                            fragment = new AkunFragment();
                            final FragmentTransaction transaction4 = fragmentManager.beginTransaction();
                            transaction4.addToBackStack("[Akun]");
                            transaction4.replace(R.id.main_container, fragment).commit();
                        }
                        break;
                }
                return true;
            }
        });
    }
}
