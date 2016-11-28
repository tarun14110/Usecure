package com.rocks.mafia.entrancesecurity.Services;


import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.rocks.mafia.entrancesecurity.AppConfig;
import com.rocks.mafia.entrancesecurity.HistoryHandler;
import com.rocks.mafia.entrancesecurity.HistoryNode;
import com.rocks.mafia.entrancesecurity.MainActivity;
import com.rocks.mafia.entrancesecurity.NotificationUtils;
import com.rocks.mafia.entrancesecurity.R;
import com.rocks.mafia.entrancesecurity.RequestHandler;
import com.rocks.mafia.entrancesecurity.RequestNode;
import com.rocks.mafia.entrancesecurity.SecurityMainActivity;
import com.rocks.mafia.entrancesecurity.SecurityRequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by mafia on 19/10/16.
 */


public class MyFirebaseMessagingService extends FirebaseMessagingService
{
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null)
        {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
            Intent i = new Intent(this,getClass());
            i.putExtra("NOTIFICATION","Notification Body: " + remoteMessage.getNotification().getBody());


            //DATA TO REQUEST MESSAGE DSIPLAY

            RequestHandler handler= new RequestHandler(this);
            String n= handler.getDatabaseName();
            Time now = new Time(2,4,5);
            handler.addRequest(new RequestNode(remoteMessage.getNotification().getBody(),now));

        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0)
        {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void handleNotification(String message)
    {
        Log.e(TAG, "ZZZZZ");
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext()))
        {
            Log.e(TAG, "YYYYYY");
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(AppConfig.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }
        else
        {
            Log.e(TAG, "XXXXXXX");
            // If the app is in background, firebase itself handles the notification
        }
    }

    private void handleDataMessage(JSONObject json)
    {
        Log.e(TAG, "push json: " + json.toString());

        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");
            JSONObject payload = data.getJSONObject("payload");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);
            Log.e(TAG, "payload: " + payload.toString());
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);

            Log.e("ROCKS", payload.getString("status"));
            Log.e("ROCKS", payload.getString("requestId"));



            if (payload.getString("type").equals("request-response")) {
                int request = 1;

                if (payload.getString("status").equals("accept")) {
                    request = 3;
                } else if (payload.getString("status").equals("reject")) {
                    request = 2;
                }

                SecurityRequestHandler securityRequestHandler = new SecurityRequestHandler(this);
                securityRequestHandler.updateStatusUsingRequestId( payload.getString("requestId"), request);

            }

            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Log.e("GROUND", "BACK");
                Intent pushNotification = new Intent(AppConfig.PUSH_NOTIFICATION);
                pushNotification.putExtra("requestId", payload.getString("requestId"));
                pushNotification.putExtra("status", payload.getString("status"));
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            } else {
                // app is in background, show the notification in notification tray
                Log.e("GROUND", "FOR");
                Intent resultIntent = new Intent(getApplicationContext(), SecurityMainActivity.class);
                resultIntent.putExtra("message", message);

                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}