/*
 * Project: total-gp-world
 * File: DriverStandings.kt
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

import com.gmail.fattazzo.formula1world.domain.F1DriverStandings

import org.apache.commons.collections4.CollectionUtils

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
class DriverStandings(private val position: Int, private val positionText: String, private val points: Float, private val wins: Int, private val driver: Driver?, private val constructors: List<Constructor>) {

    fun toF1DriverStandings(): F1DriverStandings {
        val f1DriverStandings = F1DriverStandings()
        f1DriverStandings.position = this.position
        f1DriverStandings.positionText = this.positionText
        f1DriverStandings.wins = this.wins
        f1DriverStandings.points = this.points
        if (this.driver != null) {
            f1DriverStandings.driver = this.driver.toF1Driver()
        }
        if (CollectionUtils.isNotEmpty(this.constructors)) {
            f1DriverStandings.constructor = this.constructors[0].toF1Constructor()
        }
        return f1DriverStandings
    }

    override fun toString(): String {
        return "DriverStandings{" +
                "position=" + position +
                ", positionText='" + positionText + '\'' +
                ", points=" + points +
                ", wins=" + wins +
                ", driver=" + driver +
                ", constructors=" + constructors +
                '}'
    }
}
