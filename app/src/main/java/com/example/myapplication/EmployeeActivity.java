package com.example.myapplication;

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
 * EmployeeActivity class that manages the EmployeeActivity events.
 * @authors: Nathanael Bowley,
 *          John Corsten,
 *          Nathan Horne,
 *          Ted Graveson,
 *          Hongzheng Ding,
 *          Tianhao Jia,
 *          Saher Anwar Ziauddin
 * @course: CSCI3130 @ Dalhousie University.
 * @semester: Winter 2022
 * @group: Group 4
 * @clientTA: Disha Malik
 */
public class EmployeeActivity extends AppCompatActivity {


    private FirebaseDatabase firebaseDB;
    private DatabaseReference firebaseDBRef;
    private SharedPreferences sharedPreferences;

    TextView loginDisplay;
    Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        connectFirebase();


        loginDisplay = (TextView) findViewById(R.id.employeeLoginDisplay);
        logoutButton = (Button) findViewById(R.id.employeeLogoutButton);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("Key_email", extras.getString("Login Email"));
            editor.putString("Key_password", extras.getString("Login Password"));
            editor.putString("Key_type", extras.getString("User Type"));
            editor.putString("Key_hash", extras.getString("User Hash"));
            editor.apply();

        }
        //this shouldn't be possible so that means that the user is in the wrong spot
        else {
            //DO NOT REMOVE THIS IS FOR US-3 ACCEPTANCE TEST FUNCTIONALITY.
            Intent intent = new Intent(EmployeeActivity.this, RegisterUser.class);
            startActivity(intent);
        }

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutAndChangeLoginState();
            }
        });
    }

    /**
     * logout method removes credentials added to SharedPreferences. Will take user to MainActivity
     * instead of EmployeeActivity on applications start.
     * @author Nathan Horne and Nathanael Bowley (hash functionality)
     */
    private void logoutAndChangeLoginState()
    {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        String userHash = sharedPref.getString("Key_hash", "INVALID HASH");

        if (!userHash.equals("INVALID HASH")) {
            firebaseDBRef.child(userHash).getRef().child("loginState").setValue(false);
        }

        editor.remove("Key_email");
        editor.remove("Key_password");
        editor.remove("Key_type");
        editor.remove("Key_hash");
        editor.apply();


        startActivity( new Intent( EmployeeActivity.this, MainActivity.class));
    }

    /**
     * connectFirebase method that acts to connect the firebase using the firebase url
     * @author: everyone
     */
    private void connectFirebase(){
        firebaseDB = FirebaseDatabase.getInstance(FirebaseUtils.FIREBASE_URL);
        firebaseDBRef = firebaseDB.getReference("users");

    }
}