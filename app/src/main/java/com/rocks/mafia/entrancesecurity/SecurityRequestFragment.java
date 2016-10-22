package com.rocks.mafia.entrancesecurity;

/**
 * Created by mafia on 21/10/16.
 */
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

public class SecurityRequestFragment extends Fragment {
    private View view;
    private static final String TAG = SecurityMainActivity.class.getSimpleName();
    private String title;//String for tab title

    private static RecyclerView recyclerView;

    public SecurityRequestFragment() {
    }

    public SecurityRequestFragment(String title) {
        this.title = title;//Setting tab title
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.e("LISTEN","EDIT");
        view = inflater.inflate(R.layout.security_request_layout, container, false);
        FloatingActionButton fab=(FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });

        setRecyclerView();
        return view;

    }

             //Setting recycler view
    private void setRecyclerView() {

        recyclerView = (RecyclerView) view
                .findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView
                .setLayoutManager(new LinearLayoutManager(getActivity()));//Linear Items

String[] s={"Ramesh Kumar ","ujeet singh","rahul sharma","jay singh","sujeet kumar","mukesh yadav","tarun","Ramesh Kumar ","ujeet singh","rahul sharma","jay singh","sujeet kumar","mukesh yadav","tarun","Ramesh Kumar ","ujeet singh","rahul sharma","jay singh","sujeet kumar","mukesh yadav","tarun"};
        String[] t={ "11:24 AM\n" +
                "Saturday, 22 October 2016 (IST)","2:24 PM\n" +
                "Saturday, 20 October 2016 (IST)","10:24 AM\n" +
                "Saturday, 19 October 2016 (IST)" ,"11:24 AM\n" +
                "Saturday, 25 October 2016 (IST)","9:20 PM\n" +
                "Saturday, 16 October 2016 (IST)","1:24 AM\n" +
                "Saturday, 20 October 2016 (IST)","9:24 AM\n" +
                "Saturday, 25 October 2016 (IST)","8:24 AM\n" +
                "Saturday, 17 October 2016 (IST)","11:24 AM\n" +
                "Saturday, 22 October 2016 (IST)","2:24 PM\n" +
                "Saturday, 20 October 2016 (IST)","10:24 AM\n" +
                "Saturday, 19 October 2016 (IST)" ,"11:24 AM\n" +
                "Saturday, 25 October 2016 (IST)","9:20 PM\n" +
                "Saturday, 16 October 2016 (IST)","1:24 AM\n" +
                "Saturday, 20 October 2016 (IST)","9:24 AM\n" +
                "Saturday, 25 October 2016 (IST)","8:24 AM\n" +
                "Saturday, 17 October 2016 (IST)" ,"11:24 AM\n" +
                "Saturday, 22 October 2016 (IST)","2:24 PM\n" +
                "Saturday, 20 October 2016 (IST)","10:24 AM\n" +
                "Saturday, 19 October 2016 (IST)" ,"11:24 AM\n" +
                "Saturday, 25 October 2016 (IST)","9:20 PM\n" +
                "Saturday, 16 October 2016 (IST)","1:24 AM\n" +
                "Saturday, 20 October 2016 (IST)","9:24 AM\n" +
                "Saturday, 25 October 2016 (IST)","8:24 AM\n" +
                "Saturday, 17 October 2016 (IST)"};
        String[] d={"meeting with Prof.Jalote at the auditorium C11","To attend workshop conducted by Adobe for Future technologies","want to meet my friend tarun room no. c111"," from Flipkart for customer Rahul Vedh ,Room no.c231"," Want to meet Faculty Prof. Rajiv Raman","Invited to attend seminar by Prof. Raj ayyer","want to meet my son Mukesh Kumar Yadav (Student of IIITD)","meeting with Prof.Jalote at the auditorium C11","To attend workshop conducted by Adobe for Future technologies","want to meet my friend tarun room no. c111"," from Flipkart for customer Rahul Vedh ,Room no.c231"," Want to meet Faculty Prof. Rajiv Raman","Invited to attend seminar by Prof. Raj ayyer","want to meet my son Mukesh Kumar Yadav (Student of IIITD)","meeting with Prof.Jalote at the auditorium C11","To attend workshop conducted by Adobe for Future technologies","want to meet my friend tarun room no. c111"," from Flipkart for customer Rahul Vedh ,Room no.c231"," Want to meet Faculty Prof. Rajiv Raman","Invited to attend seminar by Prof. Raj ayyer","want to meet my son Mukesh Kumar Yadav (Student of IIITD)"};
       String[] by={"sujeet kumar","mukesh yadav","tarun","Ramesh Kumar ","ujeet singh","rahul sharma","jay singh","sujeet kumar","mukesh yadav","sujeet kumar","mukesh yadav","tarun","Ramesh Kumar ","ujeet singh","rahul sharma","jay singh","sujeet kumar","mukesh yadav","sujeet kumar","mukesh yadav","tarun","Ramesh Kumar ","ujeet singh","rahul sharma","jay singh","sujeet kumar","mukesh yadav"};

        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> arrayTime = new ArrayList<>();
        ArrayList<String> arrayd = new ArrayList<>();
        ArrayList<String> arrayby = new ArrayList<>();
        for (int i = 0; i < 20; i++)
        {
            arrayTime.add(t[i]);
            arrayList.add(s[i]);//Adding items to recycler view
            arrayd.add(d[i]);
            arrayby.add(by[i]);
        }
        SecurityRequestRecyclerViewAdapter adapter = new SecurityRequestRecyclerViewAdapter(getActivity(), arrayList,arrayTime,arrayd,arrayby);
        recyclerView.setAdapter(adapter);// set adapter on recyclerview

    }
    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.security_create_request, null);

        final EditText name = (EditText) promptView.findViewById(R.id.editname);
        final EditText reason = (EditText) promptView.findViewById(R.id.editReason);
        final EditText time = (EditText) promptView.findViewById(R.id.editTime);
        final EditText whomToContact = (EditText) promptView.findViewById(R.id.whomToContactText);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)

                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        String givenName=name.getText().toString();
                        String givenReason=reason.getText().toString();
                        String givenTime=time.getText().toString();
                        String givenWhomToContact = whomToContact.getText().toString();
                        Log.e("BHAI", givenWhomToContact);
                        SendOutsiderdata sendOutsiderdata = new SendOutsiderdata(givenName, givenReason, givenTime, givenWhomToContact);
                        sendOutsiderdata.execute();
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
        // sending gcm token to server
        // Create data variable for sent values to server
        SessionManager session = new SessionManager(getActivity());
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
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL("http://usecure.site88.net/handleSecurityRequest.php");

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();
        }
        catch(Exception ex)
        {
            Log.e(TAG, "Error while sending RegId data " + ex.toString());
        }
        finally
        {
            try
            {

                reader.close();
            }

            catch(Exception ex) {}
        }

        // Show response on activity
        // content.setText( text  );
        if (text.contains("error")) {
            Log.e(TAG, "Error while Sending request to the server" + text);
        } else {
            Log.e(TAG, "Succesfully Sent request to the server" + text);

        }


        Log.e(TAG, "sendRegistrationToServer: " + whomToContact + "  to contact " +session.getContact());
    }

    public class SendOutsiderdata extends AsyncTask<Void,Void,Void> {

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

        @Override
        protected Void doInBackground(Void... voids) {
            // sending Outsider data to the server
            sendOutsiderData(name, reason, time, whomToContact);
            return null;
        }
    }

}