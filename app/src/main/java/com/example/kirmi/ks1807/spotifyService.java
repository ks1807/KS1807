package com.example.kirmi.ks1807;

import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.app.Service;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;


public class spotifyService extends Service
{
    private final IBinder mBinder = new LocalBinder();
    private Context t;
    private static final String CLIENT_ID = "9a7355bd24ff4544b4bdada73483aaa0";
    private static final String REDIRECT_URI = "com.example.kirmi.ks1807://callback";
    public SpotifyAppRemote mSpotifyAppRemote;

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        //save context
        t = this;
        Toast.makeText(this, "Service created!", Toast.LENGTH_LONG).show();
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();
        SpotifyAppRemote.CONNECTOR.connect(this, connectionParams, new Connector.ConnectionListener()
        {
            @Override
            public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                mSpotifyAppRemote = spotifyAppRemote;
                connected();
                Log.d("spotifyService.CONNECT", "onConnected: CONNNECTED");
            }
            @Override
            public void onFailure(Throwable throwable) {
                Log.e("spotifyService.CONNECT", throwable.getMessage(), throwable);
                stopSelf();
            }
        });
    }

    void connected()
    {
        //Music change detector
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState().setEventCallback(new Subscription.EventCallback<PlayerState>() {
            public void onEvent(PlayerState playerState) {
                final Track track = playerState.track;
                if (track != null) {
                    Toast.makeText(t, track.name + " by " + track.artist.name, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startid) {
        Log.d("onStartCommand", "Service call music change");
        return START_NOT_STICKY;
    }
    //Binder
    public class LocalBinder extends Binder
    {
        public spotifyService getService()
        {
            return spotifyService.this;
        }
    }
}
