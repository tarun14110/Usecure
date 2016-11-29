package com.rocks.mafia.entrancesecurity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.rocks.mafia.entrancesecurity.SqliteHandlers.SQLiteHandler;
import com.rocks.mafia.entrancesecurity.UserEnd.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

/**
 * Created by pankaj on 21/10/16.
 */


//Showing the profiles
public class ProfileFrag extends Fragment {
    final String path = android.os.Environment.DIRECTORY_DCIM;
    private final int SELECT_PHOTO = 1;
    private final int PICK_IMAGE_REQUEST = 3;
    Context applicationContext = MainActivity.getContextOfApplication();
    private TextView editName;
    private TextView editContact;
    private TextView editEmail;
    private Bitmap bitmap;
    private String UPLOAD_URL = "http://usecure.site88.net/setProfileImage.php";
    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";
    private String KEY_EMAIL = "email";
    private String KEY_ADRESS = "Address";
    private String KEY_CONTACT = "contact";
    // Reference to our image view we will use
    private ImageView imageView;

    // Reference to picker button.
    private Button mPickPhotoButton;


    public ProfileFrag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView userinfo = (TextView) view.findViewById(R.id.user);
        SessionManager sm = new SessionManager(getActivity());
        if (sm.isSecurityLoggedIn())
            userinfo.setText("Security Staff");

        SQLiteHandler handler = new SQLiteHandler(getActivity());
        HashMap<String, String> user = handler.getUserDetails();


        Button editdetails = (Button) view.findViewById(R.id.editDetails);
        final Button savedetails = (Button) view.findViewById(R.id.saveDetails);
        editName = (TextView) view.findViewById(R.id.name);
        editEmail = (TextView) view.findViewById(R.id.email);
        editContact = (TextView) view.findViewById(R.id.contact);
        editName.setText(user.get("name"));
        editEmail.setText(user.get("email"));
        editContact.setText(user.get("contact"));

        editdetails.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });

        imageView = (ImageView) view.findViewById(R.id.uploadImage);
        mPickPhotoButton = (Button) view.findViewById(R.id.selectImageButton);
        mPickPhotoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(intent, PICK_IMAGE_REQUEST);
                }
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        TextView t = (TextView) view.findViewById(R.id.name);
        String s = t.toString();


    }

    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View promptView = layoutInflater.inflate(R.layout.edit_details, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edtbox);
        editText.setText(editName.getText());
        final EditText editemail = (EditText) promptView.findViewById(R.id.email);
        editemail.setText(editEmail.getText());

        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        editName.setText(editText.getText());
                        editEmail.setText(editemail.getText());

                        //DELETING ALL USERS and ADDING CURRENT USER

                        SQLiteHandler handler = new SQLiteHandler(getActivity());
                        HashMap<String, String> user = handler.getUserDetails();
                        handler.deleteUsers();
                        handler.addUser(editName.getText().toString(), editEmail.getText().toString(), user.get("contact"));
                        user = handler.getUserDetails();
                        String s = user.get("name");
                        Log.e("CHANGE : ", s);

                        handler.close();

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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case SELECT_PHOTO:
                //if(resultCode == RESULT_OK)
            {
                try {
                    final Uri imageUri = imageReturnedIntent.getData();
                    ContentResolver resolver = getActivity().getContentResolver();
                    final InputStream imageStream = resolver.openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    imageView.setImageBitmap(selectedImage);
                    String s = selectedImage.toString();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
            break;
            case PICK_IMAGE_REQUEST: {
                Uri filePath = imageReturnedIntent.getData();
                try {
                    //Getting the Bitmap from Gallery
                    bitmap = MediaStore.Images.Media.getBitmap(applicationContext.getContentResolver(), filePath);
                    imageView.setImageBitmap(bitmap);

                    uploadProfileData();
                    //Setting the Bitmap to ImageView
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadProfileData() {


        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put(KEY_IMAGE, getStringImage(bitmap));
        SessionManager sessionManager = new SessionManager(getActivity());
        params.put(KEY_CONTACT, sessionManager.getContact());
        client.post(UPLOAD_URL, params, new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String res) {
                        // called when response HTTP status is "200 OK"
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                    }
                }
        );


    }

}

