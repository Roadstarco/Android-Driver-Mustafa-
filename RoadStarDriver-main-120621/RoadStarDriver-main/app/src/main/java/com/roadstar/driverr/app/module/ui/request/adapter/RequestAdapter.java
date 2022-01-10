package com.roadstar.driverr.app.module.ui.request.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.roadstar.driverr.R;
import com.roadstar.driverr.app.business.BaseItem;
import com.roadstar.driverr.app.data.resp.Request;
import com.roadstar.driverr.app.data.resp.RequestsModel;
import com.roadstar.driverr.app.data.resp.UserModel;
import com.roadstar.driverr.common.base.recycler_view.BaseRecyclerViewAdapter;
import com.roadstar.driverr.common.base.recycler_view.BaseRecyclerViewHolder;
import com.roadstar.driverr.common.base.recycler_view.OnRecyclerViewItemClickListener;

import java.util.List;

public class RequestAdapter extends BaseRecyclerViewAdapter {

    Context context;

    public RequestAdapter(Context context, List<BaseItem> items, OnRecyclerViewItemClickListener itemClickListener) {
        super(items, itemClickListener);
        this.context = context;
    }


    @Override
    public BaseRecyclerViewHolder createSpecificViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ArchiveHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.request_item, parent, false));
    }

    @Override
    public void bindSpecificViewHolder(@NonNull BaseRecyclerViewHolder holder, int position, @NonNull List<Object> payloads) {
        RequestsModel requestsModel = (RequestsModel) getItemAt(position);


        UserModel userModel = requestsModel.getRequests().get(0).getRequest().getUserModel();
        Request request = requestsModel.getRequests().get(0).getRequest();


//        String startTime = DateUtils.convertDate(jobs.getStartTime(), HOUR_FORMAT_1);
//        String startDate = DateUtils.convertDate(jobs.getStartTime(), DATE_FORMAT_5);
//        String endDate = DateUtils.convertDate(jobs.getEndTime(), DATE_FORMAT_5);
//
        ArchiveHolder archiveHolder = (ArchiveHolder) holder;
//        archiveHolder.tvDesc.setText(jobs.getJobDesc());
//        archiveHolder.tvTime.setText(startTime + " | " + startDate);
//        archiveHolder.tvSDate.setText(startDate);
//        archiveHolder.tvEDate.setText(endDate);


        archiveHolder.tv_user_name.setText(userModel.getFirstName());
        archiveHolder.tv_address.setText(request.getsAddress());
        archiveHolder.tripAmount.setText("$10");
        archiveHolder.tv_tripDateTime.setText(request.getStartedAt());

    }

    private class ArchiveHolder extends BaseRecyclerViewHolder {

        private TextView tvDesc, tvTime, tvSDate, tvEDate, tv_user_name, tv_time, tv_address, tv_tripDateTime, tripAmount;
        private AppCompatButton btnFullDetails;

        public ArchiveHolder(View view) {
            super(view);


            tv_user_name = view.findViewById(R.id.tv_user_name);
            tv_address = view.findViewById(R.id.tv_address);
            tv_tripDateTime = view.findViewById(R.id.tv_tripDateTime);
            tripAmount = view.findViewById(R.id.tripAmount);
            tv_time = view.findViewById(R.id.tv_time);


//            tvDesc = view.findViewById(R.id.tv_job_desc);
//            tvTime = view.findViewById(R.id.tv_time);
//            tvSDate = view.findViewById(R.id.tv_s_date);
//            tvEDate = view.findViewById(R.id.tv_e_date);
            btnFullDetails = view.findViewById(R.id.btn_view_detail);
            btnFullDetails.setOnClickListener(this);
            view.setOnClickListener(this);
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
