package com.example.gabriella.chatizoproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

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

public class MessagesScreen extends Fragment {
    private Button b1;
    private FloatingActionButton composebutton;
    private EditText editText;
    private String message = "";
    private InputMethodManager imm;

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

        imm = (InputMethodManager) getActivity().getSystemService(getActivity().getApplicationContext().INPUT_METHOD_SERVICE);
        //set default security button
        b1 = (Button) rootview.findViewById(R.id.send_button);
        editText = (EditText)rootview.findViewById(R.id.editText4);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = editText.getText().toString();
                if(!message.equals("")) {
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
                                        //startActivity(new Intent(getActivity().getApplicationContext(), NormalActivity.class));

                                    }

                                    if (checkbox2.isChecked())
                                        Toast.makeText(getActivity().getApplicationContext(), "Encryption by Key", Toast.LENGTH_SHORT).show();

                                    new MessagingSent().execute("234562", "456614", message);
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
                    Toast.makeText(getActivity().getApplicationContext(), "Please Enter Message", Toast.LENGTH_SHORT).show();
                }
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
}
