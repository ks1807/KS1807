package com.example.kirmi.ks1807;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class ProfileSettings extends Fragment
{

    String UserID = "", CurrentEmailAddress = "";
    final CommonFunctions Common = new CommonFunctions();
    private DatabaseFunctions UserFunctions;

    private RadioGroup gender;
    private RadioButton genderMale, genderFemale, genderOther;
    private EditText firstN, lastN, editemail, editdob, oldpass, newpass, newpassagain;
    private Button options, editback, changepassback, btnUpdate, signout, changePassword;
    private LinearLayout userdetails, updatepass;
    private ArrayList<String> UserDetails;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_profile_settingstab, container, false);

        UserID = Global.UserID;
        UserFunctions = new DatabaseFunctions(getContext());


        userdetails = (LinearLayout)view.findViewById(R.id.user_details);

        firstN = (EditText)view.findViewById(R.id.editText_EditFirstName);
        lastN = (EditText)view.findViewById(R.id.editText_EditLastName);
        editemail = (EditText)view.findViewById(R.id.editText_EditEmail);
        editdob = (EditText)view.findViewById(R.id.editText_EditDateOfBirth);

        gender = (RadioGroup)view.findViewById(R.id.RadioGroup_EditGenderSelect);
        genderMale = (RadioButton)view.findViewById(R.id.RadioButton_EditMale);
        genderFemale = (RadioButton)view.findViewById(R.id.RadioButton_EditFemale);
        genderOther = (RadioButton)view.findViewById(R.id.RadioButton_EditOther);

        gender.setEnabled(false);
        editback = (Button)view.findViewById(R.id.btn_profileeditsettingback);
        btnUpdate = (Button)view.findViewById(R.id.btn_updateprofile);

        updatepass = (LinearLayout)view.findViewById(R.id.changepasscontent);

        oldpass = (EditText) view.findViewById(R.id.EditText_OldPassword);
        newpass = (EditText) view.findViewById(R.id.EditText_NewPassword);
        newpassagain = (EditText)view.findViewById(R.id.EditTextNewPasswordAgain);

        changepassback = (Button)view.findViewById(R.id.btn_profilechangepasssettingback);
        changePassword = (Button) view.findViewById(R.id.btnchangepass);

        //Collecting the current user details to be used for display.
        UserDetails = UserFunctions.GetUserDetails(UserID);

        //Function to show all the details within respective text element.
        DisplayUserDetails(UserDetails);

        if (userdetails.getVisibility() != View.VISIBLE)
        {
            userdetails.setVisibility(View.VISIBLE);
            updatepass.setVisibility(View.GONE);
        }

        //Getting the selection from the options dropdown.
        options = (Button)view.findViewById(R.id.btn_Options);
        options.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // Showing the popup menu with the two different options edit and change password
                Context wrapper = new ContextThemeWrapper(getActivity(), R.style.popUpOptions);
                PopupMenu popup = new PopupMenu(wrapper, options);
                popup.getMenuInflater().inflate(R.menu.profileoptions, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
                {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem)
                    {
                        //Once a menu item is selected, doing relevant tasks to the menu item.
                        switch (menuItem.getItemId())
                        {
                            case R.id.option_edit:
                                /*Setting some elements not relevant to the edit section to be
                                invisible and some relevant section to be visible*/
                                options.setVisibility(View.INVISIBLE);
                                signout.setVisibility(View.INVISIBLE);
                                updatepass.setVisibility(View.GONE);
                                editback.setVisibility(View.VISIBLE);
                                btnUpdate.setVisibility(View.VISIBLE);

                                /*Since the text fields have been disabled at the start of the page,
                                fields should be enabled for the user to edit.*/
                                enableAllFields();

                                //Setting the correct images when a button is selected
                                genderMale.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        genderMale.setBackgroundResource(R.drawable.settingsmaleselected);
                                        genderFemale.setBackgroundResource(R.drawable.settingsfemaleunselected);
                                        genderOther.setBackgroundResource(R.drawable.settingsotherunselected);
                                    }
                                });
                                genderFemale.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        genderMale.setBackgroundResource(R.drawable.settingsmaleunselected);
                                        genderFemale.setBackgroundResource(R.drawable.settingsfemaleselected);
                                        genderOther.setBackgroundResource(R.drawable.settingsotherunselected);
                                    }
                                });
                                genderOther.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        genderMale.setBackgroundResource(R.drawable.settingsmaleunselected);
                                        genderFemale.setBackgroundResource(R.drawable.settingsfemaleunselected);
                                        genderOther.setBackgroundResource(R.drawable.settingsotherselected);
                                    }
                                });

                                /*On click of the update button, validate the inputs from the user
                                and update details in the database.*/
                                btnUpdate.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View view)
                                    {
                                        if (ValidateForm())
                                        {
                                            Toast.makeText(getActivity(), "User details updated",
                                                    Toast.LENGTH_SHORT).show();
                                            setProfileBackFromEdit();
                                            UserDetails = UserFunctions.GetUserDetails(UserID);
                                            DisplayUserDetails(UserDetails);
                                        }
                                    }
                                });

                                /*If the back button is selected then all the fields are disabled,
                                the fields are updated to what is in the database since the fields
                                are the same ones being used as the normal view of the activity*/
                                editback.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                        alertDialogBuilder.setTitle("Confirm exit");
                                        alertDialogBuilder
                                                .setMessage("Are you sure you wish to go back? All changes will be discarded.")
                                                .setCancelable(false)
                                                .setPositiveButton("Yes",new DialogInterface.OnClickListener()
                                                {
                                                    public void onClick(DialogInterface dialog,int id)
                                                    {
                                                        setProfileBackFromEdit();
                                                        UserDetails = UserFunctions.GetUserDetails(UserID);
                                                        DisplayUserDetails(UserDetails);
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
                                });

                                return true;

                                //The following is run if the change password menu item is selected.
                                case R.id.option_changepass:
                                userdetails.setVisibility(View.GONE);
                                options.setVisibility(View.INVISIBLE);

                                //Setting the fields and buttons relevant to change user password.
                                changepassback.setVisibility(View.VISIBLE);
                                updatepass.setVisibility(View.VISIBLE);

                                //If the back button is selected then display the content of the normal profile page.
                                changepassback.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View view) {
                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                        alertDialogBuilder.setTitle("Confirm exit");
                                        alertDialogBuilder
                                                .setMessage("Are you sure you wish to go back? All changes will be discarded.")
                                                .setCancelable(false)
                                                .setPositiveButton("Yes",new DialogInterface.OnClickListener()
                                                {
                                                    public void onClick(DialogInterface dialog,int id)
                                                    {
                                                        setProfileBackFromChangePass();
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
                                });

                                /*Validating the user input to change password once the button is
                                clicked and taking the user back to the main profile*/
                                changePassword.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View view)
                                    {
                                        if (ValidateChangePasswordForm())
                                        {

                                            Toast.makeText(getActivity(),
                                                    "Successfully changed password", Toast.LENGTH_SHORT).show();
                                            setProfileBackFromChangePass();
                                        }

                                    }
                                });
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                //Used to actually show the menu items with the popup.
                popup.show();
            }
        });

        /*If the user chooses to sign out then this takes them to the login page and sets the user
        ID as empty as the user is no longer logged in*/
        signout = (Button)view.findViewById(R.id.btn_signout);
        signout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setTitle("Confirm logout");
                alertDialogBuilder
                        .setMessage("Are you sure that you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,int id)
                            {
                                Global.UserID = "";
                                Intent intent = new Intent(getActivity(), MainActivity.class);
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
        });
        return view;
    }

    //Assigning the fields in the profile with the information found from the database about the user.
    public void DisplayUserDetails(ArrayList<String> UserDetails)
    {
        firstN.setText(UserDetails.get(0));
        lastN.setText(UserDetails.get(1));
        editemail.setText(UserDetails.get(2));
        CurrentEmailAddress = UserDetails.get(2);
        editdob.setText(UserDetails.get(3));
        String Gender = UserDetails.get(4);

        /*Make sure that the buttons have their image and checked status set to what the user
        put in the database*/
        if (Gender.equals("Male"))
        {
            genderMale.setBackgroundResource(R.drawable.settingsmaleselected);
            genderFemale.setBackgroundResource(R.drawable.settingsfemaleunselected);
            genderOther.setBackgroundResource(R.drawable.settingsotherunselected);
            genderMale.setChecked(true);
        }
        else if (Gender.equals("Female"))
        {
            genderMale.setBackgroundResource(R.drawable.settingsmaleunselected);
            genderFemale.setBackgroundResource(R.drawable.settingsfemaleselected);
            genderOther.setBackgroundResource(R.drawable.settingsotherunselected);
            genderFemale.setChecked(true);
        }
        else if (Gender.equals("Other"))
        {
            genderMale.setBackgroundResource(R.drawable.settingsmaleunselected);
            genderFemale.setBackgroundResource(R.drawable.settingsfemaleunselected);
            genderOther.setBackgroundResource(R.drawable.settingsotherselected);
            genderOther.setChecked(true);
        }
    }

    //Function used to set the profile back to normal after coming back from editing the user details.
    public void setProfileBackFromEdit()
    {
        options.setVisibility(View.VISIBLE);
        signout.setVisibility(View.VISIBLE);
        editback.setVisibility(View.INVISIBLE);
        btnUpdate.setVisibility(View.INVISIBLE);
        disableAllFields();
    }

    //Function used to set the profile back to normal after coming back from changing password.
    public void setProfileBackFromChangePass()
    {
        options.setVisibility(View.VISIBLE);
        updatepass.setVisibility(View.GONE);
        changepassback.setVisibility(View.INVISIBLE);
        userdetails.setVisibility(View.VISIBLE);
        disableAllFields();
    }

    //Function that enables all fields and radio buttons for the user to edit.
    public void enableAllFields()
    {
        firstN.setEnabled(true);
        firstN.setTextColor(Color.WHITE);
        firstN.setHint("First Name");

        lastN.setEnabled(true);
        lastN.setTextColor(Color.WHITE);
        lastN.setHint("Last Name");

        editemail.setEnabled(true);
        editemail.setTextColor(Color.WHITE);
        editemail.setHint("Email");

        editdob.setEnabled(true);
        editdob.setTextColor(Color.WHITE);
        editdob.setHint("DD/MM/YYYY");

        gender.setEnabled(true);
        genderFemale.setEnabled(true);
        genderMale.setEnabled(true);
        genderOther.setEnabled(true);
    }

    //Function that disables all fields and radio buttons so that the user cannot edit and only view the details
    public void disableAllFields()
    {
        firstN.setEnabled(false);
        firstN.setTextColor(Color.GRAY);
        firstN.setHint("");

        lastN.setEnabled(false);
        lastN.setTextColor(Color.GRAY);
        lastN.setHint("");

        editemail.setEnabled(false);
        editemail.setTextColor(Color.GRAY);
        editemail.setHint("");

        editdob.setEnabled(false);
        editdob.setTextColor(Color.GRAY);
        editdob.setHint("");

        gender.setEnabled(false);
        genderFemale.setEnabled(false);
        genderMale.setEnabled(false);
        genderOther.setEnabled(false);
    }

    //Validating edited user details and updating it in the database
    private boolean ValidateForm()
    {
        boolean ValidationSuccessful = true;

        String InvalidMessage = "";

        String FName = firstN.getText().toString();
        String LName = lastN.getText().toString();
        String TheEmail = editemail.getText().toString();
        String TheDateOfBirth = editdob.getText().toString();

        //Validation dialogue
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
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
                //Checking if the entered date of birth is in past or not
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

        String TheGender = "";
        if (genderMale.isChecked())
        {
            TheGender = "Male";
        }
        else if(genderFemale.isChecked())
        {
            TheGender = "Female";
        }
        else if(genderOther.isChecked())
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

    //Validating the password input from the user to update their password
    private boolean ValidateChangePasswordForm()
    {
        boolean ValidationSuccessful = true;

        String InvalidMessage = "";

        //Convert the contents of the text boxes to strings
        String OldPass = oldpass.getText().toString();
        String NewPass = newpass.getText().toString();
        String NewPassRepeat = newpassagain.getText().toString();

        //Validation dialogue
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle("Invalid Password");
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog,int id)
                    {
                        //No action to be taken until validation issue is resolved.
                    }
                });

        //Checks if password fields are empty. Display only the first error we find to the user.
        if (OldPass.equals(""))
        {
            ValidationSuccessful = false;
            InvalidMessage = "You need to enter your old password.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        if (NewPass.equals("") && ValidationSuccessful)
        {
            ValidationSuccessful = false;
            InvalidMessage = "You need to enter your new password.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        if (NewPassRepeat.equals("") && ValidationSuccessful)
        {
            ValidationSuccessful = false;
            InvalidMessage = "You need to enter your new password again.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        //Check if both new passwords match.
        if (!NewPass.equals(NewPassRepeat) && ValidationSuccessful)
        {
            ValidationSuccessful = false;
            InvalidMessage = "The new password you entered does not match what is in confirm password field.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        //Checking if the password is strong.
        if (!Common.ValidPassword(NewPass) && ValidationSuccessful)
        {
            ValidationSuccessful = false;
            InvalidMessage = "Password must have at least 8 characters, have at least" +
                    " one upper case, lower case letter, a number and a special character.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        String TestPassword = Common.EncryptPassword(OldPass);

        //Checking id the old password entered here is the same as the one in the database.
        if (!UserFunctions.VerifyPassword(UserID, TestPassword) && ValidationSuccessful)
        {
            ValidationSuccessful = false;
            InvalidMessage = "The old password you have specified does not match your current password.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        if (OldPass.equals(NewPass) && ValidationSuccessful)
        {
            ValidationSuccessful = false;
            InvalidMessage = "The new password is exactly the same as your old password! Please use a different password.";
            alertDialogBuilder.setMessage(InvalidMessage);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

        //If all the validations above are successful, then encrypt the password and update the database.
        if(ValidationSuccessful)
        {
            NewPass = Common.EncryptPassword(NewPass);

            //Update the password. If it fails then fail the validation as well.
            ValidationSuccessful = UserFunctions.UpdateNewPassword(UserID, NewPass);

            //Used to clear the password fields after completing the change.
            oldpass.setText("");
            newpass.setText("");
            newpassagain.setText("");
        }
        return ValidationSuccessful;
    }
}
