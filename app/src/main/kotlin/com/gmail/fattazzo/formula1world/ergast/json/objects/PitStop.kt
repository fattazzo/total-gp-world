package com.gmail.fattazzo.formula1world.ergast.json.objects

import com.gmail.fattazzo.formula1world.domain.F1Driver
import com.gmail.fattazzo.formula1world.domain.F1PitStop
import com.gmail.fattazzo.formula1world.domain.F1Race
import com.gmail.fattazzo.formula1world.ergast.json.parser.Parser

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
internal class PitStop {

    private val driverId: String? = null
    private val stop: Int = 0
    private val lap: Int = 0
    private val time: String? = null
    private val duration: String? = null

    fun toF1PitStop(race: F1Race, drivers: List<F1Driver>): F1PitStop {
        val f1PitStop = F1PitStop()
        f1PitStop.race = race
        val f1Driver = F1Driver()
        f1Driver.driverRef = driverId
        if (drivers.contains(f1Driver)) {
            f1PitStop.driver = drivers[drivers.indexOf(f1Driver)]
        }
        f1PitStop.duration = duration
        f1PitStop.lap = lap
        f1PitStop.stop = stop
        f1PitStop.time = time
        f1PitStop.milliseconds = Parser.parseTimeToMillisec(duration)

        return f1PitStop
    }

    override fun toString(): String {
        return "PitStop{" +
                "driverId='" + driverId + '\'' +
                ", stop=" + stop +
                ", lap=" + lap +
                ", time='" + time + '\'' +
                ", duration='" + duration + '\'' +
                '}'
    }
}
