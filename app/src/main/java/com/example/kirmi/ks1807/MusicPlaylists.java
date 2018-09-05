package com.example.kirmi.ks1807;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.view.View;

public class MusicPlaylists extends AppCompatActivity
{
    private final Context context = this;
    DatabaseFunctions PlayListFunctions = new DatabaseFunctions();
    String UserID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_playlists);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.btn_Home:
                                selectedFragment = BottomNavigationOptions.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        //Display fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, BottomNavigationOptions.newInstance());
        transaction.commit();

        //Get the UserID for this login session.
        Intent intent = getIntent();
        UserID = intent.getStringExtra("UserID");

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
        intent.putExtra("UserID", UserID);
        startActivity(intent);
    }
}
