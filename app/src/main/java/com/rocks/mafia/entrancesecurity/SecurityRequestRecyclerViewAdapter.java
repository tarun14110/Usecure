package com.rocks.mafia.entrancesecurity;

/**
 * Created by mafia on 21/10/16.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rocks.mafia.entrancesecurity.Services.ProfileHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import static com.rocks.mafia.entrancesecurity.R.id.parallax;
import static com.rocks.mafia.entrancesecurity.R.id.recyclerView;

public class SecurityRequestRecyclerViewAdapter extends RecyclerView.Adapter<SecurityRequestViewHolder>
{
    private ArrayList<SecurityRequestNode> arrayList;
    private Context context;


    public SecurityRequestRecyclerViewAdapter(Context context,ArrayList<SecurityRequestNode> arrayList)
    {
        this.context = context;
        this.arrayList = arrayList;
    }


    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);

    }

    @Override
    public void onBindViewHolder(SecurityRequestViewHolder holder,
                                 int position)
    {
        final SecurityRequestViewHolder mainHolder = (SecurityRequestViewHolder) holder;
        //Setting text over textview
        if(arrayList!=null) {
            mainHolder.title.setText(arrayList.get(position).getOutsiderName());
            mainHolder.time.setText(arrayList.get(position).getEntryTime().toString());
            mainHolder.d.setText(arrayList.get(position).getReason());
            String contact=arrayList.get(position).getInsiderContact();
            System.out.println("CONTACT" + contact);
            ProfileHandler profileHandler =new ProfileHandler(this.context);

           String name= profileHandler.getUserName(contact);
            mainHolder.by.setText(contact+"\n"+name);
            if (arrayList.get(position).getImage() != null)
                mainHolder.takenImage.setImageBitmap(getBitmapImage(arrayList.get(position).getImage()));


            int s = arrayList.get(position).getStatus();

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
    public SecurityRequestViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
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

    public void  add(int pos, SecurityRequestNode node)
    {
        arrayList.add(pos,node);
        //Collections.reverse(arrayList);
    }
    // convert from byte array to bitmap
    public Bitmap getBitmapImage(byte[] image) {
        if(image == null)
            return null;
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }


}
