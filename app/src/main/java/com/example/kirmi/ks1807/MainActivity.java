package com.example.kirmi.ks1807;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void button_Login(View view)
    {
        //Add login validation code here and make sure this new intent is wrapped in it

        Intent intent = new Intent(MainActivity.this, CurrentMusic.class);
        startActivity(intent);
    }

    public void button_Register(View view)
    {
        Intent intent = new Intent(MainActivity.this, Register.class);
        startActivity(intent);
    }

}
