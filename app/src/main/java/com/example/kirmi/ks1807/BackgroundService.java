package com.example.kirmi.ks1807;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.app.Service;
import android.content.Intent;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;
import android.text.format.DateUtils;

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
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
    public static Boolean SongStarted = false;
    Retrofit retrofit = RestInterface.getClient();
    RestInterface.Ks1807Client client;

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
    public void onDestroy()
    {
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
    public void onCreate()
    {
        client = retrofit.create(RestInterface.Ks1807Client.class);
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
            public void onConnected(SpotifyAppRemote spotifyAppRemote)
            {
                BackgroundService.this.spotifyAppRemote = spotifyAppRemote;
                connected();
                Log.d("BackgroundService", "Established connection with spotify");
            }
            //Connection failed, show error.
            @Override
            public void onFailure(Throwable error)
            {
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

    String[][] GetMoods(String MoodList)
    {
        //NOTE THIS IS USED TO AVOID CLUTTERING THE SCREEN DUE TO THE LACK OF DROPDOWN.
        //ONCE THE DROPDOWN IS IMPLEMENTED YOU CAN GET RID OF THIS
        int MaximumDisplay = 6;

        String[] LimitedMoodList = MoodList.split("\n", MaximumDisplay + 1);
        MoodList = "";
        for (int i = 1; i < MaximumDisplay; i++)
        {
            MoodList = MoodList + LimitedMoodList[i] + "\n";
        }
        //END OF CODE LIMITING MOOD LIST SIZE.

        //Begin code to get the scores from the Mood List.

        //Split the incoming string by comma then get its size.
        String[] AllScoresByComma = MoodList.split(",");
        int AllScoresByCommaSize = AllScoresByComma.length;

        /*The scores will only require half as many elements to store as anything that isn't a score
        will be discarded.*/
        int AllScoresSize = AllScoresByCommaSize/2;

        String[] AllScores = new String[AllScoresSize];

        int j = 0;
        for (int i = 0; i < AllScoresByCommaSize; i++)
        {
            //The score will appear in the comma delimited strings in the pattern of 1,3,5, etc
            if (((i % 2) - 1 == 0))
            {
                AllScores[j] = AllScoresByComma[i];
                j++;
            }
        }

        //End code to get the scores from the Mood List.

        //Begin code to get the moods and emoticons from the Mood List.

        /*First start by getting rid of the minus symbol and then all numbers after the comma.*/
        MoodList = MoodList.replace("-","");
        MoodList = MoodList.replaceAll(",[0-9],"," ");

        //Then get each mood and emoticon line by line.
        String[] AllMoods = MoodList.split("\n");
        String[] AllEmoticons = new String[AllMoods.length];

        for (int i = 0; i < AllMoods.length; i++)
        {
            //Emoticons are not being decoded properly. Leaving the code here.
            String EmoticonAsString = AllMoods[i].split(" ")[0];
            String MoodName = AllMoods[i].split(" ")[1];
            int Emoticon = Integer.parseInt(EmoticonAsString,16);

            AllMoods[i] = MoodName;
            AllEmoticons[i] = new String (Character.toChars(Emoticon));
        }
        //End code to get the moods and emoticons from the Mood List.

        String MoodListAndScore[][] = {AllMoods, AllScores, AllEmoticons};
        return MoodListAndScore;
    }

    void connected()
    {
        //Music change detector
        spotifyAppRemote.getPlayerApi().subscribeToPlayerState().setEventCallback(
                new Subscription.EventCallback<PlayerState>()
                {
                    String SpotifyTrackID;
                    String Track;
                    String Artist;
                    String Genre;
                    String Length;
                    String TheMood;
                    String BeforeMood;

                    public void onEvent(final PlayerState playerState)
                    {
                        Call<String> response = client.GetMoodList();
                        response.enqueue(new Callback<String>()
                        {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response)
                            {
                                Log.d("retrofitclick", "SUCCESS: " + response.raw());
                                if(!response.body().equals(""))
                                {
                                    String MoodList = response.body();

                                    final String[][] FullList = GetMoods(MoodList);

                                    /*Break up the two dimensional array of scores, Emoticons and
                                    Mood Names and then convert the scores from String to Integer*/
                                    final String[] List = FullList[0];
                                    final String[] StringScoreList = FullList[1];
                                    final String[] EmoticonList = FullList[2];
                                    int MoodListSize = FullList[0].length;

                                    /*Combine Emoticons and Mood Names for display purposes.
                                    However list of mood names on their own will be maintained as
                                    well as this is what will go into the DB.*/
                                    final String[] MoodAndEmoticonList = new String[MoodListSize];

                                    int[] ScoreList = new int[MoodListSize];

                                    /*Loop used to convert the string numbers to ints and to
                                    combine the mood name and emoticon.*/
                                    for (int i = 0; i < MoodListSize; i++)
                                    {
                                        ScoreList[i] = Integer.parseInt(StringScoreList[i]);
                                        MoodAndEmoticonList[i] = EmoticonList[i] + " " + List[i];
                                    }

                                    //CompleteScoreList needs to be final in order to be accessed in code below.
                                    final int[] CompleteScoreList = ScoreList;

                                    if(!lastSong.equals(playerState.track.uri))
                                    {
                                        android.app.AlertDialog.Builder builder =
                                                new android.app.AlertDialog.Builder(getApplicationContext());

                                        String DialogText;
                                        if (!SongStarted)
                                        {
                                            DialogText = "How are you feeling at the moment?";
                                        }
                                        else
                                        {
                                            DialogText = "How are you feeling now after this last song you played?";
                                        }

                                        builder.setTitle(DialogText);
                                        builder.setItems(MoodAndEmoticonList, new DialogInterface.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i)
                                            {
                                                Toast.makeText(getApplicationContext(), "You selected " +
                                                        MoodAndEmoticonList[i], Toast.LENGTH_SHORT).show();

                                                //Verify if this is before or after.
                                                if (!SongStarted)
                                                {
                                                    SongStarted = true;
                                                    SpotifyTrackID = playerState.track.uri;
                                                    Track = playerState.track.name;
                                                    Artist = playerState.track.artist.name;
                                                    Genre = playerState.track.album.name;
                                                    Length = String.valueOf(DateUtils.formatElapsedTime(
                                                            ((int)playerState.track.duration)/1000));
                                                    TheMood = List[i];
                                                    //For tracking the difference of the before and after moods.
                                                    BeforeMood = List[i];

                                                    String UserID = Global.UserID;
                                                    String UserPassword = Global.UserPassword;

                                                    /*Bug in code - Service runs even when not
                                                    logged in. This stops it crashing in that
                                                    scenario.*/
                                                    if (UserID.equals("") || UserPassword.equals(""))
                                                    {
                                                        UserID = "-1";
                                                        UserPassword = "Dummy";
                                                    }

                                                    Call<String> response = client.TrackStarted(
                                                            SpotifyTrackID, Track, Genre, Artist, Length, TheMood,
                                                            UserID, UserPassword);
                                                    response.enqueue(new Callback<String>()
                                                    {
                                                        @Override
                                                        public void onResponse(Call<String> call, Response<String> response)
                                                        {
                                                            Log.d("retrofitclick", "SUCCESS: " + response.raw());
                                                            if(!response.body().equals("Incorrect UserID or Password. Query not executed."))
                                                            {
                                                                Global.MoodID = response.body();
                                                                Toast.makeText(getApplicationContext(),
                                                                "Mood at start of track updated with Mood ID " + Global.MoodID, Toast.LENGTH_SHORT).show();
                                                            }
                                                            else
                                                            {
                                                                Global.MoodID = "-1";
                                                                Toast.makeText(getApplicationContext(),
                                                                "Error, mood at start of track failed to update", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                        @Override
                                                        public void onFailure(Call<String> call, Throwable t)
                                                        {
                                                            //This crashes on loading.
                                                            //fail_LoginNetwork();
                                                        }
                                                    });
                                                }
                                                else if (SongStarted)
                                                {
                                                    TheMood = List[i];
                                                    String UserID = Global.UserID;
                                                    String UserPassword = Global.UserPassword;

                                                    /*Bug in code - Service runs even when not
                                                    logged in. This stops it crashing in that
                                                    scenario.*/
                                                    if (UserID.equals("") || UserPassword.equals(""))
                                                    {
                                                        UserID = "-1";
                                                        UserPassword = "Dummy";
                                                    }

                                                    Call<String> response = client.TrackEnded(SpotifyTrackID,
                                                            Global.MoodID, TheMood, "-",
                                                            "-", "-", "-",
                                                            UserID, UserPassword);
                                                    response.enqueue(new Callback<String>()
                                                    {
                                                        @Override
                                                        public void onResponse(Call<String> call, Response<String> response)
                                                        {
                                                            Log.d("retrofitclick", "SUCCESS: " + response.raw());
                                                            if(response.body().equals("Incorrect UserID or Password. Query not executed."))
                                                            {
                                                                Toast.makeText(getApplicationContext(),
                                                                        "Error, mood at end of track failed to update", Toast.LENGTH_SHORT).show();
                                                            }
                                                            else
                                                            {
                                                                Toast.makeText(getApplicationContext(),
                                                                        "Mood at end of track updated with Mood ID " + Global.MoodID, Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                        @Override
                                                        public void onFailure(Call<String> call, Throwable t)
                                                        {
                                                            //This crashes on loading.
                                                            //fail_LoginNetwork();
                                                        }
                                                    });

                                                    SongStarted = false;

                                                    //This code crashes the interface - commented out.
                                                    /*
                                                    SpotifyTrackID = playerState.track.uri;
                                                    Track = playerState.track.name;
                                                    Artist = playerState.track.artist.name;
                                                    Genre = playerState.track.album.name;
                                                    Length = String.valueOf(DateUtils.formatElapsedTime(
                                                            ((int)playerState.track.duration)/1000));

                                                   //Rechecking the UserID and password.
                                                    UserID = Global.UserID;
                                                    UserPassword = Global.UserPassword;

                                                    if (UserID.equals("") || UserPassword.equals(""))
                                                    {
                                                        UserID = "-1";
                                                        UserPassword = "Dummy";
                                                    }

                                                    response = client.TrackStarted(
                                                            Track, Genre, Artist, Length, TheMood,
                                                            UserID, UserPassword);
                                                    response.enqueue(new Callback<String>()
                                                    {
                                                        @Override
                                                        public void onResponse(Call<String> call, Response<String> response)
                                                        {
                                                            Log.d("retrofitclick", "SUCCESS: " + response.raw());
                                                            if(!response.body().equals("Incorrect UserID or Password. Query not executed."))
                                                            {
                                                                Global.MoodID = response.body();
                                                            }
                                                            else
                                                            {
                                                                Global.MoodID = "-1";
                                                            }
                                                        }
                                                        @Override
                                                        public void onFailure(Call<String> call, Throwable t)
                                                        {
                                                            fail_LoginNetwork();
                                                        }
                                                    });*/

                                                    CommonFunctions Common = new CommonFunctions();
                                                    int ScoreIndex;

                                                    /*The place in the array for the score should
                                                    match that of where the text based mood is*/
                                                    ScoreIndex = Common.GetArrayIndexFromString(
                                                            List, BeforeMood);
                                                    int BeforeMoodScore = CompleteScoreList[ScoreIndex];

                                                    ScoreIndex = Common.GetArrayIndexFromString(
                                                            List, TheMood);
                                                    int AfterMoodScore = CompleteScoreList[ScoreIndex];

                                                    if (AfterMoodScore - BeforeMoodScore > 3 ||
                                                            AfterMoodScore - BeforeMoodScore < -3)
                                                    {
                                                        //Diary prompt - Not yet implemented.
                                                    }
                                                }
                                            }
                                        });
                                        android.app.AlertDialog dialog = builder.create();
                                        dialog.setCanceledOnTouchOutside(false);
                                        dialog.setCancelable(false);
                                        dialog.getWindow().setType(
                                                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
                                        dialog.show();

                                        final Track track = playerState.track;
                                        if (track != null)
                                        {
                                            Toast.makeText(t, track.name + " by " +
                                                            track.artist.name, Toast.LENGTH_SHORT).show();
                                        }
                                        lastSong = playerState.track.uri;
                                    }
                                    Log.d("playerstate", playerState.toString());
                                }
                            }
                            @Override
                            public void onFailure(Call<String> call, Throwable t)
                            {
                                //This crashes on loading.
                                //fail_LoginNetwork();
                            }
                        });

                    }
                });
    }

    void fail_LoginNetwork()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getApplicationContext());
        alertDialogBuilder.setTitle("Service Error");
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,int id)
                    {
                    }
                });
        String InvalidMessage = "The service is not available at this time, please try again later " +
                "or contact support";
        alertDialogBuilder.setMessage(InvalidMessage);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}