package com.roadstar.driverr.app.module.ui.profile;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.roadstar.driverr.R;
import com.roadstar.driverr.app.data.resp.UserProfileResp;
import com.roadstar.driverr.app.module.ui.main.MainActivity;
import com.roadstar.driverr.app.network.ApiInterface;
import com.roadstar.driverr.app.network.RetrofitClient;
import com.roadstar.driverr.common.base.BaseActivity;
import com.roadstar.driverr.common.utils.AppUtils;
import com.roadstar.driverr.common.utils.FileUtils;

import java.io.File;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class MyProfileActivity extends BaseActivity implements View.OnClickListener {


    private ProgressBar progressBar;
    public UserProfileResp profileRespLocal ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        init();
    }

    void init() {
        setActionBar(getString(R.string.my_profile));
        findTextViewById(R.id.tv_title).setTextColor(getResources().getColor(R.color.colorWhite));
        findImageView(R.id.iv_back).setColorFilter(ContextCompat.getColor(this, R.color.colorWhite), android.graphics.PorterDuff.Mode.MULTIPLY);
        progressBar = findViewById(R.id.progressBar);

        profileRespLocal = profileResp;
        findFieldById(R.id.etFirstName).setText(profileResp.getFirst_name());
        findFieldById(R.id.etLastName).setText(profileResp.getLast_name());
        findFieldById(R.id.etEmail).setText(profileResp.getEmail());
        findVizImageView(R.id.iv_profile).setImage(profileResp.getAvatar());

        bindClicklisteners();
    }

    private void bindClicklisteners() {

        findViewById(R.id.btn_save_edit).setOnClickListener(this);
        findViewById(R.id.iv_profile).setOnClickListener(this);
        findViewById(R.id.tv_setting).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_save_edit:

                callEditProfileAPI();
                break;

            case R.id.tv_setting:
                startActivity(this, SettingActivity.class);
                break;

            case R.id.iv_profile:
                checkStoragePermission(R.id.iv_profile);
                break;

        }
    }


    private void callEditProfileAPI() {
        progressBar.setVisibility(View.VISIBLE);

        ApiInterface mApiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);
        Call<ResponseBody> call = mApiInterface.updateProfile(getProfileParamsToUpdate());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);

                if (response.code() == 200) {
                    Toast.makeText(MyProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                    startActivityWithNoHistory(MyProfileActivity.this, MainActivity.class);
                }
                else {
                    Toast.makeText(MyProfileActivity.this, "error while update profile", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("onFailure", Objects.requireNonNull(t.getMessage()));

            }

        });
    }


    MultipartBody getProfileParamsToUpdate() {

        MultipartBody.Builder builder = new MultipartBody.Builder();

        try {

            builder.setType(MultipartBody.FORM);
            builder.addFormDataPart("first_name", AppUtils.getEditTextString(findViewById((R.id.etFirstName))));
            builder.addFormDataPart("last_name", AppUtils.getEditTextString(findViewById((R.id.etLastName))));
            builder.addFormDataPart("email", AppUtils.getEditTextString(findViewById((R.id.etEmail))));
            builder.addFormDataPart("mobile", profileRespLocal.getMobile());

            String pathToStoredImage1 = FileUtils.getPath(MyProfileActivity.this, Uri.parse(findVizImageView(R.id.iv_profile).getImageURL()));

            if (pathToStoredImage1 != null) {
                File file1 = new File(pathToStoredImage1);
                RequestBody requestBody1 = RequestBody.create(MediaType.parse("image/*"), file1);
                builder.addFormDataPart("avatar", file1.getName(), requestBody1);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return builder.build();

    }

}