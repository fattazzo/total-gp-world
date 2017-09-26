package com.gmail.fattazzo.formula1world.fragments.current.constructors;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.view.Gravity;

import com.gmail.fattazzo.formula1world.BaseTest;
import com.gmail.fattazzo.formula1world.R;
import com.gmail.fattazzo.formula1world.domain.F1Constructor;
import com.gmail.fattazzo.formula1world.domain.F1Driver;
import com.gmail.fattazzo.formula1world.ergast.Ergast;
import com.gmail.fattazzo.formula1world.ergast.Ergast_;
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
import java.util.Calendar;
import java.util.List;

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

    @Test
    public void testConstructorsFrom2010() {
        for (int i = 2010; i<= getLastAvailableSeason(); i++) {
            testSeason(i);
        }
    }

    private void testSeason(int season) {

        selectSeason(season);
        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.START))).perform(open());
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_current_season_constructor));

        Ergast ergast = Ergast_.getInstance_(getContext());
        ergast.setSeason(season);
        DataService dataService = DataService_.getInstance_(getContext());
        List<F1Constructor> constructors = new ArrayList<>();
        constructors.addAll(dataService.loadConstructors());

        // test constructors count
        onView(withId(R.id.list_view)).check(ViewAssertions.matches(Matchers.withListSize(constructors.size())));
        System.out.println("Teams count: " + constructors.size() + ", OK");

        ImageUtils imageUtils = ImageUtils_.getInstance_(getContext());
        Utils utils = Utils_.getInstance_(getContext());

        int pos=0;
        for (F1Constructor constructor : constructors) {
            onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(pos).onChildView(withId(R.id.constructor_item_name)).check(matches(withText(constructor.name)));

            int color = dataService.loadContructorColor(constructor);
            onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(pos).onChildView(withId(R.id.constructor_color)).check(matches(Matchers.withBgColor(color)));

            CountryNationality countryNationality = utils.getCountryNationality(constructor.nationality);
            if (countryNationality != null) {
                Bitmap bitmap = imageUtils.getFlagForCountryCode(countryNationality.getAlpha2Code());
                onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(pos).onChildView(withId(R.id.constructor_item_flag)).check(matches(Matchers.withDrawableName(bitmap, countryNationality.getAlpha2Code())));
            }
            System.out.println(constructor.name + " OK");

            pos++;
        }
    }
}