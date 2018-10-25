package com.example.kirmi.ks1807;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.text.method.BaseKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class Register extends AppCompatActivity
{
    private final Context context = this;
    private DatabaseFunctions RegisterFunctions;

    long UserID = -1;
    String BackUserID = "";
    String CurrentEmailAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        RegisterFunctions = new DatabaseFunctions(this);

        //Get the UserID for this login session if user went back from second page.
        BackUserID = Global.UserID;

        if (BackUserID != null && !BackUserID.equals(""))
        {
            ShowUserDetails();
        }
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
                        Intent intent = new Intent(Register.this, MainActivity.class);
                        Global.UserID = "";
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

    public void button_Next(View view)
    {

        if (ValidateForm())
        {
            Intent intent = new Intent(Register.this, RegisterSecondPage.class);

            /*If the user has not been to this page before, use the value found in the DB,
            otherwise just use the ID that was passed back.*/
            if (BackUserID == null)
            {
                Global.UserID = String.valueOf(UserID);
                BackUserID = Global.UserID;
            }
            else
            {
                BackUserID = Global.UserID;
            }
            startActivity(intent);
        }
    }

    public void button_RegisterNow(View view)
    {
        if (ValidateForm())
        {

            Intent intent = new Intent(Register.this, OtherPlatforms.class);

            /*If the user has not been to this page before, use the value found in the DB,
            otherwise just use the ID that was passed back.*/
            if (BackUserID == null)
            {
                Global.UserID = String.valueOf(UserID);
                BackUserID = Global.UserID;
            }
            else
            {
                BackUserID = Global.UserID;
            }
            startActivity(intent);
        }
    }

    //Buttons that switch the graphics of the gender radio buttons to indicate what is selected.
    public void RadioButtonMale(View view)
    {
        RadioButton GenderMale = (RadioButton) findViewById(R.id.RadioButton_Male);
        RadioButton GenderFemale = (RadioButton) findViewById(R.id.RadioButton_Female);
        RadioButton GenderOther = (RadioButton) findViewById(R.id.RadioButton_Other);

        GenderMale.setBackgroundResource(R.drawable.maleselected);
        GenderFemale.setBackgroundResource(R.drawable.femaleunselected);
        GenderOther.setBackgroundResource(R.drawable.otherunselected);
    }

    public void RadioButtonYesResearch(View view)
    {
        RadioButton yes1 = (RadioButton) findViewById(R.id.RadioButton_Yes1);
        RadioButton no1 = (RadioButton) findViewById(R.id.RadioButton_No1);

        yes1.setBackgroundResource(R.drawable.yesselected);
        no1.setBackgroundResource(R.drawable.nounselected);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Ethics Statement");
        alertDialogBuilder
                .setMessage("...\n\nDo you agree to comply with the above ethical statement?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,int id) {
                        Toast.makeText(context, "You have agreed.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,int id)
                    {

                        dialog.cancel();

                        RadioButton yes1 = (RadioButton) findViewById(R.id.RadioButton_Yes1);
                        RadioButton no1 = (RadioButton) findViewById(R.id.RadioButton_No1);

                        yes1.setBackgroundResource(R.drawable.yesunselected);
                        no1.setBackgroundResource(R.drawable.noselected);

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    public void RadioButtonNoResearch(View view)
    {
        RadioButton yes1 = (RadioButton) findViewById(R.id.RadioButton_Yes1);
        RadioButton no1 = (RadioButton) findViewById(R.id.RadioButton_No1);

        yes1.setBackgroundResource(R.drawable.yesunselected);
        no1.setBackgroundResource(R.drawable.noselected);
    }

    public void RadioButtonYesMoreQues(View view)
    {
        RadioButton yes = (RadioButton) findViewById(R.id.RadioButton_Yes);
        RadioButton no = (RadioButton) findViewById(R.id.RadioButton_No);
        Button register = (Button) findViewById(R.id.btn_Submit);
        Button next = (Button) findViewById(R.id.btn_Next);

        yes.setBackgroundResource(R.drawable.yesselected);
        no.setBackgroundResource(R.drawable.nounselected);

        next.setVisibility(view.VISIBLE);
        register.setVisibility(view.INVISIBLE);
    }

    public void RadioButtonNoMoreQues(View view)
    {
        RadioButton yes = (RadioButton) findViewById(R.id.RadioButton_Yes);
        RadioButton no = (RadioButton) findViewById(R.id.RadioButton_No);

        Button register = (Button) findViewById(R.id.btn_Submit);
        Button next = (Button) findViewById(R.id.btn_Next);

        yes.setBackgroundResource(R.drawable.yesunselected);
        no.setBackgroundResource(R.drawable.noselected);

        next.setVisibility(view.INVISIBLE);
        register.setVisibility(view.VISIBLE);
    }

    public void RadioButtonFemale(View view)
    {
        RadioButton GenderMale = (RadioButton) findViewById(R.id.RadioButton_Male);
        RadioButton GenderFemale = (RadioButton) findViewById(R.id.RadioButton_Female);
        RadioButton GenderOther = (RadioButton) findViewById(R.id.RadioButton_Other);

        GenderMale.setBackgroundResource(R.drawable.maleunselected);
        GenderFemale.setBackgroundResource(R.drawable.femaleselected);
        GenderOther.setBackgroundResource(R.drawable.otherunselected);
    }

    public void RadioButtonOther(View view)
    {
        RadioButton GenderMale = (RadioButton) findViewById(R.id.RadioButton_Male);
        RadioButton GenderFemale = (RadioButton) findViewById(R.id.RadioButton_Female);
        RadioButton GenderOther = (RadioButton) findViewById(R.id.RadioButton_Other);

        GenderMale.setBackgroundResource(R.drawable.maleunselected);
        GenderFemale.setBackgroundResource(R.drawable.femaleunselected);
        GenderOther.setBackgroundResource(R.drawable.otherselected);
    }

    //Repopulate the fields if user has gone back.
    private void ShowUserDetails()
    {
        RegisterFunctions.openReadable();

        ArrayList<String> tableContent = RegisterFunctions.GetUserDetailsRegisterPage(BackUserID);
        String TheFirstName = tableContent.get(0);
        String TheLastName = tableContent.get(1);
        String TheEmail = tableContent.get(2);
        String TheDateOfBirth = tableContent.get(3);
        String TheGender = tableContent.get(4);

        //Populate all the fields with the database data.
        TextView FirstName = (TextView)findViewById(R.id.EditText_FirstName);
        FirstName.setText(TheFirstName);

        TextView LastName = (TextView)findViewById(R.id.EditText_LastName);
        LastName.setText(TheLastName);

        TextView Email = (TextView)findViewById(R.id.EditText_Email);
        Email.setText(TheEmail);
        CurrentEmailAddress = TheEmail;

        TextView DateOfBirth = (TextView)findViewById(R.id.EditText_DateOfBirth);
        DateOfBirth.setText(TheDateOfBirth);

        final RadioButton GenderMale = (RadioButton) findViewById(R.id.RadioButton_Male);
        final RadioButton GenderFemale = (RadioButton) findViewById(R.id.RadioButton_Female);
        final RadioButton GenderOther = (RadioButton) findViewById(R.id.RadioButton_Other);

        /*Make sure that the buttons have their image and checked status set to what the user
        put in the database*/
        if (TheGender.equals("Male"))
        {
            GenderMale.setBackgroundResource(R.drawable.maleselected);
            GenderFemale.setBackgroundResource(R.drawable.femaleunselected);
            GenderOther.setBackgroundResource(R.drawable.otherunselected);
            GenderMale.setChecked(true);
        }
        else if (TheGender.equals("Female"))
        {
            GenderMale.setBackgroundResource(R.drawable.maleunselected);
            GenderFemale.setBackgroundResource(R.drawable.femaleselected);
            GenderOther.setBackgroundResource(R.drawable.otherunselected);
            GenderFemale.setChecked(true);
        }
        else if (TheGender.equals("Other"))
        {
            GenderMale.setBackgroundResource(R.drawable.maleunselected);
            GenderFemale.setBackgroundResource(R.drawable.femaleunselected);
            GenderOther.setBackgroundResource(R.drawable.otherselected);
            GenderOther.setChecked(true);
        }
    }

    private boolean ValidateForm()
    {
        boolean ValidationSuccessful = true;
        final CommonFunctions Common = new CommonFunctions();
        String InvalidMessage = "";

        //Convert the contents of the text boxes to strings
        TextView FirstName = (TextView)findViewById(R.id.EditText_FirstName);
        TextView LastName = (TextView)findViewById(R.id.EditText_LastName);
        TextView Email = (TextView)findViewById(R.id.EditText_Email);
        TextView DateOfBirth = (TextView)findViewById(R.id.EditText_DateOfBirth);
        TextView NewPassword = (TextView)findViewById(R.id.EditText_Password);
        TextView NewPasswordRepeat = (TextView)findViewById(R.id.EditText_ConfirmPassword);

        String FName = FirstName.getText().toString();
        String LName = LastName.getText().toString();
        String TheEmail = Email.getText().toString();
        String TheDateOfBirth = DateOfBirth.getText().toString();
        String NewPass = NewPassword.getText().toString();
        String NewPassRepeat = NewPasswordRepeat.getText().toString();

        //Get the gender
        RadioButton GenderFemale = (RadioButton)findViewById(R.id.RadioButton_Female);
        RadioButton GenderMale = (RadioButton)findViewById(R.id.RadioButton_Male);
        RadioButton GenderOther = (RadioButton)findViewById(R.id.RadioButton_Other);

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
        the user is not changing their email address (if they already set this before)*/
        if (!RegisterFunctions.IsEmailAddressUnique(TheEmail)
                && !TheEmail.toLowerCase().equals(CurrentEmailAddress.toLowerCase())
                && ValidationSuccessful)
        {
            ValidationSuccessful = false;
            InvalidMessage = "Email Address is already in use. Please pick another one.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        if (NewPass.equals("") && ValidationSuccessful)
        {
            ValidationSuccessful = false;
            InvalidMessage = "You need to enter your password.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        if (NewPassRepeat.equals("") && ValidationSuccessful)
        {
            ValidationSuccessful = false;
            InvalidMessage = "You need to enter your password again.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        if (!Common.ValidPassword(NewPass) && ValidationSuccessful)
        {
            ValidationSuccessful = false;
            InvalidMessage = "Password must have at least 8 characters, have at least" +
                    " one upper case, lower case letter, a number and a special character.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        //Check if both new passwords match.
        if (!NewPass.equals(NewPassRepeat) && ValidationSuccessful)
        {
            ValidationSuccessful = false;
            InvalidMessage = "The password you entered does not match what is in confirm password field.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        if (!TheDateOfBirth.equals("") && ValidationSuccessful)
        {
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

        RadioGroup researchques = (RadioGroup) findViewById(R.id.RadioGroup_MoreResearch);
        RadioGroup moremood = (RadioGroup) findViewById(R.id.RadioGroup_MoreMood);

        //Checking if the questions regarding the research and the extra mood questions in checked for it to continue on
        if (ValidationSuccessful && (researchques.getCheckedRadioButtonId() == -1)) {
            ValidationSuccessful = false;
            InvalidMessage = "All fields must be filled and/or selected";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        if (ValidationSuccessful && (moremood.getCheckedRadioButtonId() == -1)) {
            ValidationSuccessful = false;
            InvalidMessage = "All fields must be filled and/or selected";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }


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

        /*Insert if this is the first time the user is on this page, otherwise just update what is
        already there*/
        if (ValidationSuccessful && (BackUserID == "" || BackUserID == null))
        {
            NewPass = Common.EncryptPassword(NewPass);
            //Insert the record. If it fails then fail the validation as well.
            UserID = RegisterFunctions.InsertNewUser(FName, LName, TheEmail, TheDateOfBirth,
                    TheGender, NewPass);

            //Storing the new created user ID as global so that it can be used throughout the project
            Global.UserID = String.valueOf(UserID);
            BackUserID = Global.UserID;

            if (UserID == -1)
            {
                ValidationSuccessful = false;
            }
        }
        else if(ValidationSuccessful)
        {
            NewPass = Common.EncryptPassword(NewPass);

            //Update the record. If it fails then fail the validation as well.
            ValidationSuccessful = RegisterFunctions.UpdateNewUser(FName, LName, TheEmail,
                    TheDateOfBirth, TheGender, NewPass, BackUserID);
        }
        return ValidationSuccessful;
    }
}
