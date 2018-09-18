package com.ebediening;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ebediening.Response.OrdrResponse;
import com.ebediening.Response.TrackResponse;
import com.ebediening.Utilites.BaseActivity;
import com.ebediening.Utilites.Ebediening;
import com.ebediening.Utilites.EbedieningService;
import com.ebediening.Utilites.RestClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackActivity extends BaseActivity implements OnMapReadyCallback, ConnectionCallbacks,
        OnConnectionFailedListener,
        LocationListener{

    public static final String TRACK = "track";
    OrdrResponse.OrderData orderData;
    GoogleMap mMap;
    Location location;
    SupportMapFragment mMapFragment;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        ButterKnife.bind(this);
        TextView title = (TextView) toolbar.findViewById(R.id.text_view_toolbar);
        title.setText("TRACK ORDER");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Drawable drawable = (getResources().getDrawable(R.drawable.ic_chevron_left_black_24dp));
        toolbar.setNavigationIcon(drawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });
        orderData = (OrdrResponse.OrderData) getIntent().getParcelableExtra(TRACK);
        trackOrder(orderData.getId());
        mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds


    }

    @Override
    protected void onResume() {
        super.onResume();
        //Now lets connect to the API
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");

        //Disconnect from API onPause()
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }


    }

    /**
     * If connected get lat and long
     *
     */
    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();

            Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    /**
     * If locationChanges change lat and long
     *
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
        LatLng redmond = new LatLng(currentLatitude, currentLongitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(redmond));
        Marker marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_on_black_24dp)).position(redmond));
        marker.showInfoWindow();
        Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void trackOrder(String orderId) {
        EbedieningService ebedieningService = RestClient.getInstance().getClient().create(EbedieningService.class);
        showProgressbar("Loading", "Please wait...");
        ebedieningService.trackStatus(orderId).
                enqueue(new Callback<TrackResponse>() {
                    @Override
                    public void onResponse(Call<TrackResponse> call, Response<TrackResponse> response) {
                        if (response.body().getFlag()==1){
                            setColor(response.body().getTrackData());
                        }
                        else
                        {

                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<TrackResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), Ebediening.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setColor(TrackResponse.TrackData trackOrder) {
        tvAddress.setText(trackOrder.getAddress());
        tvTime.setText("ESTIMATED DELIVERY TIME: "+trackOrder.getEstimatedTime());
        tvOrder.setText(trackOrder.getId());
        if (trackOrder.getIsOrderPlaced()==null){}
        else {
            if (trackOrder.getIsOrderPlaced().equalsIgnoreCase("1")) {
                rlPlace.setBackgroundResource(R.drawable.circle_lg);
                t1.setTextColor(getResources().getColor(R.color.login_btn));
                t2.setTextColor(getResources().getColor(R.color.login_btn));
                v1.setBackgroundColor(getResources().getColor(R.color.login_btn));
            } else {
                t1.setTextColor(getResources().getColor(R.color.white));
                t2.setTextColor(getResources().getColor(R.color.white));
                rlPlace.setBackgroundResource(R.drawable.circle_gg);
                v1.setBackgroundColor(getResources().getColor(R.color.white));
            }
        }


        if (trackOrder.getIsOrderConfirmed()==null){}
        else {

            if (trackOrder.getIsOrderConfirmed().equalsIgnoreCase("1")) {
                t3.setTextColor(getResources().getColor(R.color.login_btn));
                t4.setTextColor(getResources().getColor(R.color.login_btn));
                rlConfirm.setBackgroundResource(R.drawable.circle_lg);
                v2.setBackgroundColor(getResources().getColor(R.color.login_btn));
            } else {
                t3.setTextColor(getResources().getColor(R.color.white));
                t4.setTextColor(getResources().getColor(R.color.white));
                rlConfirm.setBackgroundResource(R.drawable.circle_gg);
                v2.setBackgroundColor(getResources().getColor(R.color.white));
            }
        }

        if (trackOrder.getIsOrderProcessed()==null){}
        else {
            if (trackOrder.getIsOrderProcessed().equalsIgnoreCase("1")) {
                t5.setTextColor(getResources().getColor(R.color.login_btn));
                t6.setTextColor(getResources().getColor(R.color.login_btn));
                rlProcess.setBackgroundResource(R.drawable.circle_lg);
                v3.setBackgroundColor(getResources().getColor(R.color.login_btn));
            } else {
                t5.setTextColor(getResources().getColor(R.color.white));
                t6.setTextColor(getResources().getColor(R.color.white));
                rlProcess.setBackgroundResource(R.drawable.circle_gg);
                v3.setBackgroundColor(getResources().getColor(R.color.white));
            }
        }

        if (trackOrder.getIsReadyToPickup()==null){}
        else {
            if (trackOrder.getIsReadyToPickup().equals("1")) {
                t7.setTextColor(getResources().getColor(R.color.login_btn));
                t8.setTextColor(getResources().getColor(R.color.login_btn));
                rlReady.setBackgroundResource(R.drawable.circle_lg);
                v4.setBackgroundColor(getResources().getColor(R.color.login_btn));
            } else {
                t7.setTextColor(getResources().getColor(R.color.white));
                t8.setTextColor(getResources().getColor(R.color.white));
                rlReady.setBackgroundResource(R.drawable.circle_gg);
                v4.setBackgroundColor(getResources().getColor(R.color.white));
            }
        }


        if (trackOrder.getIsDelivered()==null){}
        else {

            if (trackOrder.getIsDelivered().equals("1")) {
                t9.setTextColor(getResources().getColor(R.color.login_btn));
                t10.setTextColor(getResources().getColor(R.color.login_btn));
                rlCollect.setBackgroundResource(R.drawable.circle_lg);
            } else {
                t9.setTextColor(getResources().getColor(R.color.white));
                t10.setTextColor(getResources().getColor(R.color.white));
                rlCollect.setBackgroundResource(R.drawable.circle_gg);
            }
        }
    }

    @BindView(R.id.tv_address)
    TextView tvAddress;

    @BindView(R.id.tv_time)
    TextView tvTime;

    @BindView(R.id.tv_order)
    TextView tvOrder;

    @BindView(R.id.rl_place)
    RelativeLayout rlPlace;

    @BindView(R.id.rl_confirm)
    RelativeLayout rlConfirm;

    @BindView(R.id.rl_process)
    RelativeLayout rlProcess;

    @BindView(R.id.rl_ready)
    RelativeLayout rlReady;

    @BindView(R.id.rl_collect)
    RelativeLayout rlCollect;

    @BindView(R.id.t1)
    TextView t1;

    @BindView(R.id.t2)
    TextView t2;

    @BindView(R.id.t3)
    TextView t3;

    @BindView(R.id.t4)
    TextView t4;

    @BindView(R.id.t5)
    TextView t5;

    @BindView(R.id.t6)
    TextView t6;

    @BindView(R.id.t7)
    TextView t7;

    @BindView(R.id.t8)
    TextView t8;

    @BindView(R.id.t9)
    TextView t9;

    @BindView(R.id.t10)
    TextView t10;

    @BindView(R.id.v1)
    View v1;

    @BindView(R.id.v2)
    View v2;

    @BindView(R.id.v3)
    View v3;

    @BindView(R.id.v4)
    View v4;

    @BindView(R.id.toolbar)
    android.support.v7.widget.Toolbar toolbar;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
        mMap.setOnMyLocationClickListener(onMyLocationClickListener);
        enableMyLocationIfPermitted();

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(11);
    }

    private void enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

    private void showDefaultLocation() {
        Toast.makeText(this, "Location permission not granted, " +
                        "showing default location",
                Toast.LENGTH_SHORT).show();
        LatLng redmond = new LatLng(47.6739881, -122.121512);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(redmond));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocationIfPermitted();
                } else {
                    showDefaultLocation();
                }
                return;
            }

        }
    }

    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
            new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {
                    mMap.setMinZoomPreference(15);
                    return false;
                }
            };

    private GoogleMap.OnMyLocationClickListener onMyLocationClickListener =
            new GoogleMap.OnMyLocationClickListener() {
                @Override
                public void onMyLocationClick(@NonNull Location location) {

                    mMap.setMinZoomPreference(12);

                    CircleOptions circleOptions = new CircleOptions();
                    circleOptions.center(new LatLng(location.getLatitude(),
                            location.getLongitude()));

                    circleOptions.radius(200);
                    circleOptions.fillColor(Color.RED);
                    circleOptions.strokeWidth(6);

                    mMap.addCircle(circleOptions);
                }
            };

}
