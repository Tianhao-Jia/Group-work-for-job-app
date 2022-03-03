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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateJob extends AppCompatActivity {

    private FirebaseDatabase firebaseDB;
    private DatabaseReference jobsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_job);

        Button createJobBtn = findViewById(R.id.submitJobButton);

        createJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDB = FirebaseUtils.connectFirebase();
                jobsRef = firebaseDB.getReference().child(FirebaseUtils.JOBS);
                Job job = createJob();
                if (job != null) {
                    pushJob(job, jobsRef);
                    setContentView(R.layout.activity_employer);
                }
            }
        });


    }

    protected Job createJob() {
        if(validateInput()) {
            EditText jobTitle = findViewById(R.id.jobTitle);
            EditText jobDesc = findViewById(R.id.description);
            EditText wage = findViewById(R.id.hourlyRate);
            // Dummy values to be used until location functionality is added in another user story
            double longitude = 100;
            double latitude = 100;

            Job job = new Job(getEmployerEmail(), jobTitle.getText().toString(),
                    jobDesc.getText().toString(), longitude, latitude);
            job.setCompensation(Integer.parseInt((wage.getText().toString())));
            return job;
        }
        else {
            Toast.makeText(CreateJob.this,"Invalid Input", Toast.LENGTH_SHORT).show();
            return null;
        }
    }


    protected boolean pushJob(Job job, DatabaseReference jobsRef) {
        //Push unique job details under "userID" node in jobs
        //userID needs to be mapped to logged in user
        jobsRef.child("userID").push().setValue(job);
        return true;
    }

    protected String getEmployerEmail() {
//        TextView empEmail = findViewById(R.id.employerEmail);
//        return empEmail.getText().toString();
        return "cityboi@dal.ca";
    }

    protected boolean validateInput() {
        EditText jobTitle = findViewById(R.id.jobTitle);
        EditText jobDesc = findViewById(R.id.description);
        EditText wage = findViewById(R.id.hourlyRate);

        boolean validTitle =  validateTitle(jobTitle.getText().toString());
        boolean validDesc = validateJobDescription(jobDesc.getText().toString());
        boolean validWage = validateWage(wage.getText().toString());

        return validTitle && validDesc && validWage;

    }

    protected boolean validateTitle(String jobTitle) {
        Pattern fnPattern = Pattern.compile("^.{1,200}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = fnPattern.matcher(jobTitle.trim());

        return matcher.matches();
    }

    protected boolean validateJobDescription(String desc) {
        Pattern fnPattern = Pattern.compile("^.{1,500}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = fnPattern.matcher(desc.trim());

        return matcher.matches();
    }

    protected boolean validateWage(String wage) {
        Pattern fnPattern = Pattern.compile("^[0-9]{1,20}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = fnPattern.matcher(wage.trim());

        return matcher.matches();
    }

    /**
    Method validates a given longitude or latitude (provided in degrees)
     */
    protected boolean validateLongLat(double coordinate){
        // Not yet implemented
        return false;
    }

}
