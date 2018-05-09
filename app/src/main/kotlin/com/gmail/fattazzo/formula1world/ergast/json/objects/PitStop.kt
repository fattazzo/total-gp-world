/*
 * Project: total-gp-world
 * File: PitStop.kt
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
