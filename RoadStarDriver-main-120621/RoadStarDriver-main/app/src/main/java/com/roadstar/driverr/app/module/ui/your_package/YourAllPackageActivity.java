package com.roadstar.driverr.app.module.ui.your_package;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.tabs.TabLayout;
import com.roadstar.driverr.R;
import com.roadstar.driverr.app.module.ui.your_package.adapters.AllScheduledRidesAdapters;
import com.roadstar.driverr.common.base.BaseActivity;

public class YourAllPackageActivity extends BaseActivity {

    ViewPager viewPager;
    AppCompatImageView backBtn;
    AppCompatTextView title;
    ProgressBar progressDoalog;
    private AllScheduledRidesAdapters adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_all_package);

        progressDoalog = findViewById(R.id.progressBar);

        backBtn = findViewById(R.id.iv_back);
        title = findViewById(R.id.tv_title);

        title.setText("Scheduled Rides");
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ViewPager viewPager = findViewById(R.id.view_pager);
        adapter = new AllScheduledRidesAdapters(getSupportFragmentManager());
        viewPager.setAdapter(adapter);


        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

    }
}