package com.example.kirmi.ks1807;

import java.util.ArrayList;
import android.database.Cursor;
import android.content.Context;
import android.util.Log;
import android.database.sqlite.*;

public class DatabaseFunctions
{
    //Create the local database for storing user data and settings
    private static final String DBNAME = "MusicMentalHealthDB";
    private static final int DB_VERSION = 2;
    final DatabaseSchema DBSchema = new DatabaseSchema();
    private final String Create_AllTables = DBSchema.CreateAllTables();

    private DatabaseFunctions.SQLHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public DatabaseFunctions(Context c)
    {
        this.context = c;
        helper = new DatabaseFunctions.SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public DatabaseFunctions openReadable() throws android.database.SQLException
    {
        helper = new DatabaseFunctions.SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    public void close()
    {
        helper.close();
    }

    public class SQLHelper extends SQLiteOpenHelper
    {
        public SQLHelper (Context c) {
            super(c, DBNAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(Create_AllTables);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(DBNAME, "Upgrading database (dropping tables and re-creating them)");
            db.execSQL(DBSchema.DropAllTables());
            onCreate(db);
        }
    }

    //Start of our functions designed for selecting/updating/inserting into the database

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
        UserDetails.add("Other");

        String[] ReturnUserDetails = UserDetails.toArray(new String[UserDetails.size()]);

        return ReturnUserDetails;
    }

    public String GetUserID()
    {
        String UserID = "DUMMY";
        return UserID;
    }

    public boolean VerifyPassword(String Password)
    {
        //Need to check if the password here matches the one being entered.
        return true;
    }
}
