package com.roadstar.driverr.app.module.ui.auth;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.roadstar.driverr.R;
import com.roadstar.driverr.app.data.resp.DocumentsListResp;
import com.roadstar.driverr.app.data.resp.UploadDocumentsResp;
import com.roadstar.driverr.app.module.ui.auth.adapter.UploadDocumentsAdapter;
import com.roadstar.driverr.app.module.ui.main.MainActivity;
import com.roadstar.driverr.app.network.ApiInterface;
import com.roadstar.driverr.app.network.RetrofitClient;
import com.roadstar.driverr.common.base.BaseActivity;
import com.roadstar.driverr.common.utils.FileUtil;
import com.roadstar.driverr.common.utils.SharedHelper;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegDetailsCompany extends BaseActivity implements View.OnClickListener {


    private ProgressBar progressBar;
    private EditText etCompanyRegNo;
    private EditText etNumberOfVehicle;
    private EditText etCompanyName;
    Boolean isPermissionGivenAlready = false;
    private static final int SELECT_PHOTO = 100;
    private static final String TAG = "UploadDocument";

    int imagePosition;

    public static int deviceHeight;
    public static int deviceWidth;


    ArrayList<DocumentsListResp> documentsLists;
    private RecyclerView rvDocumentRecycler;
    private UploadDocumentsAdapter uploadDocumentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_details_company);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        deviceHeight = displayMetrics.heightPixels;
        deviceWidth = displayMetrics.widthPixels;


        findView();
        init();
    }


    void findView() {

        progressBar = findViewById(R.id.progressBar);
        etCompanyName = findViewById(R.id.etCompanyName);
        etCompanyRegNo = findFieldById(R.id.etCompanyRegistration);
        etNumberOfVehicle = findViewById(R.id.etNumberOfVehicle);
        rvDocumentRecycler = findViewById(R.id.rvDocumentRecycler);
    }

    void init() {
        setActionBar(getString(R.string.register));
        bindClicklisteners();
        getDocumentList();
    }

    private void bindClicklisteners() {
        findViewById(R.id.lay_signin).setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_next:

                if (uploadDoc()) {
                    findViewById(R.id.btn_next).setClickable(false);
                    requestUploadDocuments();
                } else {
                    Toast.makeText(RegDetailsCompany.this, "Please add all documents", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.lay_signin:
                startActivityWithNoHistory(this, SigninActivity.class);
                break;

        }
    }

    public void slectImage() {
        ImagePicker.Companion.with(this).crop().compress(1024).maxResultSize(1080, 1080).start();
    }

    public void goToImageIntent() {
        isPermissionGivenAlready = true;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PHOTO);
    }

    private void getDocumentList() {

        progressBar.setVisibility(View.VISIBLE);
        ApiInterface mApiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);

        Call<ArrayList<DocumentsListResp>> call = mApiInterface.getDocumentList();
        call.enqueue(new Callback<ArrayList<DocumentsListResp>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<DocumentsListResp>> call, @NonNull retrofit2.Response<ArrayList<DocumentsListResp>> response) {

                progressBar.setVisibility(View.GONE);
                if (response.body() != null) {
                    documentsLists = response.body();
                    setAdapter();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<DocumentsListResp>> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);

            }
        });
    }

    public void setAdapter() {

        uploadDocumentsAdapter = new UploadDocumentsAdapter(this, documentsLists);
        rvDocumentRecycler.setLayoutManager(new LinearLayoutManager(this));
        rvDocumentRecycler.setAdapter(uploadDocumentsAdapter);
        uploadDocumentsAdapter.setImageClickListner(position -> {

            imagePosition = position;
            slectImage();

        });


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    if (!isPermissionGivenAlready) {
                        goToImageIntent();
                    }
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == Activity.RESULT_OK) {
            try {

                //Image Uri will not be null for RESULT_OK
                Uri fileUri = data.getData();
                //imgProfile.setImageURI(fileUri);

                //You can get File object from intent
                File file = ImagePicker.Companion.getFile(data);
                String name = file.getName();


                //You can also get File Path from intent
                String filePath = ImagePicker.Companion.getFilePath(data);


                Bitmap bitmap = getBitmapFromUri(this, fileUri);
                updateAdapterData(bitmap, fileUri, name);


            } catch (IOException e) {
                e.printStackTrace();
            }


        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private static Bitmap getBitmapFromUri(@NonNull Context context, @NonNull Uri uri) throws IOException {
        Log.e(TAG, "getBitmapFromUri: Resize uri" + uri);
        ParcelFileDescriptor parcelFileDescriptor =
                context.getContentResolver().openFileDescriptor(uri, "r");
        assert parcelFileDescriptor != null;
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        Log.e(TAG, "getBitmapFromUri: Height" + deviceHeight);
        Log.e(TAG, "getBitmapFromUri: width" + deviceWidth);
        int maxSize = Math.min(deviceHeight, deviceWidth);
        if (image != null) {
            Log.e(TAG, "getBitmapFromUri: Width" + image.getWidth());
            Log.e(TAG, "getBitmapFromUri: Height" + image.getHeight());
            int inWidth = image.getWidth();
            int inHeight = image.getHeight();
            int outWidth;
            int outHeight;
            if (inWidth > inHeight) {
                outWidth = maxSize;
                outHeight = (inHeight * maxSize) / inWidth;
            } else {
                outHeight = maxSize;
                outWidth = (inWidth * maxSize) / inHeight;
            }
            return Bitmap.createScaledBitmap(image, outWidth, outHeight, false);
        } else {
            Toast.makeText(context, context.getString(R.string.valid_image), Toast.LENGTH_SHORT).show();
            return null;
        }

    }

    public void updateAdapterData(Bitmap bitmap, Uri uri, String name) {


        documentsLists.get(imagePosition).setBitmap(bitmap);
        documentsLists.get(imagePosition).setUri(uri);
        documentsLists.get(imagePosition).setFileName(name);
        uploadDocumentsAdapter.updateImage();

    }

    public boolean uploadDoc() {
        boolean uploadDoc = true;
        if (documentsLists != null) {
            for (int i = 0; i < documentsLists.size(); i++) {

                if (documentsLists.get(i).getUri() == null) {
                    uploadDoc = false;
                }
            }
        } else {
            uploadDoc = false;
        }
        return uploadDoc;
    }

    private void requestUploadDocuments() {

        progressBar.setVisibility(View.VISIBLE);

        //MultipartBody.Part[] surveyImagesParts = new MultipartBody.Part[documentsLists.size()];
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        builder.addFormDataPart("comp_name", etCompanyName.getText().toString());
        builder.addFormDataPart("comp_reg_no", etCompanyRegNo.getText().toString());
        builder.addFormDataPart("number_of_vehicle", etNumberOfVehicle.getText().toString());


        for (int index = 0; index < documentsLists.size(); index++) {

            try {


                builder.addFormDataPart("document_id[]", documentsLists.get(index).getId() + "");
                builder.addFormDataPart("document_name[]", documentsLists.get(index).getName() + "");

                //File file = new File(documentsLists.get(index).getUri().toString());
                File file = FileUtil.from(RegDetailsCompany.this, documentsLists.get(index).getUri());
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                //surveyImagesParts[index] = MultipartBody.Part.createFormData("picture[]", documentsLists.get(index).getId() + "", requestBody);
                builder.addFormDataPart("picture[]", file.getName(), requestBody);

            } catch (Exception e) {

                e.printStackTrace();
            }


        }
        MultipartBody requestBody = builder.build();

        final ApiInterface webServicesAPI = RetrofitClient.getRetrofitClient().create(ApiInterface.class);

        Call<UploadDocumentsResp> surveyResponse = webServicesAPI.uploadDocuments(requestBody);


        surveyResponse.enqueue(new Callback<UploadDocumentsResp>() {
            @Override
            public void onResponse(@NonNull Call<UploadDocumentsResp> call, @NonNull Response<UploadDocumentsResp> response) {
                progressBar.setVisibility(View.GONE);
                SharedHelper.putKey(RegDetailsCompany.this, "loggedIn", "true");
                GoToMainActivity();

            }

            @Override
            public void onFailure(@NonNull Call<UploadDocumentsResp> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);

                findViewById(R.id.btn_next).setClickable(true);
                Toast.makeText(RegDetailsCompany.this, "Please try again", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void GoToMainActivity() {
        Intent mainIntent = new Intent(RegDetailsCompany.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainIntent);
        RegDetailsCompany.this.finish();
    }

}