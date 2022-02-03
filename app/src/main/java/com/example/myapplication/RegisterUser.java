package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegisterUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        Intent intent = getIntent();

        EditText nameFNField = findViewById(R.id.nameFN);
        EditText nameLNField = findViewById(R.id.nameLN);
        EditText emailField = findViewById(R.id.email);
        EditText userTypeField = findViewById(R.id.userType);

        Button registerBtn = findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = nameFNField.getText().toString();
                String lastName = nameLNField.getText().toString();
                String email = emailField.getText().toString();

                if(checkFirstName(firstName) && checkLastName(lastName) && checkEmail(email)) {
                    addRecord();
                    setContentView(R.layout.login_page);
                }
                else {
                    Toast failRegAlert = Toast.makeText(
                            getApplicationContext(),
                            "Registration Failed!",
                            Toast.LENGTH_LONG);

                    failRegAlert.show();
                }
            }
        });
    }

    // Function reads the existing values inputted to the fields and adds them to the database
    //  as a hashmap record under the child "users"

    protected void addRecord() {

        // Finding all views by ID from the register page
        EditText nameFNField = findViewById(R.id.nameFN);
        EditText nameLNField = findViewById(R.id.nameLN);
        EditText emailField = findViewById(R.id.email);
        EditText userTypeField = findViewById(R.id.userType);

        // Creating a HashMap of user information to store on firebase
        Map<String, Object> map = new HashMap<>();
        map.put("firstName", nameFNField.getText().toString());
        map.put("lastName", nameLNField.getText().toString());
        map.put("email", emailField.getText().toString());
        map.put("userType", userTypeField.getText().toString());

        // Getting an instance of the firebase realtime database
        FirebaseDatabase.getInstance("https://quick-cash-55715-default-rtdb.firebaseio.com/")
                .getReference()
                .child("users")
                .push()
                .setValue(map)
                .addOnSuccessListener(aVoid -> {
                    Toast regToast = Toast.makeText(getApplicationContext(), "Registered!", Toast.LENGTH_LONG);
                    regToast.show();
                    finish();
                });
    }

    protected boolean checkFirstName(String nameFN) {
        Pattern fnPattern = Pattern.compile("^[a-z]{2,}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = fnPattern.matcher(nameFN.trim());

        return matcher.find();
    }

    protected boolean checkLastName(String lastName) {
        Pattern lnPattern = Pattern.compile("^[a-z-']{2,}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = lnPattern.matcher(lastName.trim());
        return matcher.find();
    }

    //Should check database if email exists in this method
    //Also I imagine theres some sort of built in email verification that we can use
    //instead of a crazy regex
    protected boolean checkEmail(String email) {
        Pattern emailPattern = Pattern.compile(".*@.*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPattern.matcher(email.trim());
        return matcher.find();
    }
}