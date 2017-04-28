package com.example.gabriella.chatizoproject;

/**
 * Created by Joyce Amore on 4/27/2017.
 */

public class Constants {
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 100000;
    public static final int STATUS_ERROR = 400;
    public static final int STATUS_UNAUTHORIZED = 401;

    // Application key and secret that come from the server to
    // access the API // maybe not necessary
    public static final String APP_KEY = "TBA";
    public static final String APP_SECRET = "TBA";

    // URL's to be used to access the API
    public static final String END_POINT = "OURDOMAIN/api";
    public static final String LOGIN_URL = END_POINT + "/login.php";
    public static final String SIGNUP_URL = END_POINT + "/login.php";

    // Constants used in JSON Parsing or values attached in a URL server connection
    public static final String AUTHORIZATION = "Authorization";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String GRANT_TYPE = "grant_type";
    public static final String CLIENT_CREDENTIALS = "client_credentials";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String ACCESS = "access";
    public static final String INFO = "info";
    public static final String STATUS = "status";
    public static final String MESSAGE = "msg";
    public static final String ID = "id";
    public static final String ID_INFO = "ID";
    public static final String NAME = "name";

    public static final String CONNECTION_MESSAGE = "No Internet Connection!";
}
