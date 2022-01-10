package com.roadstar.driverr.app.module.ui.PostTripActivtiy;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputEditText;
import com.roadstar.driverr.R;
import com.roadstar.driverr.app.network.ApiInterface;
import com.roadstar.driverr.app.network.RetrofitClient;
import com.roadstar.driverr.common.utils.AppUtils;
import com.roadstar.driverr.common.views.VizImageView;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.theartofdev.edmodo.cropper.CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE;

public class PostATrip extends AppCompatActivity implements View.OnClickListener {

    private final int AUTOCOMPLETE_REQUEST_CODE =2587;
    final Calendar myCalendar = Calendar.getInstance();

    private TextView tvProductType, tvProductSize;
    private TextInputEditText arrivalDate,returnDate,etDeliveryFrom,etDeliveryTo;
    private boolean isCategorySelected = true;
    private boolean isProductSelected = true;

    public static final int REQUEST_STORAGE_READ_WRITE_ACCESS_PERMISSION = 101;
    public static boolean isSheetOpen = false;

    private AppCompatButton postButton;
    private ProgressBar progressDoalog;
    public static LinearLayout inProgress;

    public static BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior;
    private ConstraintLayout bottomSheet;

    private EditText recieverName ,recieverPhone;

    int attachmentID;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private boolean cardIsAvailable = false;
    private TextInputEditText etParcelDetail;
    private AppCompatButton makeRequest;

    AppCompatImageView backBtn;
    AppCompatTextView title;
    private boolean isTripFromField = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_a_trip);


        initi();
        setSpinners();
    }

    private void initi() {



        if (!Places.isInitialized()) {
            Places.initialize(this, getString(R.string.api_key));
        }

        PlacesClient placesClient = Places.createClient(getApplicationContext());


        backBtn = findViewById(R.id.iv_back);
        title = findViewById(R.id.tv_title);

        title.setText("Internation Delivery");
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        
        postButton = findViewById(R.id.btn_pkg_dtl_next);
        arrivalDate = findViewById(R.id.arrivalDate);
        returnDate = findViewById(R.id.returnDate);
        progressDoalog = findViewById(R.id.progressBar);
        inProgress = findViewById(R.id.inProgressLayout);
        tvProductSize = findViewById(R.id.tv_product_size);
        etDeliveryFrom = findViewById(R.id.et_deliveryFrom);
        etDeliveryTo = findViewById(R.id.et_deliveryTo);
        tvProductType = findViewById(R.id.tv_product_type);
        etParcelDetail = findViewById(R.id.parcel_detail);
        etParcelDetail.setSelection(0);



        etDeliveryFrom.setFocusable(false);
        etDeliveryFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadNearbyPlaces(true);
            }
        });

        etDeliveryTo.setFocusable(false);
        etDeliveryTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadNearbyPlaces(false);
            }
        });


        inProgress.setOnClickListener(this);
        arrivalDate.setOnClickListener(this);
        returnDate.setOnClickListener(this);
        postButton.setOnClickListener(this);

    }


    private void loadNearbyPlaces(boolean caller) {

        isTripFromField = caller;

        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

    }


    public void setSpinners() {
        //Spinner Category
        AppCompatSpinner spinnerCategory = (AppCompatSpinner) findViewById(R.id.spinner_product_type);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Your code here
                if (isCategorySelected) {
                    isCategorySelected = !isCategorySelected;
                } else {
                    tvProductType.setText(spinnerCategory.getSelectedItem().toString());
                    spinnerCategory.setSelection(0);
                    isCategorySelected = true;
                }

            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        //Spinner Product Type
        Spinner spinnerProduct = (Spinner) findViewById(R.id.spinner_product_size);

        spinnerProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Your code here

                if (isProductSelected) {
                    isProductSelected = !isProductSelected;
                } else {
                    tvProductSize.setText(spinnerProduct.getSelectedItem().toString());
                    spinnerProduct.setSelection(0);
                    isProductSelected = true;
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.iv_attach_1:
                checkStoragePermission(R.id.iv_attach_1);

                break;
            case R.id.iv_attach_2:
                checkStoragePermission(R.id.iv_attach_2);

                break;
            case R.id.iv_attach_3:
                checkStoragePermission(R.id.iv_attach_3);

                break;

            case R.id.btn_pkg_dtl_next:

                if (isPackageParamValid()){
                    callSendRequestApi();
                }
                break;
            case R.id.inProgressLayout:
                break;
            case R.id.arrivalDate:
                dateAndTimePicker(arrivalDate);
                break;
            case R.id.returnDate:
                dateAndTimePicker(returnDate);
                break;

            case R.id.iv_recev_detail_back:

                inProgress.setVisibility(View.GONE);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

                break;
            case R.id.btn_pay:

                if (TextUtils.isEmpty(recieverName.getText())){
                    recieverName.setError("Add Receiver name");
                }else if (TextUtils.isEmpty(recieverPhone.getText())){
                    recieverPhone.setError("Add Receiver name");
                }else /*if (UserManager.isCardAdded()){
                    Intent cardDetail = new Intent(getActivity(), AddCard.class);
                    cardDetail.putExtra(PreferenceUtils.ISFROMPOSTTRIP,true);
                    startActivity(cardDetail);

                }else*/ {
                    callSendRequestApi();
                }

                break;


        }
    }

    private void updateLabel(EditText editText) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editText.setText( sdf.format(myCalendar.getTime()));
        editText.setFocusable(false);
    }


    public void dateAndTimePicker(EditText editText){


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(editText);
            }

        };

        new DatePickerDialog(PostATrip.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();


    }

    MultipartBody getJobReqParam() {

        MultipartBody.Builder builder = new MultipartBody.Builder();

        try {

            builder.setType(MultipartBody.FORM);
            builder.addFormDataPart("tripfrom", AppUtils.getEditTextString(findViewById((R.id.et_deliveryFrom))));
            builder.addFormDataPart("tripto", AppUtils.getEditTextString(findViewById((R.id.et_deliveryTo))));
            builder.addFormDataPart("service_type", tvProductType.getText().toString());
            builder.addFormDataPart("item_size", tvProductSize.getText().toString());
            builder.addFormDataPart("arrival_date", AppUtils.getEditTextString(findViewById((R.id.arrivalDate))));
            builder.addFormDataPart("return_date", AppUtils.getEditTextString(findViewById((R.id.returnDate))));
            builder.addFormDataPart("other_information", AppUtils.getEditTextString(findViewById(R.id.parcel_detail)));

        } catch (Exception e) {
            e.printStackTrace();

        }
        return builder.build();

    }

    public void callSendRequestApi() {
        progressDoalog.setVisibility(View.VISIBLE);
        /*Create handle for the RetrofitInstance interface*/
        try {


            ApiInterface apiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);
            Call<ResponseBody> call = apiInterface.postTrip(getJobReqParam());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                    progressDoalog.setVisibility(View.GONE);

                    //SendReqRes sendReqRes = (SendReqRes) new Gson().fromJson(String.valueOf(response.body()), SendReqRes.class);

                    if (response.code() == 200) {/*
                        UserManager.setRideInprogress(true);*/
                        Toast.makeText(PostATrip.this, "Your request is sent successfully", Toast.LENGTH_LONG).show();
                        finish();

                    } else
                        Toast.makeText(PostATrip.this, R.string.job_already_in_progress, Toast.LENGTH_LONG).show();

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressDoalog.setVisibility(View.GONE);
                    Toast.makeText(PostATrip.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            progressDoalog.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }

    public void checkStoragePermission(int attachmentID) {
        this.attachmentID = attachmentID;
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PostATrip.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_READ_WRITE_ACCESS_PERMISSION);
        } else if (ActivityCompat.checkSelfPermission(PostATrip.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PostATrip.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_READ_WRITE_ACCESS_PERMISSION);

        } else
            addStoreImage();
    }

    //Add Image from gallery
    public void addStoreImage() {
        if (CropImage.isExplicitCameraPermissionRequired(getApplicationContext())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE);
            }
        } else {
            CropImage.startPickImageActivity(PostATrip.this);

        }
    }


    private void startCropImageActivity(Uri imageUri) {

        CropImage.activity(imageUri)
                .start(PostATrip.this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //For Images
        if (requestCode == PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(getApplicationContext(), data);
            // For API >= 23 we need to check specifically that we have permissions to read external storage.

            // no permissions required or already granted, can start crop image activity
            startCropImageActivity(imageUri);

        }
        //After Image Cropped
        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {

                File wallpaper_file = new File(result.getUri().getPath());
                Uri imageContentUri = getImageContentUri(getApplicationContext(), wallpaper_file.getAbsolutePath());

                setImage(imageContentUri);
//                imageFilePath = result.getUri();
//
//                uploadImage();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        else if (requestCode == AUTOCOMPLETE_REQUEST_CODE){
            Log.d("place name ",data.toString());

            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);

                if (isTripFromField)
                    etDeliveryFrom.setText(place.getName());
                else {
                    etDeliveryTo.setText(place.getName());
                }

                Log.d("place name ",place.getName());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);

                Log.d("place name ",status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;

        }



    }
    public VizImageView findVizImageView(@IdRes int viewId) {
        return ((VizImageView) findViewById(viewId));
    }

    private void setImage(Uri imageContentUri) {

        //findVizImageView(attachmentID).createAndSetBitmap(imageContentUri);
        findVizImageView(attachmentID).setImage(String.valueOf(imageContentUri));

    }


    public static Uri getImageContentUri(Context context, String absPath) {

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                , new String[]{MediaStore.Images.Media._ID}
                , MediaStore.Images.Media.DATA + "=? "
                , new String[]{absPath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Integer.toString(id));

        } else if (!absPath.isEmpty()) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, absPath);
            return context.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } else {
            return null;
        }
    }

    public boolean isPackageParamValid() {
        boolean valid = true;
        if (!AppUtils.isNotFieldEmpty(etDeliveryFrom)) {
            etDeliveryFrom.setError(getString(R.string.field_empty));
            valid = false;
            return valid;
        }
        etDeliveryFrom.setError(null);

        if (!AppUtils.isNotFieldEmpty(etDeliveryTo)){
            etDeliveryTo.setError(getString(R.string.field_empty));
            valid = false;
            return valid;
        }
        etDeliveryTo.setError(null);



        if (!AppUtils.isNotFieldEmpty(arrivalDate)) {
            arrivalDate.setError(getString(R.string.field_empty));
            valid = false;
            return valid;
        }
        arrivalDate.setError(null);

        if (!AppUtils.isNotFieldEmpty(returnDate)) {
            returnDate.setError(getString(R.string.field_empty));
            valid = false;
            return valid;
        }
        returnDate.setError(null);



        if (tvProductSize.getText().toString().equals("")) //Check Product weight
        {
            Toast.makeText(getApplicationContext(), "please Select item size", Toast.LENGTH_SHORT).show();
            valid = false;
            return valid;
        }

        if (tvProductType.getText().toString().equals("")) //Check Product weight
        {
            Toast.makeText(getApplicationContext(), "please Select item type", Toast.LENGTH_SHORT).show();
            valid = false;
            return valid;
        }

        AppUtils.hideSoftKeyboard(PostATrip.this);//Hide keyboard
        return valid;
    }


}