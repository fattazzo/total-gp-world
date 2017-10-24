package com.gmail.fattazzo.formula1world.fragments.current.drivers.comparator

import com.gmail.fattazzo.formula1world.domain.F1Driver

import java.util.Comparator

/**
 * @author fattazzo
 *
 *
 * date: 16/04/17
 */
class DriverNameComparator : Comparator<F1Driver> {

    override fun compare(d1: F1Driver, d2: F1Driver): Int {
        val d1FullName = d1.fullName
        val d2FullName = d2.fullName

        return d1FullName.compareTo(d2FullName)
    }
}
