/*package com.example.kirmi.ks1807;
import android.content.*;
import android.view.*;
import android.app.AlertDialog;
import android.widget.*;

public class ListView_Playlists extends ArrayAdapter<String>
{
        private final Context context;
        private final String[] PlayListName;
        private final String[] PlayListID;

        public ListView_Playlists(Context context, String[] PlayListName, String[] PlayListID)
        {
            super(context, R.layout.playlists, PlayListName);
            this.context = context;
            this.PlayListName = PlayListName;
            this.PlayListID = PlayListID;
        }
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.playlists, parent, false);
            rowView.setFocusable(false);

            /*Hidden PlayList Field.*/
/*
            final TextView TextView_PlayListID = (TextView) rowView.findViewById(R.id.Text_PlaylistID);
            TextView_PlayListID.setText(PlayListID[position]);

            TextView TextView_PlayListName = (TextView) rowView.findViewById(R.id.Text_PlaylistName);
            TextView_PlayListName.setText(PlayListName[position]);

            //Handle buttons and add onClickListeners.
            Button EditButton = (Button)rowView.findViewById(R.id.btn_EditPlaylist);
            Button DeleteButton = (Button)rowView.findViewById(R.id.btn_DeletePlaylist);

            DeleteButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(final View v)
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Confirm deletion");
                    alertDialogBuilder
                            .setMessage("Are you sure you wish to delete this playlist?")
                            .setCancelable(false)
                            .setPositiveButton("Yes",new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog,int id)
                                {
                                    //NEED DELETE FUNCTIONALITY HERE


                                    //Refresh the page.
                                    Intent intent = new Intent(v.getContext(), MusicPlaylists.class);
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

            EditButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(v.getContext(), ThePlayList.class);
                    String ThePlayListID = TextView_PlayListID.getText().toString();

                    //NEED TO GET THIS
                    String UserID = "DUMMY";

                    intent.putExtra("UserID", UserID);
                    intent.putExtra("PlayListID", ThePlayListID);
                    context.startActivity(intent);
                }
            });

            TextView_PlayListName.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(v.getContext(), ThePlayList.class);
                    String ThePlayListID = TextView_PlayListID.getText().toString();

                    //NEED TO GET THIS
                    String UserID = "DUMMY";

                    intent.putExtra("UserID", UserID);
                    intent.putExtra("PlayListID", ThePlayListID);
                    context.startActivity(intent);
                }
            });

            return rowView;
        }
    }*/