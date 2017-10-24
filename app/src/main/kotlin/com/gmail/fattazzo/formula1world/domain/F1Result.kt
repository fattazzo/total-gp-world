package com.gmail.fattazzo.formula1world.domain

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
class F1Result {

    var race: F1Race? = null

    var driver: F1Driver? = null

    var constructor: F1Constructor? = null

    var number: Int = 0

    var grid: Int = 0

    var position: Int = 0

    var positionText: String? = null

    var positionOrder: Int = 0

    var points: Float = 0.toFloat()

    var laps: Int = 0

    var time: F1Time? = null

    var fastestLap: F1FastestLap? = null

    var status: String? = null

    override fun toString(): String {
        return "Result{" +
                "race=" + race +
                ", driver=" + driver +
                ", constructor=" + constructor +
                ", number=" + number +
                ", grid=" + grid +
                ", position=" + position +
                ", positionText='" + positionText + '\'' +
                ", positionOrder=" + positionOrder +
                ", points=" + points +
                ", laps=" + laps +
                ", time='" + time + '\'' +
                ", fastestLap=" + fastestLap +
                ", status=" + status +
                '}'
    }
}
