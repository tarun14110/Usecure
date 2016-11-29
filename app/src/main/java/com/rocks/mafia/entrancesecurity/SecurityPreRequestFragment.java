package com.rocks.mafia.entrancesecurity;

/**
 * Created by mafia on 21/10/16.
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static com.rocks.mafia.entrancesecurity.SecurityRequestFragment.adapter;

public class SecurityPreRequestFragment extends Fragment {
    private View view;

    private String title;//String for tab title
    private ArrayList<SecurityPreRequestNode> arrayList;
    private static RecyclerView recyclerView;

    public SecurityPreRequestFragment() {
    }

    public SecurityPreRequestFragment(String title) {
        this.title = title;//Setting tab title
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.security_pre_request_layout, container, false);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter(AppConfig.PUSH_NOTIFICATION));
        setRecyclerView();


        // Schedule to sort the pre-requests with time
        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
               // Log.e("chopu", arrayList.get(0).getEntryTime());
                ArrayList<SecurityPreRequestNode> temp1 = new ArrayList<SecurityPreRequestNode>();
                ArrayList<SecurityPreRequestNode> temp2 = new ArrayList<SecurityPreRequestNode>();


                for (int i =0 ; i < arrayList.size()-1; ++i) {
                    for (int j =0; j < arrayList.size()-i-1; ++j) {
                        try{
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");

                            String str1 = arrayList.get(i).getEntryTime();
                            Date date1 = formatter.parse(str1);
                            String str2 = arrayList.get(j).getEntryTime();
                            Date date2 = formatter.parse(str2);

                            if (date1.compareTo(date2)>=0)
                            {
                                SecurityPreRequestNode tem;
                                tem = arrayList.get(j);
                                arrayList.set(j,arrayList.get(j+1));
                                arrayList.set(j+1, tem);
                            }
                        }catch (ParseException e1){
                            e1.printStackTrace();
                        }
                    }
                }

                Date date = new Date();
                for (int i =0; i < arrayList.size(); ++i) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
                    String str1 = arrayList.get(i).getEntryTime();
                    Date date1 = null;
                    try {
                        date1 = formatter.parse(str1);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long diff = date1.getTime() - date.getTime();
                    long seconds = diff / 1000;
                    long minutes = seconds / 60;
                    long hours = minutes / 60;
                    if (diff <1 && diff > -1) {
                        temp1.add(arrayList.get(i));
                    } else {
                        temp2.add(arrayList.get(i));
                    }
                }

                int i;
                for (i = 0; i< temp1.size(); ++i) {
                    arrayList.set(i, temp1.get(i));
                }

                for (int j = 0; j< temp1.size(); ++i) {
                    arrayList.set(i, temp1.get(j));
                    ++i;
                }

if(getActivity() != null) {
    getActivity().runOnUiThread(new Runnable() {
        public void run() {
            adapter.notifyDataSetChanged();
        }
    });
}
            }
        }, 0, 30000);

        return view;

    }




    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getStringExtra("type").equals("pre-request")) {
                // Get extra data included in the Intent
                String name = intent.getStringExtra("name");
                String reason = intent.getStringExtra("reason");
                String contact = intent.getStringExtra("contact");
                String visitingtime = intent.getStringExtra("visitingtime");

                arrayList.add(0, new SecurityPreRequestNode(name, reason, contact, visitingtime));

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }


    };

    public synchronized void refresAdapter(ArrayList<SecurityPreRequestNode> items) {
        arrayList.clear();
        arrayList.addAll(items);
        adapter.notifyDataSetChanged();
    }

    //Setting recycler view
    private void setRecyclerView() {

        recyclerView = (RecyclerView) view
                .findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView
                .setLayoutManager(new LinearLayoutManager(getActivity()));//Linear Items

        SecurityPreRequestHandler handler =new SecurityPreRequestHandler(getContext());
        arrayList=handler.getAllSecurityPreRequest();

        SecurityPreRequestRecyclerViewAdapter adapter = new SecurityPreRequestRecyclerViewAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(adapter);// set adapter on recyclerview

    }
}

