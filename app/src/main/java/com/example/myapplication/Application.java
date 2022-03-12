package com.example.myapplication;

public class Application {
    private String employeeEmail;
    private Boolean accepted;
    private Boolean seen;
    private String description;

    public Application(String employeeEmail, Boolean accepted, Boolean seen, String description) {
        this.employeeEmail = employeeEmail;
        this.seen = seen;
        this.accepted = accepted;
        this.description = description;

    }

    public String getEmployeeEmail(){
        return employeeEmail;
    }
    public void setEmployeeEmail(String email){employeeEmail = email;}

    public Boolean getSeen(){
        return seen;
    }

    public void setSeen(Boolean seen){this.seen = seen;}

    public Boolean getAccepted(){
        return accepted;
    }
    public void setAccepted(Boolean accepted){this.accepted = accepted;}

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){this.description = description;}

    public Application(){};
}
