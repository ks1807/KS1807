package com.example.kirmi.ks1807;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

public class spotifyActiveReceiver extends BroadcastReceiver
{
    public void onReceive(Context context, Intent intent)
    {
        context.startService(new Intent(context, spotifyService.class));
    }
}
