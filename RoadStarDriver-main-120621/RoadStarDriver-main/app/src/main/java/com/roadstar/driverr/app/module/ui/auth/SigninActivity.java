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
import com.roadstar.driverr.app.data.resp.UserProfileResp;
import com.roadstar.driverr.app.module.ui.main.MainActivity;
import com.roadstar.driverr.app.network.ApiInterface;
import com.roadstar.driverr.app.network.RetrofitClient;
import com.roadstar.driverr.common.base.BaseActivity;
import com.roadstar.driverr.common.utils.ApiConstants;
import com.roadstar.driverr.common.utils.SharedHelper;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class SigninActivity extends BaseActivity implements View.OnClickListener {

    EditText etEmail;
    EditText etPassword;
    ProgressBar progressBar;

    private Context context = SigninActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        init();
    }

    void init() {
        setActionBar(getString(R.string.sign_in));
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        progressBar = findViewById(R.id.progressBar);
        bindClicklisteners();

    }

    private void bindClicklisteners() {
        findViewById(R.id.lay_reg_now).setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);
        findViewById(R.id.tv_forget_pass).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_next:
                signInRequest();
                break;
            case R.id.lay_reg_now:
                startActivity(this, PhoneNumbAuthActivity.class);
                break;
            case R.id.tv_forget_pass:
                startActivity(this, ForgetPasswordActivity.class);
                break;
        }
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
                    SharedHelper.putKey(SigninActivity.this, "loggedIn", "true");
                    ApiConstants.ACCESS_TOKEN = response.body().access_token;
                    SharedHelper.putKey(context, "access_token", response.body().access_token);
                    SharedHelper.putKey(context, "refresh_token", response.body().refresh_token);
                    SharedHelper.putKey(context, "UserProfile", response.body());

                    //UserManager.saveUserData(response.body());

                    if (response.body().status.equalsIgnoreCase("approved")) {

                        startActivityWithNoHistory(SigninActivity.this, MainActivity.class);

                    } else {
                        findViewById(R.id.account_detail_container).setVisibility(View.GONE);
                        findViewById(R.id.account_status).setVisibility(View.VISIBLE);
                    }
                }else if (response.code() == 401){
                    Toast.makeText(SigninActivity.this, "invalid username or password", Toast.LENGTH_SHORT).show();
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
