package com.rocks.mafia.entrancesecurity.Services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rocks.mafia.entrancesecurity.Nodes.profile_node;

import java.util.ArrayList;

/**
 * Created by pankaj on 2/11/16.
 */

public class ProfileHandler extends SQLiteOpenHelper {

    private static final String TAG = ProfileHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 6;

    // Database Name
    private static final String DATABASE_NAME = "uSecure";

    // Login table name
    private static final String TABLE_PROFILE = "profile";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_CONTACT = "contact";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_ROLLNUMBER = "rollNumber";
    private static final String KEY_IMAGE = "image";

    public ProfileHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PROFILE_TABLE = "CREATE TABLE " + TABLE_PROFILE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE,"
                + KEY_CONTACT + " TEXT UNIQUE,"
                + KEY_ADDRESS + " TEXT,"
                + KEY_ROLLNUMBER + " TEXT UNIQUE,"
                + KEY_IMAGE + " BLOB "
                + ")";
        db.execSQL(CREATE_PROFILE_TABLE);
        Log.d(TAG, "Database tables created " + CREATE_PROFILE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);

        // Create tables again
        onCreate(db);
    }


    /**
     * Storing user details in database
     */
    public void addUser(String name, String email, String contact, String address, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_CONTACT, contact); // Contact
        values.put(KEY_ADDRESS, address);  //address
        values.put(KEY_IMAGE, image);
        // Inserting Row
        long id = db.insert(TABLE_PROFILE, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New profile inserted into sqlite: " + name);
    }

    public void addUser(String name, String email, String contact, String address) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_CONTACT, contact); // Contact
        values.put(KEY_ADDRESS, address);  //address
        // Inserting Row
        long id = db.insert(TABLE_PROFILE, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New profile inserted into sqlite: " + name);
    }


    public void addUsers(ArrayList<profile_node> profiles) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i = 0; i < profiles.size(); ++i) {
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, profiles.get(i).getName()); // Name
            values.put(KEY_EMAIL, profiles.get(i).getEmail()); // Email
            values.put(KEY_CONTACT, profiles.get(i).getContact()); // Contact
            values.put(KEY_ADDRESS, profiles.get(i).getAddress());  //address
            values.put(KEY_IMAGE, profiles.get(i).getImg());  //address
            // Inserting Row
            long id = db.insert(TABLE_PROFILE, null, values);
            Log.d(TAG, "New profile inserted into sqlite: " + profiles.get(i).getName() + profiles.get(i).getImg());

        }
        db.close(); // Closing database connection

    }

    public boolean checkUser(String contact)

    {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "Select * from " + TABLE_PROFILE + " where " + KEY_CONTACT + " = " + contact;
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public int updateUser(String name, String email, String contact, String address, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_EMAIL, email);
        values.put(KEY_ADDRESS, address);  //address
        values.put(KEY_IMAGE, image);
        return db.update(TABLE_PROFILE, values, KEY_CONTACT + " = ?",
                new String[]{contact});
    }

    public int updateImage(String contact, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_IMAGE, image);
        return db.update(TABLE_PROFILE, values, KEY_CONTACT + " = ?",
                new String[]{contact});
    }

    public void deleteUser(String contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROFILE, KEY_CONTACT + " = ?",
                new String[]{contact});
        db.close();
    }


    /**
     * Getting user data from database
     */
    public ArrayList<profile_node> getAllProfiles() {
        ArrayList<profile_node> profileList = new ArrayList<profile_node>();
        String selectQuery = "SELECT * FROM " + TABLE_PROFILE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                profile_node node = new profile_node(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getBlob(6));
                profileList.add(node);
            } while (cursor.moveToNext());
        }
        return profileList;
    }

    /**
     * Re crate database Delete all rows and create table again
     */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_PROFILE, null, null);
        db.close();

        Log.d(TAG, "Deleted all profiles info from sqlite");
    }

    public String getUserName(String contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "Select * from " + TABLE_PROFILE + " where " + KEY_CONTACT + " = " + contact;
        Cursor cursor = db.rawQuery(Query, null);
        String name = null;
        if (cursor.moveToFirst()) {
            name = cursor.getString(1);
        }
        return name;
    }
}
