package com.roadstar.driverr.app.module.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.roadstar.driverr.R;
import com.roadstar.driverr.app.data.req.RefreshTokenReq;
import com.roadstar.driverr.app.data.resp.RefreshTokenResp;
import com.roadstar.driverr.app.data.resp.UserProfileResp;
import com.roadstar.driverr.app.module.ui.auth.WelcomeActivity;
import com.roadstar.driverr.app.module.ui.main.MainActivity;
import com.roadstar.driverr.app.network.ApiInterface;
import com.roadstar.driverr.app.network.RetrofitClient;
import com.roadstar.driverr.common.base.BaseActivity;
import com.roadstar.driverr.common.utils.ApiConstants;
import com.roadstar.driverr.common.utils.ConnectionHelper;
import com.roadstar.driverr.common.utils.NetworkUtil;
import com.roadstar.driverr.common.utils.SharedHelper;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;


public class SplashActivity extends BaseActivity {


    String TAG = "SplashActivity";
    public Activity activity = SplashActivity.this;
    public Context context = SplashActivity.this;
    ConnectionHelper helper;
    Boolean isInternet;
    Handler handleCheckStatus;
    int retryCount = 0;
    AlertDialog alert;
    String device_token, device_UDID;
    TextView lblVersion;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash);

        helper = new ConnectionHelper(context);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        isInternet = NetworkUtil.isNetworkConnected(context);
        handleCheckStatus = new Handler();
        //progressBar = findViewById(R.id.progressBar);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        progressBar = new ProgressBar(this);

        getFirebaseToken();
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        handleCheckStatus.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (helper.isConnectingToInternet()) {
                    if (SharedHelper.getKey(context, "loggedIn").equalsIgnoreCase(getString(R.string.True))) {
                        GetToken();
                        getProfile();
                        setFcm(SharedHelper.getKey(getApplicationContext(), "access_token"));
                    } else {
                        GoToBeginActivity();
                    }
                    if (alert != null && alert.isShowing()) {
                        alert.dismiss();
                    }
                } else {
                    showDialog();
                    handleCheckStatus.postDelayed(this, 3000);
                }
            }
        }, 5000);

    }


    public void getFirebaseToken() {
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "getInstanceId failed", task.getException());
                    return;
                }
                String token = task.getResult().getToken();
                SharedHelper.putKey(getApplicationContext(), "device_token", "" + token);

            }
        });
    }


    public void GetToken() {
        try {
            if (!SharedHelper.getKey(context, "device_token").equals("") && SharedHelper.getKey(context, "device_token") != null) {
                device_token = SharedHelper.getKey(context, "device_token");
                Log.i(TAG, "GCM Registration Token: " + device_token);
            } else {
                device_token = "" + FirebaseInstanceId.getInstance().getToken();
                SharedHelper.putKey(context, "device_token", "" + FirebaseInstanceId.getInstance().getToken());
                Log.i(TAG, "Failed to complete token refresh: " + device_token);
            }
        } catch (Exception e) {
            device_token = "COULD NOT GET FCM TOKEN";
            Log.d(TAG, "Failed to complete token refresh", e);
        }

        try {
            device_UDID = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            ApiConstants.DEVICE_ID = device_UDID;
            Log.i(TAG, "Device UDID:" + device_UDID);
        } catch (Exception e) {
            device_UDID = "COULD NOT GET UDID";
            e.printStackTrace();
            Log.d(TAG, "Failed to complete device UDID");
        }
    }


    public void getProfile() {

        progressBar.setVisibility(View.VISIBLE);
        ApiConstants.ACCESS_TOKEN = SharedHelper.getKey(context, "access_token");

        ApiInterface mApiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);
        ApiConstants.DEVICE_TOKEN = device_token;
        Call<UserProfileResp> call = mApiInterface.getUserProfile("android", device_UDID, device_token);
        call.enqueue(new Callback<UserProfileResp>() {
            @Override
            public void onResponse(@NonNull Call<UserProfileResp> call, @NonNull retrofit2.Response<UserProfileResp> response) {

                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("UserProfile", response.body().isDocuments_uploaded() + "");

                    SharedHelper.putKey(context, "UserProfile", response.body());
                    if (response.body().status.equalsIgnoreCase("approved")) {
                        if(response.body().getService().status.equalsIgnoreCase("active"))
                            SharedHelper.putBoolean(SplashActivity.this,"online",true);
                        else
                            SharedHelper.putBoolean(SplashActivity.this,"online",false);
                        GoToMainActivity();
                    }else {
                        GoToMainActivity();
                        Toast.makeText(SplashActivity.this, "your account is been varified", Toast.LENGTH_SHORT).show();
                    }

                } else if (response.code() == 401) {
                    progressBar.setVisibility(View.VISIBLE);
                    refreshToken();
                }


            }

            @Override
            public void onFailure(@NonNull Call<UserProfileResp> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("onFailure", Objects.requireNonNull(t.getMessage()));

            }

        });


    }


    public void GoToMainActivity() {
        Intent mainIntent = new Intent(activity, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
        activity.finish();
    }

    public void GoToBeginActivity() {
        SharedHelper.putKey(activity, "loggedIn", getString(R.string.False));
        Intent mainIntent = new Intent(activity, WelcomeActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainIntent);
        activity.finish();
    }

    public void displayMessage(String toastString) {
        Toast.makeText(activity, toastString, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.connect_to_network))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.connect_to_wifi), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        alert.dismiss();
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton(getString(R.string.quit), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        alert.dismiss();
                        finish();
                    }
                });
        if (alert == null) {
            alert = builder.create();
            alert.show();
        }
    }

    public void refreshToken() {
        ApiConstants.ACCESS_TOKEN = SharedHelper.getKey(context, "refresh_token");


        RefreshTokenReq refreshTokenReq = new RefreshTokenReq();
        refreshTokenReq.setClient_id(ApiConstants.CLIENT_ID);
        refreshTokenReq.setClient_secret(ApiConstants.CLIENT_SECRET);
        refreshTokenReq.setGrant_type(ApiConstants.GRANT_TYPE);
        refreshTokenReq.setDevice_id(ApiConstants.DEVICE_ID);
        refreshTokenReq.setDevice_type(ApiConstants.DEVICE_TYPE);
        refreshTokenReq.setDevice_token(ApiConstants.DEVICE_TOKEN);
        refreshTokenReq.setRefresh_token("Bearer " + SharedHelper.getKey(context, "refresh_token"));
        refreshTokenReq.setScope("");


        ApiInterface mApiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);
        ApiConstants.DEVICE_TOKEN = device_token;
        Call<RefreshTokenResp> call = mApiInterface.refreshToken(refreshTokenReq);
        call.enqueue(new Callback<RefreshTokenResp>() {
            @Override
            public void onResponse(@NonNull Call<RefreshTokenResp> call, @NonNull retrofit2.Response<RefreshTokenResp> response) {

                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {

                    ApiConstants.ACCESS_TOKEN = response.body().getAccess_token();
                    SharedHelper.putKey(context, "access_token", response.body().getAccess_token());
                    SharedHelper.putKey(context, "refresh_token", response.body().getRefresh_token());
                    getProfile();

                } else {
                    GoToBeginActivity();
                }

            }

            @Override
            public void onFailure(@NonNull Call<RefreshTokenResp> call, @NonNull Throwable t) {

                progressBar.setVisibility(View.GONE);
                Log.d("onFailure", Objects.requireNonNull(t.getMessage()));

            }

        });

    }

    public void setFcm(String token) {


        ApiInterface mApiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);
        String fcm = FirebaseInstanceId.getInstance().getToken();

        Call<JSONObject> call = mApiInterface.setFcm( fcm);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(@NonNull Call<JSONObject> call, @NonNull retrofit2.Response<JSONObject> response) {

                if (response.isSuccessful() && response.body() != null) {
//                    Log.d("UserProfile", response.body().isDocuments_uploaded() + "");


                } else if (response.code() == 401) {

                }


            }

            @Override
            public void onFailure(@NonNull Call<JSONObject> call, @NonNull Throwable t) {
                Log.d("onFailure", Objects.requireNonNull(t.getMessage()));

            }

        });


    }

}
