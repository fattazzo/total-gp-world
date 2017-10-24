package com.gmail.fattazzo.formula1world.fragments.current.drivers.comparator

import com.gmail.fattazzo.formula1world.domain.F1Driver

import java.util.Comparator

/**
 * @author fattazzo
 *
 *
 * date: 16/04/17
 */
class DriverDateOfBirthComparator : Comparator<F1Driver> {

    override fun compare(d1: F1Driver, d2: F1Driver): Int {
        val millisec1 = if (d1.dateOfBirth == null) 0 else d1.dateOfBirth!!.time
        val millisec2 = if (d2.dateOfBirth == null) 0 else d2.dateOfBirth!!.time
        return millisec1.compareTo(millisec2) * -1
    }
}
