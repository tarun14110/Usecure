package com.rocks.mafia.entrancesecurity;

/**
 * Created by pankaj on 21/10/16.
 */
import java.util.ArrayList;
public class ProfileNode {
    private int id;
    private String personName;
    private String imageUrl;
    private String contact;
    private String email;

    public ProfileNode(int id,String personName,String contact, String email, String imageUrl)
    {
        this.id=id;
        this.personName = personName;
        this.imageUrl = imageUrl;
        this.contact = contact;
        this.email=email;
    }

    public ProfileNode(String personName,String contact, String email)
    {
        this.personName = personName;
        this.email=email;
        this.contact=contact;
    }


    public int getId()
    {
        return id;
    }
    public String getPersonName() {
        return personName;
    }
    public String getContact() {return contact;}
    public String getEmail(){return email;}
    public String getImageUrl()
    {
        return imageUrl;
    }


    public String toString()
    {
        return "person name: " + personName + " Contact time:" + contact;
    }
    private static int lastHistoryId = 0;

    public static ArrayList<ProfileNode> createProfileList(int numProfile)
    {
        java.util.Date today = new java.util.Date();
        // System.out.println(new java.sql.Timestamp(today.getTime()));

        ArrayList<ProfileNode> profiles = new ArrayList<ProfileNode>();

        for (int i = 1; i <= numProfile; i++)
        {
            profiles.add(new ProfileNode("Person " ,"contact" +i,"email"+i));

        }


        return profiles;
    }

}
