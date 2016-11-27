package com.rocks.mafia.entrancesecurity;

/**
 * Created by root on 21/10/16.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.icu.text.SymbolTable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rocks.mafia.entrancesecurity.Services.ProfileHandler;

import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;
import java.util.Locale;

import static com.rocks.mafia.entrancesecurity.R.id.listView;
import static java.security.AccessController.getContext;

public class SecurityMainActivity extends AppCompatActivity {
    private static Toolbar toolbar;
    private static ViewPager viewPager;
    private static TabLayout tabLayout;
    public static final String JSON_URL = "http://usecure.site88.net/getAllUsers.php";
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {



        super.onCreate(savedInstanceState);

        Log.e("Mode ","main");
        setContentView(R.layout.security_activity_main);
        String  t= getDateTime();
        SecurityHistoryHandler historyHandler= new SecurityHistoryHandler(this);
        t= getDateTime();
        historyHandler.delete();
        historyHandler.addSecurityHistory(new SecurityRequestNode("pankaj","i want to meet rahul ","888888888","", t,1));
        sendRequest();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String message = getIntent().getStringExtra("message");
            Log.e("MESSAGE", message);
        }






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
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        tab.select();

        //Implementing tab selected listener over tablayout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
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



    private void sendRequest(){

        StringRequest stringRequest = new StringRequest(JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        showJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SecurityMainActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json){
        ParseJSON pj = new ParseJSON(json);
        pj.parseJSON();
        ProfileHandler handler= new ProfileHandler(this);
        handler.deleteUsers();
        // updated users table
        for (int i = 0; i < ParseJSON.contacts.length; ++i)
        {
            //if(handler.checkUser(ParseJSON.contacts[i])==false)
            handler.addUser(ParseJSON.names[i],ParseJSON.emails[i],ParseJSON.contacts[i],ParseJSON.address[i]);
            if (!ParseJSON.profilePicUrls[i].isEmpty())
            {
                Log.e("coll2",Integer.toString(i));
                getImage(ParseJSON.profilePicUrls[i], i);
            }
            Log.e("col3",Integer.toString(i));

        }

    }

    private void getImage(String path, final int i) {
        String url = "http://usecure.site88.net/userProfilePics/";
        url = url + path;
        byte[] image;
     Log.e("coll",path);
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>()
                {
                    @Override
                    public void onResponse(Bitmap bitmap)
                    {
                        Log.e("HEEEEEEEEEYYYYYYYYYYYYY", bitmap.toString());
                        updateImageInDatabase(bitmap, i);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SecurityMainActivity.this, error.toString(), Toast.LENGTH_LONG);
                    }
                });
// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(request);
    }

    private void updateImageInDatabase(Bitmap imageBitmap, int i) {
        ProfileHandler handler= new ProfileHandler(this);
        Log.e("PICCCCCCCCCCCCCCC", String.valueOf(i));
        handler.updateImage(ParseJSON.contacts[i],getImageBytes(imageBitmap));
    }

    // convert from bitmap to byte array
    public static byte[] getImageBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
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
    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new SecurityPreRequestFragment("Pre Informed"), "Pre Informed");
        Log.e("MAIN","  : STEP 1");
        adapter.addFrag(new SecurityRequestFragment("Pending confirms"), "Pending confirms");
        Log.e("MAIN","  : STEP 2");
        adapter.addFrag(new SecurityHistoryFragment("History"), "History");
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
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "  HH:mm:ss dd/MM/yyyy ", Locale.getDefault());
        java.util.Date date = new java.util.Date();
        return dateFormat.format(date);
    }

}