package com.gmail.fattazzo.formula1world.fragments.current.drivers;

import android.graphics.Bitmap;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.view.Gravity;

import com.gmail.fattazzo.formula1world.BaseTest;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1Driver;
import com.gmail.fattazzo.formula1world.ergast.Ergast;
import com.gmail.fattazzo.formula1world.ergast.Ergast_;
import com.gmail.fattazzo.formula1world.fragments.current.drivers.comparator.DriverDateOfBirthComparator;
import com.gmail.fattazzo.formula1world.fragments.current.drivers.comparator.DriverNameComparator;
import com.gmail.fattazzo.formula1world.fragments.current.drivers.comparator.DriverNationalityComparator;
import com.gmail.fattazzo.formula1world.fragments.current.drivers.comparator.DriverNumberComparator;
import com.gmail.fattazzo.formula1world.matchers.Matchers;
import com.gmail.fattazzo.formula1world.service.DataService;
import com.gmail.fattazzo.formula1world.service.DataService_;
import com.gmail.fattazzo.formula1world.utils.CountryNationality;
import com.gmail.fattazzo.formula1world.utils.ImageUtils;
import com.gmail.fattazzo.formula1world.utils.ImageUtils_;
import com.gmail.fattazzo.formula1world.utils.Utils;
import com.gmail.fattazzo.formula1world.utils.Utils_;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/**
 * @author fattazzo
 *         <p/>
 *         date: 18/07/17
 */
public class CurrentDriversFragmentTest extends BaseTest {

    @Test
    public void testDriversData() {

        for (int season = 2015; season <= 2016; season++) {
            Ergast ergast = Ergast_.getInstance_(getContext());
            ergast.setSeason(season);
            DataService dataService = DataService_.getInstance_(getContext());
            List<F1Driver> drivers = new ArrayList<>();
            drivers.addAll(dataService.loadDrivers());
            Collections.sort(drivers, new DriverNameComparator());

            selectSeason(season);
            onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.START))).perform(open());
            onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_current_season_driver));

            onView(withId(R.id.list_view)).check(ViewAssertions.matches(Matchers.withListSize(drivers.size())));
            System.out.println("Drivers count: " + drivers.size() + ", OK");

            ImageUtils imageUtils = ImageUtils_.getInstance_(getContext());
            Utils utils = Utils_.getInstance_(getContext());

            int pos = 0;
            for (F1Driver driver : drivers) {
                onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(pos).onChildView(withId(R.id.driver_item_name)).check(matches(withText(driver.getFullName())));

                int color = dataService.loadDriverColor(driver);
                onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(pos).onChildView(withId(R.id.driver_color)).check(matches(Matchers.withBgColor(color)));

                onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(pos).onChildView(withId(R.id.driver_item_number)).check(matches(withText(String.valueOf(driver.number))));

                CountryNationality countryNationality = utils.getCountryNationality(driver.nationality);
                if (countryNationality != null) {
                    Bitmap bitmap = imageUtils.getFlagForCountryCode(countryNationality.getAlpha2Code());
                    onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(pos).onChildView(withId(R.id.driver_item_flag)).check(matches(Matchers.withDrawableName(bitmap, countryNationality.getAlpha2Code())));
                }
                pos++;
            }
        }
    }

    @Test
    public void testDriversOrders() {
        for (int season = 2010; season <= 2016; season++) {
            Ergast ergast = Ergast_.getInstance_(getContext());
            ergast.setSeason(season);
            DataService dataService = DataService_.getInstance_(getContext());
            List<F1Driver> drivers = new ArrayList<>();
            drivers.addAll(dataService.loadDrivers());

            selectSeason(season);
            onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.START))).perform(open());
            onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_current_season_driver));

            testDriversOrder(drivers, new DriverDateOfBirthComparator());
            testDriversOrder(drivers, new DriverNameComparator());
            testDriversOrder(drivers, new DriverNationalityComparator());
            testDriversOrder(drivers, new DriverNumberComparator());
        }
    }

    private void testDriversOrder(List<F1Driver> drivers, Comparator<F1Driver> comparator) {

        Collections.sort(drivers, comparator);

        onView(withId(R.id.drivers_menu)).perform(click());
        if (comparator instanceof DriverDateOfBirthComparator) {
            onView(withText(R.string.sort_drivers_by_date_of_birth)).perform(click());
        } else if (comparator instanceof DriverNameComparator) {
            onView(withText(R.string.sort_drivers_by_name)).perform(click());
        } else if (comparator instanceof DriverNationalityComparator) {
            onView(withText(R.string.sort_drivers_by_nationality)).perform(click());
        } else if (comparator instanceof DriverNumberComparator) {
            onView(withText(R.string.sort_drivers_by_number)).perform(click());
        }

        System.out.println("Test alpha order");
        int pos = 0;
        for (F1Driver driver : drivers) {
            onData(anything())
                    .inAdapterView(withId(R.id.list_view)).atPosition(pos).onChildView(withId(R.id.driver_item_name))
                    .check(matches(withText(driver.getFullName())));
            System.out.println(driver.getFullName() + " OK");
            pos++;
        }
        System.out.println("Alpha order OK");
    }
}