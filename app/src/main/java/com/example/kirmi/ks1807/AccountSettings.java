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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class AccountSettings extends Fragment {

    String UserID = "";
//    private SettingsFragment set;
    Spinner alertsSpinner;
    private DatabaseFunctions SettingFunctions;
    RadioButton yes, no;
    Button submit;

    private static final String TAG = "AccountSettingsTabFrag";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_account_settingstab, container, false);
//        TextView txt = (TextView)view.findViewById(R.id.textView3);


        SettingFunctions = new DatabaseFunctions(getActivity());

        alertsSpinner = (Spinner)view.findViewById(R.id.Spinner_AlertFrequency);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.trackalertoptions,
                R.layout.spinner_item);

        adapter1.setDropDownViewResource(R.layout.spinner_item);
        alertsSpinner.setAdapter(adapter1);

        yes = (RadioButton) view.findViewById(R.id.RadioButton_SettingsYes);
        no = (RadioButton) view.findViewById(R.id.RadioButton_SettingsNo);

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
                    Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        if (savedInstanceState != null) {
//            UserID = getArguments().getString("UserID");
//
//        }
//        UserID = set.userID.getText().toString();
//        txt.setText(UserID);


//        ShowUserSettings(view);
        return view;
    }

    private boolean UpdateSettings()
    {
        //Convert the contents of the Toggle Button and Spinner to strings
        String InvalidMessage = "";
        String MakeRecommendation = "";
        String MoodFrequencyText = alertsSpinner.getSelectedItem().toString();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

        if (yes.isChecked()) {
            MakeRecommendation = "Yes";
        } else if (no.isChecked()) {
            MakeRecommendation = "No";
        } else {
            InvalidMessage = "No recommendation selected. This error should never happen.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        //Update the user with the settings, return false if the update failed.
        return SettingFunctions.UpdateSettings(MakeRecommendation, MoodFrequencyText, UserID);
    }

//    private void ShowUserSettings(View view)
//    {
//        SettingFunctions.openReadable();
//
//        ArrayList<String> tableContent = SettingFunctions.GetUserSettings(UserID);
//        String MakeRecommendations = tableContent.get(0);
//        String MoodFrequency = tableContent.get(1);
//
//        ToggleButton BtnMakeRecommendations = (ToggleButton) view.findViewById(R.id.ToggleButton_AutoMakeRecommendations);
//        Spinner SpinnerMoodFrequency = (Spinner) view.findViewById(R.id.Spinner_AlertFrequency);
//
//        if (MakeRecommendations.equals("Yes"))
//        {
//            BtnMakeRecommendations.setChecked(true);
//        }
//        else if (MakeRecommendations.equals("No"))
//        {
//            BtnMakeRecommendations.setChecked(false);
//        }
//
//        //Set the Spinner position to match the string retrieved from the database.
//        ArrayAdapter SpinnerAdapter = (ArrayAdapter) SpinnerMoodFrequency.getAdapter();
//        SpinnerMoodFrequency.setSelection(SpinnerAdapter.getPosition(MoodFrequency));
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ProfileSettings fragment = new ProfileSettings();
//        Bundle bundle = new Bundle();
//        bundle.putString("UserID", UserID);
//        fragment.setArguments(bundle);
//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.account_frag,fragment).commit();

    }
}