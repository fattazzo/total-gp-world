package com.gmail.fattazzo.formula1world.ergast.imagedb.objects

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import com.gmail.fattazzo.formula1world.domain.F1Season

/**
 * @author fattazzo
 *
 *
 * date: 05/06/17
 */
@Table(name = "seasons")
class Season : Model() {

    @Column
    var url: String? = null

    @Column
    var description: String? = null

    fun toF1Season(): F1Season {
        val f1Season = F1Season()
        f1Season.year = id!!.toInt()
        f1Season.url = url
        f1Season.description = description
        f1Season.current = false
        return f1Season
    }

    override fun toString(): String {
        return "Season{" +
                "url='" + url + '\'' +
                ", description='" + description + '\'' +
                '}'
    }
}
