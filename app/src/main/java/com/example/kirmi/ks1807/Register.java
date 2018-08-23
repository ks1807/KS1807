package com.example.kirmi.ks1807;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Register extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void button_Back(View view)
    {
        Intent intent = new Intent(Register.this, MainActivity.class);
        startActivity(intent);
    }
}
