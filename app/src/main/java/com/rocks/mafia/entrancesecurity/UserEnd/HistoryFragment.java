package com.rocks.mafia.entrancesecurity.UserEnd;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.rocks.mafia.entrancesecurity.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends ListFragment
{

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
        Log.e("FOFA","TATA" +String.valueOf(recyclerView==null));
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
        protected Void doInBackground(Void... voids)
        {
            HistoryHandler  handler= new HistoryHandler(getActivity());
            String[] d={"To attend workshop conducted by Adobe for Future technologies","want to meet my friend tarun room no. c111"," from Flipkart for customer Rahul Vedh ,Room no.c231","meeting with Prof.Jalote at the auditorium C11","To attend workshop conducted by Adobe for Future technologies","want to meet my friend tarun room no. c111"," from Flipkart for customer Rahul Vedh ,Room no.c231"," Want to meet Faculty Prof. Rajiv Raman","Invited to attend seminar by Prof. Raj ayyer","want to meet my son Mukesh Kumar Yadav (Student of IIITD)","meeting with Prof.Jalote at the auditorium C11"," Want to meet Faculty Prof. Rajiv Raman","Invited to attend seminar by Prof. Raj ayyer","want to meet my son Mukesh Kumar Yadav (Student of IIITD)","meeting with Prof.Jalote at the auditorium C11","To attend workshop conducted by Adobe for Future technologies","want to meet my friend tarun room no. c111"," from Flipkart for customer Rahul Vedh ,Room no.c231"," Want to meet Faculty Prof. Rajiv Raman","Invited to attend seminar by Prof. Raj ayyer","want to meet my son Mukesh Kumar Yadav (Student of IIITD)"};
            ArrayList<String> arrayd = new ArrayList<>();
            for (int i = 0; i < 20; i++)
            {
                arrayd.add(d[i]);
            }

            // get all history data
            demoData = handler.getAllHistory();
            adapter = new HistoryArrayAdaptor(demoData,arrayd);

            return null;
        }
    }


}
