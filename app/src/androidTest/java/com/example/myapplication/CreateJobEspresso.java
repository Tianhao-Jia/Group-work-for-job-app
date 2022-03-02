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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CreateJobEspresso {

    private static final FirebaseDatabase firebaseDB = FirebaseUtils.connectFirebase();
    private static final DatabaseReference jobsRef = firebaseDB.getReference().child(FirebaseUtils.JOBS);

    @Rule
    public ActivityScenarioRule myRule = new ActivityScenarioRule<>(CreateJob.class);

    @Before
    public void clearNode() {
        jobsRef.child("userID").setValue(null);
    }

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
        onView(withId(R.id.jobTitle)).perform(typeText("Car Wash"));
        onView(withId(R.id.description)).perform(typeText("Make my Hellcat shine"));
        onView(withId(R.id.hourlyRate)).perform(typeText("25"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.submitJobButton)).perform(click());

        jobsRef.child("userID").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                assertEquals(snapshot.getChildrenCount(), 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                assertFalse(true);
            }
        });
    }

    @Test
    public void invalidWage() {
        onView(withId(R.id.jobTitle)).perform(typeText("Car Wash"));
        onView(withId(R.id.description)).perform(typeText("Make my Hellcat shine"));
        onView(withId(R.id.hourlyRate)).perform(typeText(""));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.submitJobButton)).perform(click());

        jobsRef.child("userID").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                assertEquals(snapshot.getChildrenCount(), 0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                assertFalse(true);
            }
        });
    }

    @Test
    public void invalidTitle() {
        onView(withId(R.id.jobTitle)).perform(typeText(""));
        onView(withId(R.id.description)).perform(typeText("Make my Hellcat shine"));
        onView(withId(R.id.hourlyRate)).perform(typeText("25"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.submitJobButton)).perform(click());

        jobsRef.child("userID").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                assertEquals(snapshot.getChildrenCount(), 0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                assertFalse(true);
            }
        });
    }

    @Test
    public void invalidDesc() {
        onView(withId(R.id.jobTitle)).perform(typeText("Title"));
        onView(withId(R.id.description)).perform(typeText(""));
        onView(withId(R.id.hourlyRate)).perform(typeText("25"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.submitJobButton)).perform(click());

        jobsRef.child("userID").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                assertEquals(snapshot.getChildrenCount(), 0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                assertFalse(true);
            }
        });
    }

    @Test
    public void allFieldsFilled(){

    }

    //This method was always returning 0.
    //Might be due to some threading/asynch stuff

//    protected long getNumChildren(DatabaseReference jobsNode, String userID) {
//        final long[] numChildren = new long[1];
//        jobsNode.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                numChildren[0] = snapshot.getChildrenCount();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                numChildren[0] = -1;
//            }
//        });
//
//        return numChildren[0];
//    }

}
