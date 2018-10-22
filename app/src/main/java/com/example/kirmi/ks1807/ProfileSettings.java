package com.example.kirmi.ks1807;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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


//        TextView txt = (TextView)view.findViewById(R.id.textView4);
//        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter("INTENT_NAME"));

//        UserID = getArguments().getString("UserID");

        final Button options = (Button)view.findViewById(R.id.btn_Options);
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

                                Toast.makeText(getActivity(), "Edit selected", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.option_changepass:
                                Toast.makeText(getActivity(), "Change password selected", Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
            }
        });


        Button signout = (Button)view.findViewById(R.id.btn_signout);
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
