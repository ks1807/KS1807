package com.example.kirmi.ks1807;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ThePlayList extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_play_list);
    }

    public void Back(View view)
    {
        Intent intent = new Intent(ThePlayList.this, MusicPlaylists.class);
        startActivity(intent);
    }
}
