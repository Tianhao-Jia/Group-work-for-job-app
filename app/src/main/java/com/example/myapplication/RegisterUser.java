package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegisterUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        EditText nameFNField = (EditText) findViewById(R.id.nameFN);
        EditText nameLNField = (EditText) findViewById(R.id.nameLN);
        EditText emailField = (EditText) findViewById(R.id.email);
        EditText userTypeField = (EditText) findViewById(R.id.userType);


        Button registerBtn = (Button) findViewById(R.id.addBTN);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = nameFNField.getText().toString();
                String lastName = nameLNField.getText().toString();
                String email = emailField.getText().toString();
                if(checkFirstName(firstName) && checkLastName(lastName) && checkEmail(email)) {
                    setContentView(R.layout.login_page);
                }
                else {
                    Toast failRegAlert = Toast.makeText(getApplicationContext(), "Registration Failed!", Toast.LENGTH_LONG);
                }
            }
        });
    }

    protected boolean checkFirstName(String nameFN) {
        Pattern fnPattern = Pattern.compile("^[a-z]{2,}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = fnPattern.matcher(nameFN.trim());

        return matcher.find();
    }

    protected boolean checkLastName(String lastName) {
        Pattern fnPattern = Pattern.compile("^[a-z-']{2,}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = fnPattern.matcher(lastName.trim());
        return matcher.find();
    }

    //Should check database if email exists in this method
    protected boolean checkEmail(String email) {
        return true;
    }

}