package ca.ualberta.cs.cmput301f18t19.hada.hada;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.User;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.UserController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.ui.NewUserActivity;

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
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.IsNot.not;


//Must delete Patient testString1 and CareProvider testString2 before testing.
@RunWith(AndroidJUnit4.class)
public class NewUserActivityTest {
    @Rule
    public ActivityTestRule<NewUserActivity> mActivityRule =
            new ActivityTestRule<>(NewUserActivity.class);


    @Test
    public void testNoInput(){
        Espresso.onView(withId(R.id.newUserConfirm)).perform(click());
        checkToastExists("Please select a user type.");
    }

    @Test
    public void testRadioButtonOnly(){
        Espresso.onView(withId(R.id.newUserPatientRadioButton)).perform(click());
        Espresso.onView(withId(R.id.newUserConfirm)).perform(click());
        checkToastExists("Please enter a User ID, Phone, and Email.");

        Espresso.onView(withId(R.id.newUserDoctorRadioButton)).perform(click());
        Espresso.onView(withId(R.id.newUserConfirm)).perform(click());
        checkToastExists("Please enter a User ID, Phone, and Email.");
    }

    @Test
    public void testUserIDContainsSpace(){
        Espresso.onView(withId(R.id.newUserEnterUsername))
                .perform(typeText("Hello   "), closeSoftKeyboard());
        Espresso.onView(withId(R.id.newUserEnterPhone))
                .perform(typeText("780-867-5309"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.newUserEnterEmail))
                .perform(typeText("hello@email.com"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.newUserPatientRadioButton)).perform(click());
        Espresso.onView(withId(R.id.newUserConfirm)).perform(click());
        checkToastExists("User ID cannot contain spaces.");
    }

    @Test
    public void testLessThan8Chars(){
        Espresso.onView(withId(R.id.newUserEnterUsername))
                .perform(typeText("Hello"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.newUserEnterPhone))
                .perform(typeText("780-867-5309"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.newUserEnterEmail))
                .perform(typeText("hello@email.com"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.newUserPatientRadioButton)).perform(click());
        Espresso.onView(withId(R.id.newUserConfirm)).perform(click());
        checkToastExists("User ID must be at least 8 characters.");
    }

    //As of now, you need to delete the user testString1 before testing.
    //TODO: Implement deletePatient/deleteCareProvider in UserController
    @Test
    public void testAddNewPatient() {
        Espresso.onView(withId(R.id.newUserEnterUsername))
                .perform(typeText("testString1"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.newUserEnterPhone))
                .perform(typeText("780-867-5309"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.newUserEnterEmail))
                .perform(typeText("hello@email.com"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.newUserPatientRadioButton)).perform(click());
        Espresso.onView(withId(R.id.newUserConfirm)).perform(click());
        assertTrue(mActivityRule.getActivity().isFinishing());
    }

    @Test
    public void testInUse(){
        new UserController().addPatient("alreadytakenID", "asd", "asd");
        Espresso.onView(withId(R.id.newUserEnterUsername))
                .perform(typeText("alreadytakenID"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.newUserEnterPhone))
                .perform(typeText("780-867-5309"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.newUserEnterEmail))
                .perform(typeText("hello@email.com"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.newUserPatientRadioButton)).perform(click());
        Espresso.onView(withId(R.id.newUserConfirm)).perform(click());
        checkToastExists("User ID already in use.");
    }

    //As of now, you need to delete the user testString2 before testing.
    @Test
    public void testAddNewCareProvider(){
        Espresso.onView(withId(R.id.newUserEnterUsername))
                .perform(typeText("testString2"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.newUserEnterPhone))
                .perform(typeText("780-867-5309"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.newUserEnterEmail))
                .perform(typeText("hello@email.com"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.newUserDoctorRadioButton)).perform(click());
        Espresso.onView(withId(R.id.newUserConfirm)).perform(click());
        assertTrue(mActivityRule.getActivity().isFinishing());
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
