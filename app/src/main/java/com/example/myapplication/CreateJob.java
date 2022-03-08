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

        Button createJobBtn = findViewById(R.id.createJobSubmitButton);

        createJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDB = FirebaseUtils.connectFirebase();
                jobsRef = firebaseDB.getReference().child(FirebaseUtils.JOBS_COLLECTION);
                Job job = createJob();
                if (job != null) {
                    pushJob(job, jobsRef);
                    Toast.makeText(CreateJob.this,"Success", Toast.LENGTH_SHORT).show();
                    setContentView(R.layout.activity_employer);
                }
            }
        });


    }

    /**
     * createJob method that takes the EditText contents from create_job.xml and creates a Job
     * object to be returned.
     * @return Job object if input is valid, otherwise NULL.
     * @author: John Corsten
     * @refactorer: Nathanael Bowley
     */
    protected Job createJob() {
        if(validateInput()) {

            EditText jobEmailEditText = findViewById(R.id.createJobEmail);
            EditText jobTitleEditText = findViewById(R.id.createJobTitle);
            EditText jobDescEditText = findViewById(R.id.createJobDescription);
            EditText jobHourlyRateEditText = findViewById(R.id.createJobHourlyRate);
            EditText latitudeEditText = findViewById(R.id.lat);
            EditText longitudeEditText = findViewById(R.id.longitude);




            String jobEmail = jobEmailEditText.getText().toString();
            String jobTitle = jobTitleEditText.getText().toString();
            String jobDesc = jobDescEditText.getText().toString();
            double jobHourlyRate, latitude, longitude;

            try {
                jobHourlyRate = Double.parseDouble(jobHourlyRateEditText.getText().toString());
                latitude = Double.parseDouble(latitudeEditText.getText().toString());
                longitude = Double.parseDouble(longitudeEditText.getText().toString());

            } catch (NumberFormatException e) {
                jobHourlyRate = 0;
                latitude = 0;
                longitude = 0;
            }

            Location location = new Location(latitude, longitude);

            Job job = new Job(jobEmail, jobTitle, jobDesc, location);
            job.setCompensation(jobHourlyRate);
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

    protected boolean validateInput() {
        EditText jobTitle = findViewById(R.id.createJobTitle);
        EditText jobDesc = findViewById(R.id.createJobDescription);
        EditText wage = findViewById(R.id.createJobHourlyRate);

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
     * Method validates a given longitude or latitude (provided in degrees)
     * @param coordinate
     * @return
     * @author John Corsten
     */
    protected boolean validateLongLat(double coordinate){
        // Valid longitudes and latitudes are both between -180 degrees and 180 degrees
        if (coordinate >= 180 || coordinate <= - 180){
            return false;
        }
        return true;
    }

}
