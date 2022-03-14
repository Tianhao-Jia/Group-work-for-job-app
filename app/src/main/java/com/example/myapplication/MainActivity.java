package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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

public class MainActivity extends AppCompatActivity {


    private FirebaseDatabase firebaseDB;
    private DatabaseReference firebaseDBRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Session.startSession(getApplicationContext());

        firebaseDB = FirebaseUtils.connectFirebase();
        firebaseDBRef = firebaseDB.getReference();

        Button loginActivity = (Button) findViewById(R.id.mainLogin);
        setIntent(loginActivity, LoginActivity.class);


        Button register = (Button) findViewById(R.id.register);
        setIntent(register, GoogleMapsActivity.class);
        redirectIfLoggedIn();
        setIntent(register, RegisterUser.class);
        registerNewJobListener();
    }
    private boolean isNewJob = false;
    public List<String> list = new ArrayList<>();
    private void registerNewJobListener() {
        createNotificationChannel();
        DatabaseReference jobRef = FirebaseDatabase.getInstance(FirebaseUtils.FIREBASE_URL).getReference("jobs");
        jobRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!isNewJob){
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        list.add(dataSnapshot.getKey());
                    }
                }else{
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        if (!list.contains(dataSnapshot.getKey())){
                            //
                            Job job = dataSnapshot.getValue(Job.class);
                            showNotification(job);
                        }

                    }
                }
                isNewJob = true;


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "job";
            String description = "new Job";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("New Job", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void showNotification(Job job) {


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "New Job")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(job.getJob_title())
                .setContentText(job.getDescription())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, builder.build());
    }
    /**
     * connectFirebase method that acts to connect the firebase using the firebase url
     * @author: everyone
     */
    private void connectFirebase() {
        firebaseDB = FirebaseDatabase.getInstance(FirebaseUtils.FIREBASE_URL);
        firebaseDBRef = firebaseDB.getReference("message");
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

    private void redirectIfLoggedIn() {
        if(Session.checkLogin()) {
            if(Session.isEmployee()) {
                Intent i = new Intent(MainActivity.this, EmployeeActivity.class);
                startActivity(i);
            }
            else if(Session.isEmployer()) {
                Intent i = new Intent(MainActivity.this, EmployerActivity.class);
                startActivity(i);
            }
        }
    }

    /**
     * checkForLogin method that checks the SharedPreferences for previously stored login credentials.
     * If credentials are found and are valid, take user to corresponding activity (employer or employee).
     * @author Nathan Horne
     */
    private void checkForLogin() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
        String emailPref = sharedPref.getString("Key_email", "INVALID EMAIL");
        String passwordPref = sharedPref.getString("Key_password", "INVALID PASSWORD");
        String typePref = sharedPref.getString("Key_type", "INVALID TYPE");

        //this wont actually check if the user is in loginState == true but will just use SharedPreferences
        //to redirect them, login state should still be true however so long as they didn't log out
        //but this may log them in when they've logged out previously.
        if (!emailPref.equals("INVALID EMAIL") && !passwordPref.equals("INVALID PASSWORD") && typePref.equals(Employee.EMPLOYEE)) {
            Intent intent = new Intent(MainActivity.this, EmployeeActivity.class);
            intent.putExtra("Login Email", emailPref);
            intent.putExtra("Login Password", passwordPref);
            intent.putExtra("Login Type", typePref);
            startActivity(intent);
        }
        else if (!emailPref.equals("INVALID EMAIL") && !passwordPref.equals("INVALID PASSWORD") && typePref.equals(Employer.EMPLOYER)) {
            Intent intent = new Intent(MainActivity.this, EmployerActivity.class);
            intent.putExtra("Login Email", emailPref);
            intent.putExtra("Login Password", passwordPref);
            intent.putExtra("Login Type", typePref);
            startActivity(intent);
        }
    }
}