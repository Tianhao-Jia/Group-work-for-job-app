package com.group04.quickcash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * EmployerActivity class that manages the EmployerActivity
 * @author: Nathan Horne and Nathanael Bowley
 * @course: CSCI3130 @ Dalhousie University.
 * @semester: Winter 2022
 * @group: Group 4
 * @clientTA: Disha Malik
 */
public class EmployerActivity extends AppCompatActivity {

    private static final String FIREBASE_DATABASE_URL = "https://quick-cash-55715-default-rtdb.firebaseio.com/";
    private FirebaseDatabase firebaseDB;
    private DatabaseReference firebaseDBRef;

    TextView loginDisplay;
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer);

        loginDisplay = (TextView) findViewById(R.id.employerLoginDisplay);
        logoutButton = (Button) findViewById(R.id.employerLogoutButton);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("Key_email", extras.getString("Login Email"));
            editor.putString("Key_password", extras.getString("Login Password"));
            editor.putString("Key_type", extras.getString("User Type"));
            editor.putString("Key_hash", extras.getString("User Hash"));
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

        String userHash = sharedPref.getString("Key_hash", "INVALID HASH");

        if (!userHash.equals("INVALID HASH")) {
            connectFirebase();
            firebaseDBRef.child(userHash).getRef().child("loginState").setValue(false);
        }

        editor.remove("Key_email");
        editor.remove("Key_password");
        editor.remove("Key_type");
        editor.apply();


        startActivity( new Intent( EmployerActivity.this, MainActivity.class));
    }

    private void connectFirebase(){
        firebaseDB = FirebaseDatabase.getInstance(FIREBASE_DATABASE_URL);
        firebaseDBRef = firebaseDB.getReference("users");

    }
}
