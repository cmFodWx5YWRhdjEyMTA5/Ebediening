package com.ebediening;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ebediening.Response.UserResponse;
import com.ebediening.Utilites.AllAPIs;
import com.ebediening.Utilites.BaseActivity;
import com.ebediening.Utilites.CommonMethods;
import com.ebediening.Utilites.Ebediening;
import com.ebediening.Utilites.EbedieningService;
import com.ebediening.Utilites.Preferences;
import com.ebediening.Utilites.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

public class SignupActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        ButterKnife.bind(this);
        setStatusBar();
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }


    private void submit_signup_request() {
        final ProgressDialog dialog = new ProgressDialog(SignupActivity.this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AllAPIs.REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            dialog.dismiss();
                            JSONObject object = new JSONObject(response);
                            if (object.getString("flag").equals("1")) {
//                                JSONObject obj = object.getJSONObject("user_data");
//                                sessionManager.createLoginSession(obj.getString("id"),
//                                        obj.getString("first_name"),
//                                        "", "", obj.getString("email"));
//                                finishAffinity();
//                                Intent i = new Intent(SignupActivity.this, MainActivity.class);
//                                startActivity(i);
                                CommonMethods.showtoast(SignupActivity.this, object.getString("message"));
                                SignupActivity.this.finish();
                            } else {
                                if (object.has("errors")) {
                                    JSONArray array = object.getJSONArray("errors");
                                    if (array.length() > 0) {
                                        CommonMethods.showtoast(SignupActivity.this, array.getString(0));
                                    }
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
                        CommonMethods.showtoast(SignupActivity.this, CommonMethods.vollyerror(error));
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", inputEmail.getText().toString().trim());
                params.put("password", inputPassword.getText().toString().trim());
                params.put("fullname", inputFullname.getText().toString().trim());
                params.put("device_id", "sdfsdfsdf");
                return params;
            }

        };


        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(SignupActivity.this);
        requestQueue.add(stringRequest);

    }


    @OnClick(R.id.button_login)
    void btLogin(){
        SignupActivity.this.finish();
    }

    @OnClick(R.id.linear_signup)
    void linearSignup(){
        if (inputFullname.getText().toString().equalsIgnoreCase("")) {
            CommonMethods.showtoast(SignupActivity.this, "Please Enter Full Name");
        } else if (inputEmail.getText().toString().trim().equalsIgnoreCase("")) {
            CommonMethods.showtoast(SignupActivity.this, "Please Enter Email ID");
        } else if (!CommonMethods.isValidEmail(inputEmail.getText().toString())) {
            CommonMethods.showtoast(SignupActivity.this, "Please Enter Valid Email");
        } else if (inputPassword.getText().toString().trim().equalsIgnoreCase("")) {
            CommonMethods.showtoast(SignupActivity.this, "Please Enter Password");
        } else {
            if (CommonMethods.checkConnection()) {
                signUp();
            } else {
                CommonMethods.showtoast_no_connetion(SignupActivity.this);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void signUp() {
        EbedieningService ebedieningService = RestClient.getInstance().getClient().create(EbedieningService.class);
        showProgressbar("Loading", "Please wait...");
        ebedieningService.register(inputEmail.getText().toString(),inputPassword.getText().toString(),inputFullname.getText().toString(),"android").
                enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, retrofit2.Response<UserResponse> response) {
                        if (response.body().getFlag()==1){
                         setPreferences(response.body().getUserInfo());
                         Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                         startActivity(intent);
                         finish();
                        }
                        else
                        {

                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), Ebediening.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setPreferences(UserResponse.UserInfo userInfo) {
        Preferences.getInstance().setUserId(userInfo.getId());
        Preferences.getInstance().setEmail(userInfo.getEmail());
        Preferences.getInstance().setFirstName(userInfo.getFirstName());
    }

    @BindView(R.id.input_email)
    EditText inputEmail;

    @BindView(R.id.input_password)
    EditText inputPassword;

    @BindView(R.id.input_fullname)
    EditText inputFullname;
}
