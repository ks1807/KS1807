package com.example.kirmi.ks1807;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.RadioButton;

public class EditUserDetails extends AppCompatActivity
{
    private final Context context = this;
    CommonFunctions Common = new CommonFunctions();
    DatabaseFunctions UserFunctions = new DatabaseFunctions();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_details);

        //Get the UserID for this login session.
        Intent intent = getIntent();
        String UserID = intent.getStringExtra("UserID");

        String[] UserDetails;
        UserDetails = UserFunctions.GetUserDetails(UserID);
        DisplayUserDetails(UserDetails);
    }

    public void button_Submit(View view)
    {
        //Need to validate data here

        Intent intent = new Intent(EditUserDetails.this, CurrentMusic.class);
        startActivity(intent);
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
                        Intent intent = new Intent(EditUserDetails.this, CurrentMusic.class);
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

    public void DisplayUserDetails(String[] UserDetails)
    {
        TextView FirstName = (TextView)findViewById(R.id.editText_EditFirstname);
        FirstName.setText(UserDetails[0]);

        TextView LastName = (TextView)findViewById(R.id.editText_EditLastName);
        LastName.setText(UserDetails[1]);

        TextView Email = (TextView)findViewById(R.id.editText_EditEmail);
        Email.setText(UserDetails[2]);

        TextView Age = (TextView)findViewById(R.id.editText_EditAge);
        Age.setText(UserDetails[3]);

        RadioButton GenderFemale = (RadioButton)findViewById(R.id.RadioButton_EditFemale);
        RadioButton GenderMale = (RadioButton)findViewById(R.id.RadioButton_EditMale);
        RadioButton GenderOther = (RadioButton)findViewById(R.id.RadioButton_EditOther);

        String Gender = UserDetails[4];

        if (Gender.equals("Male"))
        {
            GenderMale.setChecked(true);
        }
        else if (Gender.equals("Female"))
        {
            GenderFemale.setChecked(true);
        }
        else if (Gender.equals("Other"))
        {
            GenderOther.setChecked(true);
        }
    }
}
