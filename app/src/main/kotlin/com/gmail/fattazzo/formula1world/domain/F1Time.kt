package com.gmail.fattazzo.formula1world.domain

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
class F1Time {

    var millis: Int = 0

    var time: String? = null

    constructor() {}

    constructor(time: String?) {
        this.time = time
    }

    override fun toString(): String {
        return "Time{" +
                "millis=" + millis +
                ", time='" + time + '\'' +
                '}'
    }
}
