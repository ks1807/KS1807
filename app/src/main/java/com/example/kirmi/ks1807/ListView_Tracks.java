package com.example.kirmi.ks1807;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ListView_Tracks extends ArrayAdapter<String>
{
    private final Context context;
    private final String[] TrackName;
    private final String[] TrackID;

    public ListView_Tracks(Context context, String[] TrackName, String[] TrackID)
    {
        super(context, R.layout.tracks, TrackName);
        this.context = context;
        this.TrackName = TrackName;
        this.TrackID = TrackID;
    }
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.tracks, parent, false);
        rowView.setFocusable(false);

        /*Hidden TrackID Field.*/
        TextView TextView_TrackID = (TextView) rowView.findViewById(R.id.Text_TrackID);
        TextView_TrackID.setText(TrackID[position]);

        TextView TextView_TrackName = (TextView) rowView.findViewById(R.id.Text_TrackName);
        TextView_TrackName.setText(TrackName[position]);

        //Handle buttons and add onClickListeners.
        Button DeleteButton = (Button)rowView.findViewById(R.id.btn_DeleteFromPlaylist);

        DeleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Confirm deletion");
                alertDialogBuilder
                        .setMessage("Are you sure you wish to delete this music track from the playlist?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,int id)
                            {
                                //NEED DELETE FUNCTIONALITY HERE


                                //Refresh the page.
                                Intent intent = new Intent(v.getContext(), ThePlayList.class);
                                context.startActivity(intent);
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,int id)
                            {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        TextView_TrackName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //NEED FUNCTIONALITY THAT SENDS THIS TO THE SPOTIFY MUSIC TRACK

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.spotify.com"));
                context.startActivity(browserIntent);
            }
        });

        return rowView;
    }
}
