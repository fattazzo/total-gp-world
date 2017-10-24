package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.pitstops.comparator

import com.gmail.fattazzo.formula1world.domain.F1PitStop

import java.util.Comparator

/**
 * @author fattazzo
 *
 *
 * date: 03/07/17
 */
class F1PitStopDurationComparator : Comparator<F1PitStop> {

    override fun compare(pit1: F1PitStop, pit2: F1PitStop): Int {
        return Integer.valueOf(pit1.milliseconds)!!.compareTo(pit2.milliseconds)
    }
}
