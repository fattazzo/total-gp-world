package com.gmail.fattazzo.formula1world.domain

/**
 * @author fattazzo
 *
 *
 * date: 29/06/17
 */
class F1Qualification {

    var race: F1Race? = null

    var driver: F1Driver? = null

    var constructor: F1Constructor? = null

    var number: Int = 0

    var position: Int = 0

    var q1: String? = null

    var q2: String? = null

    var q3: String? = null

    override fun toString(): String {
        return "Qualification{" +
                "race=" + race +
                ", driver=" + driver +
                ", constructor=" + constructor +
                ", number=" + number +
                ", position=" + position +
                ", q1='" + q1 + '\'' +
                ", q2='" + q2 + '\'' +
                ", q3='" + q3 + '\'' +
                '}'
    }
}
