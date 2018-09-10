package com.example.kirmi.ks1807;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class RegisterSecondPage extends AppCompatActivity
{
    private final Context context = this;
    final CommonFunctions Common = new CommonFunctions();
    private DatabaseFunctions RegisterFunctions;
    String UserID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_second_page);
        RegisterFunctions = new DatabaseFunctions(this);

        //Get the UserID for this login session.
        Intent intent = getIntent();
        UserID = intent.getStringExtra("UserID");
    }

    //Confirm if the user wants to go back if the button is pressed.
    public void button_Back(View view)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Confirm going back to previous page");
        alertDialogBuilder
                .setMessage("Are you sure you wish to go back? All changes on this page will be discarded.")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,int id)
                    {
                        Intent intent = new Intent(RegisterSecondPage.this, Register.class);
                        intent.putExtra("UserID", UserID);
                        startActivity(intent);
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

    public void button_Next(View view)
    {
        if (UpdateSecondPage())
        {
            Intent intent = new Intent(RegisterSecondPage.this, CurrentMusic.class);
            intent.putExtra("UserID", UserID);
            startActivity(intent);
        }
        else
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle("Update Error");
            alertDialogBuilder
                    .setMessage("Database failed to update!")
                    .setCancelable(false)
                    .setPositiveButton("Ok",new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog,int id)
                        {

                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    public void button_Skip(View view)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Confirm skip");
        alertDialogBuilder
                .setMessage("Are you sure you wish to skip these questions and register your account?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,int id)
                    {
                        Intent intent = new Intent(RegisterSecondPage.this, CurrentMusic.class);
                        intent.putExtra("UserID", UserID);
                        startActivity(intent);
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

    private boolean UpdateSecondPage()
    {
        //Convert the contents of the text boxes to strings
        TextView PreferredPlatform = (TextView)findViewById(R.id.EditText_MusicApp);
        TextView MusicQuestionOne = (TextView)findViewById(R.id.EditText_GeneralMood);
        TextView MusicQuestionTwo = (TextView)findViewById(R.id.EditText_MusicListenQuestion);

        String ThePreferredPlatform = PreferredPlatform.getText().toString();
        String TheMusicQuestionOne = MusicQuestionOne.getText().toString();
        String TheMusicQuestionTwo = MusicQuestionTwo.getText().toString();

        //Get the gender
        RadioButton MoodYes = (RadioButton)findViewById(R.id.RadioButton_MoodYes);
        RadioButton MoodNo = (RadioButton)findViewById(R.id.RadioButton_MoodNo);

        String TheMood = "";
        if (MoodYes.isChecked())
        {
            TheMood = MoodYes.getText().toString();
        }
        else if(MoodNo.isChecked())
        {
            TheMood = MoodNo.getText().toString();
        }

        //Update the user with the settings, return false if the update failed.
        boolean Successful = RegisterFunctions.UpdateNewUserSecondPage(ThePreferredPlatform, TheMusicQuestionOne,
                TheMusicQuestionTwo, TheMood, UserID);



        return Successful;
    }
}
