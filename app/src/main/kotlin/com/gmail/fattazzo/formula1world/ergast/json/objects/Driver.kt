/*
 * Project: total-gp-world
 * File: Driver.kt
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

import com.gmail.fattazzo.formula1world.domain.F1Driver

import org.apache.commons.lang3.time.DateUtils

import java.io.Serializable

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
class Driver(private val driverId: String, private val permanentNumber: Int, private val code: String, private val url: String, private val givenName: String, private val familyName: String, private val dateOfBirth: String, private val nationality: String) : Serializable {

    fun toF1Driver(): F1Driver {
        val f1Driver = com.gmail.fattazzo.formula1world.domain.F1Driver()
        f1Driver.driverRef = this.driverId
        f1Driver.code = this.code
        try {
            f1Driver.dateOfBirth = DateUtils.parseDate(this.dateOfBirth, "yyyy-MM-dd")
        } catch (e: Exception) {
            f1Driver.dateOfBirth = null
        }

        f1Driver.familyName = this.familyName
        f1Driver.givenName = this.givenName
        f1Driver.number = this.permanentNumber
        f1Driver.url = this.url
        f1Driver.nationality = this.nationality

        return f1Driver
    }

    override fun toString(): String {
        return "Driver{" +
                "driverId='" + driverId + '\'' +
                ", permanentNumber=" + permanentNumber +
                ", code='" + code + '\'' +
                ", url='" + url + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", nationality='" + nationality + '\'' +
                '}'
    }
}
