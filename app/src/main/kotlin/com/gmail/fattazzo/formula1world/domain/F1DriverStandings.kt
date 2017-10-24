package com.gmail.fattazzo.formula1world.domain

/**
 * @author fattazzo
 *
 *
 * date: 05/06/17
 */
class F1DriverStandings {

    var position: Int = 0

    var positionText: String? = null

    var points: Float = 0.toFloat()

    var wins: Int = 0

    var driver: F1Driver? = null

    var constructor: F1Constructor? = null

    override fun toString(): String {
        return "F1DriverStandings{" +
                "position=" + position +
                ", positionText='" + positionText + '\'' +
                ", points=" + points +
                ", wins=" + wins +
                ", driver=" + driver +
                ", constructor=" + constructor +
                '}'
    }
}
