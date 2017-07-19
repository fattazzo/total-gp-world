package com.gmail.fattazzo.formula1world.fragments.home;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.web.webdriver.Locator;

import com.gmail.fattazzo.formula1world.BaseTest;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.matchers.Matchers;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.assertion.WebViewAssertions.webMatches;
import static android.support.test.espresso.web.model.Atoms.getCurrentUrl;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.getText;
import static com.gmail.fattazzo.formula1world.matchers.Matchers.withIndex;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;

/**
 * @author fattazzo
 *         <p/>
 *         date: 18/07/17
 */
public class HomeFragmentTest extends BaseTest {

    @Test
    public void testDriverStandings() {

        selectSeason(2016);

        viewNotDisplayed(onView(allOf(withId(R.id.standings_item_name), withText("Kimi Räikkönen"))));

        // top 5 drivers
        onView(withIndex(withId(R.id.standing_listview_front), 0)).check(ViewAssertions.matches(Matchers.withListSize(5)));

        // test names
        onView(allOf(withId(R.id.standings_item_name), withText("Nico Rosberg"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.standings_item_name), withText("Lewis Hamilton"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.standings_item_name), withText("Daniel Ricciardo"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.standings_item_name), withText("Sebastian Vettel"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.standings_item_name), withText("Max Verstappen"))).check(matches(isDisplayed()));

        // flip
        onView(withId(R.id.home_driver_standings_layout)).perform(click());

        // verify flip
        viewNotDisplayed(onView(allOf(withId(R.id.standings_item_name), withText("Nico Rosberg"))));

        // 19 drivers
        onView(withIndex(withId(R.id.standing_listview_back), 0)).check(ViewAssertions.matches(Matchers.withListSize(19)));

        // test names
        onView(allOf(withId(R.id.standings_item_name), withText("Kimi Räikkönen"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.standings_item_name), withText("Sergio Pérez"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.standings_item_name), withText("Valtteri Bottas"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.standings_item_name), withText("Nico Hülkenberg"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.standings_item_name), withText("Fernando Alonso"))).check(matches(isDisplayed()));

        // flip
        onView(withId(R.id.home_driver_standings_layout)).perform(click());

        // verify flip
        viewNotDisplayed(onView(allOf(withId(R.id.standings_item_name), withText("Kimi Räikkönen"))));
    }


    @Test
    public void testConstructorStandings() {

        selectSeason(2016);

        viewNotDisplayed(onView(allOf(withId(R.id.standings_item_name), withText("McLaren"))));

        // top 5 constructors
        onView(withIndex(withId(R.id.standing_listview_front), 1)).check(ViewAssertions.matches(Matchers.withListSize(5)));

        // test names
        onView(allOf(withId(R.id.standings_item_name), withText("Mercedes"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.standings_item_name), withText("Red Bull"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.standings_item_name), withText("Ferrari"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.standings_item_name), withText("Force India"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.standings_item_name), withText("Williams"))).check(matches(isDisplayed()));

        // flip
        onView(withId(R.id.home_constructor_standings_layout)).perform(click());

        // verify flip
        viewNotDisplayed(onView(allOf(withId(R.id.standings_item_name), withText("Mercedes"))));

        // 6 constructors
        onView(withIndex(withId(R.id.standing_listview_back), 1)).check(ViewAssertions.matches(Matchers.withListSize(6)));

        // test names
        onView(allOf(withId(R.id.standings_item_name), withText("McLaren"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.standings_item_name), withText("Toro Rosso"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.standings_item_name), withText("Haas F1 Team"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.standings_item_name), withText("Renault"))).check(matches(isDisplayed()));
        onView(allOf(withId(R.id.standings_item_name), withText("Sauber"))).check(matches(isDisplayed()));

        // flip
        onView(withId(R.id.home_constructor_standings_layout)).perform(click());

        // verify flip
        viewNotDisplayed(onView(allOf(withId(R.id.standings_item_name), withText("McLaren"))));
    }

    @Test
    public void testSeasonView() {

        selectSeason(2016);

        onWebView(withId(R.id.season_wv))
                .check(webMatches(getCurrentUrl(), containsString("https://en.m.wikipedia.org")));

        onWebView(withId(R.id.season_wv))
                .withElement(findElement(Locator.ID, "section_0"))
                .check(webMatches(getText(), containsString("2016 Formula One season")));

        selectSeason(2015);

        onWebView(withId(R.id.season_wv))
                .check(webMatches(getCurrentUrl(), containsString("https://en.m.wikipedia.org")));

        onWebView(withId(R.id.season_wv))
                .withElement(findElement(Locator.ID, "section_0"))
                .check(webMatches(getText(), containsString("2015 Formula One season")));
    }
}