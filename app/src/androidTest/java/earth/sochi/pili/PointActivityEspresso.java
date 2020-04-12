package earth.sochi.pili;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasPackage;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
@LargeTest
public class PointActivityEspresso {
    @Rule
    public ActivityTestRule<PointActivity> pointActivityActivityTestRule =
            new ActivityTestRule<>(PointActivity.class);
    @Test
    public void  displayRowsValidData() {
        onView(ViewMatchers.withId(R.id.photo)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.description)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.coordinate)).check(matches(isDisplayed()));
    }
    @Test
    public void notNullData() {
        onView(ViewMatchers.withId(R.id.description))
                .check(matches(isDisplayed()));
    }
    @Test
    public void startMapIntent() {
        onView(ViewMatchers.withId(R.id.coordinate))
                .perform(click());
        intended(allOf(
                hasAction(Intent.ACTION_VIEW),
                hasPackage("com.google.android.apps.maps")
        ));
    }
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("earth.sochi.pili", appContext.getPackageName());
    }
}
