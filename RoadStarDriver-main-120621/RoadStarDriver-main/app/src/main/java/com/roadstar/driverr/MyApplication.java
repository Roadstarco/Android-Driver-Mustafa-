package com.roadstar.driverr;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.roadstar.driverr.app.data.UserManager;
import com.roadstar.driverr.app.data.preferences.SharedPreferenceManager;
import com.roadstar.driverr.app.data.resp.UserProfileResp;
import com.roadstar.driverr.app.module.ui.SplashActivity;
import com.roadstar.driverr.app.network.ApiInterface;
import com.roadstar.driverr.app.network.RetrofitClient;
import com.roadstar.driverr.common.utils.ApiConstants;
import com.roadstar.driverr.common.utils.SharedHelper;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import io.fabric.sdk.android.Fabric;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by $Bilal on 11/24/2017.
 */

public class MyApplication extends MultiDexApplication {

    public static FirebaseAuth sAuth;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
        SharedPreferenceManager.setSingletonInstance(context);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        sAuth = FirebaseAuth.getInstance();


        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


      //  Crashlytics.getInstance().crash();
    }


}
