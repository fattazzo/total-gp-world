package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.pitstops.comparator;

import com.gmail.fattazzo.formula1world.domain.F1PitStop;

import java.util.Comparator;

/**
 * @author fattazzo
 *         <p/>
 *         date: 03/07/17
 */
public class F1PitStopDurationComparator implements Comparator<F1PitStop> {

    @Override
    public int compare(F1PitStop pit1, F1PitStop pit2) {
        return Integer.valueOf(pit1.milliseconds).compareTo(pit2.milliseconds);
    }
}
