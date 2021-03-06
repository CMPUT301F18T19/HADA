package ca.ualberta.cs.cmput301f18t19.hada.hada;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.ProblemController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.RecordController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.controller.UserController;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Problem;
import ca.ualberta.cs.cmput301f18t19.hada.hada.model.Record;
import ca.ualberta.cs.cmput301f18t19.hada.hada.ui.MainActivity;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.core.IsNot.not;
@RunWith(AndroidJUnit4.class)
public class ProblemListActivityTest {
    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    Problem problem = new Problem("testTitle", LocalDateTime.now(), "testDesc");
    final String timestamp = problem.getDate().format(formatter);

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

        new ProblemController().addProblem("testTitle",LocalDateTime.now(), "testDesc", "patient_problemlistactivity" );
        new RecordController().addRecord(new Record(), new ProblemController().getListOfProblems("patient_problemlistactivity").get(0).getFileId());


        //Since we manually added a problem to the patient after we loaded the list, we go back and
        //log in again to force changes
        Espresso.pressBack();
        Espresso.onView(withId(R.id.mainActivityPatientLogin)).perform(click());

    }
    @After
    public void deleteTestPatient(){
        new UserController().deletePatient("patient_problemlistactivity");
    }

    @Test
    public void testContainsProblemTitle(){
        Espresso.onView(withText("testTitle")).check(matches(isDisplayed()));
    }

    @Test
    public void testContainsProblemDate(){
        Espresso.onView(withText(timestamp)).check(matches(isDisplayed()));
    }

    @Test
    public void testContainsNumberOfRecords(){
        //Should display that it has 1 record
        Espresso.onView(withText("1")).check(matches(isDisplayed()));
    }

    @Test
    public void testAddNewProblem(){
        String newTitle = "newTitle";
        String newDesc = "Desc";
        Espresso.onView(withId(R.id.problemListFloatingButton)).perform(click());
        Espresso.onView(withId(R.id.addProblemTitle))
                .perform(typeText(newTitle), closeSoftKeyboard());
        Espresso.onView(withId(R.id.addProblemDescription)).perform(typeText(newDesc), closeSoftKeyboard());
        Espresso.onView(withId(R.id.addProblemButton)).perform(click());
        Espresso.onView(withText(newTitle)).check(matches(isDisplayed()));

    }

    @Test
    public void testChangeProblem(){
        String newTitle = "testTitle_2";
        Espresso.onData(anything()).inAdapterView(withId(R.id.problemListListView))
                .atPosition(0).perform(longClick());
        Espresso.onView(withId(R.id.editProblemTitle)).perform(replaceText(newTitle), closeSoftKeyboard());
        Espresso.onView(withId(R.id.editProblemSaveButton)).perform(click());
        Espresso.onView(withText(newTitle)).check(matches(isDisplayed()));


    }

    //Assuming deletion of problems works.
    @Test
    public void testDeleteProblem(){
        Espresso.onData(anything()).inAdapterView(withId(R.id.problemListListView))
                .atPosition(0).perform(longClick());
        Espresso.onView(withId(R.id.editProblemDelete)).perform(click());
        Espresso.onView(withText("testTitle")).check(doesNotExist());
    }

}
