package com.example.myapplication;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

//for iteration 3 user story 2 testing of acceptance tests.
@RunWith(AndroidJUnit4.class)
public class EmployerJobsEspressoTest {

    private static final String employerKey = "tempEmployer";
    private static final String employeeKey = "tempEmployee";

    @Rule
    public ActivityScenarioRule<LoginActivity> myRule = new ActivityScenarioRule<>(LoginActivity.class);

    @BeforeClass
    public static void createTestUser() {
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
        DatabaseReference dbRef = FirebaseUtils.connectFirebase().getReference().child(FirebaseUtils.USERS_COLLECTION);

        dbRef.child(employeeKey).setValue(employee);
        dbRef.child(employerKey).setValue(employer);

    }

    @BeforeClass
    public static void setup() {
        Intents.init();
        Session.startSession(InstrumentationRegistry.getInstrumentation().getTargetContext());
    }

    @AfterClass
    public static void tearDown() {
        Intents.release();
        FirebaseDatabase.getInstance().getReference(FirebaseUtils.USERS_COLLECTION).child(employerKey).setValue(null);
        FirebaseDatabase.getInstance().getReference(FirebaseUtils.USERS_COLLECTION).child(employeeKey).setValue(null);
    }

    //jobs should be visible as a button for employers
    @Test
    public void testEmployerJobsButtonPresent() {
        ActivityScenario.launch(EmployerActivity.class);

        onView(withId(R.id.employerYourJobsButton)).check(matches(isDisplayed()));


    }


    @Test
    public void testEmployerJobsButtonRedirects() {
        ActivityScenario.launch(EmployerActivity.class);

        onView(withId(R.id.employerYourJobsButton)).perform(click());
        onView(withId(R.layout.job_employer)).check(matches(isDisplayed()));


    }

        // recommend potential employees to an employer for his or her submitted jobs

    //given that im an Employer when I create a job I should be able to see it under your jobs page
    @Test
    public void testOpenEmployerSearchButton() {

        ActivityScenario.launch(GoogleMapsActivity.class);

        onView(withId(R.id.setLocationBtn)).perform(click());

        onView(withId(R.id.registerFirstName)).perform(typeText("George\n"));
        onView(withId(R.id.registerLastName)).perform(typeText("Smith\n"));
        onView(withId(R.id.registerEmail)).perform(typeText("george.smith@dal.ca\n"));
        //Espresso.closeSoftKeyboard();
        onView(withId(R.id.registerPasswordET)).perform(typeText("password123\n"));
        onView(withId(R.id.registerUserSpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Employer"))).perform(click());
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.registerButton)).perform(click());

        onView(withId(R.id.employerSearchButton)).perform(click());
        //intended(hasComponent(JobSearch.class.getName()));

    }


    //given that im an Employer when I create a job I should be able to see suggested employees
    //based on their rating

    //given that i'm an employer when I create a job I should be able to see suggested employees
    //based on their distance from me.


}
