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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

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

        setAlarm();

        return view;

    }

    public void setAlarm(){
        Intent intent=new Intent(getActivity(),Alarm_receiver.class);
        AlarmManager alarmMgr=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getActivity(), 1, intent, 0);
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, 10000, AlarmManager.INTERVAL_DAY, alarmIntent);
    }


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getStringExtra("type").equals("pre-request")) {
                Log.e("LOLU", "POPU");
                // Get extra data included in the Intent
                String name = intent.getStringExtra("name");
                String reason = intent.getStringExtra("reason");
                String contact = intent.getStringExtra("contact");
                String visitingtime = intent.getStringExtra("visitingtime");

                arrayList.add(0, new SecurityPreRequestNode(name, reason, contact, visitingtime));
                adapter.notifyDataSetChanged();

                //  Log.e("receiver", "Got message: " + message);
            }
        }
    };

    //Setting recycler view
    private void setRecyclerView() {

        recyclerView = (RecyclerView) view
                .findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView
                .setLayoutManager(new LinearLayoutManager(getActivity()));//Linear Items

        SecurityPreRequestHandler handler =new SecurityPreRequestHandler(getContext());
        //handler.addSecurityPreRequest(new SecurityPreRequestNode("Sujeet Kumar","want to meet me tarun kumar","9971949716","12:34:30 28/11/16"));
        arrayList=handler.getAllSecurityPreRequest();





//        String[] s={"sujeet kumar","mukesh yadav","tarun","Ramesh Kumar ","Ramesh Kumar ","ujeet singh","rahul sharma","jay singh","sujeet kumar","mukesh yadav","tarun","Ramesh Kumar ","Sujeet singh","rahul sharma","jay singh","ujeet singh","rahul sharma","jay singh","sujeet kumar","mukesh yadav","tarun"};
//        String[] t={ "2:24 PM\n" +
//                "Saturday, 30 October 2016 (IST)","11:24 AM\n" +
//                "Saturday, 29 October 2016 (IST)","1:24 AM\n" +
//                "Saturday, 23 October 2016 (IST)","9:24 AM\n" +
//                "Saturday, 25 October 2016 (IST)","8:24 AM\n" +
//                "Saturday, 27 October 2016 (IST)","11:24 AM\n" +
//                "Saturday, 2 November 2016 (IST)","2:24 PM\n" +
//                "Saturday, 20 October 2016 (IST)","10:24 AM\n" +
//                "Saturday, 19 October 2016 (IST)" ,"11:24 AM\n" +
//                "Saturday, 25 October 2016 (IST)","9:20 PM\n" +
//                "Saturday, 16 October 2016 (IST)" ,"11:24 AM\n" +
//                "Saturday, 25 October 2016 (IST)","9:20 PM\n" +
//                "Saturday, 16 October 2016 (IST)","1:24 AM\n" +
//                "Saturday, 20 October 2016 (IST)","9:24 AM\n" +
//                "Saturday, 25 October 2016 (IST)","8:24 AM\n" +
//                "Saturday, 17 October 2016 (IST)" ,"11:24 AM\n" +
//                "Saturday, 22 October 2016 (IST)","2:24 PM\n" +
//                "Saturday, 20 October 2016 (IST)","10:24 AM\n" +
//                "Saturday, 19 October 2016 (IST)" ,"11:24 AM\n" +
//                "Saturday, 25 October 2016 (IST)","9:20 PM\n" +
//                "Saturday, 16 October 2016 (IST)","1:24 AM\n" +
//                "Saturday, 20 October 2016 (IST)","9:24 AM\n" +
//                "Saturday, 25 October 2016 (IST)","8:24 AM\n" +
//                "Saturday, 17 October 2016 (IST)"};
//        String[] d={"To attend workshop conducted by Adobe for Future technologies","want to meet my friend tarun room no. c111"," from Flipkart for customer Rahul Vedh ,Room no.c231","meeting with Prof.Jalote at the auditorium C11","To attend workshop conducted by Adobe for Future technologies","want to meet my friend tarun room no. c111"," from Flipkart for customer Rahul Vedh ,Room no.c231"," Want to meet Faculty Prof. Rajiv Raman","Invited to attend seminar by Prof. Raj ayyer","want to meet my son Mukesh Kumar Yadav (Student of IIITD)","meeting with Prof.Jalote at the auditorium C11"," Want to meet Faculty Prof. Rajiv Raman","Invited to attend seminar by Prof. Raj ayyer","want to meet my son Mukesh Kumar Yadav (Student of IIITD)","meeting with Prof.Jalote at the auditorium C11","To attend workshop conducted by Adobe for Future technologies","want to meet my friend tarun room no. c111"," from Flipkart for customer Rahul Vedh ,Room no.c231"," Want to meet Faculty Prof. Rajiv Raman","Invited to attend seminar by Prof. Raj ayyer","want to meet my son Mukesh Kumar Yadav (Student of IIITD)"};
//        String[] by={"Ramesh Kumar ","ujeet singh","rahul sharma","sujeet kumar","mukesh yadav","tarun","jay singh","sujeet kumar","mukesh yadav","sujeet kumar","mukesh yadav","tarun","Ramesh Kumar ","ujeet singh","rahul sharma","jay singh","sujeet kumar","mukesh yadav","sujeet kumar","mukesh yadav","tarun","Ramesh Kumar ","ujeet singh","rahul sharma","jay singh","sujeet kumar","mukesh yadav"};
//
//        ArrayList<String> arrayList = new ArrayList<>();
//        ArrayList<String> arrayTime = new ArrayList<>();
//        ArrayList<String> arrayd = new ArrayList<>();
//        ArrayList<String> arrayby = new ArrayList<>();
//        for (int i = 0; i < 20; i++)
//        {
//            arrayTime.add(t[i]);
//            arrayList.add(s[i]);//Adding items to recycler view
//            arrayd.add(d[i]);
//            arrayby.add(by[i]);
//        }

        SecurityPreRequestRecyclerViewAdapter adapter = new SecurityPreRequestRecyclerViewAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(adapter);// set adapter on recyclerview

    }
}

