/*
 * Project: total-gp-world
 * File: Qualification.kt
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

import com.gmail.fattazzo.formula1world.domain.F1Qualification
import com.gmail.fattazzo.formula1world.domain.F1Race

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
class Qualification {
    private val number: Int = 0
    private val position: Int = 0
    private val driver: Driver? = null
    private val constructor: Constructor? = null
    private val q1: String? = null
    private val q2: String? = null
    private val q3: String? = null

    fun toF1Qualification(race: F1Race): F1Qualification {
        val f1Qualification = F1Qualification()
        f1Qualification.race = race
        if (this.driver != null) {
            f1Qualification.driver = this.driver.toF1Driver()
        }
        if (this.constructor != null) {
            f1Qualification.constructor = this.constructor.toF1Constructor()
        }
        f1Qualification.number = this.number
        f1Qualification.position = this.position
        f1Qualification.q1 = this.q1
        f1Qualification.q2 = this.q2
        f1Qualification.q3 = this.q3
        return f1Qualification
    }

    override fun toString(): String {
        return "Qualification{" +
                "number=" + number +
                ", position=" + position +
                ", driver=" + driver +
                ", constructor=" + constructor +
                ", q1='" + q1 + '\'' +
                ", q2='" + q2 + '\'' +
                ", q3='" + q3 + '\'' +
                '}'
    }
}
