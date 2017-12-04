package com.gmail.fattazzo.formula1world.fragments.current.constructors.detail;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.view.Gravity;

import com.gmail.fattazzo.formula1world.BaseTest;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.TestConfig;
import com.gmail.fattazzo.formula1world.domain.F1Constructor;
import com.gmail.fattazzo.formula1world.domain.F1Result;
import com.gmail.fattazzo.formula1world.ergast.Ergast;
import com.gmail.fattazzo.formula1world.ergast.Ergast_;
import com.gmail.fattazzo.formula1world.service.DataService;
import com.gmail.fattazzo.formula1world.service.DataService_;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static android.support.test.espresso.web.model.Atoms.getCurrentUrl;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static com.gmail.fattazzo.formula1world.matchers.Matchers.withIndex;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.AllOf.allOf;

/**
 * @author fattazzo
 *         <p/>
 *         date: 20/09/17
 */
public class DetailConstructorFragmentTest extends BaseTest {

    @Test
    public void testProgress() {
        rotatePortrait();
        for (int i = TestConfig.getStartYear(); i <= getLastAvailableSeason(); i++) {
            testSeasonProgress(i);
        }
    }

    @Test
    public void testInfo() {
        rotatePortrait();
        for (int i = TestConfig.getStartYear(); i <= getLastAvailableSeason(); i++) {
            testSeasonInfo(i);
        }
    }

    private void testSeasonProgress(int season) {
        selectSeason(season);

        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.START))).perform(open());
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_current_season_constructor));

        Ergast ergast = Ergast_.getInstance_(getContext());
        ergast.setSeason(season);
        DataService dataService = DataService_.getInstance_(getContext());
        List<F1Constructor> constructors = new ArrayList<>();
        constructors.addAll(dataService.loadConstructors());

        for (int i = 0; i <= constructors.size() - 1; i++) {
            onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(i).onChildView(withId(R.id.constructor_item_name)).perform(click());

            List<F1Result> constructorResults = dataService.loadConstructorRacesResult(constructors.get(i));

            MultiValuedMap<String, F1Result> resultsMap = new ArrayListValuedHashMap<>();

            for (F1Result result : constructorResults) {
                String driverName = result.getDriver() != null ? result.getDriver().getFullName() : "";
                resultsMap.put(driverName, result);
            }

            int pos = 0;
            for (String driverName : resultsMap.keySet()) {
                for (F1Result result : resultsMap.get(driverName)) {
                    onView(withIndex(withId(R.id.race_results_position), pos)).check(matches(withText(result.getPositionText())));
                    onView(withIndex(withId(R.id.race_results_laps), pos)).check(matches(withText(String.valueOf(result.getLaps()))));
                    onView(withIndex(withId(R.id.race_results_grid), pos)).check(matches(withText(String.valueOf(result.getGrid()))));
                    String time = "-";
                    if (result.getTime() != null) {
                        time = StringUtils.defaultString(result.getTime().getTime(), "-");
                    }
                    onView(withIndex(withId(R.id.race_results_time), pos)).check(matches(withText(time)));
                    onView(withIndex(withId(R.id.race_results_status), pos)).check(matches(withText(result.getStatus())));
                    pos++;
                }
            }
            onView(withId(R.id.swipe_refresh_layout)).perform(swipeDown());

            try {
                onView(withId(R.id.details_fragment_container)).check(matches(isDisplayed()));
                // Large screen display, no need pressBack
            } catch (Exception e) {
                onView(isRoot()).perform(ViewActions.pressBack());
            }
        }
    }

    private void testSeasonInfo(int season) {
        selectSeason(season);

        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.START))).perform(open());
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_current_season_constructor));

        Ergast ergast = Ergast_.getInstance_(getContext());
        ergast.setSeason(season);
        DataService dataService = DataService_.getInstance_(getContext());
        List<F1Constructor> constructors = new ArrayList<>();
        constructors.addAll(dataService.loadConstructors());

        for (int i = 0; i <= constructors.size() - 1; i++) {
            onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(i).onChildView(withId(R.id.constructor_item_name)).perform(click());

            swipeViewPager(R.id.view_pager,new int[]{R.string.info_fragment_title});
            onView(withId(R.id.view_pager)).check(matches(hasDescendant(withText(getContext().getString(R.string.info_fragment_title)))));

            List<String> teamToSkip = new ArrayList<>();
            teamToSkip.add("Lotus_Racing");
            teamToSkip.add("Virgin_Racing");
            teamToSkip.add("Williams_Grand_Prix_Engineering");
            teamToSkip.add("Lotus_F1");
            if (!teamToSkip.contains(StringUtils.substringAfterLast(constructors.get(i).getUrl(), "/")))
                onWebView(withId(R.id.webview)).check(webMatches(getCurrentUrl(), containsString(StringUtils.substringAfterLast(constructors.get(i).getUrl(), "/"))));

            try {
                onView(withId(R.id.details_fragment_container)).check(matches(isDisplayed()));
                // Large screen display, no need pressBack
            } catch (Exception e) {
                onView(isRoot()).perform(ViewActions.pressBack());
            }
        }
    }
}
