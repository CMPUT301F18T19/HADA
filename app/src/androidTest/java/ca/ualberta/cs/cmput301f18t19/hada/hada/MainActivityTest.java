package ca.ualberta.cs.cmput301f18t19.hada.hada;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.UserController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.User;
import ca.ualberta.cs.cmput301f18t19.hada.hada.ui.MainActivity;


import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;


@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp(){
        new UserController().addPatient("intenttestpatient", "780-867-5309", "email@email.com");
        new UserController().addCareProvider("intenttestdoctor", "7", "email");
    }

    @After
    public void deleteTestStuff(){
        new UserController().deletePatient("intenttestpatient");
        new UserController().deleteCareProvider("intenttestdoctor");
    }

    @Test
    public void testNoSavedUser(){
        Espresso.onView(withId(R.id.mainActivityUsernameText))
                .perform(typeText("Hello"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.mainActivityPatientLogin)).perform(click());

        checkToastExists("Username does not exist. Create a new user instead?");

    }

    @Test
    public void testValidPatientLogin(){
        Espresso.onView(withId(R.id.mainActivityUsernameText))
                .perform(typeText("intenttestpatient"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.mainActivityPatientLogin)).perform(click());
        Espresso.onView(withId(R.id.problemListUsername)).check(matches(isDisplayed()));

    }

    @Test
    public void testValidCareProviderLogin(){
        Espresso.onView(withId(R.id.mainActivityUsernameText))
                .perform(typeText("intenttestdoctor"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.mainActivityPatientLogin)).perform(click());
        Espresso.onView(withId(R.id.patientListUserSettingsButton)).check(matches(isDisplayed()));

    }

    @Test
    public void testValidShortCodeLogin(){
        String shortCode = new UserController().getPatient("intenttestpatient").getShortCode();
        Espresso.onView(withId(R.id.mainActivityUsernameText))
                .perform(typeText(shortCode), closeSoftKeyboard());
        Espresso.onView(withId(R.id.mainActivityShortCodeRadioButton)).perform(click());
        Espresso.onView(withId(R.id.mainActivityPatientLogin)).perform(click());
        Espresso.onView(withId(R.id.problemListUsername)).check(matches(isDisplayed()));
    }

    @Test
    public void testInvalidShortCodeLogin(){
        Espresso.onView(withId(R.id.mainActivityUsernameText))
                .perform(typeText("notashortcode"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.mainActivityShortCodeRadioButton)).perform(click());
        Espresso.onView(withId(R.id.mainActivityPatientLogin)).perform(click());
        checkToastExists("Shortcode not valid. Try again.");
    }

    @Test
    public void testNoInput(){
        Espresso.onView(withId(R.id.mainActivityPatientLogin)).perform(click());
        checkToastExists("Username does not exist. Create a new user instead?");

    }

    public void checkToastExists(String message){
        //Thanks to user StefanTo on StackOverflow https://stackoverflow.com/questions/28390574/checking-toast-message-in-android-espresso#comment56063447_28606603
        Espresso.onView(withText(message)).inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));

    }
    public void checkToastDoesNotExist(String message){
        //Thanks to user StefanTo on StackOverflow https://stackoverflow.com/questions/28390574/checking-toast-message-in-android-espresso#comment56063447_28606603
        Espresso.onView(withText(message)).inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView()))).check(doesNotExist());

    }
}
