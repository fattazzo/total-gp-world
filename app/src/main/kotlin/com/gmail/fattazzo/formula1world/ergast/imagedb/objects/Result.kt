package com.gmail.fattazzo.formula1world.ergast.imagedb.objects

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import com.gmail.fattazzo.formula1world.domain.F1AverageSpeed
import com.gmail.fattazzo.formula1world.domain.F1FastestLap
import com.gmail.fattazzo.formula1world.domain.F1Result
import com.gmail.fattazzo.formula1world.domain.F1Time

import org.apache.commons.lang3.StringUtils

/**
 * @author fattazzo
 *
 *
 * date: 05/06/17
 */
@Table(name = "results")
class Result : Model() {

    @Column(name = "raceId")
    internal var race: Race? = null

    @Column(name = "driverId")
    internal var driver: Driver? = null

    @Column(name = "constructorId")
    internal var constructor: Constructor? = null

    @Column
    internal var number: Int = 0

    @Column
    internal var grid: Int = 0

    @Column
    internal var position: Int = 0

    @Column
    internal var positionText: String? = null

    @Column
    internal var positionOrder: Int = 0

    @Column
    internal var points: Float = 0.toFloat()

    @Column
    internal var laps: Int = 0

    @Column
    internal var time: String? = null

    @Column
    internal var milliseconds: Int = 0

    @Column
    internal var fastestLap: Int = 0

    @Column
    internal var rank: Int = 0

    @Column
    internal var fastestLapTime: String? = null

    @Column
    internal var fastestLapSpeed: String? = null

    @Column(name = "statusId")
    internal var status: Status? = null

    fun toF1Result(): F1Result {
        val f1Result = F1Result()
        if (this.race != null) {
            f1Result.race = this.race!!.toF1Race()
        }
        if (this.driver != null) {
            f1Result.driver = this.driver!!.toF1Driver()
        }
        if (this.constructor != null) {
            f1Result.constructor = this.constructor!!.toF1Constructor()
        }
        f1Result.number = this.number
        f1Result.grid = this.grid
        f1Result.position = this.position
        f1Result.positionText = this.positionText
        f1Result.positionOrder = this.positionOrder
        f1Result.points = this.points
        f1Result.laps = this.laps
        f1Result.time = F1Time(this.time)

        val speed = if (StringUtils.isBlank(fastestLapSpeed)) null else java.lang.Double.valueOf(fastestLapSpeed)
        f1Result.fastestLap = F1FastestLap(rank, fastestLap, F1Time(fastestLapTime), F1AverageSpeed("", speed))
        if (this.status != null) {
            f1Result.status = this.status!!.status
        }

        return f1Result
    }

    override fun toString(): String {
        return "Result{" +
                "race=" + race +
                ", driver=" + driver +
                ", constructor=" + constructor +
                ", number=" + number +
                ", grid=" + grid +
                ", position=" + position +
                ", positionText='" + positionText + '\'' +
                ", positionOrder=" + positionOrder +
                ", points=" + points +
                ", laps=" + laps +
                ", time='" + time + '\'' +
                ", milliseconds=" + milliseconds +
                ", fastestLap=" + fastestLap +
                ", rank=" + rank +
                ", fastestLapTime='" + fastestLapTime + '\'' +
                ", fastestLapSpeed='" + fastestLapSpeed + '\'' +
                ", status=" + status +
                '}'
    }
}
