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


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import ca.ualberta.cs.cmput301f18t19.hada.hada.ui.AddRecordActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;


/**
 * UI and Intent tests for AddRecordActivity
 * @author Alex Li
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddRecordActivityTest {

    @Rule
    public ActivityTestRule<AddRecordActivity> mActivityRule =
            new ActivityTestRule<>(AddRecordActivity.class);

    /**
     * test for properly displayed title
     */
    @Test
    public void testTitleEntry() {
        onView(withId(R.id.addRecordActivityTitle))
                .perform(typeText("test record title"))
                .check(matches(withText("test record title")));
    }

    /**
     * test for properly displayed comment
     */
    @Test
    public void testCommentEntry() {
        onView(withId(R.id.addRecordActivityComment))
                .perform(typeText("test record comment"))
                .check(matches(withText("test record comment")));
    }

}