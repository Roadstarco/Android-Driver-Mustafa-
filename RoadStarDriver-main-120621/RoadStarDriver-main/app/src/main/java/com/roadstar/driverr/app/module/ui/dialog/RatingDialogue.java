package com.roadstar.driverr.app.module.ui.dialog;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import com.roadstar.driverr.R;
import com.roadstar.driverr.app.data.req.SubmitRatingReq;
import com.roadstar.driverr.app.data.resp.RequestsModel;
import com.roadstar.driverr.app.network.ApiInterface;
import com.roadstar.driverr.app.network.RetrofitClient;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class RatingDialogue {

    Activity activity;
    private LinearLayout ratingLayout;
    RequestsModel requestsModel;


    private ProgressBar progressBar;
    private TextView lblProviderName;
    private ImageView img05User;
    private RatingBar rat05UserRating;
    private EditText edt05Comment;
    private AppCompatButton btn_rate;

    public RatingDialogue(Activity context, RequestsModel model) {
        activity = context;
        requestsModel = model;
        setRatingLayout();
    }

    private void setRatingLayout() {
        ratingLayout = activity.findViewById(R.id.ll_05_contentLayer_feedback);
        lblProviderName = activity.findViewById(R.id.lblProviderName);
        img05User = activity.findViewById(R.id.img05User);
        rat05UserRating = activity.findViewById(R.id.rat05UserRating);
        edt05Comment = activity.findViewById(R.id.edt05Comment);
        btn_rate = activity.findViewById(R.id.btn_rate);
        progressBar = activity.findViewById(R.id.progressBar);


        ratingLayout.setVisibility(View.VISIBLE);
        btn_rate.setOnClickListener(v -> submitRatingRequest());

    }

    private void submitRatingRequest() {
        progressBar.setVisibility(View.VISIBLE);
        SubmitRatingReq submitRatingReq = new SubmitRatingReq();
        submitRatingReq.setComment(edt05Comment.getText().toString());
        submitRatingReq.setRating((int) rat05UserRating.getRating());

        ApiInterface mApiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);


        Call<String> call = mApiInterface.submitRating(requestsModel.getRequests().get(0).getRequestId(), submitRatingReq);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull retrofit2.Response<String> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    ratingLayout.setVisibility(View.GONE);
                    activity.finish();
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
