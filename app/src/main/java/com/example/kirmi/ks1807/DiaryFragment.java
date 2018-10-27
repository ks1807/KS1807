package com.example.kirmi.ks1807;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class DiaryFragment extends Fragment
{

    String UserID = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_diaryfrag, null);
        UserID = Global.UserID;
        Toast.makeText(getContext(), UserID, Toast.LENGTH_SHORT).show();
        return view;
    }
}
