package com.ebediening.Fragment;


import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ebediening.Activity_Crop_image;
import com.ebediening.Utilites.AllAPIs;
import com.ebediening.Utilites.CommonMethods;
import com.ebediening.Utilites.CommonUtilFunctions;
import com.ebediening.Utilites.CustomMultiPartRequest;
import com.ebediening.R;
import com.ebediening.Utilites.Preferences;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.ebediening.Utilites.CommonMethods.getFileDataFromDrawable;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    EditText edit_country, edit_city, edit_postalcode, edit_address, edit_company, edit_phone, edit_last_name, edit_first_name;
    CircleImageView user_image;
    TextView user_name, user_email;
    LinearLayout linear_submit;
    ImageView edit_image;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int REQUEST_IMAGE_CROP = 3;
    Uri mCapturedImageURI;
    String fileName = "";
    String image_camera = "", image_gallery = "";

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        edit_first_name = (EditText) v.findViewById(R.id.edit_first_name);
        edit_last_name = (EditText) v.findViewById(R.id.edit_last_name);
        edit_phone = (EditText) v.findViewById(R.id.edit_phone);
        edit_company = (EditText) v.findViewById(R.id.edit_company);
        edit_address = (EditText) v.findViewById(R.id.edit_address);
        edit_postalcode = (EditText) v.findViewById(R.id.edit_postalcode);
        edit_city = (EditText) v.findViewById(R.id.edit_city);
        edit_country = (EditText) v.findViewById(R.id.edit_country);
        user_image = (CircleImageView) v.findViewById(R.id.user_image);
        edit_image = (ImageView) v.findViewById(R.id.edit_image);
        edit_image.setOnClickListener(this);
        user_name = (TextView) v.findViewById(R.id.user_name);
        user_email = (TextView) v.findViewById(R.id.user_email);
        linear_submit = (LinearLayout) v.findViewById(R.id.linear_submit);
        linear_submit.setOnClickListener(this);
        if (CommonMethods.checkConnection()) {
            get_user_profile();
        } else {
            CommonMethods.showtoast_no_connetion(getActivity());
        }

        check_permission_CAMERA();
        check_permission_STORAGE();
        return v;
    }

    private void check_permission_CAMERA() {
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA);
        if (permissionCheck == 0) {
            //perform action
        } else {
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.CAMERA)) {

                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CAMERA},
                            12);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }
        }
    }

    private void check_permission_STORAGE() {
        int permissionCheck_1 = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission_group.STORAGE);
        if (permissionCheck_1 == 0) {
            //perform action
        } else {
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission_group.STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                            12);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }
        }
    }

    private void get_user_profile() {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AllAPIs.GET_PROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            dialog.dismiss();
                            JSONObject object = new JSONObject(response);
                            if (object.getString("flag").equals("1")) {
                                JSONObject obj = object.getJSONObject("customer_profile");

                                if (!obj.getString("first_name").equalsIgnoreCase(null) && !obj.getString("first_name").equalsIgnoreCase("null")) {
                                    user_name.setText(obj.getString("first_name"));
                                }

                                if (!obj.getString("email").equalsIgnoreCase(null) && !obj.getString("email").equalsIgnoreCase("null")) {
                                    user_email.setText(obj.getString("email"));
                                }


                                if (!obj.getString("first_name").equalsIgnoreCase(null) && !obj.getString("first_name").equalsIgnoreCase("null")) {
                                    edit_first_name.setText(obj.getString("first_name"));
                                }
                                if (!obj.getString("last_name").equalsIgnoreCase(null) && !obj.getString("last_name").equalsIgnoreCase("null")) {
                                    edit_last_name.setText(obj.getString("last_name"));
                                }
                                if (!obj.getString("phone").equalsIgnoreCase(null) && !obj.getString("phone").equalsIgnoreCase("null")) {
                                    edit_phone.setText(obj.getString("phone"));
                                }
                                if (!obj.getString("postcode").equalsIgnoreCase(null) && !obj.getString("postcode").equalsIgnoreCase("null")) {
                                    edit_company.setText(obj.getString("company_name"));
                                }
                                if (!obj.getString("address").equalsIgnoreCase(null) && !obj.getString("company_name").equalsIgnoreCase("null")) {
                                    edit_address.setText(obj.getString("address"));
                                }
                                if (!obj.getString("postcode").equalsIgnoreCase(null) && !obj.getString("postcode").equalsIgnoreCase("null")) {
                                    edit_postalcode.setText(obj.getString("postcode"));
                                }
                                if (!obj.getString("city").equalsIgnoreCase(null) && !obj.getString("city").equalsIgnoreCase("null")) {
                                    edit_city.setText(obj.getString("city"));
                                }
                                if (!obj.getString("country").equalsIgnoreCase(null) && !obj.getString("country").equalsIgnoreCase("null")) {
                                    edit_country.setText(obj.getString("country"));
                                }

                                Picasso.with(getActivity()).load(obj.getString("image")).placeholder(R.drawable.profileicon).into(user_image);
                            } else {
                                CommonMethods.showtoast(getActivity(), object.getString("message"));
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
                        CommonMethods.showtoast(getActivity(), CommonMethods.vollyerror(error));
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", Preferences.getInstance().getUserId());
                return params;
            }

        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_submit:
                if (CommonMethods.checkConnection()) {
                    CommonMethods.hideSoftKeyboard(getActivity());
                    if (image_camera.equalsIgnoreCase("")) {
                        submit_profile();
                    } else {
                        submit_profile_with_image();
                    }
                } else {
                    CommonMethods.showtoast_no_connetion(getActivity());
                }
                break;

            case R.id.edit_image:
                show_dialog();
                break;
        }
    }

    private void submit_profile() {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
        //our custom volley request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AllAPIs.UPDATE_PROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            dialog.dismiss();
                            JSONObject obj = new JSONObject(response);
                            if (obj.getString("success").equals("1")) {
                                JSONArray array = obj.getJSONArray("data");

                                //save in session as well update profile
                            } else {
                                JSONArray array = obj.getJSONArray("errors");
                                if (array.length() > 0) {
                                    CommonMethods.showtoast(getActivity(), array.getString(0));
                                }
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
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
            * If you want to add more parameters with the image
            * you can do it here
            * here we have only one parameter with the image
            * which is tags
            * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("first_name", edit_first_name.getText().toString().trim());
                params.put("user_id", Preferences.getInstance().getUserId());
                params.put("last_name", edit_last_name.getText().toString().trim());
                params.put("phone", edit_phone.getText().toString().trim());
                params.put("address", edit_address.getText().toString().trim());
                params.put("city", edit_city.getText().toString().trim());
                params.put("postcode", edit_postalcode.getText().toString().trim());
                params.put("country", edit_country.getText().toString().trim());
                params.put("company_name", edit_company.getText().toString().trim());
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void submit_profile_with_image() {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
        //our custom volley request
        CustomMultiPartRequest volleyMultipartRequest = new CustomMultiPartRequest(Request.Method.POST, AllAPIs.UPDATE_PROFILE,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            dialog.dismiss();
                            JSONObject obj = new JSONObject(new String(response.data));
                            if (obj.getString("flag").equals("1")) {
                                CommonMethods.showtoast(getActivity(), obj.getString("message"));
                            } else {
                                JSONArray array = obj.getJSONArray("errors");
                                if (array.length() > 0) {
                                    CommonMethods.showtoast(getActivity(), array.getString(0));
                                }
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
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
            * If you want to add more parameters with the image
            * you can do it here
            * here we have only one parameter with the image
            * which is tags
            * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("first_name", edit_first_name.getText().toString().trim());
                params.put("user_id", Preferences.getInstance().getUserId());
                params.put("last_name", edit_last_name.getText().toString().trim());
                params.put("phone", edit_phone.getText().toString().trim());
                params.put("address", edit_address.getText().toString().trim());
                params.put("city", edit_city.getText().toString().trim());
                params.put("postcode", edit_postalcode.getText().toString().trim());
                params.put("country", edit_country.getText().toString().trim());
                params.put("company_name", edit_company.getText().toString().trim());
                return params;
            }

            /*
            * Here we are passing image by renaming it with a unique name
            * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                File image = new File(image_camera);
                Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
//                bitmap = Bitmap.createScaledBitmap(bitmap,parent.getWidth(),parent.getHeight(),true);
                params.put("image", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 500,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }

    private void show_dialog() {
        final Dialog dialog_img = new Dialog(getActivity());
        dialog_img.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_img.setContentView(R.layout.dialog_for_menuimage);
        dialog_img.findViewById(R.id.btnChoosePath)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_img.dismiss();
                        activeGallery();

                    }
                });
        dialog_img.findViewById(R.id.btnTakePhoto)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_img.dismiss();
                        activeTakePhoto();
                    }
                });

        // show dialog on screen
        dialog_img.show();
    }

    private void activeTakePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            fileName = System.currentTimeMillis() + ".png";
            File dir = new File(Environment.getExternalStorageDirectory() + "/GetSetVisa");
            File output = new File(dir, fileName);
            if (Build.VERSION.SDK_INT >= 24) {
                mCapturedImageURI = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", output);
            } else {
                mCapturedImageURI = Uri.fromFile(output);
            }
            takePictureIntent
                    .putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void activeGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            //camera
            case REQUEST_IMAGE_CAPTURE:
                if (resultCode != 0) {
                    image_camera = CommonUtilFunctions.onCaptureImageResult_imageview(getActivity(), data, true, mCapturedImageURI,
                            true, fileName, user_image);
                    Log.i("capture image", image_camera);
                }
                break;

            //gallery
            case RESULT_LOAD_IMAGE:
                if (data != null && !data.equals("")) {
                    Uri selectImage = data.getData();
                    Intent i = new Intent(getActivity(), Activity_Crop_image.class);
                    i.putExtra("uri", selectImage.toString());
                    startActivityForResult(i, REQUEST_IMAGE_CROP);
//                    image_camera = CommonUtilFunctions.onSelectFromGalleryResult_imageview(getActivity(), data,
//                            false, user_image);
//                    Log.i("image", image_camera);
                }
                break;

            case REQUEST_IMAGE_CROP:
                Bitmap bitmap_i = null;
                if (data.hasExtra("bitmap")) {
                    String bitmap = data.getStringExtra("bitmap");
                    try {
                        byte[] encodeByte = Base64.decode(bitmap, Base64.DEFAULT);
                         bitmap_i = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
                image_camera = CommonUtilFunctions.onSelectFromGalleryResult_imageview_bitmap(getActivity(), bitmap_i,
                        false, user_image);
                Log.i("image", image_camera);
                break;
        }
    }

}

