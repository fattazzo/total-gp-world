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
