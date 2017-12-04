package com.gmail.fattazzo.formula1world;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.Suppress;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.NumberPicker;

import com.gmail.fattazzo.formula1world.activity.home.HomeActivity_;
import com.gmail.fattazzo.formula1world.config.Config;
import com.gmail.fattazzo.formula1world.service.DataService;
import com.gmail.fattazzo.formula1world.service.DataService_;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.gmail.fattazzo.formula1world.actions.WaitAction.waitFor;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author fattazzo
 *         <p/>
 *         date: 04/03/16
 */
@RunWith(AndroidJUnit4.class)
public class BaseTest {

    static {
        Config.Companion.setAnimationEnabled(false);
    }

    @Rule
    public ActivityTestRule<HomeActivity_> mActivityRule = new ActivityTestRule<>(HomeActivity_.class);
    private String[] menuEntry;
    private Context context;

    @Suppress
    @Test
    public void testInit() {
        assertTrue(!Config.Companion.getAnimationEnabled());
    }

    public Context getContext() {
        if (context == null) {
            context = InstrumentationRegistry.getTargetContext();
        }
        assertNotNull("Context nullo", context);

        return context;
    }

    public void selectSeason(int season) {
        onView(withId(R.id.toolbar)).perform(click());

        ViewInteraction numPicker = onView(withId(R.id.numberPicker));
        numPicker.perform(setNumberOfPicker(season));

        onView(withId(R.id.buttonSelect)).perform(click());
    }

    private ViewAction setNumberOfPicker(final int num) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                NumberPicker np = (NumberPicker) view;
                np.setValue(num);

            }

            @Override
            public String getDescription() {
                return "Set the passed number into the NumberPicker";
            }

            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(NumberPicker.class);
            }
        };
    }

    protected int getLastAvailableSeason() {
        DataService dataService = DataService_.getInstance_(getContext());
        List<Integer> seasons = dataService.getAvailableSeasons();
        return TestConfig.getEndYear() == -1 ? seasons.get(0) : TestConfig.getEndYear();
    }

    protected void viewNotDisplayed(ViewInteraction view) {
        try {
            view.check(matches(not(isDisplayed())));
        } catch (Exception e) {
            view.check(doesNotExist());
        }
    }

    public void rotatePortrait() {
        mActivityRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //onView(isRoot()).perform(orientationPortrait());
    }

    public void rotateLandscape() {
        mActivityRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //onView(isRoot()).perform(orientationLandscape());
    }

    protected void swipeViewPager(int viewPagerResId, int[] sectionTextResId) {
        onView(isRoot()).perform(waitFor(100));

        for (int sectionId : sectionTextResId) {
            boolean ok = false;
            int count = 0;

            while (!ok && count <= 30) {
                try {
                    onView(allOf(withText(sectionId), isDisplayed())).perform(click());
                    onView(withId(viewPagerResId)).check(matches(hasDescendant(withText(sectionId))));
                    ok = true;
                } catch (Exception e) {
                    ok = false;
                }
                count++;
            }

            ////onView(allOf(withText(sectionId), isDescendantOfA(withId(viewPagerResId)))).perform(click());
        }

    }

    public enum AppMenuSwipeAction {

        NONE(new Runnable() {
            @Override
            public void run() {
            }
        }), UP(new Runnable() {
            @Override
            public void run() {
                onView(withId(R.id.drawer_layout)).perform(swipeUp());
            }
        }), DOWN(new Runnable() {
            @Override
            public void run() {
                onView(withId(R.id.drawer_layout)).perform(swipeDown());
            }
        });

        private Runnable action;

        AppMenuSwipeAction(Runnable action) {
            this.action = action;
        }

        /**
         * @return the action
         */
        public Runnable getAction() {
            return action;
        }
    }
}