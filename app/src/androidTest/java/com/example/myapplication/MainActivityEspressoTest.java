package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertFalse;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {

    @Rule
    public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);

    /**
     * US5-AT1: Given that the user is not logged in, when the user closes their application, and
     * reopens it then the user should still need to login.
     *
     */
    @Test
    public void requireLoginWhenAppReopen() {
        ActivityScenario<RegisterUser> activityScenario = ActivityScenario.launch(RegisterUser.class);

        activityScenario.moveToState(Lifecycle.State.DESTROYED);
        activityScenario.close();

        ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.mainActivity)).check(matches(isDisplayed()));
    }


    /**
     * US5-AT2-Employee: Given that the user is not logged in, when the user closes their application, and
     * reopens it then the user should be sent to the MainActivty page where
     * they can register or login.
     *
     */
    @Test
    public void reopenToMainActivityWhenLoggedOutEmployee() {
        rule.getScenario().moveToState(Lifecycle.State.DESTROYED);
        rule.getScenario().close();

        ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.mainActivity)).check(matches(isDisplayed()));
    }

    /**
     * US5-AT2-Employer: Given that the user is not logged in, when the user closes their application, and
     * reopens it then the user should be sent to the MainActivty page where
     * they can register or login.
     *
     */
    @Test
    public void reopenToMainActivityWhenLoggedOutEmployer() {
        rule.getScenario().moveToState(Lifecycle.State.DESTROYED);
        rule.getScenario().close();

        ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.mainActivity)).check(matches(isDisplayed()));
    }

    /**
     * US5-AT3: Given that the user is an Employer and logged in, when the user closes
     * their application, and reopens it then the user should be sent to the EmployerActivity
     * page.
     *
     */
    @Test
    public void reopenAsEmployerWhenLoggedIn() {

        ActivityScenario.launch(RegisterUser.class);
        onView(withId(R.id.registerUser)).check(matches(isDisplayed()));
        onView(withId(R.id.registerFirstName)).perform(typeText("EmployerFirstName\n"));
        onView(withId(R.id.registerLastName)).perform(typeText("EmployerLastName\n"));
        onView(withId(R.id.registerEmail)).perform(typeText("erfirst.erlast@dal.ca\n"));
        onView(withId(R.id.registerPasswordET)).perform(typeText("employer1\n"));
        onView(withId(R.id.registerUserType)).perform(typeText("Employer\n"));
        Espresso.closeSoftKeyboard();

        //User is now registered and automatically logged in
        onView(withId(R.id.registerButton)).perform(click());

        //Verify user at the EmployerActivity
        onView(withId(R.id.employerView)).check(matches(isDisplayed()));

        //Close app then reopen
        rule.getScenario().moveToState(Lifecycle.State.DESTROYED);
        rule.getScenario().close();
        ActivityScenario.launch(MainActivity.class);

        //Verify the app has opened to EmployerActivity instead of MainActivity
        onView(withId(R.id.employerView)).check(matches(isDisplayed()));
    }

    /**
     * US5-AT4: Given that the user is an Employee and logged in, when the user closes
     * their application, and reopens it then the user should be sent to the EmployeeActivity
     * page.
     *
     */
    @Test
    public void reopenAsEmployeeWhenLoggedIn() {

        ActivityScenario.launch(RegisterUser.class);
        onView(withId(R.id.registerUser)).check(matches(isDisplayed()));
        onView(withId(R.id.registerFirstName)).perform(typeText("EmployeeFirstName\n"));
        onView(withId(R.id.registerLastName)).perform(typeText("EmployeeLastName\n"));
        onView(withId(R.id.registerEmail)).perform(typeText("eefirst.eelast@dal.ca\n"));
        onView(withId(R.id.registerPasswordET)).perform(typeText("employee1\n"));
        onView(withId(R.id.registerUserType)).perform(typeText("Employee\n"));
        Espresso.closeSoftKeyboard();

        //User is now registered and automatically logged in
        onView(withId(R.id.registerButton)).perform(click());

        //Verify user at the EmployeeActivity
        onView(withId(R.id.employeeView)).check(matches(isDisplayed()));

        //Close app then reopen
        rule.getScenario().moveToState(Lifecycle.State.DESTROYED);
        rule.getScenario().close();
        ActivityScenario.launch(MainActivity.class);

        //Verify the app has opened to EmployeeActivity instead of MainActivity
        onView(withId(R.id.employeeView)).check(matches(isDisplayed()));
    }
}
