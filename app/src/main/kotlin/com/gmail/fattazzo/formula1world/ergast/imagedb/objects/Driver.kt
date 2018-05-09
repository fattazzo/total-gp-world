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

package com.gmail.fattazzo.formula1world.ergast.imagedb.objects

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import com.gmail.fattazzo.formula1world.domain.F1Driver

import org.apache.commons.lang3.time.DateUtils

import java.io.Serializable

/**
 * @author fattazzo
 *
 *
 * date: 05/06/17
 */
@Table(name = "drivers")
class Driver : Model(), Serializable {

    @Column
    internal var driverRef: String? = null

    @Column
    internal var number: Int = 0

    @Column
    var code: String? = null

    @Column
    var forename: String? = null

    @Column
    var surname: String? = null

    @Column
    var dob: String? = null

    @Column
    internal var url: String? = null

    @Column
    var nationality: String? = null

    override fun toString(): String {
        return "Driver{" +
                "driverId=" + id +
                ", driverRef='" + driverRef + '\'' +
                ", number=" + number +
                ", code='" + code + '\'' +
                ", forename='" + forename + '\'' +
                ", surname='" + surname + '\'' +
                ", dob=" + dob +
                ", url='" + url + '\'' +
                ", nationality='" + nationality + '\'' +
                '}'
    }

    fun toF1Driver(): F1Driver {
        val f1Driver = F1Driver()
        f1Driver.driverRef = this.driverRef
        f1Driver.code = this.code
        try {
            f1Driver.dateOfBirth = DateUtils.parseDate(this.dob!!, "yyyy-MM-dd")
        } catch (e: Exception) {
            f1Driver.dateOfBirth = null
        }

        f1Driver.familyName = this.surname
        f1Driver.givenName = this.forename
        f1Driver.number = this.number
        f1Driver.url = this.url
        f1Driver.nationality = this.nationality

        return f1Driver
    }
}
