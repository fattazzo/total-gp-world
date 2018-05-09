/*
 * Project: total-gp-world
 * File: ConstructorStandings.kt
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
import com.gmail.fattazzo.formula1world.domain.F1ConstructorStandings

/**
 * @author fattazzo
 *
 *
 * date: 05/06/17
 */
@Table(name = "constructorStandings")
class ConstructorStandings : Model() {

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

    @Column(name = "constructorId")
    internal var constructor: Constructor? = null

    fun toF1ConstructorStandings(): F1ConstructorStandings {
        val f1ConstructorStandings = F1ConstructorStandings()
        f1ConstructorStandings.points = this.points
        f1ConstructorStandings.position = this.position
        f1ConstructorStandings.positionText = this.positionText
        f1ConstructorStandings.wins = this.wins
        if (this.constructor != null) {
            f1ConstructorStandings.constructor = this.constructor!!.toF1Constructor()
        }
        return f1ConstructorStandings
    }

    override fun toString(): String {
        return "ConstructorStandings{" +
                "raceId=" + raceId +
                ", points=" + points +
                ", position=" + position +
                ", positionText='" + positionText + '\'' +
                ", wins=" + wins +
                ", constructor=" + constructor +
                '}'
    }
}
