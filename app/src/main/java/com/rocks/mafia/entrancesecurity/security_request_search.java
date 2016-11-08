package com.rocks.mafia.entrancesecurity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rocks.mafia.entrancesecurity.Services.ProfileHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.extras.Base64;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.util.EntityUtils;

import static android.R.id.list;
import static com.rocks.mafia.entrancesecurity.R.id.sendRequest;
/*
activity for search in contacts to send Confirmation requests.
 */


public class security_request_search extends AppCompatActivity {

    ListView listView;
    SearchView searchView;
    ListViewAdapter adapter;
    ArrayList<profile_node> arraylist;

    private static final String TAG = SecurityMainActivity.class.getSimpleName();
    public static final String ACCOUNT_SID = "AC5590c4ed74e1ba927995055348e6e3ca";
    public static final String AUTH_TOKEN = "ab215f3f60a90eb52c9d69c38c1f7697";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_request_search);
        searchView = (SearchView) this.findViewById(R.id.searchView);



        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));
        searchView.setIconifiedByDefault(false);
        listView = (ListView) findViewById(R.id.listView);
        arraylist = new ArrayList<profile_node>();
         ProfileHandler handler= new ProfileHandler(this);
        arraylist =  handler.getAllProfiles();

//
//        for (int i = 0; i < 10; i++) {
//            profile_node wp = new profile_node("name" + i, "contact" + i,
//                    "address" + i, Integer.toString(i));
//            // Binds all strings into an array
//            arraylist.add(wp);
//        }
        adapter = new ListViewAdapter(this, arraylist);

        // Binds the Adapter to the ListView
        listView.setAdapter(adapter);
        // Capture Text in EditText

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
             holder.img.setImageBitmap(getBitmapImage(profile_nodelist.get(position)
                   .getImg()));
            // Listen for ListView Item Click
            Button sendRequest = (Button) view.findViewById(R.id.sendRequest);
            sendRequest.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    showInputDialog();
                }

            });
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    Toast.makeText(arg0.getContext(), "person" + profile_nodelist.get(position).getName(), Toast.LENGTH_LONG).show();
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

        // convert from byte array to bitmap
        public Bitmap getBitmapImage(byte[] image) {
            if(image == null)
                return null;
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        }

        // Filter Class
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

    public void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.security_create_request, null);

        final EditText name = (EditText) promptView.findViewById(R.id.editname);
        final EditText reason = (EditText) promptView.findViewById(R.id.editReason);
        final EditText time = (EditText) promptView.findViewById(R.id.editTime);
        final EditText whomToContact = (EditText) promptView.findViewById(R.id.whomToContactText);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
       // dialog.setCanceledOnTouchOutside(true);
        alertDialogBuilder.setView(promptView);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        String givenName = name.getText().toString();
                        String givenReason = reason.getText().toString();
                        String givenTime = time.getText().toString();
                        String givenWhomToContact = whomToContact.getText().toString();
                        Log.e("BHAI", givenWhomToContact);
                       SendOutsiderdata sendOutsiderdata = new SendOutsiderdata(givenName, givenReason, givenTime, givenWhomToContact);
                        sendOutsiderdata.execute();
                       // dialog.setCanceledOnTouchOutside(false);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public void sendOutsiderData(String name, String reason, String time, String whomToContact) {

        HttpClient httpclient = new DefaultHttpClient();
//
        HttpPost httppost = new HttpPost(
                "https://api.twilio.com/2010-04-01/Accounts/" + ACCOUNT_SID + "/SMS/Messages");
        String base64EncodedCredentials = "Basic "
                + Base64.encodeToString(
                (ACCOUNT_SID + ":" + AUTH_TOKEN).getBytes(),
                Base64.NO_WRAP);

        httppost.setHeader("Authorization",
                base64EncodedCredentials);
        try {

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("From",
                    "+12018905759"));
            nameValuePairs.add(new BasicNameValuePair("To",
                    "+917053359258"));
            nameValuePairs.add(new BasicNameValuePair("Body",
                    "Welcome to Twilio"));

            httppost.setEntity(new UrlEncodedFormEntity(
                    nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            System.out.println("Entity post is: "
                    + EntityUtils.toString(entity));


        } catch (ClientProtocolException e) {

        } catch (IOException e) {

        }


        // sending gcm token to server
        // Create data variable for sent values to server
        SessionManager session = new SessionManager(this);
        String data = null;
        try {
            data = URLEncoder.encode("contact", "UTF-8")
                    + "=" + URLEncoder.encode(whomToContact, "UTF-8");
            data += "&" + URLEncoder.encode("name", "UTF-8")
                    + "=" + URLEncoder.encode(name, "UTF-8");
            data += "&" + URLEncoder.encode("reason", "UTF-8")
                    + "=" + URLEncoder.encode(reason, "UTF-8");
            data += "&" + URLEncoder.encode("time", "UTF-8") + "="
                    + URLEncoder.encode(time, "UTF-8");

            Log.e("DATATATA", data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        String text = "";
        BufferedReader reader = null;

        // Send data
        try {

            // Defined URL  where to send data
            URL url = new URL("http://usecure.site88.net/handleSecurityRequest.php");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();
        } catch (Exception ex) {
            Log.e(TAG, "Error while sending RegId data " + ex.toString());
        } finally {
            try {

                reader.close();
            } catch (Exception ex) {
            }
        }

        // Show response on activity
        // content.setText( text  );
        if (text.contains("error")) {
            Log.e(TAG, "Error while Sending request to the server" + text);
        } else {
            Log.e(TAG, "Succesfully Sent request to the server" + text);

        }


        Log.e(TAG, "sendRegistrationToServer: " + whomToContact + "  to contact " + session.getContact());
    }

    public class SendOutsiderdata extends AsyncTask<Void, Void, Void> {

        String name, reason, time, whomToContact;

        SendOutsiderdata(String name, String reason, String time, String whomToContact) {
            this.name = name;
            this.reason = reason;
            this.time = time;
            this.whomToContact = whomToContact;

        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
//
//        public boolean onTouchEvent(MotionEvent event) {
//            InputMethodManager imm = (InputMethodManager)getSystemService(Context.
//                    INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//            return true;
//        }

        @Override
        protected Void doInBackground(Void... voids) {
            // sending Outsider data to the server
            sendOutsiderData(name, reason, time, whomToContact);
            return null;
        }

    }
}

