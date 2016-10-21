package com.rocks.mafia.entrancesecurity;

/**
 * Created by mafia on 21/10/16.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

public class SecurityRequestRecyclerViewAdapter extends
        RecyclerView.Adapter<SecurityRequestViewHolder> {
    private ArrayList<String> arrayList;
    private Context context;


    public SecurityRequestRecyclerViewAdapter(Context context,
                                ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }


    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);

    }

    @Override
    public void onBindViewHolder(SecurityRequestViewHolder holder,
                                 int position) {


        final SecurityRequestViewHolder mainHolder = (SecurityRequestViewHolder) holder;
        //Setting text over textview
        mainHolder.title.setText(arrayList.get(position));

    }

    @Override
    public SecurityRequestViewHolder onCreateViewHolder(
            ViewGroup viewGroup, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.security_request_item_row, viewGroup, false);
        SecurityRequestViewHolder mainHolder = new SecurityRequestViewHolder(mainGroup) {
            @Override
            public String toString() {
                return super.toString();
            }
        };

        return mainHolder;

    }


}
