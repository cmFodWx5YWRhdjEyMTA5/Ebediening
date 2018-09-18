package com.ebediening.Adapter_Class;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ebediening.Activity_Check_IN;
import com.ebediening.Utilites.AllAPIs;
import com.ebediening.Utilites.CommonMethods;
import com.ebediening.Utilites.Common_Values;
import com.ebediening.Getter_Setter.Product_Attributes;
import com.ebediening.Getter_Setter.restaurant_menu;
import com.ebediening.R;
import com.ebediening.Utilites.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by HP on 14-12-2017.
 */

public class Adapter_Scan_Menu extends BaseAdapter {
    Activity_Check_IN context;
    ArrayList<Product_Attributes> array_attributes;
    LayoutInflater inflater;
    ArrayList<restaurant_menu> array_menu_items;
    String attribute_name = "";
    ArrayList<String> array_spinner_attributes;
    int count = 1;
    double final_price_quantity = 0.0, actual_price = 0.0;
    String restaurnt_id = "", POS = "", cate_id = "", spinner_attribute_id = "", spinner_attribute_value_id = "";
    ArrayList<String> spinner_selected_item_id;
    ArrayList<String> spinner_selected_item_values_id;
    Dialog dialog_img;

    public Adapter_Scan_Menu(Activity_Check_IN context, ArrayList<restaurant_menu> array_menu_items, String restaurnt_id, String cate_id) {
        this.context = context;
        this.array_menu_items = array_menu_items;
        array_attributes = new ArrayList<>();
        inflater = LayoutInflater.from(this.context);
        array_spinner_attributes = new ArrayList<>();
        this.restaurnt_id = restaurnt_id;
        this.cate_id = cate_id;
        spinner_selected_item_id = new ArrayList<>();
        spinner_selected_item_values_id = new ArrayList<>();
        dialog_img = new Dialog(context);
    }

    @Override
    public int getCount() {
        return array_menu_items.size();
    }

    @Override
    public Object getItem(int i) {
        return array_menu_items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        final MyViewHolder mViewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.single_row_menu_items, viewGroup, false);
            mViewHolder = new MyViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();
        }

        mViewHolder.name.setText(array_menu_items.get(i).getName());
        mViewHolder.name.setTag(array_menu_items.get(i).getAttributes_counter());
        mViewHolder.description.setText(array_menu_items.get(i).getDescription());
        mViewHolder.description.setTag(array_menu_items.get(i).getId());
        mViewHolder.price.setText(Common_Values.currency_sign + array_menu_items.get(i).getDine_out_price());
        if (array_menu_items.get(i).getCart_quantity().equalsIgnoreCase("0")) {
            mViewHolder.cart_quantity.setVisibility(View.GONE);
        } else {
            mViewHolder.cart_quantity.setVisibility(View.VISIBLE);
            mViewHolder.cart_quantity.setText(array_menu_items.get(i).getCart_quantity());
        }
        mViewHolder.price.setTag(i);
        if (!array_menu_items.get(i).getDineout_offer().equalsIgnoreCase("")) {
            //discount
            if (array_menu_items.get(i).getDineout_offer().contains("Percentage")) {
                //percentage
                DecimalFormat df = new DecimalFormat("#.00");
                double amount = Double.parseDouble(array_menu_items.get(i).getDineout_offer().split("-")[1]);
                double actual_price = Double.parseDouble(array_menu_items.get(i).getDine_out_price());
                double discount_price = (amount / 100.0f) * actual_price;
                double price = actual_price - discount_price;
                mViewHolder.discount.setVisibility(View.VISIBLE);
                mViewHolder.price.setPaintFlags(mViewHolder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                mViewHolder.discount.setText(array_menu_items.get(i).getDineout_offer().split("-")[1] + "%");
                mViewHolder.discounted_price.setText(Common_Values.currency_sign + String.valueOf(df.format(price)));
                mViewHolder.discounted_price.setVisibility(View.VISIBLE);
            } else if (array_menu_items.get(i).getDineout_offer().contains("Fixed")) {
                //fixed
                Float actual_price = Float.valueOf(array_menu_items.get(i).getDine_out_price());
                Float discount_fixed = Float.valueOf(array_menu_items.get(i).getDineout_offer().split("-")[1]);
                Float price = actual_price - discount_fixed;
                mViewHolder.discounted_price.setText(Common_Values.currency_sign + String.valueOf(price));
                mViewHolder.discounted_price.setVisibility(View.VISIBLE);
                mViewHolder.discount.setVisibility(View.VISIBLE);
                mViewHolder.price.setPaintFlags(mViewHolder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                mViewHolder.discount.setText(Common_Values.currency_sign + array_menu_items.get(i).getDineout_offer().split("-")[1]);
            }
        } else {
            //no discount
            mViewHolder.discount.setVisibility(View.GONE);
            mViewHolder.discounted_price.setVisibility(View.GONE);
            mViewHolder.discounted_price.setText("0.0");
            mViewHolder.price.setPaintFlags(mViewHolder.price.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        if (!array_menu_items.get(i).getItem_label().equalsIgnoreCase("")) {
            mViewHolder.item_label.setText(array_menu_items.get(i).getItem_label());
            mViewHolder.item_label.setVisibility(View.VISIBLE);
        } else {
            mViewHolder.item_label.setVisibility(View.GONE);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                POS = mViewHolder.price.getTag().toString();
                if (!mViewHolder.name.getTag().toString().equalsIgnoreCase("0")) {
                    //show attributes api
                    String final_price = "";
                    if (CommonMethods.checkConnection()) {
                        if (mViewHolder.discounted_price.getText().toString().equalsIgnoreCase("0.0")) {
                            final_price = mViewHolder.price.getText().toString();
                        } else {
                            final_price = mViewHolder.discounted_price.getText().toString();
                        }
                        get_attributes(mViewHolder.description.getTag().toString().trim(), mViewHolder.price.getTag().toString(), final_price);
                    } else {
                        CommonMethods.showtoast_no_connetion(context);
                    }
                } else {
                    //update quantity
                    String food_item_id = mViewHolder.description.getTag().toString().trim();
                    if (CommonMethods.checkConnection()) {
                        add_to_cart("0", food_item_id, "1", mViewHolder.price.getText().toString().trim(),
                                mViewHolder.name.getText().toString(), POS, cate_id);
                    } else {
                        CommonMethods.showtoast_no_connetion(context);
                    }
                }
            }
        });
        return convertView;
    }

    private void get_attributes(final String foodItemId, final String id, final String final_price) {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AllAPIs.ATTRIBUTE_DETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            dialog.dismiss();
                            JSONObject object = new JSONObject(response);
                            if (object.getString("flag").equals("1")) {
                                array_attributes = new ArrayList<>();
                                array_spinner_attributes = new ArrayList<>();
                                array_spinner_attributes.add("Select Value");
                                JSONArray array = object.getJSONArray("data");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object1 = array.getJSONObject(i);
                                    attribute_name = object1.getString("attribute_name");
                                    Iterator<String> keys = object1.keys();
                                    while (keys.hasNext()) {
                                        String key = keys.next();
                                        if (key.equalsIgnoreCase("attribute_name")) {
                                        } else if (key.equalsIgnoreCase("total_values_counter")) {
                                        } else if (key.equalsIgnoreCase("attribute_value_list")) {
                                            JSONObject innerJObject = object1.getJSONObject(key);
                                            Iterator<String> key_ia = innerJObject.keys();
                                            while (key_ia.hasNext()) {
                                                String key_i = key_ia.next();
                                                array_spinner_attributes.add(innerJObject.getString(key_i));
                                            }
                                        } else {
                                            JSONObject innerJObject = object1.getJSONObject(key);
                                            Product_Attributes attribute = new Product_Attributes();
                                            attribute.setAttribute_name(innerJObject.getString("attribute_name"));
                                            attribute.setAttribute_type(innerJObject.getString("attribute_type"));
                                            attribute.setAttribute_id(innerJObject.getString("attribute_id"));
                                            attribute.setAttribute_value_id(innerJObject.getString("attribute_value_id"));
                                            attribute.setPrice(innerJObject.getString("price"));
                                            attribute.setRestaurant_id(innerJObject.getString("restaurant_id"));
                                            attribute.setAttribute_value(innerJObject.getString("attribute_value"));
//                                        attribute.setCurrency_type(object.getString("currency_type"));
                                            attribute.setAll_attribute_type(object.getString("attribute_types"));
                                            array_attributes.add(attribute);
                                        }
                                    }
                                }
                                //show popup
                                show_attribute_dialog(id, final_price);

                            } else {
                                CommonMethods.showtoast(context, object.getString("message"));
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
                        CommonMethods.showtoast(context, CommonMethods.vollyerror(error));
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("food_item_id", foodItemId);
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void show_attribute_dialog(final String id, final String final_price) {
        dialog_img = new Dialog(context);
        dialog_img.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_img.setContentView(R.layout.layout_product_attributes);
        TextView product_attribute_name = (TextView) dialog_img.findViewById(R.id.product_attribute_name);
        product_attribute_name.setText(attribute_name);
        TextView product_description = (TextView) dialog_img.findViewById(R.id.product_description);
        product_description.setText(array_menu_items.get(Integer.parseInt(id)).getDescription());
        final TextView product_name = (TextView) dialog_img.findViewById(R.id.product_name);
        product_name.setText(array_menu_items.get(Integer.parseInt(id)).getName());
        final TextView product_price = (TextView) dialog_img.findViewById(R.id.product_price);
        product_price.setText(final_price);
        Spinner attribute_spinner = (Spinner) dialog_img.findViewById(R.id.attribute_spinner);

        GridView grid_view = (GridView) dialog_img.findViewById(R.id.grid_view);
        //true==radio button//false== checkbox
        String type_attr = "0";
        if (array_attributes.size() != 0) {
            String all_attribute = array_attributes.get(0).getAll_attribute_type();
            try {
                JSONObject obj_attribute = new JSONObject(all_attribute);
                if (obj_attribute.getString(array_attributes.get(0).getAttribute_type()).equalsIgnoreCase("Checkbox")) {
                    type_attr = "1";
                } else if (obj_attribute.getString(array_attributes.get(0).getAttribute_type()).equalsIgnoreCase("Radio Button")) {
                    type_attr = "2";
                } else {
                    //Dropdown
                    type_attr = "3";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (type_attr.equalsIgnoreCase("3")) {
                attribute_spinner.setVisibility(View.VISIBLE);
                grid_view.setVisibility(View.GONE);
                ArrayAdapter<String> Spinner_adapter = new ArrayAdapter<String>(context, R.layout.layout_single_textview, array_spinner_attributes);
                attribute_spinner.setAdapter(Spinner_adapter);
            } else {
                attribute_spinner.setVisibility(View.GONE);
                grid_view.setVisibility(View.VISIBLE);
                Adapter_attribute adapter = new Adapter_attribute(context, array_attributes, type_attr);
                grid_view.setAdapter(adapter);
            }

            attribute_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position != 0) {
                        spinner_attribute_id = array_attributes.get(position - 1).getAttribute_id();
                        spinner_attribute_value_id = array_attributes.get(position - 1).getAttribute_value_id();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
//            grid_view.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    spinner_selected_item_id.add(array_attributes.get(position).getAttribute_id());
//                    spinner_selected_item_values_id.add(array_attributes.get(position).getAttribute_value_id());
//                    Log.e("dsdsa", spinner_selected_item_id.toString());
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//                }
//            });

        }


        final TextView text_count = (TextView) dialog_img.findViewById(R.id.text_count);
        text_count.setText(String.valueOf(count));
        ImageView img_minus = (ImageView) dialog_img.findViewById(R.id.img_minus);
        ImageView img_plus = (ImageView) dialog_img.findViewById(R.id.img_plus);
        final TextView total_price = (TextView) dialog_img.findViewById(R.id.total_price);
        total_price.setText(final_price);
        if (final_price.contains(Common_Values.currency_sign)) {
            actual_price = Double.parseDouble(final_price.replace(Common_Values.currency_sign, ""));
        }
        final_price_quantity = actual_price;
        TextView text_add = (TextView) dialog_img.findViewById(R.id.text_add);
        text_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonMethods.checkConnection()) {
//                    dialog_img.dismiss();
                    add_to_cart("1", id, text_count.getText().toString(), product_price.getText().toString().trim(), product_name.getText().toString(), POS, cate_id);
                } else {
                    CommonMethods.showtoast_no_connetion(context);
                }
            }
        });

        img_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count != 1) {
                    count = count - 1;
                    text_count.setText(String.valueOf(count));
                    final_price_quantity = actual_price * count;
                    total_price.setText(Common_Values.currency_sign + String.format("%.2f", final_price_quantity));
                }
            }
        });

        img_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = count + 1;
                text_count.setText(String.valueOf(count));
                final_price_quantity = actual_price * count;
                total_price.setText(Common_Values.currency_sign + String.format("%.2f", final_price_quantity));
            }
        });
        // show dialog on screen
        dialog_img.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog_img.show();
    }

    private void add_to_cart(final String s, final String food_item_id, final String quantity, final String item_price,
                             final String item_name, final String pos, final String cate_id) {
        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, AllAPIs.ADD_TO_CART,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            dialog.dismiss();
                            JSONObject object = new JSONObject(response);
                            if (object.getString("flag").equals("1")) {
                                if (dialog_img.isShowing()) {
                                    dialog_img.dismiss();
                                }
                                CommonMethods.showtoast(context, object.getString("message"));
                                context.get_menu_detail(pos, cate_id, "reload");
                            } else {
                                CommonMethods.showtoast(context, object.getString("message"));
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
                        CommonMethods.showtoast(context, CommonMethods.vollyerror(error));
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", Preferences.getInstance().getUserId());
                params.put("restaurant_id", restaurnt_id);
                params.put("food_item_id", food_item_id);
                params.put("delivery_type", "dinein");
                params.put("table_id", "1");
                params.put("quantity", quantity);
                if (s.equalsIgnoreCase("1")) {
                    params.put("attribute_id", spinner_attribute_id);
                    params.put("attribute_value_id", spinner_attribute_value_id);
                    if (item_price.contains(Common_Values.currency_sign)) {
                        params.put("price", item_price.replace(Common_Values.currency_sign, ""));
                    } else {
                        params.put("price", item_price);
                    }
                }
                if (item_price.contains(Common_Values.currency_sign)) {
                    params.put("original_price", item_price.replace(Common_Values.currency_sign, ""));
                } else {
                    params.put("original_price", item_price);
                }
                params.put("food_item_name", item_name);
                params.put("cart_token_number", "1");
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private class MyViewHolder {
        TextView name, description, price, discounted_price, discount, item_label, quantity, cart_quantity;

        public MyViewHolder(View convertView) {
            name = (TextView) convertView.findViewById(R.id.name);
            description = (TextView) convertView.findViewById(R.id.description);
            price = (TextView) convertView.findViewById(R.id.price);
            discounted_price = (TextView) convertView.findViewById(R.id.discounted_price);
            discount = (TextView) convertView.findViewById(R.id.discount);
            item_label = (TextView) convertView.findViewById(R.id.item_label);
            cart_quantity = (TextView) convertView.findViewById(R.id.cart_quantity);
        }
    }


    public class Adapter_attribute extends BaseAdapter {
        Context context;
        LayoutInflater inflater;
        ArrayList<Product_Attributes> array_Attributes;
        String b;


        public Adapter_attribute(Context context, ArrayList<Product_Attributes> array_Attributes, String b) {
            this.context = context;
            this.array_Attributes = array_Attributes;
            this.b = b;
            inflater = LayoutInflater.from(this.context);
        }

        @Override
        public int getCount() {
            return array_Attributes.size();
        }

        @Override
        public Object getItem(int i) {
            return array_Attributes.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View convertView, ViewGroup viewGroup) {
            final MyViewHolder_grid mViewHolder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.layout_attribute_radiobutton, viewGroup, false);
                mViewHolder = new MyViewHolder_grid(convertView);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (MyViewHolder_grid) convertView.getTag();
            }

            if (b.equalsIgnoreCase("2")) {
                mViewHolder.checkBox.setVisibility(View.GONE);
                mViewHolder.radioButton.setVisibility(View.VISIBLE);
                mViewHolder.radioButton.setText(array_Attributes.get(i).getAttribute_value());
                mViewHolder.radioButton.setTag(i);
            } else {
                mViewHolder.radioButton.setVisibility(View.GONE);
                mViewHolder.checkBox.setVisibility(View.VISIBLE);
                mViewHolder.checkBox.setText(array_Attributes.get(i).getAttribute_value());
                mViewHolder.checkBox.setTag(i);
            }

            mViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        spinner_selected_item_id.add(array_attributes.get(Integer.parseInt(mViewHolder.checkBox.getTag().toString())).getAttribute_id());
                        spinner_selected_item_values_id.add(array_attributes.get(Integer.parseInt(mViewHolder.checkBox.getTag().toString())).getAttribute_value_id());

                    } else {
                        for (int j = 0; j < spinner_selected_item_id.size(); j++) {
                            if (spinner_selected_item_id.get(i).equalsIgnoreCase(array_attributes.get(Integer.parseInt(mViewHolder.checkBox.getTag().toString())).getAttribute_id())) {
                                if (spinner_selected_item_id.size() != 0) {
                                    spinner_selected_item_id.remove(i);
                                }
                            }
                        }
                        for (int j = 0; j < spinner_selected_item_values_id.size(); j++) {
                            if (spinner_selected_item_values_id.get(i).equalsIgnoreCase(array_attributes.get(Integer.parseInt(mViewHolder.checkBox.getTag().toString())).getAttribute_value_id())) {
                                if (spinner_selected_item_values_id.size() != 0) {
                                    spinner_selected_item_values_id.remove(i);
                                }
                            }
                        }
                    }
                }
            });

            mViewHolder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        spinner_selected_item_id.add(array_attributes.get(Integer.parseInt(mViewHolder.radioButton.getTag().toString())).getAttribute_id());
                        spinner_selected_item_values_id.add(array_attributes.get(Integer.parseInt(mViewHolder.radioButton.getTag().toString())).getAttribute_value_id());

                    } else {
                        for (int j = 0; j < spinner_selected_item_id.size(); j++) {
                            if (spinner_selected_item_id.get(i).equalsIgnoreCase(array_attributes.get(Integer.parseInt(mViewHolder.checkBox.getTag().toString())).getAttribute_id())) {
                                if (spinner_selected_item_id.size() != 0) {
                                    spinner_selected_item_id.remove(i);
                                }
                            }
                        }
                        for (int j = 0; j < spinner_selected_item_values_id.size(); j++) {
                            if (spinner_selected_item_values_id.get(i).equalsIgnoreCase(array_attributes.get(Integer.parseInt(mViewHolder.checkBox.getTag().toString())).getAttribute_value_id())) {
                                if (spinner_selected_item_values_id.size() != 0) {
                                    spinner_selected_item_values_id.remove(i);
                                }
                            }
                        }
                    }
                }
            });

            return convertView;
        }

        private class MyViewHolder_grid {
            RadioButton radioButton;
            CheckBox checkBox;

            public MyViewHolder_grid(View convertView) {
                radioButton = (RadioButton) convertView.findViewById(R.id.radio);
                checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            }
        }
    }
}
