package com.example.kirmi.ks1807;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.app.Service;
import android.content.Intent;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;


public class BackgroundService extends Service
{
    private static final int NOTIFICATION_FOREGROUND_ID = 1; //ID for mandatory foreground notification
    private static final String NOTIFICATION_CHANNEL_ID = "MandatoryChannelID"; //Channel ID for mandatory foreground notification
    private final IBinder binder = new LocalBinder();
    private Context t;
    public static final String CLIENT_ID = "9a7355bd24ff4544b4bdada73483aaa0";
    public static final String REDIRECT_URI = "com.example.kirmi.ks1807://callback";
    public SpotifyAppRemote spotifyAppRemote;

    //Binder method - gives the main application a spotifyAppRemote instance - temporary, should use Web API if possible.
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    //Binder implementation
    class LocalBinder extends Binder
    {
        BackgroundService getService()
        {
            return BackgroundService.this;
        }
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Killed", Toast.LENGTH_SHORT).show();
        if(spotifyAppRemote != null)
            SpotifyAppRemote.CONNECTOR.disconnect(spotifyAppRemote);
    }

    @Override
    public void onCreate() {
        //save context
        t = this;
        Toast.makeText(this, "Background Service Created", Toast.LENGTH_SHORT).show();
        //Create mandatory notification for API 26+
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            //Create notification channel
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "MMH", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Main Notification Channel");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Notification.Builder builder = new Notification.Builder(this, NOTIFICATION_CHANNEL_ID);
            builder.setSmallIcon(R.drawable.ic_queue_music_black_24dp);
            builder.setContentTitle("MMH");
            builder.setContentText("Music for Mental Health running");
            //Make the service foreground
            startForeground(NOTIFICATION_FOREGROUND_ID, builder.build());
        }
        //Create connection parameters
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();
        //Try to connect to spotify
        SpotifyAppRemote.CONNECTOR.connect(this, connectionParams, new Connector.ConnectionListener()
        {
            //Connected to Spotify, get appremote instance.
            @Override
            public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                BackgroundService.this.spotifyAppRemote = spotifyAppRemote;
                connected();
                Log.d("BackgroundService", "Established connection with spotify");
            }
            //Connection failed, show error.
            @Override
            public void onFailure(Throwable throwable) {
                Log.e("BackgroundService", "Failed to establish connection with spotify, error: " + throwable.getMessage(), throwable);
                stopSelf();
            }
        });
    }

    void connected()
    {
        //Music change detector
        spotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState().setEventCallback(new Subscription.EventCallback<PlayerState>() {
            public void onEvent(PlayerState playerState) {
                final Track track = playerState.track;
                if (track != null)
                {
                    Toast.makeText(t, track.name + " by " + track.artist.name,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
