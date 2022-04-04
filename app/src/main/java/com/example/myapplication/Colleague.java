package com.example.myapplication;

public class Colleague {

    private String email;
    private String name;

    /**
     * Constructors creates Colleague object
     * @param email String : email of colleague
     * @param name String : name of the colleague
     */
    public Colleague(String email,String name){
        this.email = email;
        this.name = name;
    }

    // getters and setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

}
