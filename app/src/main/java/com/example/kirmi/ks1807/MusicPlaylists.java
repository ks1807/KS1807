package com.example.kirmi.ks1807;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import android.view.View;

public class MusicPlaylists extends AppCompatActivity
{
    private final Context context = this;
    DatabaseFunctions PlayListFunctions = new DatabaseFunctions();
    private RecyclerView_Playlists ListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_playlists);
    }

    public void AddPlaylist(View view)
    {

    }

    public void DeletePlaylist(View view)
    {

    }

    void DisplayPlaylists(String[] MusicDetails)
    {
        String[] PlayListIDArray;
        String[] PlayListNameArray;

        PlayListIDArray = PlayListFunctions.GetPlaylistIDs("1");
        PlayListNameArray = PlayListFunctions.GetPlaylistIDs("1");

        //NOT WORKING AT THE MOMENT

        //RecyclerView_Playlists adapter = new RecyclerView_Playlists(this, PlayListNameArray, PlayListIDArray);
        //RecyclerView Playlists = (RecyclerView) findViewById(R.id.View_Playlists);
        //Playlists.setAdapter(adapter);
    }

    public void Back(View view)
    {
        Intent intent = new Intent(MusicPlaylists.this, CurrentMusic.class);
        startActivity(intent);
    }
}
