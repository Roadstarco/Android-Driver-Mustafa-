package com.roadstar.driverr.app.module.ui.booking_history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.roadstar.driverr.R;
import com.roadstar.driverr.app.business.BaseItem;
import com.roadstar.driverr.app.data.resp.Payment;
import com.roadstar.driverr.app.data.resp.Request;
import com.roadstar.driverr.common.base.recycler_view.BaseRecyclerViewAdapter;
import com.roadstar.driverr.common.base.recycler_view.BaseRecyclerViewHolder;
import com.roadstar.driverr.common.base.recycler_view.OnRecyclerViewItemClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookingHistoryAdapter extends BaseRecyclerViewAdapter {

    Context context;

    public BookingHistoryAdapter(Context context, List<BaseItem> items, OnRecyclerViewItemClickListener itemClickListener) {
        super(items, itemClickListener);
        this.context = context;
    }


    @Override
    public BaseRecyclerViewHolder createSpecificViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ArchiveHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_item, parent, false));
    }

    @Override
    public void bindSpecificViewHolder(@NonNull BaseRecyclerViewHolder holder, int position, @NonNull List<Object> payloads) {
        Request request = (Request) getItemAt(position);
        Payment payment = request.getPayment();
        ArchiveHolder archiveHolder = (ArchiveHolder) holder;

        try {

            String amountWithSymbol = context.getString(R.string.currency_symbol)+payment.getTotal();
            archiveHolder.tripAmount.setText(amountWithSymbol);
            if (request.getsAddress() == null)
                archiveHolder.tv_trip_start_dest.setText(request.getTripfrom());
            else
                archiveHolder.tv_trip_start_dest.setText(request.getsAddress());

            if (request.getdAddress() == null)
                archiveHolder.tv_trip_end_dest.setText(request.getTripto());
            else
                archiveHolder.tv_trip_end_dest.setText(request.getdAddress());

            if (request.getBookingId() == null)
                archiveHolder.tv_booking_id.setText(request.getProviderId());
            else
                archiveHolder.tv_booking_id.setText(request.getBookingId());

            String form;
            if (request.getBookingId() == null)
                form = request.getCreatedAt();
            else
                form = request.getAssignedAt();

            String date = getDate(form) + "th " + getMonth(form) + " " + getYear(form) + " at " + getTime(form);

            archiveHolder.tv_tripDateTime.setText(date);

        } catch (Exception e) {
            e.printStackTrace();
            archiveHolder.tv_tripDateTime.setText(request.getAssignedAt());
        }
    }

    private class ArchiveHolder extends BaseRecyclerViewHolder {

        private TextView tvDesc, tvTime, tvSDate, tvEDate, tv_booking_id, tv_tripDateTime, tripAmount, tv_trip_start_dest, tv_trip_end_dest;


        public ArchiveHolder(View view) {
            super(view);

            tripAmount = view.findViewById(R.id.tripAmount);
            tv_trip_start_dest = view.findViewById(R.id.tv_trip_start_dest);
            tv_trip_end_dest = view.findViewById(R.id.tv_trip_end_dest);
            tv_tripDateTime = view.findViewById(R.id.tv_tripDateTime);
            tv_booking_id = view.findViewById(R.id.booking_id);

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
                getItemClickListener().onRecyclerViewItemClick(ArchiveHolder.this);
            }

        }
    }

    private String getMonth(String date) throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        String monthName = new SimpleDateFormat("MMM").format(cal.getTime());
        return monthName;
    }

    private String getDate(String date) throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        String dateName = new SimpleDateFormat("dd").format(cal.getTime());
        return dateName;
    }

    private String getYear(String date) throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        String yearName = new SimpleDateFormat("yyyy").format(cal.getTime());
        return yearName;
    }

    private String getTime(String date) throws ParseException {
        Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        String timeName = new SimpleDateFormat("hh:mm a").format(cal.getTime());
        return timeName;
    }
}


