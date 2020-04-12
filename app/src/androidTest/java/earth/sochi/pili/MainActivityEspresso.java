package earth.sochi.pili;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.drm.DrmStore;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.google.common.collect.Iterables;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import earth.sochi.pili.data.PointRoomDatabase;
import earth.sochi.pili.data.PointViewModel;
import earth.sochi.pili.data.db.PointsDao;
import earth.sochi.pili.data.model.Point;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.init;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static java.util.concurrent.CompletableFuture.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityEspresso {
    private PointsDao pointDao;
    private PointRoomDatabase db;
    private final int ID_POINT = 1,ID_3=3,LAST_ITEM = 9;
    private final String DESCRIPTION_3= "Дольмен расположен возле музея города " +
            "Сочи. Дольмен состоит из нескольких плит." +
            "  Перемещен из района Лазаревского.", TAG="TMA";
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);
    public IntentsTestRule<PointActivity> intentPointActivity =
            new IntentsTestRule<>(PointActivity.class);

    @Before
    public void openDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, PointRoomDatabase.class).build();
        pointDao = db.pointDao();
    }
    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void getDataFromDB() throws Exception {

        Point point = pointDao.getPointById(ID_POINT);
        assertThat("points not null",point.getDescription(),notNullValue());
        assertThat("Point ID",point.getId(), equalTo(ID_POINT));

    }

    @Test
    public void checkRecyclerView() {
        onView (ViewMatchers.withId(R.id.points_recyclerview))
                .perform(RecyclerViewActions.scrollToPosition(ID_3));
        onView(ViewMatchers.withId(R.id.name))
                .check(matches(equalTo(DESCRIPTION_3)));
    }
    @Test
    public void recyclerView_Scrolls() {
        onView(ViewMatchers.withId(R.id.points_recyclerview))
                .perform(RecyclerViewActions.scrollToPosition(LAST_ITEM));
        onView(ViewMatchers.withId(R.id.points_recyclerview))
                .check(matches(isDisplayed()));
    }
    @Test
    public void testPointActivityIntent() {
        onView(ViewMatchers.withId(R.id.points_recyclerview))
                .perform(RecyclerViewActions.scrollToPosition(3));
        onView(ViewMatchers.withId(R.id.points_recyclerview))
                .perform(click());
        Intent intent = new Intent();
        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(DrmStore.Action.DEFAULT,intent);

      /*  assertThat(intent).extras().containts("description");
        assertThat(intent).extras().string("description").isEqualTo(DESCRIPTION_3);
        assertThat(intent).extras().containts("latitude");
        assertThat(intent).extras().string("latitude").isEqualTo(0.0);
        assertThat(intent).extras().containts("longitude");
        assertThat(intent).extras().string("longitude").isEqualTo(0.0);
        */
        intending(toPackage(PointActivity.class.getName()));
        onView(ViewMatchers.withId(R.id.photo)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.description)).check(matches(isDisplayed()));
    }
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("earth.sochi.pili", appContext.getPackageName());
    }

}
