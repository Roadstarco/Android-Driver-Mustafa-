package com.roadstar.driverr.app.module.ui.request.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.roadstar.driverr.R;
import com.roadstar.driverr.app.business.BaseItem;
import com.roadstar.driverr.app.data.models.Progress;
import com.roadstar.driverr.app.data.resp.RequestsModel;
import com.roadstar.driverr.app.module.ui.request.adapter.InternationalRequestAdapter;
import com.roadstar.driverr.app.module.ui.request.model.InternationTripModel;
import com.roadstar.driverr.app.module.ui.request_detial.InternationalTripDetailsActivity;
import com.roadstar.driverr.common.base.recycler_view.BaseRecyclerViewFragment;
import com.roadstar.driverr.common.base.recycler_view.BaseRecyclerViewHolder;
import com.roadstar.driverr.common.base.recycler_view.OnRecyclerViewItemClickListener;
import com.roadstar.driverr.common.utils.SharedHelper;

import java.util.ArrayList;
import java.util.List;

public class InternationalRequests extends BaseRecyclerViewFragment implements OnRecyclerViewItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    private View view;

    ArrayList<InternationTripModel> available_trips = new ArrayList<>();
    private ProgressBar progressDoalog;
    RequestsModel requestsModel;
    private TextView tvNoRequest;
    private RecyclerView recyclerView;
    private InternationalRequestAdapter adapter;
    private Handler handler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_international_requests, container, false);

        requestsModel = SharedHelper.getKey(getActivity(), "currentRequest", RequestsModel.class);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //call function
                if (requestsModel!=null){
                    callGetBookingHistory();
                }
                handler.postDelayed(this, 3000);
            }
        }, 3000);

        init();

        return view;
    }

    private void callGetBookingHistory() {
        try {
            requestsModel = SharedHelper.getKey(getActivity(), "currentRequest", RequestsModel.class);

            addProgressItem();
            removeProgressItem();
            List<BaseItem> list = new ArrayList<>(requestsModel.getUser_trips());
            //list.add(requestsModel.getUser_trips().get(0));

            populateList(list);
        }catch (Exception ignored){}
    }

    @Override
    public void onPause() {
        super.onPause();

        handler.removeCallbacks(null);
    }

    private void populateList(List<BaseItem> itemList) {

        if (itemList.size() > 0) {

            if (adapter == null) {
                adapter = new InternationalRequestAdapter(getActivity(), itemList, this);
                recyclerView.setAdapter(adapter);

            } else {
                adapter.clearItems();
                adapter.addAll(itemList);
                adapter.notifyDataSetChanged();
            }
        } else {
            checkLoadedItems();
        }


    }

    private void checkLoadedItems() {
//        if (!isViewNull())
//            findViewById(R.id.lay_info).setVisibility(isListEmpty() ? View.VISIBLE : View.GONE);
    }

    private void populateItem(BaseItem item) {

        if (adapter == null) {
            adapter = new InternationalRequestAdapter(getActivity(), null, this);
            recyclerView.setAdapter(adapter);
        }
        adapter.add(item);
    }

    private void addProgressItem() {
        populateItem(new Progress());
    }

    private void removeProgressItem() {
        if (adapter != null && adapter.getAdapterCount() > 0 &&
                adapter.getItemAt(adapter.getAdapterCount() - 1) instanceof Progress) {
            adapter.remove(adapter.getAdapterCount() - 1);
        }
    }

    private void init() {


        setupRecyclerview();
        if (requestsModel != null && requestsModel.getUser_trips()!=null && requestsModel.getUser_trips().size()>0) {
            callGetBookingHistory();
        } else {
            view.findViewById(R.id.tvNoRequest).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRecyclerViewChildItemClick(BaseRecyclerViewHolder holder, int resourceId) {
        super.onRecyclerViewChildItemClick(holder, resourceId);

        getActivity().startActivity(new Intent(getActivity(), InternationalTripDetailsActivity.class));
    }

    public void setupRecyclerview() {

        recyclerView = view.findViewById(R.id.available_trips_RV);
        //recyclerView = getRecyclerView();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(false);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

    }

}