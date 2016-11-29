package com.rocks.mafia.entrancesecurity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by pankaj on 13/11/16.
 */

// adapter class attached withe the hitory tab


public class SecurityHistoryRecyclerViewAdapter extends
        RecyclerView.Adapter<SecurityHistoryViewHolder>
{
    private ArrayList<SecurityRequestNode> arrayList;
    private Context context;


    public SecurityHistoryRecyclerViewAdapter(Context context,ArrayList<SecurityRequestNode> arrayList)
    {
        this.context = context;
        this.arrayList = arrayList;
    }


    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);

    }

    @Override
    public void onBindViewHolder(SecurityHistoryViewHolder holder,int position)
    {

        final SecurityHistoryViewHolder mainHolder = (SecurityHistoryViewHolder) holder;
        //Setting text over textview
        if(arrayList!=null) {
            if ((arrayList != null) && (mainHolder != null) && (arrayList.get(position) != null) && (mainHolder.title != null)) {
                mainHolder.title.setText(arrayList.get(position).getOutsiderName());
                mainHolder.time.setText(arrayList.get(position).getEntryTime().toString());
                mainHolder.d.setText(arrayList.get(position).getReason());
                mainHolder.by.setText(arrayList.get(position).getInsiderContact());

                //setting initial state of the Request
                int s = arrayList.get(position).getStatus();
                if (s == 1)
                    mainHolder.status.setBackgroundResource(R.drawable.cross);
                else
                    mainHolder.status.setBackgroundResource(R.drawable.tick);

            }
        }
    }

    @Override
    public SecurityHistoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.security_history_item_row, viewGroup, false);
        SecurityHistoryViewHolder mainHolder = new SecurityHistoryViewHolder(mainGroup) {
            @Override
            public String toString() {
                return super.toString();
            }
        };

        return mainHolder;

    }


}
