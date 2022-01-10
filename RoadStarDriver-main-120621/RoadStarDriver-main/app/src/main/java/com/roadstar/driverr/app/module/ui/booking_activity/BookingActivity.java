package com.roadstar.driverr.app.module.ui.booking_activity;

import android.os.Bundle;
import android.view.View;

import com.roadstar.driverr.R;
import com.roadstar.driverr.app.module.ui.dialog.CancelReqDialog;
import com.roadstar.driverr.app.module.ui.dialog.ReqAcceptDialog;
import com.roadstar.driverr.app.module.ui.your_package.MapHandlerActivity;

public class BookingActivity extends MapHandlerActivity implements View.OnClickListener, CancelReqDialog.OnInputListener, ReqAcceptDialog.OnInputListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        init();
    }

    void init() {

        initMap();
        bindClicklisteners();
    }

    private void bindClicklisteners() {
//Vehicle Type
        findViewById(R.id.iv_vechicle_back).setOnClickListener(this);

        findViewById(R.id.lay_vechicle).setOnClickListener(this);

//        Pricing lay
        findViewById(R.id.iv_pricing_back).setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);

//        package detail lay
        findViewById(R.id.iv_pkg_detail_back).setOnClickListener(this);
        findViewById(R.id.btn_pkg_dtl_next).setOnClickListener(this);

        //        Receiver detail lay
        findViewById(R.id.iv_recev_detail_back).setOnClickListener(this);
        findViewById(R.id.btn_pay).setOnClickListener(this);

        //        Payment detail lay
        findViewById(R.id.iv_pay_back).setOnClickListener(this);
        findViewById(R.id.btn_submit_req).setOnClickListener(this);
        findViewById(R.id.btn_cancel_req).setOnClickListener(this);

        //Rating Screen
        findViewById(R.id.iv_rating_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        animate();
        switch (view.getId()) {
            case R.id.iv_vechicle_back:
                onBackPressed();
                break;
            case R.id.lay_vechicle:
                findViewById(R.id.lay_choose_vehicle).setVisibility(View.GONE);
                findViewById(R.id.lay_pricing).setVisibility(View.VISIBLE);
                break;
            case R.id.iv_pricing_back:
                findViewById(R.id.lay_pricing).setVisibility(View.GONE);
                findViewById(R.id.lay_choose_vehicle).setVisibility(View.VISIBLE);
                break;
            case R.id.btn_next: //lay pricing Next
                findViewById(R.id.lay_pricing).setVisibility(View.GONE);
                findViewById(R.id.lay_pkg_detail).setVisibility(View.VISIBLE);
                break;
            case R.id.iv_pkg_detail_back:
                findViewById(R.id.lay_pricing).setVisibility(View.VISIBLE);
                findViewById(R.id.lay_pkg_detail).setVisibility(View.GONE);
                break;
            case R.id.btn_pkg_dtl_next:
                findViewById(R.id.lay_pkg_detail).setVisibility(View.GONE);
                findViewById(R.id.lay_recv_detail).setVisibility(View.VISIBLE);
                break;
            //Reciver detail lay
            case R.id.iv_recev_detail_back:
                findViewById(R.id.lay_pkg_detail).setVisibility(View.VISIBLE);
                findViewById(R.id.lay_recv_detail).setVisibility(View.GONE);
                break;
            case R.id.btn_pay:
                findViewById(R.id.lay_recv_detail).setVisibility(View.GONE);
                findViewById(R.id.lay_pay_detail).setVisibility(View.VISIBLE);

                break;
            //Patment detail lay
            case R.id.iv_pay_back:
                findViewById(R.id.lay_pay_detail).setVisibility(View.GONE);
                findViewById(R.id.lay_recv_detail).setVisibility(View.VISIBLE);
                break;
            case R.id.btn_submit_req:
                reqAcceptDialog();

                break;
            case R.id.btn_cancel_req:
                cancelReqDialog();
                break;

//                Rating detail
            case R.id.iv_rating_back:
                findViewById(R.id.lay_rating).setVisibility(View.GONE);

                break;
        }
    }

    private void reqAcceptDialog() {
        ReqAcceptDialog reqAcceptDialog = new ReqAcceptDialog();
        reqAcceptDialog.show(getSupportFragmentManager(), "ReqAcceptDialog");
        reqAcceptDialog.setCancelable(false);

    }

    @Override
    public void onReqResponse(Boolean val) {
        if (val) {
            animate();
            resetLay();
            findViewById(R.id.lay_rating).setVisibility(View.VISIBLE);
        }

    }


    private void cancelReqDialog() {
        CancelReqDialog cancelReqDialog = new CancelReqDialog();
        cancelReqDialog.show(getSupportFragmentManager(), "CancelReqDialog");
        cancelReqDialog.setCancelable(false);

    }

    @Override
    public void onCancelReqdResponse(Boolean val) {

        if (val)
            resetLay();
    }

    private void resetLay() {

        findViewById(R.id.lay_choose_vehicle).setVisibility(View.GONE);
        findViewById(R.id.lay_pricing).setVisibility(View.GONE);
        findViewById(R.id.lay_pkg_detail).setVisibility(View.GONE);
        findViewById(R.id.lay_recv_detail).setVisibility(View.GONE);
        findViewById(R.id.lay_pay_detail).setVisibility(View.GONE);
    }


}