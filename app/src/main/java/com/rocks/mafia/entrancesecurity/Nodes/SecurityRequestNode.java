package com.rocks.mafia.entrancesecurity.Nodes;

/**
 * Created by pankaj on 7/11/16.
 */

public class SecurityRequestNode {
    private String OutsiderName;
    private String Reason;
    private String InsiderContact;
    private String time;
    private String requestId;
    private int Status;
    private byte[] image;

    public SecurityRequestNode(String outsiderName, String outsiderReason, String insiderContact, String time, String requestId, int status) {
        this.OutsiderName = outsiderName;
        this.Reason = outsiderReason;
        this.InsiderContact = insiderContact;
        this.time = time;
        this.Status = status;
        this.requestId = requestId;
    }

    public SecurityRequestNode(String outsiderName, String outsiderReason, String insiderContact, String time, String requestId, byte[] image, int status) {
        this.OutsiderName = outsiderName;
        this.Reason = outsiderReason;
        this.InsiderContact = insiderContact;
        this.time = time;
        this.image = image;
        this.Status = status;
        this.requestId = requestId;
    }


    public String getOutsiderName() {
        return OutsiderName;
    }

    public String getReason() {
        return Reason;
    }

    public String getRequestId() {
        return requestId;
    }

    public byte[] getImage() {
        return image;
    }

    public String getEntryTime() {
        return time;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        this.Status = status;
    }

    public String getInsiderContact() {
        return InsiderContact;
    }
}
