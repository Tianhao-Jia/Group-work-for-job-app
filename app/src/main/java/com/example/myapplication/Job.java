package com.example.myapplication;



import android.app.AppComponentFactory;

import java.io.Serializable;

public class Job implements Serializable {

    private String employerEmail;
    private String jobTitle;
    private String description;
    private double compensation = 0;
    // Logitude and latitude representing the location of the job
    private double lat;
    private double longitude;


    /**
     * Default constructor for AppCompatActivity. All Activities must have a default constructor
     * for API 27 and lower devices or when using the default
     * {@link AppComponentFactory}.
     */
    public Job(String employerEmail, String jobTitle, String description, double longitude, double latitude) {
        this.employerEmail = employerEmail;
        this.jobTitle = jobTitle;
        this.description = description;
        this.lat = latitude;
        this.longitude = longitude;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude =longitude;
    }

    public String getEmployerEmail() {
        return employerEmail;
    }

    public void setEmployerEmail(String employerEmail) {
        this.employerEmail = employerEmail;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCompensation() {
        return compensation;
    }

    public void setCompensation(double compensation) {
        this.compensation = compensation;
    }
}