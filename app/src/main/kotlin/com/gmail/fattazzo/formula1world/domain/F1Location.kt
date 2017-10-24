package com.gmail.fattazzo.formula1world.domain

import java.io.Serializable

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
class F1Location : Serializable {

    var lat: Float = 0.toFloat()
    var lng: Float = 0.toFloat()
    var locality: String? = null
    var country: String? = null

    constructor() {}

    constructor(lat: Float, lng: Float, locality: String, country: String) {
        this.lat = lat
        this.lng = lng
        this.locality = locality
        this.country = country
    }

    override fun toString(): String {
        return "F1Location{" +
                "lat=" + lat +
                ", lng=" + lng +
                ", locality='" + locality + '\'' +
                ", country='" + country + '\'' +
                '}'
    }
}
