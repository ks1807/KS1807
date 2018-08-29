package com.example.kirmi.ks1807;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class BottomNavigationOption_One extends Fragment
{
    ImageButton HomeButton;

        public static BottomNavigationOption_One newInstance()
        {
            BottomNavigationOption_One HomeFragment = new BottomNavigationOption_One();
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
            View ActivityView = inflater.inflate(R.layout.fragment_bottom_navigation_option__one, container,false);
            HomeButton = (ImageButton) ActivityView.findViewById(R.id.btn_Home);
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
            return ActivityView;
        }
}