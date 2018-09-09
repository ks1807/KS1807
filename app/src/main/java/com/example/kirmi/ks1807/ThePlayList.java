package com.example.kirmi.ks1807;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class ThePlayList extends AppCompatActivity
{
    private final Context context = this;
    DatabaseFunctions TrackFunctions = new DatabaseFunctions(context);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_play_list);

        //Get the UserID for this login session.
        Intent intent = getIntent();
        String UserID = intent.getStringExtra("UserID");
        String PlayListID = intent.getStringExtra("PlayListID");

        DisplayTracks(UserID, PlayListID);
    }

    void DisplayTracks(String UserID, String PlayListID)
    {
        String[] TrackIDArray;
        String[] TrackNameArray;

        TrackIDArray = TrackFunctions.GetTrackIDs(UserID, PlayListID);
        TrackNameArray = TrackFunctions.GetTrackNames(UserID, PlayListID);

        ListView_Tracks adapter = new ListView_Tracks(this, TrackNameArray, TrackIDArray);
        ListView Tracks = (ListView) findViewById(R.id.View_Tracks);
        Tracks.setAdapter(adapter);
    }

    public void Back(View view)
    {
        Intent intent = new Intent(ThePlayList.this, MusicPlaylists.class);
        startActivity(intent);
    }
}
