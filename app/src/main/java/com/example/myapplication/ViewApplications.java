package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ViewApplications extends Activity {

    private RecyclerView recyclerView;
    private ViewApplicationAdapter viewApplicationAdapter;

    private TextView applicationEmail;
    private TextView applicationName;

    private Button applicationAccept;
    private Button applicationIgnore;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private TextView employeeEmailET;
    private TextView employeeNameET;
    private Button ignore;
    private Button accept;

    private FirebaseDatabase firebaseDB;
    private DatabaseReference appsRef;
    private Button homeButton;

    //stored in order of employerEmail, jobTitle, description, hourlyRate
    private String[] inputs = new String[4];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.applications_page);
        init();

        homeButton = findViewById(R.id.applicationsToEmployer);

        firebaseDB = FirebaseUtils.connectFirebase();
        appsRef = firebaseDB.getReference().child("applications");

        //citation based on code from Dhrumils lab presentation on march 2nd in this course csci3130
        FirebaseRecyclerOptions<Application> options = new FirebaseRecyclerOptions.Builder<Application>()
                .setQuery(FirebaseDatabase.getInstance(FirebaseUtils.FIREBASE_URL)
                        .getReference().child("applications").child(Session.getUserID()), Application.class)
                .build();

        viewApplicationAdapter = new ViewApplicationAdapter(options);
        recyclerView.setAdapter(viewApplicationAdapter);
        //end citation

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewApplications.this, EmployerActivity.class);
                startActivity(intent);
            }
        });

    }

    private void init() {


        recyclerView = findViewById(R.id.jobApplicationsRecyclerView);

        //textviews for results

        employeeEmailET = findViewById(R.id.jobApplicationEmail);
        employeeNameET = findViewById(R.id.applicantName);

        ignore = recyclerView.findViewById(R.id.ignoreApplication);
        accept = recyclerView.findViewById(R.id.acceptApplication);

        recyclerView.setLayoutManager(new WrapLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
    }

}
