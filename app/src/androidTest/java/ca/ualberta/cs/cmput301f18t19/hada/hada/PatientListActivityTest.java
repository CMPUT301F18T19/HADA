package ca.ualberta.cs.cmput301f18t19.hada.hada;

import org.junit.runner.RunWith;

import androidx.test.runner.AndroidJUnit4;

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
@RunWith(AndroidJUnit4.class)

public class PatientListActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void loginTestDoctor(){
        new UserController()
                .addCareProvider("doctor_patientlistactivity", "789789", "no@email.com");
        Espresso.onView(withId(R.id.mainActivityUsernameText))
                .perform(typeText("doctor_patientlistactivity"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.mainActivityDoctorLogin)).perform(click());

        new UserController().addPatient("testPatient", "asd", "asf");
        new UserController().addPatientToCareProvider("testPatient");

        //Since we manually added a patient to the patient list after we loaded the list, we go back and
        //log in again to force changes
        Espresso.pressBack();
        Espresso.onView(withId(R.id.mainActivityDoctorLogin)).perform(click());
    }

    @Test
    public void testPatientInList(){
        Espresso.onView(withText("testPatient")).check(matches(isDisplayed()));

    }

    @Test
    public void testUpdateList(){
        Espresso.onView(withId(R.id.patientListFloatingActionButton)).perform(click());
        new UserController().addPatient("testPatient2", "ad", "asda");
        Espresso.onView(withId(R.id.addPatientTextInput))
                .perform(typeText("testPatient2"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.addPatientSavePatientButton)).perform(click());
        Espresso.onView(withText("testPatient2")).check(matches(isDisplayed()));

    }

}
