package com.rocks.mafia.entrancesecurity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by root on 21/10/16.
 */

public class SecurityPreRequestViewHolder extends RecyclerView.ViewHolder {
    public TextView title;


    public SecurityPreRequestViewHolder(View view) {
        super(view);

        this.title = (TextView) view.findViewById(R.id.cardTitle);
    }
}
