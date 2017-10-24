package com.gmail.fattazzo.formula1world.ergast.json.objects

import org.apache.commons.collections4.ListUtils

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
class LapTimes(private val season: Int, private val round: Int, private val url: String, private val raceName: String, private val circuit: Circuit, private val date: String, private val time: String, private val laps: List<Lap>) {

    fun getLaps(): List<Lap> {
        return ListUtils.emptyIfNull(laps)
    }

    override fun toString(): String {
        return "LapTimes{" +
                "season=" + season +
                ", round=" + round +
                ", url='" + url + '\'' +
                ", raceName='" + raceName + '\'' +
                ", circuit=" + circuit +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", laps=" + laps +
                '}'
    }
}
