package com.gmail.fattazzo.formula1world.domain

import org.apache.commons.lang3.StringUtils

import java.io.Serializable
import java.util.Date

/**
 * @author fattazzo
 *
 *
 * date: 05/06/17
 */
class F1Driver : Serializable {

    var driverRef: String? = null

    var number: Int = 0

    var code: String? = null

    var url: String? = null

    var givenName: String? = null
    var familyName: String? = null

    var dateOfBirth: Date? = null

    var nationality: String? = null

    val fullName: String
        get() = StringUtils.defaultString(givenName) + " " + StringUtils.defaultString(familyName)

    override fun toString(): String {
        return "Driver{" +
                "driverRef='" + driverRef + '\'' +
                ", number=" + number +
                ", code='" + code + '\'' +
                ", url='" + url + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", nationality='" + nationality + '\'' +
                '}'
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val driver = o as F1Driver?

        return driverRef == driver!!.driverRef

    }

    override fun hashCode(): Int {
        return driverRef!!.hashCode()
    }
}
