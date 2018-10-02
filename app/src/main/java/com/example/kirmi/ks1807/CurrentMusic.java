package com.example.kirmi.ks1807;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.client.ErrorCallback;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

public class CurrentMusic extends AppCompatActivity
{
    private final Context context = this;
    private DatabaseFunctions MusicFunctions;

    String UserID = "";
    BackgroundService mService;
    PlayerState playerState;
    boolean mBound;

    @Override
    protected void onStop()
    {
        super.onStop();
        unbindService(mConnection);
        Toast.makeText(context, "DISCONNECTED SERVICE", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Intent intent = new Intent(this, BackgroundService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            BackgroundService.LocalBinder binder = (BackgroundService.LocalBinder) service;
            mService = binder.getService();
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
            updatePlayerState();
            Toast.makeText(context, "CONNECTED SERVICE", Toast.LENGTH_LONG).show();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    private void updatePlayerState() {
        Log.e("CurrentMusic", "BEGUN");
        mService.spotifyAppRemote.getPlayerApi().getPlayerState()
                .setResultCallback(new CallResult.ResultCallback<PlayerState>() {
                    @Override
                    public void onResult(PlayerState mPlayerState) {
                        playerState = mPlayerState;
                        Log.e("CurrentMusic", "ENDED");
                        updateNames(playerState);
                    }
                })
                .setErrorCallback(new ErrorCallback() {
                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("CurrentMusic", throwable.getMessage());
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_music);
        MusicFunctions = new DatabaseFunctions(this);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener()
                {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item)
                    {
                        Fragment selectedFragment = null;
                        switch (item.getItemId())
                        {
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

        String[] MusicDetails;
        MusicDetails = MusicFunctions.GetMusicHistory(UserID);

        /*Check if the user has a prior history of listening to music through this app.
        If not then make the history fields invisible*/
        if (MusicDetails.length != 0)
        {
            //DisplayMusicHistory(MusicDetails);
        }
        else
        {
            TextView NoMusicHistory = (TextView)findViewById(R.id.Text_NoMusicHistory);
            NoMusicHistory.setVisibility(View.VISIBLE);

            LinearLayout MusicHistoryGroup = (LinearLayout)findViewById(R.id.Layout_MusicHistoryGroup);
            MusicHistoryGroup.setVisibility(View.GONE);
        }
    }

    public void button_Logout(View view)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Confirm logout");
        alertDialogBuilder
                .setMessage("Are you sure that you want to logout?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,int id)
                    {
                        Intent intent = new Intent(CurrentMusic.this, MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,int id)
                    {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void button_EditAccountDetails(View view)
    {
        Intent intent = new Intent(CurrentMusic.this, AccountDetails.class);
        intent.putExtra("UserID", UserID);
        startActivity(intent);
    }

    public void button_ViewPlaylists(View view)
    {
        Intent intent = new Intent(CurrentMusic.this, MusicPlaylists.class);
        intent.putExtra("UserID", UserID);
        startActivity(intent);
    }

    public void DisplayUserName(String[] MusicDetails)
    {
        TextView WelcomeUser = (TextView)findViewById(R.id.Text_WelcomeUser);
        WelcomeUser.setText(getResources().getString(R.string.WelcomeUserDefaultCaption)
                + ": " + MusicDetails[0] + " " + MusicDetails[1] + "!");
    }

    private void updateNames(PlayerState playerState)
    {
        TextView TrackName = (TextView)findViewById(R.id.Text_TrackNameDisplay);
        TextView TrackGenre = (TextView)findViewById(R.id.Text_TrackGenreDisplay);
        TextView TrackArtist = (TextView)findViewById(R.id.Text_TrackArtistDisplay);
        TextView TrackLength = (TextView)findViewById(R.id.Text_TrackLengthDisplay);
        TextView MoodBefore = (TextView)findViewById(R.id.Text_TrackMoodBeforeDisplay);
        TextView MoodAfter = (TextView)findViewById(R.id.Text_TrackMoodAfterDisplay);
        Track track = playerState.track;
        TrackName.setText(track.name);
        TrackGenre.setText("Example Genre");
        TrackArtist.setText(track.artist.name);
        TrackLength.setText(DateUtils.formatElapsedTime(((int)track.duration)/1000));
        MoodBefore.setText("Sad");
        MoodAfter.setText("Happy");
    }

    public void button_Play(View view)
    {
        mService.spotifyAppRemote.getPlayerApi().resume();
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
        updatePlayerState();
        updateNames(playerState);
    }

    public void button_Pause(View view)
    {
        mService.spotifyAppRemote.getPlayerApi().pause();
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
        updatePlayerState();
        updateNames(playerState);
    }

    public void button_Skip(View view)
    {
        mService.spotifyAppRemote.getPlayerApi().skipNext();
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
        updatePlayerState();
        updateNames(playerState);
    }

}
