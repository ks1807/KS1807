package com.example.kirmi.ks1807;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class AccountSettings extends Fragment {

    String UserID = "";
//    private SettingsFragment set;
    Spinner alertsSpinner;
    RadioButton yes, no;

    private static final String TAG = "AccountSettingsTabFrag";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_account_settingstab, container, false);
//        TextView txt = (TextView)view.findViewById(R.id.textView3);

        alertsSpinner = (Spinner)view.findViewById(R.id.Spinner_AlertFrequency);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.trackalertoptions,
                R.layout.spinner_item);

        adapter1.setDropDownViewResource(R.layout.spinner_item);
        alertsSpinner.setAdapter(adapter1);

        final RadioButton yes = (RadioButton) view.findViewById(R.id.RadioButton_SettingsYes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yes.setBackgroundResource(R.drawable.settingsyesselected);
                no.setBackgroundResource(R.drawable.settingnonormal);
            }
        });

        final RadioButton no = (RadioButton) view.findViewById(R.id.RadioButton_SettingsNo);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yes.setBackgroundResource(R.drawable.settingsyesnormal);
                no.setBackgroundResource(R.drawable.settingnoselected);
            }
        });

//        if (savedInstanceState != null) {
//            UserID = getArguments().getString("UserID");
//
//        }
//        UserID = set.userID.getText().toString();
//        txt.setText(UserID);
        return view;
    }

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