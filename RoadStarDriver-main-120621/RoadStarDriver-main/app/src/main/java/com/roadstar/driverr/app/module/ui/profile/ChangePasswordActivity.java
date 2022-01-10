package com.roadstar.driverr.app.module.ui.profile;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.roadstar.driverr.R;
import com.roadstar.driverr.app.data.models.ChangePasswrodModel;
import com.roadstar.driverr.app.module.ui.main.MainActivity;
import com.roadstar.driverr.app.network.ApiInterface;
import com.roadstar.driverr.app.network.RetrofitClient;
import com.roadstar.driverr.common.base.BaseActivity;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {


    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
    }

    void init() {
        setActionBar(getString(R.string.change_password));
        progressBar = findViewById(R.id.progressBar);

        bindClicklisteners();
    }

    private void bindClicklisteners() {

        findViewById(R.id.btn_save).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_save:
                if (TextUtils.isEmpty(findFieldById(R.id.et_old_pass).getText().toString())){
                    Toast.makeText(this, "add old password", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(findFieldById(R.id.et_new_pass).getText().toString())){
                    Toast.makeText(this, "add new password", Toast.LENGTH_SHORT).show();
                }
                else if (!findFieldById(R.id.et_cnfrm_new_pass).getText().toString().equals(findFieldById(R.id.et_new_pass).getText().toString())){
                    Toast.makeText(this, "confirm password mis-match", Toast.LENGTH_SHORT).show();
                }
                else
                    callChangePasswordAPI();

                //showSnackBar("Password Updated");
                break;

        }
    }

    private void callChangePasswordAPI() {
        progressBar.setVisibility(View.VISIBLE);

        ChangePasswrodModel data = new ChangePasswrodModel();
        data.setPassword(findFieldById(R.id.et_new_pass).getText().toString());
        data.setPassword_confirmation(findFieldById(R.id.et_cnfrm_new_pass).getText().toString());
        data.setPassword_old(findFieldById(R.id.et_old_pass).getText().toString());

        ApiInterface mApiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);
        Call<ResponseBody> call = mApiInterface.changePassword(data);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);

                if (response.code() == 200) {
                    Toast.makeText(ChangePasswordActivity.this, "password changed", Toast.LENGTH_SHORT).show();
                    startActivityWithNoHistory(ChangePasswordActivity.this, MainActivity.class);
                }
                else if (response.code() == 422){
                    Toast.makeText(ChangePasswordActivity.this, "new password should not be same as old password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("onFailure", Objects.requireNonNull(t.getMessage()));

            }

        });
    }
}