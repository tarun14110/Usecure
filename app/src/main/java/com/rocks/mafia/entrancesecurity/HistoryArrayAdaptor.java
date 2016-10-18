package com.rocks.mafia.entrancesecurity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mafia on 15/10/16.
 */
public class HistoryArrayAdaptor extends ArrayAdapter<HistoryNode> {

    public HistoryArrayAdaptor(Context context, ArrayList<HistoryNode> historyArray) {
        super(context, 0, historyArray);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        HistoryNode historyNode = getItem(position);

        // check if an existing view is being reused, otherwise inflate new view from custom layout
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.history_list_view, parent, false);
        }

        // get views, so that we can populate data into them
        TextView title = (TextView) convertView.findViewById(R.id.listItemHistoryTitle);
        TextView body = (TextView) convertView.findViewById(R.id.listItemHistoryBody);
        ImageView image = (ImageView) convertView.findViewById(R.id.listItemHistoryImage);

        // fill the data in each view
        title.setText(historyNode.getPersonName());
        body.setText(historyNode.getVisitingTime().toString());
       // if(historyNode.getImageUrl().isEmpty())
        image.setImageResource(R.drawable.ln_logo);

        // return the view to be displyed
        return convertView;
    }
}
