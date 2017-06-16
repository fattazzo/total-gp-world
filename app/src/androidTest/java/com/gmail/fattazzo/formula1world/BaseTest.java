package com.gmail.fattazzo.formula1world;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.Suppress;

import com.gmail.fattazzo.formula1world.activity.home.HomeActivity;
import com.gmail.fattazzo.formula1world.activity.home.HomeActivity_;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author fattazzo
 *         <p/>
 *         date: 04/03/16
 */
@RunWith(AndroidJUnit4.class)
public class BaseTest {

    @Rule
    public ActivityTestRule<HomeActivity_> mActivityRule = new ActivityTestRule<>(HomeActivity_.class);
    private String[] menuEntry;
    private Context context;

    public Context getContext() {
        if (context == null) {
            context = InstrumentationRegistry.getTargetContext();
        }
        assertNotNull("Context nullo", context);

        return context;
    }

    public String[] getMenuEntry() {
        if (menuEntry == null) {
            //menuEntry = getContext().getResources().getStringArray(R.array.menu_title);
        }
        assertTrue("Menu entry non valide", menuEntry != null && menuEntry.length > 0);

        return menuEntry;
    }

    @Suppress
    @Test
    public void verificaMenuEntry() {

        assertTrue("Numero menu non esatto", getMenuEntry().length == AppMenu.values().length);
    }

    protected void selectMenu(AppMenu menu, AppMenuSwipeAction swipeAction) {

        onView(withId(R.id.drawer_layout)).perform(swipeRight());
        swipeAction.getAction().run();

        onView(withText(getMenuEntry()[menu.ordinal()])).check(matches(isDisplayed()));
        onView(withText(getMenuEntry()[menu.ordinal()])).perform(click());
    }

    protected void selectMenu(AppMenu menu) {
        selectMenu(menu, AppMenuSwipeAction.NONE);
    }

    public enum AppMenu {HOME, BOLL_PROB, BOLL_LOCALE, BOLL_SINTETICO, STAZIONI, NEVE_VALANGHE, RADAR, WEBCAM, IMPOSTAZIONI, GUIDA, ABOUT}

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