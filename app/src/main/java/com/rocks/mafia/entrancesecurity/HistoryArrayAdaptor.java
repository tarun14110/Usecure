package com.rocks.mafia.entrancesecurity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HistoryArrayAdaptor
        extends RecyclerView.Adapter
        <HistoryArrayAdaptor.ListItemViewHolder> {

    private List<HistoryNode> items;
    private SparseBooleanArray selectedItems;


    HistoryArrayAdaptor(List<HistoryNode> modelData)
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
                inflate(R.layout.history_list_view, viewGroup, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder viewHolder, int position) {
        HistoryNode model = items.get(position);
        viewHolder.name.setText(String.valueOf(model.getPersonName()));
        viewHolder.age.setText(String.valueOf(model.getVisitingTime()));
        viewHolder.itemView.setActivated(selectedItems.get(position, false));
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }

    public final static class ListItemViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        TextView age;

        public ListItemViewHolder(View itemView)
        {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.listItemHistoryTitle);
            age = (TextView) itemView.findViewById(R.id.listItemHistoryBody);
        }
    }
}
