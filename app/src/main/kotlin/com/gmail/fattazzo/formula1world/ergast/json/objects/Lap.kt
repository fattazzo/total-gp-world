package com.gmail.fattazzo.formula1world.ergast.json.objects

import com.gmail.fattazzo.formula1world.domain.F1Driver
import com.gmail.fattazzo.formula1world.domain.F1LapTime
import com.gmail.fattazzo.formula1world.domain.F1Race
import com.gmail.fattazzo.formula1world.ergast.json.parser.Parser

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
class Lap internal constructor(private val number: Int, private val timings: List<Timing>) {

    fun toF1LapTime(race: F1Race, driver: F1Driver): F1LapTime {
        val f1LapTime = F1LapTime()
        f1LapTime.driver = driver
        f1LapTime.race = race
        f1LapTime.lap = number
        f1LapTime.position = timings[0].position
        f1LapTime.time = timings[0].time
        f1LapTime.milliseconds = Parser.parseTimeToMillisec(timings[0].time)
        return f1LapTime
    }

    override fun toString(): String {
        return "Lap{" +
                "number=" + number +
                ", timings=" + timings +
                '}'
    }
}
