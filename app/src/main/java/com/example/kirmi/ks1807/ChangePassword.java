/*package com.example.kirmi.ks1807;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ChangePassword extends AppCompatActivity
{
    private final Context context = this;
    final CommonFunctions PasswordFunctions = new CommonFunctions();
    private DatabaseFunctions PasswordDatabaseFunctions;
    String UserID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        PasswordDatabaseFunctions = new DatabaseFunctions(this);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.btn_Home:
                                selectedFragment = BottomNavigationOptions.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        //Display fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, BottomNavigationOptions.newInstance());
        transaction.commit();

        //Get the UserID for this login session.
        Intent intent = getIntent();
        UserID = intent.getStringExtra("UserID");
    }

    public void button_Submit(View view)
    {
        if (ValidateForm())
        {
            Intent intent = new Intent(ChangePassword.this, AccountDetails.class);
            intent.putExtra("UserID", UserID);
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

    private boolean ValidateForm()
    {
        boolean ValidationSuccessful = true;

        String InvalidMessage = "";

        //Convert the contents of the text boxes to strings
        TextView OldPassword = (TextView)findViewById(R.id.EditText_OldPassword);
        TextView NewPassword = (TextView)findViewById(R.id.EditText_NewPassword);
        TextView NewPasswordRepeat = (TextView)findViewById(R.id.EditTextNewPasswordAgain);

        String OldPass = OldPassword.getText().toString();
        String NewPass = NewPassword.getText().toString();
        String NewPassRepeat = NewPasswordRepeat.getText().toString();

        //Validation dialogue
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Invalid Password");
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,int id)
                    {
                        //No action to be taken until validation issue is resolved.
                    }
                });

        //Checks if password fields are empty. Display only the first error we find to the user.
        if (OldPass.equals(""))
        {
            ValidationSuccessful = false;
            InvalidMessage = "You need to enter your old password.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        if (NewPass.equals("") && ValidationSuccessful)
        {
            ValidationSuccessful = false;
            InvalidMessage = "You need to enter your new password.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        if (NewPassRepeat.equals("") && ValidationSuccessful)
        {
            ValidationSuccessful = false;
            InvalidMessage = "You need to enter your new password again.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        //Check if both new passwords match.
        if (!NewPass.equals(NewPassRepeat) && ValidationSuccessful)
        {
            ValidationSuccessful = false;
            InvalidMessage = "The new password you entered does not match what is in confirm password field.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        if (!PasswordFunctions.ValidPassword(NewPass) && ValidationSuccessful)
        {
            ValidationSuccessful = false;
            InvalidMessage = "Password must have at least 8 characters, have at least" +
                    " one upper case and lower case letter and a special character.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        String TestPassword = PasswordFunctions.EncryptPassword(OldPass);
        if (!PasswordDatabaseFunctions.VerifyPassword(UserID, TestPassword) && ValidationSuccessful)
        {
            ValidationSuccessful = false;
            InvalidMessage = "The old password you have specified does not match your current password.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        if (OldPass.equals(NewPass) && ValidationSuccessful)
        {
            ValidationSuccessful = false;
            InvalidMessage = "The new password is exactly the same as your old password! Please use a different password.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        if(ValidationSuccessful)
        {
            NewPass = PasswordFunctions.EncryptPassword(NewPass);

            //Update the password. If it fails then fail the validation as well.
            ValidationSuccessful = PasswordDatabaseFunctions.UpdateNewPassword(UserID, NewPass);
        }

        return ValidationSuccessful;
    }
}
*/