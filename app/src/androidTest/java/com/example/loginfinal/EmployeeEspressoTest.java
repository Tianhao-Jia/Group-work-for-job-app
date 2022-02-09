package com.example.loginfinal;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
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
public class EmployeeEspressoTest {

    @Rule
    public ActivityScenarioRule<MainActivity> myRule =
            new ActivityScenarioRule<MainActivity>(MainActivity.class);


    @Test
    public void testOpenEmployeeActivity() {

        //previous merge broke this test. need to modify it.



        onView(withId(R.id.goToEmployeeActivity)).perform(click());
        onView(withId(R.id.appCompatTextView)).check(matches(isDisplayed()));

    }

}
