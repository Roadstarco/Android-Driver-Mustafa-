package com.roadstar.driverr.app.module.ui.your_package.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.roadstar.driverr.R;
import com.roadstar.driverr.app.module.ui.request_detial.InternationalTripDetailsActivity;
import com.roadstar.driverr.app.module.ui.your_package.ListOfBidsOnProviderTrip;
import com.roadstar.driverr.app.module.ui.your_package.model.AcceptUserBid;
import com.roadstar.driverr.app.module.ui.your_package.model.AllProviderBidsModel;
import com.roadstar.driverr.app.network.ApiInterface;
import com.roadstar.driverr.app.network.RetrofitClient;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BidsOnTripAdapter extends
    RecyclerView.Adapter<BidsOnTripAdapter.ViewHolder> {

    private final Context context;
    public static List<AllProviderBidsModel> allBids;

    public static AllProviderBidsModel getSingle_item(int position){
        return allBids.get(position);
    }
    public BidsOnTripAdapter(Context context, List<AllProviderBidsModel> contacts) {
        allBids = contacts;
        this.context = context;
    }

    public AllProviderBidsModel getSingleItem(int pos){
        return allBids.get(pos);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.all_bids_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AllProviderBidsModel singleItem = allBids.get(position);

        holder.startDate.setText(singleItem.getCreated_at());
        if (singleItem.getProvider_id() != null)
            holder.provider_id.setText("Provider ID :"+singleItem.getProvider_id().toString());

        holder.src.setText("First Name: "+singleItem.getFirst_name());
        holder.des.setText("Last Name: "+singleItem.getLast_name());
        holder.price.setText("$"+singleItem.getAmount());

        if (singleItem.getStatus().toLowerCase().equals("pending")){

            holder.buttonContainer.setVisibility(View.VISIBLE);
            holder.viewStatus.setVisibility(View.GONE);

        }else {

            holder.buttonContainer.setVisibility(View.GONE);
            holder.viewStatus.setVisibility(View.VISIBLE);
        }

        if (!singleItem.getStatus().equals( "Approved") && singleItem.getIs_counter() == 1){
            holder.counterOfferStatus.setText("your Counter Offer of "+singleItem.getCounter_amount()+" has been  Sent successfully And waiting for approval");
            holder.accept.setVisibility(View.GONE);
            holder.reject.setVisibility(View.GONE);
        }

        holder.viewStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tripDetailActivity = new Intent(context, InternationalTripDetailsActivity.class);

                tripDetailActivity.putExtra("trip_id",((ListOfBidsOnProviderTrip)context).trip_id);
                tripDetailActivity.putExtra("pos",((ListOfBidsOnProviderTrip)context).selectedTripPos);
                tripDetailActivity.putExtra("bids?",false);

                context.startActivity(tripDetailActivity);

            }
        });

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                acceptRequest(singleItem.getId());
            }
        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addcounterOfferDialog(singleItem.getId());
            }
        });

    }

    private void addcounterOfferDialog(Integer bid_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.counter_bid_layout, null, false);

        EditText amount = dialogView.findViewById(R.id.et_bid_amount);
        ProgressBar progressBarInDialog = dialogView.findViewById(R.id.progressBarInDialog);
        AppCompatButton sendButton = dialogView.findViewById(R.id.btn_accept);
        AppCompatButton cancel = dialogView.findViewById(R.id.cancel);



        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(amount.getText())){
                    amount.setError("Add bid amount");
                }else {
                    callAddCounterAPI(progressBarInDialog,amount,bid_id,alertDialog);
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    private void callAddCounterAPI(ProgressBar progressBarInDialog, EditText amount, Integer bid_id, AlertDialog alertDialog) {
       // ((ListOfBidsOnTrip)context).progressDoalog.setVisibility(View.VISIBLE);
        progressBarInDialog.setVisibility(View.VISIBLE);
        AcceptUserBid model = new AcceptUserBid();
        //model.setTrip_id(trip_id);
        model.setBid_id(bid_id);
        model.setCounter_amount(amount.getText().toString());

        /*Create handle for the RetrofitInstance interface*/
        ApiInterface service = RetrofitClient.getRetrofitClient().create(ApiInterface.class);
        Call<ResponseBody> call = service.addCounterOffer(model);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //((ListOfBidsOnTrip)context).progressDoalog.setVisibility(View.GONE);
                progressBarInDialog.setVisibility(View.GONE);
                alertDialog.dismiss();
                if (response.code() == 200){
                    Toast.makeText(context, "counter offer send !", Toast.LENGTH_SHORT).show();
                    ((ListOfBidsOnProviderTrip)context).finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //((ListOfBidsOnTrip)context).progressDoalog.setVisibility(View.GONE);
                progressBarInDialog.setVisibility(View.GONE);
                alertDialog.dismiss();
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void acceptRequest(Integer bidID) {

        ((ListOfBidsOnProviderTrip)context).progressDoalog.setVisibility(View.VISIBLE);

        AcceptUserBid model = new AcceptUserBid();
        model.setTrip_id(((ListOfBidsOnProviderTrip)context).trip_id);
        model.setBid_id(bidID);
        model.setStatus("Approved");
        model.setTraveller_response("Thanks for offering my a bid. I`m glad to accept you offer");


        /*Create handle for the RetrofitInstance interface*/
        ApiInterface service = RetrofitClient.getRetrofitClient().create(ApiInterface.class);
        Call<ResponseBody> call = service.acceptBid(model);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ((ListOfBidsOnProviderTrip)context).progressDoalog.setVisibility(View.GONE);
                if (response.code() == 200){
                    Toast.makeText(context, "Rider Assigned", Toast.LENGTH_SHORT).show();
                    ((ListOfBidsOnProviderTrip)context).finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                ((ListOfBidsOnProviderTrip)context).progressDoalog.setVisibility(View.GONE);
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return allBids.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView startDate,provider_id,src,des,price,counterOfferStatus;
        public Button messageButton;
        public CardView main_layout;
        public LinearLayout buttonContainer;
        public AppCompatButton accept,reject,viewStatus;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            buttonContainer = itemView.findViewById(R.id.buttonContainer);
            main_layout = itemView.findViewById(R.id.main_layout);
            startDate = (TextView) itemView.findViewById(R.id.tv_tripDateTime);
            price = (TextView) itemView.findViewById(R.id.tripAmount);
            counterOfferStatus = (TextView) itemView.findViewById(R.id.counterOfferStatus);
            provider_id = (TextView) itemView.findViewById(R.id.booking_id);
            src = (TextView) itemView.findViewById(R.id.tv_trip_start_dest);
            des = (TextView) itemView.findViewById(R.id.tv_trip_end_dest);
            accept =  itemView.findViewById(R.id.accept_bid);
            reject =  itemView.findViewById(R.id.btn_cancel_req);
            viewStatus =  itemView.findViewById(R.id.view_status);

        }
    }
}