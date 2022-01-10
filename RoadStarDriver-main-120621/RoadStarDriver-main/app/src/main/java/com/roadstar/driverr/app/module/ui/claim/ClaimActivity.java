package com.roadstar.driverr.app.module.ui.claim;

import android.os.Bundle;
import android.view.View;

import com.roadstar.driverr.R;
import com.roadstar.driverr.common.base.BaseActivity;
import com.roadstar.driverr.common.utils.AppUtils;

public class ClaimActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim);
        init();
    }

    void init() {
        setActionBar(getString(R.string.claim));
        bindClicklisteners();
    }

    private void bindClicklisteners() {

        findViewById(R.id.btn_submit).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_submit:
                if(isParamValid()){
                    resetScreen(findViewById(R.id.layout_main));
                    showSnackBar("Complain submitted. Thankyou");
                }

                break;


        }
    }
    private boolean isParamValid() {
        boolean valid = true;
        if (!AppUtils.isNotFieldEmpty(findEditTextById(R.id.et_req_numb) )) // Req numb
        {
            findTextViewById(R.id.tv_error_req_numb).setText(R.string.field_empty);
            valid = false;
            return valid;
        }
        findTextViewById(R.id.tv_error_req_numb).setText("");

        if (!AppUtils.isNotFieldEmpty(findEditTextById(R.id.et_info_details))) // Info Details
        {
            findTextViewById(R.id.tv_error_info_detail).setText(R.string.field_empty);
            valid = false;
            return valid;
        }
        findTextViewById(R.id.tv_error_info_detail).setText("");

        AppUtils.hideSoftKeyboard(this);//Hide keyboard
        return valid;
    }

}