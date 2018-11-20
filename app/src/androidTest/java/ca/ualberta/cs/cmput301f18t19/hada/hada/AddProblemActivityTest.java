package ca.ualberta.cs.cmput301f18t19.hada.hada;

import android.widget.DatePicker;
import android.widget.TimePicker;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.UserController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.ui.MainActivity;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;
@RunWith(AndroidJUnit4.class)
public class AddProblemActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);
    @Before
    public void loginTestPatient(){
        new UserController()
                .addPatient("patient_problemlistactivity", "789789", "no@email.com");
        Espresso.onView(withId(R.id.mainActivityUsernameText))
                .perform(typeText("patient_problemlistactivity"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.mainActivityPatientLogin)).perform(click());
        Espresso.onView(withId(R.id.problemListFloatingButton)).perform(click());
    }

    @Test
    public void testNoInput(){
        Espresso.onView(withId(R.id.addProblemButton)).perform(click());
        checkToastExists("Please enter a description and title.");
    }

    @Test
    public void testSomeInput(){
        Espresso.onView(withId(R.id.addProblemTitle))
                .perform(typeText("testTitle"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.addProblemButton)).perform(click());
        checkToastExists("Please enter a description and title.");
    }

    @Test
    public void testNewProblemAutoTimestamp(){
        String testTitle = "testTitle";
        Espresso.onView(withId(R.id.addProblemTitle))
                .perform(typeText(testTitle), closeSoftKeyboard());
        Espresso.onView(withId(R.id.addProblemDescription))
                .perform(typeText("testDesc"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.addProblemButton)).perform(click());
        Espresso.onView(withText(testTitle)).check(matches(isDisplayed()));
    }

    @Test
    public void testChangeDate(){

        Espresso.onView(withId(R.id.changeDateButton)).perform(click());
        Espresso.onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(1998, 2, 11));
        Espresso.onView(withText("OK")).perform(click());
        Espresso.onView(withId(R.id.addProblemDate)).check(matches(withText(containsString("1998-02-11"))));

    }

    @Test
    public void testChangeTime(){
        Espresso.onView(withId(R.id.changeTimeButton)).perform(click());
        Espresso.onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(1, 1));
        Espresso.onView(withText("OK")).perform(click());
        Espresso.onView(withId(R.id.addProblemDate)).check(matches(withText(containsString("01:01"))));

    }

    @Test
    public void testNewProblemCustomTimestamp(){
        String testTitle = "testTitle";
        Espresso.onView(withId(R.id.addProblemTitle))
                .perform(typeText(testTitle), closeSoftKeyboard());
        Espresso.onView(withId(R.id.addProblemDescription))
                .perform(typeText("testDesc"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.changeDateButton)).perform(click());
        Espresso.onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(1998, 2, 11));
        Espresso.onView(withText("OK")).perform(click());
        Espresso.onView(withId(R.id.changeTimeButton)).perform(click());
        Espresso.onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(1, 1));
        Espresso.onView(withText("OK")).perform(click());
        Espresso.onView(withId(R.id.addProblemDate)).check(matches(withText(containsString("1998-02-11T01:01"))));
        Espresso.onView(withId(R.id.addProblemButton)).perform(click());
        Espresso.onView(withText(testTitle)).check(matches(isDisplayed()));

    }

    public void checkToastExists(String message){
        //Thanks to user StefanTo on StackOverflow https://stackoverflow.com/questions/28390574/checking-toast-message-in-android-espresso#comment56063447_28606603
        Espresso.onView(withText(message)).inRoot(withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView()))).check(matches(isDisplayed()));

    }
}
