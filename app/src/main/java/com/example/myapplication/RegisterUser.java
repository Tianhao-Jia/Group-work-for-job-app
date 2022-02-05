package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegisterUser extends AppCompatActivity {

    private EditText nameFNField;
    private EditText nameLNField;
    private EditText emailField;
    private EditText userTypeField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        Intent intent = getIntent();

        nameFNField = findViewById(R.id.nameFN);
        nameLNField = findViewById(R.id.nameLN);
        emailField = findViewById(R.id.email);
        userTypeField = findViewById(R.id.userType);

        Button registerBtn = findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    public void registerUser(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ((TextView) findViewById(R.id.test)).setText(emailField.toString());

        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean userAlreadyExists = false;

                // checks if user email already exists
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    String databaseEmail = dataSnapshot.child("email").getValue(String.class);

                    if(databaseEmail != null && databaseEmail.equals(emailField.getText().toString())){
                        userAlreadyExists = true;
                    }

                }

                if(userAlreadyExists){
                    displayToast("You are already registered!");
                } else{
                    if(validateInput()) {
                        addRecord();
                        setContentView(R.layout.login_page);
                    }
                    else {
                        displayToast("Registration Failed!");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * addRecord(): reads the existing values inputted to the fields and adds them to the database
     *              as a hashmap record under the child "users"
     */
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
                    displayToast("Registered!");
                    //finish();
                });
    }

    /**
     * clearDatabase(): clears the firebase database
     * Note: main use is for testing
     */
    public void clearDatabase(){
        FirebaseDatabase.getInstance().getReference("users").setValue(null);
    }

    /**
     * displayToast(): displays a long toast using string
     * @param message : String message to display
     */
    private void displayToast(String message) {
        Toast failRegAlert = Toast.makeText(
                getApplicationContext(),
                message,
                Toast.LENGTH_LONG);

        failRegAlert.show();
    }

    /**
     * validateInput(): checks if user's first name, last name and email has been inputted correctly
     * @return boolean: if input is valid
     */
    protected boolean validateInput(){
        if(checkFirstName(nameFNField.getText().toString())
                && checkLastName(nameLNField.getText().toString())
                && checkEmail(emailField.getText().toString())){
            return true;
        }

        return false;
    }

    /**
     * checkFirstName(): first name should start with at least 2 characters
     * @param nameFN : first name to compare using regex
     * @return boolean : true if first name is valid; false otherwise
     */
    protected boolean checkFirstName(String nameFN) {
        Pattern fnPattern = Pattern.compile("^[a-zA-z]{2,}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = fnPattern.matcher(nameFN.trim());

        return matcher.find();
    }

    /**
     * checkLastName(): last name should start with at least 2 characters
     * @param lastName : last name to compare using regex
     * @return boolean : true if last name is valid; false otherwise
     */
    protected boolean checkLastName(String lastName) {
        Pattern lnPattern = Pattern.compile("^[a-z-']{2,}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = lnPattern.matcher(lastName.trim());
        return matcher.find();
    }

    /**
     * checkEmail(): checks if string has an @ symbol
     * @param email : email input to verify
     * @return boolean : true if email is valid; false otherwise
     */
    protected boolean checkEmail(String email) {
        Pattern emailPattern = Pattern.compile(".*@.*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPattern.matcher(email.trim());
        return matcher.find();
    }
}