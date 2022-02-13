package com.example.myapplication;

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

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

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

        // Creating a HashMap of user information to store on firebase
        Map<String, Object> employee = new HashMap<>();
        employee.put("firstName", "Test");
        employee.put("lastName", "Employee");
        employee.put("email", "testEmployee@dal.ca");
        employee.put("userType", "Employee");
        employee.put("password", "1234");
        employee.put("loginState", false);

        Map<String, Object> employer = new HashMap<>();
        employer.put("firstName", "Test");
        employer.put("lastName", "Employer");
        employer.put("email", "testEmployer@dal.ca");
        employer.put("userType", "Employer");
        employer.put("password", "1234");
        employer.put("loginState", false);

        // Getting an instance of the firebase realtime database
        DatabaseReference dbRef = FirebaseDatabase.getInstance("https://quick-cash-55715-default-rtdb.firebaseio.com/")
                .getReference()
                .child("users");

        dbRef.push().setValue(employee);
        dbRef.push().setValue(employer);
    }

    @AfterClass
    public static void tearDown() {
        Intents.release();
        FirebaseDatabase.getInstance().getReference("users").setValue(null);

    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.myapplication", appContext.getPackageName());
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

    /** TODO: Currently failing */
//    @Test
//    // the information have to in the realtime database
//    public void login() {
//
//        ActivityScenario.launch(RegisterUser.class);
//        onView(withId(R.id.registerFirstName)).perform(typeText("George"));
//        onView(withId(R.id.registerLastName)).perform(typeText("Smith"));
//        onView(withId(R.id.registerEmail)).perform(typeText("george.smith@dal.ca"));
//        Espresso.closeSoftKeyboard();
//        onView(withId(R.id.registerPasswordET)).perform(typeText("123abc123"));
//        Espresso.closeSoftKeyboard();
//        onView(withId(R.id.registerUserType)).perform(typeText("Employee"));
//        Espresso.closeSoftKeyboard();
//        onView(withId(R.id.registerButton)).perform(click());
//
//
//        ActivityScenario.launch(LoginActivity.class);
//        onView(withId(R.id.loginUsernameET)).perform(typeText("george.smith@dal.ca"));
//        onView(withId(R.id.loginPasswordET)).perform(typeText("123abc123"));
//        Espresso.closeSoftKeyboard();
//        onView(withId(R.id.loginButton)).perform(click());
//        intended(hasComponent(EmployeeActivity.class.getName()));
//    }

    /**
     * US6-AT1:
     * Given that I am an employee, when I login to the app then I should see a map interface unique
     * to employees.
     */
    @Test
    public void checkLandingPageEmployee() {
        onView(withId(R.id.loginUsernameET)).perform(typeText("testEmployee@dal.ca"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.loginPasswordET)).perform(typeText("1234"), ViewActions.closeSoftKeyboard());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.employeeView)).check(matches(isDisplayed()));
    }

    /**
     * US6-AT2:
     * Given that I am an employer, when I login to the app then I should see an interface unique to
     * employers.
     */
    @Test
    public void checkLandingPageEmployer() {
        onView(withId(R.id.loginUsernameET)).perform(typeText("testEmployer@dal.ca"), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.loginPasswordET)).perform(typeText("1234"), ViewActions.closeSoftKeyboard());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.employerView)).check(matches(isDisplayed()));
    }

}