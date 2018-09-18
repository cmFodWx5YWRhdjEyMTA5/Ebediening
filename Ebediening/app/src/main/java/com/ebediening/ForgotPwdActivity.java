package com.ebediening;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.EditText;
import android.widget.Toast;

import com.ebediening.Response.LoginResponse;
import com.ebediening.Utilites.BaseActivity;
import com.ebediening.Utilites.CommonMethods;
import com.ebediening.Utilites.Ebediening;
import com.ebediening.Utilites.EbedieningService;
import com.ebediening.Utilites.RestClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

public class ForgotPwdActivity extends BaseActivity{

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_screen);
        ButterKnife.bind(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void forget() {
        EbedieningService ebedieningService = RestClient.getInstance().getClient().create(EbedieningService.class);
        showProgressbar("Loading", "Please wait...");
        ebedieningService.forget(inputEmail.getText().toString()).
                enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                        if (response.body().getFlag()==1){
                            Intent intent = new Intent(ForgotPwdActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {

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



    @BindView(R.id.input_email)
    EditText inputEmail;

    @OnClick(R.id.button_login)
    void btLogin(){
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.submit_btn)
    void btSubmit(){
        if(inputEmail.getText().toString().equalsIgnoreCase("")){
            CommonMethods.showtoast(ForgotPwdActivity.this,"Please Enter Email ID");
        }else if(!CommonMethods.isValidEmail(inputEmail.getText().toString())){
            CommonMethods.showtoast(ForgotPwdActivity.this,"Please Enter Valid Email ID");
        }else{
            forget();
        }
    }

}
