/*
 * Project: total-gp-world
 * File: RaceResults.kt
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

import com.gmail.fattazzo.formula1world.domain.F1Race
import com.gmail.fattazzo.formula1world.domain.F1Result
import org.apache.commons.collections4.CollectionUtils
import java.util.*

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
class RaceResults {

    private val season: Int = 0
    private val round: Int = 0
    private val url: String? = null
    private val raceName: String? = null
    private val circuit: Circuit? = null
    private val date: String? = null
    private val time: String? = null
    private val results: List<RaceResult>? = null

    fun toF1Result(): List<F1Result> {
        val results = ArrayList<F1Result>()

        val f1Circuit = if (this.circuit != null) this.circuit.toF1Circuit() else null
        val f1race = F1Race(this.season, this.round, this.url!!, this.raceName!!, f1Circuit!!, this.date!!, this.time!!)

        for (result in CollectionUtils.emptyIfNull(this.results)) {
            val f1Result = F1Result()
            f1Result.race = f1race
            f1Result.number = result.number
            f1Result.grid = result.grid
            f1Result.position = result.position
            f1Result.positionText = result.positionText
            f1Result.positionOrder = result.position
            f1Result.points = result.points.toFloat()
            f1Result.status = result.status
            f1Result.laps = result.laps
            if (result.constructor != null) {
                f1Result.constructor = result.constructor!!.toF1Constructor()
            }
            if (result.driver != null) {
                f1Result.driver = result.driver!!.toF1Driver()
            }
            if (result.fastestLap != null) {
                f1Result.fastestLap = result.fastestLap!!.toF1FastestLap()
            }
            if (result.time != null) {
                f1Result.time = result.time!!.toF1Time()
            }
            results.add(f1Result)
        }

        return results
    }

    override fun toString(): String {
        return "RaceResults{" +
                "season=" + season +
                ", round=" + round +
                ", url='" + url + '\'' +
                ", raceName='" + raceName + '\'' +
                ", circuit=" + circuit +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", results=" + results +
                '}'
    }
}
