package com.example.myapplication;



import android.app.AppComponentFactory;

public class Job {

    private String employer_email;
    private String job_title;
    private String description;
    private int compensation;
    private Location location;

    /**
     * Default constructor for AppCompatActivity. All Activities must have a default constructor
     * for API 27 and lower devices or when using the default
     * {@link AppComponentFactory}.
     */
    public Job(String employer_email, String job_title, String description, int compensation) {
        this.employer_email = employer_email;
        this.job_title = job_title;
        this.description = description;
        this.compensation = compensation;
    }

    public Job(){};

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

    public int getCompensation() {
        return compensation;
    }

    public void setCompensation(int compensation) {
        this.compensation = compensation;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setLocation(int lat, int longitude) {
        this.location = new Location(lat, longitude);
    }

    public Location getLocation() {
        return location;
    }
}