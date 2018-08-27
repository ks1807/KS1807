package com.example.kirmi.ks1807;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

public class MainActivity extends AppCompatActivity
{
    DatabaseFunctions UserFunctions = new DatabaseFunctions();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void button_Login(View view)
    {
        //Add login validation code here and make sure this new intent is wrapped in it

        if(ValidateLogin())
        {
            String UserID = UserFunctions.GetUserID();

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

        //INSERT VALIDATION LOGIC AND ALERTS HERE

        return ValidationSuccessful;
    }
}
