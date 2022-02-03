package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


import static org.hamcrest.Matchers.not;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.database.FirebaseDatabase;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RegisterUserEspresso {

    private static final String FIREBASE_DATABASE_URL = "https://quick-cash-55715-default-rtdb.firebaseio.com/";

    @Rule
    public ActivityScenarioRule rule  = new ActivityScenarioRule<>(RegisterUser.class);


    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @Test
    public void activityInView() {
        onView(withId(R.id.registerUser)).check(matches(isDisplayed()));
    }

    /**
     * AT1:
     *Given that the user is not registered, when the user inputs their details and clicks the
     * “register” button, the user should be registered.
     */
    @Test
    public void userRegistered() {
        onView(withId(R.id.nameFN)).perform(typeText("George"));
        onView(withId(R.id.nameLN)).perform(typeText("Smith"));
        onView(withId(R.id.email)).perform(typeText("george.smith@dal.ca"));
        onView(withId(R.id.userType)).perform(typeText("Employee"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.registerBtn)).perform(click());

        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }

    /**
     * AT2:
     * Given that the user is registered, when the user inputs their details and clicks “register”,
     * it should display an error: “You already have an account”
     */
    @Test
    public void AlreadyRegisteredTest(){
        // Assume that an 'example' record already exists on Firebase with these details
        onView(withId(R.id.nameFN)).perform(typeText("John"));
        onView(withId(R.id.nameLN)).perform(typeText("Adams"));
        onView(withId(R.id.email)).perform(typeText("john.adams@dal.ca"));
        onView(withId(R.id.userType)).perform(typeText("Employee"));

        Espresso.closeSoftKeyboard();
        onView(withId(R.id.registerBtn)).perform(click());

        onView(withId(R.id.registerUser)).check(matches(not(isDisplayed())));
    }

    /**
     * AT3: Given that the user is not registered, when the user is inputting their details, then
     * we should throw an error if the required fields are not filled
     */
    @Test
    public void requiredFieldNotFilledTest(){
        onView(withId(R.id.nameFN)).perform(typeText("George"));
        onView(withId(R.id.nameLN)).perform(typeText("Smith"));

        Espresso.closeSoftKeyboard();
        onView(withId(R.id.registerBtn)).perform(click());

        // checks if login page is not displayed
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
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.registerBtn)).perform(click());

        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }

    @Test
    public void requiredFieldsNotFilled() {
        onView(withId(R.id.nameFN)).perform(typeText("George"));
        onView(withId(R.id.email)).perform(typeText("george.smith@dal.ca"));
        onView(withId(R.id.userType)).perform(typeText("Employee"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.registerBtn)).perform(click());

        onView(withId(R.id.registerUser)).check(matches(isDisplayed()));
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
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.registerBtn)).perform(click());

        onView(withId(R.id.login)).check(matches(isDisplayed()));
    }

    @AfterClass
    public static void tearDown(){
        System.gc();
    }




}