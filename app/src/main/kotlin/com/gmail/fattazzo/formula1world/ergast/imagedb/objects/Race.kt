package com.gmail.fattazzo.formula1world.ergast.imagedb.objects

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import com.gmail.fattazzo.formula1world.domain.F1Race

/**
 * @author fattazzo
 *
 *
 * date: 05/06/17
 */
@Table(name = "races")
class Race : Model() {

    @Column
    internal var year: Int = 0

    @Column
    internal var round: Int = 0

    @Column(name = "circuitId")
    internal var circuit: Circuit? = null

    @Column
    internal var url: String? = null

    @Column
    internal var name: String? = null

    @Column
    internal var date: String? = null

    @Column
    internal var time: String? = null

    fun toF1Race(): F1Race {
        val f1Circuit = if (this.circuit != null) this.circuit!!.toF1Circuit() else null
        return F1Race(this.year, this.round, this.url!!, this.name!!, f1Circuit!!, this.date, this.time)
    }

    override fun toString(): String {
        return "Race{" +
                "year=" + year +
                ", round=" + round +
                ", circuit=" + circuit +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}'
    }
}
