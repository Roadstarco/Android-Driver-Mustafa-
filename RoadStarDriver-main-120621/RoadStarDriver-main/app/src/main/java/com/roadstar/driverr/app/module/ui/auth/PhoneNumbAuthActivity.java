package com.roadstar.driverr.app.module.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;
import com.roadstar.driverr.R;
import com.roadstar.driverr.common.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

public class PhoneNumbAuthActivity extends BaseActivity implements View.OnClickListener {

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    ProgressBar progressBar;


    EditText etPhoneNumber;
    private CountryCodePicker countryCodePicker;

    private String phoneNumber;
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    public static PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    CountryCodePicker  countryCodePickeraa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_numb_auth);
        findView();
        init();
        initializeFirebase();
    }

    void findView() {
        etPhoneNumber = findViewById(R.id.et_phone_numb);
        countryCodePicker = findViewById(R.id.ccp);
        countryCodePicker.setCountryForPhoneCode(1);
        countryCodePicker.setCountryForNameCode("us");

        progressBar = findViewById(R.id.progressBar);

    }

    void init() {
        setActionBar(getString(R.string.register));
        bindClicklisteners();
        countryCodePickeraa = new CountryCodePicker(this);


        countryCodePickeraa.setDefaultCountryUsingNameCode("us");
    }

    private void bindClicklisteners() {
        findViewById(R.id.img_google).setOnClickListener(this);
        findViewById(R.id.img_fb).setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_next:
                Nextbtn();
                break;
            case R.id.img_google:
                break;
            case R.id.img_fb:
                break;
        }
    }


    public void Nextbtn() {
        phoneNumber = countryCodePicker.getSelectedCountryCodeWithPlus().toString() + etPhoneNumber.getText().toString();
        String ccode = countryCodePicker.getDefaultCountryCodeAsInt() + "";
//        Intent intent = new Intent(PhoneNumbAuthActivity.this, RegistrationActivity.class);
////        intent.putExtra("verificationId", verificationId);
//        intent.putExtra("phoneNumber", phoneNumber);
//        startActivity(PhoneNumbAuthActivity.this, intent);

        try {
            if (isValidPhoneNumber(phoneNumber)) {
                boolean status = validateUsing_libphonenumber(ccode, phoneNumber);
                if (status) {
                    progressBar.setVisibility(View.VISIBLE);
                    startPhoneNumberVerification();
                }
            }
        } catch (NumberFormatException e) {
            Toast.makeText(PhoneNumbAuthActivity.this, "Please input a valid number", Toast.LENGTH_LONG).show();
        }

    }

    private boolean isValidPhoneNumber(CharSequence phoneNumber) {
        if (!TextUtils.isEmpty(phoneNumber)) {
            return Patterns.PHONE.matcher(phoneNumber).matches();
        }
        return false;
    }

    private boolean validateUsing_libphonenumber(String countryCode, String phNumber) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.createInstance(PhoneNumbAuthActivity.this);
        String isoCode = phoneNumberUtil.getRegionCodeForCountryCode(Integer.parseInt(countryCode));
        Phonenumber.PhoneNumber phoneNumber = null;
        try {
            //phoneNumber = phoneNumberUtil.parse(phNumber, "IN");  //if you want to pass region code
            phoneNumber = phoneNumberUtil.parse(phNumber, isoCode);
        } catch (NumberParseException e) {
            System.err.println(e);
        }

        boolean isValid = phoneNumberUtil.isValidNumber(phoneNumber);
        if (isValid) {
            String internationalFormat = phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
            return true;
        } else {
            Toast.makeText(this, "Phone Number is Invalid " + phoneNumber, Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private void startPhoneNumberVerification() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        mVerificationInProgress = true;
    }

    private void initializeFirebase() {

        mAuth = FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                progressBar.setVisibility(View.GONE);
                mVerificationInProgress = false;

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Snackbar.make(findViewById(android.R.id.content), "Invalid phone number.",
                            Snackbar.LENGTH_SHORT).show();
                } else if (e instanceof FirebaseTooManyRequestsException) {

                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {

                progressBar.setVisibility(View.GONE);
                mVerificationId = verificationId;
                mResendToken = token;
                Intent intent = new Intent(PhoneNumbAuthActivity.this, VerifyOtpActivity.class);
                intent.putExtra("verificationId", verificationId);
                intent.putExtra("phoneNumber", phoneNumber);
                startActivity(PhoneNumbAuthActivity.this, intent);


            }
        };

    }
}