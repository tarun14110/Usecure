package com.rocks.mafia.entrancesecurity.Nodes;

/**
 * Created by pankaj on 30/10/16.
 */

public class profile_node {

    private String name, email, contact, address, rollNumber;

    private byte[] image;

    public profile_node(String name, String email, String contact, String address, byte[] img) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.address = address;
        this.image = img;

    }

    public profile_node(String name, String email, String address, byte[] image) {
        this.name = name;
        this.image = image;
        this.email = email;
        this.address = address;

    }

    public profile_node(String name, String email, String contact, String address, String rollNumber, byte[] image) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.address = address;
        this.image = image;
        this.rollNumber = rollNumber;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public byte[] getImg() {
        return image;
    }
}
