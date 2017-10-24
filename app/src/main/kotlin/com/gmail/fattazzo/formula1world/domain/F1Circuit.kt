package com.gmail.fattazzo.formula1world.domain

import java.io.Serializable

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
class F1Circuit : Serializable {

    var circuitRef: String? = null

    var url: String? = null

    var name: String? = null

    var location: F1Location? = null

    override fun toString(): String {
        return "F1Circuit{" +
                "circuitRef='" + circuitRef + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", location=" + location +
                '}'
    }
}
