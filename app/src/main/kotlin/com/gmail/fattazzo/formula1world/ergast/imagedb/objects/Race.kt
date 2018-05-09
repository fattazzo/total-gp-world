/*
 * Project: total-gp-world
 * File: Race.kt
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
import com.gmail.fattazzo.formula1world.domain.F1Race

/**
 * @author fattazzo
 *
 *
 * date: 05/06/17
 */
@Table(name = "races")
class Race : Model() {

    @Column
    internal var year: Int = 0

    @Column
    internal var round: Int = 0

    @Column(name = "circuitId")
    internal var circuit: Circuit? = null

    @Column
    internal var url: String? = null

    @Column
    internal var name: String? = null

    @Column
    internal var date: String? = null

    @Column
    internal var time: String? = null

    fun toF1Race(): F1Race {
        val f1Circuit = if (this.circuit != null) this.circuit!!.toF1Circuit() else null
        return F1Race(this.year, this.round, this.url!!, this.name!!, f1Circuit!!, this.date, this.time)
    }

    override fun toString(): String {
        return "Race{" +
                "year=" + year +
                ", round=" + round +
                ", circuit=" + circuit +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}'
    }
}
