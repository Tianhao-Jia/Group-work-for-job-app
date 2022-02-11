package com.group04.quickcash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

/**
 * EmployerActivity class that manages the EmployerActivity
 * @author: Nathan Horne and Nathanael Bowley
 * @course: CSCI3130 @ Dalhousie University.
 * @semester: Winter 2022
 * @group: Group 4
 * @clientTA: Disha Malik
 */
public class EmployerActivity extends AppCompatActivity {

    TextView loginDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer);

        loginDisplay = (TextView) findViewById(R.id.employerLoginDisplay);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("Key_email", extras.getString("Login Email"));
            editor.putString("Key_password", extras.getString("Login Password"));
            editor.putString("Key_type", extras.getString("User Type"));
            editor.apply();

            loginDisplay.setText("Welcome, " + extras.getString("Login Email"));
        }
    }
}
