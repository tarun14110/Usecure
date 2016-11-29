package com.rocks.mafia.entrancesecurity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
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
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
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
import java.util.HashMap;
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

import static android.R.attr.data;
import static android.R.id.list;
import static com.rocks.mafia.entrancesecurity.R.id.sendRequest;
/*
activity for search in contacts to send Confirmation requests.
it will contain a list all profiles currently registered.
PROVIDES
- it provide you to search though the profiles.
- search can be don on bases of address, name, contact.
- each profile also have direct create request button for creating request query.
 */


public class security_request_search extends AppCompatActivity
{

    ListView listView;
    SearchView searchView;
    ListViewAdapter adapter;
    //containg the profiles
    ArrayList<profile_node> arraylist;
    String requestId = null;

    private static final String TAG = SecurityMainActivity.class.getSimpleName();

    ////////--- twilio sid for sending messeges
    public static final String ACCOUNT_SID = "ACcd5209b80f9ee5935b41dd30f44263be";
    public static final String AUTH_TOKEN = "6579a844b1bf92d7a5908d2e62a4618f";
    //////-----


    private int DetailsStatus=0;
    private static final int CAMERA_REQUEST = 1888;

    private   byte[] img;
    Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
      //layout attached
        setContentView(R.layout.activity_security_request_search);
        searchView = (SearchView) this.findViewById(R.id.searchView);
        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));
        searchView.setIconifiedByDefault(false);


       //list containg the profiles
        listView = (ListView) findViewById(R.id.listView);
        arraylist = new ArrayList<profile_node>();

        //get data of profiles from  the database SQLlITE TABLE
         ProfileHandler handler= new ProfileHandler(this);
        arraylist =  handler.getAllProfiles();


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

        public View getView(final int position, View view, ViewGroup parent)
        {
            final ViewHolder holder;
            if (view == null)
            {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.profile_list_node, null);
                // Locate the TextViews in listview_item.xml
                holder.name = (TextView) view.findViewById(R.id.name);
                holder.contact = (TextView) view.findViewById(R.id.contact);
                holder.email = (TextView) view.findViewById(R.id.email);
                holder.address = (TextView) view.findViewById(R.id.address);

                // Locate the ImageView in listview_item.xml
                holder.img = (ImageView) view.findViewById(R.id.image);
                view.setTag(holder);
            } else
            {
                holder = (ViewHolder) view.getTag();
            }



            // Set the results into TextViews
            holder.name.setText(profile_nodelist.get(position).getName());
            holder.contact.setText(profile_nodelist.get(position).getContact());
            holder.email.setText(profile_nodelist.get(position).getEmail());
            holder.address.setText(profile_nodelist.get(position)
                    .getAddress());
            // Set the results into ImageView

            holder.img.setImageBitmap(getBitmapImage(profile_nodelist.get(position)
                    .getImg()));
            // Listen for ListView Item Click
            Button sendRequest = (Button) view.findViewById(R.id.sendRequest);
            sendRequest.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0)

                {
                    showInputDialog(profile_nodelist.get(position).getContact());

                }

            });
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    Toast.makeText(arg0.getContext(), "person" + profile_nodelist.get(position).getContact(), Toast.LENGTH_LONG).show();
                    // Send single item click data to SingleItemView Class
                    Intent intent = new Intent(mContext, single_item_search.class);
                    // Pass all data name
                    intent.putExtra("name",
                            (profile_nodelist.get(position).getName()));
                    // Pass all data contact
                    intent.putExtra("contact",
                            (profile_nodelist.get(position).getContact()));
                    intent.putExtra("email",
                            (profile_nodelist.get(position).getEmail()));
                    // Pass all data address
                    intent.putExtra("address",
                            (profile_nodelist.get(position).getAddress()));
                    // Pass all data img
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
        // search filter on the bases of name. contact, address
        public void filter(String charText)
        {
            charText = charText.toLowerCase(Locale.getDefault());
            profile_nodelist.clear();
            if (charText.length() == 0)
            {
                profile_nodelist.addAll(arraylist);
            } else
            {
                for (profile_node wp : arraylist)
                {
                    if ((wp.getName().toLowerCase(Locale.getDefault()).contains(charText))|| (wp.getContact().toLowerCase(Locale.getDefault()).contains(charText)))
                    {

                        profile_nodelist.add(wp);
                    }
                    else if(wp.getAddress()!=null)
                    {
                        if (wp.getAddress().toLowerCase(Locale.getDefault())
                                .contains(charText)) {
                            profile_nodelist.add(wp);
                        }
                    }
                }
            }
            notifyDataSetChanged();
        }

    }



    //showing dialogue for creating new REquest
    public void showInputDialog(String contact)
    {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.security_create_request, null);

        final EditText name = (EditText) promptView.findViewById(R.id.editname);
        final EditText reason = (EditText) promptView.findViewById(R.id.editReason);
        final EditText time = (EditText) promptView.findViewById(R.id.editTime);
        final Button takeImage =(Button) promptView.findViewById(R.id.takeImage);
        time.setText(getDateTime());
        time.setEnabled(false);

        int x=0;
        final EditText whomToContact = (EditText) promptView.findViewById(R.id.whomToContactText);
        whomToContact.setText(contact);
        final  AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setCancelable(false)

                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
//overriding the methods
                    }

                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                //simply canceling the dialofuw on cancel.
                            }
                        });


        // create an alert dialog
        final AlertDialog alert = alertDialogBuilder.create();
        alert.show();

        //poitive action of the dialogue
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Boolean wantToCloseDialog = false;
                String givenName = name.getText().toString();
                String givenReason = reason.getText().toString();
                String givenTime = time.getText().toString();
                String givenWhomToContact = whomToContact.getText().toString();

                if((givenName.length()>0)&&(givenReason.length()>0)&&(givenTime.length()>0)&&(givenWhomToContact.length()>0))
                {
                    //checking image is click or not
                    SendOutsiderdata sendOutsiderdata = new SendOutsiderdata(givenName, givenReason, givenTime, givenWhomToContact);
                    sendOutsiderdata.execute();

                    SecurityRequestNode node;
                    SecurityRequestHandler requestHandler= new SecurityRequestHandler(getApplicationContext());

                    // generating request Id
                    try {
                        requestId = URLEncoder.encode(getRandomString(10), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    //if image clicked ? then save the image otherwise use default constructor of node
                  if(img!=null)
                     node=  new SecurityRequestNode(givenName,givenReason,givenWhomToContact,givenTime,requestId, img,1);
                    else
                     node=  new SecurityRequestNode(givenName,givenReason,givenWhomToContact,givenTime, requestId, 1);


                    requestHandler.addSecurityRequest(node);
                    SecurityRequestFragment.adapter.add(0,node);
                    SecurityRequestFragment.adapter.notifyDataSetChanged();

                    Toast.makeText(getApplicationContext(), "Sent!", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(getBaseContext(),SecurityMainActivity.class));

                    alert.dismiss();
                }
                else
                {

                    //some details is not filled
                    Toast.makeText(getApplicationContext(), "Please fill completely !", Toast.LENGTH_LONG).show();

                }
            }
        });
        takeImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
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

    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

    private static String getRandomString(final int sizeOfRandomString)
    {
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(sizeOfRandomString);
        for(int i=0;i<sizeOfRandomString;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }


    // convert from byte array to bitmap
    public Bitmap getBitmapImage(byte[] image) {
        if(image == null)
            return null;
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    // convert from bitmap to byte array
    public static byte[] getImageBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
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
                    "+91"+whomToContact));                               // replace number with whom to contact
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
                sb.append("IIII " +line + "\n");
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




        if(img!=null)
        {
            String ba1 = Base64.encodeToString(img, Base64.DEFAULT);

            String URL = "http://usecure.site88.net/uploadRequestPic.php";
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("base64", ba1));
            nameValuePairs.add(new BasicNameValuePair("ImageName", requestId + ".jpg"));
            try {
                HttpClient httpclien = new DefaultHttpClient();
                HttpPost httppos = new HttpPost(URL);
                NetworkUtils networkUtils=new NetworkUtils();

                if(networkUtils.isConnected(getApplicationContext()))


                httppos.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclien.execute(httppos);
                String st = EntityUtils.toString(response.getEntity());

            } catch (Exception e) {
            }
        }

    }



    //asynask for sending the data
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





    // funcion formatting & giving current time
    private String getDateTime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "HH:mm:ss dd/MM/yyyy ", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    //take image click result taking image and saving to img variable

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            bmp = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp=Bitmap.createScaledBitmap(bmp, 120, 80, false);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            img = stream.toByteArray();

            Toast.makeText(getApplicationContext(), "Image DAta !"+img, Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "Saving Image!", Toast.LENGTH_LONG).show();
            DetailsStatus = 1;
        }
    }

//FUNCTION used for setting image height and width fix
    public Bitmap getResizedBitmap(Bitmap image, int maxSize)
    {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

}

