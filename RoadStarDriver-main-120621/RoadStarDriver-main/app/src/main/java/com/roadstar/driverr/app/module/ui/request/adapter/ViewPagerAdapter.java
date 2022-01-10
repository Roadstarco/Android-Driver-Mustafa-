package com.roadstar.driverr.app.module.ui.request.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.roadstar.driverr.app.module.ui.request.fragments.InternationalRequests;
import com.roadstar.driverr.app.module.ui.request.fragments.LocalRequest;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final Fragment[] childFragments;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        childFragments = new Fragment[] {
                new LocalRequest(), //0
                new InternationalRequests(), //1
        };
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return childFragments[position];
    }

    @Override
    public int getCount() {
        return childFragments.length; //3 items
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return "Local";
        else
            return "International";
    }
}