package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.database.FirebaseDatabase;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class JobSearchEspressoTest {

    @Rule
    public ActivityScenarioRule myRule =
            new ActivityScenarioRule(JobSearch.class);

    @BeforeClass
    public static void setup() {
        Intents.init();
        ActivityScenario.launch(RegisterUser.class);

        onView(withId(R.id.registerFirstName)).perform(typeText("George\n"));
        onView(withId(R.id.registerLastName)).perform(typeText("Smith\n"));
        onView(withId(R.id.registerEmail)).perform(typeText("george.smith@dal.ca\n"));
        onView(withId(R.id.registerPasswordET)).perform(typeText("password123\n"));
        onView(withId(R.id.registerUserType)).perform(typeText("Employer\n"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.createJob)).perform(click());

        onView(withId(R.id.createJobEmail)).perform(typeText("george.smith@dal.ca\n"));
        onView(withId(R.id.createJobTitle)).perform(typeText("Car Wash\n"));
        onView(withId(R.id.createJobDescription)).perform(typeText("Make my Hellcat shine\n"));
        onView(withId(R.id.createJobHourlyRate)).perform(typeText("15\n"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.createJobSubmitButton)).perform(click());
    }

    @AfterClass
    public static void tearDown() {
        Intents.release();
        FirebaseDatabase.getInstance().getReference("jobs").setValue(null);
        FirebaseDatabase.getInstance().getReference("users").setValue(null);
    }

    @Test
    public void searchNoJobsExist(){

        ActivityScenario.launch(EmployerActivity.class);

        onView(withId(R.id.employerSearchButton)).perform(click());

        onView(withId(R.id.searchJobButton)).perform(click());

        assertEquals(0,ViewJobAdapter.getHolderArrayList().size());


    }

    @Test
    public void searchJobsExistNoJobsInfoProvided(){

        ActivityScenario.launch(RegisterUser.class);

        onView(withId(R.id.registerFirstName)).perform(typeText("George\n"));
        onView(withId(R.id.registerLastName)).perform(typeText("Smith\n"));
        onView(withId(R.id.registerEmail)).perform(typeText("george.smith@dal.ca\n"));
        onView(withId(R.id.registerPasswordET)).perform(typeText("password123\n"));
        onView(withId(R.id.registerUserType)).perform(typeText("Employer\n"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.registerButton)).perform(click());



        //for some reason this one causes it to crash but it doesn't in the previous test.
        onView(withId(R.id.employerSearchButton)).perform(click());

        onView(withId(R.id.searchJobButton)).perform(click());

        //shows only the one job.
        assertEquals(1,ViewJobAdapter.getHolderArrayList().size());



    }

    @Test
    public void searchJobsExistJobsInfoProvidedEmail(){

        ActivityScenario.launch(RegisterUser.class);

        onView(withId(R.id.registerFirstName)).perform(typeText("George\n"));
        onView(withId(R.id.registerLastName)).perform(typeText("Smith\n"));
        onView(withId(R.id.registerEmail)).perform(typeText("george.smith@dal.ca\n"));
        onView(withId(R.id.registerPasswordET)).perform(typeText("password123\n"));
        onView(withId(R.id.registerUserType)).perform(typeText("Employer\n"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.registerButton)).perform(click());

        onView(withId(R.id.createJob)).perform(click());

        onView(withId(R.id.createJobEmail)).perform(typeText("george.smith@dal.ca"));
        onView(withId(R.id.createJobTitle)).perform(typeText("Car Wash"));
        onView(withId(R.id.createJobDescription)).perform(typeText("Make my Hellcat shine"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.createJobHourlyRate)).perform(typeText("15"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.createJobSubmitButton)).perform(click());

        onView(withId(R.id.createJob)).perform(click());

        onView(withId(R.id.createJobEmail)).perform(typeText("george.smith@dal.ca"));
        onView(withId(R.id.createJobTitle)).perform(typeText("Car Destroy"));
        onView(withId(R.id.createJobDescription)).perform(typeText("Make my Hellcat Dirty"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.createJobHourlyRate)).perform(typeText("1"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.createJobSubmitButton)).perform(click());



        //for some reason this one causes it to crash but it doesn't in the previous test.
        onView(withId(R.id.employerSearchButton)).perform(click());


        onView(withId(R.id.searchEmployerEmail)).perform(typeText("george.smith@dal.ca"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.searchJobButton)).perform(click());

        //shows two jobs.
        ArrayList<Job> jobArrayList = ViewJobAdapter.getJobArrayList();
        for (int i = 0; i<jobArrayList.size(); i++) {
            Job job = jobArrayList.get(i);
            String currentEmail = job.getEmployerEmail();
            assertEquals("george.smith@dal.ca", currentEmail);
        }

    }


    @Test
    public void searchJobsExistJobsInfoProvidedTitle(){

        ActivityScenario.launch(RegisterUser.class);

        onView(withId(R.id.registerFirstName)).perform(typeText("George\n"));
        onView(withId(R.id.registerLastName)).perform(typeText("Smith\n"));
        onView(withId(R.id.registerEmail)).perform(typeText("george.smith@dal.ca\n"));
        onView(withId(R.id.registerPasswordET)).perform(typeText("password123\n"));
        onView(withId(R.id.registerUserType)).perform(typeText("Employer\n"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.registerButton)).perform(click());

        onView(withId(R.id.createJob)).perform(click());

        onView(withId(R.id.createJobEmail)).perform(typeText("george.smith@dal.ca"));
        onView(withId(R.id.createJobTitle)).perform(typeText("Car Wash"));
        onView(withId(R.id.createJobDescription)).perform(typeText("Make my Hellcat shine"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.createJobHourlyRate)).perform(typeText("15"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.createJobSubmitButton)).perform(click());

        onView(withId(R.id.createJob)).perform(click());

        onView(withId(R.id.createJobEmail)).perform(typeText("george.smith@dal.ca"));
        onView(withId(R.id.createJobTitle)).perform(typeText("Car Destroy"));
        onView(withId(R.id.createJobDescription)).perform(typeText("Make my Hellcat Dirty"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.createJobHourlyRate)).perform(typeText("1"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.createJobSubmitButton)).perform(click());



        //for some reason this one causes it to crash but it doesn't in the previous test.
        onView(withId(R.id.employerSearchButton)).perform(click());


        onView(withId(R.id.searchJobTitle)).perform(typeText("Car Destroy"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.searchJobButton)).perform(click());

        //shows two jobs.
        ArrayList<Job> jobArrayList = ViewJobAdapter.getJobArrayList();
        for (int i = 0; i<jobArrayList.size(); i++) {
            Job job = jobArrayList.get(i);
            String currentTitle = job.getJobTitle();
            assertEquals("Car Destroy", currentTitle);
        }

    }

    @Test
    public void searchJobsExistJobsInfoProvidedHourlyRate(){

        ActivityScenario.launch(RegisterUser.class);

        onView(withId(R.id.registerFirstName)).perform(typeText("George\n"));
        onView(withId(R.id.registerLastName)).perform(typeText("Smith\n"));
        onView(withId(R.id.registerEmail)).perform(typeText("george.smith@dal.ca\n"));
        onView(withId(R.id.registerPasswordET)).perform(typeText("password123\n"));
        onView(withId(R.id.registerUserType)).perform(typeText("Employer\n"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.registerButton)).perform(click());

        onView(withId(R.id.createJob)).perform(click());

        onView(withId(R.id.createJobEmail)).perform(typeText("george.smith@dal.ca"));
        onView(withId(R.id.createJobTitle)).perform(typeText("Car Wash"));
        onView(withId(R.id.createJobDescription)).perform(typeText("Make my Hellcat shine"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.createJobHourlyRate)).perform(typeText("15"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.createJobSubmitButton)).perform(click());

        onView(withId(R.id.createJob)).perform(click());

        onView(withId(R.id.createJobEmail)).perform(typeText("george.smith@dal.ca"));
        onView(withId(R.id.createJobTitle)).perform(typeText("Car Destroy"));
        onView(withId(R.id.createJobDescription)).perform(typeText("Make my Hellcat Dirty"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.createJobHourlyRate)).perform(typeText("1"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.createJobSubmitButton)).perform(click());



        //for some reason this one causes it to crash but it doesn't in the previous test.
        onView(withId(R.id.employerSearchButton)).perform(click());


        onView(withId(R.id.searchHourlyRate)).perform(typeText("15"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.searchJobButton)).perform(click());

        //shows two jobs.
        ArrayList<Job> jobArrayList = ViewJobAdapter.getJobArrayList();
        for (int i = 0; i<jobArrayList.size(); i++) {
            Job job = jobArrayList.get(i);
            String currentRate = String.valueOf(job.getCompensation());
            assertEquals("15.0", currentRate);
        }

    }

    @Test
    public void searchJobsExistJobsInfoProvidedDescription(){

        ActivityScenario.launch(RegisterUser.class);

        onView(withId(R.id.registerFirstName)).perform(typeText("George\n"));
        onView(withId(R.id.registerLastName)).perform(typeText("Smith\n"));
        onView(withId(R.id.registerEmail)).perform(typeText("george.smith@dal.ca\n"));
        onView(withId(R.id.registerPasswordET)).perform(typeText("password123\n"));
        onView(withId(R.id.registerUserType)).perform(typeText("Employer\n"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.registerButton)).perform(click());

        onView(withId(R.id.createJob)).perform(click());

        onView(withId(R.id.createJobEmail)).perform(typeText("george.smith@dal.ca"));
        onView(withId(R.id.createJobTitle)).perform(typeText("Car Wash"));
        onView(withId(R.id.createJobDescription)).perform(typeText("Make my Hellcat shine"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.createJobHourlyRate)).perform(typeText("15"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.createJobSubmitButton)).perform(click());

        onView(withId(R.id.createJob)).perform(click());

        onView(withId(R.id.createJobEmail)).perform(typeText("george.smith@dal.ca"));
        onView(withId(R.id.createJobTitle)).perform(typeText("Car Destroy"));
        onView(withId(R.id.createJobDescription)).perform(typeText("Make my Hellcat Dirty"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.createJobHourlyRate)).perform(typeText("1"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.createJobSubmitButton)).perform(click());


        onView(withId(R.id.employerSearchButton)).perform(click());


        onView(withId(R.id.searchDescription)).perform(typeText("Make my Hellcat shine"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.searchJobButton)).perform(click());

        //shows two jobs.
        ArrayList<Job> jobArrayList = ViewJobAdapter.getJobArrayList();
        for (int i = 0; i<jobArrayList.size(); i++) {
            Job job = jobArrayList.get(i);
            String currentDesc = job.getDescription();
            assertEquals("Make my Hellcat shine", currentDesc);
        }

    }

    //user story 14 tests below.

    @Test
    public void searchJobsEmailSaved(){

        ActivityScenario.launch(RegisterUser.class);

        onView(withId(R.id.registerFirstName)).perform(typeText("George\n"));
        onView(withId(R.id.registerLastName)).perform(typeText("Smith\n"));
        onView(withId(R.id.registerEmail)).perform(typeText("george.smith@dal.ca\n"));
        onView(withId(R.id.registerPasswordET)).perform(typeText("password123\n"));
        onView(withId(R.id.registerUserType)).perform(typeText("Employer\n"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.registerButton)).perform(click());

        onView(withId(R.id.createJob)).perform(click());

        onView(withId(R.id.createJobEmail)).perform(replaceText("george.smith@dal.ca"));
        onView(withId(R.id.createJobTitle)).perform(typeText("Car Wash"));
        onView(withId(R.id.createJobDescription)).perform(typeText("Make my Hellcat shine"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.createJobHourlyRate)).perform(typeText("15"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.createJobSubmitButton)).perform(click());

        onView(withId(R.id.employerSearchButton)).perform(click());
        onView(withId(R.id.searchEmployerEmail)).perform(typeText("george.smith@dal.ca"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.searchJobButton)).perform(click());
        Espresso.pressBack();

        onView(withId(R.id.employerSearchButton)).perform(click());
        onView(withId(R.id.searchEmployerEmail)).check(matches(withText(containsString("george.smith@dal.ca"))));

    }


    @Test
    public void searchJobsTitleSaved(){

        ActivityScenario.launch(RegisterUser.class);

        onView(withId(R.id.registerFirstName)).perform(typeText("George\n"));
        onView(withId(R.id.registerLastName)).perform(typeText("Smith\n"));
        onView(withId(R.id.registerEmail)).perform(typeText("george.smith@dal.ca\n"));
        onView(withId(R.id.registerPasswordET)).perform(typeText("password123\n"));
        onView(withId(R.id.registerUserType)).perform(typeText("Employer\n"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.registerButton)).perform(click());

        onView(withId(R.id.createJob)).perform(click());

        onView(withId(R.id.createJobEmail)).perform(replaceText("george.smith@dal.ca"));
        onView(withId(R.id.createJobTitle)).perform(typeText("Car Wash"));
        onView(withId(R.id.createJobDescription)).perform(typeText("Make my Hellcat shine"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.createJobHourlyRate)).perform(typeText("15"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.createJobSubmitButton)).perform(click());

        onView(withId(R.id.employerSearchButton)).perform(click());
        onView(withId(R.id.searchJobTitle)).perform(typeText("Car Wash"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.searchJobButton)).perform(click());
        Espresso.pressBack();

        onView(withId(R.id.employerSearchButton)).perform(click());
        onView(withId(R.id.searchJobTitle)).check(matches(withText(containsString("Car Wash"))));

    }

    @Test
    public void searchJobsHourlyRateSaved(){

        onView(withId(R.id.registerButton)).perform(click());

        onView(withId(R.id.createJob)).perform(click());

        onView(withId(R.id.createJobEmail)).perform(replaceText("george.smith@dal.ca"));
        onView(withId(R.id.createJobTitle)).perform(typeText("Car Wash"));
        onView(withId(R.id.createJobDescription)).perform(typeText("Make my Hellcat shine"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.createJobHourlyRate)).perform(typeText("15"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.createJobSubmitButton)).perform(click());

        onView(withId(R.id.employerSearchButton)).perform(click());
        onView(withId(R.id.searchHourlyRate)).perform(typeText("15"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.searchJobButton)).perform(click());
        Espresso.pressBack();

        onView(withId(R.id.employerSearchButton)).perform(click());
        onView(withId(R.id.searchHourlyRate)).check(matches(withText(containsString("15"))));

    }

    @Test
    public void searchJobsDescriptionSaved(){

        ActivityScenario.launch(RegisterUser.class);

        onView(withId(R.id.registerFirstName)).perform(typeText("George\n"));
        onView(withId(R.id.registerLastName)).perform(typeText("Smith\n"));
        onView(withId(R.id.registerEmail)).perform(typeText("george.smith@dal.ca\n"));
        onView(withId(R.id.registerPasswordET)).perform(typeText("password123\n"));
        onView(withId(R.id.registerUserType)).perform(typeText("Employer\n"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.registerButton)).perform(click());

        onView(withId(R.id.createJob)).perform(click());

        onView(withId(R.id.createJobEmail)).perform(replaceText("george.smith@dal.ca"));
        onView(withId(R.id.createJobTitle)).perform(typeText("Car Wash"));
        onView(withId(R.id.createJobDescription)).perform(typeText("Make my Hellcat shine"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.createJobHourlyRate)).perform(typeText("15"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.createJobSubmitButton)).perform(click());

        onView(withId(R.id.employerSearchButton)).perform(click());
        onView(withId(R.id.searchDescription)).perform(typeText("Make my Hellcat shine"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.searchJobButton)).perform(click());
        Espresso.pressBack();

        onView(withId(R.id.employerSearchButton)).perform(click());
        onView(withId(R.id.searchDescription)).check(matches(withText(containsString("Make my Hellcat shine"))));

    }

}
