package com.rocks.mafia.entrancesecurity;

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

/**
 * Created by pankaj on 7/11/16.
 */


//Sqllit handler attached withe the Request created and stored in Sqllite

public class SecurityRequestHandler extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "SecurityRequest";
    private static final String TABLE_REQUEST = "SecurityRequestTable";
    private static final String KEY_ID = "_id";
    private static final String KEY_TIME = "Time";
    private static final String KEY_OUTSIDERNAME= "OutsiderName";
    private static final String KEY_REASON= "Reason";
    private static final String KEY_INSIDERCONTACT= "InsiderContact";
    private static final String KEY_STATUS= "Status";
    private static  final String KEY_IMAGE="image";
    private static  final String KEY_REQUESTID="requestId";



    public SecurityRequestHandler(Context context)
    {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_REQUEST + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + KEY_OUTSIDERNAME+" TEXT,"
                +KEY_REASON+" TEXT ,"
                +KEY_INSIDERCONTACT+" TEXT ,"
                + KEY_TIME+ " TEXT ,"
                + KEY_IMAGE + " BLOB ,"
                +KEY_STATUS+" INTEGER,"
                +KEY_REQUESTID+" TEXT"
                +")";

        Log.v("CHECK :  ",CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REQUEST);
        onCreate(db);
    }
    public void delete()
    {
        Log.v("DELETING TABLE  :","lol");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REQUEST);
        onCreate(db);
    }

    public void addSecurityRequest(SecurityRequestNode node)

    {
        System.out.println("XXXXXXXXXX:"+node.getOutsiderName());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_OUTSIDERNAME, node.getOutsiderName());
        values.put(KEY_REASON, node.getReason());
        values.put(KEY_INSIDERCONTACT, node.getInsiderContact());
        values.put(KEY_TIME, node.getEntryTime());
        values.put(KEY_IMAGE,node.getImage());
        values.put(KEY_STATUS,String.valueOf(node.getStatus()));
        values.put(KEY_REQUESTID, node.getRequestId());
        db.insert(TABLE_REQUEST, null, values);
        db.close();

    }

    public ArrayList< SecurityRequestNode> getAllSecurityRequest()
    {
        ArrayList<SecurityRequestNode> RequestList = new ArrayList< SecurityRequestNode>();
        String selectQuery = "SELECT * FROM " + TABLE_REQUEST;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do {
                SecurityRequestNode node = new  SecurityRequestNode (cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4), cursor.getString(7), cursor.getBlob(5),Integer.parseInt(cursor.getString(6)));
                RequestList.add(node);
            } while (cursor.moveToNext());
        }
        db.close();
        return RequestList;
    }

    public void updateStatusUsingRequestId(String requestId, int status)
    {
        System.out.println("UPDATING STATUS:"+ requestId);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_STATUS, status);

        String where = "requestId=?";
        String[] whereArgs = new String[] {requestId};

        int l = db.update(TABLE_REQUEST, values, where, whereArgs);
        Log.e("UPDATE_RESULT", String.valueOf(l));
        db.close();
    }







}

