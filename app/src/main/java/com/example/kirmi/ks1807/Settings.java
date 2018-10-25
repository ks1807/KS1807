/*package com.example.kirmi.ks1807;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class Settings extends AppCompatActivity
{
    private final Context context = this;
    CommonFunctions Common = new CommonFunctions();
    private DatabaseFunctions SettingFunctions;
    String UserID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SettingFunctions = new DatabaseFunctions(this);

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

        AddAlertFrequencies();
        ShowUserSettings();
    }

    //Add options to the spinner
    public void AddAlertFrequencies()
    {
        Spinner AlertFrequencyDropdown = (Spinner)findViewById(R.id.Spinner_AlertFrequency);
        String[] Frequencies = GetTrackFrequencies();

        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_item, Frequencies);
        AlertFrequencyDropdown.setAdapter(adapter);
    }

    public void button_Back(View view)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Confirm exit");
        alertDialogBuilder
                .setMessage("Are you sure you wish to go back? All changes will be discarded.")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,int id)
                    {
                        Intent intent = new Intent(Settings.this, AccountDetails.class);
                        intent.putExtra("UserID", UserID);
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

    public void button_Submit(View view)
    {
        if (UpdateSettings())
        {
            Intent intent = new Intent(Settings.this, AccountDetails.class);
            intent.putExtra("UserID", UserID);
            startActivity(intent);
        }
    }

    public String[] GetTrackFrequencies()
    {
        return getResources().getStringArray(R.array.trackalertoptions);
    }

    private boolean UpdateSettings()
    {
        //Convert the contents of the Toggle Button and Spinner to strings
        ToggleButton MakeRecommendations = (ToggleButton) findViewById(R.id.ToggleButton_AutoMakeRecommendations);
        Spinner MoodFrequency = (Spinner) findViewById(R.id.Spinner_AlertFrequency);

        String MakeRecommendationsText = MakeRecommendations.getText().toString();
        String MoodFrequencyText = MoodFrequency.getSelectedItem().toString();

        //Update the user with the settings, return false if the update failed.
        return SettingFunctions.UpdateSettings(MakeRecommendationsText, MoodFrequencyText, UserID);
    }

    private void ShowUserSettings()
    {
        SettingFunctions.openReadable();

        ArrayList<String> tableContent = SettingFunctions.GetUserSettings(UserID);
        String MakeRecommendations = tableContent.get(0);
        String MoodFrequency = tableContent.get(1);

        ToggleButton BtnMakeRecommendations = (ToggleButton) findViewById(R.id.ToggleButton_AutoMakeRecommendations);
        Spinner SpinnerMoodFrequency = (Spinner) findViewById(R.id.Spinner_AlertFrequency);

        if (MakeRecommendations.equals("Yes"))
        {
            BtnMakeRecommendations.setChecked(true);
        }
        else if (MakeRecommendations.equals("No"))
        {
            BtnMakeRecommendations.setChecked(false);
        }

        //Set the Spinner position to match the string retrieved from the database.
        ArrayAdapter SpinnerAdapter = (ArrayAdapter) SpinnerMoodFrequency.getAdapter();
        SpinnerMoodFrequency.setSelection(SpinnerAdapter.getPosition(MoodFrequency));
    }
}
*/