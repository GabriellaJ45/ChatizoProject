package com.example.gabriella.chatizoproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;


public class LoginScreen extends AppCompatActivity {
    private Button b1,b2;
    private EditText ed1,ed2;
    private CheckBox rememberMe;
    private boolean saveLogin;
    private SharedPreferences loginPreferences;
    ConnectionClass connectionClass;

    private int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        connectionClass = new ConnectionClass();
        b1 = (Button)findViewById(R.id.button);
        ed1 = (EditText)findViewById(R.id.editText);
        ed2 = (EditText)findViewById(R.id.editText2);
        rememberMe = (CheckBox)findViewById(R.id.checkBox);
/*
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            ed1.setText(loginPreferences.getString("username", ""));
            ed2.setText(loginPreferences.getString("password", ""));
            rememberMe.setChecked(true);
        }
*/
        b2 = (Button)findViewById(R.id.button2);

        try {
            Connection con = connectionClass.CONN();
            if (con == null) {
                Toast.makeText(getApplicationContext(), "Connection failed",Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(getApplicationContext(), "Connection Passed!!",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Connection failed 2",Toast.LENGTH_SHORT).show();
        }




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed1.getText().toString().equals("Chatizo") &&
                        ed2.getText().toString().equals("chatizo")) {
                    Toast.makeText(getApplicationContext(), "Redirecting...",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginScreen.this, AfterLoginController.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Invalid Username or Password",Toast.LENGTH_SHORT).show();
                    counter--;
                    if (counter == 0) {
                        b1.setEnabled(false);
                    }
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}