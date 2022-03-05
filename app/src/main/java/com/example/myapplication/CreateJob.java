package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                    setContentView(R.layout.activity_employer);
                }
            }
        });


    }

    /**
     * createJob method that takes the EditText contents from create_job.xml and creates a Job
     * object to be returned.
     * @return Job object if input is valid, otherwise NULL.
     * @author:
     * @refactorer: Nathanael Bowley
     */
    protected Job createJob() {
        if(validateInput()) {

            EditText jobEmailEditText = findViewById(R.id.createJobEmail);
            EditText jobTitleEditText = findViewById(R.id.createJobTitle);
            EditText jobDescEditText = findViewById(R.id.createJobDescription);
            EditText jobHourlyRateEditText = findViewById(R.id.createJobHourlyRate);

            String jobEmail = jobEmailEditText.getText().toString();
            String jobTitle = jobTitleEditText.getText().toString();
            String jobDesc = jobDescEditText.getText().toString();
            double jobHourlyRate;

            try {
                jobHourlyRate = Integer.parseInt(jobHourlyRateEditText.getText().toString());
            } catch (NumberFormatException e) {
                jobHourlyRate = 0;
            }

            Job job = new Job(jobEmail, jobTitle, jobDesc);
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
        jobsRef.child("userID").push().setValue(job);
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


}
