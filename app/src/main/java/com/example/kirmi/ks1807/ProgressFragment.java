package com.example.kirmi.ks1807;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ProgressFragment extends Fragment {

    String UserID = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_progressfrag, null);

        UserID = getArguments().getString("UserID");
        TextView txt = (TextView)view.findViewById(R.id.textView2);
        txt.setText(UserID);
        return view;
    }
}
