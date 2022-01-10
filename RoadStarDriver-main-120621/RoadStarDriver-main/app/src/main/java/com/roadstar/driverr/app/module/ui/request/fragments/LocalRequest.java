package com.roadstar.driverr.app.module.ui.request.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roadstar.driverr.R;
import com.roadstar.driverr.app.business.BaseItem;
import com.roadstar.driverr.app.data.models.Progress;
import com.roadstar.driverr.app.data.resp.RequestsModel;
import com.roadstar.driverr.app.module.ui.request.RequestActivity;
import com.roadstar.driverr.app.module.ui.request.adapter.RequestAdapter;
import com.roadstar.driverr.app.module.ui.request_detial.RequestDetailActivity;
import com.roadstar.driverr.app.module.ui.your_package.YourPackageActivity;
import com.roadstar.driverr.app.network.HttpResponseItem;
import com.roadstar.driverr.common.base.recycler_view.BaseRecyclerViewFragment;
import com.roadstar.driverr.common.base.recycler_view.BaseRecyclerViewHolder;
import com.roadstar.driverr.common.base.recycler_view.OnRecyclerViewItemClickListener;
import com.roadstar.driverr.common.utils.SharedHelper;

import java.util.ArrayList;
import java.util.List;

public class LocalRequest extends BaseRecyclerViewFragment implements OnRecyclerViewItemClickListener, SwipeRefreshLayout.OnRefreshListener  {

    private RequestAdapter adapter;
    RecyclerView recyclerView;

    RequestsModel requestsModel;
    private View view;
    private TextView tvNoRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_local, container, false);

        requestsModel = SharedHelper.getKey(getActivity(), "currentRequest", RequestsModel.class);

        init();

        return view;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //init();
    }

    private void init() {

        setupJobRecycleView();
        bindOnClicklistners();

        if (requestsModel != null && requestsModel.getRequests().size() > 0) {
            callGetBookingHistory();
        } else {
            view.findViewById(R.id.tvNoRequest).setVisibility(View.VISIBLE);
        }
    }


    private void bindOnClicklistners() {

    }

    private void setupJobRecycleView() {

        recyclerView = view.findViewById(R.id.recycler_view);
        //recyclerView = getRecyclerView();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(false);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

    }

    private void callGetBookingHistory() {
        addProgressItem();
        removeProgressItem();
        List<BaseItem> list = new ArrayList<>();
        list.add(requestsModel);

        populateList(list);
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void onNetworkSuccess(HttpResponseItem response) {
        super.onNetworkSuccess(response);

        animate(R.id.layout_main);
        removeProgressItem();
    }


    @Override
    public void onNetworkError(HttpResponseItem response) {
        super.onNetworkError(response);
        removeProgressItem();
        checkLoadedItems();
    }

    private void addProgressItem() {
        populateItem(new Progress());
    }

    private void populateItem(BaseItem item) {

        if (adapter == null) {
            adapter = new RequestAdapter(getActivity(), null, this);
            recyclerView.setAdapter(adapter);
        }
        adapter.add(item);
    }

    private void removeProgressItem() {
        if (adapter != null && adapter.getAdapterCount() > 0 &&
                adapter.getItemAt(adapter.getAdapterCount() - 1) instanceof Progress) {
            adapter.remove(adapter.getAdapterCount() - 1);
        }
    }

    private void populateList(List<BaseItem> itemList) {

        if (itemList.size() > 0) {

            if (adapter == null) {
                adapter = new RequestAdapter(getActivity(), itemList, this);
                recyclerView.setAdapter(adapter);

            } else {
                adapter.addAll(itemList);
                adapter.notifyDataSetChanged();


            }
        } else
            checkLoadedItems();


    }

    private void checkLoadedItems() {
//        if (!isViewNull())
//            findViewById(R.id.lay_info).setVisibility(isListEmpty() ? View.VISIBLE : View.GONE);
    }

    private boolean isListEmpty() {
        if (adapter == null)
            return true;
        return adapter.getItemCount() == 0;
    }

    @Override
    public void onRecyclerViewItemClick(BaseRecyclerViewHolder holder) {
        super.onRecyclerViewItemClick(holder);
    }

    @Override
    public void onRecyclerViewChildItemClick(BaseRecyclerViewHolder holder, int resourceId) {
        super.onRecyclerViewChildItemClick(holder, resourceId);

        redirectUserToRequestScreen();
    }

    private void redirectUserToRequestScreen() {

        if (requestsModel != null && requestsModel.getRequests().size() > 0) {
            String status = requestsModel.getRequests().get(0).getRequest().getStatus();
            if (status.equalsIgnoreCase("SEARCHING")) {
                getActivity().startActivity(new Intent(getActivity(), RequestDetailActivity.class));

            } else {
                getActivity().startActivity(new Intent(getActivity(), YourPackageActivity.class));

            }


        } else {
            getActivity().startActivity(new Intent(getActivity(), RequestActivity.class));

        }

    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        clearAdapter();
        callGetBookingHistory();
    }

    void clearAdapter() {
        adapter = null;
        recyclerView.setAdapter(null);
//        if (!isViewNull())
//            findViewById(R.id.lay_info).setVisibility(View.GONE);
    }


}