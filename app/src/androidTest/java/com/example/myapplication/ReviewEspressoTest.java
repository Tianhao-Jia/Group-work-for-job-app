package com.example.myapplication;


import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.firebase.database.DatabaseReference;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ReviewEspressoTest {
    public static final String TEST_ID = "123";
    // Getting an instance of the firebase realtime database
    public static final DatabaseReference col_ref = FirebaseUtils.connectFirebase().getReference().child("colleagues");

    @Rule
    public ActivityScenarioRule myRule = new ActivityScenarioRule<>(reviewView.class);


    @BeforeClass
    public static void setup() {
        Session.startSession(InstrumentationRegistry.getInstrumentation().getTargetContext());
    }

    @Test
    public void seeReview(){
        Session.login("test@dal.ca", TEST_ID, "Employer");

    }


}
