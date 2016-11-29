package com.rocks.mafia.entrancesecurity.SqliteHandlers;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rocks.mafia.entrancesecurity.Nodes.SecurityPreRequestNode;

import java.util.ArrayList;

/**
 * Created by pankaj on 28/11/16.
 */

//Sqllite table for PreRequest
public class SecurityPreRequestHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "PreRequest";
    private static final String TABLE_REQUEST = "PreRequestTable";
    private static final String KEY_ID = "_id";
    private static final String KEY_OUTSIDERNAME = "OutsiderName";
    private static final String KEY_REASON = "Reason";
    private static final String KEY_INSIDERCONTACT = "InsiderContact";
    private static final String KEY_TIME = "Time";


    public SecurityPreRequestHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_REQUEST + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + KEY_OUTSIDERNAME + " TEXT,"
                + KEY_REASON + " TEXT ,"
                + KEY_INSIDERCONTACT + " TEXT ,"
                + KEY_TIME + " TEXT "
                + ")";

        Log.v("CHECK :  ", CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
        ;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REQUEST);
        onCreate(db);
    }

    public void delete() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REQUEST);
        onCreate(db);
    }

    public void addSecurityPreRequest(SecurityPreRequestNode node) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_OUTSIDERNAME, node.getOutsiderName());
        values.put(KEY_REASON, node.getReason());
        values.put(KEY_INSIDERCONTACT, node.getInsiderContact());
        values.put(KEY_TIME, node.getEntryTime());
        db.insert(TABLE_REQUEST, null, values);
        db.close();

    }

    public ArrayList<SecurityPreRequestNode> getAllSecurityPreRequest() {
        ArrayList<SecurityPreRequestNode> RequestList = new ArrayList<SecurityPreRequestNode>();
        String selectQuery = "SELECT * FROM " + TABLE_REQUEST;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                SecurityPreRequestNode node = new SecurityPreRequestNode(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
                RequestList.add(node);
            } while (cursor.moveToNext());
        }
        db.close();
        return RequestList;
    }

}
