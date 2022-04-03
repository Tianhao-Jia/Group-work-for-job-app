package com.example.myapplication;

/**
 * Employer class that will be used for refactoring code to reduce coupling in iteration 2 of
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
public class Employer extends User {

    public static String EMPLOYER = "Employer";

    public Employer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
