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
