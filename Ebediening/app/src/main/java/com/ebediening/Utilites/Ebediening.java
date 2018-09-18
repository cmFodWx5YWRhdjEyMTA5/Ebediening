package com.ebediening.Utilites;

import android.support.multidex.MultiDexApplication;

import com.ebediening.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class Ebediening extends MultiDexApplication {

    private static Ebediening instance;
    public String SERVER_ERROR = "Unable to connect to server, please try again after sometime";


    @Override

    public void onCreate() {
        super.onCreate();
        instance = this;
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Poppins-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public static Ebediening getInstance() {
        return instance;
    }
}
