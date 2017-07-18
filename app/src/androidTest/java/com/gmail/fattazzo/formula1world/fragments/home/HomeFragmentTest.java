package com.gmail.fattazzo.formula1world.fragments.home;

import com.gmail.fattazzo.formula1world.BaseTest;

import org.junit.Test;

/**
 * @author fattazzo
 *         <p/>
 *         date: 18/07/17
 */
public class HomeFragmentTest extends BaseTest {

    private static final String TAG = HomeFragmentTest.class.getSimpleName();

    @Test
    public void testDriverStandings() {

        selectSeason(2016);
    }
}