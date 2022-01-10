package com.roadstar.driverr.app.module.ui.your_package.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.roadstar.driverr.app.module.ui.your_package.fragments.InternationRides;
import com.roadstar.driverr.app.module.ui.request.fragments.LocalRequest;

public class AllScheduledRidesAdapters extends FragmentPagerAdapter {

    private Fragment[] childFragments;

    public AllScheduledRidesAdapters(FragmentManager fm) {
        super(fm);
        childFragments = new Fragment[] {
                new LocalRequest(), //0
                new InternationRides(), //1
        };
    }

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