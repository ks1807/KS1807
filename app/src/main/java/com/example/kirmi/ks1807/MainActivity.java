package com.example.kirmi.ks1807;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

public class MainActivity extends AppCompatActivity
{
    private final Context context = this;
    private DatabaseFunctions UserFunctions;
    String UserID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserFunctions = new DatabaseFunctions(this);
    }

    public void button_Login(View view)
    {
        if(ValidateLogin())
        {
            Intent intent = new Intent(MainActivity.this, CurrentMusic.class);
            intent.putExtra("UserID", UserID);
            startActivity(intent);
        }
    }

    public void button_LoginSpotify(View view)
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse
                ("https://www.spotify.com/login?continue=https%3A%2F%2Fwww.spotify.com%2Fau%2Faccount%2Foverview%2F"));
        startActivity(browserIntent);
    }

    public void button_Register(View view)
    {
        Intent intent = new Intent(MainActivity.this, Register.class);
        startActivity(intent);
    }

    private boolean ValidateLogin()
    {
        boolean ValidationSuccessful = true;
        String InvalidMessage = "";

        //Convert the contents of the text boxes to strings
        TextView EmailAddress = (TextView)findViewById(R.id.EditText_UserName);
        TextView Password = (TextView)findViewById(R.id.EditText_Password);

        String TheEmailAddress = EmailAddress.getText().toString();
        String ThePassword = Password.getText().toString();

        //Validation dialogue
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Invalid Credentials");
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,int id)
                    {
                        //No action to be taken until login issue is resolved.
                    }
                });

        if (TheEmailAddress.equals(""))
        {
            ValidationSuccessful = false;
            InvalidMessage = "You need to enter an Email Address to login.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        if (ThePassword.equals("") && ValidationSuccessful)
        {
            ValidationSuccessful = false;
            InvalidMessage = "You need to enter your password to login.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        //Validate the login and get the UserID. Don't run this if validation failed earlier.
        if(ValidationSuccessful)
        {
            UserID = UserFunctions.VerifyLogin(TheEmailAddress, ThePassword);
        }

        //Blank ID means either the email or password were incorrect.
        if (UserID.equals("") && ValidationSuccessful)
        {
            ValidationSuccessful = false;
            InvalidMessage = "Your login was unsuccessful. Please check that your Email Address" +
                    " and Password have been typed in correctly.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        return ValidationSuccessful;
    }
}
