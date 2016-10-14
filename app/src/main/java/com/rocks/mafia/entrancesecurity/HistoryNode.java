package com.rocks.mafia.entrancesecurity;

import java.sql.Time;

/**
 * Created by mafia on 15/10/16.
 */
public class HistoryNode {
    private String personName;
    private int imageUrl;
    private Time visitingTime;


    public HistoryNode(String personName, Time visitingTime, int imageUrl) {
        this.personName = personName;
        this.imageUrl = imageUrl;
        this.visitingTime = visitingTime;
    }

    public HistoryNode(String personName,Time visitingTime) {
        this.personName = personName;
        this.imageUrl = R.drawable.ln_logo;
        this.visitingTime = visitingTime;
    }

    public String getPersonName() {
        return personName;
    }

    public String getVisitingTime() {
        return visitingTime.toString();
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public String toString() {
        return "person name: " + personName + " visiting time:" + visitingTime.toString();
    }


}
