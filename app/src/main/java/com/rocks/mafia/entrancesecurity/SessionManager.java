package com.rocks.mafia.entrancesecurity;

/**
 * Created by mafia on 10/16/16.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class SessionManager {
    // Shared preferences file name
    private static final String PREF_NAME = "uSecureLogin";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";
    private static final String KEY_IS_SECURITYLOGGEDIN = "isSecurityLoggedIn";
    private static final String LOGGEDIN_CONTACT = "loggedInPersonContact";
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();
    // Shared Preferences
    SharedPreferences pref;
    Editor editor;
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setSecurityLogin(boolean isSecurityLoggedIn) {

        editor.putBoolean(KEY_IS_SECURITYLOGGEDIN, isSecurityLoggedIn);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public String getContact() {

        return pref.getString(LOGGEDIN_CONTACT, null);
    }

    public void setContact(String contact) {

        editor.putString(LOGGEDIN_CONTACT, contact);

        // commit changes
        editor.commit();

        Log.d(TAG, "Logged in person contact session modified!");
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }

    public boolean isSecurityLoggedIn() {
        return pref.getBoolean(KEY_IS_SECURITYLOGGEDIN, false);
    }
}