package com.rocks.mafia.entrancesecurity;

import java.sql.Time;

/**
 * Created by mafia on 15/10/16.
 */
public class HistoryNode {
    private int id;
    private String personName;
    private String imageUrl;
    private Time visitingTime;


    public HistoryNode(int id,String personName, Time visitingTime, String imageUrl)
    {
        this.id=id;
        this.personName = personName;
        this.imageUrl = imageUrl;
        this.visitingTime = visitingTime;
    }

    public HistoryNode(int id,String personName,Time visitingTime)
    {
        this.id=id;
        this.personName = personName;
        this.visitingTime = visitingTime;
    }


    public int getId()
    {
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
