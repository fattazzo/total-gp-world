/*
 * Project: total-gp-world
 * File: FastestLap.kt
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
