package com.roadstar.driverr.app.module.ui.your_package.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.roadstar.driverr.R;
import com.roadstar.driverr.app.module.ui.request.adapter.InternationScheduleRidesAdapter;
import com.roadstar.driverr.app.module.ui.request.model.InternationTripModel;
import com.roadstar.driverr.app.network.ApiInterface;
import com.roadstar.driverr.app.network.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InternationRides extends Fragment {


    private View view;
    ArrayList<InternationTripModel> available_trips = new ArrayList<>();
    private ProgressBar progressDoalog;
    TextView noData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_internation_rides, container, false);

        noData = view.findViewById(R.id.tvnoData);
        progressDoalog = view.findViewById(R.id.progressBar);
        getAllScheduledTrips();

        return view;
    }

    private void getAllScheduledTrips() {

        progressDoalog.setVisibility(View.VISIBLE);

        /*Create handle for the RetrofitInstance interface*/
        ApiInterface service = RetrofitClient.getRetrofitClient().create(ApiInterface.class);
        Call<ArrayList<InternationTripModel>> call = service.getAllScheduledTrips();
        call.enqueue(new Callback<ArrayList<InternationTripModel>>() {
            @Override
            public void onResponse(Call<ArrayList<InternationTripModel>> call, Response<ArrayList<InternationTripModel>> response) {
                progressDoalog.setVisibility(View.GONE);


                available_trips = response.body();
                ArrayList<InternationTripModel> temp = new ArrayList<>();

                if (available_trips != null && available_trips.size() > 0) {

                    for (int i = 0; i < available_trips.size(); i++) {

                        if (available_trips.get(i).getTrip_status() == null){
                            available_trips.get(i).setTrip_status("PENDING");
                            temp.add(available_trips.get(i));
                        }
                        else
                            temp.add(available_trips.get(i));

                    }

                    available_trips = temp;


                    setupRecyclerview();
                    noData.setVisibility(View.GONE);

                } else {
                    noData.setVisibility(View.VISIBLE);
                }

                Log.d("available_trips", new Gson().toJson(available_trips));


            }

            @Override
            public void onFailure(Call<ArrayList<InternationTripModel>> call, Throwable t) {
                progressDoalog.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void setupRecyclerview() {
        // Lookup the recyclerview in activity layout
        RecyclerView rvContacts = view.findViewById(R.id.available_trips_RV);

        // Initialize contacts
        //available_trips = available_trips_model.createContactsList(20);

        // Create adapter passing in the sample user data
        InternationScheduleRidesAdapter adapter = new InternationScheduleRidesAdapter(getActivity(), available_trips);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(getContext()));
        // That's all!

    }


}