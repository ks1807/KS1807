package com.example.kirmi.ks1807;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.spotify.protocol.client.CallResult;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Track> Tracks;
    private Context context;

    public RecyclerViewAdapter(List<Track> tracks, Context context) {
        Tracks = tracks;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_mostplayedtrack, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Track track = Tracks.get(position);
        holder.tracktitle.setText(track.getTitle());
        holder.artist.setText("Artist: " + track.getArtist());
        holder.genre.setText("Genre: " + track.getGenre());
        holder.length.setText("Length: " + track.getLength());
        holder.beforemood.setText("Your mood before listening: " + track.getBeforemood());
        holder.aftermood.setText("Your mood after listening:   " + track.getAftermood());

        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicked" + position, Toast.LENGTH_SHORT).show();
//                mService.spotifyAppRemote.getPlayerApi().resume();
//                try{
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {}
//                updatePlayerState();
//                updateNames(playerState);

            }
        });

    }
//
//    private void updatePlayerState() {
//        Log.e("CurrentMusic", "BEGUN");
//        mService.spotifyAppRemote.getPlayerApi().getPlayerState()
//                .setResultCallback(new CallResul.ResultCallback<PlayerState>() {
//                    @Override
//                    public void onResult(PlayerState mPlayerState) {
//                        playerState = mPlayerState;
//                        Log.e("CurrentMusic", "ENDED");
//                        updateNames(playerState);
//                    }
//                })
//                .setErrorCallback(new ErrorCallback() {
//                    @Override
//                    public void onError(Throwable throwable) {
//                        Log.e("CurrentMusic", throwable.getMessage());
//                    }
//                });
//    }

    @Override
    public int getItemCount() {
        return Tracks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tracktitle, artist, genre, length, beforemood, aftermood;
        public Button play;

        public ViewHolder(View itemView) {
            super(itemView);

            tracktitle = (TextView)itemView.findViewById(R.id.text_tracktitle);
            artist = (TextView)itemView.findViewById(R.id.Text_artist);
            genre = (TextView)itemView.findViewById(R.id.Text_genre);
            length = (TextView)itemView.findViewById(R.id.Text_length);
            beforemood = (TextView)itemView.findViewById(R.id.Text_moodbefore);
            aftermood = (TextView)itemView.findViewById(R.id.Text_moodafter);

            play = (Button)itemView.findViewById(R.id.btn_Play);


        }
    }
}
