/* CMPUT 301 - Fall 2018
 *
 * Version 1.0
 *
 * 2018-11-20
 *
 * This is a group project for CMPUT 301 course at the University of Alberta
 * Copyright (C) 2018  Austin Goebel, Anders Johnson, Alex Li,
 * Cristopher Penner, Joseph Potentier-Neal, Jason Robock
 */
package ca.ualberta.cs.cmput301f18t19.hada.hada;


import android.widget.ArrayAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;
import java.util.ArrayList;

import androidx.test.espresso.Espresso;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.ProblemController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.RecordController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.UserController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;
import ca.ualberta.cs.cmput301f18t19.hada.hada.ui.AddRecordActivity;
import ca.ualberta.cs.cmput301f18t19.hada.hada.ui.MainActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.not;


/**
 * UI and Intent tests for AddRecordActivity
 * @author Joe Potentier
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddRecordActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void loginTestPatient(){
        new UserController()
                .addPatient("Intent_Patient_Name", "789789", "no@email.com");
        Espresso.onView(withId(R.id.mainActivityUsernameText))
                .perform(typeText("Intent_Patient_Name"), closeSoftKeyboard());
        new ProblemController()
                .addProblem("Intent_Problem_Title", LocalDateTime.now(), "Intent_description", "Intent_Patient_Name");
        Espresso.onView(withId(R.id.mainActivityPatientLogin)).perform(click());
        Espresso.onView(withText(containsString("Intent_Problem_Title"))).perform(click());
        Espresso.onView(withId(R.id.viewProblemAddRecordButton)).perform(click());
    }

    @After
    public void deleteTestPatient(){
        new UserController().deletePatient("Intent_Patient_Name");
    }

    @Test
    public void testAddRecordWithNothing(){
        Espresso.onView(withId(R.id.addRecordActivitySaveButton)).perform(click());
        //Courtesy of stackoverflow user: https://stackoverflow.com/users/1540854/jeprubio - Answer: https://stackoverflow.com/a/39032655/10454730
        Espresso.onData(anything())
                .inAdapterView(withId(R.id.viewProblemRecordsList))
                .atPosition(0).perform(click()); //If it is able to click, then the record must exist. Cannot check based on date displayed.
    }

    @Test
    public void testAddRecordWithTitle(){
        Espresso.onView(withId(R.id.addRecordActivityTitle)).perform(typeText("Intent_Record_Title"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.addRecordActivitySaveButton)).perform(click());
        Espresso.onView(withText(containsString("Intent_Record_Title"))).check(matches(isDisplayed()));
    }

    @Test
    public void testAddRecordWithComment(){
        Espresso.onView(withId(R.id.addRecordActivityComment)).perform(typeText("Intent_Record_Comment"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.addRecordActivitySaveButton)).perform(click());
        //Courtesy of stackoverflow user: https://stackoverflow.com/users/1540854/jeprubio - Answer: https://stackoverflow.com/a/39032655/10454730
        Espresso.onData(anything())
                .inAdapterView(withId(R.id.viewProblemRecordsList))
                .atPosition(0).perform(click());
        Espresso.onView(withText("Intent_Record_Comment")).check(matches(isDisplayed()));
    }



}