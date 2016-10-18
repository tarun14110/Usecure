package com.rocks.mafia.entrancesecurity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rocks.mafia.entrancesecurity.HistoryNode;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pankaj on 18/10/16.
 */
public class HistoryHandler extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "History";
    private static final String TABLE_HISTORY = "HistoryTable";
    private static final String KEY_ID = "_id";
    private static final String KEY_PERSON_NAME = "personName";
    private static final String KEY_VISIT_TIME = "visitingTime";
    private static final String KEY_IMAGE_URL = "imageUrl";



    public HistoryHandler(Context context)
    {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_HISTORY + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +KEY_PERSON_NAME+" TEXT,"
                + KEY_VISIT_TIME + " TIME,"  +KEY_IMAGE_URL + " TEXT"+ ")";

        Log.v("CHECK :  ",CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        onCreate(db);
    }
    public void addHistory(HistoryNode node)

    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PERSON_NAME, node.getPersonName());
        values.put(KEY_VISIT_TIME, String.valueOf(node.getVisitingTime()));
        values.put(KEY_IMAGE_URL, node.getImageUrl());
        db.insert(TABLE_HISTORY, null, values);
        db.close();

    }

    public HistoryNode getPerson(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_HISTORY, new String[] { KEY_ID,
                        KEY_PERSON_NAME, KEY_VISIT_TIME,KEY_IMAGE_URL }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null,null, null);
        if (cursor != null)
            cursor.moveToFirst();
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        Time timeValue = null;
        try {
            timeValue = new Time(formatter.parse(cursor.getString(2)).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        HistoryNode node = new HistoryNode(id,cursor.getString(1),timeValue, cursor.getString(3));

        return node;
    }

    public ArrayList< HistoryNode> getAllHistory()
    {
       ArrayList< HistoryNode> HistoryList = new ArrayList< HistoryNode>();
        String selectQuery = "SELECT * FROM " + TABLE_HISTORY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do {
                DateFormat formatter = new SimpleDateFormat("HH:mm");
                Time timeValue = null;
                try {
                    timeValue = new Time(formatter.parse(cursor.getString(2)).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                HistoryNode node = new HistoryNode(Integer.parseInt(cursor.getString(0)),cursor.getString(1),timeValue, cursor.getString(3));
                HistoryList.add(node);
            } while (cursor.moveToNext());
        }
        return HistoryList;
    }


    public void deleteHistory(HistoryNode node)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HISTORY, KEY_ID + " = ?",
                new String[] { String.valueOf(node.getPersonName()) });
        db.close();
    }
    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_HISTORY);

    }
}
