package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import androidx.annotation.NonNull;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class JobSearchEspressoTest {

    @Rule
    public ActivityScenarioRule myRule =
            new ActivityScenarioRule(JobSearch.class);

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @AfterClass
    public static void tearDown() {
        Intents.release();
    }

//    @Test
//    public void searchJob(){
//        onView(withId(R.id.jobTitle)).perform(typeText("Car Wash"));
//        onView(withId(R.id.description)).perform(typeText("Make my Hellcat shine"));
//        onView(withId(R.id.hourlyRate)).perform(typeText("25"));
//        Espresso.closeSoftKeyboard();
//        onView(withId(R.id.submitJobButton)).perform(click());
//
//        jobsRef.child("userID").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                assertEquals(snapshot.getChildrenCount(), 1);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                assertFalse(true);
//            }
//        });
//    }


}
