package com.example.myapplication;

/**
 * Employee class that will be used for refactoring code to reduce coupling in iteration 2 of
 * the Winter 2022 CSCI3130 group project.
 * @authors: Nathanael Bowley,
 *          John Corsten,
 *          Nathan Horne,
 *          Ted Graveson,
 *          Hongzheng Ding,
 *          Tianhao Jia,
 *          Saher Anwar Ziauddin
 * @course: CSCI3130 @ Dalhousie University.
 * @semester: Winter 2022
 * @group: Group 4
 * @clientTA: Disha Malik
 */
public class Employee extends User {

    public static String EMPLOYEE = "Employee";
    private double distance;

    public Employee(String firstName, String lastName, String rating, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.rating = rating;
        try {
            this.doubleRating = Double.parseDouble(rating);
        } catch (NumberFormatException e) {
            this.doubleRating = -1;
        }
        this.email = email;
        this.distance = Double.MAX_VALUE;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
