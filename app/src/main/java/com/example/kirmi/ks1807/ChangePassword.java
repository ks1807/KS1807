package com.example.kirmi.ks1807;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ChangePassword extends AppCompatActivity
{
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        //Get the UserID for this login session.
        Intent intent = getIntent();
        String UserID = intent.getStringExtra("UserID");
    }

    public void button_Submit(View view)
    {
        if (ValidateForm())
        {
            Intent intent = new Intent(ChangePassword.this, AccountDetails.class);
            startActivity(intent);
        }
    }

    //Confirm if the user wants to go back if the button is pressed.
    public void button_Back(View view)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Confirm exit");
        alertDialogBuilder
                .setMessage("Are you sure you wish to go back? All changes will be discarded.")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,int id)
                    {
                        Intent intent = new Intent(ChangePassword.this, AccountDetails.class);
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

    private boolean ValidateForm()
    {
        boolean ValidationSuccessful = true;

        //INSERT VALIDATION LOGIC AND ALERTS HERE

        return ValidationSuccessful;
    }
}
