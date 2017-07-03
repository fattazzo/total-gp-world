package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.pitstops.comparator;

import com.gmail.fattazzo.formula1world.domain.F1PitStop;

import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;

/**
 * @author fattazzo
 *         <p/>
 *         date: 03/07/17
 */
public class F1PitStopDriverComparator implements Comparator<F1PitStop> {

    @Override
    public int compare(F1PitStop pit1, F1PitStop pit2) {
        int nameCompare = StringUtils.defaultString(pit1.driver.getFullName()).compareTo(StringUtils.defaultString(pit2.driver.getFullName()));
        if (nameCompare != 0) {
            return nameCompare;
        }

        return Integer.valueOf(pit1.stop).compareTo(pit2.stop);
    }
}
