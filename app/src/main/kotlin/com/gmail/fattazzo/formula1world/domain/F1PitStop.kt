package com.gmail.fattazzo.formula1world.domain

/**
 * @author fattazzo
 *
 *
 * date: 05/06/17
 */
class F1PitStop {

    var race: F1Race? = null

    var driver: F1Driver? = null

    var stop: Int = 0

    var lap: Int = 0

    var time: String? = null

    var duration: String? = null

    var milliseconds: Int = 0

    override fun toString(): String {
        return "F1PitStop{" +
                "race=" + race +
                ", driver=" + driver +
                ", stop=" + stop +
                ", lap=" + lap +
                ", time='" + time + '\'' +
                ", duration='" + duration + '\'' +
                ", milliseconds=" + milliseconds +
                '}'
    }
}
