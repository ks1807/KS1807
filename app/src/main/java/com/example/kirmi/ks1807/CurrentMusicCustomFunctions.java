package com.example.kirmi.ks1807;

import java.util.ArrayList;

//Functions to display data on the Current Music page
public class CurrentMusicCustomFunctions
{
    public String[] GetMusicHistory(String UserID)
    {
        //REPLACE WITH A DB CALL and pass UserID into it.

        ArrayList<String> UserDetails = new ArrayList<String>();

        UserDetails.add("Barrack");
        UserDetails.add("Obama");
        UserDetails.add("Beethoven: Sinfonía No. 6 en Fa Mayor \"Pastoral\"");
        UserDetails.add("Classical");
        UserDetails.add("Orquesta del Cine Infantil");
        UserDetails.add("12:13");
        UserDetails.add("Sad");
        UserDetails.add("Happy");

        String[] ReturnUserDetails = UserDetails.toArray(new String[UserDetails.size()]);

        return ReturnUserDetails;
    }
}
