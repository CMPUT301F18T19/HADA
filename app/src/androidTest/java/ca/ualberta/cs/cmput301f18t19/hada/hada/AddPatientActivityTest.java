package ca.ualberta.cs.cmput301f18t19.hada.hada;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.UserController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.User;
import ca.ualberta.cs.cmput301f18t19.hada.hada.ui.MainActivity;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

public class AddPatientActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void loginTestDoctor() {
        new UserController()
                .addCareProvider("intenttestdoctor", "789789", "no@email.com");
        Espresso.onView(withId(R.id.mainActivityUsernameText))
                .perform(typeText("intenttestdoctor"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.mainActivityPatientLogin)).perform(click());
        new UserController()
                .addPatient("intenttestpatient", "asd", "asf");


    }
    @After
    public void deleteTestDoctor(){
        new UserController().deleteCareProvider("intenttestdoctor");
        new UserController().deletePatient("intenttestpatient");
    }

    @Test
    public void testPatientAddToList(){
        String shortCode = new UserController().getPatient("intenttestpatient").getShortCode();
        Espresso.onView(withId(R.id.patientListFloatingActionButton)).perform(click());
        Espresso.onView(withId(R.id.addPatientTextInput)).perform(typeText(shortCode),closeSoftKeyboard());
        Espresso.onView(withId(R.id.addPatientSavePatientButton)).perform(click());
        Espresso.onView(withText("intenttestpatient")).check(matches(isDisplayed()));
    }

    @Test
    public void testIncorrectShortCode(){
        String shortCode = "incorrect";
        Espresso.onView(withId(R.id.patientListFloatingActionButton)).perform(click());
        Espresso.onView(withId(R.id.addPatientTextInput)).perform(typeText(shortCode),closeSoftKeyboard());
        Espresso.onView(withId(R.id.addPatientSavePatientButton)).perform(click());
        checkToastExists("Patient does not exist. Check spelling?");

    }

    public void checkToastExists(String message){
        //Thanks to user StefanTo on StackOverflow https://stackoverflow.com/questions/28390574/checking-toast-message-in-android-espresso#comment56063447_28606603
        Espresso.onView(withText(message)).inRoot(withDecorView(not(mainActivityActivityTestRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));

    }



}