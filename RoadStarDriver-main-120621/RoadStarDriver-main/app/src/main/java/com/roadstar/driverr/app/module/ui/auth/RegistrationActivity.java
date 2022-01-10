package com.roadstar.driverr.app.module.ui.auth;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.roadstar.driverr.R;
import com.roadstar.driverr.app.data.req.LoginReq;
import com.roadstar.driverr.app.data.req.RegistrationReq;
import com.roadstar.driverr.app.data.resp.UserProfileResp;
import com.roadstar.driverr.app.network.ApiInterface;
import com.roadstar.driverr.app.network.RetrofitClient;
import com.roadstar.driverr.common.base.BaseActivity;
import com.roadstar.driverr.common.utils.ApiConstants;
import com.roadstar.driverr.common.utils.SharedHelper;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class RegistrationActivity extends BaseActivity implements View.OnClickListener {

    ProgressBar progressBar;

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etHomeAddress;
    private EditText etPassword;
    private EditText etEmail;
    private Context context = RegistrationActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        findView();
        init();
    }

    void findView() {
        progressBar = findViewById(R.id.progressBar);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etHomeAddress = findViewById(R.id.etAddressHome);
        etPassword = findViewById(R.id.etPassword);

    }

    void init() {
        setActionBar(getString(R.string.register));
        bindClicklisteners();
        if (getIntent() != null && getIntent().hasExtra("phoneNumber")) {
            phoneNumber = getIntent().getStringExtra("phoneNumber");
        }

    }

    private void bindClicklisteners() {
        findViewById(R.id.lay_signin).setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_next:
                progressBar.setVisibility(View.VISIBLE);
                signUpRequest();
                break;
            case R.id.lay_signin:
                startActivityWithNoHistory(this, SigninActivity.class);
                break;

        }
    }

    public void signUpRequest() {

        progressBar.setVisibility(View.VISIBLE);

        RegistrationReq registrationReq = new RegistrationReq();
        registrationReq.setDevice_id(ApiConstants.DEVICE_ID);
        registrationReq.setDevice_token(ApiConstants.DEVICE_TOKEN);
        registrationReq.setDevice_type(ApiConstants.DEVICE_TYPE);
        registrationReq.setPassword(etPassword.getText().toString());
        registrationReq.setEmail(etEmail.getText().toString());
        registrationReq.setPassword_confirmation(etPassword.getText().toString());
        registrationReq.setFirst_name(etFirstName.getText().toString());
        registrationReq.setLast_name(etLastName.getText().toString());
        registrationReq.setHome_address(etHomeAddress.getText().toString());
        registrationReq.setMobile(phoneNumber);
        registrationReq.setLogin_by("manual");


        ApiInterface mApiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);

        Call<UserProfileResp> call = mApiInterface.registerRequest(registrationReq);
        call.enqueue(new Callback<UserProfileResp>() {
            @Override
            public void onResponse(@NonNull Call<UserProfileResp> call, @NonNull retrofit2.Response<UserProfileResp> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {

                    SharedHelper.putKey(context, "UserProfile", response.body());

                    signInRequest();

                }else if (response.code() == 422){
                    Toast.makeText(RegistrationActivity.this, "email has already been taken", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(RegistrationActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(@NonNull Call<UserProfileResp> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("onFailure", Objects.requireNonNull(t.getMessage()));

            }
        });


    }

    public void signInRequest() {

        progressBar.setVisibility(View.VISIBLE);

        LoginReq loginReq = new LoginReq();
        loginReq.setDevice_id(ApiConstants.DEVICE_ID);
        loginReq.setDevice_token(ApiConstants.DEVICE_TOKEN);
        loginReq.setDevice_type(ApiConstants.DEVICE_TYPE);
        loginReq.setPassword(etPassword.getText().toString());
        loginReq.setEmail(etEmail.getText().toString());


        ApiInterface mApiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);

        Call<UserProfileResp> call = mApiInterface.loginRequest(loginReq);
        call.enqueue(new Callback<UserProfileResp>() {
            @Override
            public void onResponse(@NonNull Call<UserProfileResp> call, @NonNull retrofit2.Response<UserProfileResp> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    ApiConstants.ACCESS_TOKEN = response.body().access_token;
                    SharedHelper.putKey(context, "refresh_token", response.body().refresh_token);
                    SharedHelper.putKey(context, "access_token", response.body().access_token);
                    SharedHelper.putKey(context, "UserProfile", response.body());
                    if (response.body().status.equalsIgnoreCase("onboarding")) {

                        startActivityWithNoHistory(RegistrationActivity.this, RegDetailsCompany.class);

                    } else {

                    }


                }

            }

            @Override
            public void onFailure(@NonNull Call<UserProfileResp> call, @NonNull Throwable t) {
                 progressBar.setVisibility(View.GONE);
                Log.d("onFailure", Objects.requireNonNull(t.getMessage()));

            }

        });


    }
}