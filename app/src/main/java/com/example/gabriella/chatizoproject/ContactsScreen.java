package com.example.gabriella.chatizoproject;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;


public class ContactsScreen extends Fragment {
    private FloatingActionButton b1;
    private String m_Text,n_text;
    private ArrayList<Contact> contacts_list;

    public static ContactsScreen newInstance() {
        ContactsScreen frag = new ContactsScreen();
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.activity_contacts_screen, container, false);

        b1 = (FloatingActionButton)rootview.findViewById(R.id.fab);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity().getApplicationContext(), "Add Contact",Toast.LENGTH_SHORT).show();

                LinearLayout layout = new LinearLayout(getActivity());
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText titleBox = new EditText(getActivity());
                titleBox.setHint("Contact ID");
                layout.addView(titleBox);

                final EditText descriptionBox = new EditText(getActivity());
                descriptionBox.setHint("Nickname");
                layout.addView(descriptionBox);

                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Add Contact:");
                alert.setView(layout).setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                if(titleBox.getText().toString().equals("")){
                                    Toast.makeText(getActivity().getApplicationContext(), "Please Enter Contact ID",Toast.LENGTH_SHORT).show();
                                } else {
                                    m_Text = titleBox.getText().toString();
                                    n_text = descriptionBox.getText().toString();
                                    //contacts_list.add(new Contact(m_Text,n_text));
                                }
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
        return rootview;
    }
}
