package com.rocks.mafia.entrancesecurity.Nodes;

/**
 * Created by pankaj on 28/11/16.
 */

public class SecurityPreRequestNode {
    private String OutsiderName;
    private String Reason;
    private String InsiderContact;
    private String time;

    public SecurityPreRequestNode(String outsiderName, String outsiderReason, String insiderContact, String time) {
        this.OutsiderName = outsiderName;
        this.Reason = outsiderReason;
        this.InsiderContact = insiderContact;
        this.time = time;
    }


    public String getOutsiderName() {
        return OutsiderName;
    }

    public String getReason() {
        return Reason;
    }

    public String getEntryTime() {
        return time;
    }

    public String getInsiderContact() {
        return InsiderContact;
    }
}
