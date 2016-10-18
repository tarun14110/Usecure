package com.rocks.mafia.entrancesecurity;

public class AppConfig {
    // Server user login url
    public static String URL_LOGIN = "http://usecure.site88.net/login.php";

    // Server user register url
    public static String URL_REGISTER = "http://usecure.site88.net/register.php?";



    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;

    public static final String SHARED_PREF = "ah_firebase";

}