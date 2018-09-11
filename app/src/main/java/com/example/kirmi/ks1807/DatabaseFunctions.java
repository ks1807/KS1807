package com.example.kirmi.ks1807;

import java.util.ArrayList;
import android.database.Cursor;
import android.content.Context;
import android.content.*;
import android.util.Log;
import android.database.sqlite.*;

public class DatabaseFunctions
{
    //Create the local database for storing user data and settings
    private static final String DBNAME = "MusicMentalHealthDB";
    private static final int DB_VERSION = 3;
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

    private String EncryptPassword(String Password)
    {
        //Need to figure out how to do this.

        return Password;
    }

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

        return UserDetails.toArray(new String[UserDetails.size()]);
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

    private String GetAge(String UserID)
    {
        String Age = "";
        String[] columns = new String[] {"Age"};
        Cursor cursor = db.query("UserAccount", columns, "UserID = " + UserID, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            Age = cursor.getString(cursor.getColumnIndex("Age"));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }
        return Age;
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
        String Password = "";
        String[] columns = new String[] {"Password"};
        Cursor cursor = db.query("UserPassword", columns, "UserID = " + UserID, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            Password = cursor.getString(cursor.getColumnIndex("Password"));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }
        return Password;
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
        UserDetails.add(GetAge(UserID));
        UserDetails.add(GetGender(UserID));
        return UserDetails;
    }

    public ArrayList<String> GetUserDetailsRegisterPage(String UserID)
    {
        ArrayList<String> UserDetails = new ArrayList<String>();

        UserDetails.add(GetFirstName(UserID));
        UserDetails.add(GetLastName(UserID));
        UserDetails.add(GetEmailAddress(UserID));
        UserDetails.add(GetAge(UserID));
        UserDetails.add(GetUserPassword(UserID));
        UserDetails.add(GetGender(UserID));
        return UserDetails;
    }

    private String GetUserID(String EmailAddress)
    {
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

    private boolean InsertNewPassword (String UserID, String Password)
    {
        Password = EncryptPassword(Password);

        synchronized (this.db)
        {
            ContentValues NewPassword = new ContentValues();
            NewPassword.put("UserID", UserID);
            NewPassword.put("Password", Password);

            try
            {
                db.insertOrThrow("UserPassword", null, NewPassword);
            } catch (Exception e)
            {
                Log.e("Error in inserting row", e.toString());
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    //Create a new settings record with default values set.
    private boolean InsertNewSettings (String UserID)
    {
        synchronized (this.db)
        {
            ContentValues NewPassword = new ContentValues();
            NewPassword.put("UserID", UserID);
            NewPassword.put("MoodFrequency", "Once Per Track");
            NewPassword.put("MakeRecommendations", "Yes");
            NewPassword.put("AllowFriendRecommendations", "Yes");

            try
            {
                db.insertOrThrow("UserSettings", null, NewPassword);
            } catch (Exception e)
            {
                Log.e("Error in inserting row", e.toString());
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    public Long InsertNewUser(String FirstName, String LastName, String EmailAddress, String Age,
                                 String Gender, String Password)
    {
        long ID = -1;

        synchronized(this.db)
        {
            ContentValues NewUser = new ContentValues();
            NewUser.put("FirstName", FirstName);
            NewUser.put("LastName", LastName);
            NewUser.put("EmailAddress", EmailAddress);

            if (!Age.equals(""))
            {
                int AgeNum = Integer.parseInt(Age);
                NewUser.put("Age", AgeNum);
            }
            NewUser.put("Gender", Gender);

            try
            {
                ID = db.insertOrThrow("UserAccount", null, NewUser);
            } catch (Exception e)
            {
                Log.e("Error in inserting row", e.toString());
                e.printStackTrace();
                return ID;
            }

            //Once we have the UserID we need to insert their password data into a different table.
            if (!InsertNewPassword(String.valueOf(ID), Password))
            {
                //Return -1 if it failed.
                ID = -1;
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

    public boolean UpdateNewPassword (String UserID, String Password)
    {
        Password = EncryptPassword(Password);

        synchronized (this.db)
        {
            ContentValues NewPassword = new ContentValues();
            NewPassword.put("UserID", UserID);
            NewPassword.put("Password", Password);

            try
            {
                db.update("UserPassword", NewPassword, " UserID = " + UserID, null);
            } catch (Exception e)
            {
                Log.e("Error in inserting row", e.toString());
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

    public boolean UpdateCurrentUser(String FirstName, String LastName, String EmailAddress, String Age,
                                 String Gender, String UserID)
    {
        synchronized(this.db)
        {
            ContentValues UpdateNewUser = new ContentValues();
            UpdateNewUser.put("FirstName", FirstName);
            UpdateNewUser.put("LastName", LastName);
            UpdateNewUser.put("EmailAddress", EmailAddress);

            if (!Age.equals(""))
            {
                int AgeNum = Integer.parseInt(Age);
                UpdateNewUser.put("Age", AgeNum);
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
        }
        return true;
    }

    public boolean UpdateNewUser(String FirstName, String LastName, String EmailAddress, String Age,
                              String Gender, String Password, String UserID)
    {
        synchronized(this.db)
        {
            ContentValues UpdateNewUser = new ContentValues();
            UpdateNewUser.put("FirstName", FirstName);
            UpdateNewUser.put("LastName", LastName);
            UpdateNewUser.put("EmailAddress", EmailAddress);

            if (!Age.equals(""))
            {
                int AgeNum = Integer.parseInt(Age);
                UpdateNewUser.put("Age", AgeNum);
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

            //Update the existing password record as well.
            return UpdateNewPassword(UserID, Password);
        }
    }

    public boolean UpdateNewUserSecondPage(String PreferredPlatform, String MusicQuestionOne, String MusicQuestionTwo,
                                 String MusicQuestionThree, String UserID)
    {
        synchronized(this.db)
        {
            ContentValues UpdateNewUser = new ContentValues();
            UpdateNewUser.put("PreferredPlatform", PreferredPlatform);
            UpdateNewUser.put("MusicQuestionOne", MusicQuestionOne);
            UpdateNewUser.put("MusicQuestionTwo", MusicQuestionTwo);
            UpdateNewUser.put("MusicQuestionThree", MusicQuestionThree);

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

    public String VerifyLogin(String EmailAddress, String Password)
    {
        String UserID = GetUserID(EmailAddress);

        //Don't bother checking password if the ID does not match.
        if(UserID.equals(""))
        {
            return "";
        }

        Password = EncryptPassword(Password);
        String StoredPassword = GetUserPassword(UserID);

        //If password is wrong, don't return an ID.
        if (Password.equals(StoredPassword))
        {
            return UserID;
        }
        else
        {
            return "";
        }
    }

    public boolean VerifyPassword(String UserID, String Password)
    {
        Password = EncryptPassword(Password);

        String StoredPassword = GetUserPassword(UserID);

        if (Password.equals(StoredPassword))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
