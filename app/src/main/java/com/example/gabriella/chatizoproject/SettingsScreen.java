package com.example.gabriella.chatizoproject;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.gabriella.chatizoproject.keypattern.NormalActivity;


/**
 * Created by yungbena on 4/23/17.
 */

public class SettingsScreen extends Fragment {
    private Button b1,b2,b3,b4,b5;
    public static SettingsScreen newInstance() {
        SettingsScreen frag = new SettingsScreen();
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.activity_settings_screen, container, false);

        //Set Language button
        b1 =(Button)rootview.findViewById(R.id.selectLanguage);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout layout = new LinearLayout(getActivity());
                layout.setOrientation(LinearLayout.VERTICAL);

                final CheckBox checkBox = new CheckBox(getActivity());
                checkBox.setText("English");
                layout.addView(checkBox);
                checkBox.setChecked(true);

                final CheckBox checkBox1 = new CheckBox(getActivity());
                checkBox1.setText("Espanol");
                layout.addView(checkBox1);


                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Select Language:");
                alert.setView(layout).setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Toast.makeText(getActivity().getApplicationContext(), "Langauge changed to:",Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        });
                alert.show();
            }
        });

        //edit font size
        b2 =(Button)rootview.findViewById(R.id.editFont);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout layout = new LinearLayout(getActivity());
                layout.setOrientation(LinearLayout.VERTICAL);

                final CheckBox checkbox = new CheckBox(getActivity());
                checkbox.setText("Small");
                layout.addView(checkbox);

                final CheckBox checkbox2 = new CheckBox(getActivity());
                checkbox2.setText("Medium");
                layout.addView(checkbox2);
                checkbox2.setChecked(true);

                final CheckBox checkbox1 = new CheckBox(getActivity());
                checkbox1.setText("Large");
                layout.addView(checkbox1);


                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Change Theme Colors:");
                alert.setView(layout).setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                //Toast.makeText(getActivity().getApplicationContext(), "Langauge changed to:",Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                dialog.cancel();

                            }
                        });
                alert.show();
            }
        });

        //change theme button
        b3 =(Button)rootview.findViewById(R.id.changeTheme);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout layout = new LinearLayout(getActivity());
                layout.setOrientation(LinearLayout.VERTICAL);

                final CheckBox checkbox = new CheckBox(getActivity());
                checkbox.setText("Blue");
                layout.addView(checkbox);
                checkbox.setChecked(true);

                final CheckBox checkbox2 = new CheckBox(getActivity());
                checkbox2.setText("Black");
                layout.addView(checkbox2);

                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Change Theme Colors:");
                alert.setView(layout).setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                //Toast.makeText(getActivity().getApplicationContext(), "Langauge changed to:",Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                dialog.cancel();

                            }
                        });
                alert.show();
            }
        });






        //set default security button
        b4 =(Button)rootview.findViewById(R.id.defaultSecurity);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinearLayout layout = new LinearLayout(getActivity());
                layout.setOrientation(LinearLayout.VERTICAL);

                final CheckBox checkbox = new CheckBox(getActivity());
                checkbox.setText("Message Timeout Deletion");
                layout.addView(checkbox);
                checkbox.setChecked(true);

                final CheckBox checkbox1 = new CheckBox(getActivity());
                checkbox1.setText("Encryption by Pattern");
                layout.addView(checkbox1);

                final CheckBox checkbox2 = new CheckBox(getActivity());
                checkbox2.setText("Encryption by Key");
                layout.addView(checkbox2);

                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Change Security Options:");
                alert.setView(layout).setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                /*
                                if(checkbox.isChecked()) {
                                    Intent intent = new Intent(getActivity().getApplicationContext(), MessageTimeout.class);
                                    startActivity(intent);
                                    if(checkbox1.isChecked()){
                                        Intent intent1 = new Intent(getActivity().getApplicationContext(), EncryptionPattern.class);
                                        startActivity(intent1);
                                        if(checkbox2.isChecked()){
                                            Intent intent2 = new Intent(getActivity().getApplicationContext(), EncryptionKey.class);
                                            startActivity(intent2);
                                        }else {
                                            return;
                                        }
                                    }
                                }*/
                                if(checkbox1.isChecked()) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Encryption by Pattern", Toast.LENGTH_SHORT).show();
                                    //startActivity(new Intent(getActivity().getApplicationContext(), NormalActivity.class));

                                }

                                if(checkbox2.isChecked())
                                    Toast.makeText(getActivity().getApplicationContext(), "Encryption by Key",Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();

                            }
                        });
                alert.show();
            }
        });

        //Logout button
        b5 =(Button)rootview.findViewById(R.id.logout);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Logging Out...",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity().getApplicationContext(), LoginScreen.class); //LoginScreen.class
                startActivity(intent);
                getActivity().finish();
            }
        });

        return rootview;
    }



}