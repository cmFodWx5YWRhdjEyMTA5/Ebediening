package com.ebediening;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ebediening.Adapter_Class.Adapter_offers;
import com.ebediening.Getter_Setter.Offers;
import com.ebediening.Utilites.AllAPIs;
import com.ebediening.Utilites.BaseActivity;
import com.ebediening.Utilites.CommonMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activity_Offers extends BaseActivity implements View.OnClickListener {
    GridView offer_grid;
    ImageView img_back;
    ArrayList<Offers> array_offers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        offer_grid = (GridView) findViewById(R.id.offer_grid);
        if (CommonMethods.checkConnection()) {
            get_all_offers();
        } else {
            CommonMethods.showtoast_no_connetion(Activity_Offers.this);
        }

    }

    private void get_all_offers() {
        final ProgressDialog dialog = new ProgressDialog(Activity_Offers.this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, AllAPIs.ALL_OFFERS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            dialog.dismiss();
                            JSONObject object = new JSONObject(response);
                            if (object.getString("flag").equals("1")) {
                                array_offers = new ArrayList<>();
                                JSONArray array = object.getJSONArray("data");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject obj = array.getJSONObject(i);
                                    Offers offers = new Offers();
                                    offers.setId(obj.getString("id"));
                                    offers.setRestaurent_name(obj.getString("restaurant_name"));
                                    offers.setImage_url(object.getString("image_base_path") + obj.getString("restaurant_image"));
                                    offers.setDiscount_type(obj.getString("discount_type"));
                                    offers.setDiscount_on(obj.getString("discount_on"));
                                    offers.setDiscount_for(obj.getString("discount_for"));
                                    offers.setTotal_offers(obj.getString("total_offers"));
                                    offers.setTitle(obj.getString("title"));
                                    array_offers.add(offers);
                                }
                                Adapter_offers adapter = new Adapter_offers(Activity_Offers.this, array_offers);
                                offer_grid.setAdapter(adapter);
//                                CommonMethods.showtoast(Activity_Offers.this, object.getString("message"));
                            } else {
                                CommonMethods.showtoast(Activity_Offers.this, object.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        CommonMethods.showtoast(Activity_Offers.this, CommonMethods.vollyerror(error));
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("user_id", "58");
                return params;
            }

        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(Activity_Offers.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                Activity_Offers.this.finish();
                break;
        }
    }
}
