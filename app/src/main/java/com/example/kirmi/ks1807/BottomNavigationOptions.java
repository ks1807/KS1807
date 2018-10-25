/*package com.example.kirmi.ks1807;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BottomNavigationOptions extends Fragment
{
    Button HomeButton;
    Button LibraryButton;
    Button ProfileButton;
    Button SettingsButton;

        public static BottomNavigationOptions newInstance()
        {
            BottomNavigationOptions HomeFragment = new BottomNavigationOptions();
            return HomeFragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            View ActivityView = inflater.inflate(R.layout.fragment_bottom_navigation, container,false);
            HomeButton = (Button) ActivityView.findViewById(R.id.btn_Home);
            HomeButton.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent UserIDIntent = getActivity().getIntent();
                    String UserID = UserIDIntent.getStringExtra("UserID");

                    Intent intent = new Intent(getActivity(), CurrentMusic.class);
                    intent.putExtra("UserID", UserID);
                    startActivity(intent);
                }
            });

            LibraryButton = (Button) ActivityView.findViewById(R.id.btn_Library);
            LibraryButton.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent UserIDIntent = getActivity().getIntent();
                    String UserID = UserIDIntent.getStringExtra("UserID");

                    Intent intent = new Intent(getActivity(), MusicPlaylists.class);
                    intent.putExtra("UserID", UserID);
                    startActivity(intent);
                }
            });

            ProfileButton = (Button) ActivityView.findViewById(R.id.btn_Profile);
            ProfileButton.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent UserIDIntent = getActivity().getIntent();
                    String UserID = UserIDIntent.getStringExtra("UserID");

                    Intent intent = new Intent(getActivity(), AccountDetails.class);
                    intent.putExtra("UserID", UserID);
                    startActivity(intent);
                }
            });

            SettingsButton = (Button) ActivityView.findViewById(R.id.btn_Settings);
            SettingsButton.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent UserIDIntent = getActivity().getIntent();
                    String UserID = UserIDIntent.getStringExtra("UserID");

                    Intent intent = new Intent(getActivity(), Settings.class);
                    intent.putExtra("UserID", UserID);
                    startActivity(intent);
                }
            });
            return ActivityView;
        }
}*/