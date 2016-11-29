package com.rocks.mafia.entrancesecurity.SqliteHandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rocks.mafia.entrancesecurity.Nodes.SecurityRequestNode;

import java.util.ArrayList;


/**
 * Created by pankaj on 12/11/16.
 */


//Sqllite handler of the Historytab


public class SecurityHistoryHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SecurityHistory";
    private static final String TABLE_HISTORY = "SecurityHistoryTable";
    private static final String KEY_ID = "_id";
    private static final String KEY_TIME = "Time";
    private static final String KEY_OUTSIDERNAME = "OutsiderName";
    private static final String KEY_REASON = "Reason";
    private static final String KEY_INSIDERCONTACT = "InsiderContact";
    private static final String KEY_STATUS = "Status";


    public SecurityHistoryHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_HISTORY + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + KEY_OUTSIDERNAME + " TEXT,"
                + KEY_REASON + " TEXT ,"
                + KEY_INSIDERCONTACT + " TEXT ,"
                + KEY_TIME + " TEXT ,"
                + KEY_STATUS + " INTEGER"
                + ")";

        Log.v("CHECK :  ", CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
        ;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        onCreate(db);
    }

    public void delete() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        onCreate(db);
    }

    //adding new node in the list
    public void addSecurityHistory(SecurityRequestNode node)

    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_OUTSIDERNAME, node.getOutsiderName());
        values.put(KEY_REASON, node.getReason());
        values.put(KEY_INSIDERCONTACT, node.getInsiderContact());
        values.put(KEY_TIME, node.getEntryTime());
        values.put(KEY_STATUS, String.valueOf(node.getStatus()));
        db.insert(TABLE_HISTORY, null, values);
        db.close();

    }

    //get all details
    public ArrayList<SecurityRequestNode> getAllSecurityHistory() {
        ArrayList<SecurityRequestNode> HistoryList = new ArrayList<SecurityRequestNode>();
        String selectQuery = "SELECT * FROM " + TABLE_HISTORY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                SecurityRequestNode node = new SecurityRequestNode(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), "", Integer.parseInt(cursor.getString(5)));
                HistoryList.add(node);
            } while (cursor.moveToNext());
        }
        db.close();
        return HistoryList;
    }
}

