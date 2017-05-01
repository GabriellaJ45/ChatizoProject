package com.example.gabriella.chatizoproject;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ContactsScreen extends Fragment {
    private FloatingActionButton addcontactbutton;
    private String id_Text,nname_text;
    private ArrayList<Contact> contacts_list;
    private ListView lv;
    private ArrayAdapter<Contact> listAdapter;
    private Contact cntact;

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

        //this is the contact list view
        lv = (ListView)rootview.findViewById(R.id.mainListView);
        contacts_list = new ArrayList<Contact>();
        listAdapter = new ArrayAdapter<Contact>(getActivity(), R.layout.simplerow, contacts_list);

        //the pink button to add contacts
        addcontactbutton = (FloatingActionButton)rootview.findViewById(R.id.fab);
        addcontactbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity().getApplicationContext(), "Add Contact",Toast.LENGTH_SHORT).show();

                //Set alert pop up to add contacts
                LinearLayout layout = new LinearLayout(getActivity());
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText titleBox = new EditText(getActivity());
                titleBox.setHint("Contact ID");
                titleBox.setFilters(new InputFilter[] {new InputFilter.LengthFilter(15)});
                layout.addView(titleBox);

                final EditText descriptionBox = new EditText(getActivity());
                descriptionBox.setHint("Nickname");
                descriptionBox.setFilters(new InputFilter[] {new InputFilter.LengthFilter(15)});
                layout.addView(descriptionBox);

                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Add Contact:");
                alert.setView(layout).setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                //This is where we would check if the ID entered is a correct ID
                                if(titleBox.getText().toString().equals("")){
                                    Toast.makeText(getActivity().getApplicationContext(), "Please Enter Contact ID",Toast.LENGTH_SHORT).show();
                                } else {
                                    id_Text = titleBox.getText().toString();
                                    nname_text = descriptionBox.getText().toString();
                                    cntact = new Contact(id_Text,nname_text);
                                    //contacts_list.add(cntact);
                                    listAdapter.add(cntact);
                                }
                            }
                        }).setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                //cancel button is pressed on add contact pop up
                                dialog.cancel();

                            }
                        });
                alert.show();

            }
        });


        lv.setAdapter(listAdapter);

        //Set listener for when user clicks on contact from list
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = contacts_list.get(position).getNickname1();
                String idnum = contacts_list.get(position).getId1();
                Toast.makeText(getActivity().getApplicationContext(), "Name: " + name + " -- ID: " + idnum, Toast.LENGTH_LONG).show();
            }
        });
        return rootview;
    }
}
