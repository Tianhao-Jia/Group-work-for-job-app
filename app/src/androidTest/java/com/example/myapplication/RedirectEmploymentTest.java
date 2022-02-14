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

import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class RedirectEmploymentTest {

    @Rule
    public ActivityScenarioRule rule  = new ActivityScenarioRule<>(RegisterUser.class);

    @Before
    public void teardown(){
        FirebaseDatabase.getInstance().getReference("users").setValue(null);
    }


    /**
     * US3-AT1:
     *
     * Given that the User is an Employer, when the user enters the employee Activity,
     * then they should be redirected to the Employer Activity.
     */
    @Test
    public void testEmployerOnWrongPage() {

        onView(withId(R.id.registerFirstName)).perform(typeText("Queen"));
        onView(withId(R.id.registerLastName)).perform(typeText("Elizabeth"));
        onView(withId(R.id.registerEmail)).perform(typeText("her.majesty@dal.ca"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.registerPasswordET)).perform(typeText("password123"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.registerUserType)).perform(typeText("Employer"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.registerButton)).perform(click());

        ActivityScenario.launch(LoginActivity.class);

        onView(withId(R.id.loginUsernameET)).perform(typeText("her.majesty@dal.ca"));
        onView(withId(R.id.loginPasswordET)).perform(typeText("password123"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(click());


        ActivityScenario.launch(EmployeeActivity.class);

        onView(withId(R.id.registerUser)).check(matches(isDisplayed()));


    }



    /**
     * US3-AT2:
     *
     * Given that the User is an Employee, when the user enters the employer Activity,
     * then they should be redirected to the Employee Activity.
     */
    @Test
    public void testEmployeeOnWrongPage() {

        onView(withId(R.id.registerFirstName)).perform(typeText("Shrek"));
        onView(withId(R.id.registerLastName)).perform(typeText("Ogre"));
        onView(withId(R.id.registerEmail)).perform(typeText("shrek.orge@dal.ca"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.registerPasswordET)).perform(typeText("password123"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.registerUserType)).perform(typeText("Employee"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.registerButton)).perform(click());

        ActivityScenario.launch(LoginActivity.class);

        onView(withId(R.id.loginUsernameET)).perform(typeText("shrek.ogre@dal.ca"));
        onView(withId(R.id.loginPasswordET)).perform(typeText("password123"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(click());


        ActivityScenario.launch(EmployerActivity.class);

        onView(withId(R.id.registerUser)).check(matches(isDisplayed()));
    }

    /**
     * US3-AT3:
     *
     * Given that the User is an Employee, when the User enters the Employee activity,
     * then they should be allowed to stay on the Employee Activity.
     */
    @Test
    public void testEmployerOnRightPage() {

        onView(withId(R.id.registerFirstName)).perform(typeText("Queen"));
        onView(withId(R.id.registerLastName)).perform(typeText("Elizabeth"));
        onView(withId(R.id.registerEmail)).perform(typeText("her.majesty@dal.ca"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.registerPasswordET)).perform(typeText("password123"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.registerUserType)).perform(typeText("Employer"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.registerButton)).perform(click());

        onView(withId(R.id.employerView)).check(matches(isDisplayed()));

    }

    /**
     * US3-AT4:
     *
     * Given that the User is an Employer, when the User enters the Employer activity,
     * then they should be allowed to stay on the Employer Activity.
     */
    @Test
    public void testEmployeeOnRightPage() {

        onView(withId(R.id.registerFirstName)).perform(typeText("Shrek"));
        onView(withId(R.id.registerLastName)).perform(typeText("Ogre"));
        onView(withId(R.id.registerEmail)).perform(typeText("shrek.orge@dal.ca"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.registerPasswordET)).perform(typeText("password123"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.registerUserType)).perform(typeText("Employee"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.registerButton)).perform(click());

        onView(withId(R.id.employeeView)).check(matches(isDisplayed()));

    }

}
