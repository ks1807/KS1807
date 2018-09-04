package com.example.kirmi.ks1807;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class AccountDetails extends AppCompatActivity
{
    String UserID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

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

    public void button_Back(View view)
    {
        Intent intent = new Intent(AccountDetails.this, CurrentMusic.class);
        intent.putExtra("UserID", UserID);
        startActivity(intent);
    }

    public void button_ChangePassword(View view)
    {
        Intent intent = new Intent(AccountDetails.this, ChangePassword.class);
        intent.putExtra("UserID", UserID);
        startActivity(intent);
    }

    public void button_ChangeSettings(View view)
    {
        Intent intent = new Intent(AccountDetails.this, Settings.class);
        intent.putExtra("UserID", UserID);
        startActivity(intent);
    }

    public void button_EditUserDetails(View view)
    {
        Intent intent = new Intent(AccountDetails.this, EditUserDetails.class);
        intent.putExtra("UserID", UserID);
        startActivity(intent);
    }
}
