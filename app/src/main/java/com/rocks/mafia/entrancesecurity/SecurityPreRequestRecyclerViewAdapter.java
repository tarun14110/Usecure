package com.rocks.mafia.entrancesecurity;

/**
 * Created by root on 21/10/16.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

public class SecurityPreRequestRecyclerViewAdapter extends
        RecyclerView.Adapter<SecurityPreRequestViewHolder>
{
    private ArrayList<String> arrayList;
    private ArrayList<String> arrayTime;
    private ArrayList<String> arrayd;
    private ArrayList<String> arrayby;
    private Context context;

    public SecurityPreRequestRecyclerViewAdapter(Context context,
                                                 ArrayList<String> arrayList,ArrayList<String> arrayTime,ArrayList<String> arrayd,ArrayList<String> arrayby) {
        this.context = context;
        this.arrayList = arrayList;
        this.arrayTime = arrayTime;
        this.arrayd = arrayd;
        this.arrayby = arrayby;
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);

    }

    @Override
    public void onBindViewHolder(SecurityPreRequestViewHolder holder,
                                 int position) {


        final SecurityPreRequestViewHolder mainHolder = (SecurityPreRequestViewHolder) holder;
        //Setting text over textview
        mainHolder.title.setText(arrayList.get(position));
        mainHolder.time.setText(arrayTime.get(position));
        mainHolder.d.setText(arrayd.get(position));
        mainHolder.by.setText(arrayby.get(position));

    }

    @Override
    public SecurityPreRequestViewHolder onCreateViewHolder(
            ViewGroup viewGroup, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.security_pre_request_item_row, viewGroup, false);
        SecurityPreRequestViewHolder mainHolder = new SecurityPreRequestViewHolder(mainGroup)
        {
            @Override
            public String toString() {
                return super.toString();
            }
        };

        return mainHolder;

    }


}
