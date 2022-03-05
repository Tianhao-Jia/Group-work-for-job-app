package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class JobSearch extends Activity {

    private EditText employerEmailEditText;
    private EditText jobTitleEditText;
    private EditText descriptionEditText;
    private EditText hourlyRateEditText;
    private Button searchButton;

    private FirebaseDatabase firebaseDB;
    private DatabaseReference jobsRef;

    //stored in order of employerEmail, jobTitle, description, hourlyRate
    private String[] inputs = new String[4];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_search);
        init();

        firebaseDB = FirebaseUtils.connectFirebase();
        jobsRef = firebaseDB.getReference().child(FirebaseUtils.JOBS);

        setSearchButtonListener();

    }

    /**
     * init method that links the parts of the job_search.xml file to the JobSearch.java fields.
     * @author: Nathanael Bowley
     */
    private void init() {
        employerEmailEditText = findViewById(R.id.searchEmployerEmail);
        jobTitleEditText = findViewById(R.id.searchJobTitle);
        descriptionEditText = findViewById(R.id.searchDescription);
        hourlyRateEditText = findViewById(R.id.searchHourlyRate);
        searchButton = findViewById(R.id.searchJobButton);
    }

    /**
     * setSearchButtonListener method that gets the contents of the 4 editText boxes and stores them
     * inside of the String[] array to determine if any inputs are to be checked, if all are left
     * blank then no search will be conducted, however if even one is non empty then
     * searchJobs method will be called.
     * @author: Nathanael Bowley
     */
    private void setSearchButtonListener() {
        searchButton.setOnClickListener(view -> {
            inputs[0] = employerEmailEditText.getText().toString();
            inputs[1] = employerEmailEditText.getText().toString();
            inputs[2] = descriptionEditText.getText().toString();
            inputs[3] = hourlyRateEditText.getText().toString();

            boolean searchableString = false;
            for (int i = 0; i< inputs.length; i++) {
                if (!inputs[i].trim().isEmpty()) {
                    searchableString = true;
                }
                else {
                    inputs[i] = null;
                }
            }

            if (searchableString) {
                searchJobs(inputs);
            }
        });
    }

    /**
     * searchJobs method that takes in an array of Strings as the searchPreferences and searches
     * the firebase database to see if any of the chosen names or properties chosen match what the
     * which then shows the corresponding searched inputs on the screen.
     * user entered in.
     * @param searchPreferences String[] array that is used as the search preferences of the user
     * @author: Nathanael Bowley
     */
    private void searchJobs(String[] searchPreferences) {
        System.exit(0);
    }

}
