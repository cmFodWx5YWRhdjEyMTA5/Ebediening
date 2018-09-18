package com.ebediening;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ebediening.Adapter_Class.Adapter_Scan_Menu;
import com.ebediening.Getter_Setter.Restaurant_Menu_Categories;
import com.ebediening.Getter_Setter.menu_category;
import com.ebediening.Getter_Setter.restaurant_menu;
import com.ebediening.Utilites.AllAPIs;
import com.ebediening.Utilites.BaseActivity;
import com.ebediening.Utilites.CommonMethods;
import com.ebediening.Utilites.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Activity_Check_IN extends BaseActivity implements View.OnClickListener {
    ImageView image_info, img_back, img_share, img_logout, img_back_i;
    LinearLayout linear_categories;
    ArrayList<Restaurant_Menu_Categories> array_categories;
    ListView menu_list;
    LinkedHashMap<String, ArrayList<restaurant_menu>> array_menu_items;
    String selected_category_id = "", restaurnt_id = "2";
    Adapter_Scan_Menu adapter;
    ArrayList<menu_category> arry_linear_category;
    TextView cart_count;
    int cart_quantity = 0;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        array_categories = new ArrayList<>();
        array_menu_items = new LinkedHashMap<>();
        arry_linear_category = new ArrayList<>();
        cart_count = (TextView) findViewById(R.id.cart_count);
        linear_categories = (LinearLayout) findViewById(R.id.linear_categories);
        menu_list = (ListView) findViewById(R.id.menu_list);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        image_info = (ImageView) findViewById(R.id.image_info);
        image_info.setOnClickListener(this);
        img_share = (ImageView) findViewById(R.id.img_share);
        img_share.setOnClickListener(this);
        img_logout = (ImageView) findViewById(R.id.img_logout);
        img_logout.setOnClickListener(this);
        check_permission_location();

        set_up_categories();
        if (CommonMethods.checkConnection()) {
            get_menu_detail("0", "0", "start");
        } else {
            CommonMethods.showtoast_no_connetion(Activity_Check_IN.this);
        }
    }

    private void set_up_categories() {
        for (int a = 0; a < array_categories.size(); a++) {
//            arry_linear_category = new ArrayList<>();
            View child = getLayoutInflater().inflate(R.layout.single_row_menu_cateogries, null);
            final TextView cat_name = (TextView) child.findViewById(R.id.cat_name);
            final LinearLayout linear_border = (LinearLayout) child.findViewById(R.id.linear_border);
            linear_border.setTag(array_categories.get(a).getCate_id());
            linear_border.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < arry_linear_category.size(); i++) {
                        if (arry_linear_category.get(i).getBorder().equals(linear_border)) {
                            menu_category menu = arry_linear_category.get(i);
                            menu.setSelect_unselect("1");
                            menu.getBorder().setBackgroundResource(R.drawable.category_selected);
                            selected_category_id = menu.getBorder().getTag().toString();
                            ArrayList<restaurant_menu> array_restaurant_menu = new ArrayList<>();
                            array_restaurant_menu = array_menu_items.get(selected_category_id);
                            Adapter_Scan_Menu adapter = new Adapter_Scan_Menu(Activity_Check_IN.this, array_restaurant_menu, restaurnt_id, selected_category_id);
                            menu_list.setAdapter(adapter);
                        } else {
                            menu_category menu = arry_linear_category.get(i);
                            menu.setSelect_unselect("0");
                            menu.getBorder().setBackgroundResource(R.drawable.category_unselected);
                        }
                    }
                }
            });
            cat_name.setText(array_categories.get(a).getCate_name());
            menu_category menu = new menu_category();
            if (a == 0) {
                menu.setSelect_unselect("1");
                menu.setBorder(linear_border);
                arry_linear_category.add(menu);
                linear_border.setBackgroundResource(R.drawable.category_selected);
                cat_name.setTextColor(getResources().getColor(R.color.white));
            } else {
                menu.setSelect_unselect("0");
                menu.setBorder(linear_border);
                arry_linear_category.add(menu);
            }
            linear_categories.addView(child);
        }
    }

    public void get_menu_detail(final String pos, final String cate_id, final String start) {
        final ProgressDialog dialog = new ProgressDialog(Activity_Check_IN.this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AllAPIs.MENU_CATEGORY_BASIS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            dialog.dismiss();
                            array_menu_items = new LinkedHashMap<>();
                            array_categories = new ArrayList<>();
                            JSONObject object = new JSONObject(response);
                            if (object.getString("flag").equals("1")) {
                                JSONArray array = object.getJSONArray("data");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject obj = array.getJSONObject(i);
                                    //get all categories
                                    Restaurant_Menu_Categories cate = new Restaurant_Menu_Categories();
                                    cate.setCate_name(obj.getString("category_name"));
                                    cate.setCate_id(obj.getString("food_catgory_id"));
                                    array_categories.add(cate);

                                    //get menu items
                                    ArrayList<restaurant_menu> array_restaurant = new ArrayList<>();
                                    Iterator<String> keys = obj.keys();
                                    while (keys.hasNext()) {
                                        String key = keys.next();
                                        if (key.equalsIgnoreCase("category_name")) {
                                        } else if (key.equalsIgnoreCase("food_catgory_id")) {
                                        } else if (key.equalsIgnoreCase("items_counter")) {
                                        } else {
                                            JSONObject innerJObject = obj.getJSONObject(key);
                                            restaurant_menu menu = new restaurant_menu();
                                            menu.setId(innerJObject.getString("id"));
                                            menu.setName(innerJObject.getString("name"));
                                            menu.setFood_category_id(innerJObject.getString("food_category_id"));
                                            menu.setDine_out_price(innerJObject.getString("dineout_price"));
                                            menu.setDescription(innerJObject.getString("description"));
                                            menu.setDelivery_price(innerJObject.getString("delivery_price"));
                                            menu.setTakeaway_price(innerJObject.getString("takeaway_price"));
                                            menu.setAttributes_counter(innerJObject.getString("attributes_counter"));
                                            menu.setImage(object.getString("image_base_path") + innerJObject.getString("image"));
                                            if (!innerJObject.getString("item_label").equalsIgnoreCase(null)
                                                    && !innerJObject.getString("item_label").equalsIgnoreCase("null")
                                                    && !innerJObject.getString("item_label").equalsIgnoreCase("")) {
                                                menu.setItem_label(innerJObject.getString("item_label"));
                                            } else {
                                                menu.setItem_label("");
                                            }
                                            if (innerJObject.has("cart_quantity")) {
                                                cart_quantity = cart_quantity + Integer.parseInt(innerJObject.getString("cart_quantity"));
                                                menu.setCart_quantity(innerJObject.getString("cart_quantity"));
                                            } else {
                                                menu.setCart_quantity("0");
                                            }
                                            if (innerJObject.has("dinein_offer")) {
                                                menu.setDineout_offer(innerJObject.getString("dinein_offer"));
                                            } else {
                                                menu.setDineout_offer("");
                                            }
                                            array_restaurant.add(menu);
                                        }
                                    }
                                    if (array_restaurant.size() != 0) {
                                        array_menu_items.put(obj.getString("food_catgory_id"), array_restaurant);
                                    }
                                }

                                if (start.equalsIgnoreCase("start")) {
                                    set_up_categories();
                                    ArrayList<restaurant_menu> array_restaurant_menu = new ArrayList<>();
                                    array_restaurant_menu = array_menu_items.get(array_categories.get(0).getCate_id());
                                    Adapter_Scan_Menu adapter = new Adapter_Scan_Menu(Activity_Check_IN.this, array_restaurant_menu, restaurnt_id, array_categories.get(0).getCate_id());
                                    menu_list.setAdapter(adapter);
                                } else {
                                    ArrayList<restaurant_menu> array_restaurant_menu = new ArrayList<>();
                                    array_restaurant_menu = array_menu_items.get(cate_id);
                                    Adapter_Scan_Menu adapter = new Adapter_Scan_Menu(Activity_Check_IN.this, array_restaurant_menu, restaurnt_id, selected_category_id);
                                    menu_list.setAdapter(adapter);
                                }

                                if (!pos.equalsIgnoreCase("0")) {
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        public void run() {
                                            try {
                                                menu_list.setSmoothScrollbarEnabled(true);
                                                menu_list.setSelection(Integer.parseInt(pos));
                                            } catch (Exception e) {
                                            }
                                        }
                                    }, 100);

                                }

                                cart_count.setText("(" + String.valueOf(cart_quantity) + ")");

                            } else {
                                CommonMethods.showtoast(Activity_Check_IN.this, object.getString("message"));
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
                        CommonMethods.showtoast(Activity_Check_IN.this, CommonMethods.vollyerror(error));
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("restaurant_id", restaurnt_id);
                params.put("user_id", Preferences.getInstance().getUserId());
                return params;
            }
        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(Activity_Check_IN.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_info:
                show_dialog();
                break;

            case R.id.img_back:
                Activity_Check_IN.this.finish();
                break;

            case R.id.img_share:
                CommonMethods.share_with_other(Activity_Check_IN.this);
                break;

            case R.id.img_logout:
                finish();
                break;
        }
    }

    private void show_dialog() {
        final Dialog dialog_img = new Dialog(Activity_Check_IN.this);
        dialog_img.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_img.setContentView(R.layout.layout_popup_checkin);
        Window window = dialog_img.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog_img.findViewById(R.id.text_information)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_img.dismiss();
                        Intent i = new Intent(Activity_Check_IN.this, Activity_Information.class);
                        startActivity(i);
                    }
                });
        dialog_img.findViewById(R.id.text_review)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_img.dismiss();
                        Intent i = new Intent(Activity_Check_IN.this, Activity_Review.class);
                        startActivity(i);
                    }
                });

        // show dialog on screen
        dialog_img.show();
    }

    private void check_permission_location() {
        int permissionCheck = ContextCompat.checkSelfPermission(Activity_Check_IN.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck == 0) {
            //perform action
        } else {
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(Activity_Check_IN.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(Activity_Check_IN.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {

                } else {
                    ActivityCompat.requestPermissions(Activity_Check_IN.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            12);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }
        }
    }
}
