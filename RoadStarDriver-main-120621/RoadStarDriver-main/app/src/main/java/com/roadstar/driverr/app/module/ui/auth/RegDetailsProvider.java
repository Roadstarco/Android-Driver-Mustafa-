package com.roadstar.driverr.app.module.ui.auth;

import android.os.Bundle;
import android.view.View;

import com.roadstar.driverr.R;
import com.roadstar.driverr.app.module.ui.main.MainActivity;
import com.roadstar.driverr.common.base.BaseActivity;

public class RegDetailsProvider extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_details_provider);
        init();
    }

    void init() {
        setActionBar(getString(R.string.register));
        bindClicklisteners();
    }

    private void bindClicklisteners() {
        findViewById(R.id.lay_signin).setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_next:
                startActivityWithNoHistory(this, MainActivity.class);
                break;
            case R.id.lay_signin:
                startActivityWithNoHistory(this, SigninActivity.class);
                break;

        }
    }
}