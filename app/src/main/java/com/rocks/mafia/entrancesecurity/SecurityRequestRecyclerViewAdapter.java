package com.rocks.mafia.entrancesecurity;

/**
 * Created by mafia on 21/10/16.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rocks.mafia.entrancesecurity.Nodes.SecurityRequestNode;
import com.rocks.mafia.entrancesecurity.Services.ProfileHandler;

import java.util.ArrayList;

public class SecurityRequestRecyclerViewAdapter extends RecyclerView.Adapter<SecurityRequestViewHolder> {
    private ArrayList<SecurityRequestNode> arrayList;
    private Context context;


    public SecurityRequestRecyclerViewAdapter(Context context, ArrayList<SecurityRequestNode> arrayList) {
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
        if (arrayList != null) {
            mainHolder.title.setText(arrayList.get(position).getOutsiderName());
            //taking time and converting itinto two entity DATA AND TIME

            String dateTime = arrayList.get(position).getEntryTime();
            String[] str = dateTime.trim().split(" ");
            if (str[0] != null)
                mainHolder.time.setText(str[0]);
            mainHolder.d.setText(arrayList.get(position).getReason());


            if (str[1] != null)
                mainHolder.date.setText(str[1]);
            String contact = arrayList.get(position).getInsiderContact();


            //get the user name by contact to show the insider data on request

            ProfileHandler profileHandler = new ProfileHandler(this.context);
            String name = profileHandler.getUserName(contact);
            mainHolder.by.setText(contact + "\n" + name);
            if (arrayList.get(position).getImage() != null)
                mainHolder.takenImage.setImageBitmap(getBitmapImage(arrayList.get(position).getImage()));


            int s = arrayList.get(position).getStatus();

            //changing the status depending upon the value stored in DATAbase

            final int pos = position;
            if (s == 1)
                mainHolder.status.setBackgroundResource(R.mipmap.ic_yello);

            else if (s == 2)
                mainHolder.status.setBackgroundResource(R.drawable.cross);
            else
                mainHolder.status.setBackgroundResource(R.drawable.tick);
        }
    }

    @Override
    public SecurityRequestViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
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

    public void add(int pos, SecurityRequestNode node) {
        arrayList.add(pos, node);
    }

    // convert from byte array to bitmap
    public Bitmap getBitmapImage(byte[] image) {
        if (image == null)
            return null;
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }


}
