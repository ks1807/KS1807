package com.example.kirmi.ks1807;

public class TrackDetails
{
    String title, artist, genre, length, spotifyTrackID;
    public TrackDetails()
    {

    }
    public TrackDetails(String spotifyID, String title, String artist, String genre, String length) {
        this.spotifyTrackID = spotifyID;
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.length = length;
    }
    public String getTitle()
    {
        return title;
    }
    public String getArtist()
    {
        return artist;
    }
    public String getGenre()
    {
        return genre;
    }
    public String getLength()
    {
        return length;
    }
    public String getSpotifyTrackID()
    {
        return spotifyTrackID;
    }
}
