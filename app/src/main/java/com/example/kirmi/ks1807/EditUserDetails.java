/*package com.example.kirmi.ks1807;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.RadioButton;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class EditUserDetails extends AppCompatActivity
{
    private final Context context = this;
    final CommonFunctions Common = new CommonFunctions();
    private DatabaseFunctions UserFunctions;
    String UserID = "";
    String CurrentEmailAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_details);
        UserFunctions = new DatabaseFunctions(this);

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

        ArrayList<String> UserDetails = UserFunctions.GetUserDetails(UserID);

        DisplayUserDetails(UserDetails);
        DisableAllFields();
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
                        Intent intent = new Intent(EditUserDetails.this, AccountDetails.class);
                        intent.putExtra("UserID", UserID);
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

    public void button_Edit(View view)
    {
        EnableAllFields();

        Button EditButton = (Button)findViewById(R.id.btn_Edit);
        EditButton.setVisibility(View.GONE);

        Button ViewButton = (Button)findViewById(R.id.btn_View);
        ViewButton.setVisibility(View.VISIBLE);

        Button SubmitButton = (Button)findViewById(R.id.btn_SubmitEdit);
        SubmitButton.setVisibility(View.VISIBLE);
    }

    public void button_Submit(View view)
    {
        if (ValidateForm())
        {
            Intent intent = new Intent(EditUserDetails.this, AccountDetails.class);
            intent.putExtra("UserID", UserID);
            startActivity(intent);
        }
    }

    public void button_View(View view)
    {
        DisableAllFields();

        Button EditButton = (Button)findViewById(R.id.btn_Edit);
        EditButton.setVisibility(View.VISIBLE);

        Button ViewButton = (Button)findViewById(R.id.btn_View);
        ViewButton.setVisibility(View.GONE);

        Button SubmitButton = (Button)findViewById(R.id.btn_SubmitEdit);
        SubmitButton.setVisibility(View.GONE);
    }

    private void DisableAllFields()
    {
        TextView FirstName = (TextView)findViewById(R.id.EditText_EditFirstName);
        FirstName.setEnabled(false);

        TextView LastName = (TextView)findViewById(R.id.EditText_EditLastName);
        LastName.setEnabled(false);

        TextView Email = (TextView)findViewById(R.id.EditText_EditEmail);
        Email.setEnabled(false);

        TextView DateOfBirth = (TextView)findViewById(R.id.EditText_EditDateOfBirth);
        DateOfBirth.setEnabled(false);

        RadioButton GenderFemale = (RadioButton)findViewById(R.id.RadioButton_EditFemale);
        GenderFemale.setEnabled(false);

        RadioButton GenderMale = (RadioButton)findViewById(R.id.RadioButton_EditMale);
        GenderMale.setEnabled(false);

        RadioButton GenderOther = (RadioButton)findViewById(R.id.RadioButton_EditOther);
        GenderOther.setEnabled(false);
    }

    public void DisplayUserDetails(ArrayList<String> UserDetails)
    {
        TextView FirstName = (TextView)findViewById(R.id.EditText_EditFirstName);
        FirstName.setText(UserDetails.get(0));

        TextView LastName = (TextView)findViewById(R.id.EditText_EditLastName);
        LastName.setText(UserDetails.get(1));

        TextView Email = (TextView)findViewById(R.id.EditText_EditEmail);
        Email.setText(UserDetails.get(2));
        CurrentEmailAddress = UserDetails.get(2);

        TextView DateOfBirth = (TextView)findViewById(R.id.EditText_EditDateOfBirth);
        DateOfBirth.setText(UserDetails.get(3));

        RadioButton GenderFemale = (RadioButton)findViewById(R.id.RadioButton_EditFemale);
        RadioButton GenderMale = (RadioButton)findViewById(R.id.RadioButton_EditMale);
        RadioButton GenderOther = (RadioButton)findViewById(R.id.RadioButton_EditOther);

        String Gender = UserDetails.get(4);

        /*Make sure that the buttons have their image and checked status set to what the user
        put in the database*/
/*
        if (Gender.equals("Male"))
        {
            GenderMale.setBackgroundResource(R.drawable.maleselected);
            GenderFemale.setBackgroundResource(R.drawable.femaleunselected);
            GenderOther.setBackgroundResource(R.drawable.otherunselected);
            GenderMale.setChecked(true);
        }
        else if (Gender.equals("Female"))
        {
            GenderMale.setBackgroundResource(R.drawable.maleunselected);
            GenderFemale.setBackgroundResource(R.drawable.femaleselected);
            GenderOther.setBackgroundResource(R.drawable.otherunselected);
            GenderFemale.setChecked(true);
        }
        else if (Gender.equals("Other"))
        {
            GenderMale.setBackgroundResource(R.drawable.maleunselected);
            GenderFemale.setBackgroundResource(R.drawable.femaleunselected);
            GenderOther.setBackgroundResource(R.drawable.otherselected);
            GenderOther.setChecked(true);
        }
    }

    private void EnableAllFields()
    {
        TextView FirstName = (TextView)findViewById(R.id.EditText_EditFirstName);
        FirstName.setEnabled(true);

        TextView LastName = (TextView)findViewById(R.id.EditText_EditLastName);
        LastName.setEnabled(true);

        TextView Email = (TextView)findViewById(R.id.EditText_EditEmail);
        Email.setEnabled(true);

        TextView DateOfBirth = (TextView)findViewById(R.id.EditText_EditDateOfBirth);
        DateOfBirth.setEnabled(true);

        RadioButton GenderFemale = (RadioButton)findViewById(R.id.RadioButton_EditFemale);
        GenderFemale.setEnabled(true);

        RadioButton GenderMale = (RadioButton)findViewById(R.id.RadioButton_EditMale);
        GenderMale.setEnabled(true);

        RadioButton GenderOther = (RadioButton)findViewById(R.id.RadioButton_EditOther);
        GenderOther.setEnabled(true);
    }

    //Buttons that switch the graphics of the gender radio buttons to indicate what is selected.
    public void RadioButtonMale(View view)
    {
        RadioButton GenderMale = (RadioButton) findViewById(R.id.RadioButton_EditMale);
        RadioButton GenderFemale = (RadioButton) findViewById(R.id.RadioButton_EditFemale);
        RadioButton GenderOther = (RadioButton) findViewById(R.id.RadioButton_EditOther);

        GenderMale.setBackgroundResource(R.drawable.maleselected);
        GenderFemale.setBackgroundResource(R.drawable.femaleunselected);
        GenderOther.setBackgroundResource(R.drawable.otherunselected);
    }

    public void RadioButtonFemale(View view)
    {
        RadioButton GenderMale = (RadioButton) findViewById(R.id.RadioButton_EditMale);
        RadioButton GenderFemale = (RadioButton) findViewById(R.id.RadioButton_EditFemale);
        RadioButton GenderOther = (RadioButton) findViewById(R.id.RadioButton_EditOther);

        GenderMale.setBackgroundResource(R.drawable.maleunselected);
        GenderFemale.setBackgroundResource(R.drawable.femaleselected);
        GenderOther.setBackgroundResource(R.drawable.otherunselected);
    }

    public void RadioButtonOther(View view)
    {
        RadioButton GenderMale = (RadioButton) findViewById(R.id.RadioButton_EditMale);
        RadioButton GenderFemale = (RadioButton) findViewById(R.id.RadioButton_EditFemale);
        RadioButton GenderOther = (RadioButton) findViewById(R.id.RadioButton_EditOther);

        GenderMale.setBackgroundResource(R.drawable.maleunselected);
        GenderFemale.setBackgroundResource(R.drawable.femaleunselected);
        GenderOther.setBackgroundResource(R.drawable.otherselected);
    }

    private boolean ValidateForm()
    {
        boolean ValidationSuccessful = true;

        String InvalidMessage = "";

        //Convert the contents of the text boxes to strings
        TextView FirstName = (TextView)findViewById(R.id.EditText_EditFirstName);
        TextView LastName = (TextView)findViewById(R.id.EditText_EditLastName);
        TextView Email = (TextView)findViewById(R.id.EditText_EditEmail);
        TextView DateOfBirth = (TextView)findViewById(R.id.EditText_EditDateOfBirth);

        String FName = FirstName.getText().toString();
        String LName = LastName.getText().toString();
        String TheEmail = Email.getText().toString();
        String TheDateOfBirth = DateOfBirth.getText().toString();

        //Validation dialogue
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Invalid Edits");
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,int id)
                    {
                        //No action to be taken until validation issue is resolved.
                    }
                });

        if (FName.equals("") && LName.equals(""))
        {
            ValidationSuccessful = false;
            InvalidMessage = "You need to enter either a First Name or a Last Name.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        if (TheEmail.equals("") && ValidationSuccessful)
        {
            ValidationSuccessful = false;
            InvalidMessage = "You must enter an Email Address.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        if (!Common.IsEmailValid(TheEmail) && !TheEmail.equals("") && ValidationSuccessful)
        {
            ValidationSuccessful = false;
            InvalidMessage = "Email Address is invalid.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        /*Check if the email address is used by another user and also don't trigger validation if
        the user is not changing their address*/
/*
        if (!UserFunctions.IsEmailAddressUnique(TheEmail) &&
                !TheEmail.toLowerCase().equals(CurrentEmailAddress.toLowerCase())
                && ValidationSuccessful)
        {
            ValidationSuccessful = false;
            InvalidMessage = "Email Address is already in use. Please pick another one.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        if (!TheDateOfBirth.equals("") && ValidationSuccessful)
        {
            CommonFunctions Common = new CommonFunctions();
            try
            {
                Date DOBTest = Common.DateFromStringAustraliaFormat(TheDateOfBirth);
                if (DOBTest.after(new Date()))
                {
                    ValidationSuccessful = false;
                    InvalidMessage = "Date of Birth is invalid as it is in the future.";
                    alertDialogBuilder.setMessage(InvalidMessage);
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
            catch (ParseException e)
            {
                e.printStackTrace();
                ValidationSuccessful = false;
                InvalidMessage = "Date of Birth must be a valid date in the format of " +
                        "Day, Month and Year (DD/MM/YYYY).";
                alertDialogBuilder.setMessage(InvalidMessage);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }

        RadioButton GenderMale = (RadioButton)findViewById(R.id.RadioButton_EditMale);
        RadioButton GenderFemale = (RadioButton)findViewById(R.id.RadioButton_EditFemale);
        RadioButton GenderOther = (RadioButton)findViewById(R.id.RadioButton_EditOther);

        String TheGender = "";
        if (GenderMale.isChecked())
        {
            TheGender = "Male";
        }
        else if(GenderFemale.isChecked())
        {
            TheGender = "Female";
        }
        else if(GenderOther.isChecked())
        {
            TheGender = "Other";
        }
        else
        {
            ValidationSuccessful = false;
            InvalidMessage = "No gender. This error should never happen.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        if(ValidationSuccessful)
        {
            //Update the record. If it fails then fail the validation as well.
            ValidationSuccessful = UserFunctions.UpdateCurrentUser(FName, LName, TheEmail,
                    TheDateOfBirth, TheGender, UserID);
        }

        return ValidationSuccessful;
    }
}*/