package com.gmail.fattazzo.formula1world.domain

/**
 * @author fattazzo
 *
 *
 * date: 11/07/17
 */
class F1LapTime {

    var race: F1Race? = null

    var driver: F1Driver? = null

    var lap: Int = 0

    var position: Int = 0

    var time: String? = null

    var milliseconds: Int = 0

    override fun toString(): String {
        return "LapTime{" +
                "race=" + race +
                ", driver=" + driver +
                ", lap=" + lap +
                ", position=" + position +
                ", time='" + time + '\'' +
                ", milliseconds=" + milliseconds +
                '}'
    }
}
