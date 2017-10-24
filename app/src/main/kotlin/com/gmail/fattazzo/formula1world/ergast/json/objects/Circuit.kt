package com.gmail.fattazzo.formula1world.ergast.json.objects

import com.gmail.fattazzo.formula1world.domain.F1Circuit

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
class Circuit internal constructor(private val circuitId: String, private val url: String, private val circuitName: String, private val location: Location?) {

    internal fun toF1Circuit(): F1Circuit {
        val f1Circuit = F1Circuit()
        f1Circuit.circuitRef = this.circuitId
        f1Circuit.name = this.circuitName
        f1Circuit.url = this.url
        if (this.location != null) {
            f1Circuit.location = this.location.toF1Location()
        }
        return f1Circuit
    }

    override fun toString(): String {
        return "Circuit{" +
                "circuitId='" + circuitId + '\'' +
                ", url='" + url + '\'' +
                ", circuitName='" + circuitName + '\'' +
                ", location=" + location +
                '}'
    }
}
