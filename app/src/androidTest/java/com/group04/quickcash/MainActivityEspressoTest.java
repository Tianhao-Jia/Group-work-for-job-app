package com.group04.quickcash;

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

        ActivityScenario.launch(RegisterUser.class);
        onView(withId(R.id.registerFirstName)).perform(typeText("Eugene"));
        onView(withId(R.id.registerLastName)).perform(typeText("Krabs"));
        onView(withId(R.id.registerEmail)).perform(typeText("eugene.krabs@dal.ca"));
        onView(withId(R.id.registerPasswordET)).perform(typeText("MoneyMoneyMoney123"));
        onView(withId(R.id.registerUserType)).perform(typeText("Employer"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.registerButton)).perform(click());

        //force it back to MainActivity to login
        ActivityScenario.launch(MainActivity.class);

        //login --requires functionality from us-1
        onView(withId(R.id.goToEmployeeActivity)).perform(click());

        onView(withId(R.id.loginUsernameET)).perform(typeText("eugene.krabs@dal.ca"));
        onView(withId(R.id.loginPasswordET)).perform(typeText("MoneyMoneyMoney123"));
        onView(withId(R.id.loginButton)).perform(click());

        //force it back to MainActivity
        //see if it will redirect back to page since logged in
        ActivityScenario.launch(MainActivity.class);

        //check if its still MainActivity
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
        onView(withId(R.id.registerFirstName)).perform(typeText("Larry"));
        onView(withId(R.id.registerLastName)).perform(typeText("Lobster"));
        onView(withId(R.id.registerEmail)).perform(typeText("larry.lobster@dal.ca"));
        onView(withId(R.id.registerPasswordET)).perform(typeText("RedLobster"));
        onView(withId(R.id.registerUserType)).perform(typeText("Employee"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.registerButton)).perform(click());

        //force it back to MainActivity to login
        ActivityScenario.launch(MainActivity.class);

        //login --requires functionality from us-1
        onView(withId(R.id.goToEmployeeActivity)).perform(click());

        onView(withId(R.id.loginUsernameET)).perform(typeText("larry.lobster@dal.ca"));
        onView(withId(R.id.loginPasswordET)).perform(typeText("RedLobster"));
        onView(withId(R.id.loginButton)).perform(click());

        //force it back to MainActivity
        //see if it will redirect back to page since logged in
        ActivityScenario.launch(MainActivity.class);

        //check if its still MainActivity
        onView(withId(R.id.employeeView)).check(matches(isDisplayed()));
    }
}
