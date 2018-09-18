package com.ebediening;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ebediening.Utilites.AllAPIs;
import com.ebediening.Utilites.BaseActivity;
import com.ebediening.Utilites.CommonMethods;
import com.ebediening.Utilites.GPSTracker;
import com.ebediening.Utilites.Preferences;
import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import info.androidhive.barcode.BarcodeReader;

public class Activity_Scanner_Checkin extends BaseActivity implements BarcodeReader.BarcodeReaderListener {
    ImageView img_back;
    private BarcodeReader barcodeReader;
    GPSTracker tracker;
    ProgressBar progress_bar;
    RelativeLayout layout_scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_checkin);
        layout_scanner = (RelativeLayout) findViewById(R.id.layout_scanner);
        layout_scanner.setVisibility(View.VISIBLE);
        tracker = new GPSTracker(Activity_Scanner_Checkin.this);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_Scanner_Checkin.this.finish();
            }
        });
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_fragment);
    }

    @Override
    public void onScanned(Barcode barcode) {
        // play beep sound
        if (CommonMethods.checkConnection()) {
            barcodeReader.pauseScanning();
//            layout_scanner.setVisibility(View.GONE);
            post_data(barcode.displayValue);
        } else {
            CommonMethods.showtoast_no_connetion(Activity_Scanner_Checkin.this);
        }
    }

    private void post_data(final String displayValue) {
//        final ProgressDialog dialog = new ProgressDialog(Activity_Scanner_Checkin.this);
//        dialog.setMessage("Loading...");
//        dialog.setCancelable(false);
//        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AllAPIs.POST_SCAN_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getString("flag").equals("1")) {
                                Intent intent = new Intent(Activity_Scanner_Checkin.this, Activity_Check_IN.class);
                                startActivity(intent);
                                Activity_Scanner_Checkin.this.finish();
                            } else {
                                if (object.getString("message").equalsIgnoreCase("Invalid QR code.")) {
                                    CommonMethods.showtoast(Activity_Scanner_Checkin.this, "Please scan valid OR code");

                                } else if (object.getString("message").equalsIgnoreCase("Permission sent to pos for scaning")) {
                                    CommonMethods.showtoast(Activity_Scanner_Checkin.this, "Please wait .Your request has been sent to post for approval.");

                                } else if (object.getString("message").equalsIgnoreCase("Permission Request Send to parent user")) {
                                    CommonMethods.showtoast(Activity_Scanner_Checkin.this, "Please wait .Your request has been sent to previous user of table for approval.");

                                } else if (object.getString("message").equalsIgnoreCase("Out of restaurant area")) {
                                    CommonMethods.showtoast(Activity_Scanner_Checkin.this, "Sorry, You can't scan outside the area of restaurant.");
                                } else {
                                    CommonMethods.showtoast(Activity_Scanner_Checkin.this, object.getString("message"));
                                }
                                Activity_Scanner_Checkin.this.finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress_bar.setVisibility(View.GONE);
                        CommonMethods.showtoast(Activity_Scanner_Checkin.this, CommonMethods.vollyerror(error));

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", Preferences.getInstance().getUserId());
                params.put("qr_code", displayValue);
//                params.put("latitude", String.valueOf(tracker.getLatitude()));
//                params.put("longtitude", String.valueOf(tracker.getLongitude()));
                params.put("latitude", "52.0646679");
                params.put("longtitude", "4.2840514");
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(Activity_Scanner_Checkin.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(getApplicationContext(), "Camera permission denied!", Toast.LENGTH_LONG).show();
    }
}
