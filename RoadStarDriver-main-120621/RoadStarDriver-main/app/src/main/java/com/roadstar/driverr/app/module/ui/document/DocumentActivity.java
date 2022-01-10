package com.roadstar.driverr.app.module.ui.document;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.roadstar.driverr.R;
import com.roadstar.driverr.app.data.models.documentModel;
import com.roadstar.driverr.app.data.models.support.SupportMessageModel;
import com.roadstar.driverr.app.network.ApiInterface;
import com.roadstar.driverr.app.network.RetrofitClient;
import com.roadstar.driverr.common.base.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocumentActivity extends BaseActivity implements View.OnClickListener{

    private ProgressBar progressDoalog;
    ArrayList<documentModel> documentsLists;
    private RecyclerView rvDocumentRecycler;
    private documentAdapter documentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        init();

        getDocumentList();
    }

    private void getDocumentList() {

        progressDoalog.setVisibility(View.VISIBLE);
        ApiInterface mApiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);

        Call<ArrayList<documentModel>> call = mApiInterface.getDocumentListAndDisplay();
        call.enqueue(new Callback<ArrayList<documentModel>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<documentModel>> call, @NonNull retrofit2.Response<ArrayList<documentModel>> response) {

                progressDoalog.setVisibility(View.GONE);
                if (response.body() != null) {
                    documentsLists = response.body();
                    setAdapter();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<documentModel>> call, @NonNull Throwable t) {
                progressDoalog.setVisibility(View.GONE);

            }
        });
    }

    public void setAdapter() {

        documentListAdapter = new documentAdapter(this, documentsLists);
        rvDocumentRecycler.setLayoutManager(new LinearLayoutManager(this));
        rvDocumentRecycler.setAdapter(documentListAdapter);

    }

    void init() {
        setActionBar(getString(R.string.documents));
        progressDoalog = findViewById(R.id.progressBar);
        rvDocumentRecycler = findViewById(R.id.rvDocumentRecycler);
        bindClicklisteners();
    }

    private void bindClicklisteners() {

        findViewById(R.id.img_view_vi).setOnClickListener(this);
        findViewById(R.id.img_edit_vi).setOnClickListener(this);
        findViewById(R.id.img_view_license).setOnClickListener(this);
        findViewById(R.id.img_edit_license).setOnClickListener(this);
        findViewById(R.id.img_view_insu).setOnClickListener(this);
        findViewById(R.id.img_edit_insu).setOnClickListener(this);
        findViewById(R.id.img_view_residance).setOnClickListener(this);
        findViewById(R.id.img_edit_residance).setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.img_view_vi:
                break;
            case R.id.img_edit_vi:
                break;
            case R.id.img_view_license:
                break;
            case R.id.img_edit_license:
                break;
            case R.id.img_view_insu:
                break;
            case R.id.img_edit_insu:
                break;
            case R.id.img_view_residance:
                break;
            case R.id.img_edit_residance:
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
                    Toast.makeText(DocumentActivity.this, "Support Email Send successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else if (response.code() == 500) {

                    Toast.makeText(DocumentActivity.this, R.string.server_down, Toast.LENGTH_SHORT).show();

                }else {

                    Toast.makeText(DocumentActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                progressDoalog.setVisibility(View.GONE);
                Toast.makeText(DocumentActivity.this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
            }
        });
    }

}