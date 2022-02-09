package com.example.loginfinal;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
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
    public IntentsTestRule<LoginActivity> myIntentRule = new IntentsTestRule<>(LoginActivity.class);
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
    @Test
    public void testSignUpButtonClick() {
        onView(withId(R.id.signup)).perform(click()).check(matches(isDisplayed()));
    }
    /***sign up**/
    @Test
    public void testSignUpActivity() {
        onView(withId(R.id.signup)).perform(click());
        intended(hasComponent(Register.class.getName()));
    }
    @Test
    public void checkUserNameIsEmpty() {
        onView(withId(R.id.username)).perform(typeText(""));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.status)).check(matches(withText(R.string.name_empty)));
    }

    @Test
    public void checkPasswordIsEmpty() {
        onView(withId(R.id.username)).perform(typeText("123456"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText(""), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.status)).check(matches(withText(R.string.pwd_empty)));
    }
    @Test
    public void login() {
        onView(withId(R.id.signup)).perform(click());
        intended(hasComponent(Register.class.getName()));
    }
}