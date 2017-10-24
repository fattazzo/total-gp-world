package com.gmail.fattazzo.formula1world.ergast.json.objects

import com.gmail.fattazzo.formula1world.domain.F1Constructor

import java.io.Serializable

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
class Constructor(private val constructorId: String, private val url: String, private val name: String, private val nationality: String) : Serializable {

    fun toF1Constructor(): F1Constructor {
        val f1Constructor = F1Constructor()
        f1Constructor.constructorRef = this.constructorId
        f1Constructor.name = this.name
        f1Constructor.nationality = this.nationality
        f1Constructor.url = this.url
        return f1Constructor
    }

    override fun toString(): String {
        return "Constructor{" +
                "constructorId='" + constructorId + '\'' +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", nationality='" + nationality + '\'' +
                '}'
    }
}
