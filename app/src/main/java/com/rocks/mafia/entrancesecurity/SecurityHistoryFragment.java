package com.rocks.mafia.entrancesecurity;

/**
 * Created by mafia on 21/10/16.
 */
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;



//recycleview class handling the history data
//its taking the data from the sqllite and showing the details in listform in historytab.

public class SecurityHistoryFragment extends Fragment
{
    private View view;
    private static final String TAG = SecurityMainActivity.class.getSimpleName();
    private String title;//String for tab title

    private static RecyclerView recyclerView;

    public SecurityHistoryFragment()
    {

    }

    public SecurityHistoryFragment(String title) {
        title = title;//Setting tab title
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.security_history_layout, container, false);
        setRecyclerView();
        return view;

    }

    //Setting recycler view
    private void setRecyclerView() {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView
                .setLayoutManager(new LinearLayoutManager(getActivity()));//Linear Items

        //taking data from the sqllite
        SecurityHistoryHandler handler=new  SecurityHistoryHandler(getActivity());
        ArrayList<SecurityRequestNode> arrayList=handler.getAllSecurityHistory();
        //connecting the data to the adapter

        SecurityHistoryRecyclerViewAdapter adapter = new SecurityHistoryRecyclerViewAdapter (getActivity(), arrayList);
        recyclerView.setAdapter(adapter);// set adapter on recyclerview

    }
}