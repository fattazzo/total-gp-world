package com.gmail.fattazzo.formula1world.ergast.json.objects

import com.gmail.fattazzo.formula1world.domain.F1Time

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
class Time(private val millis: Int, private val time: String) {

    internal fun toF1Time(): F1Time {
        val f1Time = F1Time()
        f1Time.millis = this.millis
        f1Time.time = this.time
        return f1Time
    }

    override fun toString(): String {
        return "Time{" +
                "millis=" + millis +
                ", time='" + time + '\'' +
                '}'
    }
}
