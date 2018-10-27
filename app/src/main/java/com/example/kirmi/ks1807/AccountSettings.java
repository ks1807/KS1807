package com.example.kirmi.ks1807;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

public class AccountSettings extends Fragment
{
    String UserID = "";
    Spinner alertsSpinner;
    private DatabaseFunctions SettingFunctions;
    RadioButton yes, no;
    Button submit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_account_settingstab, container, false);

        SettingFunctions = new DatabaseFunctions(getActivity());

        //Passing the user ID
        UserID = Global.UserID;

        // Adding content to the spinner to collect alert frequency
        alertsSpinner = (Spinner)view.findViewById(R.id.Spinner_AlertFrequency);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.trackalertoptions,
                R.layout.spinner_item);

        //Adding custom style to the spinner
        adapter1.setDropDownViewResource(R.layout.spinner_item);
        alertsSpinner.setAdapter(adapter1);

        yes = (RadioButton) view.findViewById(R.id.RadioButton_SettingsYes);
        no = (RadioButton) view.findViewById(R.id.RadioButton_SettingsNo);

        //Changing the radio button look when selected
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yes.setBackgroundResource(R.drawable.settingsyesselected);
                no.setBackgroundResource(R.drawable.settingnonormal);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yes.setBackgroundResource(R.drawable.settingsyesnormal);
                no.setBackgroundResource(R.drawable.settingnoselected);
            }
        });

        submit = (Button)view.findViewById(R.id.btn_SubmitSettings);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UpdateSettings()) {
                    Toast.makeText(getActivity(), "Settings Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Showing the user's current account settings
        ShowUserSettings(view);

        return view;
    }

    private boolean UpdateSettings()
    {
        //Convert the contents of the Radio buttons and Spinner to strings
        String InvalidMessage = "";
        String MakeRecommendation = "";
        String MoodFrequencyText = alertsSpinner.getSelectedItem().toString();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

        if (yes.isChecked())
        {
            MakeRecommendation = "Yes";
        }
        else if (no.isChecked())
        {
            MakeRecommendation = "No";
        }
        else
        {
            InvalidMessage = "No recommendation selected. This error should never happen.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        //Update the user with the settings, return false if the update failed.
        return SettingFunctions.UpdateSettings(MakeRecommendation, MoodFrequencyText, UserID);
    }

    private void ShowUserSettings(View view)
    {
        SettingFunctions.openReadable();

        //Getting the results of user settings from database using the UerID.
        ArrayList<String> tableContent = SettingFunctions.GetUserSettings(UserID);
        String MakeRecommendations = tableContent.get(0);
        String MoodFrequency = tableContent.get(1);

        if (MakeRecommendations.equals("Yes"))
        {
            yes.setChecked(true);
            yes.setBackgroundResource(R.drawable.settingsyesselected);
            no.setBackgroundResource(R.drawable.settingnonormal);
        }
        else if (MakeRecommendations.equals("No"))
        {
            no.setChecked(true);
            yes.setBackgroundResource(R.drawable.settingsyesnormal);
            no.setBackgroundResource(R.drawable.settingnoselected);
        }

        //Set the Spinner position to match the string retrieved from the database.
        ArrayAdapter SpinnerAdapter = (ArrayAdapter) alertsSpinner.getAdapter();
        alertsSpinner.setSelection(SpinnerAdapter.getPosition(MoodFrequency));
    }
}