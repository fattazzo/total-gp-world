package com.gmail.fattazzo.formula1world.fragments.current.constructors;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.view.Gravity;

import com.gmail.fattazzo.formula1world.BaseTest;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.matchers.Matchers;
import com.gmail.fattazzo.formula1world.utils.ImageUtils;
import com.gmail.fattazzo.formula1world.utils.ImageUtils_;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/**
 * @author fattazzo
 *         <p/>
 *         date: 15/06/17
 */
public class CurrentConstructorsFragmentTest extends BaseTest {

    private String[] teamsName = new String[]{"Ferrari", "Force India", "Haas F1 Team", "Manor Marussia", "McLaren", "Mercedes", "Red Bull", "Renault", "Sauber", "Toro Rosso", "Williams"};
    private String[] teamsColor = new String[]{"#c30000", "#3e1700", "#6c0000", "#92c0ff", "#635f60", "#00cfba", "#00007d", "#ffd800", "#006dff", "#0000ff", "#ffffff"};
    private String[] teamsState = new String[]{"IT", "IN", "US", "GB", "GB", "DE", "AT", "FR", "CH", "IT", "GB"};

    @Test
    public void testConstructorsData() {

        selectSeason(2016);

        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.START))).perform(open());

        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_current_season_constructor));

        // test 11 Teams
        onView(withId(R.id.list_view)).check(ViewAssertions.matches(Matchers.withListSize(11)));
        System.out.println("Teams count: 11, OK");

        ImageUtils imageUtils = ImageUtils_.getInstance_(getContext());
        for (int i = 0; i < 11; i++) {
            onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(i).onChildView(withId(R.id.constructor_item_name)).check(matches(withText(teamsName[i])));
            onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(i).onChildView(withId(R.id.constructor_color)).check(matches(Matchers.withBgColor(Color.parseColor(teamsColor[i]))));

            Bitmap bitmap = imageUtils.getFlagForCountryCode(teamsState[i]);
            onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(i).onChildView(withId(R.id.constructor_item_flag)).check(matches(Matchers.withDrawableName(bitmap, teamsState[i])));
            System.out.println(teamsName[i] + " OK");
        }
    }

    @Test
    public void testConstructorsAlphaOrder() {

        selectSeason(2016);

        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.START))).perform(open());

        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_current_season_constructor));

        System.out.println("Test alpha order");
        for (int i = 0; i < 11; i++) {
            onData(anything())
                    .inAdapterView(withId(R.id.list_view)).atPosition(i).onChildView(withId(R.id.constructor_item_name))
                    .check(matches(withText(teamsName[i])));
            System.out.println(teamsName[i] + " OK");
        }
        System.out.println("Alpha order OK");
    }
}