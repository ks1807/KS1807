package com.example.kirmi.ks1807;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterSecondPage extends AppCompatActivity
{
    private final Context context = this;
    final CommonFunctions Common = new CommonFunctions();
    private DatabaseFunctions RegisterFunctions;
    String UserID = "";
    Spinner s1, s2, s3, s4;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_second_page);
        RegisterFunctions = new DatabaseFunctions(this);

        //Get the UserID for this login session.
        Intent intent = getIntent();
        UserID = intent.getStringExtra("UserID");

        s1 = (Spinner) findViewById(R.id.spinner1);
        s2 = (Spinner) findViewById(R.id.spinner2);
        s3 = (Spinner) findViewById(R.id.spinner3);
        s4 = (Spinner) findViewById(R.id.spinner4);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.spinner1ques,
                R.layout.spinner_item);

        adapter1.setDropDownViewResource(R.layout.spinner_item);
        s1.setAdapter(adapter1);
        s2.setAdapter(adapter1);
        s3.setAdapter(adapter1);
        s4.setAdapter(adapter1);

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

    public void button_RegisterNow2(View view)
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

//    public void button_Skip(View view)
//    {
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
//        alertDialogBuilder.setTitle("Confirm skip");
//        alertDialogBuilder
//                .setMessage("Are you sure you wish to skip these questions and register your account?")
//                .setCancelable(false)
//                .setPositiveButton("Yes",new DialogInterface.OnClickListener()
//                {
//                    public void onClick(DialogInterface dialog,int id)
//                    {
//                        Intent intent = new Intent(RegisterSecondPage.this, CurrentMusic.class);
//                        intent.putExtra("UserID", UserID);
//                        startActivity(intent);
//                    }
//                })
//                .setNegativeButton("No",new DialogInterface.OnClickListener()
//                {
//                    public void onClick(DialogInterface dialog,int id)
//                    {
//                        dialog.cancel();
//                    }
//                });
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
//    }

    private boolean UpdateSecondPage()
    {

        String TheMusicQuestionOne = "";
        String TheMusicQuestionTwo = "";
        String TheMusicQuestionThree = "";
        String TheMusicQuestionFour = "";

        if (s1.getSelectedItem() != null)
        {
            TheMusicQuestionOne = s1.getSelectedItem().toString();
        }
        if (s2.getSelectedItem() != null)
        {
            TheMusicQuestionTwo = s2.getSelectedItem().toString();
        }
        if (s3.getSelectedItem() != null)
        {
            TheMusicQuestionThree = s3.getSelectedItem().toString();
        }
        if (s4.getSelectedItem() != null)
        {
            TheMusicQuestionFour = s4.getSelectedItem().toString();
        }

        //Update the user with the settings, return false if the update failed.
        return RegisterFunctions.UpdateNewUserSecondPage(TheMusicQuestionOne, TheMusicQuestionTwo,
                TheMusicQuestionThree, TheMusicQuestionFour, UserID);
    }
}
