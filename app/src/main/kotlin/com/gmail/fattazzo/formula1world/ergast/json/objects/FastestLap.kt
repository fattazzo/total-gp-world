package com.gmail.fattazzo.formula1world.ergast.json.objects

import com.gmail.fattazzo.formula1world.domain.F1FastestLap

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
internal class FastestLap(private val rank: Int, private val lap: Int, private val time: Time?, private val averageSpeed: AverageSpeed?) {

    fun toF1FastestLap(): F1FastestLap {
        val f1FastestLap = F1FastestLap()
        f1FastestLap.rank = this.rank
        f1FastestLap.lap = this.lap
        if (this.time != null) {
            f1FastestLap.time = this.time.toF1Time()
        }
        if (this.averageSpeed != null) {
            f1FastestLap.averageSpeed = this.averageSpeed.toF1AverageSpeed()
        }
        return f1FastestLap
    }

    override fun toString(): String {
        return "FastestLap{" +
                "rank=" + rank +
                ", lap=" + lap +
                ", time=" + time +
                ", averageSpeed=" + averageSpeed +
                '}'
    }
}
