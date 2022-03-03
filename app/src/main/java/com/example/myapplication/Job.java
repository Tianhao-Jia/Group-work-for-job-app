package com.example.myapplication;



import android.app.AppComponentFactory;

public class Job {

    private String employer_email;
    private String job_title;
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
    public Job(String employer_email, String job_title, String description, double longitude, double latitude) {
        this.employer_email = employer_email;
        this.job_title = job_title;
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

    public String getEmployer_email() {
        return employer_email;
    }

    public void setEmployer_email(String employer_email) {
        this.employer_email = employer_email;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
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