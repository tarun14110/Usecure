package com.rocks.mafia.entrancesecurity;

/**
 * Created by pankaj on 30/10/16.
 */

public class profile_node
{

    private String name,email,contact,address,rollNumber ;

    private  String image;
    public String getName() {
        return name;
    }
    public String getEmail(){ return email; }
    public  String getAddress() { return address;}
    public  String getContact(){ return  contact;}
    public  String getRollNumber(){ return rollNumber;}
    public String getImg() {
        return image;
    }

    public  profile_node(String name, String contact, String email, String address, String img)
   {
       this.name=name;
       this.email=email;
       this.contact=contact;
       this.address=address;
       this.image=img;

   }
    public  profile_node(String name,String contact,String address,String image)
    {
        this.name=name;
        this.image=image;
        this.contact=contact;
        this.address=address;

    }
    public  profile_node(String name, String contact, String email, String address,String rollNumber, String image)
    {
        this.name=name;
        this.email=email;
        this.contact=contact;
        this.address=address;
        this.image=image;
        this.rollNumber=rollNumber;
    }
}
