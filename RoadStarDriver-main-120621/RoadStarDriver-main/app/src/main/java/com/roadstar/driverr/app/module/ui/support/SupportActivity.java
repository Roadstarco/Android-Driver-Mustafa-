package com.roadstar.driverr.app.module.ui.support;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.roadstar.driverr.R;
import com.roadstar.driverr.app.data.models.support.SupportMessageModel;
import com.roadstar.driverr.app.network.ApiInterface;
import com.roadstar.driverr.app.network.RetrofitClient;
import com.roadstar.driverr.common.base.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupportActivity extends BaseActivity implements View.OnClickListener {

    private ProgressBar progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        init();
    }

    void init() {
        setActionBar(getString(R.string.support));
        progressDoalog = findViewById(R.id.progressBar);
        bindClicklisteners();
    }

    private void bindClicklisteners() {

        findViewById(R.id.send_message).setOnClickListener(this);
        findViewById(R.id.message_layout).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.send_message:

                if (TextUtils.isEmpty(findEditTextById(R.id.et_subject).getText()) || TextUtils.isEmpty(findEditTextById(R.id.et_message).getText()))
                    Toast.makeText(this, "fill all values", Toast.LENGTH_SHORT).show();
                else{
                    callSendMessageApi();
                }


                break;
            case R.id.message_layout:
                findEditTextById(R.id.et_message).requestFocus();
                break;

        }
    }

    private void callSendMessageApi() {

        progressDoalog.setVisibility(View.VISIBLE);

        SupportMessageModel supportMessageModel  = new SupportMessageModel();
        supportMessageModel.setSubject(Objects.requireNonNull(findEditTextById(R.id.et_subject).getText()).toString());
        supportMessageModel.setMessage(Objects.requireNonNull(findEditTextById(R.id.et_message).getText()).toString());


        /*Create handle for the RetrofitInstance interface*/
        ApiInterface service = RetrofitClient.getRetrofitClient().create(ApiInterface.class);
        Call<ResponseBody> call = service.sendSupportMessage(supportMessageModel);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                progressDoalog.setVisibility(View.GONE);

                if (response.code() == 200){
                    resetScreen(findViewById(R.id.layout_main));
                    showSuccessDialog();
                }
                else if (response.code() == 500) {

                    Toast.makeText(SupportActivity.this, R.string.server_down, Toast.LENGTH_SHORT).show();

                }else {

                    Toast.makeText(SupportActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                progressDoalog.setVisibility(View.GONE);
                Toast.makeText(SupportActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showSuccessDialog() {

        AlertDialog alertDialog = new AlertDialog.Builder(this)
//set icon
                .setIcon(R.drawable.ic_baseline_playlist_add_check_24)
//set title
                .setTitle("Message Send")
//set message
                .setMessage("Support Email Send successfully. Our team will contact you soon !")

                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .show();

    }

}