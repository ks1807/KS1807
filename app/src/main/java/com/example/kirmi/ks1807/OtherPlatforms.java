package com.example.kirmi.ks1807;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import static com.spotify.sdk.android.authentication.LoginActivity.REQUEST_CODE;

public class OtherPlatforms extends AppCompatActivity
{
    String UserID = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platforms);

        UserID = Global.UserID;
        Toast.makeText(this, UserID, Toast.LENGTH_SHORT).show();
    }

    public void button_back(View view)
    {
        if (Global.UserExtraMoodQuestions.equals("Yes"))
        {
            Intent intent = new Intent(OtherPlatforms.this, RegisterSecondPage.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(OtherPlatforms.this, Register.class);
            startActivity(intent);
        }
    }

    public void button_connect(View view)
    {
        Intent intent = new Intent(OtherPlatforms.this, NavBarMain.class);
        startActivity(intent);
    }

    public void button_LoginSpotify(View view)
    {
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(BackgroundService.CLIENT_ID,
                AuthenticationResponse.Type.TOKEN, BackgroundService.REDIRECT_URI);
        builder.setScopes(new String[]{"streaming"});
        AuthenticationRequest request = builder.build();
        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }
}
