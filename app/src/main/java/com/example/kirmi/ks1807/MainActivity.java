package com.example.kirmi.ks1807;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.spotify.sdk.android.authentication.*;

import static com.spotify.sdk.android.authentication.LoginActivity.REQUEST_CODE;

public class MainActivity extends AppCompatActivity
{
    private final Context context = this;
    private DatabaseFunctions UserFunctions;
    final CommonFunctions Common = new CommonFunctions();
    String UserID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        //run service
        if(!BackgroundService.isRunning)
            new BackgroundServiceStarter().onReceive(context, new Intent());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserFunctions = new DatabaseFunctions(this);

        //Ensures that password hint disappears when user focuses the text box.
        final EditText PasswordTextBox = (EditText) findViewById(R.id.EditText_Password);
        PasswordTextBox.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                String TextBoxString = PasswordTextBox.getText().toString();
                if (hasFocus)
                {
                    PasswordTextBox.setText("");
                }
                /*If user unfocuses the text box but hasn't typed anything yet, repopulate the
                field. But if they have started typing just leave it as it is.*/
                else if (!hasFocus && TextBoxString.equals(""))
                {
                    PasswordTextBox.setText("password");
                }
            }
        });
    }

    public void button_Login(View view)
    {
        if(ValidateLogin())
        {
            Intent intent = new Intent(MainActivity.this, NavBarMain.class);
            intent.putExtra("UserID", UserID);
            startActivity(intent);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        // Check if it's from spotify
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    // Handle successful response
                    Toast.makeText(this, "Got token: " + response.getAccessToken(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, CurrentMusic.class));
                    break;
                case ERROR:
                    // Handle error response
                    Toast.makeText(this, response.getError(), Toast.LENGTH_SHORT).show();
                    break;
                    // Other cases, not sure what they are.
                default:
                    Toast.makeText(this, "An error occured, please try again", Toast.LENGTH_SHORT).show();
            }
        }
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
            ThePassword = Common.EncryptPassword(ThePassword);
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
