package com.roadstar.driverr.app.module.ui.request;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.roadstar.driverr.R;
import com.roadstar.driverr.app.business.BaseItem;
import com.roadstar.driverr.app.data.models.Progress;
import com.roadstar.driverr.app.data.resp.RequestsModel;
import com.roadstar.driverr.app.module.ui.request.adapter.RequestAdapter;
import com.roadstar.driverr.app.module.ui.request_detial.RequestDetailActivity;
import com.roadstar.driverr.app.network.HttpResponseItem;
import com.roadstar.driverr.common.base.recycler_view.BaseRecyclerViewActivity;
import com.roadstar.driverr.common.base.recycler_view.BaseRecyclerViewHolder;
import com.roadstar.driverr.common.utils.SharedHelper;

import java.util.ArrayList;
import java.util.List;

public class RequestActivityOld extends BaseRecyclerViewActivity {
    private RequestAdapter adapter;
    RecyclerView recyclerView;

    RequestsModel requestsModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        requestsModel = SharedHelper.getKey(RequestActivityOld.this, "currentRequest", RequestsModel.class);
        init();
    }

    private void init() {
        setActionBar(getString(R.string.str_request));
        setupJobRecycleView();
        bindOnClicklistners();

        if (requestsModel != null && requestsModel.getRequests().size() > 0) {
            callGetBookingHistory();
        } else {
            findTextViewById(R.id.tvNoRequest).setVisibility(View.VISIBLE);
        }


    }

    private void bindOnClicklistners() {

    }

    private void setupJobRecycleView() {

        recyclerView = findViewById(R.id.recycler_view);
        //recyclerView = getRecyclerView();

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(false);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

    }

    private void callGetBookingHistory() {
        addProgressItem();
        removeProgressItem();
        List<BaseItem> list = new ArrayList<>();
        list.add(requestsModel);

        // list.add(new Empty(getString(R.string.str_no_history)));
        populateList(list);
//        HttpRequestItem requestItem = new HttpRequestItem(GET_ALL_JOBS_BY_USER_URL, "");
//        requestItem.setHeaderParams(AppUtils.getHeaderParams());
//        requestItem.setHttpRequestType(NetworkUtils.HTTP_GET);
//        requestItem.setHeaderParams(AppUtils.getHeaderParams());
//        AppNetworkTask appNetworkTask = new AppNetworkTask(null, this);
//        appNetworkTask.execute(requestItem);
    }

    @Override
    public void onNetworkSuccess(HttpResponseItem response) {
        super.onNetworkSuccess(response);

        animate(R.id.layout_main);
        removeProgressItem();

//        try {
//            JSONObject apiResponse = new JSONObject(response.getResponse());
//            if (apiResponse.getInt(KEY_STATUS) == 200) {
//                if (response.getHttpRequestEndPoint().equals(GET_ALL_JOBS_BY_USER_URL)) {
//                    JSONArray archiveData = apiResponse.getJSONArray(ApiConstants.KEY_DATA);
//                    List<BaseItem> list = new Gson().fromJson(archiveData.toString(), new TypeToken<List<Jobs>>() {
//                    }.getType());
//                    populateList(list);
//
//                }
//
//            } else {
//                showSnackBar(apiResponse.getString("message"));
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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
            adapter = new RequestAdapter(this, null, this);
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
                adapter = new RequestAdapter(this, itemList, this);
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
        if (adapter.getItemCount() == 0)
            return true;
        return false;
    }

    @Override
    public void onRecyclerViewItemClick(BaseRecyclerViewHolder holder) {
        super.onRecyclerViewItemClick(holder);
//        Jobs jobs = (Jobs) adapter.getItemAt(holder.getAdapterPosition());
//        Bundle bundle = new Bundle();
//        bundle.putParcelable(AppConstants.KEY_JOB, jobs);
//        JobDetailsFragment jobDetailsFragment = new JobDetailsFragment();
//        jobDetailsFragment.setArguments(bundle);
//        ((MainActivity) this).onReplaceFragment(jobDetailsFragment, true);

    }

    @Override
    public void onRecyclerViewChildItemClick(BaseRecyclerViewHolder holder, int resourceId) {
        super.onRecyclerViewChildItemClick(holder, resourceId);
        startActivity(this, RequestDetailActivity.class);
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
