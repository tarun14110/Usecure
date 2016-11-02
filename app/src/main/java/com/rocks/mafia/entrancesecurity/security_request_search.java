package com.rocks.mafia.entrancesecurity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.R.id.list;
/*
activity for search in contacts to send Confirmation requests.
 */


public class security_request_search extends AppCompatActivity {

    ListView listView;
    SearchView searchView;
    ListViewAdapter adapter;
    ArrayList<profile_node> arraylist ;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_request_search);
        searchView = (SearchView)this.findViewById(R.id.searchView);
        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));
        searchView.setIconifiedByDefault(false);
        listView = (ListView) findViewById(R.id.listView);

        //HashMap<String, String> user=new  HashMap<String, String>();
       // SQLiteHandler handler= new SQLiteHandler(this);
         //    user =  handler.getUserDetails();
        arraylist = new ArrayList<profile_node>();
        for (int i = 0; i < 10; i++)
        {
            profile_node wp = new profile_node("name"+i,"contact"+i,
                    "address"+i, i);
            // Binds all strings into an array
            arraylist.add(wp);
        }


       adapter = new ListViewAdapter(this, arraylist);

        // Binds the Adapter to the ListView
       listView.setAdapter(adapter);
        // Capture Text in EditText
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {

                String t = text.toLowerCase(Locale.getDefault());
                adapter.filter(text);
                return false;
            }
        });

    }
    
    public class ListViewAdapter extends BaseAdapter {

        // Declare Variables
        Context mContext;
        LayoutInflater inflater;
        private List<profile_node> profile_nodelist = null;
        private ArrayList<profile_node> arraylist;

        public ListViewAdapter(Context context,
                               List<profile_node> profile_nodelist) {
            mContext = context;
            this.profile_nodelist = profile_nodelist;
            inflater = LayoutInflater.from(mContext);
            this.arraylist = new ArrayList<profile_node>();
            this.arraylist.addAll(profile_nodelist);
        }

        public class ViewHolder {
            TextView name;
            TextView contact;
            TextView email;
            TextView address;
            ImageView img;
        }

        @Override
        public int getCount() {
            return profile_nodelist.size();
        }

        @Override
        public profile_node getItem(int position) {
            return profile_nodelist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.profile_list_node, null);
                // Locate the TextViews in listview_item.xml
                holder.name = (TextView) view.findViewById(R.id.name);
                holder.contact = (TextView) view.findViewById(R.id.contact);
                holder.address = (TextView) view.findViewById(R.id.address);
                // Locate the ImageView in listview_item.xml
                holder.img = (ImageView) view.findViewById(R.id.image);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            // Set the results into TextViews
            holder.name.setText(profile_nodelist.get(position).getName());
            holder.contact.setText(profile_nodelist.get(position).getContact());
            holder.address.setText(profile_nodelist.get(position)
                    .getAddress());
            // Set the results into ImageView
           // holder.img.setImageResource(profile_nodelist.get(position)
             //       .getImg());
            // Listen for ListView Item Click
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    Toast.makeText(arg0.getContext(),"person"+profile_nodelist.get(position).getName(), Toast.LENGTH_LONG).show();
                    // Send single item click data to SingleItemView Class
                   Intent intent = new Intent(mContext, single_item_search.class);
                    // Pass all data rank
                    intent.putExtra("name",
                            (profile_nodelist.get(position).getName()));
                    // Pass all data country
                    intent.putExtra("contact",
                            (profile_nodelist.get(position).getContact()));
                    // Pass all data population
                    intent.putExtra("address",
                            (profile_nodelist.get(position).getAddress()));
                    // Pass all data flag
                    intent.putExtra("img",
                            (profile_nodelist.get(position).getImg()));
                    // Start SingleItemView Class
                    mContext.startActivity(intent);
                }
            });

            return view;
        }

        // Filter Class
        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            profile_nodelist.clear();
            if (charText.length() == 0) {
                profile_nodelist.addAll(arraylist);
            } else {
                for (profile_node wp : arraylist) {
                    if (wp.getName().toLowerCase(Locale.getDefault())
                            .contains(charText)) {
                        profile_nodelist.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }

    }

}


