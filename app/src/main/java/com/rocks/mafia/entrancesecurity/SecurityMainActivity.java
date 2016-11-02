package com.rocks.mafia.entrancesecurity;

/**
 * Created by root on 21/10/16.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.rocks.mafia.entrancesecurity.Services.ProfileHandler;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class SecurityMainActivity extends AppCompatActivity {
    private static Toolbar toolbar;
    private static ViewPager viewPager;
    private static TabLayout tabLayout;

    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.security_activity_main);

//dummy data in search prpfile part

        ProfileHandler handler= new ProfileHandler(this);
       handler.addUser("pankajA","pankaj14073@iiitd.ac.in","993772568","RAI pur,New Delhi");
        handler.addUser("pankajB","pankajB14073@iiitd.ac.in","9937789668","chattarpur,New Delhi");
       handler.addUser("pankajC","pankajC14073@iiitd.ac.in","992342568","satbari beri,New Delhi");
        handler.addUser("RAHUL","rahul14073@iiitd.ac.in","9968123456","fatehpur beri sikri ,New Delhi");
                        //profile_node(String name, String contact, String email, String address, String img)


        ArrayList<profile_node>list=new ArrayList<profile_node>();
            list= handler.getAllProfiles();
      int i ;
        for(i=0;i<list.size();i++)
            Log.v("PROFILE: ",list.get(i).toString());

         toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("SmartSec");
        setSupportActionBar(toolbar);;

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
      //  searchView = (SearchView)this.findViewById(R.id.searchView);
        FloatingActionButton fab=(FloatingActionButton)this.findViewById(R.id.fab);
       fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
               Intent intent = new Intent(view.getContext(), security_request_search.class);
                startActivity(intent);

               Toast.makeText(view.getContext(),"NEW ACITVITY", Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id) {
            case R.id.action_logout:
                SessionManager session = new SessionManager(getApplicationContext());
                if(session.isSecurityLoggedIn()) {
                    session.setSecurityLogin(false);
                } else {
                    session.setLogin(false);
                }

                Intent intent = new Intent(this, WelcomeActivity.class);
                startActivity(intent);
                break;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Setting View Pager
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new SecurityPreRequestFragment("Pre Informed"), "Pre Informed");
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
