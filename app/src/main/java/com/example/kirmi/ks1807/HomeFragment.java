package com.example.kirmi.ks1807;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment
{
    String UserID = "";
    String password = "";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<RestInterface.TrackDetails> listItems;
    private DatabaseFunctions TracksHistory;
    private ArrayList<RestInterface.TrackDetails> tracks;
    private String[] MusicDetails;

    Retrofit retrofit = RestInterface.getClient();
    RestInterface.Ks1807Client client;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_homefrag, null);

        UserID = Global.UserID;
        password = Global.UserPassword;
        TracksHistory = new DatabaseFunctions(getContext());
        client = retrofit.create(RestInterface.Ks1807Client.class);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listItems = new ArrayList<>();

        Call<String> response = client.GetMusicHistory(UserID, password);
        response.enqueue(new Callback<String>()
        {
            @Override
            public void onResponse(Call<String> call, Response<String> response)
            {
                Log.d("retrofitclick", "SUCCESS: " + response.raw());
                if(response.body().equals("-1"))
                {
                    Toast.makeText(getActivity(), "Failed to get details from server",
                        Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String musicHistory = response.body();
                    listItems = RestInterface.getTrackFromResult(response.body());


//                    MusicDetails = musicHistory.split("\n");
//
//                    for (int i = 0; i < tracks.size(); i++) {
//                        TrackDetails list = new TrackDetails(
//                                tracks.get(i).getTitle(),
//                                tracks.get(i).getArtist(),
//                                tracks.get(i).getGenre(),
//                                tracks.get(i).getLength(),
//                                tracks.get(i).getBeforemood(),
//                                tracks.get(i).getAftermood(),
//                                tracks.get(i).getSpotifyTrackID()
//                        );
//                        listItems.add(list);
//                    }

                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t)
            {
                fail_LoginNetwork();
            }
        });

        adapter = new RecyclerViewAdapter(listItems, getContext());
        recyclerView.setAdapter(adapter);

        return view;

//        for (int i=0; i <= 5; i++) {
//            Log.d("ForLoop", "adding the record" + i);
//            TracksHistory.InsertMoods(UserID, String.valueOf(i), "angry", "", "happy",
//                    "", "yes", "no");
//        }
//
//        for (int i=0; i <= 10; i++) {
//            Log.d("trackforloop", "record" + i);
//            TracksHistory.InsertTrack("goodbye goodbye", "pop", "someone", "2:80", "12dFghVXANMlKmJXsNCbNl");
//        }
//
//        for (int i=0; i <= 5; i++) {
//            Log.d("taki", "record" + i);
//            TracksHistory.InsertTrack("taki taki", "pop", "cardi", "1:80", "11dFghVXANMlKmJXsNCbNl");
//        }



//        tracks = TracksHistory.GetMusicHistory(UserID);

//        Toast.makeText(getContext(), Integer.toString(tracks.size()), Toast.LENGTH_SHORT).show();



    }

    void fail_LoginNetwork()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle("Service Error");
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,int id)
                    {
                    }
                });
        String InvalidMessage = "The service is not available at this time, please try again later " +
                "or contact support";
        alertDialogBuilder.setMessage(InvalidMessage);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
