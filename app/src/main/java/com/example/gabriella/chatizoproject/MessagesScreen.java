package com.example.gabriella.chatizoproject;

import java.io.UnsupportedEncodingException;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.gabriella.chatizoproject.info.InfoOfFriend;
import com.example.gabriella.chatizoproject.info.InfoOfMessage;
import com.example.gabriella.chatizoproject.interfacer.Manager;
import com.example.gabriella.chatizoproject.keypattern.NormalActivity;
import com.example.gabriella.chatizoproject.serve.MessagingService;
import com.example.gabriella.chatizoproject.toolbox.StorageManipulater;


import static android.content.Context.NOTIFICATION_SERVICE;

public class MessagesScreen extends Fragment {
    private Button b1;
    private FloatingActionButton composebutton;

    public static final int MESSAGE_NOT_SENT = 0;

        // change as appropriate
    public TextView messageBox;
    public EditText sendMessage;
    public Button send;

    public Manager serviceProvider;

    public InfoOfFriend friends = new InfoOfFriend();
    public StorageManipulater localDataStorage;

    public Cursor cursor;

    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder service) {
            serviceProvider = ((MessagingService.IMBinder)service).getService();
        }

        public void onServiceDisconnected(ComponentName className) {
            serviceProvider = null;
            //Toast.makeText(MessagesScreen.this, R.string."local_service_stopped", Toast.LENGTH_SHORT).show();
        }
    };

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


        messageBox = (TextView) rootview.findViewById(R.id.message_panel);

        sendMessage = (EditText) rootview.findViewById(R.id.message_input);

        sendMessage.requestFocus();

        Bundle extras = getActivity().getIntent().getExtras();

        try {
            friends.userName = extras.getString(InfoOfFriend.USERNAME);
            friends.ip = extras.getString(InfoOfFriend.IP);
            friends.port = extras.getString(InfoOfFriend.PORT);

        } catch(Exception e) {

        }
        String msg = extras.getString(InfoOfMessage.MESSAGETEXT);

        getActivity().setTitle("Messaging with " + friends.userName);


        localDataStorage = new StorageManipulater(getActivity());
        cursor = localDataStorage.get(friends.userName, MessagingService.USERNAME);

        if(cursor.getCount() > 0) {
            int noOfScorer = 0;
            cursor.moveToFirst();

            while((!cursor.isAfterLast() && noOfScorer < cursor.getCount())) {
                noOfScorer++;

                this.appendToMessageHistory(cursor.getString(2), cursor.getString(3));
                cursor.moveToNext();
            }
        }

        localDataStorage.close();

        if(msg != null) {
            this.appendToMessageHistory(friends.userName, msg);
            //((NotificationManager)getSystemService(NOTIFICATION_SERVICE)).cancel((friends.userName + msg).hashCode());
        }

        send.setOnClickListener(new View.OnClickListener() {

            CharSequence message;
            Handler handler = new Handler();

            @Override
            public void onClick(View v) {

                message = sendMessage.getText();

                if(message.length() > 0) {
                    appendToMessageHistory(serviceProvider.getUsername(), message.toString());

                    localDataStorage.insert(serviceProvider.getUsername(), friends.userName, message.toString());

                    sendMessage.setText("");

                    Thread thread = new Thread() {
                      public void run() {
                          try {
                              if(serviceProvider.sendMessage(serviceProvider.getUsername(), friends.userName, message.toString()) == null) {

                                  handler.post(new Runnable() {

                                      public void run() {
                                          Toast.makeText(getActivity().getApplicationContext(), "Message can't be sent", Toast.LENGTH_SHORT).show();
                                      }
                                  });
                              }
                          } catch(UnsupportedEncodingException e) {
                              Toast.makeText(getActivity().getApplicationContext(), "Message can't be sent", Toast.LENGTH_SHORT).show();

                              e.printStackTrace();
                          }
                      }
                    };

                    thread.start();


                }

            }
        });


        return rootview;
    }


    public class MessageReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            String username = extras.getString(InfoOfMessage.USERID);
            String message = extras.getString(InfoOfMessage.MESSAGETEXT);

            if(username != null && message != null) {
                if(friends.userName.equals(username)) {
                    appendToMessageHistory(username, message);
                    localDataStorage.insert(username, InfoOfFriend.USERNAME, message);
                }
            }
        }
    }

    public MessageReceiver messageReceiver = new MessageReceiver();


    private void appendToMessageHistory(String username, String message) {

        if(username != null && message != null) {
            messageBox.append(username + ": \n");
            messageBox.append(message + ": \n");
        }


    }







}




























