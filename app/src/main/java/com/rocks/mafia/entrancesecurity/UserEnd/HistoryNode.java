package com.rocks.mafia.entrancesecurity.UserEnd;

import java.sql.Time;
import java.util.ArrayList;

/**
 * Created by mafia on 15/10/16.
 */
public class HistoryNode {
    private static int lastHistoryId = 0;
    private int id;
    private String personName;
    private String imageUrl;
    private Time visitingTime;


    public HistoryNode(String personName, Time visitingTime, String imageUrl) {
        this.personName = personName;
        this.imageUrl = imageUrl;
        this.visitingTime = visitingTime;
    }


    public HistoryNode(String personName, Time visitingTime) {
        this.personName = personName;
        this.visitingTime = visitingTime;
    }

    public static ArrayList<HistoryNode> createContactsList(int numContacts) {
        java.util.Date today = new java.util.Date();
        // System.out.println(new java.sql.Timestamp(today.getTime()));

        ArrayList<HistoryNode> contacts = new ArrayList<HistoryNode>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new HistoryNode("Person ", new java.sql.Time(today.getTime())));

        }


        return contacts;
    }

    public int getId() {
        return id;
    }

    public String getPersonName() {
        return personName;
    }

    public Time getVisitingTime() {
        return visitingTime;
    }

    public String getImageUrl() {

        return imageUrl;
    }

    public String toString() {
        return "person name: " + personName + " visiting time:" + visitingTime.toString();
    }

}
