package com.example.kirmi.ks1807;

import java.util.ArrayList;
import android.database.Cursor;
import android.content.Context;
import android.content.*;
import android.util.Log;
import android.database.sqlite.*;
import java.util.ArrayList;

public class DatabaseFunctions
{
    //Create the local database for storing user data and settings
    private static final String DBNAME = "MusicMentalHealthDB";
    private static final int DB_VERSION = 3;
    final DatabaseSchema DBSchema = new DatabaseSchema();
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
        public SQLHelper (Context c) {
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

    private String GetAge(String UserID)
    {
        String Age = "";
        String[] columns = new String[] {"Age"};
        Cursor cursor = db.query("UserAccount", columns, "UserID = " + UserID, null, null, null, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false)
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

        while (cursor.isAfterLast() == false)
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

        while (cursor.isAfterLast() == false)
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

        while (cursor.isAfterLast() == false)
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

        while (cursor.isAfterLast() == false)
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

    private String GetUserPassword(String UserID)
    {
        String Password = "";
        String[] columns = new String[] {"Password"};
        Cursor cursor = db.query("UserPassword", columns, "UserID = " + UserID, null, null, null, null);
        cursor.moveToFirst();

        while (cursor.isAfterLast() == false)
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

    public ArrayList<String> GetUserDetails(String UserID)
    {
        //REPLACE WITH A DB CALL and pass UserID into it.

        ArrayList<String> UserDetails = new ArrayList<String>();

        UserDetails.add("Barrack");
        UserDetails.add("Obama");
        UserDetails.add("test@test.com.au");
        UserDetails.add("57");
        UserDetails.add("Other");

        return UserDetails;
    }

    public ArrayList<String> GetUserDetailsRegisterPage(String UserID)
    {
        //REPLACE WITH A DB CALL and pass UserID into it.

        ArrayList<String> UserDetails = new ArrayList<String>();

        UserDetails.add(GetFirstName(UserID));
        UserDetails.add(GetLastName(UserID));
        UserDetails.add(GetEmailAddress(UserID));
        UserDetails.add(GetAge(UserID));
        UserDetails.add(GetUserPassword(UserID));
        UserDetails.add(GetGender(UserID));
        return UserDetails;
    }

    public String GetUserID()
    {
        String UserID = "DUMMY";
        return UserID;
    }

    private boolean InsertNewPassword (String UserID, String Password)
    {
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
                Log.e("Error in inserting rows", e.toString());
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
                Log.e("Error in inserting rows", e.toString());
                e.printStackTrace();
                return ID;
            }

            //Once we have the UserID we need to insert their password data into a different table.
            InsertNewPassword(String.valueOf(ID), Password);
            return ID;
        }
    }

    private boolean UpdateNewPassword (String UserID, String Password)
    {
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
                Log.e("Error in inserting rows", e.toString());
                e.printStackTrace();
                return false;
            }
            return true;
        }
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
                Log.e("Error in inserting rows", e.toString());
                e.printStackTrace();
                return false;
            }

            //Update the existing password record as well.
            boolean PasswordUpdated = UpdateNewPassword(UserID, Password);
            return PasswordUpdated;
        }
    }

    public boolean VerifyPassword(String Password)
    {
        //Need to check if the password here matches the one being entered.
        return true;
    }
}
