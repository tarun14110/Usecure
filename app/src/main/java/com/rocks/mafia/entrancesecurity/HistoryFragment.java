package com.rocks.mafia.entrancesecurity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends ListFragment
{
    //private ArrayList<HistoryNode> historyData;
    //private HistoryArrayAdaptor historyNodeArrayAdapter;

    public HistoryFragment()
    {
        // Required empty public constructor
    }
    public static List<HistoryNode> history;
    HistoryArrayAdaptor  adapter;
    RecyclerView recyclerView;
    private static List<HistoryNode> demoData;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        HistoryHandler  handler= new HistoryHandler(getActivity());
        recyclerView = (RecyclerView) getView().findViewById(R.id.allHistory);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        demoData = handler.getAllHistory();
        int i;
        adapter = new HistoryArrayAdaptor(demoData);
        recyclerView.setAdapter(adapter);


/*
    // TARUN DATA
     String[] dummyData = new String[]{"Tarun", "Vibha", "Mukesh", "Chetna", "Shikha","Tarun", "Vibha", "Mukesh", "Chetna", "Shikha","Tarun", "Vibha", "Mukesh", "Chetna", "Shikha"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,  dummyData);
        setListAdapter(adapter);*/


/*
//PANKAJ DATA WORKING-part1

HistoryHandler  handler= new HistoryHandler(getActivity());
        historyData = new ArrayList<HistoryNode>();

        String n= handler.getDatabaseName();
        handler.addHistory(new HistoryNode("Sujeet", new Time(3,4,5)));
        handler.addHistory(new HistoryNode("Tarun", new Time(3,4,5)));
        handler.addHistory(new HistoryNode("Mukesh", new Time(3,4,5)));
        handler.addHistory(new HistoryNode("Sudhir", new Time(3,4,5)));
        handler.addHistory(new HistoryNode("pankaj", new Time(3,4,5)));
        historyData= handler.getAllHistory();

*/


     /*
       //PANKAJ DATA WORKING-part2
       historyNodeArrayAdapter = new HistoryArrayAdaptor(getActivity(), historyData);
        setListAdapter(historyNodeArrayAdapter);
        handler.close();
*/
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
