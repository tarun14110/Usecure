package com.rocks.mafia.entrancesecurity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.Time;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends ListFragment {

    private ArrayList<HistoryNode> historyData;
    private HistoryArrayAdaptor historyNodeArrayAdapter;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

/*        String[] dummyData = new String[]{"Tarun", "Vibha", "Mukesh", "Chetna", "Shikha","Tarun", "Vibha", "Mukesh", "Chetna", "Shikha","Tarun", "Vibha", "Mukesh", "Chetna", "Shikha"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,  dummyData);
        setListAdapter(adapter);*/

        historyData = new ArrayList<HistoryNode>();
        historyData.add(new HistoryNode("Tarun kumar yadav", new Time(3,4,5)));
        historyData.add(new HistoryNode("Vibha", new Time(3,4,5)));
        historyData.add(new HistoryNode("Chetna", new Time(3,4,5)));

        historyNodeArrayAdapter = new HistoryArrayAdaptor(getActivity(), historyData);

        setListAdapter(historyNodeArrayAdapter);


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.history_display, container, false);
    }
*/

}
