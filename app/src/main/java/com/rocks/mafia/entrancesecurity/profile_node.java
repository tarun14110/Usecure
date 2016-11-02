package com.rocks.mafia.entrancesecurity;

/**
 * Created by pankaj on 30/10/16.
 */

public class profile_node
{

    private String name,email,contact,address ;

    private  int img;
    public String getName() {
        return name;
    }
    public String getEmail(){ return email; }
    public  String getAddress() { return address;}
    public  String getContact(){ return  contact;}

    public int getImg() {
        return img;
    }

    public  profile_node(String name, String contact, String email, String address, int img)
   {
       this.name=name;
       this.email=email;
       this.contact=contact;
       this.address=address;
       this.img=img;

   }
    public  profile_node(String name,String contact,String address,int img)
    {
        this.name=name;
        this.img=img;
        this.contact=contact;
        this.address=address;

    }
}
