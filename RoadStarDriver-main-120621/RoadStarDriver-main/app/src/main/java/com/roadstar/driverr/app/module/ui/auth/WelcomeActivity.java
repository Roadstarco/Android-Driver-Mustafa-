package com.roadstar.driverr.app.module.ui.auth;

import android.os.Bundle;
import android.view.View;

import com.roadstar.driverr.R;
import com.roadstar.driverr.common.base.BaseActivity;

public class WelcomeActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
    }

    void init() {
        bindClicklistners();
    }

    void bindClicklistners() {
        findViewById(R.id.btn_signin).setOnClickListener(this);
        findViewById(R.id.btn_register).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.btn_signin:
                startActivity(this,SigninActivity.class);
                break;
            case R.id.btn_register:
                startActivity(this,RegisterLandingActivity.class);
                break;
        }
    }
}
