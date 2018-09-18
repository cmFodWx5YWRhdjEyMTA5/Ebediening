package com.ebediening;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.ebediening.Response.LoginResponse;
import com.ebediening.Response.UserData;
import com.ebediening.Response.UserResponse;
import com.ebediening.Utilites.BaseActivity;
import com.ebediening.Utilites.CommonMethods;
import com.ebediening.Utilites.Ebediening;
import com.ebediening.Utilites.EbedieningService;
import com.ebediening.Utilites.Preferences;
import com.ebediening.Utilites.RestClient;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends BaseActivity {

    GoogleSignInClient mGoogleSignInClient;
    int RC_SIGN_IN = 12;
    CallbackManager callbackManager;
    private static final String EMAIL = "email";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        ButterKnife.bind(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        //google login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("555277150704-2ph8979cfpqb63h0pl5vaagm42t76qce.apps.googleusercontent.com").requestProfile().requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        //fb login
        callbackManager = CallbackManager.Factory.create();
        setStatusBar();
    }


    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            if (CommonMethods.checkConnection()) {
                gplusLogin(account.getId(), account.getEmail(), account.getGivenName(), account.getFamilyName(), account.getPhotoUrl());
            } else {
                CommonMethods.showtoast_no_connetion(LoginActivity.this);
            }

        } catch (ApiException e) {
            ;
        }
    }

    private void loginFb() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("login", loginResult.getAccessToken().getUserId());
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());

                                try {
                                    String fbId = object.getString("id");
                                    String fbEmail = object.getString("email");
                                    String fbFirstName = object.getString("first_name");
                                    String fbLastName = object.getString("last_name");
                                    //String fbGender = object.getString("gender");
                                    String fbGender = " ";
                                    String fbImage = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                    Log.e("login", fbImage);
                                    facebookLogin(fbId,fbEmail,fbFirstName,fbLastName,fbGender,fbImage);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, name, email, gender, first_name, last_name, picture.type(large)");
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void gplusLogin(final String accountId, final String accountEmail, final String givenName, final String familyName, final Uri photoUrl) {
        EbedieningService ebedieningService = RestClient.getInstance().getClient().create(EbedieningService.class);
        showProgressbar("Loading", "Please wait...");
        ebedieningService.registerGplus(accountId,accountEmail,givenName,familyName, String.valueOf(photoUrl),"android").
                enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, retrofit2.Response<UserResponse> response) {
                        if (response.body().getFlag() == 1) {
                            setGPreference(response.body().getUserInfo(),accountEmail,givenName,familyName,String.valueOf(photoUrl));
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {

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


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void facebookLogin(String fbId, String fbEmail, final String fbFirstName, final String fbLastName, final String fbGender, final String fbImage) {
        EbedieningService ebedieningService = RestClient.getInstance().getClient().create(EbedieningService.class);
        showProgressbar("Loading", "Please wait...");
        ebedieningService.registerFb(fbId,fbEmail,fbFirstName,fbLastName,fbGender,fbImage,"android").
                enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, retrofit2.Response<UserResponse> response) {
                        if (response.body().getFlag() == 1) {
                            setFPreference(response.body().getUserInfo(),fbFirstName,fbLastName,fbGender,fbImage);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {

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






    @BindView(R.id.input_name)
    EditText inputName;

    @BindView(R.id.input_password)
    EditText inputPassword;

    @BindView(R.id.checkBoxLogin)
    CheckBox cbLogin;

    @OnClick(R.id.button_signup)
    void btSignup() {
        Intent i = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(i);
    }


    @OnClick(R.id.linear_google)
    void btGoogle() {
        signIn();
    }

    @OnClick(R.id.linear_fb)
    void btFb() {
        loginFb();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.linear_login)
    void btLogin() {
        if (inputName.getText().toString().trim().equalsIgnoreCase("")) {
            CommonMethods.showtoast(LoginActivity.this, "Please Enter Email ID");
        } else if (inputPassword.getText().toString().trim().equalsIgnoreCase("")) {
            CommonMethods.showtoast(LoginActivity.this, "Please Enter Password");
        } else {
            login();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void login() {
        EbedieningService ebedieningService = RestClient.getInstance().getClient().create(EbedieningService.class);
        showProgressbar("Loading", "Please wait...");
        ebedieningService.login(inputName.getText().toString(), inputPassword.getText().toString(), "android").
                enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                        if (response.body().getFlag() == 1) {
                            setPreferences(response.body().getUserData());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {

                        }
                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        hideProgressBar();
                        Toast.makeText(getApplicationContext(), Ebediening.getInstance().SERVER_ERROR, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setPreferences(UserData userData) {
        Preferences.getInstance().setLogIn(true);
        Preferences.getInstance().setUserId(userData.getId() != null ? userData.getId() : "");
        Preferences.getInstance().setEmail(userData.getEmail() != null ? userData.getEmail() : "");
        Preferences.getInstance().setMobile(userData.getPhone() != null ? userData.getPhone() : "");
        Preferences.getInstance().setFirstName(userData.getFirstName() != null ? userData.getFirstName() : "");
        Preferences.getInstance().setLastName(userData.getLastName() != null ? userData.getLastName() : "");
        Preferences.getInstance().setAddress(userData.getAddress() != null ? userData.getAddress() : "");
        Preferences.getInstance().setEmailVerified(userData.getIsEmailVerified() != null ? userData.getIsEmailVerified() : "");
        Preferences.getInstance().setUserPhotoPath(userData.getImage() != null ? userData.getImage() : "");
        Preferences.getInstance().setAuthKey(userData.getAuthorization() != null ? userData.getAuthorization() : "");
    }


    private void setGPreference(UserResponse.UserInfo userInfo,String email,String givenName,String familyName,String image) {
        Preferences.getInstance().setLogIn(true);
        Preferences.getInstance().setUserId(userInfo.getId() != null ? userInfo.getId() : "");
        Preferences.getInstance().setEmail(email != null ? email : "");
        Preferences.getInstance().setFirstName(givenName != null ? givenName : "");
        Preferences.getInstance().setLastName(familyName != null ? familyName : "");
        Preferences.getInstance().setUserPhotoPath(image != null ? image : "");
    }

    private void setFPreference(UserResponse.UserInfo userInfo,String firstName,String lastName,String gender,String image) {
        Preferences.getInstance().setLogIn(true);
        Preferences.getInstance().setUserId(userInfo.getId() != null ? userInfo.getId() : "");
        Preferences.getInstance().setEmail(userInfo.getEmail() != null ? userInfo.getEmail() : "");
        Preferences.getInstance().setGender(gender != null ? gender : "");
        Preferences.getInstance().setFirstName(firstName != null ? firstName : "");
        Preferences.getInstance().setLastName(lastName != null ? lastName : "");
        Preferences.getInstance().setUserPhotoPath(image != null ? image : "");
    }
    @OnClick(R.id.forgot_password)
    void forget() {
        Intent intent = new Intent(LoginActivity.this, ForgotPwdActivity.class);
        startActivity(intent);
    }


}






