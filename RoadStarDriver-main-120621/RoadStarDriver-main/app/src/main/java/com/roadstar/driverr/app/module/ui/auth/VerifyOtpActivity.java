package com.roadstar.driverr.app.module.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.broooapps.otpedittext2.OnCompleteListenerOtp;
import com.google.android.gms.tasks.OnCompleteListener;

import com.broooapps.otpedittext2.OtpEditText;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.roadstar.driverr.R;
import com.roadstar.driverr.common.base.BaseActivity;
import com.roadstar.driverr.common.utils.AppUtils;

import java.util.concurrent.TimeUnit;

public class VerifyOtpActivity extends BaseActivity implements View.OnClickListener {
    OtpEditText otpEditText;
    private FirebaseAuth mAuth;
    private String verificationId;
    private String phoneNumber;
    private ProgressBar progressBar;
    private TextView tv_code_numb;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        init();
        mAuth = FirebaseAuth.getInstance();

    }

    void init() {
        setActionBar(getString(R.string.verify_otp));
        bindClicklisteners();
        progressBar = findViewById(R.id.progressBar);
        tv_code_numb = findViewById(R.id.tv_code_numb);

        if (getIntent() != null && getIntent().hasExtra("verificationId")) {
            verificationId = getIntent().getStringExtra("verificationId");
            phoneNumber = getIntent().getStringExtra("phoneNumber");

            tv_code_numb.setText("Enter the 6 digit code send to you\nAT " + phoneNumber);
        }
//        Intent intent = new Intent(VerifyOtpActivity.this, RegistrationActivity.class);
//        intent.putExtra("phoneNumber", phoneNumber);
//
//        startActivity(VerifyOtpActivity.this, intent);
//        finish();

        otpEditText = (OtpEditText) findEditTextById(R.id.et_otp);
        otpEditText.setOnCompleteListener(new OnCompleteListenerOtp() {
            @Override
            public void onCompleteOtp(String value) {
                AppUtils.hideSoftKeyboard(VerifyOtpActivity.this);
                progressBar.setVisibility(View.VISIBLE);
                verifyPhoneNumberWithCode(value);
                //  Toast.makeText(OtpVerificationActivity.this, "Completed " + value, Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void bindClicklisteners() {
        findViewById(R.id.tv_resend).setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tv_resend:
                resendVerificationCode(phoneNumber, PhoneNumbAuthActivity.mResendToken);
                break;
            case R.id.btn_next:
                startActivity(this, RegistrationActivity.class);
                break;
        }
    }

    private void verifyPhoneNumberWithCode(String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            FirebaseUser user = task.getResult().getUser();

                            Intent intent = new Intent(VerifyOtpActivity.this, RegistrationActivity.class);
                            intent.putExtra("phoneNumber", phoneNumber);

                            startActivity(VerifyOtpActivity.this, intent);

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                progressBar.setVisibility(View.GONE);
                                Snackbar.make(findViewById(android.R.id.content), "Invalid code.",
                                        Snackbar.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }

    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {
        initializeFirebase();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
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

                Toast.makeText(VerifyOtpActivity.this, "Code sent on" +
                        "  " + phoneNumber, Toast.LENGTH_LONG).show();

            }
        };

    }
}
