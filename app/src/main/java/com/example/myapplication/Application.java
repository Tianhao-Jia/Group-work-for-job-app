package com.example.myapplication;

public class Application {
    private String employeeEmail;

    public Application(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeeEmail(){
        return employeeEmail;
    }


    public Application(){};
}
