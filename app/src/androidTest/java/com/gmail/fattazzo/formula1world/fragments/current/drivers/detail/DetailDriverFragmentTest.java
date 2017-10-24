package com.gmail.fattazzo.formula1world.fragments.current.drivers.detail;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.view.Gravity;

import com.gmail.fattazzo.formula1world.BaseTest;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.TestConfig;
import com.gmail.fattazzo.formula1world.domain.F1Driver;
import com.gmail.fattazzo.formula1world.domain.F1Result;
import com.gmail.fattazzo.formula1world.ergast.Ergast;
import com.gmail.fattazzo.formula1world.ergast.Ergast_;
import com.gmail.fattazzo.formula1world.fragments.current.drivers.comparator.DriverNameComparator;
import com.gmail.fattazzo.formula1world.service.DataService;
import com.gmail.fattazzo.formula1world.service.DataService_;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static android.support.test.espresso.web.model.Atoms.getCurrentUrl;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static com.gmail.fattazzo.formula1world.matchers.Matchers.withChildViewCount;
import static com.gmail.fattazzo.formula1world.matchers.Matchers.withIndex;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;

/**
 * @author fattazzo
 *         <p/>
 *         date: 05/09/17
 */
public class DetailDriverFragmentTest extends BaseTest {

    @Test
    public void testProgress() {
        for (int i = TestConfig.INSTANCE.getStartYear(); i<=getLastAvailableSeason(); i++) {
            testSeasonProgress(i);
        }
    }

    @Test
    public void testRanking() {
        for (int i=TestConfig.INSTANCE.getStartYear(); i<=getLastAvailableSeason(); i++) {
            testSeasonRanking(i);
        }
    }

    @Test
    public void testInfo() {
        for (int i=TestConfig.INSTANCE.getStartYear(); i<=getLastAvailableSeason(); i++) {
            testSeasonInfo(i);
        }
    }

    @Test
    public void testVettel2016() {
        F1Driver vettel = new F1Driver();
        vettel.setDriverRef("vettel");

        Ergast ergast = Ergast_.getInstance_(getContext());
        ergast.setSeason(2016);
        DataService dataService = DataService_.getInstance_(getContext());
        List<F1Result> vettelResults = dataService.loadDriverRacesResult(vettel);

        selectSeason(2016);

        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.START))).perform(open());
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_current_season_driver));

        onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(20).onChildView(withId(R.id.driver_item_name)).check(matches(withText("Sebastian Vettel")));
        onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(20).onChildView(withId(R.id.driver_item_name)).perform(click());

        onView(withId(R.id.title)).check(matches(withText("Sebastian Vettel")));

        // Progress
        onView(withId(R.id.view_pager)).check(matches(hasDescendant(withText(getContext().getString(R.string.detail_driver_season_progress_tab_title)))));
        onView(withId(R.id.progress_driver_table_layout)).check(matches(withChildViewCount(vettelResults.size()+1, null)));

        int pos=0;
        for (F1Result result:vettelResults) {
            onView(withIndex(withId(R.id.race_results_position), pos)).check(matches(withText(result.getPositionText())));
            onView(withIndex(withId(R.id.race_results_laps), pos)).check(matches(withText(String.valueOf(result.getLaps()))));
            onView(withIndex(withId(R.id.race_results_grid), pos)).check(matches(withText(String.valueOf(result.getGrid()))));
            String time = "-";
            if (result.getTime() != null) {
                time = StringUtils.defaultString(result.getTime().getTime(),"-");
            }
            onView(withIndex(withId(R.id.race_results_time), pos)).check(matches(withText(time)));
            onView(withIndex(withId(R.id.race_results_status), pos)).check(matches(withText(result.getStatus())));

            pos++;
        }
        onView(withId(R.id.swipe_refresh_layout)).perform(swipeDown());

        pos=0;
        for (F1Result result:vettelResults) {
            onView(withIndex(withId(R.id.race_results_position), pos)).check(matches(withText(result.getPositionText())));
            onView(withIndex(withId(R.id.race_results_laps), pos)).check(matches(withText(String.valueOf(result.getLaps()))));
            onView(withIndex(withId(R.id.race_results_grid), pos)).check(matches(withText(String.valueOf(result.getGrid()))));
            String time = "-";
            if (result.getTime() != null) {
                time = StringUtils.defaultString(result.getTime().getTime(),"-");
            }
            onView(withIndex(withId(R.id.race_results_time), pos)).check(matches(withText(time)));
            onView(withIndex(withId(R.id.race_results_status), pos)).check(matches(withText(result.getStatus())));

            pos++;
        }

        // Classifica
        onView(withId(R.id.view_pager)).perform(swipeLeft());
        onView(withId(R.id.view_pager)).check(matches(hasDescendant(withText(getContext().getString(R.string.detail_driver_ranking_tab_title)))));

        // Info
        onView(withId(R.id.view_pager)).perform(swipeLeft());
        onView(withId(R.id.view_pager)).check(matches(hasDescendant(withText(getContext().getString(R.string.info_fragment_title)))));

        onView(withId(R.id.view_pager)).perform(swipeRight());
        onView(withId(R.id.view_pager)).perform(swipeRight());

        onView(withId(R.id.view_pager)).check(matches(hasDescendant(withText(getContext().getString(R.string.detail_driver_season_progress_tab_title)))));
    }

    private void testSeasonProgress(int season) {
        selectSeason(season);

        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.START))).perform(open());
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_current_season_driver));

        Ergast ergast = Ergast_.getInstance_(getContext());
        ergast.setSeason(season);
        DataService dataService = DataService_.getInstance_(getContext());
        List<F1Driver> drivers = dataService.loadDrivers();
        Collections.sort(drivers,new DriverNameComparator());

        for (int i=0; i<=drivers.size()-1; i++) {
            onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(i).onChildView(withId(R.id.driver_item_name)).perform(click());

            List<F1Result> driverResults = dataService.loadDriverRacesResult(drivers.get(i));
            int pos=0;
            for (F1Result result:driverResults) {
                onView(withIndex(withId(R.id.race_results_position), pos)).check(matches(withText(result.getPositionText())));
                onView(withIndex(withId(R.id.race_results_laps), pos)).check(matches(withText(String.valueOf(result.getLaps()))));
                onView(withIndex(withId(R.id.race_results_grid), pos)).check(matches(withText(String.valueOf(result.getGrid()))));
                String time = "-";
                if (result.getTime() != null) {
                    time = StringUtils.defaultString(result.getTime().getTime(),"-");
                }
                onView(withIndex(withId(R.id.race_results_time), pos)).check(matches(withText(time)));
                onView(withIndex(withId(R.id.race_results_status), pos)).check(matches(withText(result.getStatus())));
                pos++;
            }
            onView(isRoot()).perform(ViewActions.pressBack());
        }
    }

    private void testSeasonRanking(int season) {
        selectSeason(season);

        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.START))).perform(open());
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_current_season_driver));

        Ergast ergast = Ergast_.getInstance_(getContext());
        ergast.setSeason(season);
        DataService dataService = DataService_.getInstance_(getContext());
        List<F1Driver> drivers = dataService.loadDrivers();
        Collections.sort(drivers,new DriverNameComparator());

        for (int i=0; i<=drivers.size()-1; i++) {
            onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(i).onChildView(withId(R.id.driver_item_name)).perform(click());

            onView(withId(R.id.view_pager)).perform(swipeLeft());
            onView(withId(R.id.view_pager)).check(matches(hasDescendant(withText(getContext().getString(R.string.detail_driver_ranking_tab_title)))));

            onView(withId(R.id.textView2)).check(matches(withText(getContext().getString(R.string.detail_driver_positions_chart))));
            onView(withId(R.id.textView3)).check(matches(withText(getContext().getString(R.string.detail_driver_points_chart))));

            onView(isRoot()).perform(ViewActions.pressBack());
        }
    }

    private void testSeasonInfo(int season) {
        selectSeason(season);

        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.START))).perform(open());
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_current_season_driver));

        Ergast ergast = Ergast_.getInstance_(getContext());
        ergast.setSeason(season);
        DataService dataService = DataService_.getInstance_(getContext());
        List<F1Driver> drivers = dataService.loadDrivers();
        Collections.sort(drivers,new DriverNameComparator());

        for (int i=0; i<=drivers.size()-1; i++) {
            onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(i).onChildView(withId(R.id.driver_item_name)).perform(click());

            onView(withId(R.id.view_pager)).perform(swipeLeft());
            onView(withId(R.id.view_pager)).perform(swipeLeft());
            onView(withId(R.id.view_pager)).check(matches(hasDescendant(withText(getContext().getString(R.string.info_fragment_title)))));

            onWebView(withId(R.id.webview)).check(webMatches(getCurrentUrl(), containsString(StringUtils.substringAfterLast(drivers.get(i).getFamilyName()," "))));
            onWebView(withId(R.id.webview)).check(webMatches(getCurrentUrl(), containsString(StringUtils.substringAfterLast(drivers.get(i).getGivenName()," "))));

            //onWebView(withId(R.id.webview)).withElement(findElement(Locator.ID, "firstHeading")).check(webMatches(getText(), containsString(drivers.get(i).familyName)));
            //onWebView(withId(R.id.webview)).withElement(findElement(Locator.ID, "firstHeading")).check(webMatches(getText(), containsString(drivers.get(i).givenName)));

            onView(isRoot()).perform(ViewActions.pressBack());
        }
    }
}
