package com.example.myapplication;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import android.text.TextUtils;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class CreateJobTest {

    static CreateJob createJobActivity;

    @BeforeClass
    public static void setup() {
        createJobActivity = new CreateJob();
    }

    @AfterClass
    public static void tearDown() {
        System.gc();
    }

    @Test
    public void validDesc() {
        assertTrue(createJobActivity.validateJobDescription("This is a job description!"));
    }

    @Test
    public void invalidDesc() {
        assertFalse(createJobActivity.validateJobDescription(""));
    }

    @Test
    public void validTitle() {
        assertTrue(createJobActivity.validateTitle("A valid title!"));
    }

    @Test
    public void invalidTitle() {
        assertFalse(createJobActivity.validateTitle(""));
    }

    @Test
    public void validWage() {
        assertTrue(createJobActivity.validateWage("12345"));
    }

    @Test
    public void invalidWage() {
        assertFalse(createJobActivity.validateWage("-5"));
    }


}
