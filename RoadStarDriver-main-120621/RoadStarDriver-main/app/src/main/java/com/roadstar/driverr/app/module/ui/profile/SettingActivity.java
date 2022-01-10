package com.roadstar.driverr.app.module.ui.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import com.roadstar.driverr.R;
import com.roadstar.driverr.common.base.BaseActivity;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        init();
    }

    void init() {
        setActionBar(getString(R.string.settings));
        bindClicklisteners();
    }

    private void bindClicklisteners() {

        findViewById(R.id.lay_change_pass).setOnClickListener(this);
        findSwitchByID(R.id.switch_notif).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.lay_change_pass:
                startActivity(this, ChangePasswordActivity.class);
                break;

        }
    }
}