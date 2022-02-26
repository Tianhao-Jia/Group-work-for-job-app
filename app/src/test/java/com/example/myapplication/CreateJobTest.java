package com.example.myapplication;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import android.text.TextUtils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class CreateJobTest {
    private FirebaseDatabase firebaseDB = FirebaseUtils.connectFirebase();
    private DatabaseReference jobsRef = firebaseDB.getReference(FirebaseUtils.JOBS);

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
    public void pushJobTest() {
        Job test = new Job("rsmith@dal.ca", "TA", "Carry 2110");
        assertTrue(createJobActivity.pushJob(test, jobsRef));
    }


}
