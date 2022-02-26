package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CreateJob extends AppCompatActivity {

    private FirebaseDatabase firebaseDB = FirebaseUtils.connectFirebase();
    private DatabaseReference jobsRef = firebaseDB.getReference(FirebaseUtils.JOBS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_job);
    }


    protected boolean pushJob(Job job, DatabaseReference jobsRef) {
        Map<String, Job> jobMap = new HashMap<>();
        jobMap.put("name", job);
        jobsRef.push().setValue(jobMap);
        return true;
    }


}
