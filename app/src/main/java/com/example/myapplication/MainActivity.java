package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * EmployeeActivity class that manages the EmployeeActivity
 * @author: Nathanael Bowley, Nathan Horn, John Corsten,
 *          Saher Anwar Ziauddin, Ted Graveson, Ding Ding, Jia Jia
 * @course: CSCI3130 @ Dalhousie University.
 * @semester: Winter 2022
 * @group: Group 4
 * @clientTA: Disha Malik
 */
public class MainActivity extends AppCompatActivity {


    private static final String FIREBASE_DATABASE_URL = "https://quick-cash-55715-default-rtdb.firebaseio.com/";
    private FirebaseDatabase firebaseDB;
    private DatabaseReference firebaseDBRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectFirebase();
        writeToFirebaseRealTimeDB();


        Button employeeButton = (Button) findViewById(R.id.debugGoToEmployeeActivity);
        setIntent(employeeButton, EmployeeActivity.class);

        Button employerButton = (Button) findViewById(R.id.debugGoToEmployerActivity);
        setIntent(employerButton, EmployerActivity.class);

        Button register = (Button) findViewById(R.id.register);
        setIntent(register, RegisterUser.class);

    }

    private void connectFirebase() {
        firebaseDB = FirebaseDatabase.getInstance(FIREBASE_DATABASE_URL);
        firebaseDBRef = firebaseDB.getReference("message");
    }

    private void writeToFirebaseRealTimeDB() {
        // Just a test, can delete
        firebaseDBRef.setValue("Hello Group 4");
    }

    /**
     * setIntent method that reduces code clutter and improves switching between intents of buttons.
     * @author Nathanael Bowley
     * @param button Button the button at which when pressed activates an Intent.
     * @param className Class that represents the class linked to that the window will switch to.
     */
    private void setIntent(Button button, Class className) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, className);
                startActivity(intent);
            }
        });
    }
}