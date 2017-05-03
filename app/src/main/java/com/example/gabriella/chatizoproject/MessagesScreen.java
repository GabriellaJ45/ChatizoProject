package com.example.gabriella.chatizoproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;

import com.example.gabriella.chatizoproject.keypattern.NormalActivity;

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
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MessagesScreen extends Fragment {
    private Button b1, back, unread;
    private FloatingActionButton composebutton;
    private EditText editText, contact;
    private String message = "";
    private InputMethodManager imm;
    private String userid = "";
    private String nname = "";
    private String toid;
    private ListView lv;
    private ArrayAdapter<String> listAdapter;
    private List<String> messages;

    public static MessagesScreen newInstance() {
        MessagesScreen frag = new MessagesScreen();
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        nname = bundle.getString("NNAME");
        userid = bundle.getString("USERID");
        toid = bundle.getString("TOID");

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.activity_messages_screen, container, false);
        messages = new ArrayList<String>();
        new MessagingRequest().execute(userid);
        lv = (ListView)rootview.findViewById(R.id.mainListView1);
        listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simplerow, messages);

        imm = (InputMethodManager) getActivity().getSystemService(getActivity().getApplicationContext().INPUT_METHOD_SERVICE);
        //set default security button
        b1 = (Button) rootview.findViewById(R.id.send_button);
        unread = (Button) rootview.findViewById(R.id.unread_button);
        back = (Button) rootview.findViewById(R.id.back_button);
        editText = (EditText)rootview.findViewById(R.id.editText4);
        contact = (EditText) rootview.findViewById(R.id.editText3);
        composebutton = (FloatingActionButton)rootview.findViewById(R.id.fab);

        lv.setVisibility(ListView.INVISIBLE);
        editText.setVisibility(EditText.VISIBLE);
        b1.setVisibility(Button.VISIBLE);
        unread.setVisibility(Button.VISIBLE);
        back.setVisibility(Button.INVISIBLE);
        composebutton.setVisibility(FloatingActionButton.VISIBLE);
        contact.setVisibility(EditText.VISIBLE);

        if(!nname.equals("")) {
            String str = nname;
            //final EditText editText = (EditText) rootview.findViewById(R.id.editText3);
            contact.setText(str ,TextView.BufferType.EDITABLE);
        }




        composebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("USERID", userid);
                Fragment selectedFragment = null;
                selectedFragment = ContactsScreen.newInstance();
                selectedFragment.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, selectedFragment);
                transaction.commit();
            }
    });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = editText.getText().toString();
                if(!message.equals("") && !toid.equals("")) {
                    LinearLayout layout = new LinearLayout(getActivity());
                    layout.setOrientation(LinearLayout.VERTICAL);

                    final CheckBox checkbox = new CheckBox(getActivity());
                    checkbox.setText("Message Timeout Deletion");
                    layout.addView(checkbox);
                    checkbox.setChecked(true);

                    /*final CheckBox checkbox1 = new CheckBox(getActivity());
                    checkbox1.setText("Encryption by Pattern");
                    layout.addView(checkbox1);

                    final CheckBox checkbox2 = new CheckBox(getActivity());
                    checkbox2.setText("Encryption by Key");
                    layout.addView(checkbox2);*/

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
                                   /* if (checkbox1.isChecked()) {
                                        Toast.makeText(getActivity().getApplicationContext(), "Encryption by Pattern", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getActivity().getApplicationContext(), NormalActivity.class));

                                    }

                                    if (checkbox2.isChecked())
                                        Toast.makeText(getActivity().getApplicationContext(), "Encryption by Key", Toast.LENGTH_SHORT).show();*/

                                    new MessagingSent().execute(userid, toid, message);
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.cancel();

                        }
                    });
                    alert.show();
                    editText.setText("");
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                } else{
                    Toast.makeText(getActivity().getApplicationContext(), "Please Enter Message or Contact", Toast.LENGTH_SHORT).show();
                }
            }
        });

        unread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lv.setVisibility(ListView.VISIBLE);
                editText.setVisibility(EditText.INVISIBLE);
                b1.setVisibility(Button.INVISIBLE);
                unread.setVisibility(Button.INVISIBLE);
                back.setVisibility(Button.VISIBLE);
                composebutton.setVisibility(FloatingActionButton.INVISIBLE);
                contact.setVisibility(EditText.INVISIBLE);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lv.setVisibility(ListView.INVISIBLE);
                editText.setVisibility(EditText.VISIBLE);
                b1.setVisibility(Button.VISIBLE);
                unread.setVisibility(Button.VISIBLE);
                back.setVisibility(Button.INVISIBLE);
                composebutton.setVisibility(FloatingActionButton.VISIBLE);
                contact.setVisibility(EditText.VISIBLE);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String name = contacts_list.get(position).getNickname1();
                //String idnum = contacts_list.get(position).getId1();
                String msg = messages.get(position);
                Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });

        return rootview;
    }


    public class MessagingSent extends AsyncTask<String, String, String> {
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

                url = new URL("http://138.197.83.20/insmessage.inc.php");


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("fuid", args[0])
                        .appendQueryParameter("ruid", args[1]).appendQueryParameter("mtext", args[2]);
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


    public class MessagingRequest extends AsyncTask<String, String, String> {
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

                url = new URL("http://138.197.83.20/reqmessage.inc.php");


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("ruid", args[0]);
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
            if (!result.equals("")) {
                String[] column = result.split(">");

                for (String entry : column) {
                    String[] c2 = entry.split("&");
                    String content = c2[0];
                    String messageID = c2[1];
                    listAdapter.add(content + "-" + messageID);
                }


                lv.setAdapter(listAdapter);

                //Set listener for when user clicks on contact from list
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        final String messageID = String.valueOf(position);

                        String[] parts = (listAdapter.getItem(position)).split("-");
                        String content = parts[0];
                        final String messagesID = parts[1];
                        String[] send1 = content.split(":");
                        String sender = send1[0];
                        String body = send1[1];


                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Message from " + send1[0])
                                .setCancelable(false)
                                .setMessage(body)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        new deleteMessage().execute(userid, "chatizo", messagesID);
                                        listAdapter.remove(messageID);
                                        lv.setAdapter(listAdapter);
                                    }
                                });
                        final AlertDialog alert = builder.create();
                        alert.show();
                        final Timer timer2 = new Timer();
                        timer2.schedule(new TimerTask() {
                            public void run() {
                                new deleteMessage().execute(userid, "chatizo", messagesID);
                                alert.dismiss();
                                timer2.cancel(); //this will cancel the timer of the system
                            }
                        }, 20000);
                    }
                });

            }
        }

    }

    public class deleteMessage extends AsyncTask<String, String, String>  {

        private HttpURLConnection conn = null;
        private URL url;



        protected String doInBackground(String... args) {

            try {
                    // grab delete message php script
                url = new URL("http://138.197.83.20/delmessage.inc.php");

            } catch(MalformedURLException e) {
                e.printStackTrace();
            }

            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("messageID", args[2]);
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


            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                if(conn != null) {
                    conn.disconnect();
                }
            }

            return "unsuccessful";

        }

    }   // end deleteMessages class

}
