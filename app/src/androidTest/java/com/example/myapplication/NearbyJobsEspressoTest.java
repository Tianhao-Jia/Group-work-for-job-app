package com.example.myapplication;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

public class NearbyJobsEspressoTest {

    @Rule
    public ActivityScenarioRule rule  = new ActivityScenarioRule<>(RegisterUser.class);

    @Test
    public void checkNearbyJobsAreNearby()
    {}

    @Test
    public void checkFarJobsAreNotNearby()
    {}

}
