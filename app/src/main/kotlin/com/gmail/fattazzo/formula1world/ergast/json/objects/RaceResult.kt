package com.gmail.fattazzo.formula1world.ergast.json.objects

import com.gmail.fattazzo.formula1world.domain.F1Result

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
class RaceResult internal constructor(val number: Int, val position: Int, val positionText: String,
                                      val points: Int, val driver: Driver?, val constructor: Constructor?,
                                      val grid: Int, val laps: Int, val status: String, val time: Time?, val fastestLap: FastestLap?) {

    fun toF1Result(): F1Result {
        val f1Result = F1Result()

        f1Result.number = number
        f1Result.grid = grid
        f1Result.position = position
        f1Result.positionText = positionText
        f1Result.positionOrder = position
        f1Result.points = points.toFloat()
        f1Result.status = status
        f1Result.laps = laps
        if (constructor != null) {
            f1Result.constructor = constructor.toF1Constructor()
        }
        if (driver != null) {
            f1Result.driver = driver.toF1Driver()
        }
        if (fastestLap != null) {
            f1Result.fastestLap = fastestLap.toF1FastestLap()
        }
        if (time != null) {
            f1Result.time = time.toF1Time()
        }
        return f1Result
    }

    override fun toString(): String {
        return "RaceResult{" +
                "number=" + number +
                ", position=" + position +
                ", positionText='" + positionText + '\'' +
                ", points=" + points +
                ", driver=" + driver +
                ", constructor=" + constructor +
                ", grid=" + grid +
                ", laps=" + laps +
                ", status='" + status + '\'' +
                ", time=" + time +
                ", fastestLap=" + fastestLap +
                '}'
    }
}
