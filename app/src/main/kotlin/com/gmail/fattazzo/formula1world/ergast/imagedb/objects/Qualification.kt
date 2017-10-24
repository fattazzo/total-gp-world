package com.gmail.fattazzo.formula1world.ergast.imagedb.objects

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table
import com.gmail.fattazzo.formula1world.domain.F1Qualification

/**
 * @author fattazzo
 *
 *
 * date: 05/06/17
 */
@Table(name = "qualifying")
class Qualification : Model() {

    @Column(name = "raceId")
    internal var race: Race? = null

    @Column(name = "driverId")
    internal var driver: Driver? = null

    @Column(name = "constructorId")
    internal var constructor: Constructor? = null

    @Column
    internal var number: Int = 0

    @Column
    internal var position: Int = 0

    @Column
    internal var q1: String? = null

    @Column
    internal var q2: String? = null

    @Column
    internal var q3: String? = null

    fun toF1Qualification(): F1Qualification {
        val f1Qualification = F1Qualification()
        f1Qualification.race = this.race!!.toF1Race()
        if (this.driver != null) {
            f1Qualification.driver = this.driver!!.toF1Driver()
        }
        if (this.constructor != null) {
            f1Qualification.constructor = this.constructor!!.toF1Constructor()
        }
        f1Qualification.number = this.number
        f1Qualification.position = this.position
        f1Qualification.q1 = this.q1
        f1Qualification.q2 = this.q2
        f1Qualification.q3 = this.q3
        return f1Qualification
    }

    override fun toString(): String {
        return "Qualification{" +
                "race=" + race +
                ", driver=" + driver +
                ", constructor=" + constructor +
                ", number=" + number +
                ", position=" + position +
                ", q1='" + q1 + '\'' +
                ", q2='" + q2 + '\'' +
                ", q3='" + q3 + '\'' +
                '}'
    }
}
