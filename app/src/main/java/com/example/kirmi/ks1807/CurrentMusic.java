package com.example.kirmi.ks1807;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.LinearLayout;

public class CurrentMusic extends AppCompatActivity
{
    private final Context context = this;
    CommonFunctions Common = new CommonFunctions();
    DatabaseFunctions MusicFunctions = new DatabaseFunctions();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_music);

        //Get the UserID for this login session.
        Intent intent = getIntent();
        String UserID = intent.getStringExtra("UserID");

        String[] MusicDetails;
        MusicDetails = MusicFunctions.GetMusicHistory(UserID);
        DisplayUserName(MusicDetails);

        /*Check if the user has a prior history of listening to music through this app.
        If not then make the history fields invisible*/
        if (MusicDetails.length != 0)
        {
            DisplayMusicHistory(MusicDetails);
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

    public void button_ChangePassword(View view)
    {
        Intent intent = new Intent(CurrentMusic.this, ChangePassword.class);
        startActivity(intent);
    }

    public void button_EditUserDetails(View view)
    {
        Intent intent = new Intent(CurrentMusic.this, EditUserDetails.class);
        startActivity(intent);
    }

    public void DisplayUserName(String[] MusicDetails)
    {
        TextView WelcomeUser = (TextView)findViewById(R.id.Text_WelcomeUser);
        WelcomeUser.setText(getResources().getString(R.string.WelcomeUserDefaultCaption)
                + ": " + MusicDetails[0] + " " + MusicDetails[1] + "!");
    }

    public void DisplayMusicHistory(String[] MusicDetails)
    {
        TextView TrackName = (TextView)findViewById(R.id.Text_TrackNameDisplay);
        TrackName.setText(MusicDetails[2]);

        TextView TrackGenre = (TextView)findViewById(R.id.Text_TrackGenreDisplay);
        TrackGenre.setText(MusicDetails[3]);

        TextView TrackArtist = (TextView)findViewById(R.id.Text_TrackArtistDisplay);
        TrackArtist.setText(MusicDetails[4]);

        TextView TrackLength = (TextView)findViewById(R.id.Text_TrackLengthDisplay);
        TrackLength.setText(MusicDetails[5]);

        TextView MoodBefore = (TextView)findViewById(R.id.Text_TrackMoodBeforeDisplay);
        MoodBefore.setText(MusicDetails[6]);

        TextView MoodAfter = (TextView)findViewById(R.id.Text_TrackMoodAfterDisplay);
        MoodAfter.setText(MusicDetails[7]);
    }

}
