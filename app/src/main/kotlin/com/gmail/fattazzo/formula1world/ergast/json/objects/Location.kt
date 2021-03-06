/*
 * Project: total-gp-world
 * File: Location.kt
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

import com.gmail.fattazzo.formula1world.domain.F1Location

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
internal class Location(private val lat: Float, private val lng: Float, private val locality: String, private val country: String) {

    fun toF1Location(): F1Location {
        val f1Location = F1Location()
        f1Location.lat = this.lat
        f1Location.lng = this.lng
        f1Location.locality = this.locality
        f1Location.country = this.country
        return f1Location
    }

    override fun toString(): String {
        return "Location{" +
                "lat=" + lat +
                ", lng=" + lng +
                ", locality='" + locality + '\'' +
                ", country='" + country + '\'' +
                '}'
    }
}
