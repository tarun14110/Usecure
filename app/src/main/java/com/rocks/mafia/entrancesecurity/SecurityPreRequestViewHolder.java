package com.rocks.mafia.entrancesecurity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by root on 21/10/16.
 */

public class SecurityPreRequestViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public TextView time;
    public TextView d;
    public TextView by;


    public SecurityPreRequestViewHolder(View view) {
        super(view);

        this.title = (TextView) view.findViewById(R.id.cardTitle);
        this.time = (TextView) view.findViewById(R.id.time);
        this.d = (TextView) view.findViewById(R.id.description);
        this.by = (TextView) view.findViewById(R.id.by);
    }
}