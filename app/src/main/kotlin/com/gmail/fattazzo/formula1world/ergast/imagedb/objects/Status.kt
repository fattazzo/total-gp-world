package com.gmail.fattazzo.formula1world.ergast.imagedb.objects

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table

/**
 * @author fattazzo
 *
 *
 * date: 05/06/17
 */
@Table(name = "status")
class Status : Model() {

    @Column
    internal var status: String? = null

    override fun toString(): String {
        return "Status{" +
                "statusid=" + id +
                ", status='" + status + '\'' +
                '}'
    }
}
