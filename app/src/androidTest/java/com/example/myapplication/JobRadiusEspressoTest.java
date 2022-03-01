package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.app.Activity;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

public class JobRadiusEspressoTest {

    /**
     * US11-AT1
     *
     * Given the user is in a location, when the user searches for jobs,
     * they should only see jobs within some radius
     */
    @Test
    public void testUserGetsJobsWithinRadius()
    {}

    /**
     * US11-AT2
     *
     * Given the user is in a location, when the user searches for jobs,
     * the jobs that are outside the radius should not appear
     */
    @Test
    public void testUserDoesNotGetJobOutsideRadius()
    {}

    /**
     * US11-AT3
     *
     * Given the user is in a location, when they change their location,
     * the jobs within the new radius should appear
     */
    @Test
    public void testUserGetsNewJobsAfterLocationChange()
    {}

    /**
     * US11-AT4
     *
     * Given the user is in a location, when they change their search radius,
     * jobs within the new radius should appear.
     */
    @Test
    public void testUserGetsNewJobsAfterRadiusChange()
    {}
}
