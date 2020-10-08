package com.example.mylogin;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class AppPrefs {

    private static SharedPreferences sharedPreferences,loginPreferences;
    private static final String PREF_FILE = "userfile";
    private static final String LOGIN_FILE = "login";
    private static final String NAME = "name";
    private static final String USER_NAME = "userName";
    private static final String PASSWORD = "pass";
    private static final String EMAIL = "userName";
    private static final String PASS = "pass";


    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        loginPreferences = context.getSharedPreferences(LOGIN_FILE, Context.MODE_PRIVATE);
    }

    public static String getEMAIL() {
        return loginPreferences.getString(EMAIL, "");
    }

    public static String getPASS() {
        return loginPreferences.getString(PASS, "");
    }

    public static String getNAME() {
        return sharedPreferences.getString(NAME, "");
    }

    public static String getUserName() {
        return sharedPreferences.getString(USER_NAME, "");
    }

    public static String getPassword() {
        return sharedPreferences.getString(PASSWORD, "");
    }

    public static void setName(String  name) {

        sharedPreferences.edit().putString(NAME, name).apply();
    }

    public static void setUserName(String  userName) {
        sharedPreferences.edit().putString(USER_NAME, userName).apply();
    }

    public static void setPassword(String pass) {
        sharedPreferences.edit().putString(PASSWORD, pass).apply();
    }

    public static void setEmail(String email) {
        loginPreferences.edit().putString(USER_NAME, email).apply();
    }

    public static void setPASS(String pass) {
        loginPreferences.edit().putString(PASSWORD, pass).apply();
    }

}
