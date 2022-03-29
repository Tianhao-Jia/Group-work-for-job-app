package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

//Popup Window concept referenced from https://www.youtube.com/watch?v=fn5OlqQuOCk&ab_channel=FilipVujovic
public class JobPopupWindow extends Activity {


    private TextView jobTitle;
    private TextView jobDesc;
    private TextView jobComp;
    private TextView jobEmp;
    private TextView jobCat;
    private Button accept;
    private Button decline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_job_popup_window);

        //Setup popup window
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int popupWidth = metrics.widthPixels;
        int popupHeight = metrics.heightPixels;

        getWindow().setLayout((int) (popupWidth * 0.8), (int) (popupHeight * 0.6));

        jobTitle = (TextView) findViewById(R.id.jobTitleVar);
        jobDesc = (TextView) findViewById(R.id.jobDescVar);
        jobComp = (TextView) findViewById(R.id.jobCompVar);
        jobEmp = (TextView) findViewById(R.id.jobEmpVar);
        jobCat = (TextView) findViewById(R.id.jobCatVar);
        accept = (Button) findViewById(R.id.acceptBtn);
        decline = (Button) findViewById(R.id.declineBtn);
    }

}
