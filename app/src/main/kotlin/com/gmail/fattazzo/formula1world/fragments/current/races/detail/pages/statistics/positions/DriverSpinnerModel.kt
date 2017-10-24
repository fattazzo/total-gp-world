package com.gmail.fattazzo.formula1world.fragments.current.races.detail.pages.statistics.positions

import com.gmail.fattazzo.formula1world.domain.F1Constructor
import com.gmail.fattazzo.formula1world.domain.F1Driver

/**
 * @author fattazzo
 *
 *
 * date: 11/07/17
 */
class DriverSpinnerModel(var driver: F1Driver?, var constructor: F1Constructor?) {

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val that = o as DriverSpinnerModel?

        return driver == that!!.driver

    }

    override fun hashCode(): Int {
        return driver!!.hashCode()
    }
}
