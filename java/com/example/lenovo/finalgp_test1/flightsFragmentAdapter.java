package com.example.lenovo.finalgp_test1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Lenovo on 3/04/2018.
 */

public class flightsFragmentAdapter  extends FragmentPagerAdapter {
    public flightsFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new BlankFragmentFlight();
            case 1:
                return new OneWayFragmentFlight();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "ROUNDTRIP";
            case 1:
                return "ONEWAY";
        }
        return super.getPageTitle(position);
    }
}
