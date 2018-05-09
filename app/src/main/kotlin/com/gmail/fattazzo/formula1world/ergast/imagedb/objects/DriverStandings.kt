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

package com.gmail.fattazzo.formula1world.ergast.imagedb.objects

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import com.activeandroid.query.Select
import com.gmail.fattazzo.formula1world.domain.F1DriverStandings

/**
 * @author fattazzo
 *
 *
 * date: 05/06/17
 */
@Table(name = "driverStandings")
class DriverStandings : Model() {

    @Column
    internal var raceId: Int = 0

    @Column
    internal var points: Float = 0.toFloat()

    @Column
    internal var position: Int = 0

    @Column
    internal var positionText: String? = null

    @Column
    internal var wins: Int = 0

    @Column(name = "driverId")
    internal var driver: Driver? = null

    private fun getConstructor(year: Int?): Constructor? {
        return if (year == null) {
            null
        } else Select("c.*").distinct()
                .from(Constructor::class.java).`as`("c")
                .innerJoin(DriverConstructor::class.java).`as`("dc").on("dc.constructorId = c.Id")
                .where("dc.driverId = ?", this.driver!!.id!!)
                .where("dc.year = ?", year)
                .limit(1).executeSingle()

    }

    fun toF1DriverStandings(year: Int?): F1DriverStandings {
        val f1DriverStandings = F1DriverStandings()
        f1DriverStandings.position = this.position
        f1DriverStandings.positionText = this.positionText
        f1DriverStandings.wins = this.wins
        f1DriverStandings.points = this.points
        if (this.driver != null) {
            f1DriverStandings.driver = this.driver!!.toF1Driver()
        }
        val constructor = getConstructor(year)
        if (constructor != null) {
            f1DriverStandings.constructor = constructor.toF1Constructor()
        }

        return f1DriverStandings
    }

    override fun toString(): String {
        return "DriverStandings{" +
                "raceId=" + raceId +
                ", points=" + points +
                ", position=" + position +
                ", positionText='" + positionText + '\'' +
                ", wins=" + wins +
                ", driver=" + driver +
                '}'
    }
}
