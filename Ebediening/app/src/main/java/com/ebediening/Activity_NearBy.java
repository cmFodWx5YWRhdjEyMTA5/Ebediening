package com.ebediening;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ebediening.Utilites.BaseActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Activity_NearBy extends BaseActivity implements View.OnClickListener,OnMapReadyCallback {
    LinearLayout linear_mapview, linear_nearby, linear_list, linear_map;
    TextView text_mapview, text_nearby;
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by);
        linear_mapview = (LinearLayout) findViewById(R.id.linear_mapview);
        linear_mapview.setOnClickListener(this);
        linear_mapview.setTag("0");
        linear_nearby = (LinearLayout) findViewById(R.id.linear_nearby);
        linear_nearby.setOnClickListener(this);
        linear_nearby.setTag("1");
        text_mapview = (TextView) findViewById(R.id.text_mapview);
        text_nearby = (TextView) findViewById(R.id.text_nearby);

        linear_list = (LinearLayout) findViewById(R.id.linear_list);
        linear_map = (LinearLayout) findViewById(R.id.linear_map);

        select_near_by(true);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_nearby:
                select_near_by(true);
                select_mapview(false);
                break;
            case R.id.linear_mapview:
                select_mapview(true);
                select_near_by(false);
                break;
        }
    }

    private void select_near_by(boolean select) {
        if (select) {
            linear_list.setVisibility(View.VISIBLE);
            text_nearby.setTextColor(getResources().getColor(R.color.white));
            linear_nearby.setTag("1");
            linear_nearby.setBackgroundResource(R.drawable.near_by_btn_bg_select);
        } else {
            linear_list.setVisibility(View.GONE);
            text_nearby.setTextColor(getResources().getColor(R.color.login_btn));
            linear_nearby.setTag("0");
            linear_nearby.setBackgroundResource(R.drawable.near_by_btn_bg_unselect);
        }
    }

    private void select_mapview(boolean select) {
        if (select) {
            linear_map.setVisibility(View.VISIBLE);
            text_mapview.setTextColor(getResources().getColor(R.color.white));
            linear_mapview.setTag("1");
            linear_mapview.setBackgroundResource(R.drawable.near_by_btn_bg_select);
        } else {
            linear_map.setVisibility(View.GONE);
            text_mapview.setTextColor(getResources().getColor(R.color.login_btn));
            linear_mapview.setTag("0");
            linear_mapview.setBackgroundResource(R.drawable.near_by_btn_bg_unselect);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
