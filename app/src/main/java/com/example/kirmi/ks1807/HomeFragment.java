package com.example.kirmi.ks1807;

import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment
{
    String UserID = "";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Track> listItems;
    private DatabaseFunctions TracksHistory;
    private ArrayList<Track> tracks;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_homefrag, null);

        UserID = Global.UserID;
        TracksHistory = new DatabaseFunctions(getContext());

        for (int i=0; i <= 10; i++) {
            Log.d("ForLoop", "adding the record" + i);
            TracksHistory.InsertMoods(UserID, String.valueOf(i), "angry", "", "happy",
                    "", "yes", "no");
        }

        for (int i=0; i <= 20; i++) {
            Log.d("trackforloop", "record" + i);
            TracksHistory.InsertTrack("goodbye goodbye", "pop", "someone", "2:80");
        }


        tracks = TracksHistory.GetMusicHistory(UserID);


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listItems = new ArrayList<>();
//        Toast.makeText(getContext(), tracks[0] + "/" + tracks[1] + "jlkaj" + tracks.length, Toast.LENGTH_SHORT).show();
        for (int i = 0; i < tracks.size(); i++) {
            Track list = new Track(
                    tracks.get(i).getTitle(),
                    tracks.get(i).getArtist(),
                    tracks.get(i).getGenre(),
                    tracks.get(i).getLength(),
                    tracks.get(i).getBeforemood(),
                    tracks.get(i).getAftermood()
            );
            listItems.add(list);
        }

        adapter = new RecyclerViewAdapter(listItems, getContext());
        recyclerView.setAdapter(adapter);

        return view;
    }
}
