package com.example.gabriella.chatizoproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

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

        //set language button
        b1 =(Button)rootview.findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout layout = new LinearLayout(getActivity());
                layout.setOrientation(LinearLayout.VERTICAL);

                final CheckBox checkbox = new CheckBox(getActivity());
                checkbox.setText("English");
                layout.addView(checkbox);
                checkbox.setChecked(true);

                final CheckBox checkbox2 = new CheckBox(getActivity());
                checkbox2.setText("Espanol");
                layout.addView(checkbox2);

                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Select Language:");
                alert.setView(layout).setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                Toast.makeText(getActivity().getApplicationContext(), "Langauge changed to:",Toast.LENGTH_SHORT).show();
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
        b2 =(Button)rootview.findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "Edit Font Size",Toast.LENGTH_SHORT).show();
            }
        });

        b3 =(Button)rootview.findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "Change Theme Colors",Toast.LENGTH_SHORT).show();

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
                                Toast.makeText(getActivity().getApplicationContext(), "Langauge changed to:",Toast.LENGTH_SHORT).show();
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
        b4 =(Button)rootview.findViewById(R.id.button4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity().getApplicationContext(), "Default Security Options",Toast.LENGTH_SHORT).show();

                LinearLayout layout = new LinearLayout(getActivity());
                layout.setOrientation(LinearLayout.VERTICAL);

                final CheckBox checkbox = new CheckBox(getActivity());
                checkbox.setText("Message Timeout Deletion");
                layout.addView(checkbox);
                checkbox.setChecked(true);

                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Change Security Options:");
                alert.setView(layout).setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                Toast.makeText(getActivity().getApplicationContext(), "Security options changed to:",Toast.LENGTH_SHORT).show();
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

        //Logout button
        b5 =(Button)rootview.findViewById(R.id.button5);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Redirecting...",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity().getApplicationContext(), LoginScreen.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return rootview;
    }
}
