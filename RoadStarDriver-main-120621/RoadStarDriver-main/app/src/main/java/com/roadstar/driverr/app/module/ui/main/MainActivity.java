package com.roadstar.driverr.app.module.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.roadstar.driverr.R;
import com.roadstar.driverr.app.data.preferences.SharedPreferenceManager;
import com.roadstar.driverr.app.data.resp.DocumentsListResp;
import com.roadstar.driverr.app.data.resp.RequestsModel;
import com.roadstar.driverr.app.module.ui.PostTripActivtiy.PostATrip;
import com.roadstar.driverr.app.module.ui.auth.WelcomeActivity;
import com.roadstar.driverr.app.module.ui.booking_history.BookingHistoryActivity;
import com.roadstar.driverr.app.module.ui.dialog.LogoutConfrmDialog;
import com.roadstar.driverr.app.module.ui.document.DocumentActivity;
import com.roadstar.driverr.app.module.ui.earning.EarningActivity;
import com.roadstar.driverr.app.module.ui.manage_vehicle.ManageVehicleActivity;
import com.roadstar.driverr.app.module.ui.payment_method.PaymentMethodActivity;
import com.roadstar.driverr.app.module.ui.profile.MyProfileActivity;
import com.roadstar.driverr.app.module.ui.request.RequestActivity;
import com.roadstar.driverr.app.module.ui.support.SupportActivity;
import com.roadstar.driverr.app.module.ui.your_package.YourAllPackageActivity;
import com.roadstar.driverr.app.module.ui.your_package.YourPackageActivity;
import com.roadstar.driverr.app.network.ApiInterface;
import com.roadstar.driverr.app.network.RetrofitClient;
import com.roadstar.driverr.common.base.BaseActivity;
import com.roadstar.driverr.common.utils.SharedHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends BaseActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, LogoutConfrmDialog.OnInputListener {


    private int selectedItemId = 0;
    private boolean isItemSelected = false;
    private FusedLocationProviderClient fusedLocationClient;
    public Handler handler;

    private Location currentLocation;

    private Context context = MainActivity.this;

    private TextView tvNumberOfRequest;
    RequestsModel requestsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Log.d("Firebase", "token " + FirebaseInstanceId.getInstance().getToken());
        initFusedLocationProvider();
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //call function
                if (currentLocation != null) {
                    SharedHelper.putKey(context, "latitude", currentLocation.getLatitude());
                    SharedHelper.putKey(context, "longitude", currentLocation.getLongitude());
                    checkStatus();
                } else {
                    initFusedLocationProvider();
                }
                handler.postDelayed(this, 3000);
            }
        }, 3000);


        setOnlineStatus();

    }

    BottomNavigationView navigation;


    void init() {
        setActionBar(getString(R.string.road_star));
        tvNumberOfRequest = findViewById(R.id.tvNumberOfRequest);
        populateDrawerView();
        setDrawerToggle();
        bindClickListners();
        // setHeaderData();

    }

    private void bindClickListners() {

        findViewById(R.id.tv_view_profile).setOnClickListener(MainActivity.this);
        findViewById(R.id.img_req).setOnClickListener(MainActivity.this);
        findViewById(R.id.img_earning).setOnClickListener(MainActivity.this);
        findViewById(R.id.img_history).setOnClickListener(MainActivity.this);
        findViewById(R.id.ll_home).setOnClickListener(MainActivity.this);
        findViewById(R.id.ll_manage_vehicle).setOnClickListener(MainActivity.this);
        findViewById(R.id.post_trip).setOnClickListener(MainActivity.this);
        findViewById(R.id.ll_your_package).setOnClickListener(MainActivity.this);
        findViewById(R.id.ll_doc).setOnClickListener(MainActivity.this);
        findViewById(R.id.ll_booking_history).setOnClickListener(MainActivity.this);
        findViewById(R.id.ll_available_booking).setOnClickListener(MainActivity.this);
        findViewById(R.id.ll_earning).setOnClickListener(MainActivity.this);
        findViewById(R.id.ll_payment_method).setOnClickListener(MainActivity.this);
        findViewById(R.id.ll_contact_us).setOnClickListener(MainActivity.this);
        findViewById(R.id.ll_logout).setOnClickListener(MainActivity.this);

    }


    private void populateDrawerView() {
        mDrawerLayout = findViewById(R.id.layout_main);
        NavigationView mNavigationView = findViewById(R.id.nav_drawer_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setItemIconTintList(null);
        mNavigationView.setCheckedItem(R.id.home);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setHeaderData();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        if (mDrawerToggle != null)
            mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mDrawerToggle != null)
            mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        NavigationView mNavigationView = (NavigationView) findViewById(R.id.nav_drawer_view);
        mNavigationView.setCheckedItem(item.getItemId());
        selectedItemId = item.getItemId();
        isItemSelected = true;
        mDrawerLayout.closeDrawers();
        //  onDrawerClosed();
        return true;
    }

    @Override
    public void onDrawerClosed() {
        super.onDrawerClosed();
        if (!isItemSelected)
            return;
        isItemSelected = false;
        Class targetActivity = null;
        if (selectedItemId == R.id.tv_view_profile)
            targetActivity = MyProfileActivity.class;
        else if (selectedItemId == R.id.ll_home)
            closeDrawer();
        else if (selectedItemId == R.id.ll_manage_vehicle)
            targetActivity = ManageVehicleActivity.class;
        else if (selectedItemId == R.id.post_trip)
            targetActivity = PostATrip.class;
        else if (selectedItemId == R.id.ll_your_package)
            targetActivity = YourAllPackageActivity.class;
        else if (selectedItemId == R.id.ll_doc)
            targetActivity = DocumentActivity.class;
        else if (selectedItemId == R.id.ll_booking_history)
            targetActivity = BookingHistoryActivity.class;
//        else if (selectedItemId == R.id.ll_available_booking)
//            targetActivity = AvailBookingActivity.class;
        else if (selectedItemId == R.id.ll_earning)
            targetActivity = EarningActivity.class;
        else if (selectedItemId == R.id.ll_payment_method)
            targetActivity = PaymentMethodActivity.class;
        else if (selectedItemId == R.id.ll_contact_us)
            targetActivity = SupportActivity.class;
        else if (selectedItemId == R.id.ll_logout)
            showLogoutDialog();


        ScrollView scrollView = findViewById(R.id.scroll_drawer);
        scrollView.scrollTo(0, 0);
        if (targetActivity != null)
            startActivity(MainActivity.this, targetActivity);


    }

    @Override
    public void onClick(View view) {


        selectedItemId = view.getId();


        if (selectedItemId == R.id.img_req)
            redirectUserToRequestScreen();
        else if (selectedItemId == R.id.img_earning)
            startActivity(this, EarningActivity.class);
        else if (selectedItemId == R.id.img_history)
            startActivity(this, BookingHistoryActivity.class);
        else {
            isItemSelected = true;
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }


    }

    void logoutApp() {
        SharedPreferenceManager.getInstance().clearPreferences();
        SharedHelper.editor.clear().apply();
        startActivity(new Intent(MainActivity.this, WelcomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();

    }

    @Override
    public void sendResponse(Boolean policyAccept) {

        if (policyAccept)
            logoutApp();
    }

    private void showLogoutDialog() {
        LogoutConfrmDialog logoutConfrmDialog = new LogoutConfrmDialog();
        logoutConfrmDialog.show(getSupportFragmentManager(), "LogoutConfrmDialog");
        logoutConfrmDialog.setCancelable(false);

    }


    private void initFusedLocationProvider() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getLocationFused();
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            currentLocation = location;
                            Log.d("currentLocation", currentLocation.getLatitude() + " , " + currentLocation.getLongitude());
                        }
                    }
                });
    }

    public void getLocationFused() {
        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                initFusedLocationProvider();
//                mMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            initFusedLocationProvider();
//            mMap.setMyLocationEnabled(true);
        }
    }


    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(context)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        1);
                            }
                        })
                        .create()
                        .show();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // Permission Granted
//                        //Toast.makeText(SignInActivity.this, "PERMISSION_GRANTED", Toast.LENGTH_SHORT).show();
                        getLocationFused();
                        //MapsInitializer.initialize(this);

                        if (ContextCompat.checkSelfPermission(context,
                                Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED) {

                            if (fusedLocationClient == null) {
                                initFusedLocationProvider();
                            }

                        }
                    } else {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    }
                }
                break;
            case 2:
                try {
                    if (grantResults.length > 0) {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            // Permission Granted
                            Intent intent = new Intent(Intent.ACTION_CALL);
                            intent.setData(Uri.parse("tel:" + SharedHelper.getKey(context, "provider_mobile_no")));
                            startActivity(intent);
                        } else {
                            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 2);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // Permission Granted
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + SharedHelper.getKey(context, "sos")));
                        startActivity(intent);
                    } else {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 3);
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void checkStatus() {

        ApiInterface mApiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);
        Call<RequestsModel> call = mApiInterface.checkRequestStatus();//currentLocation.getLatitude() + "", currentLocation.getLongitude() + "");
        call.enqueue(new Callback<RequestsModel>() {
            @Override
            public void onResponse(@NonNull Call<RequestsModel> call, @NonNull retrofit2.Response<RequestsModel> response) {
                if (response.isSuccessful() && response.body() != null) {

                    requestsModel = response.body();
                    SharedHelper.putKey(context, "currentRequest", response.body());
                    if (response.body().getRequests().size() > 0) {
                        tvNumberOfRequest.setText(response.body().getRequests().size() + "");
                        tvNumberOfRequest.setVisibility(View.VISIBLE);
                    }else if (response.body().getUser_trips().size() > 0){
                        tvNumberOfRequest.setText(response.body().getUser_trips().size()+"");
                        tvNumberOfRequest.setVisibility(View.VISIBLE);
                    }else {
                        tvNumberOfRequest.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<RequestsModel> call, @NonNull Throwable t) {
                Log.d("onFailure", Objects.requireNonNull(t.getMessage()));

            }

        });
    }



    private void redirectUserToRequestScreen() {

        if (requestsModel != null && requestsModel.getRequests().size() > 0) {
            String status = requestsModel.getRequests().get(0).getRequest().getStatus();
            if (status.equalsIgnoreCase("SEARCHING")) {
                startActivity(this, RequestActivity.class);

            } else {
                startActivity(this, YourPackageActivity.class);

            }
        } else {
            startActivity(this, RequestActivity.class);

        }
    }

    private void getProfile() {

        ApiInterface mApiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);
        Call<RequestsModel> call = mApiInterface.checkRequestStatus(currentLocation.getLatitude() + "", currentLocation.getLongitude() + "");
        call.enqueue(new Callback<RequestsModel>() {
            @Override
            public void onResponse(@NonNull Call<RequestsModel> call, @NonNull retrofit2.Response<RequestsModel> response) {
                if (response.isSuccessful() && response.body() != null) {

                    requestsModel = response.body();
                    SharedHelper.putKey(context, "currentRequest", response.body());
                    if (response.body().getRequests().size() > 0) {
                        tvNumberOfRequest.setText(response.body().getRequests().size() + "");
                        tvNumberOfRequest.setVisibility(View.VISIBLE);
                    }else if (response.body().getUser_trips().size() > 0){
                        tvNumberOfRequest.setText(response.body().getUser_trips().size()+"");
                        tvNumberOfRequest.setVisibility(View.VISIBLE);
                    }else {
                        tvNumberOfRequest.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<RequestsModel> call, @NonNull Throwable t) {
                Log.d("onFailure", Objects.requireNonNull(t.getMessage()));

            }

        });
    }
}
