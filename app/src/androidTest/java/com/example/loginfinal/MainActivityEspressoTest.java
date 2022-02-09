package com.example.loginfinal;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

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
    public void reopenToMainActivityWhenLoggedOut() {
        rule.getScenario().moveToState(Lifecycle.State.DESTROYED);
        rule.getScenario().close();

        ActivityScenario.launch(EmployeeActivity.class);
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


        ActivityScenario.launch(EmployerActivity.class);
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
        onView(withId(R.id.nameFN)).perform(typeText("Eugene"));
        onView(withId(R.id.nameLN)).perform(typeText("Krabs"));
        onView(withId(R.id.email)).perform(typeText("eugene.krabs@dal.ca"));
        onView(withId(R.id.userType)).perform(typeText("Employer"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.registerBtn)).perform(click());

        //force it back to MainActivity to login
        ActivityScenario.launch(MainActivity.class);

        //login --requires functionality from us-1



        //it should move to new window
        onView(withId(R.id.login)).perform(click());

        //force it back to MainActivity
        ActivityScenario.launch(MainActivity.class);

        //see if it will redirect back to page since logged in


        //check if its still MainActivity
        onView(withId(R.id.employer_view)).check(matches(isDisplayed()));



    }

    /**
     * US5-AT4: Given that the user is an Employee and logged in, when the user closes
     * their application, and reopens it then the user should be sent to the EmployeeActivity
     * page.
     *
     */
    @Test
    public void reopenAsEmployeeWhenLoggedIn() {

        onView(withId(R.id.nameFN)).perform(typeText("Larry"));
        onView(withId(R.id.nameLN)).perform(typeText("Lobster"));
        onView(withId(R.id.email)).perform(typeText("larry.lobster@dal.ca"));
        onView(withId(R.id.userType)).perform(typeText("Employee"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.registerBtn)).perform(click());

        //force it back to MainActivity to login
        ActivityScenario.launch(MainActivity.class);

        //login --requires functionality from us-1



        //it should move to new window
        onView(withId(R.id.login)).perform(click());

        //force it back to MainActivity
        ActivityScenario.launch(MainActivity.class);

        //see if it will redirect back to page since logged in


        //check if its still MainActivity
        onView(withId(R.id.employee_view)).check(matches(isDisplayed()));


    }
}
