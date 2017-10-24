package com.gmail.fattazzo.formula1world.ergast.imagedb.objects

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import com.gmail.fattazzo.formula1world.domain.F1LapTime

/**
 * @author fattazzo
 *
 *
 * date: 05/06/17
 */
@Table(name = "lapTimes")
class LapTime : Model() {

    @Column(name = "raceId")
    internal var race: Race? = null

    @Column(name = "driverId")
    internal var driver: Driver? = null

    @Column
    internal var lap: Int = 0

    @Column
    internal var position: Int = 0

    @Column
    internal var time: String? = null

    @Column
    internal var milliseconds: Int = 0

    fun toF1LapTime(): F1LapTime {
        val f1LapTime = F1LapTime()
        if (race != null) {
            f1LapTime.race = race!!.toF1Race()
        }
        if (driver != null) {
            f1LapTime.driver = driver!!.toF1Driver()
        }
        f1LapTime.milliseconds = milliseconds
        f1LapTime.time = time
        f1LapTime.position = position
        f1LapTime.lap = lap
        return f1LapTime
    }

    override fun toString(): String {
        return "LapTime{" +
                "race=" + race +
                ", driver=" + driver +
                ", lap=" + lap +
                ", position=" + position +
                ", time='" + time + '\'' +
                ", milliseconds=" + milliseconds +
                '}'
    }
}
