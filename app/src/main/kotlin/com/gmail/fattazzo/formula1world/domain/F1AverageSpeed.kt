package com.gmail.fattazzo.formula1world.domain

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
class F1AverageSpeed {

    var units: String? = null

    var speed: Double? = null

    constructor(units: String, speed: Double?) {
        this.units = units
        this.speed = speed
    }

    constructor() {

    }

    override fun toString(): String {
        return "AverageSpeed{" +
                "units='" + units + '\'' +
                ", speed=" + speed +
                '}'
    }
}
