package com.roadstar.driverr.app.module.ui.request.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.roadstar.driverr.R;
import com.roadstar.driverr.app.module.ui.request.model.InternationTripModel;
import com.roadstar.driverr.app.module.ui.request_detial.InternationalTripDetailsActivity;
import com.roadstar.driverr.app.module.ui.your_package.ListOfBidsOnProviderTrip;

import java.util.ArrayList;
import java.util.List;

public class InternationScheduleRidesAdapter extends Adapter<InternationScheduleRidesAdapter.ArchiveHolder> {

    Context context;
    public static List<InternationTripModel> allBids;


    public InternationScheduleRidesAdapter(Context context, ArrayList<InternationTripModel> items) {
        allBids = items;
        this.context = context;
    }

    @Override
    public ArchiveHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.international_trip_layout, parent, false);

        // Return a new holder instance
        return new ArchiveHolder(contactView);

    }

    @Override
    public void onBindViewHolder(@NonNull ArchiveHolder holder, int position) {
        InternationTripModel internationTripModel =  allBids.get(position);// requestsModel.getUser_trips().get(position);

        holder.startDate.setText(internationTripModel.getArrival_date());
        holder.provider_id.setText(String.valueOf(internationTripModel.getProvider_id()));
        holder.src.setText(internationTripModel.getTripfrom());
        holder.des.setText(internationTripModel.getTripto());
        holder.tripAmount.setText(String.valueOf(""+internationTripModel.getTrip_status()));

        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (internationTripModel.getCreated_by().equals("user")){
                        Intent intent = new Intent(context, InternationalTripDetailsActivity.class);
                        intent.putExtra("pos",position);
                        intent.putExtra("bids?",false);
                        context.startActivity(intent);

                    }else {
                        Intent intent = new Intent(context, ListOfBidsOnProviderTrip.class);
                        intent.putExtra("trip_id",internationTripModel.getId());
                        intent.putExtra("pos",position);
                        intent.putExtra("bids?",true);

                        context.startActivity(intent);
                    }


            }
        });
    }

    public static InternationTripModel getSingleItem(int pos){
        return allBids.get(pos);
    }
    @Override
    public int getItemCount() {
        return allBids.size();
    }

    public static class ArchiveHolder extends RecyclerView.ViewHolder {

        public TextView startDate,provider_id,src,des,tripAmount;
        public CardView main_layout;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ArchiveHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            tripAmount = itemView.findViewById(R.id.tripAmount);
            main_layout = itemView.findViewById(R.id.main_layout);
            startDate =  itemView.findViewById(R.id.tv_tripDateTime);
            provider_id =  itemView.findViewById(R.id.booking_id);
            src =  itemView.findViewById(R.id.tv_trip_start_dest);
            des =  itemView.findViewById(R.id.tv_trip_end_dest);

        }
    }

}
