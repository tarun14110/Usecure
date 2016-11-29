package com.rocks.mafia.entrancesecurity.UserEnd;

/**
 * Created by pankaj on 22/10/16.
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

public class RequestHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Request";
    private static final String TABLE_REQUEST = "RequestTable";
    private static final String KEY_ID = "_id";
    private static final String KEY_TIME = "Time";
    private static final String KEY_MESSAGE = "message";


    public RequestHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_REQUEST + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + KEY_MESSAGE + " TIME,"
                + KEY_TIME + ")";

        Log.v("CHECK :  ", CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REQUEST);
        onCreate(db);
    }

    public void addRequest(RequestNode node)

    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE, node.getMessage());
        values.put(KEY_TIME, String.valueOf(node.getTime()));
        db.insert(TABLE_REQUEST, null, values);
        db.close();

    }

    public RequestNode getPerson(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_REQUEST, new String[]{KEY_ID,
                        KEY_MESSAGE, KEY_TIME}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        Time timeValue = null;
        try {
            timeValue = new Time(formatter.parse(cursor.getString(2)).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        RequestNode node = new RequestNode(cursor.getString(1), timeValue);

        return node;
    }

    public ArrayList<RequestNode> getAllRequest() {
        ArrayList<RequestNode> RequestList = new ArrayList<RequestNode>();
        String selectQuery = "SELECT * FROM " + TABLE_REQUEST;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DateFormat formatter = new SimpleDateFormat("HH:mm");
                Time timeValue = null;
                try {
                    timeValue = new Time(formatter.parse(cursor.getString(2)).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                RequestNode node = new RequestNode(cursor.getString(1), timeValue);
                RequestList.add(node);
            } while (cursor.moveToNext());
        }
        return RequestList;
    }


    public void deleteHistory(String m) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REQUEST, KEY_ID + " = ?",
                new String[]{m});
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_REQUEST);

    }
}
