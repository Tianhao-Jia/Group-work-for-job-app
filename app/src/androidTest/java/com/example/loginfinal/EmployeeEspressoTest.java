package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.ComponentName;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EmployeeEspressoTest {

    @Rule
    public ActivityScenarioRule<MainActivity> myRule =
            new ActivityScenarioRule<MainActivity>(MainActivity.class);


    @Test
    public void testOpenEmployeeActivity() {
        onView(withId(R.id.debugGoToEmployeeActivity)).perform(click());
        onView(withId(R.id.appCompatTextView)).check(matches(isDisplayed()));

    }

}
