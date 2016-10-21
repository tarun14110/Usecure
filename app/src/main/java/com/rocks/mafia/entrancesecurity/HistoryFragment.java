package com.rocks.mafia.entrancesecurity;


import android.os.AsyncTask;
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

import com.google.firebase.iid.FirebaseInstanceId;

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

        recyclerView = (RecyclerView) getView().findViewById(R.id.allHistory);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        FetchHistoryDataFromSQL fetchHistoryDataFromSQL = new FetchHistoryDataFromSQL();
        fetchHistoryDataFromSQL.execute();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    public class FetchHistoryDataFromSQL extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recyclerView.setAdapter(adapter);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HistoryHandler  handler= new HistoryHandler(getActivity());
            // get all history data
            demoData = handler.getAllHistory();
            adapter = new HistoryArrayAdaptor(demoData);

            return null;
        }
    }


}
