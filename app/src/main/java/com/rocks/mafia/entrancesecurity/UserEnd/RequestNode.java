package com.rocks.mafia.entrancesecurity.UserEnd;

/**
 * Created by pankaj on 22/10/16.
 */

import java.sql.Time;

public class RequestNode {
    private String message;
    private Time time;

    public RequestNode(String m, Time time) {
        this.time = time;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Time getTime() {
        return time;
    }

}

