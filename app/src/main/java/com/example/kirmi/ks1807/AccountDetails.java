package com.example.kirmi.ks1807;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AccountDetails extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        //Get the UserID for this login session.
        Intent intent = getIntent();
        String UserID = intent.getStringExtra("UserID");
    }

    public void button_Back(View view)
    {
        Intent intent = new Intent(AccountDetails.this, CurrentMusic.class);
        startActivity(intent);
    }

    public void button_ChangePassword(View view)
    {
        Intent intent = new Intent(AccountDetails.this, ChangePassword.class);
        startActivity(intent);
    }

    public void button_ChangeSettings(View view)
    {
        Intent intent = new Intent(AccountDetails.this, Settings.class);
        startActivity(intent);
    }

    public void button_EditUserDetails(View view)
    {
        Intent intent = new Intent(AccountDetails.this, EditUserDetails.class);
        startActivity(intent);
    }
}
