package com.rocks.mafia.entrancesecurity;

/**
 * Created by mafia on 21/10/16.
 */

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



//Pre-Request tab , showing all current PreRequest data in list form


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
        arrayList=handler.getAllSecurityPreRequest();
        SecurityPreRequestRecyclerViewAdapter adapter = new SecurityPreRequestRecyclerViewAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(adapter);// set adapter on recyclerview

    }
}

