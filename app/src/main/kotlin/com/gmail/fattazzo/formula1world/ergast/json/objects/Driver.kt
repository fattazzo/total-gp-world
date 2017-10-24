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
