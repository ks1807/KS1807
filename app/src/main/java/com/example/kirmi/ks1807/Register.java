package com.example.kirmi.ks1807;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class Register extends AppCompatActivity
{
    private final Context context = this;
    final CommonFunctions Common = new CommonFunctions();
    DatabaseFunctions RegisterFunctions = new DatabaseFunctions();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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
                        Intent intent = new Intent(Register.this, MainActivity.class);
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
        if (ValidateForm())
        {
            Intent intent = new Intent(Register.this, RegisterSecondPage.class);
            startActivity(intent);
        }
    }

    private boolean ValidateForm()
    {
        boolean ValidationSuccessful = true;

        String InvalidMessage = "";

        //Convert the contents of the text boxes to strings
        TextView FirstName = (TextView)findViewById(R.id.EditText_FirstName);
        TextView LastName = (TextView)findViewById(R.id.EditText_LastName);
        TextView Email = (TextView)findViewById(R.id.EditText_Email);
        TextView Age = (TextView)findViewById(R.id.EditText_Age);
        TextView NewPassword = (TextView)findViewById(R.id.EditText_Password);
        TextView NewPasswordRepeat = (TextView)findViewById(R.id.EditText_ConfirmPassword);

        String FName = FirstName.getText().toString();
        String LName = LastName.getText().toString();
        String TheEmail = Email.getText().toString();
        String TheAge = Age.getText().toString();
        String NewPass = NewPassword.getText().toString();
        String NewPassRepeat = NewPasswordRepeat.getText().toString();

        //Validation dialogue
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Invalid Edits");
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,int id)
                    {
                        //No action to be taken until validation issue is resolved.
                    }
                });

        if (FName.equals("") && LName.equals(""))
        {
            ValidationSuccessful = false;
            InvalidMessage = "You need to enter either a First Name or a Last Name.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        if (!Common.IsEmailValid(TheEmail) && !TheEmail.equals("") && ValidationSuccessful)
        {
            ValidationSuccessful = false;
            InvalidMessage = "Email Address is invalid.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        if (NewPass.equals("") && ValidationSuccessful)
        {
            ValidationSuccessful = false;
            InvalidMessage = "You need to enter your password.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        if (NewPassRepeat.equals("") && ValidationSuccessful)
        {
            ValidationSuccessful = false;
            InvalidMessage = "You need to enter your password again.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        //Check if both new passwords match.
        if (!NewPass.equals(NewPassRepeat) && ValidationSuccessful)
        {
            ValidationSuccessful = false;
            InvalidMessage = "The password you entered does not match what is in confirm password field.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        //Note: User input will normally prevent these errors in the first place.
        //But just in case validate it.
        else if (!Common.isNumeric(TheAge))
        {
            if (!TheAge.equals(""))
            {
                ValidationSuccessful = false;
                InvalidMessage = "Age must be a number.";
                alertDialogBuilder.setMessage(InvalidMessage);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }
        else
        {
            if (!TheAge.equals(""))
            {
                int AgeInt = Integer.parseInt(TheAge);
                if (AgeInt < 0)
                {
                    ValidationSuccessful = false;
                    InvalidMessage = "Age must be a positive number.";
                    alertDialogBuilder.setMessage(InvalidMessage);
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        }
        return ValidationSuccessful;
    }
}
