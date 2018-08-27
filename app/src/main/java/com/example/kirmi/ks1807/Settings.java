package com.example.kirmi.ks1807;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Settings extends AppCompatActivity
{
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

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
                        Intent intent = new Intent(Settings.this, AccountDetails.class);
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

    public void button_Submit(View view)
    {
        if (ValidateForm())
        {
            Intent intent = new Intent(Settings.this, AccountDetails.class);
            startActivity(intent);
        }
    }

    boolean ValidateForm()
    {
        boolean ValidationSuccessful = true;

        //INSERT VALIDATION LOGIC AND ALERTS HERE

        return ValidationSuccessful;
    }
}
