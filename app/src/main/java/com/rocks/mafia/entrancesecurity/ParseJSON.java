package com.rocks.mafia.entrancesecurity;

/**
 * Created by mafia on 3/11/16.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseJSON {
    public static String[] contacts;
    public static String[] names;
    public static String[] emails;
    public static String[] address;
    public static String[] profilePicUrls;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_CONTACT = "contact";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_PROFILE_PIC_URL = "profilePicUrl";

    private JSONArray users = null;

    private String json;

    public ParseJSON(String json){
        this.json = json;
    }

    protected void parseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            contacts = new String[users.length()];
            names = new String[users.length()];
            emails = new String[users.length()];
            address = new String[users.length()];
            profilePicUrls = new String[users.length()];

            for(int i=0;i<users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                contacts[i] = jo.getString(KEY_CONTACT);
                names[i] = jo.getString(KEY_NAME);
                emails[i] = jo.getString(KEY_EMAIL);
                address[i] = jo.getString(KEY_ADDRESS);
                profilePicUrls[i] = jo.getString(KEY_PROFILE_PIC_URL);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}