package com.example.gabriella.chatizoproject;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Created by Gabriella on 4/18/2017.
 */

public class ConnectionClass {
    private String ip = "138.197.83.20";
    private String classs = "com.mysql.jdbc.Driver";
    private String db = "chatizo";
    private String un = "root";
    private String password = "chatizo2017";

    //@SuppressLint("NewApi")
    public Connection CONN(){
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        //String ConnURL = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            //ConnURL = "jdbc:mysql://" + ip + ";" + "databaseName=" + db + ";user=" + un + ";password" + password + ";";
            conn = DriverManager.getConnection("jdbc:mysql://138.197.83.20:22/chatizo","root","chatizo2017");
        } catch (SQLException se){
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e){
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }
        return conn;
    }

    public boolean verifyUser(String username, String password){
        return true;
    }


}
