package com.roadstar.driverr.app.module.ui.request.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.roadstar.driverr.R;
import com.roadstar.driverr.app.business.BaseItem;
import com.roadstar.driverr.app.module.ui.request_detial.InternationalTripDetailsActivity;
import com.roadstar.driverr.app.module.ui.request.model.InternationTripModel;
import com.roadstar.driverr.common.base.recycler_view.BaseRecyclerViewAdapter;
import com.roadstar.driverr.common.base.recycler_view.BaseRecyclerViewHolder;
import com.roadstar.driverr.common.base.recycler_view.OnRecyclerViewItemClickListener;

import java.util.List;

public class InternationalRequestAdapter extends BaseRecyclerViewAdapter {


    Context context;

    public InternationalRequestAdapter(Context context, List<BaseItem> items, OnRecyclerViewItemClickListener itemClickListener) {
        super(items, itemClickListener);
        this.context = context;
    }


    @Override
    public BaseRecyclerViewHolder createSpecificViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ArchiveHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.international_trip_layout, parent, false));
    }

    @Override
    public void bindSpecificViewHolder(@NonNull BaseRecyclerViewHolder holder, int position, @NonNull List<Object> payloads) {
        //RequestsModel requestsModel = (RequestsModel) getItemAt(position);


        InternationTripModel internationTripModel = (InternationTripModel) getItemAt(position);// requestsModel.getUser_trips().get(position);
        ArchiveHolder archiveHolder = (ArchiveHolder) holder;

        archiveHolder.startDate.setText(internationTripModel.getArrival_date());
        archiveHolder.provider_id.setText(internationTripModel.getProvider_id().toString());
        archiveHolder.src.setText(internationTripModel.getTripfrom());
        archiveHolder.des.setText(internationTripModel.getTripto());
        archiveHolder.tripAmount.setText("$ "+internationTripModel.getTrip_amount());

        ((ArchiveHolder) holder).main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InternationalTripDetailsActivity.class);
                intent.putExtra("pos",position);
                intent.putExtra("bids?",true);
                context.startActivity(intent);
            }
        });
    }

    private class ArchiveHolder extends BaseRecyclerViewHolder {

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

        @Override
        protected BaseRecyclerViewHolder populateView() {
            return ArchiveHolder.this;
        }

        @Override
        public void onClick(View v) {
            super.onClick(v);
            if (getItemClickListener() != null) {
                getItemClickListener().onRecyclerViewChildItemClick(ArchiveHolder.this, v.getId());
            }

        }
    }
}
