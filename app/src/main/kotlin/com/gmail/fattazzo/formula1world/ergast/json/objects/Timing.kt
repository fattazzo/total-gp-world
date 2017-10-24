package com.gmail.fattazzo.formula1world.ergast.json.objects

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
internal class Timing(private val driverId: String, val position: Int, val time: String) {

    override fun toString(): String {
        return "Timing{" +
                "driverId='" + driverId + '\'' +
                ", position=" + position +
                ", time='" + time + '\'' +
                '}'
    }
}
