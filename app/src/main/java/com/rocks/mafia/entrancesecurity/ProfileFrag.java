package com.rocks.mafia.entrancesecurity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by pankaj on 21/10/16.
 */
public class ProfileFrag extends Fragment
{
    private String mParam1;
    private String mParam2;
    private ImageView image;
    private Button selectImageButton;
    private TextView editName;
    private TextView editContact;
    private TextView editEmail;
    private final int SELECT_PHOTO = 1;
    private final int RESULT_LOAD_IMAGE=1;
    private final int RESULT_OK=1;
    final String path = android.os.Environment.DIRECTORY_DCIM;
    // Reference to our image view we will use
    private ImageView imageView;

    // Reference to picker button.
    private Button mPickPhotoButton;



    public ProfileFrag(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {


        Log.e("Test","FRAAAGGG");

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        SQLiteHandler handler= new SQLiteHandler(getActivity());
        HashMap<String, String> user =  handler.getUserDetails();


        Button editdetails = (Button) view.findViewById(R.id.editDetails);
        final Button savedetails = (Button) view.findViewById(R.id.saveDetails);
        editName=(TextView)view.findViewById(R.id.name);
        editEmail=(TextView)view.findViewById(R.id.email);
        editContact=(TextView)view.findViewById(R.id.contact);
            editName.setText(user.get("name"));
            editEmail.setText(user.get("email"));
        editContact.setText(user.get("contact"));

        editdetails.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });

        imageView = (ImageView)view.findViewById(R.id.uploadImage);
        mPickPhotoButton = (Button)view.findViewById(R.id.selectImageButton);
        mPickPhotoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent,SELECT_PHOTO);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        // Setup any handles to view objects here
        TextView t = (TextView) view.findViewById(R.id.name);
        String s=t.toString();
        Log.e("TEST","FUCK");


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
                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        editName.setText(editText.getText());
                        editEmail.setText(editemail.getText());

                        //DELETINNG ALL USERS and ADDING CURRENT USER

                        SQLiteHandler handler= new SQLiteHandler(getActivity());
                        HashMap<String, String> user =  handler.getUserDetails();
                        handler.deleteUsers();
                        handler.addUser(editName.getText().toString(),editEmail.getText().toString(),user.get("contact"));
                        user =  handler.getUserDetails();
                        String s=user.get("name");
                        Log.e("CHANGE : ",s);

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

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK)
                {
                    try
                    {
                        final Uri imageUri = imageReturnedIntent.getData();
                        ContentResolver resolver = getActivity().getContentResolver();
                        final InputStream imageStream = resolver.openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imageView.setImageBitmap(selectedImage);
                        String s=selectedImage.toString();
                        Log.e("ERROR ","IMAGE ");
                    }
                    catch (FileNotFoundException e)
                    {
                        e.printStackTrace();
                    }

                }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}

