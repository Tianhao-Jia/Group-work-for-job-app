package com.example.myapplication;

public class Review {

    private String reviewerID;
    private int review;

    public Review(String userID, String reviewerID, int review){
        this.userID = userID;
        this.review = review;
        this.reviewerID = reviewerID;
    }
    
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
