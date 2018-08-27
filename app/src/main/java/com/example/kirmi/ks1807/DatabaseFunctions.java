package com.example.kirmi.ks1807;

import java.util.ArrayList;

public class DatabaseFunctions
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

    //Get list of Playlist IDs
    public String[] GetPlaylistIDs(String UserID)
    {
        //REPLACE WITH A DB CALL and pass UserID into it.

        ArrayList<String> PlaylistIDs = new ArrayList<String>();

        PlaylistIDs.add("1");
        PlaylistIDs.add("2");
        PlaylistIDs.add("3");

        String[] ReturnPlaylistIDs = PlaylistIDs.toArray(new String[PlaylistIDs.size()]);

        return ReturnPlaylistIDs;
    }

    //Get list of Playlist Names
    public String[] GetPlaylistNames(String UserID)
    {
        //REPLACE WITH A DB CALL and pass UserID into it.

        ArrayList<String> PlaylistNames = new ArrayList<String>();

        PlaylistNames.add("Classical Collection");
        PlaylistNames.add("Rock and Roll");
        PlaylistNames.add("Recommendations");

        String[] ReturnPlaylistNames = PlaylistNames.toArray(new String[PlaylistNames.size()]);

        return ReturnPlaylistNames;
    }

    //Get list of Track IDs
    public String[] GetTrackIDs(String UserID, String PlayListID)
    {
        //REPLACE WITH A DB CALL and pass UserID into it.

        ArrayList<String> TrackIDs = new ArrayList<String>();

        TrackIDs.add("1");
        TrackIDs.add("2");
        TrackIDs.add("3");

        String[] ReturnTrackIDs = TrackIDs.toArray(new String[TrackIDs.size()]);

        return ReturnTrackIDs;
    }

    //Get list of Track Names
    public String[] GetTrackNames(String UserID, String PlayListID)
    {
        //REPLACE WITH A DB CALL and pass UserID into it.

        ArrayList<String> TrackNames = new ArrayList<String>();

        TrackNames.add("Harmonielehre");
        TrackNames.add("Bouken Desho Desho");
        TrackNames.add("Gangnam Style");

        String[] ReturnTrackNames = TrackNames.toArray(new String[TrackNames.size()]);

        return ReturnTrackNames;
    }

    public String[] GetUserDetails(String UserID)
    {
        //REPLACE WITH A DB CALL and pass UserID into it.

        ArrayList<String> UserDetails = new ArrayList<String>();

        UserDetails.add("Barrack");
        UserDetails.add("Obama");
        UserDetails.add("test@test.com.au");
        UserDetails.add("57");
        UserDetails.add("Spotify");
        UserDetails.add("Other");

        String[] ReturnUserDetails = UserDetails.toArray(new String[UserDetails.size()]);

        return ReturnUserDetails;
    }
}
