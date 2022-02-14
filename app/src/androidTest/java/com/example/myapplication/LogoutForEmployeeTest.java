package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.firebase.database.FirebaseDatabase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LogoutForEmployeeTest {
    @Rule
    public ActivityScenarioRule<EmployeeActivity> myRule = new ActivityScenarioRule<>(EmployeeActivity.class);

    @BeforeClass
    public static void setup() {
        Intents.init();
    }

    @AfterClass
    public static void tearDown() {
        Intents.release();
    }
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.myapplication", appContext.getPackageName());
    }
    /**
     * US4-AT1:
     *
     * Register one user as an employee and then try click the logout button and test if it work.
     */

    @Test
    // run isolate
    public void logOutWithIntent() {
        ActivityScenario.launch(RegisterUser.class);
        onView(withId(R.id.registerFirstName)).perform(typeText("George"));
        onView(withId(R.id.registerLastName)).perform(typeText("Smith"));
        onView(withId(R.id.registerEmail)).perform(typeText("george.smith@dal.ca"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.registerPasswordET)).perform(typeText("123abc123"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.registerUserType)).perform(typeText("Employee"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.registerButton)).perform(click());


        ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.loginUsernameET)).perform(typeText("george.smith@dal.ca"));
        onView(withId(R.id.loginPasswordET)).perform(typeText("123abc123"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(click());


        onView(withId(R.id.employeeLogoutButton)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }

    /**
     * US4-AT1:
     *
     * test the logout button can worked after one user registered
     */

    @Test
    public void testLogOutSp() {
        ActivityScenario.launch(RegisterUser.class);
        onView(withId(R.id.registerFirstName)).perform(typeText("George"));
        onView(withId(R.id.registerLastName)).perform(typeText("Smith"));
        onView(withId(R.id.registerEmail)).perform(typeText("george.smith@dal.ca"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.registerPasswordET)).perform(typeText("123abc123"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.registerUserType)).perform(typeText("Employee"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.registerButton)).perform(click());


        ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.loginUsernameET)).perform(typeText("george.smith@dal.ca"));
        onView(withId(R.id.loginPasswordET)).perform(typeText("123abc123"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(click());


        onView(withId(R.id.employeeLogoutButton)).perform(click());
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SharedPreferences sharedPref = appContext.getSharedPreferences("pref", Context.MODE_PRIVATE);
        assertEquals("", sharedPref.getString("Key_email",""));
    }

    @After
    public void teardown(){
        FirebaseDatabase.getInstance().getReference("users").setValue(null);
    }


}
