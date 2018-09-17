package com.example.kirmi.ks1807;

//All of this could be moved to the server...
public class GeneratePlaylists
{
    private int AddTrack(String TrackName, String Genre, String Artist, String Length)
    {
        //Dummy data, to be passed in through Spotify API
        TrackName = "BestMusicTrack";
        Genre = "Rock";
        Artist = "John Adams";
        Length = "3:25";

        //ADD TO DATABASE HERE - Make sure that the track is not added if TrackName already exists.
        //AddTrack(TrackName, Genre, Artist, Length);
        //In either case, get the TrackID and return it.
        int TrackID = 1;

        return TrackID;
    }

    private void AddTracksToPlaylist(String UserID)
    {
        /*Count all rows in the UserMood table that have a match UserID
        and where HasBeenRecommended = “No”.*/

        int NonRecommendedCount = 11;

        if (NonRecommendedCount > 10)
        {
            //NEED A FUNCTION TO CHECK DB FOR USER SETIING MakeRecommendations = Yes
            boolean RecommendationsOn = true;
            if (RecommendationsOn)
            {
                int TrackIDs[] = MakeRecommendation(UserID);
                int UserTrackIDs[] = MakeRecommendationUsers();

                /*Insert a record into the PlayList table with the current UserID, PlayListName
                as ‘Music To Make You Feel Better’ and RecommendedBy as ‘System’.
                Get the PlayListID of this newly inserted record.*/

                for (int i = 0; i < TrackIDs.length; i++)
                {
                    int TrackIDToInsert = TrackIDs[i];

                    /*Insert a record into the TracksInPlayList table with the current
                    UserID, the PlayListID and the RecommendedTrackID from the array.*/
                }

                /*Insert a record into the PlayList table with the current UserID, PlayListName
                as ‘Music that others are listening to’ and RecommendedBy as ‘Users’.
                Get the PlayListID of this newly inserted record.*/

                for (int i = 0; i < UserTrackIDs.length; i++)
                {
                    int TrackIDToInsert = UserTrackIDs[i];

                    /*Insert a record into the TracksInPlayList table with the current
                    UserID, the PlayListID and the UserTrackID from the array.*/
                }
            }
        }
    }

    private boolean CheckMoodEntry()
    {
        /*Get the MoodFrequency (String) parameter from the UserSettings database table.

        Get the MoodAfterTime (Datetime) of the last entry into the UserMood table.*/

        String MoodFrequency = "";

        switch(MoodFrequency)
        {
            case "Once per track" :
                return true;
            case "Once every 15 minutes" /*AND CURRENTDATE - 15 minutes = NOW*/:
                return true;
            case "Once per hour" /*AND CURRENTDATE - 1 Hour = NOW*/:
                return true;
            case "Once per 24 hours" /*AND CURRENTDATE - 24 Hours = NOW*/:
                return true;
            case "Never" :
                return false;
            default :
                return false;
        }
    }

    private int ConvertMoodToNumber(String MoodName)
    {
        int Score = 0;

        return Score;
    }

    private int[] MakeRecommendation(String UserID)
    {
        int TrackIDs[] = {1,2,3};

        return TrackIDs;

    }

    private int[] MakeRecommendationUsers()
    {
        int TrackIDs[] = {1,2,3};

        return TrackIDs;

    }

    private float SetRecommendationScoreForGenre(String UserID)
    {
        int Score = 0;

        return Score;
    }

    private float SetRecommendationScoreForTrack(String UserID, String TrackID)
    {
        float Score = 0;

        return Score;
    }

    public void TrackEnded(int MoodID)
    {
        if (MoodID > 0)
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

        if (CheckMoodEntry())
        {
            //ALERT TO USER - ENTER MOOD
            boolean UserEnteredMood = true;

            if (UserEnteredMood)
            {
                /*System gets current Date/Time, BeforeMood, UserID and Track ID and adds
                these to UserMood database table.*/
                /*Get the mood that was just entered as well*/
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

        boolean UserEnteredMood = true;

        if (UserEnteredMood)
        {
            /*System to get the BeforeMood from the table by matching this with MoodID.*/

            String BeforeMood = "Happy";
            int BeforeScore = ConvertMoodToNumber(BeforeMood);
            int AfterScore = ConvertMoodToNumber(AfterMood);
            int ScoreDiff = AfterScore - BeforeScore;

            if (ScoreDiff < -3 || ScoreDiff > 3)
            {
                //OPEN DIARY ALERT
                //INSERT DIARY ENTRY
            }

            /*System gets current Date/Time, AfterMood, UserLiked, MoodID and updates the UserMood
            database table with these parameters where MoodID matches.*/

            /*Get UserID from table based on MoodID*/

            String UserID = "1";

            AddTracksToPlaylist(UserID);

            return true;
        }
        else
        {
            return false;
        }
    }
}
