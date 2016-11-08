package com.rocks.mafia.entrancesecurity;

/**
 * Created by mafia on 21/10/16.
 */
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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
public class SecurityRequestFragment extends Fragment {
    private View view;
    private static final String TAG = SecurityMainActivity.class.getSimpleName();
    private String title;//String for tab title
    public static final String ACCOUNT_SID = "AC5590c4ed74e1ba927995055348e6e3ca";
    public static final String AUTH_TOKEN = "ab215f3f60a90eb52c9d69c38c1f7697";

    private static RecyclerView recyclerView;

    public SecurityRequestFragment()
    {

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
//        FloatingActionButton fab=(FloatingActionButton)view.findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(getContext(), security_request_search.class);
//                startActivity(intent);
//               // showInputDialog();
//            }
//        });
//
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

//String[] s={"Ramesh Kumar ","ujeet singh","rahul sharma","jay singh","sujeet kumar","mukesh yadav","tarun","Ramesh Kumar ","ujeet singh","rahul sharma","jay singh","sujeet kumar","mukesh yadav","tarun","Ramesh Kumar ","ujeet singh","rahul sharma","jay singh","sujeet kumar","mukesh yadav","tarun"};
//        String[] t={ "11:24 AM\n" +
//                "Saturday, 22 October 2016 (IST)","2:24 PM\n" +
//                "Saturday, 20 October 2016 (IST)","10:24 AM\n" +
//                "Saturday, 19 October 2016 (IST)" ,"11:24 AM\n" +
//                "Saturday, 25 October 2016 (IST)","9:20 PM\n" +
//                "Saturday, 16 October 2016 (IST)","1:24 AM\n" +
//                "Saturday, 20 October 2016 (IST)","9:24 AM\n" +
//                "Saturday, 25 October 2016 (IST)","8:24 AM\n" +
//                "Saturday, 17 October 2016 (IST)","11:24 AM\n" +
//                "Saturday, 22 October 2016 (IST)","2:24 PM\n" +
//                "Saturday, 20 October 2016 (IST)","10:24 AM\n" +
//                "Saturday, 19 October 2016 (IST)" ,"11:24 AM\n" +
//                "Saturday, 25 October 2016 (IST)","9:20 PM\n" +
//                "Saturday, 16 October 2016 (IST)","1:24 AM\n" +
//                "Saturday, 20 October 2016 (IST)","9:24 AM\n" +
//                "Saturday, 25 October 2016 (IST)","8:24 AM\n" +
//                "Saturday, 17 October 2016 (IST)" ,"11:24 AM\n" +
//                "Saturday, 22 October 2016 (IST)","2:24 PM\n" +
//                "Saturday, 20 October 2016 (IST)","10:24 AM\n" +
//                "Saturday, 19 October 2016 (IST)" ,"11:24 AM\n" +
//                "Saturday, 25 October 2016 (IST)","9:20 PM\n" +
//                "Saturday, 16 October 2016 (IST)","1:24 AM\n" +
//                "Saturday, 20 October 2016 (IST)","9:24 AM\n" +
//                "Saturday, 25 October 2016 (IST)","8:24 AM\n" +
//                "Saturday, 17 October 2016 (IST)"};
//        String[] d={"meeting with Prof.Jalote at the auditorium C11","To attend workshop conducted by Adobe for Future technologies","want to meet my friend tarun room no. c111"," from Flipkart for customer Rahul Vedh ,Room no.c231"," Want to meet Faculty Prof. Rajiv Raman","Invited to attend seminar by Prof. Raj ayyer","want to meet my son Mukesh Kumar Yadav (Student of IIITD)","meeting with Prof.Jalote at the auditorium C11","To attend workshop conducted by Adobe for Future technologies","want to meet my friend tarun room no. c111"," from Flipkart for customer Rahul Vedh ,Room no.c231"," Want to meet Faculty Prof. Rajiv Raman","Invited to attend seminar by Prof. Raj ayyer","want to meet my son Mukesh Kumar Yadav (Student of IIITD)","meeting with Prof.Jalote at the auditorium C11","To attend workshop conducted by Adobe for Future technologies","want to meet my friend tarun room no. c111"," from Flipkart for customer Rahul Vedh ,Room no.c231"," Want to meet Faculty Prof. Rajiv Raman","Invited to attend seminar by Prof. Raj ayyer","want to meet my son Mukesh Kumar Yadav (Student of IIITD)"};
//       String[] by={"sujeet kumar","mukesh yadav","tarun","Ramesh Kumar ","ujeet singh","rahul sharma","jay singh","sujeet kumar","mukesh yadav","sujeet kumar","mukesh yadav","tarun","Ramesh Kumar ","ujeet singh","rahul sharma","jay singh","sujeet kumar","mukesh yadav","sujeet kumar","mukesh yadav","tarun","Ramesh Kumar ","ujeet singh","rahul sharma","jay singh","sujeet kumar","mukesh yadav"};

        SecurityRequestHandler handler=new SecurityRequestHandler(getActivity());
       ArrayList<SecurityRequestNode> arrayList=handler.getAllSecurityRequest();
        System.out.println("DATATATA : "+ arrayList.get(0).getEntryTime());
        SecurityRequestRecyclerViewAdapter adapter = new SecurityRequestRecyclerViewAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(adapter);// set adapter on recyclerview

    }
    protected void showInputDialog()
    {



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

//
        HttpClient httpclient = new DefaultHttpClient();
//
        HttpPost httppost = new HttpPost(
                "https://api.twilio.com/2010-04-01/Accounts/{ACCOUNT_SID}/SMS/Messages");
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