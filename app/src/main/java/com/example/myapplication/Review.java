package com.example.myapplication;

public class Review {

    private String reviewerID;
    private int review;
    private String reviewedType;
    private String name;
    private String email;

    private static final String employer_tag = "EMPLOYER";
    private static final String employee_tag = "EMPLOYEE";


    public void reviewEmployer(String userID, String email, String name, String reviewerID, int review){
        this.userID = userID;
        this.review = review;
        this.reviewerID = reviewerID;
        this.reviewedType = employer_tag;
        this.name = name;
        this.email = email;
    }

    public void reviewEmployee(String userID, String email, String name, String reviewerID, int review){
        this.userID = userID;
        this.review = review;
        this.reviewerID = reviewerID;
        this.reviewedType = employee_tag;
        this.name = name;
        this.email = email;

    }


    public String getEmail(){return this.email;}
    public String getName(){return this.name;}

    public String getReviewedType(){return reviewedType;}

    public String getUserID() {
        return userID;
    }

    public String getReviewerID() {
        return reviewerID;
    }

    public int getReview() {
        return review;
    }

    private String userID;

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setReviewerID(String reviewerID) {
        this.reviewerID = reviewerID;
    }

    public void setReview(int review) {
        this.review = review;
    }

}
