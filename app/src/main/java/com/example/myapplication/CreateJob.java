package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CreateJob extends AppCompatActivity {

    private FirebaseDatabase firebaseDB = FirebaseUtils.connectFirebase();
    private DatabaseReference jobsRef = firebaseDB.getReference().child(FirebaseUtils.JOBS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_job);

        Button createJobBtn = findViewById(R.id.submitJobButton);

        createJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Job job = getJob();
                pushJob(job, jobsRef);
            }
        });
    }

    protected Job getJob() {
        EditText jobTitle = findViewById(R.id.jobTitle);
        EditText jobDesc = findViewById(R.id.description);
        EditText wage = findViewById(R.id.hourlyRate);

        Job job = new Job("cityboi@dal.ca", jobTitle.getText().toString(),
                jobDesc.getText().toString(), Integer.parseInt(wage.getText().toString()));

        job.setLocation(getLocation());
        return job;
    }


    protected String pushJob(Job job, DatabaseReference jobsRef) {
        //Push unique job details under "userID" node in jobs
        //userID needs to be mapped to logged in user
        String jobID = jobsRef.child("userID").push().getKey();
        jobsRef.child("userID").child(jobID).setValue(job);
        return jobID;
    }

    protected String getEmployerEmail() {
        TextView empEmail = findViewById(R.id.employerEmail);
        return empEmail.getText().toString();
    }

    //This will need to be extended.
    //Perhaps based on a google maps pin
    //or address and then extract lat and long
    //with gmaps API
    protected Location getLocation() {
        //Hard coded for now
        return new Location(32.539555, 75.970955);
    }


}
