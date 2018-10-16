package com.example.kirmi.ks1807;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class RegisterSecondPage extends AppCompatActivity
{
    private final Context context = this;
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
            Intent intent = new Intent(RegisterSecondPage.this, OtherPlatforms.class);
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

    private boolean UpdateSecondPage()
    {
        //Convert Spinner contents to strings
        Spinner MusicQuestionOne = (Spinner) findViewById(R.id.QuestionOne_Spinner);
        Spinner MusicQuestionTwo = (Spinner) findViewById(R.id.QuestionTwo_Spinner);
        Spinner MusicQuestionThree = (Spinner) findViewById(R.id.QuestionThree_Spinner);
        Spinner MusicQuestionFour = (Spinner) findViewById(R.id.QuestionFour_Spinner);

        String TheMusicQuestionOne = "";
        String TheMusicQuestionTwo = "";
        String TheMusicQuestionThree = "";
        String TheMusicQuestionFour = "";

        if (MusicQuestionOne.getSelectedItem() != null)
        {
            TheMusicQuestionOne = MusicQuestionOne.getSelectedItem().toString();
        }
        if (MusicQuestionTwo.getSelectedItem() != null)
        {
            TheMusicQuestionTwo = MusicQuestionTwo.getSelectedItem().toString();
        }
        if (MusicQuestionThree.getSelectedItem() != null)
        {
            TheMusicQuestionThree = MusicQuestionThree.getSelectedItem().toString();
        }
        if (MusicQuestionFour.getSelectedItem() != null)
        {
            TheMusicQuestionFour = MusicQuestionFour.getSelectedItem().toString();
        }

        //Update the user with the settings, return false if the update failed.
        return RegisterFunctions.UpdateNewUserSecondPage(TheMusicQuestionOne, TheMusicQuestionTwo,
                TheMusicQuestionThree, TheMusicQuestionFour, UserID);
    }
}
