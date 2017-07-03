package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.pitstops.comparator;

import com.gmail.fattazzo.formula1world.domain.F1PitStop;

import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;

/**
 * @author fattazzo
 *         <p/>
 *         date: 03/07/17
 */
public class F1PitStopTimeComparator implements Comparator<F1PitStop> {

    @Override
    public int compare(F1PitStop pit1, F1PitStop pit2) {
        int timeCompare = StringUtils.defaultString(pit1.time).compareTo(StringUtils.defaultString(pit2.time));
        if (timeCompare != 0) {
            return timeCompare;
        }

        return Integer.valueOf(pit1.lap).compareTo(pit2.lap);
    }
}
