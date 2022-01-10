package com.roadstar.driverr.app.module.ui.your_package;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.roadstar.driverr.R;
import com.roadstar.driverr.app.module.ui.your_package.adapters.BidsOnTripAdapter;
import com.roadstar.driverr.app.module.ui.your_package.model.AllProviderBidsModel;
import com.roadstar.driverr.app.network.ApiInterface;
import com.roadstar.driverr.app.network.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOfBidsOnProviderTrip extends AppCompatActivity {

    ArrayList<AllProviderBidsModel> available_trips = new ArrayList<>();
    public ProgressBar progressDoalog;

    private TextView tv_no_bid;
    AppCompatImageView backBtn;
    AppCompatTextView title;
    public int trip_id;
    public int selectedTripPos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_bids_on_provider_trip);

        backBtn = findViewById(R.id.iv_back);
        title = findViewById(R.id.tv_title);
        tv_no_bid = findViewById(R.id.tv_no_bid);

        if (getIntent().hasExtra("trip_id")){
            trip_id = getIntent().getIntExtra("trip_id",-1);
        }

        if (getIntent().hasExtra("pos")){
            selectedTripPos = getIntent().getIntExtra("pos",-1);
        }

        title.setText("Bids");
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        progressDoalog = findViewById(R.id.progressBar);
        getAllAvailable_trips();
    }


    private void getAllAvailable_trips() {

        progressDoalog.setVisibility(View.VISIBLE);

        AllProviderBidsModel model = new AllProviderBidsModel();
        if (getIntent().hasExtra("trip_id"))
            model.setTrip_id(getIntent().getIntExtra("trip_id",-1));
        else
            model.setTrip_id(-1);

        /*Create handle for the RetrofitInstance interface*/
        ApiInterface service = RetrofitClient.getRetrofitClient().create(ApiInterface.class);
        Call<ArrayList<AllProviderBidsModel>> call = service.providerTripBids(model);
        call.enqueue(new Callback<ArrayList<AllProviderBidsModel>>() {
            @Override
            public void onResponse(Call<ArrayList<AllProviderBidsModel>> call, Response<ArrayList<AllProviderBidsModel>> response) {
                progressDoalog.setVisibility(View.GONE);
                available_trips =  response.body();
                if (available_trips != null && available_trips.size()>0) {
                    tv_no_bid.setVisibility(View.GONE);
                    setupRecyclerview();
                }else{
                    tv_no_bid.setVisibility(View.VISIBLE);
                }

                Log.d("available_trips", new Gson().toJson(available_trips));

            }

            @Override
            public void onFailure(Call<ArrayList<AllProviderBidsModel>> call, Throwable t) {
                progressDoalog.setVisibility(View.GONE);
                Toast.makeText(ListOfBidsOnProviderTrip.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void setupRecyclerview(){
        RecyclerView rvContacts = findViewById(R.id.available_trips_RV);

        BidsOnTripAdapter adapter = new BidsOnTripAdapter(ListOfBidsOnProviderTrip.this,available_trips);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(ListOfBidsOnProviderTrip.this));
        // That's all!
    }

}