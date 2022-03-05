package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class JobSearch extends Activity {

    private EditText employerEmailEditText;
    private EditText jobTitleEditText;
    private EditText descriptionEditText;
    private EditText hourlyRateEditText;
    private Button searchButton;

    //private static final String[] TEST = new String[]{ "Programmer", "Coder", "Miner", "Tester", "Problem Solver"};
    //private HashMap<> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_search);

        init();

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, TEST);
        //AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.searchCreateJob);
        //textView.setAdapter(adapter);

    }

    private void init() {
        employerEmailEditText = findViewById(R.id.searchEmployerEmail);
        jobTitleEditText = findViewById(R.id.searchJobTitle);
        descriptionEditText = findViewById(R.id.searchDescription);
        hourlyRateEditText = findViewById(R.id.searchHourlyRate);
        searchButton = findViewById(R.id.searchJobButton);
    }



}
