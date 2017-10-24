package com.gmail.fattazzo.formula1world.ergast.json.objects

import com.gmail.fattazzo.formula1world.domain.F1Race

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
class Schedule(private val season: Int, private val round: Int, private val url: String, private val raceName: String, private val circuit: Circuit?, val date: String, val time: String) {

    fun toF1Race(): F1Race {
        val f1Circuit = if (this.circuit != null) this.circuit.toF1Circuit() else null
        return F1Race(season, round, url, raceName, f1Circuit!!, date, time)
    }

    override fun toString(): String {
        return "Schedule{" +
                "season=" + season +
                ", round=" + round +
                ", url='" + url + '\'' +
                ", raceName='" + raceName + '\'' +
                ", circuit=" + circuit +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}'
    }
}
