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
        setContentView(R.layout.activity_create_job);

        Button createJobBtn = findViewById(R.id.submitJobButton);

        createJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDB = FirebaseUtils.connectFirebase();
                jobsRef = firebaseDB.getReference().child(FirebaseUtils.JOBS);
                Job job = createJob();
                if (job != null) {
                    pushJob(job, jobsRef);
                    Toast.makeText(CreateJob.this,"Success", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    protected Job createJob() {
        if(validateInput()) {
            EditText jobTitle = findViewById(R.id.jobTitle);
            EditText jobDesc = findViewById(R.id.description);
            EditText wage = findViewById(R.id.hourlyRate);
            EditText email = findViewById(R.id.employer_email);
            EditText lat = findViewById(R.id.lat);
            EditText longitude = findViewById(R.id.longitude);
            Job job = new Job(getEmployerEmail(), jobTitle.getText().toString(),
                    jobDesc.getText().toString());
            job.setCompensation(Integer.parseInt((wage.getText().toString())));
            job.setLat(Double.parseDouble((lat.getText().toString())));
            job.setLongitude(Double.parseDouble((longitude.getText().toString())));
            return job;
        }
        else {
            Toast.makeText(CreateJob.this,"Invalid Input", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CreateJob.this, EmployerActivity.class);
            startActivity(intent);
            return null;
        }
    }


    protected boolean pushJob(Job job, DatabaseReference jobsRef) {
        //Push unique job details under "userID" node in jobs
        //userID needs to be mapped to logged in user
        jobsRef.push().setValue(job);
        return true;
    }

    protected String getEmployerEmail() {
        EditText email = findViewById(R.id.employer_email);
       return email.getText().toString();
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


}
