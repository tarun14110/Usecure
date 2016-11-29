package com.rocks.mafia.entrancesecurity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mafia on 21/10/16.
 */

public class SecurityRequestViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public TextView time;
    public TextView d;
    public TextView by;
    public ImageView status;
    public View v;
    public TextView date;
    public ImageView takenImage;


    public SecurityRequestViewHolder(View view) {
        super(view);
        this.v = view;
        this.title = (TextView) view.findViewById(R.id.cardTitle);
        this.time = (TextView) view.findViewById(R.id.time);
        this.date = (TextView) view.findViewById(R.id.date);
        this.d = (TextView) view.findViewById(R.id.description);
        this.by = (TextView) view.findViewById(R.id.by);
        this.status = (ImageView) view.findViewById(R.id.status);
        this.takenImage = (ImageView) view.findViewById(R.id.takenImage);

    }

}
