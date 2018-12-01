package ca.ualberta.cs.cmput301f18t19.hada.hada;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.UserController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.ui.MainActivity;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class AddPatientActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void loginTestDoctor() {
        new UserController()
                .addCareProvider("doctor_patientlistactivity", "789789", "no@email.com");
        Espresso.onView(withId(R.id.mainActivityUsernameText))
                .perform(typeText("doctor_patientlistactivity"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.mainActivityPatientLogin)).perform(click());
        new UserController()
                .addPatient("testPatient", "asd", "asf");


    }

    @Test
    public void testPatientAddToList(){
        Espresso.onView(withId(R.id.patientListFloatingActionButton)).perform(click());
        Espresso.onView(withId(R.id.addPatientTextInput)).perform(typeText("testPatient"),closeSoftKeyboard());
        Espresso.onView(withId(R.id.addPatientSavePatientButton)).perform(click());
        Espresso.onView(withText("testPatient")).check(matches(isDisplayed()));
    }



}