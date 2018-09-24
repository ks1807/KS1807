package com.example.kirmi.ks1807;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//All of this is to be moved to the server...
public class GeneratePlaylists
{
    private static final String JDBCDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String ServerName = "JONATHONPC\\SQLEXPRESS";
    private static final String ServerPort = "1433";
    private static final String DatabaseName = "MFMHDatabase";

    private static final String JDBC_URL = "jdbc:microsoft:sqlserver://" + ServerName
            + ":" + ServerPort + ";databasename=" + DatabaseName + ";integratedSecurity=true;";

    private int AddTrack(String TrackName, String Genre, String Artist, String Length)
    {
        //Dummy data, to be passed in through Spotify API
        TrackName = "Harmonielehre";
        Genre = "Instrumental";
        Artist = "John Adams";
        Length = "3:25";

        /*Verify that we haven't already inserted this record before. If we have then just get the
        ID*/
        String SQLQuery = "SELECT TrackID FROM MusicTrack WHERE TrackName = '" + TrackName + "'" ;

        int TrackID = 0;

        if (TrackID == 0)
        {
                    /*Create a new record in the MusicTrack table and get back the ID of the newly inserted
        record*/
            SQLQuery = "INSERT INTO MusicTrack (TrackName, Genre, Artist, Length)\n" +
                    "VALUES('" + TrackName + "', '" + Genre + "', '" + Artist + "', '" + Length +
                    "')\n\nSELECT SCOPE_IDENTITY()";
        }

        return TrackID;
    }

    private void AddTracksToPlaylist(String UserID)
    {
        /*Count all rows in the UserMood table that have a match UserID
        and where HasBeenRecommended = “No”.*/
        String SQLQuery = "SELECT Count(UserID) FROM UserMood WHERE UserID = " + "'" +
                UserID + "' AND HasBeenRecommended = 'No'";

        int NonRecommendedCount = 11;

        if (NonRecommendedCount > 10)
        {
            //Checks if the user wants the system to make recommendations.
            SQLQuery = "SELECT Count(UserID) FROM UserSettings WHERE UserID = " + "'" +
                    UserID + "' AND MakeRecommendations = 'Yes'";

            int RecommendationsOn = 1;
            if (RecommendationsOn > 0)
            {
                int TrackIDs[] = MakeRecommendation(UserID);
                int UserTrackIDs[] = MakeRecommendationUsers();

                /*Insert a record into the PlayList table with the current UserID, PlayListName
                as ‘Music To Make You Feel Better’ and RecommendedBy as ‘System’.
                Get the PlayListID of this newly inserted record.*/
                SQLQuery = "INSERT INTO PlayList (UserID, PlayListName, RecommendedBy)\n" +
                "VALUES('" + UserID + "', " + "'Music To Make You Feel Better'" + ", '" +
                        "System" + "')\n\nSELECT SCOPE_IDENTITY()";

                int PlayListID = 5;

                for (int i = 0; i < TrackIDs.length; i++)
                {
                    int TrackIDToInsert = TrackIDs[i];

                    /*Insert a record into the TracksInPlayList table with the current
                    UserID, the PlayListID and the RecommendedTrackID from the array.*/
                    SQLQuery = "INSERT INTO TracksInPlayList (UserID, PlayListID, TrackID)\n" +
                            "VALUES('" + UserID + "', '" + PlayListID +
                            "', '" + TrackIDToInsert + "')";
                }

                /*Insert a record into the PlayList table with the current UserID, PlayListName
                as ‘Music that others are listening to’ and RecommendedBy as ‘Users’.
                Get the PlayListID of this newly inserted record.*/
                SQLQuery = "INSERT INTO PlayList (UserID, PlayListName, RecommendedBy)\n" +
                        "VALUES('" + UserID + "', " + "'Music that others are listening to'" +
                        ", '" + "Users" + "')\n\nSELECT SCOPE_IDENTITY()";

                for (int i = 0; i < UserTrackIDs.length; i++)
                {
                    int TrackIDToInsert = UserTrackIDs[i];

                    /*Insert a record into the TracksInPlayList table with the current
                    UserID, the PlayListID and the UserTrackID from the array.*/
                    SQLQuery = "INSERT INTO TracksInPlayList (UserID, PlayListID, TrackID)\n" +
                            "VALUES('" + UserID + "', '" + PlayListID +
                            "', '" + TrackIDToInsert + "')";
                }
            }
        }
    }

    private boolean CheckMoodEntry(String UserID)
    {
        /*Get the MoodFrequency (String) parameter from the UserSettings database table.*/
        String SQLQuery = "SELECT MoodFrequency FROM UserSettings WHERE UserID = " + "'" +
                UserID + "'";

        String MoodFrequency = "Once per hour";

        /*Get the MoodAfterTime (Datetime) of the last entry into the UserMood table.*/
        SQLQuery = "SELECT TOP (1) MoodAfterTime FROM UserMood WHERE UserID = " + "'" +
                UserID + "'" +
        " ORDER BY MoodAfterTime DESC";

        String MoodAfterTimeString = "2018-09-20 11:34:46";

        try
        {
            Date MoodAfterTime = DateTimeFromStringSQLFormat(MoodAfterTimeString);
            Date CurrentDate = new Date();

            long DateDifference = CurrentDate.getTime() - MoodAfterTime.getTime();
            long MinutesDifference = DateDifference / (60 * 1000);

                switch(MoodFrequency)
            {
                case "Once per track" :
                    return true;
                case "Once every 15 minutes":
                    if (MinutesDifference > 15)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                case "Once per hour":
                    if (MinutesDifference > 60)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                case "Once per 24 hours":
                    //1440 - Number of Minutes in a day
                    if (MinutesDifference > 1440)
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                case "Never" :
                    return false;
                default :
                    return false;
            }
        } catch (ParseException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    private int ConvertMoodToNumber(String MoodName)
    {
        int Score = 0;

        //Select the score from the MoodScore table.
        String SQLQuery = "SELECT Score FROM MoodScore WHERE Mood = " + "'" + MoodName + "'";

        String MoodScore = "2";
        Score = Integer.parseInt(MoodScore);

        return Score;
    }

    //Gets a string and formats it into the format used by SQL Server.
    public Date DateTimeFromStringSQLFormat(String DateString) throws ParseException
    {
        SimpleDateFormat SQLServerDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            Date FormattedDate = SQLServerDateFormat.parse(DateString);
            return FormattedDate;
        } catch (ParseException e)
        {
            e.printStackTrace();
            throw new ParseException("Invalid Datetime SQL Format", -1);
        }
    }

    public int GetArrayIndexFromString(String[] Array, String SearchString)
    {
        int Index=0;
        for(int i=0; i<Array.length; i++)
        {
            if(Array[i].equals(SearchString))
            {
                Index=i;
                break;
            }
        }
        return Index;
    }

    //Gets the index place for the highest number in a floating point array.
    public int GetIndexOfMaximumFloatValue(float[] FloatArray)
    {
        float MaximumValue = FloatArray[0];
        int i;
        for (i = 1; i < FloatArray.length - 1; i++)
        {
            if (FloatArray[i] > MaximumValue)
            {
                MaximumValue = FloatArray[i];
            }
        }
        return i;
    }

    private int[] MakeRecommendation(String UserID)
    {
        String[][] GenresAndScores = SetRecommendationScoreForGenre(UserID);

        //Need to convert the strings back to numbers.
        float GenreScores[] = new float[GenresAndScores.length];
        for (int i = 0; i < GenresAndScores.length; i++)
        {
            GenreScores[i] = Float.valueOf(GenresAndScores[1][i]);
        }

        //Now we check which of these genres has the highest number and get its place in the index.
        int Index = GetIndexOfMaximumFloatValue(GenreScores);

        //We then get the string value of this genre from the index.
        String HighestGenre= GenresAndScores[0][Index];

        /*Select 10 unique music and random tracks that belong to this genre. Note ORDER BY newid()
        should make the selection random*/
        String SQLQuery = "SELECT TOP (10) TrackID FROM MusicTrack WHERE Genre = " + "'" +
                HighestGenre + "' ORDER BY newid()";

        int TrackIDs[] = {1,2,3,50,34,43,4,5,9,67};

        return TrackIDs;
    }

    private int[] MakeRecommendationUsers()
    {
        String[][] GenresAndScores = SetRecommendationScoreForGenre("");

        //Need to convert the strings back to numbers.
        float GenreScores[] = new float[GenresAndScores.length];
        for (int i = 0; i < GenresAndScores.length; i++)
        {
            GenreScores[i] = Float.valueOf(GenresAndScores[1][i]);
        }

        //Now we check which of these genres has the highest number and get its place in the index.
        int Index = GetIndexOfMaximumFloatValue(GenreScores);

        //We then get the string value of this genre from the index.
        String HighestGenre= GenresAndScores[0][Index];

        /*Select 10 unique music and random tracks that belong to this genre. Note ORDER BY newid()
        should make the selection random*/
        String SQLQuery = "SELECT TOP (10) TrackID FROM MusicTrack WHERE Genre = '" +
                HighestGenre + "' ORDER BY newid()";

        int TrackIDs[] = {1,2,3,50,34,43,4,5,9,67};

        return TrackIDs;
    }

    private String[][] SetRecommendationScoreForGenre(String UserID)
    {
        String SQLQuery = "";

        /*Get the list of all unique genres from the MusicTrack table (as long as the genre has
        been listened to at least once by the user)*/
        if (!UserID.equals(""))
        {
            SQLQuery = "SELECT DISTINCT Genre FROM MusicTrack INNER JOIN UserMood ON " +
                    "MusicTrack.TrackID = UserMood.TrackID WHERE UserID = '" + UserID + "'";
        }
        else /*Or get all Genres that all users have ever listened to*/
        {
            SQLQuery = "SELECT DISTINCT Genre FROM MusicTrack";
        }

        int GenreSize = 10;
        String Genres[] = new String[GenreSize];
        for (int i = 0; i < GenreSize; i++)
        {
            Genres[i] = "";
        }

        /*Create a  float Array of the same size as Genres called GenreScores,
        initialize all values as 0.*/
        float GenreScores[] = new float[GenreSize];
        for (int i = 0; i < GenreSize; i++)
        {
            GenreScores[i] = 0;
        }

        /*Create int Array of the same size as Genres called GenreTrackCount, initialize
        all values as 0.*/
        int GenreTrackCount[] = new int[GenreSize];
        for (int i = 0; i < GenreSize; i++)
        {
            GenreTrackCount[i] = 0;
        }

        int TrackArray[] = {1, 2, 4, 23, 12};
        int TrackArraySize = 0;

        /*Get the list of all unique TrackIDs from the UserMood table with a matching UserID.*/
        if (!UserID.equals(""))
        {
            SQLQuery = "SELECT DISTINCT TrackID FROM UserMood WHERE UserID = " +
                    "'" + UserID + "'";
            TrackArraySize = TrackArray.length;
        }
        /*Get the list of all unique TrackIDs from the UserMood table.*/
        else
        {
            SQLQuery = "SELECT DISTINCT TrackID FROM UserMood";
            TrackArraySize = TrackArray.length;
        }

        //Create an empty second float array of the same size as TrackArray called TrackScores.
        float TrackScores[] = new float[TrackArraySize];
        for (int i = 0; i < TrackArraySize; i++)
        {
            /*Get the average mood score for every music track we have.*/
            TrackScores[i] = SetRecommendationScoreForTrack(UserID, String.valueOf(TrackArray[i]));

            /*For the nth TrackID in the TrackArray, match the TrackID with the Genre through the
            MusicTrack table.*/
            SQLQuery = "SELECT DISTINCT Genre FROM MusicTrack WHERE TrackID = " +
                    "'" + TrackArray[i] + "'";

            String TheGenre = "TEST";

            /*Make sure that the Genre Scores correspond to where the string was originally
            added in the Genre array (so that the Classical Music score should go where
            Classical Music was added)*/
            int GenreIndex = GetArrayIndexFromString(Genres, TheGenre);

            /*Add up the scores for each music track in the same genre and count how many music
            tracks are in that genre.*/
            GenreScores[GenreIndex] = GenreScores[GenreIndex] + TrackScores[i];
            GenreTrackCount[GenreIndex] = GenreTrackCount[GenreIndex] + 1;
        }

        String[][] AverageGenreScores = new String[GenreSize][GenreSize];
        for (int i = 0; i < GenreSize; i++)
        {
            /*We now get the average genre score for each genre, which is all of the track scores
            per genre divided by the number of tracks in that genre.*/
            float TrackCount = GenreTrackCount[i];

            //Avoid Divide by Zero Error
            if (TrackCount == 0.0)
            {
                TrackCount = 1;
            }

            float Average = GenreScores[i]/TrackCount;
            String TheGenre = Genres[i];
            AverageGenreScores[0][i] = TheGenre;
            AverageGenreScores[1][i] = String.valueOf(Average);
        }
        return AverageGenreScores;
    }

    private float SetRecommendationScoreForTrack(String UserID, String TrackID)
    {
        float Score = 0;
        String SQLQuery = "";
        int MoodTotal = 0;

        if (!UserID.equals(""))
        {
            //Count the number of rows we need to go through.
            SQLQuery = "SELECT Count(UserID) FROM UserMood WHERE UserID = '" + UserID + "' " +
                    "AND TrackID = '" + TrackID + "'";

            int RowCount = 4;

            /*Get the MoodBefore and MoodAfter strings from the UserMood table by matching the
            record to the UserID and TrackID. Note this gets the nth record in the database as
            we want all the accumulated mood scores*/
            int i;
            for (i = 1; i < RowCount; i++)
            {
                SQLQuery = "SELECT TOP(1) MoodBefore FROM (SELECT ROW_NUMBER() OVER" +
                        "(ORDER BY MoodBefore ASC) AS rownumber, MoodBefore FROM UserMood " +
                        "WHERE UserID = '" + UserID + "' AND TrackID = '" + TrackID + "')" +
                        "AS Mood WHERE rownumber = " + i;

                String BeforeMood = "Happy";

                SQLQuery = "SELECT TOP(1) MoodAfter FROM (SELECT ROW_NUMBER() OVER" +
                        "(ORDER BY MoodAfter ASC) AS rownumber, MoodAfter FROM UserMood " +
                        "WHERE UserID = '" + UserID + "' AND TrackID = '" + TrackID + "')" +
                        "AS Mood WHERE rownumber = " + i;

                String AfterMood = "Sad";

                int MoodBeforeNum  = ConvertMoodToNumber(BeforeMood);
                int MoodAfterNum  = ConvertMoodToNumber(AfterMood);
                MoodTotal = MoodTotal + (MoodAfterNum - MoodBeforeNum);
            }
            return (float) MoodTotal/(float) i;
        }
        else
        {
            //Count the number of rows we need to go through.
            SQLQuery = "SELECT Count(UserID) FROM UserMood WHERE " +
                    "TrackID = '" + TrackID + "'";

            int RowCount = 4;

            /*Get the MoodBefore and MoodAfter strings from the UserMood table by matching the
            record to the TrackID (getting all tracks regardless of user). Note this gets the nth
            record in the database as we want all the accumulated mood scores*/
            int i;
            for (i = 1; i < RowCount; i++)
            {
                SQLQuery = "SELECT TOP(1) MoodBefore FROM (SELECT ROW_NUMBER() OVER" +
                        "(ORDER BY MoodBefore ASC) AS RowNumber, MoodBefore FROM UserMood " +
                        "WHERE TrackID = '" + TrackID + "')" +
                        " AS Mood WHERE RowNumber = " + i;

                String BeforeMood = "Happy";

                SQLQuery = "SELECT TOP(1) MoodAfter FROM (SELECT ROW_NUMBER() OVER" +
                        "(ORDER BY MoodAfter ASC) AS RowNumber, MoodAfter FROM UserMood " +
                        "WHERE TrackID = '" + TrackID + "')" +
                        " AS Mood WHERE RowNumber = " + i;

                String AfterMood = "Sad";

                int MoodBeforeNum  = ConvertMoodToNumber(BeforeMood);
                int MoodAfterNum  = ConvertMoodToNumber(AfterMood);
                MoodTotal = MoodTotal + (MoodAfterNum - MoodBeforeNum);
            }
            return (float) MoodTotal/(float) i;
        }
    }

    public void TrackEnded(int MoodID)
    {
        MoodID = 2; //TEST

        if (MoodID > 1)
        {
            UserEnterMoodAfter(MoodID);
        }
    }

    public int TrackStarted(String UserID, String TrackName, String Genre, String Artist,
                             String Length)
    {
        int TrackID = AddTrack(TrackName, Genre, Artist, Length);
        int MoodID = UserEnterMoodBefore(UserID, TrackID);
        return MoodID;
    }

    private int UserEnterMoodBefore(String UserID, int TrackID)
    {
        int MoodID = 0;

        if (CheckMoodEntry(UserID))
        {
            //ALERT TO USER - ENTER MOOD
            boolean UserEnteredMood = true;
            String BeforeMood = "Happy";

            String MoodBeforeTime = "2018-05-23 06:34:46";
            //ALERT TO USER - ENTER MOOD

            if (UserEnteredMood)
            {
            /*System gets current Date/Time, BeforeMood, UserID and Track ID and adds
            these to UserMood database table.*/
                String SQLQuery = "INSERT INTO UserMood (UserID, TrackID, MoodBefore," +
                        "MoodBeforeTime)\n" +
                        "VALUES('" + UserID + "', '" + TrackID + "', '" +
                        BeforeMood + "', '" + MoodBeforeTime + "')\n" +
                        "SELECT SCOPE_IDENTITY()";
                MoodID = 2;
                return MoodID;
            }
            else
            {
                return -1;
            }
        }
        else
        {
            return -1;
        }
    }

    private boolean UserEnterMoodAfter(int MoodID)
    {
        //ALERT TO USER - ENTER AFTER MOOD
        String AfterMood = "Sad";
        String UserLiked = "Yes";
        String MoodAfterTime = "2018-05-23 06:34:46";
        //ALERT TO USER - ENTER AFTER MOOD

        boolean UserEnteredMood = true;

        if (UserEnteredMood)
        {
            /*System to get the BeforeMood from the table by matching this with MoodID.*/
            String SQLQuery = "SELECT MoodBefore FROM UserMood WHERE MoodID = " + "'" +
                    MoodID + "'";

            /*System to get the UserID from the table by matching this with MoodID.*/
            SQLQuery = "SELECT UserID FROM UserMood WHERE MoodID = " + "'" +
                    MoodID + "'";

            String UserID = "1";

            String BeforeMood = "Happy";
            int BeforeScore = ConvertMoodToNumber(BeforeMood);
            int AfterScore = ConvertMoodToNumber(AfterMood);
            int ScoreDiff = AfterScore - BeforeScore;

            ScoreDiff = 6; //TEST

            if (ScoreDiff < -3 || ScoreDiff > 3)
            {
                //OPEN DIARY ALERT
                String DiaryEntryText = "Dear Diary...";
                String DiaryEntryTime = "2018-05-23 06:34:46";
                //OPEN DIARY ALERT

                //INSERT DIARY ENTRY
                SQLQuery = "INSERT INTO UserDiary (UserID, DiaryEntryDate, DiaryEntryText)\n" +
                        "VALUES('" + UserID + "', '" + DiaryEntryTime + "', '" +
                        DiaryEntryText +"')";
            }

        /*System gets current Date/Time, AfterMood, UserLiked, MoodID and updates the UserMood
        database table with these parameters where MoodID matches.*/
            SQLQuery = "UPDATE UserMood SET MoodAfter = '" + AfterMood + "', " +
                    "MoodAfterTime = '" + MoodAfterTime + "', " + "UserLiked = '" +
                    UserLiked + "', " + "HasBeenRecommended = '" + "No" + "'\n" +
                    "WHERE MoodID = '" + MoodID + "'";

            AddTracksToPlaylist(UserID);

            return true;
        }
        return false;
    }
}
