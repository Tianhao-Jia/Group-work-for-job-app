package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.content.SharedPreferences;

import java.util.HashMap;

public class Session  {

    private static Context context;
    private static SharedPreferences sharedPref;
    private static SharedPreferences.Editor editor;

    public static final String SHARED_PREFS = "shared_prefs";

    // key for storing email.
    public static final String EMAIL = "email";

    public static final String F_NAME = "first_name";

    public static final String L_NAME = "last_name";

    public static final String LOGIN = "logged_in";

    public static final String TYPE = "user_type";

    public static final String ID = "user_id";

    public static void startSession(Context appContext) {
         context = appContext;
         sharedPref = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
         editor = sharedPref.edit();
    }


    public static boolean login(String email, String userID, String userType) {
        editor.putString(EMAIL, email);
        editor.putString(ID, userID);
        editor.putString(TYPE, userType);
        editor.putBoolean(LOGIN, true);

        return editor.commit();
    }

    public static void logout() {
        editor.clear();
        editor.commit();

        redirectToLogin();
    }

    public static boolean checkLogin() {
        return sharedPref.getBoolean(LOGIN, false);

    }

    public static void redirectIfNotLoggedIn() {
        if(!checkLogin()) {
            redirectToLogin();
        }
    }

    public static String getKey(String key) {
        return sharedPref.getString(key, "Value not found");
    }

    public static String getEmail() {
        return sharedPref.getString(EMAIL, "No Email");
    }

    public static String getFName() {
        return sharedPref.getString(F_NAME, "No First Name");
    }

    public static String getLName() {
        return sharedPref.getString(L_NAME, "No Last Name");
    }

    public static String getUserType() {
        return sharedPref.getString(TYPE, "No User Type");
    }

    public static String getUserID() {
        return sharedPref.getString(ID, "No user ID found");
    }

    public static void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    private static void redirectToLogin() {
        Intent redirect = new Intent(context, LoginActivity.class);
        redirect.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(redirect);
    }

}
