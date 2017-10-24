package com.gmail.fattazzo.formula1world.ergast.json.objects

import com.gmail.fattazzo.formula1world.domain.F1Qualification
import com.gmail.fattazzo.formula1world.domain.F1Race

/**
 * @author fattazzo
 *
 *
 * date: 06/06/17
 */
class Qualification {
    private val number: Int = 0
    private val position: Int = 0
    private val driver: Driver? = null
    private val constructor: Constructor? = null
    private val q1: String? = null
    private val q2: String? = null
    private val q3: String? = null

    fun toF1Qualification(race: F1Race): F1Qualification {
        val f1Qualification = F1Qualification()
        f1Qualification.race = race
        if (this.driver != null) {
            f1Qualification.driver = this.driver.toF1Driver()
        }
        if (this.constructor != null) {
            f1Qualification.constructor = this.constructor.toF1Constructor()
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
                "number=" + number +
                ", position=" + position +
                ", driver=" + driver +
                ", constructor=" + constructor +
                ", q1='" + q1 + '\'' +
                ", q2='" + q2 + '\'' +
                ", q3='" + q3 + '\'' +
                '}'
    }
}
