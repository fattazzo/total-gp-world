package com.gmail.fattazzo.formula1world.ergast.json.objects

import com.gmail.fattazzo.formula1world.domain.F1AverageSpeed

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
internal class AverageSpeed(private val units: String, private val speed: Double) {

    fun toF1AverageSpeed(): F1AverageSpeed {
        val f1AverageSpeed = F1AverageSpeed()
        f1AverageSpeed.units = this.units
        f1AverageSpeed.speed = this.speed
        return f1AverageSpeed
    }

    override fun toString(): String {
        return "AverageSpeed{" +
                "units='" + units + '\'' +
                ", speed=" + speed +
                '}'
    }
}
