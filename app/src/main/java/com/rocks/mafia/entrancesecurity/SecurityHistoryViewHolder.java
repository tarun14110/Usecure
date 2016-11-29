package com.rocks.mafia.entrancesecurity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by pankaj on 13/11/16.
 */

public class SecurityHistoryViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public TextView time;
    public TextView d;
    public TextView by;
    public ImageView status;

    public SecurityHistoryViewHolder(View view) {
        super(view);

        this.title = (TextView) view.findViewById(R.id.cardTitle);
        this.time = (TextView) view.findViewById(R.id.time);
        this.d = (TextView) view.findViewById(R.id.description);
        this.by = (TextView) view.findViewById(R.id.by);
        this.status = (ImageView) view.findViewById(R.id.status);

    }
}
