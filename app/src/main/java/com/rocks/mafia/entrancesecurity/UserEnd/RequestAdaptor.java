package com.rocks.mafia.entrancesecurity.UserEnd;

/**
 * Created by pankaj on 22/10/16.
 */

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rocks.mafia.entrancesecurity.R;

import java.util.List;


public class RequestAdaptor extends RecyclerView.Adapter
        <RequestAdaptor.ListItemViewHolder> {

    private List<RequestNode> items;
    private SparseBooleanArray selectedItems;

    RequestAdaptor(List<RequestNode> modelData)
    {
        if (modelData == null)
        {
            throw new IllegalArgumentException("modelData must not be null");
        }
        items = modelData;
        selectedItems = new SparseBooleanArray();
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.request_view_node, viewGroup, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder viewHolder, int position)
    {
        RequestNode model = items.get(position);
        viewHolder.message.setText("Message: "+String.valueOf(model.getMessage()));
        viewHolder.itemView.setActivated(selectedItems.get(position, false));
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }

    public final static class ListItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView message;
        TextView time;
        public ListItemViewHolder(View itemView)
        {
            super(itemView);
            /*message = (TextView) itemView.findViewById(R.id.request);*/
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }
}
