package com.gmail.fattazzo.formula1world.domain

import java.io.Serializable

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
class F1Race(var year: Int, var round: Int, var url: String, var name: String, var circuit: F1Circuit, var date: String?, var time: String?) : Serializable {

    override fun toString(): String {
        return "F1Race{" +
                "year=" + year +
                ", round=" + round +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", circuit=" + circuit +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}'
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val f1Race = o as F1Race?

        return if (year != f1Race!!.year) false else round == f1Race.round

    }

    override fun hashCode(): Int {
        var result = year
        result = 31 * result + round
        return result
    }
}
