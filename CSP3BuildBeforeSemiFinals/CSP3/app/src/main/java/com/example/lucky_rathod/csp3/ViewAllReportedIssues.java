package com.example.lucky_rathod.csp3;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static com.example.lucky_rathod.csp3.CategoryAdapter.tabTitles;

/**
 * Created by Lucky_Rathod on 10-02-2018.
 */

public class ViewAllReportedIssues extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity())
                .setActionBarTitle("Reported Issues ");
        return  inflater.inflate(R.layout.reported_issues_fragment,null);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ViewPager viewPager = view.findViewById(R.id.viewpager);
        CategoryAdapter categoryAdapter = new CategoryAdapter(getFragmentManager());
        viewPager.setAdapter(categoryAdapter);

        final TabLayout tabLayout = view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub


//                actionBar.setTitle(tabTitles[position]);

                ((MainActivity) getActivity())
                        .setActionBarTitle(tabTitles[tabLayout.getSelectedTabPosition()]);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
                ((MainActivity) getActivity())
                        .setActionBarTitle(tabTitles[tabLayout.getSelectedTabPosition()]);

            }

            @Override
            public void onPageScrollStateChanged(int pos) {
                // TODO Auto-generated method stub

            }
        });
    }
}
