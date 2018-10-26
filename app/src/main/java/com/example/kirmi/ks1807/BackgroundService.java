package com.example.kirmi.ks1807;

import java.io.UnsupportedEncodingException;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.app.Service;
import android.content.Intent;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.android.appremote.api.error.AuthenticationFailedException;
import com.spotify.android.appremote.api.error.CouldNotFindSpotifyApp;
import com.spotify.android.appremote.api.error.LoggedOutException;
import com.spotify.android.appremote.api.error.NotLoggedInException;
import com.spotify.android.appremote.api.error.OfflineModeException;
import com.spotify.android.appremote.api.error.SpotifyConnectionTerminatedException;
import com.spotify.android.appremote.api.error.SpotifyDisconnectedException;
import com.spotify.android.appremote.api.error.UnsupportedFeatureVersionException;
import com.spotify.android.appremote.api.error.UserNotAuthorizedException;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerContext;
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
    public static boolean isRunning = false; //used by activity to check if it should start the service
    public static String lastSong = "First";
    Spinner alertsSpinner;

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
        isRunning = false;
        Toast.makeText(this, "Service Killed", Toast.LENGTH_SHORT).show();
        if(spotifyAppRemote != null)
            SpotifyAppRemote.CONNECTOR.disconnect(spotifyAppRemote);
    }

    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        isRunning = true;
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
            public void onFailure(Throwable error) {
                if(error instanceof AuthenticationFailedException)
                {
                    Toast.makeText(t, "Authentication Failed, please try again", Toast.LENGTH_SHORT).show();
                } else if(error instanceof CouldNotFindSpotifyApp)
                {
                    Toast.makeText(t, "Spotify is not installed", Toast.LENGTH_SHORT).show();
                } else if(error instanceof LoggedOutException)
                {
                    Toast.makeText(t, "You are not logged into Spotify", Toast.LENGTH_SHORT).show();
                } else if(error instanceof NotLoggedInException)
                {
                    Toast.makeText(t, "You are not logged into Spotify", Toast.LENGTH_SHORT).show();
                } else if(error instanceof OfflineModeException)
                {
                    Toast.makeText(t, "This feature is not available in offline mode", Toast.LENGTH_SHORT).show();
                } else if(error instanceof SpotifyConnectionTerminatedException)
                {
                    Toast.makeText(t, "Spotify closed unexpectedly, please try again", Toast.LENGTH_SHORT).show();
                } else if(error instanceof SpotifyDisconnectedException)
                {
                    Toast.makeText(t, "Spotify closed unexpectedly, please try again", Toast.LENGTH_SHORT).show();
                } else if(error instanceof UnsupportedFeatureVersionException)
                {
                    Toast.makeText(t, "Sorry, this feature is not supported", Toast.LENGTH_SHORT).show();
                } else if(error instanceof UserNotAuthorizedException)
                {
                    Toast.makeText(t, "Did not get authorization from Spotify, please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    String[] GetMoods()
    {
        /*String TheMood = "GetMoodList: U+1F606,4,Amused\n" +
                "U+1F620,-4,Angry\n" +
                "U+2639,-3,Annoyed\n" +
                "U+1F610,1,Calm\n" +
                "U+1F603,2,Cheerful\n" +
                "U+1F627,-4,Depressed\n" +
                "U+1F600,3,Excited\n" +
                "U+1F623,-3,Frustrated\n" +
                "U+1F603,2,Good\n" +
                "U+1F626,-2,Grumpy\n" +
                "U+1F642,2,Happy\n" +
                "U+1F61F,-1,Irritated\n" +
                "U+1F601,3,Joyful\n" +
                "U+1F641,-2,Melancholy\n" +
                "U+1F622,-3,Sad\n" +
                "U+1F60A,1,Satisfied\n" +
                "U+1F616,-2,Stressed";*/

        String TheMood = "GetMoodList: 0x1F606,4,Amused\n" +
                "0x1F620,-4,Angry\n";

        TheMood = TheMood.replace("GetMoodList: ","");
        TheMood = TheMood.replace("-","");
        TheMood = TheMood.replaceAll(",[0-9],"," ");
        //TheMood = TheMood.replace("U+","\\u");

        String[] List = TheMood.split("\n");
        for (int i = 0; i < List.length; i++)
        {
            String EmoticonAsString = List[i].split(" ")[0];
            String MoodName = List[i].split(" ")[1];
            int Emoticon = EmoticonAsString.codePointAt(0);
            List[i] = "" + Emoticon + " " + MoodName;
        }
        return List;
    }

    public static final String utf8ToString( byte[] bytes )
    {
        if ( bytes == null )
        {
            return "";
        }

        try
        {
            return new String( bytes, "UTF-8" );
        }
        catch ( UnsupportedEncodingException uee )
        {
            return "";
        }
    }

    void connected()
    {
        //Music change detector
        spotifyAppRemote.getPlayerApi().subscribeToPlayerState().setEventCallback(new Subscription.EventCallback<PlayerState>() {
            public void onEvent(PlayerState playerState) {
                if(!lastSong.equals(playerState.track.uri))
                {
                    //final CharSequence[] items =
                            //{
                            //new String(Character.toChars(0x1F603)) + "Happy",
                            //new String(Character.toChars(0x1F61F)) + "Sad",
                            //new String(Character.toChars(0x1F620)) + "Angry"
                            //};
                    final String[] List;

                    List = GetMoods();
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getApplicationContext());
                    builder.setTitle("How are you feeling?");
                    builder.setItems(List, new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            Toast.makeText(getApplicationContext(), "Selected " + List[i], Toast.LENGTH_SHORT).show();
                        }
                    });
                    android.app.AlertDialog dialog = builder.create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setCancelable(false);
                    dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
                    dialog.show();

                    final Track track = playerState.track;
                    if (track != null)
                    {
                        Toast.makeText(t, track.name + " by " + track.artist.name,
                                Toast.LENGTH_SHORT).show();
                    }
                    lastSong = playerState.track.uri;
                }
                Log.d("playerstate", playerState.toString());
            }
        });
    }
}
