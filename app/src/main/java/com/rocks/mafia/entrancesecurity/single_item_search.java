package com.rocks.mafia.entrancesecurity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class single_item_search extends AppCompatActivity {

    TextView textName;
    TextView textContact;
    TextView textEmail;
    TextView textAddress;
    ImageView imageView;
    String name,contact,address,email;
    int img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item_search);
        // Get the intent from ListViewAdapter
        Intent i = getIntent();
        // Get the results of rank
        name = i.getStringExtra("name");
        // Get the results of country
        contact = i.getStringExtra("contact");
        // Get the results of population
        address = i.getStringExtra("address");
        // Get the results of flag
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
    }
    }

