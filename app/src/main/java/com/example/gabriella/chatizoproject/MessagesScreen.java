package com.example.gabriella.chatizoproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.gabriella.chatizoproject.keypattern.NormalActivity;

public class MessagesScreen extends Fragment {
    private Button b1;
    private FloatingActionButton composebutton;

    public static MessagesScreen newInstance() {
        MessagesScreen frag = new MessagesScreen();
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.activity_messages_screen, container, false);


        //set default security button
        b1 = (Button) rootview.findViewById(R.id.send_button);
        b1.setOnClickListener(new View.OnClickListener() {
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
                                if (checkbox1.isChecked()) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Encryption by Pattern", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getActivity().getApplicationContext(), NormalActivity.class));

                                }

                                if (checkbox2.isChecked())
                                    Toast.makeText(getActivity().getApplicationContext(), "Encryption by Key", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();

                    }
                });
                alert.show();
            }
        });

        return rootview;
    }









}
