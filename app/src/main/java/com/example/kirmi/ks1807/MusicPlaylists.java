package com.example.kirmi.ks1807;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.view.View;

public class MusicPlaylists extends AppCompatActivity
{
    private final Context context = this;
    DatabaseFunctions PlayListFunctions = new DatabaseFunctions();
    private ListView_Playlists ListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_playlists);

        //Get the UserID for this login session.
        Intent intent = getIntent();
        String UserID = intent.getStringExtra("UserID");

        DisplayPlaylists(UserID);
    }

    public void AddPlaylist(View view)
    {

    }

    void DisplayPlaylists(String UserID)
    {
        String[] PlayListIDArray;
        String[] PlayListNameArray;

        PlayListIDArray = PlayListFunctions.GetPlaylistIDs(UserID);
        PlayListNameArray = PlayListFunctions.GetPlaylistNames(UserID);

        ListView_Playlists adapter = new ListView_Playlists(this, PlayListNameArray, PlayListIDArray);
        ListView Playlists = (ListView) findViewById(R.id.View_Playlists);
        Playlists.setAdapter(adapter);
    }

    public void Back(View view)
    {
        Intent intent = new Intent(MusicPlaylists.this, CurrentMusic.class);
        startActivity(intent);
    }
}
