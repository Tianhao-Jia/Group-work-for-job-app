package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateJob extends AppCompatActivity {

    private FirebaseDatabase firebaseDB;
    private DatabaseReference jobsRef;

    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_job);

        Button createJobBtn = findViewById(R.id.createJobSubmitButton);

        Intent intent = getIntent();
        extras = getIntent().getExtras();

        TextView email = findViewById(R.id.createJobEmail);

        email.setText(getEmployerEmail());

        createJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDB = FirebaseUtils.connectFirebase();
                jobsRef = firebaseDB.getReference().child(FirebaseUtils.JOBS_COLLECTION);
                Job job = createJob();
                if (job != null) {
                    pushJob(job, jobsRef);
                    Intent intent = new Intent(CreateJob.this, EmployerActivity.class);

                    intent.putExtra("User Hash", (String) extras.get("User Hash"));
                    intent.putExtra("Login Email", (String) extras.get("Login Email"));
                    intent.putExtra("Login Password", (String) extras.get("Login Password"));
                    intent.putExtra("User Type", (String) extras.get("User Type"));

                    startActivity(intent);
                    //setContentView(R.layout.activity_employer);
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

            // Dummy values to be used until location functionality is added in another user story
            double latitude = 100;
            double longitude = 100;
            Location location = new Location(latitude, longitude);

            String jobEmail = jobEmailEditText.getText().toString();
            String jobTitle = jobTitleEditText.getText().toString();
            String jobDesc = jobDescEditText.getText().toString();
            double jobHourlyRate;

            try {
                jobHourlyRate = Integer.parseInt(jobHourlyRateEditText.getText().toString());
            } catch (NumberFormatException e) {
                jobHourlyRate = 0;
            }

            Job job = new Job(jobEmail, jobTitle, jobDesc, location);
            job.setCompensation(jobHourlyRate);
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

        // Stores job in job node on realtime database, filed under the hash corresponding to the user
        // that created the job
        //jobsRef.child(getUserHash()).push().setValue(job);
        jobsRef.child("jobs").push().setValue(job);

        return true;
    }

    protected String getUserHash(){
        String hash;
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            hash = extras.getString("User Hash");;
        }
        else{
            hash = "hashNotFound";
        }
        return hash;
    }

    protected String getEmployerEmail() {
//        TextView empEmail = findViewById(R.id.employerEmail);
//        return empEmail.getText().toString();

        String employerEmail;
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            employerEmail = extras.getString("Login Email");;
        }
        else{
            employerEmail = "notFound@dal.ca";
        }
        return employerEmail;
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

    protected static boolean validateTitle(String jobTitle) {
        Pattern fnPattern = Pattern.compile("^.{1,200}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = fnPattern.matcher(jobTitle.trim());

        return matcher.matches();
    }

    protected static boolean validateJobDescription(String desc) {
        Pattern fnPattern = Pattern.compile("^.{1,500}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = fnPattern.matcher(desc.trim());

        return matcher.matches();
    }

    protected static boolean validateWage(String wage) {
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
    protected static boolean validateLongLat(double coordinate){
        // Valid longitudes and latitudes are both between -180 degrees and 180 degrees
        if (coordinate >= 180 || coordinate <= - 180){
            return false;
        }
        return true;
    }

}
