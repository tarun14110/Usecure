package com.rocks.mafia.entrancesecurity;

import android.support.annotation.IntegerRes;

import java.sql.Time;

/**
 * Created by pankaj on 7/11/16.
 */

public class SecurityRequestNode
{
    private String OutsiderName;
    private String Reason;
    private String InsiderContact;
    private Time time;
    private int Status;
    private  byte[] image;

    public SecurityRequestNode(String outsiderName,String outsiderReason,String insiderContact ,Time time,int status)
    {
        this.OutsiderName=outsiderName;
        this.Reason=outsiderReason;
        this.InsiderContact=insiderContact;
        this.time=time;
        this.Status=status;
    }
    public SecurityRequestNode(String outsiderName,String outsiderReason,String insiderContact ,Time time,byte[] image,int status)
    {
        this.OutsiderName=outsiderName;
        this.Reason=outsiderReason;
        this.InsiderContact=insiderContact;
        this.time=time;
        this.image=image;
        this.Status=status;
    }

    public String getOutsiderName() {
        return OutsiderName;
    }

    public String getReason() {
        return Reason;
    }

    public byte[] getImage() {
        return  image;
    }

    public Time getEntryTime() {
        return time;
    }

    public int getStatus() {
        return Status;
    }

    public String getInsiderContact() {
        return InsiderContact;
    }
}
