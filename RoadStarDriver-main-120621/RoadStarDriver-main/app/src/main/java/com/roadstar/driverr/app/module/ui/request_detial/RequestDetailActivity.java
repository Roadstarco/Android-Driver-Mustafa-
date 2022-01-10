package com.roadstar.driverr.app.module.ui.request_detial;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.roadstar.driverr.R;
import com.roadstar.driverr.app.data.resp.Request;
import com.roadstar.driverr.app.data.resp.RequestsModel;
import com.roadstar.driverr.app.module.ui.dialog.CancelReqDialog;
import com.roadstar.driverr.app.module.ui.your_package.YourPackageActivity;
import com.roadstar.driverr.app.network.ApiInterface;
import com.roadstar.driverr.app.network.RetrofitClient;
import com.roadstar.driverr.common.base.BaseActivity;
import com.roadstar.driverr.common.utils.SharedHelper;
import com.roadstar.driverr.common.views.VizImageView;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class RequestDetailActivity extends BaseActivity implements View.OnClickListener, CancelReqDialog.OnInputListener {


    RequestsModel requestsModel;
    Request request;
    ProgressBar progressBar;

    private TextView tv_category;
    private TextView tv_product;
    private TextView tv_weight;
    private TextView tv_height;
    private TextView tv_width;
    private TextView tv_product_description;
    private TextView tv_instructions;
    private TextView tv_receiver_name;
    private TextView tv_receiver_number;
    private TextView tv_trip_start_dest;
    private TextView tv_trip_end_dest;
    private VizImageView attachement1;
    private VizImageView attachement2;
    private VizImageView attachement3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detial);

        requestsModel = SharedHelper.getKey(RequestDetailActivity.this, "currentRequest", RequestsModel.class);
        request = requestsModel.getRequests().get(0).getRequest();

        findViews();
        init();
        setData();
    }

    private void init() {
        setActionBar(getString(R.string.str_request));
        bindClicklisteners();
    }

    void findViews() {
        progressBar = findViewById(R.id.progressBar);
        tv_category = findViewById(R.id.tv_category);
        tv_product = findViewById(R.id.tv_product);
        tv_weight = findViewById(R.id.tv_weight);
        tv_height = findViewById(R.id.tv_height);
        tv_width = findViewById(R.id.tv_width);
        tv_product_description = findViewById(R.id.tv_product_description);
        tv_instructions = findViewById(R.id.tv_instructions);
        tv_receiver_name = findViewById(R.id.tv_receiver_name);
        tv_receiver_number = findViewById(R.id.tv_receiver_number);
        tv_trip_start_dest = findViewById(R.id.tv_trip_start_dest);
        tv_trip_end_dest = findViewById(R.id.tv_trip_end_dest);
        attachement1 = findViewById(R.id.attachement1);
        attachement2 = findViewById(R.id.attachement2);
        attachement3 = findViewById(R.id.attachement3);
    }

    void setData() {

        tv_category.setText(request.getCategory());
        tv_product.setText(request.getProduct_type());
        tv_weight.setText(request.getProduct_weight() + " " + request.getWeight_unit());
        tv_height.setText(request.getProduct_height()); // height need to be added in request
        tv_width.setText(request.getProduct_width());
        tv_product_description.setText(request.getProduct_description());
        tv_instructions.setText(request.getInstruction());
        tv_receiver_name.setText(request.getReceiver_name());
        tv_receiver_number.setText(request.getReceiver_number());
        tv_trip_start_dest.setText(request.getsAddress());
        tv_trip_end_dest.setText(request.getdAddress());


        if (request.getAttachment1() != null) {
            attachement1.setImage(request.getAttachment1());

        }
        if (request.getAttachment2() != null) {
            attachement2.setImage(request.getAttachment2());

        }
        if (request.getAttachment3() != null) {
            attachement3.setImage(request.getAttachment3());

        }


    }

    private void bindClicklisteners() {

        findViewById(R.id.btn_accept).setOnClickListener(this);
        findViewById(R.id.btn_cancel_req).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        animate();
        switch (view.getId()) {

            case R.id.btn_accept:
                acceptRequest();
                break;
            case R.id.btn_cancel_req:
                cancelReqDialog();
                break;

        }
    }


    private void cancelReqDialog() {
        CancelReqDialog cancelReqDialog = new CancelReqDialog();
        cancelReqDialog.show(getSupportFragmentManager(), "CancelReqDialog");
        cancelReqDialog.setCancelable(false);

    }

    @Override
    public void onCancelReqdResponse(Boolean policyAccept) {

        if (policyAccept)
            onBackPressed();
    }

    private void acceptRequest() {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface mApiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);


        Call<String> call = mApiInterface.acceptRequest(requestsModel.getRequests().get(0).getRequestId().toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull retrofit2.Response<String> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("response", response.body());
                    startActivity(RequestDetailActivity.this, YourPackageActivity.class);
                    finish();
                }

            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d("onFailure", Objects.requireNonNull(t.getMessage()));

            }
        });
    }
}
