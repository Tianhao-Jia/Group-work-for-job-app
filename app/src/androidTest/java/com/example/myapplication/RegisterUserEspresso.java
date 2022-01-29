package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RegisterUserEspresso {

    @Rule
    public ActivityScenarioRule rule  = new ActivityScenarioRule<>(RegisterUser.class);

    @Test
    public void activityInView() {
        onView(withId(R.id.registerUser)).check(matches(isDisplayed()));
    }

    /**
     * AT4:
     * Given that the user is not registered, when the user in inputting their details,
     * we should accept when the user has only filled the required fields
      */
    //Need to add optional fields to UI, covid check was broken so will try and add that back
    //Also will add database mocks to make sure user doesn't already exist
    @Test
    public void allRequiredFieldsFilled() {
        onView(withId(R.id.nameFN)).perform(typeText("George"));
        onView(withId(R.id.nameLN)).perform(typeText("Smith"));
        onView(withId(R.id.email)).perform(typeText("george.smith@dal.ca"));
        onView(withId(R.id.userType)).perform(typeText("Employee"));

        onView(withId(R.id.addBTN)).perform(click());

        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }

    /**
     * AT5:
     * Given that the user is not registered, when the user fills their details and clicks the ‘register button’,
     * then the user should be directed to the login page.
     */
    @Test
    public void redirectToLoginPage() {
        onView(withId(R.id.nameFN)).perform(typeText("George"));
        onView(withId(R.id.nameLN)).perform(typeText("Smith"));
        onView(withId(R.id.email)).perform(typeText("george.smith@dal.ca"));
        onView(withId(R.id.userType)).perform(typeText("Employee"));

        onView(withId(R.id.addBTN)).perform(click());

        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }



}