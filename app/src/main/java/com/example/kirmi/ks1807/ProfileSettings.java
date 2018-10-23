package com.example.kirmi.ks1807;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileSettings extends Fragment {

    String UserID = "";
    private static final String TAG = "ProfileSettingsTabFrag";
    RadioGroup gender;
    RadioButton genderMale, genderFemale, genderOther;
    EditText firstN, lastN, editemail, editdob;
    private Button options, editback, changepassback, btnUpdate, signout, changePassword;
    private LinearLayout userdetails, updatepass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile_settingstab, container, false);

        gender = (RadioGroup)view.findViewById(R.id.RadioGroup_EditGenderSelect);
        genderMale = (RadioButton)view.findViewById(R.id.RadioButton_EditMale);
        genderFemale = (RadioButton)view.findViewById(R.id.RadioButton_EditFemale);
        genderOther = (RadioButton)view.findViewById(R.id.RadioButton_EditOther);

        gender.setEnabled(false);

        firstN = (EditText)view.findViewById(R.id.editText_EditFirstName);
        lastN = (EditText)view.findViewById(R.id.editText_EditLastName);
        editemail = (EditText)view.findViewById(R.id.editText_EditEmail);
        editdob = (EditText)view.findViewById(R.id.editText_EditDateOfBirth);

        userdetails = (LinearLayout)view.findViewById(R.id.user_details);
        updatepass = (LinearLayout)view.findViewById(R.id.changepasscontent);

//        TextView txt = (TextView)view.findViewById(R.id.textView4);
//        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("INTENT_NAME"));

//        UserID = getArguments().getString("UserID");
        editback = (Button)view.findViewById(R.id.btn_profileeditsettingback);
        changepassback = (Button)view.findViewById(R.id.btn_profilechangepasssettingback);
        btnUpdate = (Button)view.findViewById(R.id.btn_updateprofile);
        changePassword = (Button) view.findViewById(R.id.btnchangepass);


        options = (Button)view.findViewById(R.id.btn_Options);
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context wrapper = new ContextThemeWrapper(getActivity(), R.style.popUpOptions);
                PopupMenu popup = new PopupMenu(wrapper, options);
                popup.getMenuInflater().inflate(R.menu.profileoptions, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.option_edit:
                                options.setVisibility(View.INVISIBLE);
                                signout.setVisibility(View.INVISIBLE);
                                updatepass.setVisibility(View.GONE);

                                editback.setVisibility(View.VISIBLE);
                                btnUpdate.setVisibility(View.VISIBLE);

                                firstN.setEnabled(true);
                                firstN.setTextColor(Color.WHITE);
                                lastN.setEnabled(true);
                                lastN.setTextColor(Color.WHITE);
                                editemail.setEnabled(true);
                                editemail.setTextColor(Color.WHITE);
                                editdob.setEnabled(true);
                                editdob.setTextColor(Color.WHITE);

                                gender.setEnabled(true);
                                genderFemale.setEnabled(true);
                                genderMale.setEnabled(true);
                                genderOther.setEnabled(true);

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

                                btnUpdate.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //Need to get the userID first to be able to update
                                        Toast.makeText(getActivity(), "User details updated", Toast.LENGTH_SHORT).show();
                                    }
                                });

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
                                                        options.setVisibility(View.VISIBLE);
                                                        signout.setVisibility(View.VISIBLE);

                                                        editback.setVisibility(View.INVISIBLE);
                                                        btnUpdate.setVisibility(View.INVISIBLE);
                                                        firstN.setEnabled(false);
                                                        firstN.setTextColor(Color.GRAY);

                                                        lastN.setEnabled(false);
                                                        lastN.setTextColor(Color.GRAY);
                                                        editemail.setEnabled(false);
                                                        editemail.setTextColor(Color.GRAY);
                                                        editdob.setEnabled(false);
                                                        editdob.setTextColor(Color.GRAY);

                                                        gender.setEnabled(false);
                                                        genderFemale.setEnabled(false);
                                                        genderMale.setEnabled(false);
                                                        genderOther.setEnabled(false);
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

                                Toast.makeText(getActivity(), "Edit selected", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.option_changepass:
                                userdetails.setVisibility(View.GONE);
                                options.setVisibility(View.INVISIBLE);

                                changepassback.setVisibility(View.VISIBLE);
                                updatepass.setVisibility(View.VISIBLE);

                                changepassback.setOnClickListener(new View.OnClickListener() {
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
                                                        options.setVisibility(View.VISIBLE);

                                                        changepassback.setVisibility(View.INVISIBLE);
                                                        userdetails.setVisibility(View.VISIBLE);
                                                        firstN.setEnabled(false);
                                                        firstN.setTextColor(Color.GRAY);
                                                        lastN.setEnabled(false);
                                                        lastN.setTextColor(Color.GRAY);
                                                        editemail.setEnabled(false);
                                                        editemail.setTextColor(Color.GRAY);
                                                        editdob.setEnabled(false);
                                                        editdob.setTextColor(Color.GRAY);
                                                        gender.setEnabled(false);
                                                        genderFemale.setEnabled(false);
                                                        genderMale.setEnabled(false);
                                                        genderOther.setEnabled(false);
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
                                changePassword.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //Should change password in database need to do after getting USERID
                                        Toast.makeText(getActivity(), "Password Changes", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
            }
        });


        signout = (Button)view.findViewById(R.id.btn_signout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setTitle("Confirm logout");
                alertDialogBuilder
                        .setMessage("Are you sure that you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog,int id)
                            {
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
//        txt.setText(UserID);
        return view;
    }


//    private BroadcastReceiver mReceiver = new B oadcastReceiver()() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String receivedHexColor = intent.getStringExtra(BG_SELECT);
//        }
//    };


}
