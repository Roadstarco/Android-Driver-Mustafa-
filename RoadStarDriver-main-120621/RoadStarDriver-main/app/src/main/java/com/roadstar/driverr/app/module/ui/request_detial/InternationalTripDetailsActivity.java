package com.roadstar.driverr.app.module.ui.request_detial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputEditText;
import com.roadstar.driverr.R;
import com.roadstar.driverr.app.data.req.SubmitRatingReq;
import com.roadstar.driverr.app.data.resp.RequestsModel;
import com.roadstar.driverr.app.module.ui.available_booking.AvailBookingActivity;
import com.roadstar.driverr.app.module.ui.request.model.BidModel;
import com.roadstar.driverr.app.module.ui.request.model.InternationTripModel;
import com.roadstar.driverr.app.module.ui.request.model.UpdateStatusModel;
import com.roadstar.driverr.app.module.ui.your_package.model.AcceptUserBid;
import com.roadstar.driverr.app.network.ApiInterface;
import com.roadstar.driverr.app.network.RetrofitClient;
import com.roadstar.driverr.common.utils.SharedHelper;
import com.roadstar.driverr.common.views.VizImageView;

import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.roadstar.driverr.app.module.ui.request.adapter.InternationScheduleRidesAdapter.getSingleItem;

public class InternationalTripDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean isCategorySelected = true;
    private AppCompatTextView title;
    private ProgressBar progressRating;
    private AppCompatButton adBid_Button,viewCounterOffer;
    public static InternationalTripDetailsActivity activity;

    private TextInputEditText etItemName,etDeliveryFrom,etDeliveryTo,etArrivalDate,etParcelDetails;
    private TextView itemType,itemSize,txt04BasePrice,txt04Total,txt04AmountToPaid;
    private VizImageView pic1,pic2,pic3;
    private AppCompatButton confirmPaymentMethodButton,btnRate;
    private LinearLayout ratingLayout,paymentLayout ;
    private BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior;
    private InternationTripModel internationTripModel;
    private String currentStatus;
    ProgressBar progressBar ;
    int buttonText = R.string.tab_when_arrived;
    private RatingBar rat05UserRating;
    private EditText edt05Comment;
    RequestsModel requestsModel;

    int pos =-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_international_trip_details);
        activity = this;
        intialize();
        setvalues();
        findViewById(R.id.iv_back).setOnClickListener(this);
        showRating();
    }

    private void intialize() {
        requestsModel = SharedHelper.getKey(getApplicationContext(), "currentRequest", RequestsModel.class);
        setRatingLayout();
        progressBar = findViewById(R.id.progressBar);
        btnRate = findViewById(R.id.btn_rate);
        paymentLayout = findViewById(R.id.ll_04_contentLayer_payment);
        ratingLayout = findViewById(R.id.ll_05_contentLayer_feedback);
        confirmPaymentMethodButton = findViewById(R.id.btnConfirmPayment);
        title = findViewById(R.id.tv_title);
        title.setText("International Request Detail");
        progressRating = findViewById(R.id.progressBarRating);

        adBid_Button = findViewById(R.id.btn_accept);
        viewCounterOffer = findViewById(R.id.view_counter_details);
        etItemName = findViewById(R.id.et_item_name);
        etDeliveryFrom = findViewById(R.id.et_deliveryFrom);
        etDeliveryTo = findViewById(R.id.et_deliveryTo);
        etArrivalDate = findViewById(R.id.arrivalDate);
        etParcelDetails = findViewById(R.id.parcel_detail);

        itemType = findViewById(R.id.tv_product_type);
        itemSize = findViewById(R.id.tv_product_size);
        txt04BasePrice = findViewById(R.id.txt04BasePrice);
        txt04Total = findViewById(R.id.txt04Total);
        txt04AmountToPaid = findViewById(R.id.txt04AmountToPaid);

        pic1 = findViewById(R.id.iv_attach_1);
        pic2 = findViewById(R.id.iv_attach_2);
        pic3 = findViewById(R.id.iv_attach_3);

        ConstraintLayout bottomSheet = findViewById(R.id.bottomSheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:

                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    }
                    break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        confirmPaymentMethodButton.setOnClickListener(this);
        btnRate.setOnClickListener(this);

    }

    private void setvalues() {

        if (getIntent().hasExtra("pos")){

            try {

                if (!getIntent().getBooleanExtra("bids?",false)){
                    internationTripModel = getSingleItem(getIntent().getIntExtra("pos",-1));
                }else {
                    internationTripModel = requestsModel.getUser_trips().get(getIntent().getIntExtra("pos",-1));
                    pos = getIntent().getIntExtra("pos",-1);
                }

            /*if (internationTripModel.getCreated_by().equals("provider")){
                pic1.setImage(getSingle_item(pos).getPicture1());
                pic2.setImage(getSingle_item(pos).getPicture2());
                pic3.setImage(getSingle_item(pos).getPicture3());
            }else */{

                    pic1.setImage(internationTripModel.getPicture1());
                    pic2.setImage(internationTripModel.getPicture2());
                    pic3.setImage(internationTripModel.getPicture3());
                }


                etItemName.setText(internationTripModel.getItem());
                etDeliveryFrom.setText(internationTripModel.getTripfrom());
                etDeliveryTo.setText(internationTripModel.getTripto());
                etArrivalDate.setText(internationTripModel.getArrival_date());
                etParcelDetails.setText(internationTripModel.getOther_information());

                itemType.setText(internationTripModel.getItem_type());
                itemSize.setText(internationTripModel.getItem_size());

                String Status = internationTripModel.getTrip_status();

                switch (internationTripModel.getTrip_status()) {
                    case "PENDING":
                        adBid_Button.setText(R.string.ad_bid);
                        break;
                    case "SCHEDULED":
                        adBid_Button.setText(R.string.start_process);
                        break;
                    case "STARTED":
                        adBid_Button.setText(R.string.tab_when_arrived);
                        break;
                    case "ARRIVED":
                        adBid_Button.setText(R.string.picked_up_package);
                        break;
                    case "PICKEDUP":
                        adBid_Button.setText(R.string.tab_when_delivered);
                        break;
                    case "DROPPED":

                        adBid_Button.setText(R.string.drop_location);
                        showPaymentBottomSheet();
                        break;
                    case "PAYMENT":
                        showRating();
                        adBid_Button.setText(R.string.rate_the_servis);
                        break;

                    case "COMPLETED":
                        adBid_Button.setVisibility(View.GONE);

                        break;
                }

                if (internationTripModel.getTrip_status().toLowerCase().equals("pending") && pos != -1 && requestsModel.getUser_trips().get(pos).getBid_details().getIs_counter() == 1){
                    viewCounterOffer.setVisibility(View.VISIBLE);
                }else{
                    viewCounterOffer.setVisibility(View.GONE);
                }

                if (pos != -1 && requestsModel.getUser_trips().get(pos).getBid_details().getId() != null){
                    adBid_Button.setText(R.string.view_bids);
                }

            }catch (Exception e){

            }
        }
    }

    private void showRating() {
        paymentLayout.setVisibility(View.GONE);
        ratingLayout.setVisibility(View.VISIBLE);
    }


    public void onClick(View view){
        int clickedView = view.getId();
        switch (clickedView){
            case R.id.iv_back:
                finish();
                break;
            case R.id.view_counter_details:
                if (internationTripModel.getTrip_status().toLowerCase().equals("pending") &&
                        pos != -1 && requestsModel.getUser_trips().get(pos).getBid_details().getIs_counter() == 1)

                {

                    int counterAmount = requestsModel.getUser_trips().get(pos).getBid_details().getCounter_amount();
                    int bidID = requestsModel.getUser_trips().get(pos).getBid_details().getId();
                    int trip_id = requestsModel.getUser_trips().get(pos).getBid_details().getTrip_id();

                    showCounterOffer(counterAmount,bidID,trip_id);

                }
                break;
            case R.id.btn_accept:

                if (adBid_Button.getText().toString().toLowerCase().equals("Processing...")){
                }else if (adBid_Button.getText().toString().toLowerCase().equals("add bid")){
                    showAcceptDialog();
                }else if (adBid_Button.getText().toString().toLowerCase().equals("view bid")){
                    showBidDetail(internationTripModel.getBid_details().getAmount(),
                            internationTripModel.getBid_details().getTraveller_response(),
                            internationTripModel.getBid_details().getService_type());

                }else if (adBid_Button.getText().equals("Start Process")){
                    buttonText = R.string.tab_when_arrived;
                    currentStatus = "STARTED";
//                    if(internationTripModel.getBid_details().getService_type()

                    if(internationTripModel.getService_type()!=null && internationTripModel.getService_type().equalsIgnoreCase("by air")){
                        Intent intent = new Intent(getApplicationContext(),AvailBookingActivity.class);
                        intent.putExtra("id",internationTripModel.getId());
                        intent.putExtra("status",currentStatus);
                        startActivity(intent);

                    }else {
                        adBid_Button.setText("Processing...");
                        updatePackageStatus(currentStatus);
                    }
                }else if (adBid_Button.getText().equals("Tap when arrived")){
                    buttonText = R.string.picked_up_package;
                    currentStatus = "ARRIVED";

                    adBid_Button.setText("Processing...");
                    updatePackageStatus(currentStatus);
                }else if (adBid_Button.getText().equals("Pickup package")){

                    buttonText = R.string.tab_when_delivered;
                    currentStatus = "PICKEDUP";

                    adBid_Button.setText("Processing...");
                    updatePackageStatus(currentStatus);
                }else if (adBid_Button.getText().equals("Tap when delivered")){

                    currentStatus = "DROPPED";

                    adBid_Button.setText("Processing...");
                    updatePackageStatus(currentStatus);

                }
                break;
            case R.id.btnConfirmPayment:

                currentStatus = "COMPLETED";
                updatePackageStatus(currentStatus);

                break;
            case R.id.btn_rate:
                submitRatingRequest();
                break;
        }
    }

    private void showBidDetail(int bid_amount,String bid_message,String service_type) {

        AlertDialog.Builder builder = new AlertDialog.Builder(InternationalTripDetailsActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.accept_dialog_layout, viewGroup, false);

        TextView title = dialogView.findViewById(R.id.title);
        ProgressBar progressBarInDialog = dialogView.findViewById(R.id.progressBarInDialog);
        EditText amount = dialogView.findViewById(R.id.et_bid_amount);
        EditText message = dialogView.findViewById(R.id.et_message);
        TextView serviceType = dialogView.findViewById(R.id.tv_service_type);
        AppCompatButton sendButton = dialogView.findViewById(R.id.btn_accept);
        AppCompatButton cancel = dialogView.findViewById(R.id.cancel);


        title.setText("Your Submitted Request");
        amount.setFocusable(false);
        amount.setClickable(false);
        amount.setText(String.valueOf(bid_amount));

        message.setFocusable(false);
        message.setFocusable(false);
        message.setClickable(false);
        message.setText(bid_message);

        serviceType.setText(service_type);


        sendButton.setVisibility(View.GONE);

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(amount.getText())){
                    amount.setError("Add bid amount");
                }
                else if (TextUtils.isEmpty(serviceType.getText())){
                    serviceType.setError("Add service type");
                }else {
                    callSendRequestApi(progressBarInDialog, internationTripModel.getId(),serviceType.getText().toString(),message.getText().toString(),amount.getText().toString());
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    private void showCounterOffer(int counterAmount,int bid_id,int trip_id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.counter_bid_layout, null, false);

        ProgressBar progressBarInDialog = dialogView.findViewById(R.id.progressBarInDialog);
        TextView title = dialogView.findViewById(R.id.titleOffers);
        TextView closeDialog = dialogView.findViewById(R.id.closeDialog);
        closeDialog.setVisibility(View.VISIBLE);
        title.setText(R.string.counter_offer_detail);
        EditText amount = dialogView.findViewById(R.id.et_bid_amount);
        amount.setText(String.valueOf(counterAmount));
        amount.setFocusable(false);
        amount.setClickable(false);
        amount.setEnabled(false);
        AppCompatButton sendButton = dialogView.findViewById(R.id.btn_accept);
        sendButton.setText(R.string.Accept);
        AppCompatButton cancel = dialogView.findViewById(R.id.cancel);
        cancel.setText(R.string.Reject);

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(amount.getText())){
                    amount.setError("Add bid amount");
                }else {
                    progressBarInDialog.setVisibility(View.VISIBLE);
                    AcceptCounterOffer(progressBarInDialog,bid_id,trip_id,true,alertDialog);
                }
            }
        });

        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarInDialog.setVisibility(View.VISIBLE);
                AcceptCounterOffer(progressBarInDialog,bid_id,trip_id,false,alertDialog);
            }
        });

    }

    private void AcceptCounterOffer(ProgressBar progressBarInDialog, int bid_id, int trip_id, boolean isAccept, AlertDialog alertDialog) {

        AcceptUserBid model = new AcceptUserBid();
        //model.setTrip_id(trip_id);
        model.setBid_id(bid_id);
        model.setTrip_id(trip_id);

        /*Create handle for the RetrofitInstance interface*/
        ApiInterface service = RetrofitClient.getRetrofitClient().create(ApiInterface.class);
        Call<ResponseBody> call;

        if (isAccept)
            call = service.AcceptCounterOffer(model);
        else
            call = service.retjectCounterOffer(model);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                alertDialog.dismiss();
                progressBarInDialog.setVisibility(View.GONE);

                if (response.code() == 200){
                    if (isAccept)
                        Toast.makeText(InternationalTripDetailsActivity.this, "counter offer send !", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(InternationalTripDetailsActivity.this, "counter offer Rejected !", Toast.LENGTH_SHORT).show();

                    finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                alertDialog.dismiss();
                progressBarInDialog.setVisibility(View.GONE);
                Toast.makeText(InternationalTripDetailsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRatingLayout() {
        rat05UserRating = findViewById(R.id.rat05UserRating);
        edt05Comment = findViewById(R.id.edt05Comment);
    }

    private void submitRatingRequest() {
        progressRating.setVisibility(View.VISIBLE);
        SubmitRatingReq submitRatingReq = new SubmitRatingReq();
        submitRatingReq.setComment(edt05Comment.getText().toString());
        submitRatingReq.setRating((int) rat05UserRating.getRating());
        submitRatingReq.setTrip_id(internationTripModel.getId().toString());

        ApiInterface mApiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);

        Call<ResponseBody> call = mApiInterface.submitRatingInternational(submitRatingReq);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {
                progressRating.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {

                    ratingLayout.setVisibility(View.GONE);
                    finish();

                }else
                    Toast.makeText(InternationalTripDetailsActivity.this, "something went wrong try again", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                progressRating.setVisibility(View.GONE);
                Log.d("onFailure", Objects.requireNonNull(t.getMessage()));

            }

        });
    }

    private void showPaymentBottomSheet() {

        paymentLayout.setVisibility(View.VISIBLE);
        ratingLayout.setVisibility(View.GONE);

        txt04BasePrice.setText(internationTripModel.getTrip_amount());
        txt04Total.setText(internationTripModel.getTrip_amount());
        txt04AmountToPaid.setText(internationTripModel.getTrip_amount());

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public void onBackPressed() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        else
            super.onBackPressed();
    }

    private void updatePackageStatus(String currentButtonStatus) {

        progressBar.setVisibility(View.VISIBLE);
        UpdateStatusModel updatePackageStatusReq = new UpdateStatusModel();

        updatePackageStatusReq.setTrip_id(internationTripModel.getId());
        updatePackageStatusReq.setTrip_status(currentButtonStatus);

        ApiInterface mApiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);


        Call<ResponseBody> call = mApiInterface.updateStatus(updatePackageStatusReq);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {


                progressBar.setVisibility(View.GONE);

                if (response.code() == 200) {

                    if (currentStatus.equals("DROPPED")){
                        showPaymentBottomSheet();
                    }else if (currentStatus.equals("COMPLETED")){
                        showRating();
                    }
                    adBid_Button.setText(buttonText);
                    Log.d("response", response.body().toString());
                }

            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

                progressBar.setVisibility(View.GONE);

                Log.d("onFailure", Objects.requireNonNull(t.getMessage()));

            }

        });
    }

    private void showAcceptDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(InternationalTripDetailsActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.accept_dialog_layout, viewGroup, false);

        ProgressBar progressBarInDialog = dialogView.findViewById(R.id.progressBarInDialog);
        EditText amount = dialogView.findViewById(R.id.et_bid_amount);
        EditText message = dialogView.findViewById(R.id.et_message);
        TextView serviceType = dialogView.findViewById(R.id.tv_service_type);
        AppCompatButton sendButton = dialogView.findViewById(R.id.btn_accept);
        AppCompatButton cancel = dialogView.findViewById(R.id.cancel);


        setSpinners(dialogView,serviceType);

        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        sendButton.setOnClickListener(v -> {
            if (TextUtils.isEmpty(amount.getText())){
                amount.setError("Add bid amount");
            }
            else if (TextUtils.isEmpty(serviceType.getText())){
                serviceType.setError("Add service type");
            }else {
                callSendRequestApi(progressBarInDialog,internationTripModel.getId(),serviceType.getText().toString(),message.getText().toString(),amount.getText().toString());
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               alertDialog.dismiss();
            }
        });

    }

    public void setSpinners(View view,TextView textView) {
        //Spinner Category
        AppCompatSpinner spinnerCategory = (AppCompatSpinner) view.findViewById(R.id.spinner_service_type);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Your code here
                if (isCategorySelected) {
                    isCategorySelected = !isCategorySelected;
                } else {
                    textView.setText(spinnerCategory.getSelectedItem().toString());
                    spinnerCategory.setSelection(0);
                    isCategorySelected = true;
                }

            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    public void callSendRequestApi(ProgressBar progressBarInDialog, Integer id, String s, String message, String amount) {

        try {
            progressBarInDialog.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            BidModel model = new BidModel();
            model.setTrip_id(String.valueOf(id));
            model.setService_type(s);
            model.setTraveller_response(message);
            model.setAmount(amount);


            ApiInterface apiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);
            Call<ResponseBody> call = apiInterface.bidOnUserRequest( model);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                    progressBarInDialog.setVisibility(View.GONE);
                    if (response.code() == 200) {
                        Toast.makeText(InternationalTripDetailsActivity.this, "Your request is sent successfully", Toast.LENGTH_LONG).show();
                        finish();

                    } else
                        Toast.makeText(InternationalTripDetailsActivity.this, "job already exists", Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    progressBarInDialog.setVisibility(View.GONE);
                    Toast.makeText(InternationalTripDetailsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            progressBarInDialog.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

    private void showDialogProgressbar() {
        progressRating.setVisibility(View.VISIBLE);
    }

    MultipartBody getJobReqParam(String trip_id,String serviceType,String travellerResponce,String amount) {

        MultipartBody.Builder builder = new MultipartBody.Builder();

        try {

            builder.setType(MultipartBody.FORM);
            builder.addFormDataPart("trip_id", trip_id);
            builder.addFormDataPart("service_type", serviceType);
            builder.addFormDataPart("traveller_response", travellerResponce);
            builder.addFormDataPart("amount", amount);


        } catch (Exception e) {
            e.printStackTrace();

        }
        return builder.build();

    }

}