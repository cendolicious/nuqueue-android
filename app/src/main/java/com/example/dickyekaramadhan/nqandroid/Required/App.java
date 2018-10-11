package com.example.dickyekaramadhan.nqandroid.Required;

import android.app.Application;


import com.example.dickyekaramadhan.nqandroid.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by DICKYEKA on 19/03/2018.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/OpenSans-Light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}