/*
 * Project: total-gp-world
 * File: Circuit.kt
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
import com.gmail.fattazzo.formula1world.domain.F1Circuit
import com.gmail.fattazzo.formula1world.domain.F1Location

/**
 * @author fattazzo
 *
 *
 * date: 05/06/17
 */
@Table(name = "circuits")
class Circuit : Model() {

    @Column
    var circuitRef: String? = null

    @Column
    internal var url: String? = null

    @Column
    var name: String? = null

    @Column
    var lat: Float = 0.toFloat()

    @Column
    var lng: Float = 0.toFloat()

    @Column
    internal var alt: Int = 0

    @Column
    var location: String? = null

    @Column
    var country: String? = null

    fun toF1Circuit(): F1Circuit {
        val f1Circuit = F1Circuit()
        f1Circuit.circuitRef = this.circuitRef
        f1Circuit.url = this.url
        f1Circuit.name = this.name
        f1Circuit.location = F1Location(lat, lng, location!!, country!!)
        return f1Circuit
    }

    override fun toString(): String {
        return "Circuit{" +
                "circuitId=" + id +
                ", circuitRef='" + circuitRef + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", alt=" + alt +
                ", location='" + location + '\'' +
                ", country='" + country + '\'' +
                '}'
    }
}
