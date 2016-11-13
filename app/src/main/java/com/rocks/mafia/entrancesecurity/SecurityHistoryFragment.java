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

public class SecurityHistoryFragment extends Fragment {
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
        Log.e("LISTEN","EDIT");
        view = inflater.inflate(R.layout.security_history_layout, container, false);
        setRecyclerView();
        return view;

    }

    //Setting recycler view
    private void setRecyclerView() {

        recyclerView = (RecyclerView) view
                .findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView
                .setLayoutManager(new LinearLayoutManager(getActivity()));//Linear Items
        SecurityHistoryHandler handler=new  SecurityHistoryHandler(getActivity());
        Log.e("HISTORY ","  : STEP 1");
        ArrayList<SecurityRequestNode> arrayList=handler.getAllSecurityHistory();
        Log.e("HISTORY ","  : STEP 2");
        SecurityHistoryRecyclerViewAdapter adapter = new SecurityHistoryRecyclerViewAdapter (getActivity(), arrayList);
        Log.e("HISTORY ","  : STEP 3");
        recyclerView.setAdapter(adapter);// set adapter on recyclerview

    }
}