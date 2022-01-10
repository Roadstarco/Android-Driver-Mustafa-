package com.roadstar.driverr.app.module.ui.your_package;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.roadstar.driverr.R;
import com.roadstar.driverr.app.data.req.LocationObjectReq;
import com.roadstar.driverr.app.data.req.UpdatePackageStatusReq;
import com.roadstar.driverr.app.data.resp.Request;
import com.roadstar.driverr.app.data.resp.RequestsModel;
import com.roadstar.driverr.app.module.ui.dialog.PaymentDialogue;
import com.roadstar.driverr.app.module.ui.dialog.RatingDialogue;
import com.roadstar.driverr.app.module.ui.support.SupportActivity;
import com.roadstar.driverr.app.network.ApiInterface;
import com.roadstar.driverr.app.network.RetrofitClient;
import com.roadstar.driverr.common.utils.LocationUpdatesService;
import com.roadstar.driverr.common.utils.LocationUtils;
import com.roadstar.driverr.common.utils.SharedHelper;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class YourPackageActivity extends MapHandlerActivity implements View.OnClickListener {


    // The BroadcastReceiver used to listen from broadcasts from the service.
    private MyReceiver myReceiver;
    // A reference to the service used to get location updates.
    private LocationUpdatesService mService = null;
    // Tracks the bound state of the service.
    private boolean mBound = false;
    // Monitors the state of the connection to the service.

    private Context context = YourPackageActivity.this;
    public Handler handler;

    RequestsModel requestsModel;

    private ProgressBar progressBar;

    private double srcLatitude = 0;
    private double srcLongitude = 0;
    private double destLatitude = 0;
    private double destLongitude = 0;

    private Button btnPackageStatus;
    private Button btnConfirmPayment;

    private String currentButtonStatus = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_package);
        init();
        initService();
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //call function

                String longitude = SharedHelper.getKey(context, "longitude");
                String latitude = SharedHelper.getKey(context, "latitude");
                if (latitude != null && longitude != null) {
                    checkStatus();

                }
                handler.postDelayed(this, 3000);
            }
        }, 3000);
    }

    void init() {
        setActionBar("");
        btnPackageStatus = findViewById(R.id.btn_package_status);
        progressBar = findViewById(R.id.progressBar);
        initMap();
        bindClicklisteners();
    }

    private void bindClicklisteners() {

        findViewById(R.id.btn_chat).setOnClickListener(this);
        findViewById(R.id.btn_package_status).setOnClickListener(this);
        findViewById(R.id.btnConfirmPayment).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_chat:
                startActivity(this, SupportActivity.class);
                break;
            case R.id.btn_package_status:
                performActionOnButtonClick();
                break;

            case R.id.btnConfirmPayment:
                updatePackageStatus();
                break;
        }
    }


    public void setPackageStatus() {

        String currentStatus = requestsModel.getRequests().get(0).getRequest().getStatus();
        Request request = requestsModel.getRequests().get(0).getRequest();

        switch (currentStatus) {

            case "STARTED":
                currentButtonStatus = "ARRIVED";
                btnPackageStatus.setText("TAP WHEN ARRIVED");
                setPickupRoutOnMap();
                break;

            case "ARRIVED":
                currentButtonStatus = "PICKEDUP";
                setPickupRoutOnMap();
                btnPackageStatus.setText("PICKUP PACKAGE");
                break;


            case "PICKEDUP":
                currentButtonStatus = "DROPPED";
                btnPackageStatus.setText("TAP WHEN DELIVERED");
                setDestinationRouteOnOnMap();
                break;

            case "DROPPED":
                currentButtonStatus = "COMPLETED";
                btnPackageStatus.setText("DELIVERED");
                setDestinationRouteOnOnMap();
                new PaymentDialogue(YourPackageActivity.this, requestsModel);

                break;

            case "COMPLETED":
                currentButtonStatus = "RATE";
                btnPackageStatus.setText("RATE");
                new RatingDialogue(YourPackageActivity.this, requestsModel);
                break;

            case "RATE":
                currentButtonStatus = "RATE";
                btnPackageStatus.setText("RATE");
                break;
        }


    }


    private void performActionOnButtonClick() {

        switch (currentButtonStatus) {

            case "ARRIVED":
            case "PICKEDUP":
            case "DROPPED":
            case "COMPLETED":
                updatePackageStatus();
                break;


        }

    }

    public void initService() {
        bindService(new Intent(YourPackageActivity.this, LocationUpdatesService.class), mServiceConnection,
                Context.BIND_AUTO_CREATE);

    }

    private void checkStatus() {

        ApiInterface mApiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);

        String longitude = SharedHelper.getKey(context, "longitude");
        String latitude = SharedHelper.getKey(context, "latitude");
        Call<RequestsModel> call = mApiInterface.checkRequestStatus(latitude + "", longitude + "");
        call.enqueue(new Callback<RequestsModel>() {
            @Override
            public void onResponse(@NonNull Call<RequestsModel> call, @NonNull retrofit2.Response<RequestsModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getRequests().size() > 0) {
                        SharedHelper.putKey(context, "currentRequest", response.body());
                        requestsModel = response.body();
                        setPackageStatus();

                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<RequestsModel> call, @NonNull Throwable t) {
                Log.d("onFailure", Objects.requireNonNull(t.getMessage()));

            }

        });
    }


    private void updatePackageStatus() {
        progressBar.setVisibility(View.VISIBLE);
        UpdatePackageStatusReq updatePackageStatusReq = new UpdatePackageStatusReq();
        updatePackageStatusReq.set_method("PATCH");
        updatePackageStatusReq.setStatus(currentButtonStatus);

        ApiInterface mApiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);


        Call<Request> call = mApiInterface.updatePackageStatus(requestsModel.getRequests().get(0).getRequestId(), updatePackageStatusReq);
        call.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(@NonNull Call<Request> call, @NonNull retrofit2.Response<Request> response) {

                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {

                    Log.d("response", response.body().toString());
                }

            }

            @Override
            public void onFailure(@NonNull Call<Request> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("onFailure", Objects.requireNonNull(t.getMessage()));

            }

        });
    }


    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocationUpdatesService.LocalBinder binder = (LocationUpdatesService.LocalBinder) service;
            mService = binder.getService();
            mService.requestLocationUpdates();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };

    /**
     * Receiver for broadcasts sent by {@link LocationUpdatesService}.
     */
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Location location = intent.getParcelableExtra(LocationUpdatesService.EXTRA_LOCATION);
            double distance = intent.getDoubleExtra("DISTANCE", 1);
            if (location != null) {
                updateLiveTracking(location.getLatitude() + "", location.getLongitude() + "", distance);
                SharedHelper.putKey(context, "latitude", location.getLatitude());
                SharedHelper.putKey(context, "longitude", location.getLongitude());
            }
        }
    }

    private void updateLiveTracking(String latitude, String longitude, double distance) {
        LocationObjectReq latLongObject = new LocationObjectReq();
        latLongObject.setLatitude(Double.parseDouble(latitude));
        latLongObject.setLongitude(Double.parseDouble(longitude));
        latLongObject.setDistance(distance);

        ApiInterface mApiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);

        Call<ResponseBody> call = mApiInterface.getLiveTracking(requestsModel.getRequests().get(0).getRequestId(), latLongObject);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Log.e("sUCESS", "SUCESS" + response.body());
                if (response.body() != null) {
                    try {
                        String bodyString = new String(response.body().bytes());
                        Log.e("sUCESS", "bodyString" + bodyString);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    private void setPickupRoutOnMap() {
        LatLng sourceLatLng = new LatLng(Double.parseDouble(SharedHelper.getKey(context, "latitude")), Double.parseDouble(SharedHelper.getKey(context, "longitude")));

        srcLatitude = Double.parseDouble(requestsModel.getRequests().get(0).getRequest().getsLatitude());
        srcLongitude = Double.parseDouble(requestsModel.getRequests().get(0).getRequest().getsLongitude());
        destLatitude = Double.parseDouble(requestsModel.getRequests().get(0).getRequest().getdLatitude());
        destLongitude = Double.parseDouble(requestsModel.getRequests().get(0).getRequest().getdLongitude());

        LatLng destLatLng = new LatLng(srcLatitude, srcLongitude);
        LocationUtils locationUtils = new LocationUtils(context, mMap, sourceLatLng, destLatLng);
    }

    private void setDestinationRouteOnOnMap() {

        srcLatitude = Double.parseDouble(requestsModel.getRequests().get(0).getRequest().getsLatitude());
        srcLongitude = Double.parseDouble(requestsModel.getRequests().get(0).getRequest().getsLongitude());
        destLatitude = Double.parseDouble(requestsModel.getRequests().get(0).getRequest().getdLatitude());
        destLongitude = Double.parseDouble(requestsModel.getRequests().get(0).getRequest().getdLongitude());


        if (SharedHelper.getKey(context, "latitude").length() > 0 && SharedHelper.getKey(context, "longitude").length() > 0) {
            LatLng sourceLatLng = new LatLng(Double.parseDouble(SharedHelper.getKey(context, "latitude")), Double.parseDouble(SharedHelper.getKey(context, "longitude")));
            LatLng destLatLng = new LatLng(destLatitude, destLongitude);
            try {
                LocationUtils locationUtils = new LocationUtils(context, mMap, sourceLatLng, destLatLng);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}