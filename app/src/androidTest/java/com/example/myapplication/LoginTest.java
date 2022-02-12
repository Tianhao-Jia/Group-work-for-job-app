package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LoginTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> myRule = new ActivityScenarioRule<>(LoginActivity.class);
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
        assertEquals("com.example.loginfinal", appContext.getPackageName());
    }
    /***sign up**/

    /***sign up**/
    @Test
    public void testSignUpActivity() {
        onView(withId(R.id.loginToSignupButton)).perform(click());
        intended(hasComponent(RegisterUser.class.getName()));
    }
    @Test
    public void checkUserNameIsEmpty() {
        onView(withId(R.id.loginUsernameET)).perform(typeText(""));
        onView(withId(R.id.loginButton)).perform(click());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.loginStatus)).check(matches(withText("username is empty")));
    }

    @Test
    public void checkPasswordIsEmpty() {
        onView(withId(R.id.loginUsernameET)).perform(typeText("123456"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.loginPasswordET)).perform(typeText(""), ViewActions.closeSoftKeyboard());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.loginStatus)).check(matches(withText("password is empty")));
    }
    @Test
    // the information have to in the realtime database
    public void login() {

        ActivityScenario.launch(RegisterUser.class);
        onView(withId(R.id.loginUsernameET)).perform(typeText("tn608503@dal.ca"));
        onView(withId(R.id.loginPasswordET)).perform(typeText("123456"));
        onView(withId(R.id.login)).perform(typeText("tn608503@dal.ca"));



        onView(withId(R.id.loginUsernameET)).perform(typeText("tn608503@dal.ca"));
        onView(withId(R.id.loginPasswordET)).perform(typeText("123456"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(click());
        intended(hasComponent(EmployeeActivity.class.getName()));
    }
}