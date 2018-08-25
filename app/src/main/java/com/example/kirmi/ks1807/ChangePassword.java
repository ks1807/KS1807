package com.example.kirmi.ks1807;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ChangePassword extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
    }

    public void button_Submit(View view)
    {
        //Need to validate data here

        Intent intent = new Intent(ChangePassword.this, CurrentMusic.class);
        startActivity(intent);
    }
}
