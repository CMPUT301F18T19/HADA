package ca.ualberta.cs.cmput301f18t19.hada.hada;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.UserController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.ui.MainActivity;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static org.hamcrest.core.IsNot.not;
@RunWith(AndroidJUnit4.class)
public class EditUserSettingsActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void loginTestPatient() {
        new UserController()
                .addPatient("patient_problemlistactivity", "789789", "no@email.com");
        Espresso.onView(withId(R.id.mainActivityUsernameText))
                .perform(typeText("patient_problemlistactivity"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.mainActivityPatientLogin)).perform(click());
        Espresso.onView(withId(R.id.problemListSettingsButton)).perform(click());
    }
    @Test
    public void testAutofill(){
        String email = new UserController().getPatient("patient_problemlistactivity").getEmailAddress();
        String userID = "patient_problemlistactivity";
        String phone = new UserController().getPatient("patient_problemlistactivity").getPhoneNumber();
        Espresso.onView(withText(email)).check(matches(isDisplayed()));
        Espresso.onView(withText(userID)).check(matches(isDisplayed()));
        Espresso.onView(withText(phone)).check(matches(isDisplayed()));
    }

    @Test
    public void testEmptyFields(){
        Espresso.onView(withId(R.id.editUserSettingsEditTextInput))
                .perform(replaceText(""), closeSoftKeyboard());
        Espresso.onView(withId(R.id.editUserSettingsContactNumber))
                .perform(replaceText(""), closeSoftKeyboard());
        Espresso.onView(withId(R.id.editUserSettingsSaveButton)).perform(click());
        checkToastExists("Fields cannot be left blank.");
    }

    @Test
    public void testNewEmail(){
        Espresso.onView(withId(R.id.editUserSettingsEditTextInput))
                .perform(replaceText("newEmail"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.editUserSettingsSaveButton)).perform(click());
        assert(new UserController().getPatient("patient_problemlistactivity").getEmailAddress() == "newEmail");
    }

    @Test
    public void testNewPhone(){
        Espresso.onView(withId(R.id.editUserSettingsContactNumber))
                .perform(replaceText("newPhone"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.editUserSettingsSaveButton)).perform(click());
        assert(new UserController().getPatient("patient_problemlistactivity").getPhoneNumber() == "newPhone");
    }
    public void checkToastExists(String message){
        //Thanks to user StefanTo on StackOverflow https://stackoverflow.com/questions/28390574/checking-toast-message-in-android-espresso#comment56063447_28606603
        Espresso.onView(withText(message)).inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));

    }
}