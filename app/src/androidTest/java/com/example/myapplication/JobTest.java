package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class JobTest {

    private static final FirebaseDatabase firebaseDB = FirebaseUtils.connectFirebase();
    private static final DatabaseReference jobsRef = firebaseDB.getReference().child(FirebaseUtils.JOBS);

    @Rule
    public ActivityScenarioRule myRule = new ActivityScenarioRule<>(CreateJob.class);

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @AfterClass
    public static void tearDown() {
        Intents.release();
    }

    @Test
    public void createJob(){
        jobsRef.child("userID").setValue(null);
        onView(withId(R.id.jobTitle)).perform(typeText("Car Wash"));
        onView(withId(R.id.description)).perform(typeText("Make my Hellcat shine"));
        onView(withId(R.id.hourlyRate)).perform(typeText("25"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.submitJobButton)).perform(click());

        assertNotNull(jobsRef.child("userID"));
    }

    @Test
    public void checkLocation(){
//        jobsRef.child("userID").setValue(null);

        Location verify = new Location(32.539555,75.970955);
        onView(withId(R.id.jobTitle)).perform(typeText("Car Wash"));
        onView(withId(R.id.description)).perform(typeText("Make my Hellcat shine"));
        onView(withId(R.id.hourlyRate)).perform(typeText("25"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.submitJobButton)).perform(click());

        jobsRef.child("userID").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Job job = snapshot.getValue(Job.class);
                    assertEquals(job.getLocation(), verify);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                assertTrue(false);
            }
        });


    }

}
