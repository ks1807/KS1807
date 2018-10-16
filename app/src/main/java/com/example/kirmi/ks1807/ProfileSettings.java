package com.example.kirmi.ks1807;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProfileSettings extends android.support.v4.app.Fragment {

    private static final String TAG = "ProfileSettingsTabFrag";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile_settingstab, container, false);

        return view;
    }
}
