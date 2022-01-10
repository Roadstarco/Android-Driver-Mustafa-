package com.roadstar.driverr.app.module.ui.request;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.tabs.TabLayout;
import com.roadstar.driverr.R;
import com.roadstar.driverr.app.module.ui.request.adapter.ViewPagerAdapter;
import com.roadstar.driverr.common.base.BaseActivity;

public class RequestActivity extends BaseActivity {

    ViewPager viewPager;
    AppCompatImageView backBtn;
    AppCompatTextView title;
    ProgressBar progressDoalog;
    public static RequestActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request1);
        activity = this;
        progressDoalog = findViewById(R.id.progressBar);

        backBtn = findViewById(R.id.iv_back);
        title = findViewById(R.id.tv_title);

        title.setText(R.string.Requests);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        viewPager = findViewById(R.id.view_pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);


        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

    }

}