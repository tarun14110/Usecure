package com.rocks.mafia.entrancesecurity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

public class single_item_search extends AppCompatActivity {

    private static final String TAG = SecurityMainActivity.class.getSimpleName();

    TextView textName;
    TextView textContact;
    TextView textEmail;
    TextView textAddress;
    ImageView imageView;
    Button  sendRequest;
    String name,contact,address,email;
    int img;

    public single_item_search()
    {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item_search);
        // Get the intent from ListViewAdapter
        Intent i = getIntent();
        // Get the results of name
        name = i.getStringExtra("name");
        // Get the results of contact
        contact = i.getStringExtra("contact");
        // Get the results of address
        address = i.getStringExtra("address");
        // Get the results of img
        img = i.getIntExtra("img", img);

        // Locate the TextViews in singleitemview.xml
        textName = (TextView) findViewById(R.id.name);
        textContact = (TextView) findViewById(R.id.contact);
        textAddress = (TextView) findViewById(R.id.address);

        // Locate the ImageView in singleitemview.xml
        imageView = (ImageView) findViewById(R.id.uploadImage);

        // Load the results into the TextViews
        textName.setText(name);
        textContact.setText(contact);
        //kmtextAddress.setText(address);
        // Load the image into the ImageView
       // imgflag.setImageResource(flag);

        sendRequest=(Button)findViewById(R.id.sendRequest);
        sendRequest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });

    }
    protected void showInputDialog()
    {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.security_create_request, null);

        final EditText name = (EditText) promptView.findViewById(R.id.editname);
        final EditText reason = (EditText) promptView.findViewById(R.id.editReason);
        final EditText time = (EditText) promptView.findViewById(R.id.editTime);
        final EditText whomToContact = (EditText) promptView.findViewById(R.id.whomToContactText);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
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

