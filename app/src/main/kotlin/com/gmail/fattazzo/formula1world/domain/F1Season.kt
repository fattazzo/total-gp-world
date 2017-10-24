package com.gmail.fattazzo.formula1world.domain

/**
 * @author fattazzo
 *
 *
 * date: 09/07/17
 */
class F1Season {

    var year: Int? = null

    var url: String? = null

    var current: Boolean = false

    var description: String? = null

    override fun toString(): String {
        return "F1Season{" +
                "year=" + year +
                ", url='" + url + '\'' +
                ", current=" + current +
                ", description='" + description + '\'' +
                '}'
    }
}
