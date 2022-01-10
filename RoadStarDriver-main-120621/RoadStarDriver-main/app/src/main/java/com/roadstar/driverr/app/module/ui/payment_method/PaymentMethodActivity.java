package com.roadstar.driverr.app.module.ui.payment_method;

import android.os.Bundle;
import android.view.View;

import com.roadstar.driverr.R;
import com.roadstar.driverr.app.module.ui.main.MainActivity;
import com.roadstar.driverr.common.base.BaseActivity;

public class PaymentMethodActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        init();
    }

    void init() {
        setActionBar(getString(R.string.payment_method));
      //  bindClicklisteners();
    }

    private void bindClicklisteners() {

        findViewById(R.id.btn_submit).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_submit:
                startActivityWithNoHistory(this, MainActivity.class);
                break;


        }
    }
}