/*
 * Project: total-gp-world
 * File: F1Result.kt
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

package com.gmail.fattazzo.formula1world.domain

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
class F1Result {

    var race: F1Race? = null

    var driver: F1Driver? = null

    var constructor: F1Constructor? = null

    var number: Int = 0

    var grid: Int = 0

    var position: Int = 0

    var positionText: String? = null

    var positionOrder: Int = 0

    var points: Float = 0.toFloat()

    var laps: Int = 0

    var time: F1Time? = null

    var fastestLap: F1FastestLap? = null

    var status: String? = null

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
                ", fastestLap=" + fastestLap +
                ", status=" + status +
                '}'
    }
}
