package com.example.kirmi.ks1807;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import android.database.Cursor;
import android.content.Context;
import android.content.*;
import android.util.Log;
import android.database.sqlite.*;

public class DatabaseFunctions
{
    //Create the local database for storing user data and settings
    private static final String DBNAME = "MusicMentalHealthDB";
    private static final int DB_VERSION = 17;
    private final DatabaseSchema DBSchema = new DatabaseSchema();
    private final ArrayList<String> Create_AllTables = DBSchema.CreateAllTables();
    private final ArrayList<String> Drop_AllTables = DBSchema.DropAllTables();

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
        private SQLHelper (Context c)
        {
            super(c, DBNAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            //Statements for table create can only be executed one at a time.
            for (int i = 0; i < Create_AllTables.size(); i++)
            {
                db.execSQL(Create_AllTables.get(i));
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(DBNAME, "Upgrading database (dropping tables and re-creating them)");

            //Statements for table drops can only be executed one at a time.
            for (int i = 0; i < Drop_AllTables.size(); i++)
            {
                db.execSQL(Drop_AllTables.get(i));
            }
            onCreate(db);
        }
    }

    //Start of our functions designed for selecting/updating/inserting into the database

    //For testing only - Be careful with this!
    private void DeleteEverything()
    {
        context.deleteDatabase(DBNAME);
    }

    //For inserting mood data into database
    public void InsertMoods(String userID, String track, String moodbefore, String mbtime,
                              String moodafter, String matime, String userliked, String recommended) {

        synchronized (this.db) {
            ContentValues NewMood = new ContentValues();
            NewMood.put("UserID", userID);
            NewMood.put("TrackID", track);
            NewMood.put("MoodBefore", moodbefore);
            NewMood.put("MoodBeforeTime", mbtime);
            NewMood.put("MoodAfter", moodafter);
            NewMood.put("MoodAfterTime", matime);
            NewMood.put("UserLiked", userliked);
            NewMood.put("HasBeenRecommended", recommended);

            try {
                db.insertOrThrow("UserMood", null, NewMood);
                Log.d("Records", "user added");
            } catch (Exception e) {
                Log.e("Error in inserting row", e.toString());
                e.printStackTrace();
            }
        }
    }

    //Function used to insert tracks into the database
    public void InsertTrack(String name, String genre, String artist, String length, String SpotifyTrackID) {
        synchronized (this.db) {
            ContentValues NewTrack = new ContentValues();
            NewTrack.put("TrackName", name);
            NewTrack.put("Genre", genre);
            NewTrack.put("Artist", artist);
            NewTrack.put("Length", length);
            NewTrack.put("SpotifyTrackID", SpotifyTrackID);

            try {
                db.insertOrThrow("MusicTrack", null, NewTrack);
                Log.d("Track", "track added");
            } catch (Exception e) {
                Log.e("Error in inserting row", e.toString());
                e.printStackTrace();
            }
        }

    }


    // Function used to get the top 10 mostly played tracks for display on the home page.
    public ArrayList<TrackDetails> GetMusicHistory(String UserID)
    {
        ArrayList<TrackDetails> mostPlayed = new ArrayList<TrackDetails>();

        /*Gets the last ten music tracks that the user has listened to, using the mood after
        time as the time when the user finished the song*/
        String SQLQuery = "SELECT DISTINCT TrackName, Artist, Genre, Length, MoodBefore, MoodAfter, SpotifyTrackID " +
                "FROM MusicTrack INNER JOIN UserMood ON MusicTrack.TrackID = UserMood.TrackID " +
                "WHERE UserMood.UserID = '" + UserID + "' " +
                "ORDER BY UserMood.MoodAfterTime DESC LIMIT 0, 10" ;

        Cursor cursor = db.rawQuery(SQLQuery, null);

        if (cursor.getCount() > 0) {

            cursor.moveToFirst();

            do {

                TrackDetails track = new TrackDetails(cursor.getString(cursor.getColumnIndex("SpotifyTrackID")), cursor.getString(cursor.getColumnIndex("TrackName")), cursor.getString(cursor.getColumnIndex("Artist")),
                        cursor.getString(cursor.getColumnIndex("Genre")), cursor.getString(cursor.getColumnIndex("Length")));
                mostPlayed.add(track);

            } while (cursor.moveToNext());

            cursor.close();
        }

        return mostPlayed;
    }

    //Get list of Playlist IDs
    public String[] GetPlaylistIDs(String UserID)
    {
        //REPLACE WITH A DB CALL and pass UserID into it.

        ArrayList<String> PlaylistIDs = new ArrayList<String>();

        PlaylistIDs.add("1");
        PlaylistIDs.add("2");
        PlaylistIDs.add("3");

        return PlaylistIDs.toArray(new String[PlaylistIDs.size()]);
    }

    //Get list of Playlist Names
    public String[] GetPlaylistNames(String UserID)
    {
        //REPLACE WITH A DB CALL and pass UserID into it.

        ArrayList<String> PlaylistNames = new ArrayList<String>();

        PlaylistNames.add("Classical Collection");
        PlaylistNames.add("Rock and Roll");
        PlaylistNames.add("Recommendations");

        return PlaylistNames.toArray(new String[PlaylistNames.size()]);
    }

    //Get list of Track IDs
    public String[] GetTrackIDs(String UserID, String PlayListID)
    {
        //REPLACE WITH A DB CALL and pass UserID into it.

        ArrayList<String> TrackIDs = new ArrayList<String>();

        TrackIDs.add("1");
        TrackIDs.add("2");
        TrackIDs.add("3");

        return TrackIDs.toArray(new String[TrackIDs.size()]);
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

    private String GetDateOfBirth(String UserID)
    {
        String DateOfBirth = "";
        String[] columns = new String[] {"DateOfBirth"};
        Cursor cursor = db.query("UserAccount", columns, "UserID = " + UserID, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            DateOfBirth = cursor.getString(cursor.getColumnIndex("DateOfBirth"));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }
        return DateOfBirth;
    }

    private String GetEmailAddress(String UserID)
    {
        String EmailAddress = "";
        String[] columns = new String[] {"EmailAddress"};
        Cursor cursor = db.query("UserAccount", columns, "UserID = " + UserID, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            EmailAddress = cursor.getString(cursor.getColumnIndex("EmailAddress"));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }
        return EmailAddress;
    }

    private String GetFirstName(String UserID)
    {
        String FirstName = "";
        String[] columns = new String[] {"FirstName"};
        Cursor cursor = db.query("UserAccount", columns, "UserID = " + UserID, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            FirstName = cursor.getString(cursor.getColumnIndex("FirstName"));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }
        return FirstName;
    }

    private String GetGender(String UserID)
    {
        String Gender = "";
        String[] columns = new String[] {"Gender"};
        Cursor cursor = db.query("UserAccount", columns, "UserID = " + UserID, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            Gender = cursor.getString(cursor.getColumnIndex("Gender"));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }
        return Gender;
    }

    private String GetLastName(String UserID)
    {
        String LastName = "";
        String[] columns = new String[] {"LastName"};
        Cursor cursor = db.query("UserAccount", columns, "UserID = " + UserID, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            LastName = cursor.getString(cursor.getColumnIndex("LastName"));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }
        return LastName;
    }

    private String GetMakeRecommendations(String UserID)
    {
        String MakeRecommendations = "";
        String[] columns = new String[] {"MakeRecommendations"};
        Cursor cursor = db.query("UserSettings", columns, "UserID = " + UserID, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            MakeRecommendations = cursor.getString(cursor.getColumnIndex("MakeRecommendations"));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }
        return MakeRecommendations;
    }

    private String GetMoodFrequency(String UserID)
    {
        String MoodFrequency = "";
        String[] columns = new String[] {"MoodFrequency"};
        Cursor cursor = db.query("UserSettings", columns, "UserID = " + UserID, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            MoodFrequency = cursor.getString(cursor.getColumnIndex("MoodFrequency"));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }
        return MoodFrequency;
    }

    private String GetUserPassword(String UserID)
    {
        String UserPassword = "";
        String[] columns = new String[] {"UserPassword"};
        Cursor cursor = db.query("UserAccount", columns, "UserID = " + UserID, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            UserPassword = cursor.getString(cursor.getColumnIndex("UserPassword"));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }
        return UserPassword;
    }

    public ArrayList<String> GetUserSettings(String UserID)
    {
        ArrayList<String> UserSettings = new ArrayList<String>();

        UserSettings.add(GetMakeRecommendations(UserID));
        UserSettings.add(GetMoodFrequency(UserID));
        return UserSettings;
    }

    public ArrayList<String> GetUserDetails(String UserID)
    {
        ArrayList<String> UserDetails = new ArrayList<String>();

        UserDetails.add(GetFirstName(UserID));
        UserDetails.add(GetLastName(UserID));
        UserDetails.add(GetEmailAddress(UserID));
        UserDetails.add(GetDateOfBirth(UserID));
        UserDetails.add(GetGender(UserID));
        return UserDetails;
    }

    public ArrayList<String> GetUserDetailsRegisterPage(String UserID)
    {
        ArrayList<String> UserDetails = new ArrayList<String>();

        UserDetails.add(GetFirstName(UserID));
        UserDetails.add(GetLastName(UserID));
        UserDetails.add(GetEmailAddress(UserID));
        UserDetails.add(GetDateOfBirth(UserID));
        UserDetails.add(GetGender(UserID));
        return UserDetails;
    }

    private String GetUserID(String EmailAddress)
    {
        //Make the Email Address check case insensitive.
        EmailAddress = EmailAddress.toLowerCase();

        String UserID = "";
        String[] columns = new String[]{"UserID"};
        Cursor cursor = db.query("UserAccount", columns, "EmailAddress = " + "'" + EmailAddress + "'", null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            UserID = cursor.getString(cursor.getColumnIndex("UserID"));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }
        return UserID;
    }

    //Create a new settings record with default values set.
    private boolean InsertNewSettings (String UserID)
    {
        synchronized (this.db)
        {
            ContentValues NewSettings = new ContentValues();
            NewSettings.put("UserID", UserID);
            NewSettings.put("MoodFrequency", "Once Per Track");
            NewSettings.put("MakeRecommendations", "Yes");
            NewSettings.put("RememberLogin", "No");

            try
            {
                db.insertOrThrow("UserSettings", null, NewSettings);
            } catch (Exception e)
            {
                Log.e("Error in inserting row", e.toString());
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    public Long InsertNewUser(String FirstName, String LastName, String EmailAddress, String DateOfBirth,
                                 String Gender, String UserPassword)
    {
        long ID = -1;

        //Make the Email Address all lowercase to ensure case insensitive search.
        EmailAddress = EmailAddress.toLowerCase();

        synchronized(this.db)
        {
            ContentValues NewUser = new ContentValues();
            NewUser.put("FirstName", FirstName);
            NewUser.put("LastName", LastName);
            NewUser.put("EmailAddress", EmailAddress);

            if (!DateOfBirth.equals(""))
            {
                CommonFunctions Common = new CommonFunctions();
                try
                {
                    Date TestDOB = Common.DateFromStringAustraliaFormat(DateOfBirth);
                    NewUser.put("DateOfBirth", DateOfBirth);
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
            NewUser.put("Gender", Gender);
            NewUser.put("UserPassword", UserPassword);

            try
            {
                ID = db.insertOrThrow("UserAccount", null, NewUser);
            } catch (Exception e)
            {
                Log.e("Error in inserting row", e.toString());
                e.printStackTrace();
                return ID;
            }

            //Insert new settings record as well.
            if(!InsertNewSettings(String.valueOf(ID)))
            {
                //Return -1 if it failed.
                ID = -1;
            }
            return ID;
        }
    }

    public boolean IsEmailAddressUnique(String EmailAddress)
    {
        //Make the Email Address check case insensitive.
        EmailAddress = EmailAddress.toLowerCase();

        String Email = "";
        String[] columns = new String[] {"EmailAddress"};
        Cursor cursor = db.query("UserAccount", columns, "EmailAddress = " + "'" + EmailAddress + "'", null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            Email = cursor.getString(cursor.getColumnIndex("EmailAddress"));
            if (Email.equals(EmailAddress))
            {
                return false;
            }
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }
        return true;
    }

    public boolean UpdateNewPassword (String UserID, String UserPassword)
    {
        synchronized (this.db)
        {
            ContentValues NewPassword = new ContentValues();
            NewPassword.put("UserID", UserID);
            NewPassword.put("UserPassword", UserPassword);

            try
            {
                db.update("UserAccount", NewPassword, " UserID = " + UserID, null);
            } catch (Exception e)
            {
                Log.e("Error in updating row", e.toString());
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    public boolean UpdateCurrentUser(String FirstName, String LastName, String EmailAddress,
                                     String DateOfBirth, String Gender, String UserID)
    {
        //Make the Email Address all lowercase to ensure case insensitive search.
        EmailAddress = EmailAddress.toLowerCase();

        synchronized(this.db)
        {
            ContentValues UpdateCurrentUser = new ContentValues();
            UpdateCurrentUser.put("FirstName", FirstName);
            UpdateCurrentUser.put("LastName", LastName);
            UpdateCurrentUser.put("EmailAddress", EmailAddress);

            if (!DateOfBirth.equals(""))
            {
                CommonFunctions Common = new CommonFunctions();
                try
                {
                    Date TestDOB = Common.DateFromStringAustraliaFormat(DateOfBirth);
                    UpdateCurrentUser.put("DateOfBirth", DateOfBirth);
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
            UpdateCurrentUser.put("Gender", Gender);

            try
            {
                db.update("UserAccount", UpdateCurrentUser, " UserID = " + UserID, null);
            } catch (Exception e)
            {
                Log.e("Error in updating row", e.toString());
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public boolean UpdateNewUser(String FirstName, String LastName, String EmailAddress,
                                 String DateOfBirth, String Gender, String UserPassword,
                                 String UserID)
    {
        synchronized(this.db)
        {
            ContentValues UpdateNewUser = new ContentValues();
            UpdateNewUser.put("FirstName", FirstName);
            UpdateNewUser.put("LastName", LastName);
            UpdateNewUser.put("EmailAddress", EmailAddress);
            UpdateNewUser.put("UserPassword", UserPassword);

            if (!DateOfBirth.equals(""))
            {
                CommonFunctions Common = new CommonFunctions();
                try
                {
                    Common.DateFromStringAustraliaFormat(DateOfBirth);
                    UpdateNewUser.put("DateOfBirth", DateOfBirth);
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
            UpdateNewUser.put("Gender", Gender);

            try
            {
                db.update("UserAccount", UpdateNewUser, " UserID = " + UserID, null);
            } catch (Exception e)
            {
                Log.e("Error in updating row", e.toString());
                e.printStackTrace();
                return false;
            }

            return true;
        }
    }

    public boolean UpdateNewUserSecondPage(String MusicQuestionOne, String MusicQuestionTwo,
                                           String MusicQuestionThree, String MusicQuestionFour,
                                           String UserID)
    {
        synchronized(this.db)
        {
            ContentValues UpdateNewUser = new ContentValues();
            UpdateNewUser.put("MusicQuestionOne", MusicQuestionOne);
            UpdateNewUser.put("MusicQuestionTwo", MusicQuestionTwo);
            UpdateNewUser.put("MusicQuestionThree", MusicQuestionThree);
            UpdateNewUser.put("MusicQuestionFour", MusicQuestionFour);

            try
            {
                db.update("UserAccount", UpdateNewUser, " UserID = " + UserID, null);
            } catch (Exception e)
            {
                Log.e("Error in updating row", e.toString());
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    public boolean UpdateSettings (String MakeRecommendations, String MoodFrequency,
                                    String UserID)
    {
        synchronized (this.db)
        {
            ContentValues NewSettings = new ContentValues();
            NewSettings.put("UserID", UserID);
            NewSettings.put("MakeRecommendations", MakeRecommendations);
            NewSettings.put("MoodFrequency", MoodFrequency);

            try
            {
                db.update("UserSettings", NewSettings, " UserID = " + UserID, null);
            } catch (Exception e)
            {
                Log.e("Error in updating row", e.toString());
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    public String VerifyLogin(String EmailAddress, String UserPassword)
    {
        String UserID = GetUserID(EmailAddress);

        //Don't bother checking password if the ID does not match.
        if(UserID.equals(""))
        {
            return "";
        }
        String StoredPassword = GetUserPassword(UserID);

        //If password is wrong, don't return an ID.
        if (UserPassword.equals(StoredPassword))
        {
            return UserID;
        }
        else
        {
            return "";
        }
    }

    public boolean VerifyPassword(String UserID, String UserPassword)
    {
        String StoredPassword = GetUserPassword(UserID);

        if (UserPassword.equals(StoredPassword))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
