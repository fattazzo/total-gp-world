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
class F1PitStopDriverComparator : Comparator<F1PitStop> {

    override fun compare(pit1: F1PitStop, pit2: F1PitStop): Int {
        val nameCompare = StringUtils.defaultString(pit1.driver!!.fullName).compareTo(StringUtils.defaultString(pit2.driver!!.fullName))
        return if (nameCompare != 0) {
            nameCompare
        } else Integer.valueOf(pit1.stop)!!.compareTo(pit2.stop)

    }
}
