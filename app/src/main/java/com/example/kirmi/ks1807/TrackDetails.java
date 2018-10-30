package com.example.kirmi.ks1807;

public class TrackDetails {
    String title,  artist,  genre, length, beforemood, aftermood, spotifyTrackID;

    public TrackDetails(String title, String artist, String genre, String length, String beforemood, String aftermood, String spotifyID) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.length = length;
        this.beforemood = beforemood;
        this.aftermood = aftermood;
        this.spotifyTrackID = spotifyID;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getGenre() {
        return genre;
    }

    public String getLength() {
        return length;
    }

    public String getBeforemood() {
        return beforemood;
    }

    public String getAftermood() {
        return aftermood;
    }

    public String getSpotifyTrackID() {
        return spotifyTrackID;
    }
}


