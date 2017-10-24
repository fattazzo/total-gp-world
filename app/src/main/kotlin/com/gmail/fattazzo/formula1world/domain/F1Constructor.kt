package com.gmail.fattazzo.formula1world.domain

import java.io.Serializable

/**
 * @author fattazzo
 *
 *
 * date: 05/06/17
 */
class F1Constructor : Serializable {

    var constructorRef: String? = null

    var url: String? = null

    var name: String? = null

    var nationality: String? = null

    override fun toString(): String {
        return "Constructor{" +
                "constructorRef='" + constructorRef + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", nationality='" + nationality + '\'' +
                '}'
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val that = o as F1Constructor?

        return constructorRef == that!!.constructorRef

    }

    override fun hashCode(): Int {
        return constructorRef!!.hashCode()
    }
}
