/*
 * Project: total-gp-world
 * File: LapTime.kt
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
