package com.example.gabriella.chatizoproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.content.ContentValues;
import org.json.JSONObject;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import com.example.gabriella.chatizoproject.webservices.webServiceTask;
import com.example.gabriella.chatizoproject.webservices.webServiceUtils;
import com.example.gabriella.chatizoproject.data.User;

public class LoginScreen extends AppCompatActivity {
   // private UserLoginRegisterTask mUserLoginRegisterTask = null;
    private Button login, exit, b1,b2;
    private EditText usernameView, passwordView, ed1,ed2;
    private CheckBox rememberMe;
    private boolean saveLogin;
    private SharedPreferences loginPreferences;
    ConnectionClass connectionClass;

    private int counter = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
   //     initViews();

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
/*
    }
    public void initViews() {
        login = (Button) findViewById(R.id.button);
        exit = (Button) findViewById(R.id.button2);
        usernameView = (EditText) findViewById(R.id.editText);
        passwordView = (EditText) findViewById(R.id.editText2);
        rememberMe = (CheckBox) findViewById(R.id.checkBox);
    }

    public void attemptLoginRegister(View view) {
        if (mUserLoginRegisterTask != null) {
            return;
        }

        usernameView.setError(null);
        passwordView.setError(null);

        String username = usernameView.getText().toString();
        String password = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            passwordView.setError(getString(R.string.error_password_length));
            focusView = passwordView;
            cancel = true;
        }

        if (!TextUtils.isEmpty(username)) {
            usernameView.setError(getString(R.string.error_field_required));
            focusView = usernameView;
            cancel = true;
        } else if (!isUsernameValid(username)) {
            usernameView.setError(getString(R.string.error_invalid_email));
            focusView = usernameView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            mUserLoginRegisterTask = new UserLoginRegisterTask(username, password, view.getId() == R.id.button);
            mUserLoginRegisterTask.execute((Void) null);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    private boolean isUsernameValid(String username) {
        return Patterns.EMAIL_ADDRESS.matcher(username).matches();
    }

    private void showProgress(final boolean isShow) {
        // findViewById(R.id.login_progress).setVisibility(isShow ? View.VISIBLE : View.GONE);
        //findViewById(R.id.login_form).setVisibility(isShow ? View.GONE : View.VISIBLE);
    }

    private class UserLoginRegisterTask extends webServiceTask {
        private final ContentValues contentValues = new ContentValues();
        private boolean mIsLogin;

        UserLoginRegisterTask(String username, String password, boolean isLogin) {
            super(LoginScreen.this);
            contentValues.put(Constants.USERNAME, username);
            contentValues.put(Constants.PASSWORD, password);
            contentValues.put(Constants.GRANT_TYPE, Constants.CLIENT_CREDENTIALS);
            mIsLogin = isLogin;
        }

        @Override
        public void showProgress() {
            LoginScreen.this.showProgress(true);
        }

        @Override
        public void hideProgress() {
            LoginScreen.this.showProgress(false);
        }

        @Override
        public boolean performRequest() {
            JSONObject obj = webServiceUtils.requestJSONObject(mIsLogin ? Constants.LOGIN_URL : Constants.SIGNUP_URL,
                    webServiceUtils.METHOD.POST, contentValues, true);
            mUserLoginRegisterTask = null;
            if (!hasError(obj)) {
                if (mIsLogin) {
                    User user = new User();
                    user.setId(obj.optLong(Constants.ID));
                    user.setUsername(contentValues.getAsString(Constants.USERNAME));
                    user.setPassword(contentValues.getAsString(Constants.PASSWORD));
                    ChatizoApplication.getInstance().setUser(user);
                    ChatizoApplication.getInstance().setAccessToken(
                            obj.optJSONObject(Constants.ACCESS).optString(Constants.ACCESS_TOKEN));
                    return true;
                } else {
                    mIsLogin = true;
                    performRequest();
                    return true;
                }
            }
            return false;
        }

        @Override
        public void performSuccessfulOperation() {
            Intent intent = new Intent(LoginScreen.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }

    }
     */
}

