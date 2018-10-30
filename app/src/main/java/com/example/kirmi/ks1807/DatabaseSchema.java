package com.example.kirmi.ks1807;

import java.util.ArrayList;

//This file is where the we define all of our tables and columns for our local database.
public class DatabaseSchema
{
    private static final String DBTable_MoodScore = "MoodScore";
    private static final String DBTable_UserAccount = "UserAccount";
    private static final String DBTable_MusicTrack = "MusicTrack";
    private static final String DBTable_PlayList = "PlayList";
    private static final String DBTable_TracksInPlayList = "TracksInPlayList";
    private static final String DBTable_UserMood = "UserMood";
    private static final String DBTable_UserDiary = "UserDiary";
    private static final String DBTable_UserSettings = "UserSettings";

    public  ArrayList<String> CreateAllTables()
    {
        ArrayList<String> TablesToCreate = new ArrayList<String>();

        TablesToCreate.add(CreateMoodScore());
        TablesToCreate.add(CreateUserAccount());
        TablesToCreate.add(CreateMusicTrack());
        TablesToCreate.add(CreatePlayList());
        TablesToCreate.add(CreateUserMood());
        TablesToCreate.add(CreateTracksInPlayList());
        TablesToCreate.add(CreateUserDiary());
        TablesToCreate.add(CreateUserSettings());
        return TablesToCreate;
    }

    public ArrayList<String> DropAllTables()
    {
        ArrayList<String> TablesToDrop = new ArrayList<String>();

        TablesToDrop.add("DROP TABLE IF EXISTS " + DBTable_MoodScore + "; ");
        TablesToDrop.add("DROP TABLE IF EXISTS " + DBTable_UserAccount + "; ");
        TablesToDrop.add("DROP TABLE IF EXISTS " + DBTable_MusicTrack + "; ");
        TablesToDrop.add("DROP TABLE IF EXISTS " + DBTable_PlayList + "; ");
        TablesToDrop.add("DROP TABLE IF EXISTS " + DBTable_TracksInPlayList + "; ");
        TablesToDrop.add("DROP TABLE IF EXISTS " + DBTable_UserMood + "; ");
        TablesToDrop.add("DROP TABLE IF EXISTS " + DBTable_UserDiary + "; ");
        TablesToDrop.add("DROP TABLE IF EXISTS " + DBTable_UserSettings + "; ");
        return TablesToDrop;
    }

    private String CreateMoodScore()
    {
        String MoodScore;
        final String MoodScoreID = "MoodScoreID INTEGER PRIMARY KEY AUTOINCREMENT, ";
        final String Mood = "Mood VARCHAR (100), ";
        final String Score = "Score INTEGER, ";
        final String Emoticon = "Emoticon VARCHAR (100)";

        MoodScore = "CREATE TABLE " + DBTable_MoodScore + "(" + MoodScoreID + Mood + Score
                + Emoticon + ");\n";
        return MoodScore;
    }

    private String CreateMusicTrack()
    {
        String MusicTrack;
        final String TrackID = "TrackID INTEGER PRIMARY KEY AUTOINCREMENT, ";
        final String TrackName = "TrackName VARCHAR (100), ";
        final String Genre = "Genre VARCHAR (100), ";
        final String Artist = "Artist VARCHAR (100), ";
        final String Length = "Length VARCHAR (100), ";
        final String SpotifyTrackID = "SpotifyTrackID VARCHAR (200)";

        MusicTrack = "CREATE TABLE " + DBTable_MusicTrack + "(" + TrackID +
                TrackName + Genre + Artist + Length + SpotifyTrackID + ");\n";
        return MusicTrack;
    }

    private String CreatePlayList()
    {
        String PlayList;
        final String PlayListID = "PlayListID INTEGER PRIMARY KEY AUTOINCREMENT, ";
        final String UserID = "UserID INTEGER, ";
        final String PlayListName = "PlayListName VARCHAR (100), ";
        final String RecommendedBy = "RecommendedBy VARCHAR (100)";

        PlayList = "CREATE TABLE " + DBTable_PlayList + "(" + PlayListID + UserID +
                PlayListName + RecommendedBy + ");\n";
        return PlayList;
    }

    private String CreateTracksInPlayList()
    {
        String TracksInPlayList;
        final String TracksInPlayListID = "TracksInPlayListID INTEGER PRIMARY KEY AUTOINCREMENT, ";
        final String UserID = "UserID INTEGER, ";
        final String PlayListID = "PlayListID INTEGER, ";
        final String TrackID = "TrackID INTEGER";

        TracksInPlayList = "CREATE TABLE " + DBTable_TracksInPlayList + "(" + TracksInPlayListID + UserID +
                PlayListID + TrackID + ");\n";
        return TracksInPlayList;
    }

    private String CreateUserMood()
    {
        String UserMood;
        final String MoodID = "MoodID INTEGER PRIMARY KEY AUTOINCREMENT, ";
        final String UserID = "UserID INTEGER, ";
        final String TrackID = "TrackID INTEGER, ";
        final String MoodBefore = "MoodBefore VARCHAR (100), ";
        final String MoodBeforeTime = "MoodBeforeTime DATETIME, ";
        final String MoodAfter = "MoodAfter VARCHAR (100), ";
        final String MoodAfterTime = "MoodAfterTime DATETIME, ";
        final String UserLiked = "UserLiked VARCHAR (100), ";
        final String HasBeenRecommended = "HasBeenRecommended VARCHAR (100)";

        UserMood = "CREATE TABLE " + DBTable_UserMood + "(" + MoodID + UserID +
                TrackID + MoodBefore + MoodBeforeTime + MoodAfter + MoodAfterTime +
                UserLiked + HasBeenRecommended + ");\n";
        return UserMood;
    }

    private String CreateUserAccount()
    {
        String UserAccount;
        final String UserID = "UserID INTEGER PRIMARY KEY AUTOINCREMENT, ";
        final String FirstName = "FirstName VARCHAR (100), ";
        final String LastName = "LastName VARCHAR (100), ";
        final String DateOfBirth = "DateOfBirth DATE, ";
        final String Gender = "Gender VARCHAR (100), ";
        final String EmailAddress = "EmailAddress VARCHAR (100), ";
        final String PreferredPlatform = "PreferredPlatform VARCHAR (100), ";
        final String SpotifyID = "SpotifyID VARCHAR (100), ";
        final String MusicQuestionOne = "MusicQuestionOne VARCHAR (100), ";
        final String MusicQuestionTwo = "MusicQuestionTwo VARCHAR (100), ";
        final String MusicQuestionThree = "MusicQuestionThree VARCHAR (100),";
        final String MusicQuestionFour = "MusicQuestionFour VARCHAR (100),";
        final String UserPassword = "UserPassword VARCHAR (100)";

        UserAccount = "CREATE TABLE " + DBTable_UserAccount + "(" + UserID + FirstName +
                LastName + DateOfBirth + Gender + EmailAddress + PreferredPlatform +
                SpotifyID + MusicQuestionOne + MusicQuestionTwo + MusicQuestionThree +
                MusicQuestionFour + UserPassword + ");\n";
        return UserAccount;
    }

    private String CreateUserDiary()
    {
        String UserDiary;
        final String UserDiaryID = "UserDiaryID INTEGER PRIMARY KEY AUTOINCREMENT, ";
        final String UserID = "UserID INTEGER, ";
        final String DiaryEntryDate = "DiaryEntryDate DATETIME, ";
        final String DiaryEntry = "DiaryEntryText VARCHAR (2000)";

        UserDiary = "CREATE TABLE " + DBTable_UserDiary + "(" + UserDiaryID + UserID +
                DiaryEntryDate + DiaryEntry + ");\n";
        return UserDiary;
    }

    private String CreateUserSettings()
    {
        String UserSettings;
        final String UserSettingID = "UserSettingID INTEGER PRIMARY KEY AUTOINCREMENT, ";
        final String UserID = "UserID INTEGER, ";
        final String MoodFrequency = "MoodFrequency VARCHAR (100), ";
        final String MakeRecommendations = "MakeRecommendations VARCHAR (100), ";
        final String RememberLogin = "RememberLogin VARCHAR (100)";

        UserSettings = "CREATE TABLE " + DBTable_UserSettings + "(" + UserSettingID + UserID +
                MoodFrequency + MakeRecommendations + RememberLogin + ");\n";
        return UserSettings;
    }
}
