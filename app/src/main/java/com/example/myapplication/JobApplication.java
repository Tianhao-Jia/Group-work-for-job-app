package com.example.myapplication;

public class JobApplication {
    private String jobID;
    private String employeeID;
    private String employerID;
    private String message;
    private boolean accepted;
    private boolean seen;

    public JobApplication(String jobID, String employeeID, String employerID, String message, boolean accepted, boolean seen) {
        this.jobID = jobID;
        this.employeeID = employeeID;
        this.employerID = employerID;
        this.message = message;
        this.accepted = accepted;
        this.seen = seen;
    }

    public JobApplication(){};

    public String getJobID() {
        return jobID;
    }

    public void setJobID(String jobID) {
        this.jobID = jobID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployerID() {
        return employerID;
    }

    public void setEmployerID(String employerID) {
        this.employerID = employerID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
