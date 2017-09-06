package com.gmail.fattazzo.formula1world.fragments.current.races;

import android.graphics.Bitmap;
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
 *         date: 05/09/17
 */
public class CurrentRacesFragmentText extends BaseTest {

    private String[] raceName1950 = new String[]{"British Grand Prix","Monaco Grand Prix","Indianapolis 500","Swiss Grand Prix","Belgian Grand Prix","French Grand Prix","Italian Grand Prix"};
    private String[] raceState1950 = new String[]{"GB", "MC", "US", "CH", "BE", "FR", "IT"};

    private String[] raceName2015 = new String[]{"Australian Grand Prix","Malaysian Grand Prix","Chinese Grand Prix","Bahrain Grand Prix","Spanish Grand Prix",
            "Monaco Grand Prix","Canadian Grand Prix","Austrian Grand Prix","British Grand Prix","Hungarian Grand Prix","Belgian Grand Prix","Italian Grand Prix",
            "Singapore Grand Prix","Japanese Grand Prix","Russian Grand Prix","United States Grand Prix","Mexican Grand Prix","Brazilian Grand Prix","Abu Dhabi Grand Prix"};
    private String[] raceState2015 = new String[]{"AU","MY","CN","BH","ES","MC","CA","AT","GB","HU","BE","IT","SG","JP","RU","US","MX","BR","AE"};

    private String[] raceName2016 = new String[]{"Australian Grand Prix", "Bahrain Grand Prix", "Chinese Grand Prix", "Russian Grand Prix", "Spanish Grand Prix", "Monaco Grand Prix",
            "Canadian Grand Prix", "European Grand Prix", "Austrian Grand Prix", "British Grand Prix", "Hungarian Grand Prix", "German Grand Prix", "Belgian Grand Prix",
            "Italian Grand Prix", "Singapore Grand Prix", "Malaysian Grand Prix", "Japanese Grand Prix", "United States Grand Prix", "Mexican Grand Prix", "Brazilian Grand Prix", "Abu Dhabi Grand Prix"};
    private String[] raceState2016 = new String[]{"AU", "BH", "CN", "RU", "ES", "MC", "CA", "AZ", "AT", "GB", "HU", "DE", "BE", "IT", "SG", "MY", "JP", "US", "MX", "BR", "AE"};

    @Test
    public void test2016() {

        selectSeason(2016);

        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.START))).perform(open());

        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_current_season_race));

        System.out.println("Test 2016 races");
        ImageUtils imageUtils = ImageUtils_.getInstance_(getContext());
        for (int i = 0; i < 21; i++) {
            onData(anything())
                    .inAdapterView(withId(R.id.list_view)).atPosition(i).onChildView(withId(R.id.race_item_name))
                    .check(matches(withText(raceName2016[i])));

            Bitmap bitmap = imageUtils.getFlagForCountryCode(raceState2016[i]);
            onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(i).onChildView(withId(R.id.race_item_flag)).check(matches(Matchers.withDrawableName(bitmap, raceState2016[i])));

            System.out.println(raceName2016[i] + " OK");
        }
        System.out.println("Test 2016 races OK");
    }

    @Test
    public void test2015() {

        selectSeason(2015);

        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.START))).perform(open());

        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_current_season_race));

        System.out.println("Test 2015 races");
        ImageUtils imageUtils = ImageUtils_.getInstance_(getContext());
        for (int i = 0; i < 19; i++) {
            onData(anything())
                    .inAdapterView(withId(R.id.list_view)).atPosition(i).onChildView(withId(R.id.race_item_name))
                    .check(matches(withText(raceName2015[i])));

            Bitmap bitmap = imageUtils.getFlagForCountryCode(raceState2015[i]);
            onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(i).onChildView(withId(R.id.race_item_flag)).check(matches(Matchers.withDrawableName(bitmap, raceState2015[i])));

            System.out.println(raceName2015[i] + " OK");
        }
        System.out.println("Test 2015 races OK");
    }

    @Test
    public void test1950() {

        selectSeason(1950);

        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.START))).perform(open());

        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_current_season_race));

        System.out.println("Test 1950 races");
        ImageUtils imageUtils = ImageUtils_.getInstance_(getContext());
        for (int i = 0; i < 7; i++) {
            onData(anything())
                    .inAdapterView(withId(R.id.list_view)).atPosition(i).onChildView(withId(R.id.race_item_name))
                    .check(matches(withText(raceName1950[i])));

            Bitmap bitmap = imageUtils.getFlagForCountryCode(raceState1950[i]);
            onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(i).onChildView(withId(R.id.race_item_flag)).check(matches(Matchers.withDrawableName(bitmap, raceState1950[i])));

            System.out.println(raceName1950[i] + " OK");
        }
        System.out.println("Test 1950 races OK");
    }
}
