package com.example.kirmi.ks1807;

import android.content.*;
import android.view.*;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import android.widget.*;

//INCOMPLETE


public class RecyclerView_Playlists
{
    private final String[] values;
    private final String[] TrackID;



    public class PlaylistsViewHolder extends RecyclerView.ViewHolder
    {
        public TextView PlayListID, PlayListName;

        public PlaylistsViewHolder(View view)
        {
            super(view);
            PlayListID = (TextView) view.findViewById(R.id.Text_PlaylistID);
            PlayListName = (TextView) view.findViewById(R.id.Text_PlaylistName);
        }
    }


    public RecyclerView_Playlists(String[] TrackName, String[] TrackID)
    {
        this.values = TrackName;
        this.TrackID = TrackID;
    }

    public PlaylistsViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.playlists, parent, false);

        return new PlaylistsViewHolder(itemView);
    }

    public void onBindViewHolder(PlaylistsViewHolder holder, String UserID)
    {
        DatabaseFunctions Playlist = new DatabaseFunctions();
        holder.PlayListID.setText("1");
        holder.PlayListName.setText("Metalica");
    }

    public int getItemCount()
    {
        return values.length;
    }
}
