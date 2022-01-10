package com.roadstar.driverr.app.module.ui.available_booking;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.roadstar.driverr.R;
import com.roadstar.driverr.app.data.resp.DocumentsListResp;
import com.roadstar.driverr.app.module.ui.available_booking.adapter.AirportsAdapter;
import com.roadstar.driverr.app.module.ui.main.MainActivity;
import com.roadstar.driverr.app.module.ui.request.RequestActivity;
import com.roadstar.driverr.app.module.ui.request.model.UpdateStatusModel;
import com.roadstar.driverr.app.module.ui.request_detial.InternationalTripDetailsActivity;
import com.roadstar.driverr.app.network.ApiInterface;
import com.roadstar.driverr.app.network.RetrofitClient;
import com.roadstar.driverr.common.base.BaseActivity;

import java.util.ArrayList;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class AvailBookingActivity extends BaseActivity implements View.OnClickListener {
    EditText editTextFlight,editTextAirport;
    RecyclerView recyclerViewAirports;
    AirportsAdapter adapter;
    ProgressBar progressBar;
    String status,airport,flight;
    Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_dialouge);
        init();
    }

    void init() {
        setActionBar(getString(R.string.available_booking));
        id = getIntent().getIntExtra("id",0);
        status = getIntent().getStringExtra("status");
          bindClicklisteners();
        editTextFlight = findViewById(R.id.edt05Comment);
        editTextAirport = findViewById(R.id.edtSearchAirport);
        progressBar = findViewById(R.id.progressBar);
        recyclerViewAirports = findViewById(R.id.recycler_view_airport);
        recyclerViewAirports.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
    }

    private void bindClicklisteners() {
        findViewById(R.id.btn_search).setOnClickListener(this);
        findViewById(R.id.btn_submit).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_search:
                if(editTextAirport.getText().toString().isEmpty()) {
                    editTextAirport.setError("Requuired!");
                    editTextAirport.requestFocus();
                    return;
                }
                checkStatusAirport(editTextAirport.getText().toString());
//                startActivityWithNoHistory(this, MainActivity.class);
                break;
            case R.id.btn_submit:
                if(editTextFlight.getText().toString().isEmpty()) {
                    editTextFlight.setError("Required!");
                    editTextFlight.requestFocus();
                    return;
                }
                if(editTextAirport.getText().toString().isEmpty()) {
                    editTextAirport.setError("Required!");
                    editTextAirport.requestFocus();
                    return;
                }
                updatePackageStatus(id,status,editTextAirport.getText().toString(),editTextFlight.getText().toString());

                break;


        }
    }

    private void checkStatusAirport(String search) {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface mApiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);
        Call<ArrayList<DocumentsListResp>> call = mApiInterface.getairport(search);//currentLocation.getLatitude() + "", currentLocation.getLongitude() + "");
        call.enqueue(new Callback<ArrayList<DocumentsListResp>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<DocumentsListResp>> call, @NonNull retrofit2.Response<ArrayList<DocumentsListResp>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {

                    adapter = new AirportsAdapter(AvailBookingActivity.this, response.body(), new AirportsAdapter.Click() {
                        @Override
                        public void selectAirport(String position) {
                            editTextAirport.setText(position);
                        }
                    });
                    recyclerViewAirports.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<DocumentsListResp>> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("onFailure", Objects.requireNonNull(t.getMessage()));

            }

        });
    }

    private void updatePackageStatus(Integer id,String currentButtonStatus,String airport,String flightno) {

        progressBar.setVisibility(View.VISIBLE);
        UpdateStatusModel updatePackageStatusReq = new UpdateStatusModel();

        updatePackageStatusReq.setTrip_id(id);
        updatePackageStatusReq.setTrip_status(currentButtonStatus);
        updatePackageStatusReq.setAirport(airport);
        updatePackageStatusReq.setFlight_no(flightno);

        ApiInterface mApiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);


        Call<ResponseBody> call = mApiInterface.updateStatus(updatePackageStatusReq);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {


                progressBar.setVisibility(View.GONE);

                if (response.code() == 200) {
                    if(InternationalTripDetailsActivity.activity!=null)
                        InternationalTripDetailsActivity.activity.finish();
                    if(RequestActivity.activity!=null)
                        RequestActivity.activity.finish();
                    finish();
                    Log.d("response", response.body().toString());
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