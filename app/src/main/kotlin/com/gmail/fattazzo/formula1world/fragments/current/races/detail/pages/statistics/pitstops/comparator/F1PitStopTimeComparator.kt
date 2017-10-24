package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.pitstops.comparator

import com.gmail.fattazzo.formula1world.domain.F1PitStop

import org.apache.commons.lang3.StringUtils

import java.util.Comparator

/**
 * @author fattazzo
 *
 *
 * date: 03/07/17
 */
class F1PitStopTimeComparator : Comparator<F1PitStop> {

    override fun compare(pit1: F1PitStop, pit2: F1PitStop): Int {
        val timeCompare = StringUtils.defaultString(pit1.time).compareTo(StringUtils.defaultString(pit2.time))
        return if (timeCompare != 0) {
            timeCompare
        } else Integer.valueOf(pit1.lap)!!.compareTo(pit2.lap)

    }
}
