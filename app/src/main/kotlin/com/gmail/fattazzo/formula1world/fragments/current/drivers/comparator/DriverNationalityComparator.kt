package com.gmail.fattazzo.formula1world.fragments.current.drivers.comparator

import com.gmail.fattazzo.formula1world.domain.F1Driver

import org.apache.commons.lang3.StringUtils

import java.util.Comparator

/**
 * @author fattazzo
 *
 *
 * date: 16/04/17
 */
class DriverNationalityComparator : Comparator<F1Driver> {

    override fun compare(d1: F1Driver, d2: F1Driver): Int {
        return StringUtils.defaultString(d1.nationality).compareTo(StringUtils.defaultString(d2.nationality))
    }
}
