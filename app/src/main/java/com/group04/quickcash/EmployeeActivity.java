package com.group04.quickcash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

/**
 * EmployeeActivity class that manages the EmployeeActivity
 * @author: Nathan Horne and Nathanael Bowley
 * @course: CSCI3130 @ Dalhousie University.
 * @semester: Winter 2022
 * @group: Group 4
 * @clientTA: Disha Malik
 */
public class EmployeeActivity extends AppCompatActivity {

    TextView loginDisplay;
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        loginDisplay = (TextView) findViewById(R.id.employeeLoginDisplay);
        logoutButton = (Button) findViewById(R.id.employeeLogoutButton);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("Key_email", extras.getString("Login Email"));
            editor.putString("Key_password", extras.getString("Login Password"));
            editor.putString("Key_type", extras.getString("User Type"));
            editor.apply();

            //loginDisplay.setText("Welcome, " + extras.getString("Login Email"));
        }

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    /**
     * logout method removes credentials added to SharedPreferences. Will take user to MainActivity
     * instead of EmployeeActivity on applications start.
     * @author Nathan Horne
     */
    private void logout()
    {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("Key_email");
        editor.remove("Key_password");
        editor.remove("Key_type");
        editor.apply();
        startActivity( new Intent( EmployeeActivity.this, MainActivity.class));
    }
}