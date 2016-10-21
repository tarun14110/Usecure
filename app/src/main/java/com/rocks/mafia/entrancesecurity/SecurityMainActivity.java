package com.rocks.mafia.entrancesecurity;

/**
 * Created by root on 21/10/16.
 */

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class SecurityMainActivity extends AppCompatActivity {
    private static Toolbar toolbar;
    private static ViewPager viewPager;
    private static TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.security_activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (findViewById(R.id.viewPager) == null) {
            Log.e("WOWOWOW", "HOHOHO");
        }
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);//setting tab over viewpager

        //Implementing tab selected listener over tablayout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());//setting current selected item over viewpager
                switch (tab.getPosition()) {
                    case 0:
                        Log.e("TAG","TAB1");
                        break;
                    case 1:
                        Log.e("TAG","TAB2");
                        break;
                    case 2:
                        Log.e("TAG","TAB3");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }


    //Setting View Pager
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new SecurityRequestFragment("Pre Informed"), "Pre Informed");
        adapter.addFrag(new SecurityRequestFragment("Pending confirms"), "Pending confirms");
        adapter.addFrag(new SecurityRequestFragment("History"), "History");
        Log.e(adapter.toString(), "PPOOOO");
        Log.e(viewPager.toString(), "YOOOO");
        viewPager.setAdapter(adapter);
    }


    //View Pager fragments setting adapter class
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();//fragment arraylist
        private final List<String> mFragmentTitleList = new ArrayList<>();//title arraylist

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }


        //adding fragments and title method
        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
