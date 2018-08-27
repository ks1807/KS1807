package com.example.kirmi.ks1807;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

public class MainActivity extends AppCompatActivity
{
    private static final String CLIENT_ID = "9a7355bd24ff4544b4bdada73483aaa0";
    private static final String REDIRECT_URI = "com.example.kirmi.ks1807://callback";
    private SpotifyAppRemote mSpotifyAppRemote;
    final DatabaseFunctions UserFunctions = new DatabaseFunctions();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void button_Login(View view)
    {
        if(ValidateLogin())
        {
            String UserID = UserFunctions.GetUserID();

            Intent intent = new Intent(MainActivity.this, CurrentMusic.class);
            intent.putExtra("UserID", UserID);
            startActivity(intent);
        }
    }

    public void button_LoginSpotify(View view)
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse
                ("https://www.spotify.com/login?continue=https%3A%2F%2Fwww.spotify.com%2Fau%2Faccount%2Foverview%2F"));
        startActivity(browserIntent);
    }

    public void button_Register(View view)
    {
        Intent intent = new Intent(MainActivity.this, Register.class);
        startActivity(intent);
    }


    //---SPOTIFY TEST CODE---
    //This needs to go into a service later on.
    @Override
    protected void onStart() {
        super.onStart();
        ConnectionParams connectionParams =
            new ConnectionParams.Builder(CLIENT_ID)
                .setRedirectUri(REDIRECT_URI)
                .showAuthView(true)
                .build();
        SpotifyAppRemote.CONNECTOR.connect(this, connectionParams,
                new Connector.ConnectionListener() {

                    @Override
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("MainActivity", "Connected! Yay!");
                        connected();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e("MainActivity", throwable.getMessage(), throwable);
                    }
                });
    }

    private void connected() {
        mSpotifyAppRemote.getPlayerApi().play("spotify:user:spotify:playlist:37i9dQZF1DX7K31D69s4M1");
    }

    @Override
    protected void onStop() {
        super.onStop();
        SpotifyAppRemote.CONNECTOR.disconnect(mSpotifyAppRemote);
    }
    ///---SPOTIFY TEST CODE---
    //For Emoji Experiment - Not to be in the final application
    public void button_Experiment(View view)
    {
        Intent intent = new Intent(MainActivity.this, EmojiExperiment.class);
        startActivity(intent);
    }

    private boolean ValidateLogin()
    {
        boolean ValidationSuccessful = true;

        //INSERT VALIDATION LOGIC AND ALERTS HERE

        return ValidationSuccessful;
    }
}
