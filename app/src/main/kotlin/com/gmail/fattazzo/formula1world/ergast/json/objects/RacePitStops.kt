/*
 * Project: total-gp-world
 * File: RacePitStops.kt
 *
 * Created by fattazzo
 * Copyright © 2018 Gianluca Fattarsi. All rights reserved.
 *
 * MIT License
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.gmail.fattazzo.formula1world.ergast.json.objects

import com.gmail.fattazzo.formula1world.domain.F1Driver
import com.gmail.fattazzo.formula1world.domain.F1PitStop
import com.gmail.fattazzo.formula1world.domain.F1Race
import org.apache.commons.collections4.CollectionUtils
import java.util.*

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
