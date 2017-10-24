package com.gmail.fattazzo.formula1world.ergast.imagedb.objects

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import com.gmail.fattazzo.formula1world.domain.F1PitStop

/**
 * @author fattazzo
 *
 *
 * date: 05/06/17
 */
@Table(name = "pitStops")
class PitStop : Model() {

    @Column(name = "raceId")
    internal var race: Race? = null

    @Column(name = "driverId")
    internal var driver: Driver? = null

    @Column
    internal var stop: Int = 0

    @Column
    internal var lap: Int = 0

    @Column(name = "time")
    internal var time: String? = null

    @Column
    internal var duration: String? = null

    @Column
    internal var milliseconds: Int = 0

    fun f1PitStop(): F1PitStop {
        val f1PitStop = F1PitStop()
        if (race != null) {
            f1PitStop.race = race!!.toF1Race()
        }
        if (driver != null) {
            f1PitStop.driver = driver!!.toF1Driver()
        }
        f1PitStop.stop = stop
        f1PitStop.lap = lap
        f1PitStop.time = time
        f1PitStop.duration = duration
        f1PitStop.milliseconds = milliseconds
        return f1PitStop
    }

    override fun toString(): String {
        return "PitStop{" +
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
