package com.gmail.fattazzo.formula1world.fragments.current.drivers;

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
 *         date: 18/07/17
 */
public class CurrentDriversFragmentTest extends BaseTest {

    private String[] driversName = new String[]{"Carlos Sainz","Daniel Ricciardo","Daniil Kvyat","Esteban Gutiérrez","Esteban Ocon","Felipe Massa","Felipe Nasr","Fernando Alonso",
            "Jenson Button","Jolyon Palmer","Kevin Magnussen","Kimi Räikkönen","Lewis Hamilton","Marcus Ericsson","Max Verstappen","Nico Hülkenberg","Nico Rosberg",
            "Pascal Wehrlein","Rio Haryanto","Romain Grosjean","Sebastian Vettel","Sergio Pérez","Stoffel Vandoorne","Valtteri Bottas",};
    private String[] driversColor = new String[]{"#0000ff","#00007d","#0000ff","#6c0000","#92c0ff","#ffffff","#006dff","#635f60","#635f60","#ffd800","#ffd800",
            "#c30000","#00cfba","#006dff","#0000ff","#00007d","#3e1700","#00cfba","#92c0ff","#92c0ff","#6c0000","#c30000","#3e1700","#635f60","#ffffff",};
    private String[] driversNumber = new String[]{"55","3","26","21","31","19","12","14","22","30","20","7","44","9","33","27","6","94","88","8","5","11","2","77",};
    private String[] driversState = new String[]{"ES","AU","RU","MX","FR","BR","BR","ES","GB","GB","DK","FI","GB","SE","NL","DE","DE","DE","ID","FR","DE","MX","BE","FI",};

    @Test
    public void testDriversData() {

        selectSeason(2016);

        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.START))).perform(open());

        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_current_season_driver));

        // test 24 drivers
        onView(withId(R.id.list_view)).check(ViewAssertions.matches(Matchers.withListSize(24)));
        System.out.println("Drivers count: 24, OK");

        ImageUtils imageUtils = ImageUtils_.getInstance_(getContext());
        for (int i = 0; i < 11; i++) {
            onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(i).onChildView(withId(R.id.driver_item_name)).check(matches(withText(driversName[i])));
            onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(i).onChildView(withId(R.id.driver_color)).check(matches(Matchers.withBgColor(Color.parseColor(driversColor[i]))));
            onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(i).onChildView(withId(R.id.driver_item_number)).check(matches(withText(driversNumber[i])));

            Bitmap bitmap = imageUtils.getFlagForCountryCode(driversState[i]);
            onData(anything()).inAdapterView(withId(R.id.list_view)).atPosition(i).onChildView(withId(R.id.driver_item_flag)).check(matches(Matchers.withDrawableName(bitmap, driversState[i])));
            System.out.println(driversName[i] + " OK");
        }
    }

    @Test
    public void testDriversAlphaOrder() {

        selectSeason(2016);

        onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.START))).perform(open());

        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_current_season_driver));

        System.out.println("Test alpha order");
        for (int i = 0; i < 11; i++) {
            onData(anything())
                    .inAdapterView(withId(R.id.list_view)).atPosition(i).onChildView(withId(R.id.driver_item_name))
                    .check(matches(withText(driversName[i])));
            System.out.println(driversName[i] + " OK");
        }
        System.out.println("Alpha order OK");
    }
}