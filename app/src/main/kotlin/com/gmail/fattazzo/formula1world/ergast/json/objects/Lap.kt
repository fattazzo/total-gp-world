/*
 * Project: total-gp-world
 * File: Lap.kt
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
import com.gmail.fattazzo.formula1world.domain.F1LapTime
import com.gmail.fattazzo.formula1world.domain.F1Race
import com.gmail.fattazzo.formula1world.ergast.json.parser.Parser

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
class Lap internal constructor(private val number: Int, private val timings: List<Timing>) {

    fun toF1LapTime(race: F1Race, driver: F1Driver): F1LapTime {
        val f1LapTime = F1LapTime()
        f1LapTime.driver = driver
        f1LapTime.race = race
        f1LapTime.lap = number
        f1LapTime.position = timings[0].position
        f1LapTime.time = timings[0].time
        f1LapTime.milliseconds = Parser.parseTimeToMillisec(timings[0].time)
        return f1LapTime
    }

    override fun toString(): String {
        return "Lap{" +
                "number=" + number +
                ", timings=" + timings +
                '}'
    }
}
