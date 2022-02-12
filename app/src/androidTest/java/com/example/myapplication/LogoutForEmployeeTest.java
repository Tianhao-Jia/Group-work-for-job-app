package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.AfterClass;
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
        assertEquals("com.example.loginfinal", appContext.getPackageName());
    }

    @Test
    // run isolate
    public void logOutWithIntent() {
        onView(withId(R.id.employeeLogoutButton)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void testLogOutSp() {
        onView(withId(R.id.employeeLogoutButton)).perform(click());
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SharedPreferences sharedPref = appContext.getSharedPreferences("pref", Context.MODE_PRIVATE);
        assertEquals("", sharedPref.getString("Key_email",""));
    }






}
