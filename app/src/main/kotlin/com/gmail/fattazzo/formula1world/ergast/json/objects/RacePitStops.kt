package com.gmail.fattazzo.formula1world.ergast.json.objects

import com.gmail.fattazzo.formula1world.domain.F1Driver
import com.gmail.fattazzo.formula1world.domain.F1PitStop
import com.gmail.fattazzo.formula1world.domain.F1Race

import org.apache.commons.collections4.CollectionUtils

import java.util.ArrayList

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
class RacePitStops {
    private val season: Int = 0
    private val round: Int = 0
    private val url: String? = null
    private val raceName: String? = null
    private val circuit: Circuit? = null
    private val date: String? = null
    private val time: String? = null
    private val pitStops: List<PitStop>? = null

    fun toF1PitStop(race: F1Race, drivers: List<F1Driver>): List<F1PitStop> {
        val pits = ArrayList<F1PitStop>()

        val pitStopsRace = CollectionUtils.emptyIfNull(pitStops)
        for (pitStop in pitStopsRace) {
            pits.add(pitStop.toF1PitStop(race, drivers))
        }

        return pits
    }

    override fun toString(): String {
        return "RacePitStops{" +
                "season=" + season +
                ", round=" + round +
                ", url='" + url + '\'' +
                ", raceName='" + raceName + '\'' +
                ", circuit=" + circuit +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", pitStops=" + pitStops +
                '}'
    }
}
