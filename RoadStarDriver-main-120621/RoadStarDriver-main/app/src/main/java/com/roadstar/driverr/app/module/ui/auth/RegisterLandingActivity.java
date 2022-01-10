package com.roadstar.driverr.app.module.ui.auth;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.roadstar.driverr.R;
import com.roadstar.driverr.common.base.BaseActivity;

public class RegisterLandingActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_landing);
        init();
    }

    void init() {
        bindClicklistners();
    }

    void bindClicklistners() {
        findViewById(R.id.btn_reg_comp).setOnClickListener(this);
        findViewById(R.id.btn_reg_driver).setOnClickListener(this);
        findViewById(R.id.btn_reg_provider).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.btn_reg_comp:

                Uri uri = Uri.parse("https://myroadstar.org/fleet/register"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

                //startActivity(this,PhoneNumbAuthActivity.class);
                break;
            case R.id.btn_reg_driver:
                startActivity(this,PhoneNumbAuthActivity.class);
                break;
            case R.id.btn_reg_provider:
                startActivity(this,PhoneNumbAuthActivity.class);
                break;
        }
    }
}
