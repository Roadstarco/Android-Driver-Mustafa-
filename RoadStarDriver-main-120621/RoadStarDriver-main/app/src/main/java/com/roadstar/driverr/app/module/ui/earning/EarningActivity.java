package com.roadstar.driverr.app.module.ui.earning;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.roadstar.driverr.R;
import com.roadstar.driverr.app.business.BaseItem;
import com.roadstar.driverr.app.data.models.CombineHistoryModel;
import com.roadstar.driverr.app.data.models.Progress;
import com.roadstar.driverr.app.data.resp.Request;
import com.roadstar.driverr.app.data.resp.TotalEarningsSummary;
import com.roadstar.driverr.app.network.ApiInterface;
import com.roadstar.driverr.app.network.HttpResponseItem;
import com.roadstar.driverr.app.network.RetrofitClient;
import com.roadstar.driverr.common.base.recycler_view.BaseRecyclerViewActivity;
import com.roadstar.driverr.common.base.recycler_view.BaseRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class EarningActivity extends BaseRecyclerViewActivity {
    private EarningHistoryAdapter adapter;
    RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earning);
        progressBar = findViewById(R.id.progressBar);
        init();
    }

    private void init() {
        setActionBar(getString(R.string.earning));
        setupJobRecycleView();
        getTotalEarningSummary();
        getHistory();
        bindOnClicklistners();
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

    private void callGetBookingHistory(CombineHistoryModel AllRequests) {
        addProgressItem();
        removeProgressItem();


        ArrayList<Request> list1 = AllRequests.getLocalJobs();
        ArrayList<Request> list2 = AllRequests.getInternationalJobs();
        list1.addAll(list2);

        List<BaseItem> allRequestList = new ArrayList<>(list1);
        populateList(allRequestList);

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
            adapter = new EarningHistoryAdapter(this, null, this);
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
                adapter = new EarningHistoryAdapter(this, itemList, this);
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
    public void onRefresh() {
        super.onRefresh();
        clearAdapter();
        getTotalEarningSummary();
        getHistory();
    }

    void clearAdapter() {
        adapter = null;
        recyclerView.setAdapter(null);
//        if (!isViewNull())
//            findViewById(R.id.lay_info).setVisibility(View.GONE);
    }

    private void getTotalEarningSummary() {

        progressBar.setVisibility(View.VISIBLE);
        ApiInterface mApiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);

        stopRefresh();
        Call<TotalEarningsSummary> call = mApiInterface.getTotalEarnings();
        call.enqueue(new Callback<TotalEarningsSummary>() {
            @Override
            public void onResponse(@NonNull Call<TotalEarningsSummary> call, @NonNull retrofit2.Response<TotalEarningsSummary> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {

                    TextView tv_earn_amount = findViewById(R.id.tv_earn_amount);
                    Double localEarning = Double.parseDouble(response.body().getRevenue());
                    Double InternationEarning = Double.parseDouble(response.body().getInternationalRidesRevenue());

                    double total = localEarning+InternationEarning;

                    String amountWithSymbol = getString(R.string.currency_symbol)+total;

                    tv_earn_amount.setText(amountWithSymbol);
                }

            }

            @Override
            public void onFailure(@NonNull Call<TotalEarningsSummary> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);

                Log.d("onFailure", Objects.requireNonNull(t.getMessage()));

            }

        });
    }


    private void getHistory() {

        ApiInterface mApiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);


        Call<CombineHistoryModel> call = mApiInterface.getHistory();
        call.enqueue(new Callback<CombineHistoryModel>() {
            @Override
            public void onResponse(@NonNull Call<CombineHistoryModel> call, @NonNull retrofit2.Response<CombineHistoryModel> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    if (response.body() !=  null) {
                        callGetBookingHistory(response.body());
                    }
                    else {
                        findViewById(R.id.tvNoHistory).setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<CombineHistoryModel> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                stopRefresh();

                Log.d("onFailure", Objects.requireNonNull(t.getMessage()));

            }

        });
    }


}
