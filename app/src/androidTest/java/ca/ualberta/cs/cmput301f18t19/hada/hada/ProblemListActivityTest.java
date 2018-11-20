package ca.ualberta.cs.cmput301f18t19.hada.hada;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.User;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.UserController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.ui.MainActivity;
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
@RunWith(AndroidJUnit4.class)
public class ProblemListActivityTest {
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
        Problem problem = new Problem("testTitle", LocalDateTime.now(), "testDesc");
        new UserController().addProblemToPatient(new Problem("testTitle", LocalDateTime.now(), "testDesc"));

        //Since we manually added a problem to the patient after we loaded the list, we go back and
        //log in again to force changes
        Espresso.pressBack();
        Espresso.onView(withId(R.id.mainActivityPatientLogin)).perform(click());

    }
    @Test
    public void containsProblem(){
        Espresso.onView(withText("testTitle"));
    }


}
