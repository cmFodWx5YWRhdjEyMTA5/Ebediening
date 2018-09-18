package com.ebediening;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.ebediening.Utilites.BaseActivity;
import com.ebediening.Utilites.ColorArcProgressBar;
import com.ebediening.Utilites.Preferences;
import com.ebediening.Utilites.SnowFlakesLayout;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Splash2Activity extends BaseActivity implements ColorArcProgressBar.ViewWasTouchedListener {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        ButterKnife.bind(this);
        setStatusBar();
        showAnimation();
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    private void showAnimation() {
        colorArcProgressBar.setVisibility(View.GONE);
        colorArcProgressBar.setCurrentValues(100);
        colorArcProgressBar.setWasTouchedListener(this);
        snowflakelayout.init();
        snowflakelayout.setWholeAnimateTiming(1000000);
        snowflakelayout.setAnimateDuration(1500);
        snowflakelayout.setGenerateSnowTiming(300);
        snowflakelayout.setRandomSnowSizeRange(75, 35);
        snowflakelayout.setImageResourceID(R.drawable.ic_star_black_24dp);
        snowflakelayout.setEnableRandomCurving(true);
        snowflakelayout.setEnableAlphaFade(true);
        snowflakelayout.startSnowing();
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();

    }

    @Override
    public void onViewTouched(String value) {
        if (value.matches("360.0") || value.contains("359.9") )
        {
            colorLogo.setVisibility(View.VISIBLE);
            colorArcProgressBar.setVisibility(View.GONE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (Preferences.getInstance().isLogIn())
                    {
                        Intent intent=new Intent(Splash2Activity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Intent intent=new Intent(Splash2Activity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }, 2000);
        }
        else {
            colorArcProgressBar.setVisibility(View.VISIBLE);
            colorLogo.setVisibility(View.GONE);
        }
    }


    @BindView(R.id.color_bar)
    ColorArcProgressBar colorArcProgressBar;

    @BindView(R.id.color_logo)
    ImageView colorLogo;

    @BindView(R.id.snowflakelayout)
    SnowFlakesLayout snowflakelayout;


}
