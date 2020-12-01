package com.example.lucky_rathod.csp3;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Lucky_Rathod on 10-02-2018.
 */


public class CategoryAdapter extends android.support.v13.app.FragmentStatePagerAdapter {


    public static String tabTitles[] = new String[] { "Potholes ", "Sanitation ", "Resend"};
    public  CategoryAdapter(android.app.FragmentManager fm){
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public android.app.Fragment getItem(int position) {
/*        if (position == 0) {
            return new PotholeFragmentActivity();
        } else if (position == 1){
            return new SanitationFragmentActivity();
        }
        else {
            return new ResendFragmentActivity();
        }*/
        switch (position) {
            case 0:
                return new PotholeFragmentActivity();
            case 1:
                return new SanitationFragmentActivity();
            case 2:
                return new ResendFragmentActivity();
            default:
                break;
        }
        return null;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
