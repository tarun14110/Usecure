package com.rocks.mafia.entrancesecurity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rocks.mafia.entrancesecurity.Nodes.SecurityRequestNode;
import com.rocks.mafia.entrancesecurity.SqliteHandlers.SecurityRequestHandler;
import com.rocks.mafia.entrancesecurity.Utils.NetworkUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

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

// after item searched from the search activity this activity start
//this activity handle direct request sending

public class single_item_search extends AppCompatActivity {

    public static final String ACCOUNT_SID = "AC5590c4ed74e1ba927995055348e6e3ca";
    public static final String AUTH_TOKEN = "ab215f3f60a90eb52c9d69c38c1f7697";
    private static final String TAG = SecurityMainActivity.class.getSimpleName();
    private static final int CAMERA_REQUEST = 1888;
    private static final String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm";
    NetworkUtils networkUtils = new NetworkUtils();
    TextView textName;
    TextView textContact;
    TextView textEmail;
    TextView textAddress;
    ImageView imageView;
    Button sendRequest;
    String name, contact, address, email;
    byte[] img;
    byte[] taken_img;
    String requestId = null;
    private int DetailsStatus = 0;
    private byte[] CameraImg;


    public single_item_search() {

    }

    private static String getRandomString(final int sizeOfRandomString) {
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(sizeOfRandomString);
        for (int i = 0; i < sizeOfRandomString; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item_search);
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle("USecure ");
        ab.setDisplayHomeAsUpEnabled(true);
        // Get the intent from ListViewAdapter
        Intent i = getIntent();
        // Get the results of name
        name = i.getStringExtra("name");
        // Get the results of contact
        contact = i.getStringExtra("contact");

        //get email data
        email = i.getStringExtra("email");
        // Get the results of address
        address = i.getStringExtra("address");
        // Get the results of img
        img = i.getByteArrayExtra("img");


        // Locate the TextViews in singleitemview.xml
        textName = (TextView) findViewById(R.id.name);
        textContact = (TextView) findViewById(R.id.contact);
        textAddress = (TextView) findViewById(R.id.address);
        textEmail = (TextView) findViewById(R.id.email);

        // Locate the ImageView in singleitemview.xml
        imageView = (ImageView) findViewById(R.id.image);

        // Load the results into the TextViews
        if (name != null)
            textName.setText(name);
        if (contact != null)
            textContact.setText(contact);
        if (email != null)
            textEmail.setText(email);
        if (address.length() > 0)
            textAddress.setText(address);
        else
            textAddress.setText("Address");
        imageView.setImageBitmap(getBitmapImage(img));

        sendRequest = (Button) findViewById(R.id.sendRequest);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog(contact);
            }
        });

    }

    // convert from byte array to bitmap
    public Bitmap getBitmapImage(byte[] image) {
        if (image == null)
            return null;
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    //showing dialogue for creating new REquest
    public void showInputDialog(String contact) {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.security_create_request, null);

        final EditText name = (EditText) promptView.findViewById(R.id.editname);
        final EditText reason = (EditText) promptView.findViewById(R.id.editReason);
        final EditText time = (EditText) promptView.findViewById(R.id.editTime);
        final Button takeImage = (Button) promptView.findViewById(R.id.takeImage);
        time.setText(getDateTime());
        time.setEnabled(false);

        int x = 0;
        final EditText whomToContact = (EditText) promptView.findViewById(R.id.whomToContactText);
        whomToContact.setText(contact);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setCancelable(false)

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
//overriding the methods
                    }

                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                //simply canceling the dialofuw on cancel.
                            }
                        });


        // create an alert dialog
        final AlertDialog alert = alertDialogBuilder.create();
        alert.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        alert.show();

        //poitive action of the dialogue
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean wantToCloseDialog = false;
                String givenName = name.getText().toString();
                String givenReason = reason.getText().toString();
                String givenTime = time.getText().toString();
                String givenWhomToContact = whomToContact.getText().toString();

                NetworkUtils n = new NetworkUtils();

                if ((givenName.length() == 0) || (givenReason.length() == 0) || (givenTime.length() == 0)) {
                    Toast.makeText(getApplicationContext(), "Please fill completely !", Toast.LENGTH_LONG).show();
                } else if (!(n.isMobileConnected(getApplicationContext()) || n.isWifiConnected(getApplicationContext())) && !n.isConnected(getApplicationContext())) {
                    //checking internet connections
                    Toast.makeText(getApplicationContext(), "No Internet Connection Available!", Toast.LENGTH_SHORT).show();

                } else {
                    //checking image is click or not
                    SendOutsiderdata sendOutsiderdata = new SendOutsiderdata(givenName, givenReason, givenTime, givenWhomToContact);
                    sendOutsiderdata.execute();

                    SecurityRequestNode node;
                    SecurityRequestHandler requestHandler = new SecurityRequestHandler(getApplicationContext());

                    // generating request Id
                    try {
                        requestId = URLEncoder.encode(getRandomString(10), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    //if image clicked ? then save the image otherwise use default constructor of node
                    if (taken_img != null)
                        node = new SecurityRequestNode(givenName, givenReason, givenWhomToContact, givenTime, requestId, taken_img, 1);
                    else
                        node = new SecurityRequestNode(givenName, givenReason, givenWhomToContact, givenTime, requestId, 1);


                    requestHandler.addSecurityRequest(node);
                    SecurityRequestFragment.adapter.add(0, node);
                    SecurityRequestFragment.adapter.notifyDataSetChanged();

                    startActivity(new Intent(getBaseContext(), SecurityMainActivity.class));

                    alert.dismiss();
                }

            }
        });
        takeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //click action for takeimage.
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        //back button problem hiding the keyboard.
        alert.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    finish();
                    hideKeyboard(getParent());
                    alert.dismiss();
                }
                return true;
            }
        });

    }

    public void sendOutsiderData(String name, String reason, String time, String whomToContact) {


        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(
                "https://api.twilio.com/2010-04-01/Accounts/" + ACCOUNT_SID + "/SMS/Messages");
        String base64EncodedCredentials = "Basic "
                + Base64.encodeToString(
                (ACCOUNT_SID + ":" + AUTH_TOKEN).getBytes(),
                Base64.NO_WRAP);

        httppost.setHeader("Authorization",
                base64EncodedCredentials);
        try {


            String messageBody = "Hey someone came to meet you. http://usecure.site88.net/entryRequest.php?requestId=" + requestId;
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("From",
                    "+1 205-708-3167"));
            nameValuePairs.add(new BasicNameValuePair("To",
                    "+91" + whomToContact));                               // replace number with whom to contact
            nameValuePairs.add(new BasicNameValuePair("Body",
                    messageBody));

            httppost.setEntity(new UrlEncodedFormEntity(
                    nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
            data += "&" + URLEncoder.encode("requestId", "UTF-8") + "="
                    + requestId;

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
                sb.append("IIII " + line + "\n");
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


        if (taken_img != null) {
            String ba1 = Base64.encodeToString(taken_img, Base64.DEFAULT);

            String URL = "http://usecure.site88.net/uploadRequestPic.php";
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("base64", ba1));
            nameValuePairs.add(new BasicNameValuePair("ImageName", requestId + ".jpg"));
            try {
                HttpClient httpclien = new DefaultHttpClient();
                HttpPost httppos = new HttpPost(URL);
                httppos.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclien.execute(httppos);
                String st = EntityUtils.toString(response.getEntity());

            } catch (Exception e) {
            }
        }

    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "  HH:mm:ss dd/MM/yyyy ", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap bmp = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            taken_img = stream.toByteArray();

            DetailsStatus = 1;
        }
    }
    //take image click result taking image and saving to taken_img variable

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
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

        @Override
        protected Void doInBackground(Void... voids) {
            // sending Outsider data to the server
            sendOutsiderData(name, reason, time, whomToContact);
            return null;
        }
    }

}

