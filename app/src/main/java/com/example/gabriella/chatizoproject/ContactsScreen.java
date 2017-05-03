package com.example.gabriella.chatizoproject;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class ContactsScreen extends Fragment {
    private FloatingActionButton addcontactbutton;
    private String id_Text,nname_text;
    private ArrayList<Contact> contacts_list;
    private ListView lv;
    private ArrayAdapter<Contact> listAdapter;
    private Contact cntact;
    private String userid;

    public static ContactsScreen newInstance() {
        ContactsScreen frag = new ContactsScreen();
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        userid = bundle.getString("USERID");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.activity_contacts_screen, container, false);

        //this is the contact list view
        lv = (ListView)rootview.findViewById(R.id.mainListView);
        contacts_list = new ArrayList<Contact>();
        listAdapter = new ArrayAdapter<Contact>(getActivity(), R.layout.simplerow, contacts_list);
        new ContactRequest().execute(userid);

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
                                    new ContactAdded().execute(userid,id_Text,nname_text);
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


    public class ContactAdded extends AsyncTask<String, String, String> {
        /*        private TextView statusField,roleField;
                private Context context;
                private int byGetOrPost = 0;*/
        private HttpURLConnection conn = null;
        private URL url;
        //flag 0 means get and 1 means post.(By default it is get.)
/*        public (Context context,TextView statusField,TextView roleField,int flag) {
            this.context = context;
            this.statusField = statusField;
            this.roleField = roleField;
            byGetOrPost = flag;
        }*/

        protected void onPreExecute() {
            super.onPreExecute();
        }

        //@Override
        protected String doInBackground(String... args) {

            try {

                url = new URL("http://138.197.83.20/inscontact.inc.php");


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("uid", args[0])
                        .appendQueryParameter("cid", args[1]).appendQueryParameter("nname", args[2]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();
                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                }

            }
            catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return "Unsuccessful";
        }

        //@Override
        protected void onPostExecute(String result) {
            Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_SHORT).show();

        }


    }

    public class ContactRequest extends AsyncTask<String, String, String> {
        /*        private TextView statusField,roleField;
                private Context context;
                private int byGetOrPost = 0;*/
        private HttpURLConnection conn = null;
        private URL url;
        //flag 0 means get and 1 means post.(By default it is get.)
/*        public (Context context,TextView statusField,TextView roleField,int flag) {
            this.context = context;
            this.statusField = statusField;
            this.roleField = roleField;
            byGetOrPost = flag;
        }*/

        protected void onPreExecute() {
            super.onPreExecute();
        }

        //@Override
        protected String doInBackground(String... args) {

            try {

                url = new URL("http://138.197.83.20/getcontacts.inc.php");


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("uid", args[0]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();
                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                }

            }
            catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return "Unsuccessful";
        }

        //@Override
        protected void onPostExecute(String result) {
            //Toast.makeText(getActivity().getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            String[] rows = result.split("`");
            for(String s : rows){
                String[] r = s.split("~");
                listAdapter.add(new Contact(r[0], r[1]));
            }
            lv.setAdapter(listAdapter);
        }


    }
}
