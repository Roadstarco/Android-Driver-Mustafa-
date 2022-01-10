package com.roadstar.driverr.app.module.ui.manage_vehicle;

import android.os.Bundle;
import android.view.View;

import com.roadstar.driverr.R;
import com.roadstar.driverr.common.base.BaseActivity;

public class ManageVehicleActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_vehicle);

        init();
    }

    void init() {
        setActionBar(getString(R.string.manage_vehcle));
        bindClickListners();
    }

    private void bindClickListners() {
        findViewById(R.id.lay_ext_vehicle).setOnClickListener(this);
        findViewById(R.id.lay_add_new_vehcile).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.lay_ext_vehicle:
                break;
            case R.id.lay_add_new_vehcile:
                break;

        }
    }
}
