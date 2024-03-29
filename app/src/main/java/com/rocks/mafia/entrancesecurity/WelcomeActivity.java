package com.rocks.mafia.entrancesecurity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.rocks.mafia.entrancesecurity.UserEnd.MainActivity;
import com.rocks.mafia.entrancesecurity.UserEnd.SigninActivity;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        passToLoginPageIfUserNotLoggedIn();
        setContentView(R.layout.activity_welcome);

        Button goToLoginPage = (Button) findViewById(R.id.goToLoginPage);
        goToLoginPage.setOnClickListener(this);

    }

    /**
     * @return true if the user already logged in.
     */
    private void passToLoginPageIfUserNotLoggedIn() {
        SessionManager sessionManager = new SessionManager(getApplicationContext());

       /* SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean islogin = sharedPref.getBoolean(getString(R.string.isLogin), false);                         // get value of last login status
*/
        if (sessionManager.isLoggedIn()) {                                                                 // if user not logged in, take him to log in page
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goToLoginPage:
                Intent intent = new Intent(this, SigninActivity.class);
                startActivity(intent);
        }
    }
}
