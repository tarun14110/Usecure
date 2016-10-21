package com.rocks.mafia.entrancesecurity;

/**
 * Created by pankaj on 21/10/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ProfileHandler extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Profile";
    private static final String TABLE_PROFILE = "ProfileTable";
    private static final String KEY_ID = "_id";
    private static final String KEY_PERSON_NAME = "personName";
    private static final String KEY_CONTACT = "contact";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_IMAGE_URL = "imageUrl";



    public ProfileHandler(Context context)
    {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_PROFILE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +KEY_PERSON_NAME+" TEXT,"
                + KEY_CONTACT + "INTEGER, " + KEY_EMAIL + " TEXT,"  +KEY_IMAGE_URL + " TEXT"+ ")";

        Log.v("CHECK :  ",CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        onCreate(db);
    }
    public void addProfile(ProfileNode node)

    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PERSON_NAME, node.getPersonName());
        values.put(KEY_CONTACT, node.getContact());
        values.put(KEY_EMAIL, node.getEmail());
        values.put(KEY_IMAGE_URL, node.getImageUrl());
        db.insert(TABLE_PROFILE, null, values);
        db.close();

    }

    public ProfileNode getPerson(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PROFILE, new String[] { KEY_ID,
                        KEY_PERSON_NAME,KEY_CONTACT,KEY_EMAIL,KEY_IMAGE_URL }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null,null, null);
        if (cursor != null)
            cursor.moveToFirst();
        ProfileNode node = new ProfileNode(id,cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));

        return node;
    }

    public ArrayList< ProfileNode> getAllProfile()
    {
        ArrayList<  ProfileNode> ProfileList = new ArrayList<  ProfileNode>();
        String selectQuery = "SELECT * FROM " + TABLE_PROFILE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do {
                if (cursor.moveToFirst()) {
                    ProfileNode node = new ProfileNode(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
                    ProfileList.add(node);
                }
            }while (cursor.moveToNext()) ;
            }
        return ProfileList;
    }


    public void deleteProfile(ProfileNode node)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROFILE, KEY_ID + " = ?",
                new String[] { String.valueOf(node.getPersonName()) });
        db.close();
    }
    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_PROFILE);

    }
}
