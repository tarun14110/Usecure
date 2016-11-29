package com.rocks.mafia.entrancesecurity.UserEnd;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rocks.mafia.entrancesecurity.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryArrayAdaptor
        extends RecyclerView.Adapter
        <HistoryArrayAdaptor.ListItemViewHolder> {

    private List<HistoryNode> items;
    private ArrayList<String> arrayd;
    private SparseBooleanArray selectedItems;


    HistoryArrayAdaptor(List<HistoryNode> modelData, ArrayList<String> arrayd) {
        this.arrayd = arrayd;
        if (modelData == null) {
            throw new IllegalArgumentException("modelData must not be null");
        }
        items = modelData;
        selectedItems = new SparseBooleanArray();
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.history_list_view, viewGroup, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder viewHolder, int position) {
        HistoryNode model = items.get(position);
        viewHolder.name.setText("Name : " + String.valueOf(model.getPersonName()));
        viewHolder.age.setText("Time :" + String.valueOf(model.getVisitingTime()));
        viewHolder.imageView.setImageResource(Integer.parseInt(model.getImageUrl()));
        viewHolder.d.setText(arrayd.get(position));
        viewHolder.itemView.setActivated(selectedItems.get(position, false));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public final static class ListItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView age;
        TextView d;
        ImageView imageView;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.listItemHistoryTitle);
            age = (TextView) itemView.findViewById(R.id.listItemHistoryBody);
            imageView = (ImageView) itemView.findViewById(R.id.listItemHistoryImage);
            d = (TextView) itemView.findViewById(R.id.description);
        }
    }
}
